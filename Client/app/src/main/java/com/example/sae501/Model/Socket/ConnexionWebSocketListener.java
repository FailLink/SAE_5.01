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
    /**
     * fonction définissant le comportement après l'ouverture de la connexion avec le websocket
     *
     * @param webSocket le websocket
     * @param response  la réponse envoyé par le websocket suite à la connexion
     * @author Matisse Gallouin
     */
    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
    }

    /**
     * fonction définissant le comportement en cas de message reçu
     *
     * @param webSocket le websocket
     * @param text      le message envoyé
     * @author Matisse Gallouin
     */
    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        Gson gson = new Gson();
        Log.d("WebSocketMessage", text);
        if (text.contains("{")) {
            //conversion du message en dictionnaire
            Type mapType = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> msg = gson.fromJson(text, mapType);

            //définit le comportement selon le type contenu dans le JSON
            if (!msg.containsKey("type")) {
                MainActivity.connexionPartie(gson.fromJson(text, Partie.class));
            } else {
                String msgType = (String) msg.get("type");
                if (msgType.equalsIgnoreCase("partieNonTrouve")) {
                    new PartieNonTrouveFragment()
                            .show(MainActivity.currentActivity.getSupportFragmentManager(), "Partie non trouvé");
                }
            }
        }
        super.onMessage(webSocket, text);
    }

    /**
     * foncion définissant le comportement en cas de fermeture de la connexion
     *
     * @param webSocket le websocket
     * @param code      le code retourné
     * @param reason    la raison
     * @author Matisse Gallouin
     */
    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    /**
     * fonction définissant le comportement en cas d'erreur d'envoie du message
     *
     * @param webSocket le websocket
     * @param t         l'erreur
     * @param response  la réponse
     * @author Matisse Gallouin
     */
    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        Log.e("WebSocketListener", "Erreur de connexion : " + t.getMessage());
    }

}
