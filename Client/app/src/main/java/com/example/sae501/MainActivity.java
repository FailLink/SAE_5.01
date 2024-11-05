package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainActivity extends AppCompatActivity {
    public static Partie partie;
    public static Joueur joueur=new Joueur();
    public static String sessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionID=recupSessionID();

        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /*
    public void onClickJouer(View view){
        System.out.println("bouton cliqué");
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/connexionPartie").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
    }
    public static void connexionPartie(Partie partieDonnee){
        joueur.setId(1L);
        partie=partieDonnee;
        System.out.println("connexionALaPartie");
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/game/"+partie.getId()).build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
    }
    */
    public String recupSessionID(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("SlayMonstersData",MODE_PRIVATE);
        return sharedPreferences.getString("session_cookie",null);
    }

}