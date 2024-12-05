package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sae501.Controller.Connexion.ConnexionRepository;
import com.example.sae501.Controller.Joueur.JoueurRepository;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.Model.Socket.GameWebSocketListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {

    public static Partie partie;

    public static Joueur joueur;
    public static ArrayList<Joueur> joueursPartie = new ArrayList<>();
    public static WebSocket webSocketPartie;
    //sessionID de mon joueur nécessaire pour garder l'authentification
    public static String sessionID;
    public static Joueur chefDePartie;
    public static FragmentActivity currentActivity;
    //ip du serveur
    public static String globalIP = "192.168.1.27:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test du session présent dans les fichiers
        sessionID = recupSessionID();
        currentActivity = this;

        ConnexionRepository connexionRepository = new ConnexionRepository(new ScheduleConnexion());
        connexionRepository.testSessionId();
    }

    /**
     * fonction permettant de récupérer dans les fichiers le jsessionid enregistrés
     *
     * @return le jsessionid enregistré
     * @author Matisse Gallouin
     */
    public String recupSessionID() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
        return sharedPreferences.getString("session_cookie", null);
    }

    /**
     * fonction permettant de se connecter au websocket lié à la partie
     *
     * @param partieDonnee partie renvoyé par le serveur lors de la création d'une partie
     * @author Matisse Gallouin
     */
    public static void connexionPartie(Partie partieDonnee) {
        partie = partieDonnee;
        OkHttpClient okHttpClient = OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://" + MainActivity.globalIP + "/game/" + partie.getId()).build();
        MainActivity.webSocketPartie = okHttpClient.newWebSocket(request, new GameWebSocketListener());
    }

    /**
     * fonction servant à l'ajout des informations des joueurs donnés
     *
     * @param listJoueurId liste des id des joueurs hors chef de la partie
     * @param chefId       id du chef de la partie
     * @author Matisse Gallouin
     */
    public static void infoJoueur(List<Long> listJoueurId, Long chefId) {
        JoueurRepository joueurRepository = new JoueurRepository();
        if (chefId != null) {
            joueurRepository.getChefPartieById(chefId);
        }
        for (int i = 0; i < listJoueurId.size(); i++) {
            joueurRepository.getJoueurPartieById(listJoueurId.get(i), i + 2);
        }
    }

    /**
     * fonction servant à l'ajout des informations d'un' joueurs donné
     *
     * @param joueurId id du joueur a ajouté
     * @author Matisse Gallouin
     */
    public static void ajoutJoueur(Long joueurId) {
        JoueurRepository joueurRepository = new JoueurRepository();
        System.out.println("entrée dans fonction 1");
        joueurRepository.getJoueurPartieById(joueurId, joueursPartie.size() + 1);
    }

}