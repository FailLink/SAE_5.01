package com.example.sae501.Model.Serveur;

import androidx.annotation.NonNull;

import com.example.sae501.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SessionIntercepteur implements Interceptor {
    /**
     * permet d'ajouter le jsessionid dans toutes mes requêtes
     *
     * @param chain chaîne de requête vers le serveur
     * @return response avec le jsessionID ajouté
     * @throws IOException
     * @author Matisse Gallouin
     */
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (MainActivity.sessionID != null) {
            request = request.newBuilder()
                    .addHeader("Cookie", "JSESSIONID=" + MainActivity.sessionID)
                    .build();
        }
        Response response = chain.proceed(request);
        return response;
    }
}
