package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.sae501.Controller.Joueur.JoueurRepository;
import com.example.sae501.View.Caroussel.CarousselAdapter;
import com.example.sae501.View.Caroussel.PageClasse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChoixClasseActivity extends AppCompatActivity {
    private final String descriptionTank="";
    private final String descriptionGuerrier="";
    private final String descriptionMage="";
    private final String descriptionHealer="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_classe);
        MainActivity.currentActivity = this;

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        JoueurRepository joueurRepository=new JoueurRepository();


        List<PageClasse> pages = new ArrayList<>();

        pages.add(new PageClasse(R.drawable.icone_tank, "Musclow", descriptionTank,
                "Choisir Musclow",v->joueurRepository.setJoueurClasse("Musclow")));
        pages.add(new PageClasse(R.drawable.icone_guerrier, "Guewier", descriptionGuerrier,
                "Choisir Guewier",v->joueurRepository.setJoueurClasse("Guewier")));
        pages.add(new PageClasse(R.drawable.icone_mage, "Mawabou", descriptionMage,
                "Choisir Mawabou",v->joueurRepository.setJoueurClasse("Mawabou")));
        pages.add(new PageClasse(R.drawable.icone_healer, "Healer", descriptionHealer,
                "Choisir Healer",v->joueurRepository.setJoueurClasse("Healer")));

        CarousselAdapter adapter = new CarousselAdapter(pages);
        viewPager.setAdapter(adapter);
    }
}