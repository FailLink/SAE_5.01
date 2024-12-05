package com.example.sae501;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.Competence;
import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
import com.example.sae501.R;
import com.example.sae501.View.RejoindrePartie.RejoindrePartieFragment;
import com.example.sae501.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MainActivity.currentActivity = this;
    }

    public void onClickJouer(View view) {
        checkPerm();
    }
    public void checkPerm(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);
        }else{

            envoieMessage();
        }
    }
    public void onClickRejoindrePartie(View view){
        new RejoindrePartieFragment().show(getSupportFragmentManager(),"rejoindrePartie");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, continuez
                envoieMessage();
            } else {
                Toast.makeText(this,"Vous ne pourrez pas jouer sans ces permissions",Toast.LENGTH_SHORT).show();
                checkPerm();
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void envoieMessage(){
        OkHttpClient okHttpClient = OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://" + MainActivity.globalIP + "/connexionPartie").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());

        FusedLocationProviderClient providerLocation = LocationServices.getFusedLocationProviderClient(this);
        providerLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                String jsonMessage = "{ \"type\": \"creationPartie\"," +
                        "\"positionLongitude\":" + location.getLongitude() + "," +
                        "\"positionLatitude\":"+location.getLatitude()+"}";

                webSocket.send(jsonMessage);
            }
        });
    }
}
