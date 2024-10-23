package com.example.SAE501.Model.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.security.Provider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class ConnexionWebSocketService extends Service {
    private WebSocket webSocket;
    private OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient=new OkHttpClient();
        Request request = new Request.Builder().url("ws://10.0.2.2:8080/connexionPartie").build();
        webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
