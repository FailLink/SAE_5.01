package com.example.SAE501.Model.Serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static final String urlSpring="http://10.0.2.2:8080/";
    private static Retrofit retrofit;

    /**
     * permet de récupérer l'objet retrofit du singleton
     * @author Matisse Gallouin
     * @return retourne l'objet singleton retrofit de la classe
     */
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlSpring)
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Utilise Gson pour convertir la réponse JSON
                    .build();
        }
        return retrofit;
    }
}
