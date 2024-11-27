package com.example.sae501.Model.Socket;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.PartieActivity;
import com.example.sae501.R;
import com.example.sae501.View.RejoindrePartie.PartieNonTrouveFragment;
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

    public GameWebSocketListener() {
    }

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
                    setJoueurPartie(gson,msg,listType);
                }
                if(msgType.equalsIgnoreCase("partieNonTrouve")){
                    new PartieNonTrouveFragment()
                            .show(MainActivity.currentActivity.getSupportFragmentManager(),"Partie non trouvé");
                }
            }
            else if(text.equalsIgnoreCase("bienvenue dans la partie")){
                Intent intent = new Intent(MainActivity.currentActivity, PartieActivity.class);
                MainActivity.currentActivity.startActivity(intent);
                System.out.println(MainActivity.currentActivity+"listener");
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

        public void setJoueurPartie(Gson gson,Map<String,Object> msg,Type listType){
            List<Map<String, Object>> list = gson.fromJson(msg.get("joueurs").toString(), listType);
            ArrayList<Long> idJoueur=new ArrayList<>();
            Long idChefDePartie=null;
            for(Map<String,Object> map : list ){
                if((Boolean) map.get("isChief")){
                    System.out.println("ischief=true");
                    idChefDePartie=Math.round((Double) map.get("joueur"));
                }
                else{
                    idJoueur.add(Math.round((Double) map.get("joueur")));
                }
            }
            MainActivity.infoJoueur(idJoueur,idChefDePartie);
        }
}

