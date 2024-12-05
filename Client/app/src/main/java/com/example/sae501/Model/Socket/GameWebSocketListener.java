package com.example.sae501.Model.Socket;
// import static com.example.sae501.Combat.PVJoueur;

import static com.example.sae501.Combat.JoueursMort;
import static com.example.sae501.Combat.MonstreVaincuVerif;
import static com.example.sae501.Combat.PVJoueur;
import static com.example.sae501.Combat.PVJoueurText;
import static com.example.sae501.Combat.PVMonstre;
import static com.example.sae501.Combat.activiteCombat;
import static com.example.sae501.Combat.joueurViewsMap;
import static com.example.sae501.Combat.monstreCombat;
import static com.example.sae501.Combat.monstreMort;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sae501.Combat;
import com.example.sae501.MainActivity;
import com.example.sae501.MapsActivity;
import com.example.sae501.Model.Game.CombatManager;
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
import java.util.Objects;

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

        @SuppressLint("SetTextI18n")
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            Gson gson = new Gson();
            Log.d("WebSocketMessage", text);
            if (text.contains("{")) {
                System.out.println(text.equalsIgnoreCase("bienvenue dans la partie"));
                Type mapType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Type listType = new TypeToken<List<Map<String, Object>>>() {
                }.getType();
                Map<String, Object> msg = gson.fromJson(text, mapType);
                String msgType = (String) msg.get("type");

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
                if (msgType.equalsIgnoreCase("attaqueDefValide")) {
                    MainActivity.currentActivity.runOnUiThread(() -> {
                    List<Map<String, Object>> results = (List<Map<String, Object>>) msg.get("results");
                    for (Map<String, Object> joueurData : results) {
                        Long joueurId = Long.parseLong((String) joueurData.get("joueurId"));
                        int pvMonstre = ((Double) joueurData.get("monstrePdv")).intValue();


                        // Vérifier si l'ID du joueur correspond à 1
                        if (Objects.equals(MainActivity.joueur.getId(), joueurId)) {
                            // Récupérer les autres informations (dégâts, PV, etc.)
                            int degats = ((Double) joueurData.get("degats")).intValue();  // Les valeurs peuvent être des Double
                            int pv = ((Double) joueurData.get("pdv")).intValue();

                            // Afficher les informations du joueur 1
                            System.out.println("Joueur ID: " + joueurId);
                            System.out.println("Dégâts: " + degats);
                            System.out.println("PV: " + pv);
                            System.out.println("pvMonstre: " + pvMonstre);

                            MainActivity.joueur.setHpActuel(Math.max(pv, 0));

                            if (MainActivity.joueur.getHpActuel() <= 0){
                                PVJoueurText.setText("Vous êtes mort");
                            } else {
                                PVJoueurText.setText(MainActivity.joueur.getHpActuel() + "/" + MainActivity.joueur.getClasse().getHp());

                            }
                            PVJoueur.setProgress(MainActivity.joueur.getHpActuel());

                        }
                        if (joueurViewsMap.containsKey(joueurId) && !joueurId.equals(MainActivity.joueur.getId())){
                            Pair<ProgressBar, TextView> joueurViews = joueurViewsMap.get(joueurId);
                            ProgressBar playerHealthBar = joueurViews.first;  // ProgressBar du joueur
                            TextView playerHealthText = joueurViews.second;  // TextView du joueur

                            // Mettre à jour la ProgressBar et le TextView avec les nouvelles valeurs
                            int nouvellePV =((Double) joueurData.get("pdv")).intValue() ;  // Exemple de valeur, tu peux la remplacer par celle du joueur
                            int maxPV = 100;      // Exemple de PV maximum, remplace avec la valeur réelle

                            // Mettre à jour la ProgressBar
                            playerHealthBar.setProgress(nouvellePV);

                            // Mettre à jour le TextView
                            playerHealthText.setText(nouvellePV + "/" + maxPV);

                        }
                        PVMonstre.setProgress(pvMonstre);
                        monstreCombat.setHp(pvMonstre);
                        if (monstreCombat.getHp() <= 0){
                            Combat.monstreMort = true;
                        }
                        System.out.println("monstreMort : " + monstreMort);
                        MonstreVaincuVerif(activiteCombat);
                        if (MainActivity.joueur.getHpActuel() > 0){
                            CombatManager.stopCombat();

                        }

                    }

                });
                }
                if (msgType.equalsIgnoreCase("joueursMort")) {
                    JoueursMort(activiteCombat);
                    CombatManager.stopCombat();

                }
            }
                else if (text.equalsIgnoreCase("bienvenue dans la partie")) {
                    MainActivity.joueur.setHpActuel( MainActivity.joueur.getClasse().getHp());
                    Intent intent = new Intent(MainActivity.currentActivity, PartieActivity.class);
                    MainActivity.currentActivity.startActivity(intent);
                } else if (text.equalsIgnoreCase("la session est pleine")) {
                    new PartiePleineFragment().show(MainActivity.currentActivity.getSupportFragmentManager(), "Partie pleine");
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
                    idChefDePartie=Math.round((Double) map.get("joueur"));
                }
                else{
                    idJoueur.add(Math.round((Double) map.get("joueur")));
                }
            }
            MainActivity.infoJoueur(idJoueur,idChefDePartie);
        }
        public void ajoutJoueurPartie(Map<String,Object> msg){
            MainActivity.ajoutJoueur(Math.round((Double) msg.get("idJoueur")));
        }
}

