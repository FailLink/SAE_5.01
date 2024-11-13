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
    public static Joueur joueur;
    public static String sessionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionID=recupSessionID();


        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public String recupSessionID(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("SlayMonstersData",MODE_PRIVATE);
        return sharedPreferences.getString("session_cookie",null);
    }
    public Joueur recupJoueur(){
        SharedPreferences sharedPreferences=this.getSharedPreferences("SlayMonstersData",MODE_PRIVATE);
        return sharedPreferences.getString("joueur_id",null);
    }

}