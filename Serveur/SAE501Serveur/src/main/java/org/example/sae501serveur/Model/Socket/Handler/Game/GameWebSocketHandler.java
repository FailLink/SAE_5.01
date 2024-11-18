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
        if(joueurs.size()>=4){
            session.sendMessage(new TextMessage("la session est pleine"));
            session.close();
        }
        session.sendMessage(new TextMessage("bienvenue dans la partie"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> msg=objectMapper.readValue(message.getPayload(),Map.class);
        switch ((String) msg.get("type")){
            case "connexion":
                session.sendMessage(new TextMessage("connexion en cours..."));
                handleConnexionMessage(session,msg,objectMapper);
                break;
            case  "action":
                handleMoveMessage(session, message);
                break;
        }
    }
    public void handleMoveMessage(WebSocketSession session,TextMessage message){

    }
    public void handleConnexionMessage(WebSocketSession session,Map<String,Object> msg,ObjectMapper objectMapper) throws IOException {
        Joueur joueur=joueurService.getJoueurById(Integer.toUnsignedLong((Integer) msg.get("joueurId"))).get();
        String partageJoueur="{ \"type\": \"joueurPartie\", \"joueurs\" : [";
        if (joueurs.size()<4){
            if(joueurs.isEmpty()){
                chefDePartie=joueur;
            }
            for(int i=0;i<webSocketSessions.size();i++){
                webSocketSessions.get(i).sendMessage(new TextMessage(partageJoueur+
                        "\"idjoueur\" : "+(joueur.getId())+"]}"));
            }
            sessionJoueurs.put(session,joueur);
            joueurSession.put(joueur,session);
            joueurs.add(joueur);
            webSocketSessions.add(session);
            for(int i=0;i<joueurs.size();i++){
                if(i>0){
                    if(joueurs.get(i)==chefDePartie){
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief",true);
                        partageJoueur=partageJoueur+","+objectMapper.writeValueAsString(data);
                    }else{
                        Map<String, Object> data = new HashMap<>();
                        data.put("joueur", joueurs.get(i).getId());
                        data.put("isChief", false);
                        partageJoueur = partageJoueur+"," + objectMapper.writeValueAsString(data);
                    }
                }else {
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
            partageJoueur+="]}";
            session.sendMessage(new TextMessage(partageJoueur));
        }else{
            session.sendMessage(new TextMessage("la session est pleine"));
            session.close();
        }
    }
}
