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
    private int idpartie;

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
    public static GameWebSocketHandler createWithIdPartie(ApplicationContext applicationContext) {
        GameWebSocketHandler handler = applicationContext.getBean(GameWebSocketHandler.class);
        return handler;
    }

    public GameWebSocketHandler(PartieService partieService, JoueurService joueurService) {
        this.partieService = partieService;
        this.joueurService = joueurService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> msg=objectMapper.readValue(message.getPayload(),Map.class);
        switch ((String) msg.get("type")){
            case "connexion":
                handleConnexionMessage(session,msg,objectMapper);
                break;
            case  "action":
                handleMoveMessage(session, message);
                break;
            case "deconnexion" :
                session.close();
                webSocketSessions.remove(session);
                joueurSession.remove(sessionJoueurs.get(session));
                joueurs.remove(sessionJoueurs.get(session));
                if(chefDePartie==sessionJoueurs.get(session)){
                    chefDePartie=joueurs.get(0);
                }
                sessionJoueurs.remove(session);
                for(WebSocketSession webSocketSession:webSocketSessions){
                    webSocketSession.sendMessage(new TextMessage("{\"type\":\"joueurDeconnecte\"}"));
                }
                renvoiJoueurRafraichi(objectMapper);
            case "exclusion" :
                Joueur joueur = joueurService.getJoueurById(Integer.toUnsignedLong((Integer) msg.get("joueur"))).get();
                session.sendMessage(new TextMessage(String.valueOf(joueurSession.containsKey(joueur))));
                joueurSession.get(joueur).sendMessage(new TextMessage("{\"type\":\"exclusion\"}"));
                joueurSession.get(joueur).close();
                webSocketSessions.remove(joueurSession.get(joueur));
                joueurs.remove(joueur);
                sessionJoueurs.remove(joueurSession.get(joueur));
                joueurSession.remove(joueur);
                for(WebSocketSession webSocketSession:webSocketSessions){
                    webSocketSession.sendMessage(new TextMessage("{\"type\":\"joueurExclu\"}"));
                }
                renvoiJoueurRafraichi(objectMapper);
            case "lancementPartie":
                for (WebSocketSession webSocketSession:webSocketSessions){
                    if(webSocketSession!=session){
                        webSocketSession.sendMessage(new TextMessage("{\"type\":\"lancementPartie\"}"));
                    }
                }
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
