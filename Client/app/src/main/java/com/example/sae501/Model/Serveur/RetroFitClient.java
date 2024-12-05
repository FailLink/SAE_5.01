package com.example.sae501.Model.Serveur;

import com.example.sae501.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    //lien vers le serveur
    private static final String urlSpring = "http://" + MainActivity.globalIP + "/";
    //singleton
    private static Retrofit retrofit;

    /**
     * permet de récupérer l'objet retrofit du singleton
     *
     * @return retourne l'objet singleton retrofit de la classe
     * @author Matisse Gallouin
     */
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient() // permet d'envoyer d'autre message que des jsons présent pour testConnexion et les websockets
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlSpring) // set l'url de retrofit
                    .client(OkHttpClientSingleton.getOkHttpClient()) // ajout du client okhttp pour les logs et l'intercepteur
                    .addConverterFactory(GsonConverterFactory.create(gson))  // ajout de gson pour convertir les jsons en objets
                    .build();
        }
        return retrofit;
    }
}
