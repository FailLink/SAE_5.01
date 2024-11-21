package com.example.sae501.Controller.Joueur;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Partie;
import com.example.sae501.Model.Service.JoueurService;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.Partie_Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JoueurRepository {
    private JoueurService joueurService;

    private FragmentActivity fragmentActivity;

    /**
     * constructeur de la classe Connexion repository
     * @author Matisse Gallouin
     */
    public JoueurRepository() {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.joueurService = retroFitClient.create(JoueurService.class);
        this.fragmentActivity=MainActivity.currentActivity;
    }

    public void getJoueurBySessionId(){
        Call<Joueur> reponseHTTP = joueurService.getJoueurBySessionId();
        reponseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                MainActivity.joueur=response.body();
                SharedPreferences sharedPreferences = fragmentActivity.getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("joueur_id",MainActivity.joueur.getId().toString());
                editor.apply();
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {
                Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
            }
        });
    }

    public void getJoueurPartieById(Long id,int nbJoueur){
        Call<Joueur> responseHTTP= joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                Joueur joueur=response.body();
                MainActivity.joueursPartie.add(joueur);
                String[] idString={"pseudoJoueur","imageClasseJoueur","boutonExclusion"};
                for(String s:idString){
                    String idName=s+nbJoueur;
                    int resId = fragmentActivity.getResources().getIdentifier(idName, "id",
                            fragmentActivity.getPackageName());
                    View view =fragmentActivity.findViewById(resId);
                    System.out.println(view);
                    if(s.equalsIgnoreCase("pseudoJoueur")){
                        TextView textView=(TextView) view;
                        textView.setText(joueur.getPseudo());
                    } else if (s.equalsIgnoreCase("imageClasseJoueur")) {
                        ImageView imageView=(ImageView) view;
                        /*int drawableId = fragmentActivity.getResources().getIdentifier("icone_"+joueurs.get(i).getClasse().getNom()
                                , "drawable", fragmentActivity.getPackageName());
                        imageView.setImageResource(drawableId);*/
                    } else if (s.equalsIgnoreCase("boutonExclusion")) {
                        Button button=(Button) view;
                        button.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }
    public void getChefPartieById(Long id){
        Call<Joueur> responseHTTP= joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                Joueur joueur=response.body();
                MainActivity.chefDePartie=joueur;
                String[] idString={"pseudoJoueur","imageClasseJoueur","boutonExclusion"};
                fragmentActivity=MainActivity.currentActivity;
                for(String s:idString) {
                    String idName=s+1;
                    int resId = fragmentActivity.getResources().getIdentifier(idName, "id",
                            fragmentActivity.getPackageName());
                    View view = fragmentActivity.findViewById(resId);
                    if (s.equalsIgnoreCase("pseudoJoueur")) {
                        TextView textView = (TextView) view;
                        textView.setText(joueur.getPseudo());
                    } else if (s.equalsIgnoreCase("imageClasseJoueur")) {
                        ImageView imageView = (ImageView) view;
                        /*int drawableId = fragmentActivity.getResources().getIdentifier("icone_"+joueurs.get(i).getClasse().getNom()
                               , "drawable", fragmentActivity.getPackageName());
                        imageView.setImageResource(drawableId);*/
                    }
                }

            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }
}
