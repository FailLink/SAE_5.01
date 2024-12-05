package org.example.sae501serveur.Model.Socket.Handler.Connexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.MonstreLieux;
import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.JsonViewEntity.Views;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.PartieService;
import org.example.sae501serveur.Model.Socket.Handler.Game.RedirectorHandler;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ConnexionWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private PartieService partieService;
    @Autowired
    private JoueurService joueurService;
    @Autowired
    private RedirectorHandler redirectorHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("connexion"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> msg=objectMapper.readValue(message.getPayload(),Map.class);
        switch ((String) msg.get("type")){
            case "creationPartie":
                Partie partie=partieService.createPartie((Double) msg.get("positionLongitude"),(Double) msg.get("positionLatitude") );
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partie)));
                session.close();
                break;
            case "connexionPartie":
                if(!redirectorHandler.getHandlerSessionId().containsKey(msg.get("idPartie"))){
                    session.sendMessage(new TextMessage("{ \"type\": \"partieNonTrouve\"}"));
                }
                else{
                    Partie partie1=partieService.getPartieById(Long.parseLong((String) msg.get("idPartie")));
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partie1)));
                    session.close();
                }
        }
    }

}
