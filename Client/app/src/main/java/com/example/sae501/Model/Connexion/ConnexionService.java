package com.example.sae501.Model.Connexion;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnexionService {
    @GET("/testConnexion")
    Call<String> getTestConnexion();
}
