package org.example.sae501serveur.Model.Socket.Config;

import org.example.sae501serveur.Model.Socket.Handler.Connexion.ConnexionWebSocketHandler;
import org.example.sae501serveur.Model.Socket.Handler.Game.GameWebSocketHandler;
import org.example.sae501serveur.Model.Socket.Handler.Game.GameWebSocketHandlerFactory;
import org.example.sae501serveur.Model.Socket.Handler.Game.RedirectorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ConnexionWebSocketHandler connexionWebSocketHandler;
    private final RedirectorHandler redirectorHandler;

    @Autowired
    public WebSocketConfig(ConnexionWebSocketHandler connexionWebSocketHandler, RedirectorHandler redirectorHandler) {
        this.connexionWebSocketHandler = connexionWebSocketHandler;
        this.redirectorHandler = redirectorHandler;
    }

    /**
     * fonction associant mes endpoints à des handler
     *
     * @param registry
     * @author Matisse Gallouin
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(connexionWebSocketHandler, "/connexionPartie").setAllowedOrigins("*");
        registry.addHandler(redirectorHandler, "/game/{idPartie}").setAllowedOrigins("*");
    }
}
