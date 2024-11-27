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
    public static ArrayList<Joueur> joueursPartie=new ArrayList<>();
    public static WebSocket webSocketPartie;
    public static String sessionID;
    public static Joueur chefDePartie;
    public static FragmentActivity currentActivity;
    public static String globalIP="192.168.1.27:8080";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionID=recupSessionID();
        currentActivity=this;

        ConnexionRepository connexionRepository=new ConnexionRepository(new ScheduleConnexion());
        connexionRepository.testSessionId();
    }
    public String recupSessionID(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("SlayMonstersData",MODE_PRIVATE);
        return sharedPreferences.getString("session_cookie",null);
    }
    public Long recupIdJoueur(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("SlayMonstersData",MODE_PRIVATE);
        if(sharedPreferences.getString("joueur_id",null)!=null) {
            return Long.getLong(sharedPreferences.getString("joueur_id", null));
        }
        else {
            return null;
        }
    }
    public static void connexionPartie(Partie partieDonnee){
        partie=partieDonnee;
        OkHttpClient okHttpClient= OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://"+ MainActivity.globalIP +"/game/"+partie.getId()).build();
        MainActivity.webSocketPartie = okHttpClient.newWebSocket(request, new GameWebSocketListener());
    }
    public static void infoJoueur(List<Long> listJoueurId,Long chefId){
        JoueurRepository joueurRepository=new JoueurRepository();
        for(int i=0;i<listJoueurId.size();i++){
            joueurRepository.getJoueurPartieById(listJoueurId.get(i),i+2);
        }
        if(chefId!=null){
            joueurRepository.getChefPartieById(chefId);
        }
        System.out.println(MainActivity.chefDePartie);
        System.out.println(MainActivity.joueursPartie);
    }

}