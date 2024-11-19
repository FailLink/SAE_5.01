package com.example.sae501.Model.Service;

import com.example.sae501.Model.Entity.Joueur;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JoueurService {
    @GET("/joueurSession")
    Call<Joueur> getJoueurBySessionId();
    @POST("/joueurId")
    Call<Joueur> getJoueurById(Long id);
}
