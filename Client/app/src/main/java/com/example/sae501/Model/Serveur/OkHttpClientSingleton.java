package com.example.sae501.Model.Serveur;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientSingleton {
    private static okhttp3.OkHttpClient okHttpClient;

    /**
     * permet de récupérer l'objet okhttp du singleton
     * @author Matisse Gallouin
     * @return retourne l'objet singleton okhttp de la classe
     */
    public static okhttp3.OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {
            okHttpClient = new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(new SessionIntercepteur())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(0, TimeUnit.MILLISECONDS) // Désactive le timeout de lecture
                    .writeTimeout(0, TimeUnit.MILLISECONDS) // Désactive le timeout d'écriture
                    .build();
        }
        return okHttpClient;
    }
}
