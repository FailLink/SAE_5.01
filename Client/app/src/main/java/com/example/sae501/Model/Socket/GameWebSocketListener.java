package com.example.sae501.Model.Socket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Joueur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class GameWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            String jsonMessage = "{ \"type\": \"connexion\", \"joueurId\": "+MainActivity.joueur.getId()+"}";
            webSocket.send(jsonMessage);
        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            Gson gson=new Gson();
            Log.d("WebSocketMessage",text);
            if(text.contains("{")) {
                Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
                Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
                Map<String, Object> msg = gson.fromJson(text, mapType);
                String msgType=(String) msg.get("type");

                if(msgType.equalsIgnoreCase("joueurPartie")){
                    List<Map<String, Object>> list = gson.fromJson(msg.get("joueurs").toString(), listType);
                    ArrayList<Long> idJoueur=new ArrayList<>();
                    Long idChefDePartie=null;
                    for(Map<String,Object> map : list ){
                        if((Boolean) map.get("isChief")){
                            idChefDePartie=(Long) map.get("joueur");
                        }
                        else{
                            idJoueur.add((Long) map.get("joueur"));
                        }
                    }
                    MainActivity.infoJoueur(idJoueur,idChefDePartie);
                }
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

