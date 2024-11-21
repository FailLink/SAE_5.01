package com.example.sae_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import com.example.sae_application.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity {

    private MapView mMap;
    private IMapController controller;
    private MyLocationNewOverlay mMyLocationOverlay;

    // Coordonnées des arènes
    private static final double ARENA1_LATITUDE = 50.279056549072266;
    private static final double ARENA1_LONGITUDE = 3.967270851135254;
    private static final double ARENA2_LATITUDE = 50.2708082;
    private static final double ARENA2_LONGITUDE = 3.9892575;
    private static final double ARENA3_LATITUDE = 50.31313875559269;
    private static final double ARENA3_LONGITUDE = 4.009256872461906;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Chargement de la configuration OSM
        Configuration.getInstance().load(
                getApplicationContext(),
                getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        );

        // Initialisation de la MapView
        mMap = binding.osmMV;
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMultiTouchControls(false);  // Désactive les gestes pour zoomer
        mMap.setBuiltInZoomControls(false); // Désactive les boutons de zoom intégrés

        // Empêcher l'utilisateur de zoomer ou faire défiler la carte
        mMap.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                return false;  // Empêche le scroll
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                return false;  // Empêche le zoom via gestes
            }
        });

        // Initialisation du MyLocationOverlay pour obtenir la position de l'utilisateur
        mMyLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mMap);
        mMyLocationOverlay.enableMyLocation();
        mMyLocationOverlay.enableFollowLocation();
        mMyLocationOverlay.setDrawAccuracyEnabled(true);
        mMap.getOverlays().add(mMyLocationOverlay);

        // Récupérer la position de l'utilisateur et centrer la carte sur cette position
        controller = mMap.getController();
        mMyLocationOverlay.runOnFirstFix(() -> runOnUiThread(() -> {
            GeoPoint location = mMyLocationOverlay.getMyLocation();
            if (location != null) {
                controller.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
                controller.animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()));

                // Applique un zoom fixe
                double zoomLevel = 21.0;
                controller.setZoom(zoomLevel);
            }
        }));

        // Ajouter des marqueurs pour les arènes
        addArenaMarker(ARENA1_LATITUDE, ARENA1_LONGITUDE, "Lycée Pierre Forest");
        addArenaMarker(ARENA2_LATITUDE, ARENA2_LONGITUDE, "Deuxième Arène");
        addArenaMarker(ARENA3_LATITUDE, ARENA3_LONGITUDE, "Troisième Arène");

        // Récupérer le bouton via le binding et gérer l'événement de clic
        binding.recenterButton.setOnClickListener(v -> recentrerCarte());
    }

    // Méthode pour ajouter des marqueurs pour les arènes
    private void addArenaMarker(double latitude, double longitude, String title) {
        GeoPoint arenaLocation = new GeoPoint(latitude, longitude);
        Marker arenaMarker = new Marker(mMap);
        arenaMarker.setPosition(arenaLocation);
        arenaMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        arenaMarker.setTitle(title);  // Définit le titre de l'arène

        // Listener pour gérer le clic sur les marqueurs
        arenaMarker.setOnMarkerClickListener((marker, mapView) -> {
            showBattleConfirmationDialog(title);
            return true;  // Empêche la propagation de l'événement
        });

        mMap.getOverlays().add(arenaMarker);
    }

    // Méthode pour afficher un dialogue de confirmation avant de commencer un combat
    private void showBattleConfirmationDialog(String arenaName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Combat dans " + arenaName)
                .setMessage("Voulez-vous combattre dans cette arène ?")
                .setPositiveButton("Oui", (dialog, id) -> {
                    startCombat(); // Appelle la méthode qui démarre le combat
                })
                .setNegativeButton("Non", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    // Méthode pour démarrer le combat (exemple, peut être personnalisé selon votre logique)
    private void startCombat() {
        // Logique pour démarrer le combat (exemple avec une nouvelle activité)
        // Vous pouvez remplacer cela par la logique de combat réelle de votre application
        Intent intent = new Intent(this, CombatActivity.class);
        startActivity(intent);
    }

    // Méthode pour recentrer la carte sur la position actuelle de l'utilisateur
    private void recentrerCarte() {
        GeoPoint location = mMyLocationOverlay.getMyLocation();
        if (location != null) {
            controller.animateTo(location);  // Recentre la carte sur la position actuelle
        }
    }
}
