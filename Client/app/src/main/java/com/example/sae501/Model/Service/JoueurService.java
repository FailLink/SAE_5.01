package com.example.sae501.Model.Service;

import com.example.sae501.Model.Entity.Joueur;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JoueurService {
    @GET("/joueurSession")
    Call<Joueur> getJoueurBySessionId();
    @POST("/joueurId")
    Call<Joueur> getJoueurById(@Query("id") Long id);
    @POST("/setJoueurClasse")
    Call<Joueur> setJoueurClasse(@Query("id") Long id,@Query("classe") String classeName);
}
