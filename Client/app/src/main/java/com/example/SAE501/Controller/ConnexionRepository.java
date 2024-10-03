package com.example.SAE501.Controller;

import android.util.Log;

import com.example.SAE501.Model.ConnexionService;
import com.example.SAE501.Model.RetroFitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnexionRepository {
    private ConnexionService connexionService;

    public ConnexionRepository() {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.connexionService = retroFitClient.create(ConnexionService.class);
    }

    public void getTestConnexion() {
        Call<String> reponseHTTP = connexionService.getTestConnexion();
        reponseHTTP.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body(); // Devrait afficher "Le serveur est joignable"
                    Log.d("API Response", message);
                } else {
                    Log.e("API Error", "Erreur de connexion: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
            }
        });
    }
}
