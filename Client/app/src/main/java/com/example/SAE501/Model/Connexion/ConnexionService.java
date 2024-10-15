package com.example.SAE501.Model.Connexion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConnexionService {
    @GET("/testConnexion")
    Call<String> getTestConnexion();
}
