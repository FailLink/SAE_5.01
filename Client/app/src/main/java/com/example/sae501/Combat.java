package com.example.sae501;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sae501.R;

import android.os.Handler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

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

    //image pour les animation sur le monstre
    private ImageView Monster_animate;

    @SuppressLint("MissingInflatedId")
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
        PVAlly1 = findViewById(R.id.health_bar_player_1);
        PVAlly2 = findViewById(R.id.health_bar_player_2);
        PVAlly3 = findViewById(R.id.health_bar_player_3);
        PVMonstre = findViewById(R.id.health_bar_cyclope);

        //- l'image du monstre
        Monstre = findViewById(R.id.cyclope_image);

        //- l'image view des animation du monstre
        Monster_animate = findViewById(R.id.monster_animate_image);

        //Choix de l'action attaque par le joueur
        BouttonATK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoie de l'action au serveur
                // ici
                // Reduire les point de vie en conséquence en appellant ReduirePV(dégat subie en int, et PVMonstre)

                //Lancer l'animation
                AnimationAttackMonstre();
                //Et enfin vérifier si le Monstre est vaincu
                MonstreVaincuVerif();
            }
        });

        //Choix de l'action Defence par le joueur
        BouttonDEF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoie de l'action au serveur
                // ici
                // Reduire les point de vie en conséquence en appellant ReduirePV(dégat subie en int, et PVMonstre)

                //Et enfin vérifier si le Monstre est vaincu
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
        if (actualPV-degat <= 0){
            barre.setProgress(0);
        }else{
            barre.setProgress(actualPV-degat);
        }
    }

    //Fonction pour augmenter les points de vie d'un joueur ou d'un monstre il suffit de donnée les soins puis qui les recoits
    public void AugmenterPV(int soin, ProgressBar barre){
        int actualPV = barre.getProgress();
        if (actualPV+soin >= barre.getMax()){
            barre.setProgress(barre.getMax());
        }else{
            barre.setProgress(actualPV+soin);
        }
    }

    //Vérifier que le Monstre soit mort ou non
    public boolean MonstreVaincuVerif(){
        int ActualPV = PVMonstre.getProgress();
        if (ActualPV==0){
            Monstre.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    public void AnimationAttackMonstre() {
        // Charge le GIF dans l'ImageView
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(Monster_animate);
        Glide.with(this).load(R.drawable.attack_effect).into(imageViewTarget);

        // Planifie l'arrêt de l'animation après 2 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Supprime l'animation
                Monster_animate.setImageDrawable(null);
            }
        }, 2000); // Délai de 2 secondes
    }
}
