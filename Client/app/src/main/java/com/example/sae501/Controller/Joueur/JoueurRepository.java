package com.example.sae501.Controller.Joueur;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.HomeActivity;
import com.example.sae501.MainActivity;
import com.example.sae501.Model.Service.JoueurService;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.PartieActivity;
import com.example.sae501.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoueurRepository {
    private JoueurService joueurService;

    private FragmentActivity fragmentActivity;

    /**
     * constructeur de la classe JoueurRepository
     *
     * @author Matisse Gallouin
     */
    public JoueurRepository() {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.joueurService = retroFitClient.create(JoueurService.class);
        this.fragmentActivity = MainActivity.currentActivity;
    }

    /**
     * fonction permettant de récupérer un joueur à l'aide de son id
     * et de l'ajouter au différentes variables global de la partie ainsi qu'a la view de PartieActivité
     *
     * @param id       id du joueur voulu
     * @param nbJoueur position dans la liste pour la view
     * @author Matisse Gallouin
     */
    public void getJoueurPartieById(Long id, int nbJoueur) {
        Call<Joueur> responseHTTP = joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                Joueur joueur = response.body();
                joueur.setHpActuel(joueur.getClasse().getHp());
                MainActivity.joueursPartie.add(joueur);

                while (fragmentActivity.getClass() != PartieActivity.class) {
                    fragmentActivity = MainActivity.currentActivity;
                }
                String[] idString = {"pseudoJoueur", "imageClasseJoueur", "boutonExclusion", "linearLayoutJoueur"};
                for (String s : idString) {
                    String idName = s + nbJoueur;
                    int resId = fragmentActivity.getResources().getIdentifier(idName, "id",
                            fragmentActivity.getPackageName());
                    View view = fragmentActivity.findViewById(resId);
                    if (s.equalsIgnoreCase("pseudoJoueur")) {
                        TextView textView = (TextView) view;
                        textView.setText(joueur.getPseudo());
                    } else if (s.equalsIgnoreCase("imageClasseJoueur")) {
                        ImageView imageView = (ImageView) view;
                        int drawableId = fragmentActivity.getResources().getIdentifier("icone_" + joueur.getClasse().getNom().toLowerCase()
                                , "drawable", fragmentActivity.getPackageName());
                        imageView.setImageResource(drawableId);
                    } else if (s.equalsIgnoreCase("boutonExclusion") && Objects.equals(MainActivity.joueur.getId(), MainActivity.chefDePartie.getId())) {
                        ImageButton button = (ImageButton) view;
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(v -> {
                            String message = "{\"type\":\"exclusion\",\"joueur\":" + joueur.getId() + "}";
                            MainActivity.webSocketPartie.send(message);
                        });
                    } else if (s.equalsIgnoreCase("linearLayoutJoueur")) {
                        LinearLayout linearLayout = (LinearLayout) view;
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                if (MainActivity.joueursPartie.size() == 4 && Objects.equals(MainActivity.joueur.getId(), MainActivity.chefDePartie.getId())) {
                    fragmentActivity.findViewById(R.id.boutonPartie).setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }

    /**
     * fonction permettant de récupérer le chef de la partie à l'aide de son id
     * et de l'ajouter au différentes variables global de la partie ainsi qu'a la view de PartieActivité
     *
     * @param id id du joueur chef de la partie
     * @author Matisse Gallouin
     */
    public void getChefPartieById(Long id) {
        Call<Joueur> responseHTTP = joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                Joueur joueur = response.body();
                joueur.setHpActuel(joueur.getClasse().getHp());
                MainActivity.chefDePartie = joueur;
                MainActivity.joueursPartie.add(joueur);
                String[] idString = {"pseudoJoueur", "imageClasseJoueur", "linearLayoutJoueur"};
                while (fragmentActivity.getClass() != PartieActivity.class) {
                    fragmentActivity = MainActivity.currentActivity;
                }
                for (String s : idString) {
                    String idName = s + 1;
                    int resId = fragmentActivity.getResources().getIdentifier(idName, "id",
                            fragmentActivity.getPackageName());
                    View view = fragmentActivity.findViewById(resId);
                    if (s.equalsIgnoreCase("pseudoJoueur")) {
                        TextView textView = (TextView) view;
                        textView.setText(joueur.getPseudo());
                    } else if (s.equalsIgnoreCase("imageClasseJoueur")) {
                        ImageView imageView = (ImageView) view;
                        int drawableId = fragmentActivity.getResources().getIdentifier("icone_" + joueur.getClasse().getNom().toLowerCase()
                                , "drawable", fragmentActivity.getPackageName());
                        imageView.setImageResource(drawableId);
                    } else if (s.equalsIgnoreCase("linearLayoutJoueur")) {
                        LinearLayout linearLayout = (LinearLayout) view;
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                if (MainActivity.joueursPartie.size() == 4 && Objects.equals(MainActivity.joueur.getId(), MainActivity.chefDePartie.getId())) {
                    fragmentActivity.findViewById(R.id.boutonPartie).setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }

    /**
     * permet de transmettre le choix de classe du joueur de l'utilisateur actuel au serveur et à la BDD
     * à partir du nom de la classe
     *
     * @param classeName nom de la classe choisi par le joueur
     * @author Matisse Gallouin
     */
    public void setJoueurClasse(String classeName) {
        fragmentActivity = MainActivity.currentActivity;
        Call<Joueur> responseHttp = joueurService.setJoueurClasse(MainActivity.joueur.getId(), classeName);
        responseHttp.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                if (response.isSuccessful()) {
                    MainActivity.joueur = response.body();
                    MainActivity.joueur.setHpActuel(MainActivity.joueur.getClasse().getHp());
                    Intent intent = new Intent(fragmentActivity, HomeActivity.class);
                    fragmentActivity.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {
                Log.e("Erreur requête", "erreur", t);
            }
        });
    }
}
