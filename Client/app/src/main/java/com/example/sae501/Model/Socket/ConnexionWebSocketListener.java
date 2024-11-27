package com.example.sae501.Model.Socket;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.View.RejoindrePartie.PartieNonTrouveFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ConnexionWebSocketListener extends WebSocketListener {
    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Gson gson=new Gson();
        Log.d("WebSocketMessage",text);
        if(text.contains("{")) {
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> msg = gson.fromJson(text, mapType);

            if(!msg.containsKey("type")) {
                MainActivity.connexionPartie(gson.fromJson(text, Partie.class));
            }
            else {
                String msgType=(String) msg.get("type");
                if (msgType.equalsIgnoreCase("partieNonTrouve")) {
                    new PartieNonTrouveFragment()
                            .show(MainActivity.currentActivity.getSupportFragmentManager(), "Partie non trouvé");
                }
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
