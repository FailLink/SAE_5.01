package org.example.sae501serveur.Model.Socket.Handler.Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.sae501serveur.Model.Entity.Competence;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.Game.AttaqueDefRequest;
import org.example.sae501serveur.Model.Game.CombatResponse;
import org.example.sae501serveur.Model.Game.CombatResult;
import org.example.sae501serveur.Model.Service.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameWebSocketHandler extends TextWebSocketHandler {

    private PartieService partieService;
    private Partie partie;

    private JoueurService joueurService;
    private final Map<WebSocketSession, Joueur> sessionJoueurs=new ConcurrentHashMap<>();
    private final Map<Joueur,WebSocketSession> joueurSession=new ConcurrentHashMap<>();

    private final ArrayList<WebSocketSession> webSocketSessions=new ArrayList<>();

    private final ArrayList<Joueur> joueurs=new ArrayList<>();

    private Joueur chefDePartie;

    public Map<WebSocketSession, Joueur> getSessionJoueurs() {
        return sessionJoueurs;
    }

    public Map<Joueur, WebSocketSession> getJoueurSession() {
        return joueurSession;
    }

    public ArrayList<WebSocketSession> getWebSocketSessions() {
        return webSocketSessions;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    private final Map<Long,Competence> actions=new ConcurrentHashMap<>();

    private final int maxPlayer=4;

    private final List<AttaqueDefRequest> joueursAction = new CopyOnWriteArrayList<>();
    private final Map<String, CombatResult> reponses = new ConcurrentHashMap<>();


    Gson gson = new Gson();

    private int tourPartie = 0;


    public GameWebSocketHandler(PartieService partieService, JoueurService joueurService,Partie partie) {
        this.partieService = partieService;
        this.joueurService = joueurService;
        this.partie = partie;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> msg = objectMapper.readValue(message.getPayload(), Map.class);
        switch ((String) msg.get("type")) {
            case "connexion":
                handleConnexionMessage(session, msg, objectMapper);
                break;
            case "action":
                handleMoveMessage(session, message);
                break;
            case "deconnexion":
                session.close();
                webSocketSessions.remove(session);
                joueurSession.remove(sessionJoueurs.get(session));
                joueurs.remove(sessionJoueurs.get(session));
                if (chefDePartie == sessionJoueurs.get(session)) {
                    chefDePartie = joueurs.get(0);
                }
                sessionJoueurs.remove(session);
                for (WebSocketSession webSocketSession : webSocketSessions) {
                    webSocketSession.sendMessage(new TextMessage("{\"type\":\"joueurDeconnecte\"}"));
                }
                renvoiJoueurRafraichi(objectMapper);
                break;
            case "exclusion":
                Joueur joueur = joueurService.getJoueurById(Integer.toUnsignedLong((Integer) msg.get("joueur"))).get();
                session.sendMessage(new TextMessage(String.valueOf(joueurSession.containsKey(joueur))));
                joueurSession.get(joueur).sendMessage(new TextMessage("{\"type\":\"exclusion\"}"));
                joueurSession.get(joueur).close();
                webSocketSessions.remove(joueurSession.get(joueur));
                joueurs.remove(joueur);
                sessionJoueurs.remove(joueurSession.get(joueur));
                joueurSession.remove(joueur);
                for (WebSocketSession webSocketSession : webSocketSessions) {
                    webSocketSession.sendMessage(new TextMessage("{\"type\":\"joueurExclu\"}"));
                }
                renvoiJoueurRafraichi(objectMapper);
                break;
            case "lancementPartie":
                for (Joueur joueurBDD : joueurs) {
                    partie.addJoueur(joueurBDD);
                }
                partieService.savePartie(partie);
                for (WebSocketSession webSocketSession : webSocketSessions) {
                    if (webSocketSession != session) {
                        webSocketSession.sendMessage(new TextMessage("{\"type\":\"lancementPartie\"}"));
                    }
                }
                break;
            case "attaqueDef":
                AttaqueDefRequest attaqueRequest = gson.fromJson(message.getPayload(), AttaqueDefRequest.class);
                // Vérifie si des réponses existent déjà pour ce joueur
                synchronized (joueursAction) {
                    if (!reponses.isEmpty() && reponses.containsKey(attaqueRequest.getJoueurId())) {
                        List<CombatResult> resultList = new ArrayList<>(reponses.values());
                        reponses.clear();
                        // Ajouter le type "attaqueDef" à la réponse
                        System.out.println("reponse");
                        sendResponse(session, new CombatResponse("attaqueDefValide", resultList));
                        return;
                    }
                }
                System.out.println(attaqueRequest.toString());
                boolean joueurDejaPresent = joueursAction.stream()
                        .anyMatch(joueurP -> joueurP.getJoueurId().equals(attaqueRequest.getJoueurId()));

                if (joueurDejaPresent ) {
                    sendResponse(session, new CombatResponse("attente",
                            Collections.singletonList(new CombatResult("Action déja envoyé...", 0, 0,0))));
                    return;
                }

                joueursAction.add(attaqueRequest);
                if (joueursAction.size() == sessionJoueurs.size()) {
                    tourPartie ++;
                    // Créer une liste des IDs des joueurs
                    List<String> joueursIds = new ArrayList<>();
                    for (AttaqueDefRequest joueurP : joueursAction) {
                        if (joueurP.getPdv() > 0) {
                            joueursIds.add(joueurP.getJoueurId());
                        }
                        System.out.println(joueurP.getJoueurId() + " : " + joueurP.getPdv());
                    }


                    int degats = 0;

                    for(AttaqueDefRequest joueurP : joueursAction){
                        if ( attaqueRequest.getTour() % tourPartie == 0 ) {
                            if (Objects.equals(attaqueRequest.getCompetenceNom(),
                                    attaqueRequest.getMonstre().getTypeMonstre().getFaiblesse())) {
                                // Dégâts doublés si la compétence est la faiblesse du monstre
                                degats += joueurP.getDegats() * 2;

                            } else if (Objects.equals(attaqueRequest.getCompetenceNom(),
                                    attaqueRequest.getMonstre().getTypeMonstre().getResistance())) {
                                // Dégâts normaux si aucun avantage/désavantage
                                degats += joueurP.getDegats() / 2;

                            } else {
                                degats += joueurP.getDegats();
                            }
                        } else {
                            if (Objects.equals(attaqueRequest.getCompetenceNom(),
                                    attaqueRequest.getMonstre().getTypeMonstre().getFaiblesse())) {
                                // Dégâts divisés par 2 même si c'est la faiblesse du monstre
                                degats += joueurP.getDegats() / 2;

                            } else if (Objects.equals(attaqueRequest.getCompetenceNom(),
                                    attaqueRequest.getMonstre().getTypeMonstre().getResistance())) {
                                // Dégâts divisés par 4 si c'est la résistance du monstre
                                degats += joueurP.getDegats() / 4;

                            } else if(!Objects.equals(attaqueRequest.getCompetenceNom(), "null")) {
                                // Si la compétence n'est ni faiblesse ni résistance mais est présente, réduire de moitié
                                degats += joueurP.getDegats() / 2;
                            } else {
                                // Dégâts normaux si l'attaque n'est pas une compétence
                                degats += joueurP.getDegats();
                            }
                        }
                    }

                    List<CombatResult> results = new ArrayList<>();
                    Random random = new Random();

                    if (!joueursIds.isEmpty()) {
                        String randomId = joueursIds.get(random.nextInt(joueursIds.size()));

                        for (AttaqueDefRequest joueurP : joueursAction) {
                            if (randomId.equals(joueurP.getJoueurId())) {
                                results.add(new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv() - (attaqueRequest.getMonstre().getAttack() - joueurP.getDef()), attaqueRequest.getMonstre().getHp() - degats));
                                reponses.put(joueurP.getJoueurId(), new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv() - (attaqueRequest.getMonstre().getAttack() - joueurP.getDef()), attaqueRequest.getMonstre().getHp() - degats));
                            } else {results.add(new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv(), attaqueRequest.getMonstre().getHp() - degats));
                                reponses.put(joueurP.getJoueurId(), new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv(), attaqueRequest.getMonstre().getHp() - degats));
                            }
                        }
                        broadcastToAll("attaqueDefValide", results);

                    } else {
                        for (AttaqueDefRequest joueurP : joueursAction) {
                            results.add(new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv(), attaqueRequest.getMonstre().getHp() - degats));
                            reponses.put(joueurP.getJoueurId(), new CombatResult(joueurP.getJoueurId(), joueurP.getDegats(), joueurP.getPdv(), attaqueRequest.getMonstre().getHp() - degats));
                        }
                        broadcastToAll("joueursMort", results);

                    }
                    joueursAction.clear();
                    // Broadcast la réponse à tous les joueurs avec le type "attaqueDef"
                    reponses.clear();
                } else {
                    System.out.println("En attente d'un second joueur...");
                    // Ajouter le type "attaqueDef" à la réponse d'attente
                    sendResponse(session, new CombatResponse("attente",
                            Collections.singletonList(new CombatResult("En attente d'un second joueur...", 0, 0,0))
                    ));


                }
                break;
            case "PartieFini" :
                tourPartie = 0;
                for (WebSocketSession wb : webSocketSessions) {
                    wb.sendMessage(new TextMessage("{\"type\":\"partieFini\"}"));
                }
        }
    }

    // Méthode pour envoyer une réponse spécifique avec un type
    private void sendResponse(WebSocketSession session, CombatResponse response) throws IOException {
        String jsonResponse = gson.toJson(response);
        session.sendMessage(new TextMessage(jsonResponse));
    }

    // Méthode pour envoyer une réponse à tous les clients connectés
    private void broadcastToAll(String type, List<CombatResult> results) throws IOException {
        CombatResponse response = new CombatResponse(type, results);
        String jsonResponse = gson.toJson(response);
        for (WebSocketSession session : webSocketSessions) {
            session.sendMessage(new TextMessage(jsonResponse));
        }
    }

    public void handleMoveMessage(WebSocketSession session,TextMessage message){

    }
    public void handleConnexionMessage(WebSocketSession session,Map<String,Object> msg,ObjectMapper objectMapper) throws IOException {
        Joueur joueur = joueurService.getJoueurById(Integer.toUnsignedLong((Integer) msg.get("joueurId"))).get();
        String partageJoueur = "{ \"type\": \"ajoutJoueur\", ";
            if (joueurs.size() < 4) {
                session.sendMessage(new TextMessage("bienvenue dans la partie"));
                if (joueurs.isEmpty()) {
                    chefDePartie = joueur;
                }
                for (int i = 0; i < webSocketSessions.size(); i++) {
                    webSocketSessions.get(i).sendMessage(new TextMessage(partageJoueur +
                            "\"idJoueur\" : " + (joueur.getId()) + "}"));
                }

                sessionJoueurs.put(session, joueur);
                joueurSession.put(joueur, session);
                joueurs.add(joueur);
                webSocketSessions.add(session);

                partageJoueur = "{ \"type\": \"joueurPartie\", \"joueurs\" : [";
                for (int i = 0; i < joueurs.size(); i++) {
                    if (i > 0) {
                        if (joueurs.get(i) == chefDePartie) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("joueur", joueurs.get(i).getId());
                            data.put("isChief", true);
                            partageJoueur = partageJoueur + "," + objectMapper.writeValueAsString(data);
                        } else {
                            Map<String, Object> data = new HashMap<>();
                            data.put("joueur", joueurs.get(i).getId());
                            data.put("isChief", false);
                            partageJoueur = partageJoueur + "," + objectMapper.writeValueAsString(data);
                        }
                    } else {
                        if (joueurs.get(i) == chefDePartie) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("joueur", joueurs.get(i).getId());
                            data.put("isChief", true);
                            partageJoueur = partageJoueur + objectMapper.writeValueAsString(data);
                        } else {
                            Map<String, Object> data = new HashMap<>();
                            data.put("joueur", joueurs.get(i).getId());
                            data.put("isChief", false);
                            partageJoueur = partageJoueur + objectMapper.writeValueAsString(data);
                        }
                    }
                }
                partageJoueur += "]}";
                session.sendMessage(new TextMessage(partageJoueur));
            } else {
                session.sendMessage(new TextMessage("la session est pleine"));
                session.close();
            }
    }
    public void renvoiJoueurRafraichi(ObjectMapper objectMapper) throws IOException {
        for (int i = 0; i < webSocketSessions.size(); i++) {
            String partageJoueur = "{ \"type\": \"joueurPartie\", \"joueurs\" : [";
            for (int j = 0; j < joueurs.size(); j++) {
                if (j > 0) {
                    if (joueurs.get(i) == chefDePartie) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief", true);
                        partageJoueur = partageJoueur + "," + objectMapper.writeValueAsString(data);
                    } else {
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief", false);
                        partageJoueur = partageJoueur + "," + objectMapper.writeValueAsString(data);
                    }
                } else {
                    if (joueurs.get(i) == chefDePartie) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief", true);
                        partageJoueur = partageJoueur + objectMapper.writeValueAsString(data);
                    } else {
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief", false);
                        partageJoueur = partageJoueur + objectMapper.writeValueAsString(data);
                    }
                }
            }
            partageJoueur += "]}";
            webSocketSessions.get(i).sendMessage(new TextMessage(partageJoueur));
        }
    }
}
