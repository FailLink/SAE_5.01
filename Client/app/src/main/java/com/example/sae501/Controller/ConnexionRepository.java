package com.example.sae501.Controller;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.Model.Connexion.ConnexionService;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.View.ConnexionErrorFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnexionRepository {
    private ConnexionService connexionService;

    private FragmentActivity fragmentActivity;

    private ScheduleConnexion scheduleConnexion;

    /**
     * constructeur de la classe Connexion repository
     * @author Matisse Gallouin
     */
    public ConnexionRepository(FragmentActivity fragmentActivity, ScheduleConnexion scheduleConnexion) {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.connexionService = retroFitClient.create(ConnexionService.class);
        this.fragmentActivity=fragmentActivity;
        this.scheduleConnexion=scheduleConnexion;
    }

    /**
     * Permet d'entrer en contact avec le serveur afin de savoir si le client et le serveur communique bien renvoie les informations obtenues dans le logcat
     * @author Matisse Gallouin
     */
    public void getTestConnexion() {
        Call<String> reponseHTTP = connexionService.getTestConnexion();
        ConnexionRepository connexionRepository=this;
        reponseHTTP.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body();
                    Log.d("Connection Success", message);
                    scheduleConnexion.startVerification();
                } else {
                    Log.e("Connection Error", "Connection error : " + response.code());
                    scheduleConnexion.stopVerification();
                    ConnexionErrorFragment connexionErrorFragment=new ConnexionErrorFragment(connexionRepository);
                    connexionErrorFragment.show(fragmentActivity.getSupportFragmentManager(),"ConnexionErrorDialog");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
                scheduleConnexion.stopVerification();
                ConnexionErrorFragment connexionErrorFragment=new ConnexionErrorFragment(connexionRepository);
                connexionErrorFragment.show(fragmentActivity.getSupportFragmentManager(),"ConnexionErrorDialog");
            }
        });
    }
}
