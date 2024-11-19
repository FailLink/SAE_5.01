package com.example.SAE501.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.SAE501.R;

import android.view.View;
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
        setContentView(R.layout.combat);

        //Récupérer les élèments par leur id :
        //- les bouttons d'actions
        BouttonATK = findViewById(R.id.button_attack);
        BouttonDEF = findViewById(R.id.button_defend);
        BouttonCOMP1 = findViewById(R.id.button_class_skill_1);
        BouttonCOMP2 = findViewById(R.id.button_class_skill_2);

        //- le texte
        TextTour = findViewById(R.id.turn);

        //- les barres de vie
        PVJoueur = findViewById(R.id.health_bar_player);
        PVAlly1 = findViewById(R.id.health_bar_1);
        PVAlly2 = findViewById(R.id.health_bar_2);
        PVAlly3 = findViewById(R.id.health_bar_3);
        PVMonstre = findViewById(R.id.health_bar_cyclope);

        //- l'image du monstre
        Monstre = findViewById(R.id.cyclope_image);

        //Choix de l'action attaque par le joueur
        BouttonATK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoie de l'action au serveur
                // ici
                // Reduire les point de vie en conséquence en appellant ReduirePV(dégat subie en int, et PVMonstre)

                //Et enfin vérifier si le Monstre est vaincu
                MonstreVaincuVerif();
            }
        });

        //Choix de l'action compétence 1 par le joueur
        BouttonCOMP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoie de l'action au serveur
                // ici
                // effet de la compétence 1

                //Et enfin vérifier si le Monstre est vaincu
                MonstreVaincuVerif();
            }
        });

        //Choix de l'action compétence 2 par le joueur
        BouttonCOMP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoie de l'action au serveur
                // ici
                // effet de la compétence 2

                //Et enfin vérifier si le Monstre est vaincu
                MonstreVaincuVerif();
            }
        });
    }

    //Fonction pour réduire les points de vie d'un joueur ou d'un monstre il suffit de donnée les dégats puis qui les recoits
    public void ReduirePV(int degat, ProgressBar barre){
        int actualPV = barre.getProgress();
        barre.setProgress(actualPV-degat);
    }

    //Fonction pour augmenter les points de vie d'un joueur ou d'un monstre il suffit de donnée les soins puis qui les recoits
    public void AugmenterPV(int soin, ProgressBar barre){
        int actualPV = barre.getProgress();
        barre.setProgress(actualPV+soin);
    }

    public boolean MonstreVaincuVerif(){
        int ActualPV = PVMonstre.getProgress();
        if (ActualPV==0){
            Monstre.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
}
