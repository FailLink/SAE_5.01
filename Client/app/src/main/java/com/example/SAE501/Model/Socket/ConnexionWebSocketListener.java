package com.example.SAE501.Model.Socket;

import androidx.annotation.NonNull;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ConnexionWebSocketListener extends WebSocketListener {
    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        System.out.println(text);
        super.onMessage(webSocket, text);
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

}
