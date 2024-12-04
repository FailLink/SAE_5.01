package org.example.sae501serveur.Model.Socket.Handler.Game;

import org.example.sae501serveur.Model.Entity.Partie;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.PartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameWebSocketHandlerFactory {
    @Autowired
    private PartieService partieService;
    @Autowired
    private JoueurService joueurService;


    public GameWebSocketHandler createHandler(Partie partie) {
        return new GameWebSocketHandler(partieService, joueurService,partie);
    }
}
