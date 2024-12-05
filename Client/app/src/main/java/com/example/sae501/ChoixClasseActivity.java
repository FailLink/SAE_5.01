package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.sae501.Controller.Joueur.JoueurRepository;
import com.example.sae501.View.Caroussel.CarousselAdapter;
import com.example.sae501.View.Caroussel.PageClasse;

import java.util.ArrayList;
import java.util.List;

public class ChoixClasseActivity extends AppCompatActivity {
    private final String descriptionTank = "Une classe possédant des stats défensives élevée " +
            "mais possédant une attaque en dessous des autres classes. Son rôle est de protéger ses alliés " +
            "en prenant les dégâts des monstres.";
    private final String descriptionGuerrier = "Une classe possédant des stats équilibrés. " +
            "Son rôle est d'aider son équipe soit à tanker soit à infliger des dégâts en fonction des situations.";
    private final String descriptionMage = "Une classe possédant une énorme stat d'attaque mais des stats défensives faibles. " +
            "Son rôle est d'infliger le maximum de dégâts au boss à l'aide de ses compétences.";
    private final String descriptionHealer = "Une classe possédant un grand nombre d'HP " +
            "mais une stat d'attaque et de defense assez faible. " +
            "Le healer a pour rôle de soigner les dégâts pris par ses alliés.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_classe);
        MainActivity.currentActivity = this;

        //élément nécessaire pour définir le caroussel
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        JoueurRepository joueurRepository = new JoueurRepository();


        List<PageClasse> pages = new ArrayList<>();

        //ajout des éléments dans le caroussel
        pages.add(new PageClasse(R.drawable.icone_musclow, "Musclow", descriptionTank,
                "Choisir Musclow", v -> joueurRepository.setJoueurClasse("Musclow")));
        pages.add(new PageClasse(R.drawable.icone_guewier, "Guewier", descriptionGuerrier,
                "Choisir Guewier", v -> joueurRepository.setJoueurClasse("Guewier")));
        pages.add(new PageClasse(R.drawable.icone_mawabou, "Mawabou", descriptionMage,
                "Choisir Mawabou", v -> joueurRepository.setJoueurClasse("Mawabou")));
        pages.add(new PageClasse(R.drawable.icone_healer, "Healer", descriptionHealer,
                "Choisir Healer", v -> joueurRepository.setJoueurClasse("Healer")));

        CarousselAdapter adapter = new CarousselAdapter(pages);
        viewPager.setAdapter(adapter);
    }
}