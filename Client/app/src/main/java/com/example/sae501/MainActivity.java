package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sae501.Controller.Connexion.ConnexionRepository;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.Model.Socket.GameWebSocketListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {
    public static Partie partie;
    public static Joueur joueur;
    ArrayList<Joueur> joueursPartie;
    public static String sessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionID=recupSessionID();

        ConnexionRepository connexionRepository=new ConnexionRepository(this,new ScheduleConnexion(this));
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
    public static void connexionPartie(Partie partieDonnee, Activity activity){
        partie=partieDonnee;
        System.out.println("connexionALaPartie");
        OkHttpClient okHttpClient= OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/game/"+partie.getId()).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new GameWebSocketListener());
        Intent intent=new Intent(activity, Partie_Activity.class);
        activity.startActivity(intent);
    }

}