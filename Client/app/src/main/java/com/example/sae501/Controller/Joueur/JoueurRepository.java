package com.example.sae501.Controller.Joueur;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Service.JoueurService;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Serveur.RetroFitClient;

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
    public JoueurRepository(FragmentActivity fragmentActivity) {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.joueurService = retroFitClient.create(JoueurService.class);
        this.fragmentActivity=fragmentActivity;
    }
    public JoueurRepository() {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.joueurService = retroFitClient.create(JoueurService.class);
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

    public void getJoueurPartieById(Long id){
        Call<Joueur> responseHTTP= joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                MainActivity.joueursPartie.add(response.body());
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
                MainActivity.chefDePartie=response.body();
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }
}
