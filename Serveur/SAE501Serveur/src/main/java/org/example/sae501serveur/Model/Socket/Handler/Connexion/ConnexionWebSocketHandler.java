package org.example.sae501serveur.Model.Socket.Handler.Connexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.PartieService;
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
                Partie partie=partieService.createPartie();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partie)));
                session.close();
                break;
            case  "connexion":
                Partie partieVoulu=partieService.getPartieById(Integer.toUnsignedLong((Integer) msg.get("partieId")));
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partieVoulu)));
                session.close();
                break;
        }
    }

}
