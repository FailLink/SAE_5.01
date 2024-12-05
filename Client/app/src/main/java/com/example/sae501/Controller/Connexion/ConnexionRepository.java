package com.example.sae501.Controller.Connexion;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.ChoixClasseActivity;
import com.example.sae501.HomeActivity;
import com.example.sae501.LoginActivity;
import com.example.sae501.MainActivity;
import com.example.sae501.Model.Service.ConnexionService;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.View.Connexion.ConnexionErrorFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnexionRepository {
    private ConnexionService connexionService;

    private FragmentActivity fragmentActivity;

    private ScheduleConnexion scheduleConnexion;

    /**
     * constructeur de la classe Connexion repository
     *
     * @author Matisse Gallouin
     */
    public ConnexionRepository(ScheduleConnexion scheduleConnexion) {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.connexionService = retroFitClient.create(ConnexionService.class);
        this.fragmentActivity = MainActivity.currentActivity;
        this.scheduleConnexion = scheduleConnexion;
    }

    /**
     * Permet d'entrer en contact avec le serveur afin de savoir si le client et le serveur communique bien
     * renvoie les informations obtenues dans le logcat
     *
     * @author Matisse Gallouin
     */
    public void getTestConnexion() {
        Call<String> reponseHTTP = connexionService.getTestConnexion();
        ConnexionRepository connexionRepository = this;
        reponseHTTP.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //dans le cas d'une réussite redémarre la vérification
                    String message = response.body();
                    Log.d("Connection Success", message);
                    scheduleConnexion.startVerification();
                } else {
                    //si le message n'est pas un succès alors on affiche le fragment
                    Log.e("Connection Error", "Connection error : " + response.code());
                    scheduleConnexion.stopVerification();
                    ConnexionErrorFragment connexionErrorFragment = new ConnexionErrorFragment(connexionRepository);
                    connexionErrorFragment.show(fragmentActivity.getSupportFragmentManager(), "ConnexionErrorDialog");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    //dans le cas d'un timeout on affiche le fragment
                    Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
                    scheduleConnexion.stopVerification();
                    ConnexionErrorFragment connexionErrorFragment = new ConnexionErrorFragment(connexionRepository);
                    connexionErrorFragment.show(fragmentActivity.getSupportFragmentManager(), "ConnexionErrorDialog");
                } else if (t instanceof HttpException) {
                    //dans le cas d'une erreur http on renvoie sur la connexion car l'erreur est sûrement lié à
                    //une identifcation trop ancienne
                    Intent intent = new Intent(fragmentActivity, LoginActivity.class);
                    fragmentActivity.startActivity(intent);
                }
            }
        });
    }

    /**
     * fonction permettant à partir du jsessionid reçu lors de la connexion de récupérer le joueur affilié,
     * conserver les informations de connexion le JSESSIONID ainsi que le joueur pour nos requêtes
     * et éviter que l'utilisateur doivent se reconnecter à chaque redémarrage
     *
     * @author Matisse Gallouin
     */
    public void getJoueurBySessionId() {
        Call<Joueur> reponseHTTP = connexionService.getJoueurBySessionId();
        reponseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                //ajout dans les data du jeux
                MainActivity.joueur = response.body();
                MainActivity.joueur.setHpActuel(MainActivity.joueur.getClasse().getHp());
                SharedPreferences sharedPreferences = fragmentActivity.getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("joueur_id", MainActivity.joueur.getId().toString());
                editor.apply();

                //lancement du choix du rôle dans le cas où celui-ci n'a pas encore été choisi
                if (MainActivity.joueur.getClasse() != null) {
                    Intent intent = new Intent(fragmentActivity, HomeActivity.class);
                    fragmentActivity.startActivity(intent);
                } else {
                    Intent intent = new Intent(fragmentActivity, ChoixClasseActivity.class);
                    fragmentActivity.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {
                Log.e("Connection failed", "La connexion n'a pas pu être établie", t);
            }
        });
    }

    /**
     * fonction permettant à partir du jsessionid conservé de vérifier si la connexion est toujours valable<br>
     * si celle-ci n'est plus valable on demande alors à l'utilisateur de se reconnecter
     *
     * @author Matisse Gallouin
     */
    public void testSessionId() {
        Call<Joueur> reponseHTTP = connexionService.getJoueurBySessionId();
        reponseHTTP.enqueue(new Callback<Joueur>() {
            @Override
            public void onResponse(Call<Joueur> call, Response<Joueur> response) {
                if (!response.isSuccessful()) {
                    Intent intent = new Intent(fragmentActivity, LoginActivity.class);
                    fragmentActivity.startActivity(intent);
                } else {
                    MainActivity.joueur = response.body();
                    MainActivity.joueur.setHpActuel(MainActivity.joueur.getClasse().getHp());
                    if (MainActivity.joueur.getClasse() != null) {
                        Intent intent = new Intent(fragmentActivity, HomeActivity.class);
                        fragmentActivity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(fragmentActivity, ChoixClasseActivity.class);
                        fragmentActivity.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Joueur> call, Throwable t) {
                Intent intent = new Intent(fragmentActivity, LoginActivity.class);
                fragmentActivity.startActivity(intent);
            }
        });
    }
}
