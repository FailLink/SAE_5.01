package org.example.sae501serveur.Model.Socket.Connexion;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ConnexionWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private PartieService partieService;
    @Autowired
    private JoueurService joueurService;
    private final Map<WebSocketSession, Joueur> joueurs=new ConcurrentHashMap<>();

    private final List<Partie> gameSession=new ArrayList<>();

    private final List<Joueur> fileAttente=new ArrayList<>();

    private final Map<Long, Competence> actions=new ConcurrentHashMap<>();

    private final int maxPlayer=4;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if(joueurs.size()==4){
            session.sendMessage(new TextMessage("la session est pleine"));
            session.close();
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String,Object> msg=objectMapper.readValue(message.getPayload(),Map.class);
        switch ((String) msg.get("type")){
            case "creationPartie":
                Joueur joueur=joueurService.getJoueurById((Long) msg.get("joueurId"));
                Partie partie=partieService.createPartie();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(partie)));
                gameSession.add(partie);
                break;
            case  "connexion":

                break;
        }
    }

    public void addFileAttente(Joueur joueur){
        fileAttente.add(joueur);
        //relancement lancer la fonction pour vérifier si des servuers sont dispos ou lancement

    }

    public void connexionJoeurFileAttente(){
        //vérifier qu'un serveur
        //y tenter de connecter le joueur
        // si réussi supprimer et vérifier si d'autres joueurs attendent
        //si oui lancer faire la schedule ou relancer
        //si non stopper
        //si connexion pas réussi relancer la méthode sans suppression de la personne
    }
}
