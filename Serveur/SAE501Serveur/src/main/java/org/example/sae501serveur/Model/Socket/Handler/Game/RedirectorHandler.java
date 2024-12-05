package org.example.sae501serveur.Model.Socket.Handler.Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sae501serveur.Model.Service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedirectorHandler extends TextWebSocketHandler {
    //map permettant de stocker les différents gamewebsockethandler
    // afin de correctement séparer les variables selon les parties
    private final Map<String, GameWebSocketHandler> handlerSessionId = new HashMap<>();

    @Autowired
    private GameWebSocketHandlerFactory gameWebSocketHandlerFactory;
    @Autowired
    private PartieService partieService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //récupération de l'id de la partie contenue dans le lien du websocket
        String path = session.getUri().getPath().split("/")[2];

        //transformation du json en dictionnaire
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> msg = objectMapper.readValue(message.getPayload(), Map.class);

        //recherche de la partie pour voir si elle existe dans la bdd
        if (partieService.getPartieById(Integer.toUnsignedLong(Integer.parseInt(path))) == null) {
            session.sendMessage(new TextMessage("{ \"type\": \"partieNonTrouve\"}"));
            session.close();
        } else {
            //recherche du handler correspondant
            if (handlerSessionId.containsKey(path)) {
                //gestion de la déconnexion dans le cas le joueur est seule afin de supprimer la partie
                if (((String) msg.get("type")).equalsIgnoreCase("deconnexion")) {
                    if (handlerSessionId.get(path).getJoueurs().size() == 1) {
                        handlerSessionId.remove(path);
                        session.close();
                    } else {
                        handlerSessionId.get(path).handleTextMessage(session, message);
                    }
                } else {
                    handlerSessionId.get(path).handleTextMessage(session, message);
                }
            } else {
                handlerSessionId.put(path, gameWebSocketHandlerFactory.
                        createHandler(partieService.getPartieById(Integer.toUnsignedLong(Integer.parseInt(path)))));
                handlerSessionId.get(path).handleTextMessage(session, message);
            }
        }
    }

    public Map<String, GameWebSocketHandler> getHandlerSessionId() {
        return handlerSessionId;
    }
}
