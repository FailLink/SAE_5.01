package org.example.sae501serveur.Model.Socket.Connexion;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import org.example.sae501serveur.Model.Entity.Competence;
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
    private final Map<WebSocketSession, Joueur> joueurs=new ConcurrentHashMap<>();

    private final Map<Joueur,WebSocketSession> gameSession=new ConcurrentHashMap<>();

    private final Queue<WebSocketSession> fileAttente=new ConcurrentLinkedQueue<>();

    private final List<WebSocketSession> partieDispo=new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Bienvenue dans la connexion"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> msg=objectMapper.readValue(message.getPayload(),Map.class);
        Joueur joueur=joueurService.getJoueurById((Long) msg.get("joueurId"));
        switch ((String) msg.get("type")){
            case "creationPartie":
                Partie partie=partieService.createPartie();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partie)));

                break;
            case  "connexion":
                addFileAttente(joueur,session);

                break;
            case "connexionById":
                Long idpartie= (Long) msg.get("idpartie");
        }
    }

    public void addFileAttente(Joueur joueur, WebSocketSession webSocketSession){
        fileAttente.add(webSocketSession);
        gameSession.put(joueur,webSocketSession);
        joueurs.put(webSocketSession,joueur);
        if(fileAttente.size()==1){
        }

    }

}
