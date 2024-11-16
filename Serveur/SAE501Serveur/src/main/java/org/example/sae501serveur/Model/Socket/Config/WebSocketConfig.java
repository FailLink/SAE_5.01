package org.example.sae501serveur.Model.Socket.Config;

import org.example.sae501serveur.Model.Socket.Handler.Connexion.ConnexionWebSocketHandler;
import org.example.sae501serveur.Model.Socket.Handler.Game.GameWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ConnexionWebSocketHandler connexionWebSocketHandler;

    public WebSocketConfig(ConnexionWebSocketHandler connexionWebSocketHandler) {
        this.connexionWebSocketHandler = connexionWebSocketHandler;
    }
    @Bean
    public GameWebSocketHandler gameWebSocketHandler() {
        return new GameWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(connexionWebSocketHandler,"/connexionPartie").setAllowedOrigins("*");
        registry.addHandler(gameWebSocketHandler(),"/game/{idPartie}").setAllowedOrigins("*");
    }
}
