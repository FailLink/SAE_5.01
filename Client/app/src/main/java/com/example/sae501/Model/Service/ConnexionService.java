package com.example.sae501.Model.Service;

import com.example.sae501.Model.Entity.Joueur;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnexionService {
    @GET("/testConnexion")
    Call<String> getTestConnexion();
    @GET("/joueurSession")
    Call<Joueur> getJoueurBySessionId();
}
