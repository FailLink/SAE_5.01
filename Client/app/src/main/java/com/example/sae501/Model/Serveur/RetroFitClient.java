package com.example.sae501.Model.Serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new SessionIntercepteur())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlSpring)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))  // Utilise Gson pour convertir la réponse JSON
                    .build();
        }
        return retrofit;
    }
}
