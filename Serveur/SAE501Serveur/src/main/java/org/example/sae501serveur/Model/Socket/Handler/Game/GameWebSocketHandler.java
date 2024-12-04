package org.example.sae501serveur.Model.Socket.Handler.Game;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import org.apache.logging.log4j.message.Message;
import org.example.sae501serveur.Model.Entity.Competence;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class GameWebSocketHandler extends TextWebSocketHandler {

    private PartieService partieService;
    private Partie partie;

    private JoueurService joueurService;
    private final Map<WebSocketSession, Joueur> sessionJoueurs = new ConcurrentHashMap<>();
    private final Map<Joueur, WebSocketSession> joueurSession = new ConcurrentHashMap<>();

    private final ArrayList<WebSocketSession> webSocketSessions = new ArrayList<>();

    private final ArrayList<Joueur> joueurs = new ArrayList<>();

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

    private final Map<Long, Competence> actions = new ConcurrentHashMap<>();

    private final int maxPlayer = 4;


    public GameWebSocketHandler(PartieService partieService, JoueurService joueurService, Partie partie) {
        this.partieService = partieService;
        this.joueurService = joueurService;
        this.partie = partie;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //transformation du json en dictionnaire
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> msg = objectMapper.readValue(message.getPayload(), Map.class);

        //change le code selon le type de message
        switch ((String) msg.get("type")) {
            case "connexion":
                handleConnexionMessage(session, msg, objectMapper);
                break;
            case "action":
                handleMoveMessage(session, message);
                break;
            case "deconnexion":
                //retire le joueur de toutes les variables du websocket et prévient les autres joueurs
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
            case "exclusion":
                //retire le joueur de toutes les variables du websocket et prévient les autres joueurs
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
            case "lancementPartie":
                //prévient tous les joueurs du lancement de la partie
                for (Joueur joueurBDD : joueurs) {
                    partie.addJoueur(joueurBDD);
                }
                partieService.savePartie(partie);
                for (WebSocketSession webSocketSession : webSocketSessions) {
                    if (webSocketSession != session) {
                        webSocketSession.sendMessage(new TextMessage("{\"type\":\"lancementPartie\"}"));
                    }
                }
        }
    }

    public void handleMoveMessage(WebSocketSession session, TextMessage message) {

    }

    /**
     * fonction permettant de transmettre les infos nécessaires aux joueurs déjà connectés et au joueur se connectant
     *
     * @param session      session envoyant le message
     * @param msg          message envoyé
     * @param objectMapper objet pour transformer mon message en json
     * @throws IOException
     * @author Matisse Gallouin
     */
    public void handleConnexionMessage(WebSocketSession session, Map<String, Object> msg, ObjectMapper objectMapper) throws IOException {
        //recherche du joueur à l'aide de l'id envoyé
        Joueur joueur = joueurService.getJoueurById(Integer.toUnsignedLong((Integer) msg.get("joueurId"))).get();

        //création du string pour prévenir de l'arriver d'un joueur
        String partageJoueur = "{ \"type\": \"ajoutJoueur\", ";

        //vérification du nombre de joueurs connectés afin de savoir si le joueur peut se connecter
        if (joueurs.size() < 4) {
            session.sendMessage(new TextMessage("bienvenue dans la partie"));

            //set du chef de partie
            if (joueurs.isEmpty()) {
                chefDePartie = joueur;
            }

            //envoie de l'information qu'un joueur vient de rejoindre
            for (int i = 0; i < webSocketSessions.size(); i++) {
                webSocketSessions.get(i).sendMessage(new TextMessage(partageJoueur +
                        "\"idJoueur\" : " + (joueur.getId()) + "}"));
            }

            //ajout du joueur au différentes variables du websocket
            sessionJoueurs.put(session, joueur);
            joueurSession.put(joueur, session);
            joueurs.add(joueur);
            webSocketSessions.add(session);

            //envoie de la liste des joueurs au joueur rejoignant
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

    /**
     * fonction permettant d'envoyer la liste des joueurs connectés à tous les joueurs
     *
     * @param objectMapper
     * @throws IOException
     * @author Matisse Gallouin
     */
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
