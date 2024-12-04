package com.example.sae501.Model.Socket;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.sae501.MainActivity;
import com.example.sae501.MapsActivity;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.PartieActivity;
import com.example.sae501.R;
import com.example.sae501.View.Partie.ExclusionFragment;
import com.example.sae501.View.RejoindrePartie.PartieNonTrouveFragment;
import com.example.sae501.View.RejoindrePartie.PartiePleineFragment;
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

    /**
     * fonction définissant le comportement après l'ouverture de la connexion avec le websocket
     *
     * @param webSocket le websocket
     * @param response  la réponse envoyé par le websocket suite à la connexion
     * @author Matisse Gallouin
     */
    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        String jsonMessage = "{ \"type\": \"connexion\", \"joueurId\": " + MainActivity.joueur.getId() + "}";
        webSocket.send(jsonMessage);
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
            //conversion en json
            Type mapType = new TypeToken<Map<String, Object>>() {
            }.getType();
            Type listType = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            Map<String, Object> msg = gson.fromJson(text, mapType);
            String msgType = (String) msg.get("type");

            //change le comportement du code selon le type contenue dans le json
            if (msgType.equalsIgnoreCase("joueurPartie")) {
                setJoueurPartie(gson, msg, listType);
            }
            if (msgType.equalsIgnoreCase("partieNonTrouve")) {
                new PartieNonTrouveFragment()
                        .show(MainActivity.currentActivity.getSupportFragmentManager(), "Partie non trouvé");
            }
            if (msgType.equalsIgnoreCase("ajoutJoueur")) {
                ajoutJoueurPartie(msg);
            }
            if (msgType.equalsIgnoreCase("exclusion")) {
                new ExclusionFragment()
                        .show(MainActivity.currentActivity.getSupportFragmentManager(), "Exclue de la partie");
            }
            if (msgType.equalsIgnoreCase("joueurExclu")) {
                //obligé de l'exécuter sur le thread principal sinon le code ne peut pas marcher
                MainActivity.currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.currentActivity, "Un joueur a été exclu", Toast.LENGTH_SHORT).show();
                        MainActivity.joueursPartie.clear();

                        ViewGroup listeJoueurView = MainActivity.currentActivity.findViewById(R.id.layoutPartie);
                        listeJoueurView.removeAllViews();

                        LayoutInflater.from(MainActivity.currentActivity).inflate(R.layout.activity_partie, listeJoueurView, true);
                        TextView textView = MainActivity.currentActivity.findViewById(R.id.textIdPartie);
                        textView.setText(textView.getText() + MainActivity.partie.getId().toString());
                    }
                });
            }
            if (msgType.equalsIgnoreCase("joueurDeconnecte")) {
                //obligé de l'exécuter sur le thread principal sinon le code ne peut pas marcher
                MainActivity.currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.currentActivity, "Un joueur s'est déconnecté", Toast.LENGTH_SHORT).show();
                        MainActivity.joueursPartie.clear();

                        ViewGroup listeJoueurView = MainActivity.currentActivity.findViewById(R.id.layoutPartie);
                        listeJoueurView.removeAllViews();

                        LayoutInflater.from(MainActivity.currentActivity).inflate(R.layout.activity_partie, listeJoueurView, true);
                        TextView textView = MainActivity.currentActivity.findViewById(R.id.textIdPartie);
                        textView.setText(textView.getText() + MainActivity.partie.getId().toString());
                    }
                });
            }
            if (msgType.equalsIgnoreCase("lancementPartie")) {
                Intent intent = new Intent(MainActivity.currentActivity, MapsActivity.class);
                MainActivity.currentActivity.startActivity(intent);
            }
        } else if (text.equalsIgnoreCase("bienvenue dans la partie")) {
            Intent intent = new Intent(MainActivity.currentActivity, PartieActivity.class);
            MainActivity.currentActivity.startActivity(intent);
        } else if (text.equalsIgnoreCase("la session est pleine")) {
            new PartiePleineFragment().show(MainActivity.currentActivity.getSupportFragmentManager(), "Partie pleine");
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

    /**
     * fonction permettant à partir du message contenant les joueurs de la partie de les ajouter au variable global
     * et à la vue de PartieActivity
     *
     * @param gson     gson pour la conversion des jsons en objets
     * @param msg      message reçu
     * @param listType type permettant la conversion de mon json en objet
     * @author Matisse Gallouin
     */
    public void setJoueurPartie(Gson gson, Map<String, Object> msg, Type listType) {
        List<Map<String, Object>> list = gson.fromJson(msg.get("joueurs").toString(), listType);
        ArrayList<Long> idJoueur = new ArrayList<>();
        Long idChefDePartie = null;
        for (Map<String, Object> map : list) {
            if ((Boolean) map.get("isChief")) {
                //obligé d'exécuter ces fonctions pour obtenir un long
                idChefDePartie = Math.round((Double) map.get("joueur"));
            } else {
                //obligé d'exécuter ces fonctions pour obtenir un long
                idJoueur.add(Math.round((Double) map.get("joueur")));
            }
        }
        //lance ma fonction dans mon main
        MainActivity.infoJoueur(idJoueur, idChefDePartie);
    }

    /**
     * fonction permettant d'ajouter un joueur à la partie et donc au variable global ainsi que à la vue de Partie Activity
     *
     * @param msg message reçu
     * @author Matisse Gallouin
     */
    public void ajoutJoueurPartie(Map<String, Object> msg) {
        MainActivity.ajoutJoueur(Math.round((Double) msg.get("idJoueur")));
    }
}