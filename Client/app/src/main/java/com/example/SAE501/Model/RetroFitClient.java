package com.example.SAE501.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static final String urlSpring="http://10.0.2.2:8080/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // Active le mode lenient
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlSpring)
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Utilise Gson pour convertir la réponse JSON
                    .build();
        }
        return retrofit;
    }
}
