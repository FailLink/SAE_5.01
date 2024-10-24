package org.example.sae501serveur.Model.Socket.Handler.Game;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import org.apache.logging.log4j.message.Message;
import org.example.sae501serveur.Model.Entity.Competence;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private PartieService partieService;
    @Autowired
    private JoueurService joueurService;
    private final Map<WebSocketSession, Joueur>joueurs=new ConcurrentHashMap<>();
    private final Map<Joueur,WebSocketSession> joueurSession=new ConcurrentHashMap<>();

    private final Map<Long,Competence> actions=new ConcurrentHashMap<>();

    private final int maxPlayer=4;

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
                Joueur joueur=joueurService.getJoueurById((Long) msg.get("joueurId"));
                if (joueurs.size()<4){
                    joueurs.put(session,joueur);
                    joueurSession.put(joueur,session);

                }else{
                    session.sendMessage(new TextMessage("la session est pleine"));
                    session.close();
                }
                break;
            case  "action":
                handleMoveMessage(session, message);
                break;
        }
    }
    public void handleMoveMessage(WebSocketSession session,TextMessage message){

    }
}
