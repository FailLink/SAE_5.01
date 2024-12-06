package com.example.sae501;

import static com.example.sae501.MapsActivity.carte;
import static com.example.sae501.MapsActivity.clickedMarker;
import static com.example.sae501.MapsActivity.markers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sae501.Model.Entity.Competence;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Monstre;
import com.example.sae501.Model.Game.CombatManager;

import android.os.Handler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Combat extends AppCompatActivity  {

    //les différent boutton d'action
    private Button BouttonATK;
    private Button BouttonDEF;
    private Button BouttonCOMP1;
    private Button BouttonCOMP2;

    //le texte qui annonce le tour du joueur, des alliés ou du monstre
    private TextView TextTour;

    //les différentes barre de vie
    public static ProgressBar PVJoueur;
    public static TextView PVJoueurText ;


    public static ProgressBar PVAlly1;
    public static TextView PVAlly1Text  ;

    public static ProgressBar PVAlly2;
    public static TextView PVAlly2Text;

    public static ProgressBar PVAlly3;
    public static TextView PVAlly3Text;

    private TextView AllyName1 ;
    private TextView AllyName2 ;
    private TextView AllyName3 ;

    public static ProgressBar PVMonstre;

    public static Map<Long, Pair<ProgressBar, TextView>> joueurViewsMap = new HashMap<>();

    public static Monstre monstreCombat;

    //Image du monstre a supprimer a ça mort
    public static ImageView MonstreView;
    private TextView monstreText;

    //image pour les animation sur le monstre
    private ImageView Monster_animate;

    public static boolean monstreMort;

    public static Activity activiteCombat;

    private Handler handler = new Handler(Looper.getMainLooper());



    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat);

        activiteCombat = this;
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

        monstreText= findViewById(R.id.monster_health_label);


        PVJoueurText =  findViewById(R.id.health_text_player);

        PVAlly1Text =  findViewById(R.id.health_text_player_1);
        PVAlly2Text =  findViewById(R.id.health_text_player_2);
        PVAlly3Text =  findViewById(R.id.health_text_player_3);

        joueurViewsMap.put(1L, new Pair<>(PVAlly1, PVAlly1Text));  // Joueur 1
        joueurViewsMap.put(2L, new Pair<>(PVAlly2, PVAlly2Text));  // Joueur 2
        joueurViewsMap.put(3L, new Pair<>(PVAlly3, PVAlly3Text));  // Joueur 3


        // Initialiser les vues (ProgressBar et TextView) pour chaque joueur
        ProgressBar PVAlly1 = findViewById(R.id.health_bar_player_1);
        ProgressBar PVAlly2 = findViewById(R.id.health_bar_player_2);
        ProgressBar PVAlly3 = findViewById(R.id.health_bar_player_3);

        TextView PVAlly1Text = findViewById(R.id.health_text_player_1);
        TextView PVAlly2Text = findViewById(R.id.health_text_player_2);
        TextView PVAlly3Text = findViewById(R.id.health_text_player_3);

        TextView AllyName1 = findViewById(R.id.player_1_health_label) ;
        TextView AllyName2 = findViewById(R.id.player_2_health_label) ;
        TextView AllyName3 = findViewById(R.id.player_3_health_label) ;


        PVJoueurText.setText(MainActivity.joueur.getHpActuel() + "/" + MainActivity.joueur.getClasse().getHp());
        PVJoueur.setProgress(MainActivity.joueur.getHpActuel());



        // Création de la Map pour associer chaque joueur à sa ProgressBar et TextView
        for (int i = 0; i < MainActivity.joueursPartie.size(); i++) {
            List<Joueur> joueursList = new ArrayList<>(MainActivity.joueursPartie);

            Joueur joueur = joueursList.get(i);

            // Associer les joueurs à leurs vues respectives
            ProgressBar progressBar = null;
            TextView textView = null;


            if (!Objects.equals(joueursList.get(i).getId(), MainActivity.joueur.getId())) {
                // En fonction de l'index du joueur, associer les vues
                switch (i) {
                    case 0:
                        progressBar = PVAlly1;
                        textView = PVAlly1Text;
                        PVAlly1.setProgress(joueur.getHpActuel());
                        PVAlly1Text.setText(joueur.getHpActuel() + "/" + joueur.getClasse().getHp());
                        AllyName1.setText(joueur.getPseudo());
                        break;
                    case 1:
                        progressBar = PVAlly2;
                        textView = PVAlly2Text;
                        PVAlly2.setProgress(joueur.getHpActuel());
                        PVAlly2Text.setText(joueur.getHpActuel() + "/" + joueur.getClasse().getHp());
                        AllyName2.setText(joueur.getPseudo());
                        break;
                    case 2:
                        progressBar = PVAlly3;
                        textView = PVAlly3Text;
                        PVAlly3.setProgress(joueur.getHpActuel());
                        PVAlly3Text.setText(joueur.getHpActuel() + "/" + joueur.getClasse().getHp());
                        AllyName3.setText(joueur.getPseudo());
                        break;
                }
            }

            // Associer chaque joueur à la ProgressBar et TextView correspondantes dans la Map
            if (progressBar != null && textView != null) {
                joueurViewsMap.put(joueur.getId(), new Pair<>(progressBar, textView));
            }
        }


        monstreCombat = new Monstre(
                getIntent().getStringExtra("monstreNom"),
                getIntent().getIntExtra("monstreHp", 0),
                getIntent().getIntExtra("monstreAttaque", 0),
                getIntent().getIntExtra("monstreDef", 0),
                getIntent().getStringExtra("monstreResistance"),
                getIntent().getStringExtra("monstreFaiblesse")
        );

        //initialiser la progressBar
        ProgressBar pvMonstre=findViewById(R.id.health_bar_cyclope);
        pvMonstre.setMax(monstreCombat.getHp());

        monstreMort = false;

        PVMonstre.setProgress(getIntent().getIntExtra("monstreHp", 0));
        monstreText.setText(getIntent().getStringExtra("monstreNom"));

        //- l'image du monstre
        MonstreView = findViewById(R.id.cyclope_image);

        //- l'image view des animation du monstre
        Monster_animate = findViewById(R.id.monster_animate_image);
        List<Competence> competences = new ArrayList<>(MainActivity.joueur.getCompetences());

        TextView competence1 = findViewById(R.id.button_class_skill_1);
        competence1.setText(competences.get(0).getNom());

        TextView competence2 = findViewById(R.id.button_class_skill_2);
        competence2.setText(competences.get(1).getNom());
        JoueurMortSend();


        //Choix de l'action attaque par le joueur
        BouttonATK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.joueur.getHpActuel() <= 0) {
                    PVJoueurText.setText("Vous êtes mort");
                    CombatManager.startCombatMort(monstreCombat);

                } else {
                    // Envoie de l'action au serveur
                    CombatManager.startCombatAttaque(monstreCombat);
                    // Reduire les point de vie en conséquence en appellant ReduirePV(dégat subie en int, et PVMonstre)
                    //Lancer l'animation
                    AnimationAttackMonstre();
                    //Et enfi
                    // n vérifier si le Monstre est vaincu
                }
            }
        });

        //Choix de l'action Defence par le joueur
        BouttonDEF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.joueur.getHpActuel() <= 0) {
                    PVJoueurText.setText("Vous êtes mort");
                    CombatManager.startCombatMort(monstreCombat);

                } else {
                    // Envoie de l'action au serveur
                    CombatManager.startCombatDefense(monstreCombat);

                    // Reduire les point de vie en conséquence en appellant ReduirePV(dégat subie en int, et PVMonstre)

                    //Et enfin vérifier si le Monstre est vaincu
                }
            }
        });

        //Choix de l'action compétence 1 par le joueur
        BouttonCOMP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.joueur.getHpActuel() <= 0) {
                    PVJoueurText.setText("Vous êtes mort");
                    CombatManager.startCombatMort(monstreCombat);

                } else {
                    // Envoie de l'action au serveur
                    CombatManager.startCombatCompetence1(monstreCombat);

                    // effet de la compétence 1

                    //Et enfin vérifier si le Monstre est vaincu
                }
            }
        });

        //Choix de l'action compétence 2 par le joueur
        BouttonCOMP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.joueur.getHpActuel() <= 0) {
                    CombatManager.startCombatMort(monstreCombat);

                } else {
                    // Envoie de l'action au serveur
                    CombatManager.startCombatCompetence2(monstreCombat);
                    // effet de la compétence 2

                    //Et enfin vérifier si le Monstre est vaincu
                }
            }
        });

        System.out.println("monstreMortComabt :" + monstreMort);
        if (monstreMort){
            finish();
        }
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
    public static boolean MonstreVaincuVerif(Activity activity){
        int ActualPV = PVMonstre.getProgress();
        if (ActualPV<=0){
            MonstreView.setVisibility(View.INVISIBLE);
            MainActivity.webSocketPartie.send("{\"type\": \"PartieFini\"}");
            clickedMarker.remove(carte);
            System.out.println(markers.size());
            markers.remove(markers.size() - 1);
            activity.finish();
            return true;

        }
        return false;
    }

    public static void JoueursMort(Activity activity){
            activity.finish();
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

    private void JoueurMortSend() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Vérifie si la condition est remplie (PV du joueur <= 0)
                if (MainActivity.joueur.getHpActuel() <= 0) {
                    // Appeler la méthode pour gérer la mort du joueur
                    CombatManager.startCombatMort(monstreCombat);
                } else {
                    // Si le joueur est toujours en vie, relancer la vérification après 5 secondes
                    JoueurMortSend();
                }
            }
        }, 1000);  // 5000 ms = 5 secondes
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter le Handler pour éviter les fuites de mémoire
        handler.removeCallbacksAndMessages(null);
    }

}
