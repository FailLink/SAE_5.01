package com.example.sae501.Model.Serveur;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientSingleton {
    //singleton
    private static okhttp3.OkHttpClient okHttpClient;

    /**
     * permet de récupérer l'objet okhttp du singleton
     *
     * @return retourne l'objet singleton okhttp de la classe
     * @author Matisse Gallouin
     */
    public static okhttp3.OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {
            okHttpClient = new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(new SessionIntercepteur()) // ajout de l'intercepteur de session pour récupérer et intégrer le jsessionID à toutes les requêtes
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) // ajout de logs des différentes requêtes
                    .readTimeout(0, TimeUnit.MILLISECONDS) // Désactive le timeout de lecture pour les websockets
                    .writeTimeout(0, TimeUnit.MILLISECONDS) // Désactive le timeout d'écriture pour les websockets
                    .build();
        }
        return okHttpClient;
    }
}
