package com.example.sae501.Controller.Joueur;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.Controller.Connexion.ConnexionRepository;
import com.example.sae501.LoginActivity;
import com.example.sae501.MainActivity;
import com.example.sae501.Model.Connexion.ConnexionService;
import com.example.sae501.Model.Connexion.JoueurService;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.View.ConnexionErrorFragment;

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

    public void getJoueurById(Long id){
        Call<Joueur> responseHTTP= joueurService.getJoueurById(id);
        responseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                MainActivity.joueur=response.body();
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {

            }
        });
    }
}
