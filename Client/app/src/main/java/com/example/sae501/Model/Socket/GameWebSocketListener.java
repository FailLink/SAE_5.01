package com.example.sae501.Model.Socket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Partie;
import com.google.gson.Gson;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class GameWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            String jsonMessage = "{ \"type\": \"connexion\", \"joueurId\": \""+MainActivity.joueur.getId()+"\" }";
            webSocket.send(jsonMessage);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            Gson gson=new Gson();
            Log.d("WebSocketMessage",text);
            if(text.contains("{")) {
                //MainActivity.connexionPartie(gson.fromJson(text, Partie.class));
            }
            super.onMessage(webSocket, text);
        }

        @Override
        public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
            super.onClosing(webSocket, code, reason);
        }
        @Override
        public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
            Log.e("WebSocketListener", "Erreur de connexion : " + t.getMessage());
        }

}

