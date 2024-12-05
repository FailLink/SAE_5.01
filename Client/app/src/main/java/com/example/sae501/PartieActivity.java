package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PartieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);
        MainActivity.currentActivity = this;
        TextView textView = this.findViewById(R.id.textIdPartie);
        textView.setText(textView.getText() + MainActivity.partie.getId().toString());
    }

    /**
     * fonction définissant le comportement du bouton lancer partie au clique
     *
     * @param view
     * @author Matisse Gallouin
     */
    public void onClickLancerPartie(View view) {
        System.out.println("boutonCliqué");
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        MainActivity.webSocketPartie.send("{\"type\" : \"lancementPartie\"}");
    }
    
    /**
     * fonction définissant le comportement du quitter lancer partie au clique
     *
     * @param view
     * @author Matisse Gallouin
     */
    public void onClickQuitter(View view) {
        String message = "{ \"type\" : \"deconnexion\" , \"joueur\" : " + MainActivity.joueur.getId() + "}";
        MainActivity.webSocketPartie.send(message);
        MainActivity.joueursPartie.clear();
        MainActivity.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup listeJoueurView = MainActivity.currentActivity.findViewById(R.id.layoutPartie);
                listeJoueurView.removeAllViews();

                LayoutInflater.from(MainActivity.currentActivity).inflate(R.layout.activity_partie, listeJoueurView, true);
                TextView textView = MainActivity.currentActivity.findViewById(R.id.textIdPartie);
                textView.setText(textView.getText() + MainActivity.partie.getId().toString());
            }
        });
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}