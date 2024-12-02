package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PartieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);
        MainActivity.currentActivity=this;
        TextView textView=this.findViewById(R.id.textIdPartie);
        textView.setText(textView.getText()+MainActivity.partie.getId().toString());
    }
    public void onClickLancerPartie(View view){
        Intent intent=new Intent(this, MapsActivity.class);
        startActivity(intent);
        MainActivity.webSocketPartie.send("{\"type\" : \"lancementPartie\"}");
    }
    public void onClickQuitter(View view) {
        String message = "{ \"type\" : \"deconnexion\" , \"joueur\" : " + MainActivity.joueur.getId() + "}";
        MainActivity.webSocketPartie.send(message);
        MainActivity.joueursPartie.clear();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}