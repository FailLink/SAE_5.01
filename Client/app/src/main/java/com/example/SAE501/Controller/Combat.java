package com.example.SAE501.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.SAE501.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Combat extends AppCompatActivity {

    //les différent boutton d'action
    private Button BouttonATK;
    private Button BouttonDEF;
    private Button BouttonCOMP1;
    private Button BouttonCOMP2;

    //le texte qui annonce le tour du joueur, des alliés ou du monstre
    private TextView TextTour;

    //les différentes barre de vie
    private ProgressBar PVJoueur;
    private ProgressBar PVAlly1;
    private ProgressBar PVAlly2;
    private ProgressBar PVAlly3;
    private ProgressBar PVMonstre;

    //Image du monstre a supprimer a ça mort
    private ImageView Monstre;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.Combat);

        //Récupérer les élèments par leur id :
        //- les bouttons d'actions
        BouttonATK = findViewById(R.id.button_attack);
        BouttonDEF = findViewById(R.id.button_defend);
        BouttonCOMP1 = findViewById(R.id.button_class_skill_1);
        BouttonCOMP2 = findViewById(R.id.button_class_skill_2);

        //- le texte
        TextTour = findViewById(R.id.turn);
    }
}
