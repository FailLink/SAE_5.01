package com.example.sae501;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.View.RejoindrePartie.RejoindrePartieFragment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MainActivity.currentActivity=this;
    }
    public void onClickJouer(View view) {
        System.out.println("bouton cliqué");
        OkHttpClient okHttpClient = OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://"+ MainActivity.globalIP +"/connexionPartie").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
        String jsonMessage = "{ \"type\": \"creationPartie\" }";
        webSocket.send(jsonMessage);
    }
    public void onClickRejoindrePartie(View view){
        new RejoindrePartieFragment().show(getSupportFragmentManager(),"rejoindrePartie");
    }
}
