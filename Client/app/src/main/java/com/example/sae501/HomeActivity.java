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

import com.example.sae501.Model.Serveur.OkHttpClientSingleton;
import com.example.sae501.Model.Socket.ConnexionWebSocketListener;
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

    /**
     * fonction définissant le comportement au click du bouton créer partie
     *
     * @param view la view
     * @author Matisse Gallouin
     */
    public void onClickJouer(View view) {
        checkPerm();
    }

    /**
     * vérification des permissions de localisation donné par le joueur pour récupérer ses coordonnées<br>
     * dans le cas où celui-ci n'a pas donné les permissions de localisation il verra une popup les demandants<br>
     * dans l'autre cas le message de création de partie est envoyé
     *
     * @author Matisse Gallouin
     */
    public void checkPerm() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);
        } else {
            envoieMessage();
        }
    }

    /**
     * fonction définissant le comportement au click du bouton rejoindre partie
     *
     * @param view la view
     * @author Matisse Gallouin
     */
    public void onClickRejoindrePartie(View view) {
        new RejoindrePartieFragment().show(getSupportFragmentManager(), "rejoindrePartie");
    }

    /**
     * fonction définissant le comportement des résultats de demande de permissions
     *
     * @param requestCode  The request code passed in requestPermission
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     * @author Matisse Gallouin
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                envoieMessage();
            } else {
                Toast.makeText(this, "Vous ne pourrez pas jouer sans ces permissions", Toast.LENGTH_SHORT).show();
                checkPerm();
            }
        }
    }

    /**
     * fonction permettant d'envoyer un message de création de partie au serveur
     * avec notre localisation pour permettre la génération d'une partie
     *
     * @author Matisse Gallouin
     */
    @SuppressLint("MissingPermission")
    public void envoieMessage() {
        OkHttpClient okHttpClient = OkHttpClientSingleton.getOkHttpClient();
        Request request = new Request.Builder().url("ws://" + MainActivity.globalIP + "/connexionPartie").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new ConnexionWebSocketListener());

        FusedLocationProviderClient providerLocation = LocationServices.getFusedLocationProviderClient(this);
        providerLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                String jsonMessage = "{ \"type\": \"creationPartie\"," +
                        "\"positionLongitude\":" + location.getLongitude() + "," +
                        "\"positionLatitude\":" + location.getLatitude() + "}";
                webSocket.send(jsonMessage);
            }
        });
    }
}
