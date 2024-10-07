package com.example.SAE501.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.SAE501.MainActivity;
import com.example.SAE501.Model.ConnexionService;
import com.example.SAE501.Model.RetroFitClient;
import com.example.SAE501.View.ConnexionErrorFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnexionRepository {
    private ConnexionService connexionService;
    //importer les divers éléments nécessaires pour l'affichage dans l'application
    // afin d'éviter de rédiger les fonctions dans le main
    private FragmentActivity fragmentActivity;

    /**
     * constructeur de la classe Connexion repository
     * @author Matisse Gallouin
     */
    public ConnexionRepository(FragmentActivity fragmentActivity) {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.connexionService = retroFitClient.create(ConnexionService.class);
        this.fragmentActivity=fragmentActivity;
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
                    String message = response.body(); // Devrait afficher "Le serveur est joignable"
                    Log.d("Connection Success", message);
                } else {
                    Log.e("Connection Failed", "Connection error : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
                ConnexionErrorFragment connexionErrorFragment=new ConnexionErrorFragment(connexionRepository);
                connexionErrorFragment.show(fragmentActivity.getSupportFragmentManager(),"ConnexionErrorDialog");
            }
        });
    }
}
