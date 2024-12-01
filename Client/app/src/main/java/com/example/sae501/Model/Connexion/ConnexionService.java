package com.example.sae501.Model.Connexion;

import com.example.sae501.Model.Entity.Joueur;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConnexionService {
    @GET("/testConnexion")
    Call<String> getTestConnexion();
    @GET("/joueurSession")
    Call<Joueur> getJoueurBySessionId();
}
