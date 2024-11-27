package com.example.sae501.View.RejoindrePartie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class RejoindrePartieFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle saveInstance){
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.rejoindre_partie_dialog,null);

        EditText editText=view.findViewById(R.id.idPartieRejoindre);
        Button button=view.findViewById(R.id.rejoindrePartie);
        Button buttonExit=view.findViewById(R.id.quitterRejoindrePartie);

        button.setOnClickListener(v->{
            this.dismiss();
            OkHttpClient okHttpClient = OkHttpClientSingleton.getOkHttpClient();
            Request request = new Request.Builder().url("ws://"+ MainActivity.globalIP +"/connexionPartie").build();
            WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());
            String jsonMessage = "{ \"type\": \"connexionPartie\", \"idPartie\" : \""+editText.getText()+"\" }";
            System.out.println("envoieMessage");
            webSocket.send(jsonMessage);
        });
        buttonExit.setOnClickListener(v->{this.dismiss();});
        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }
}
