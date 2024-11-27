package com.example.sae501;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Serveur.SessionIntercepteur;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.View.RejoindrePartie.RejoindrePartieFragment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.logging.HttpLoggingInterceptor;

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
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/connexionPartie").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
        String jsonMessage = "{ \"type\": \"creationPartie\" }";
        webSocket.send(jsonMessage);
        Intent intent = new Intent(this, Partie_Activity.class);
        startActivity(intent);
    }
    public void onClickRejoindrePartie(View view){
        new RejoindrePartieFragment().show(getSupportFragmentManager(),"rejoindrePartie");
    }
}
