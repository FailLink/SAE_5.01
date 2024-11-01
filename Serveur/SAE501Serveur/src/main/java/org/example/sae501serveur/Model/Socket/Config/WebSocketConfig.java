package org.example.sae501serveur.Model.Socket.Config;

import org.example.sae501serveur.Model.Socket.Handler.Connexion.ConnexionWebSocketHandler;
import org.example.sae501serveur.Model.Socket.Handler.Game.GameWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ConnexionWebSocketHandler connexionWebSocketHandler;
    private final GameWebSocketHandler gameWebSocketHandler;

    public WebSocketConfig(ConnexionWebSocketHandler connexionWebSocketHandler,GameWebSocketHandler gameWebSocketHandler) {
        this.connexionWebSocketHandler = connexionWebSocketHandler;
        this.gameWebSocketHandler=gameWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(connexionWebSocketHandler,"/connexionPartie").setAllowedOrigins("*");
        registry.addHandler(gameWebSocketHandler,"/game/{idPartie}").setAllowedOrigins("*");
    }
}
