package com.example.sae501;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sae501.databinding.ActivityMapsBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapsActivity extends AppCompatActivity implements MapListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1; // Code de demande de permission
    private MapView mMap;
    private IMapController controller;
    private MyLocationNewOverlay mMyLocationOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.currentActivity=this;
        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load OSM configuration
        Configuration.getInstance().load(
                getApplicationContext(),
                getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        );

        // Initialize MapView
        mMap = binding.osmMV;
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMultiTouchControls(true);

        // Vérifiez les permissions de localisation
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            setupMyLocationOverlay(); // Configurer le suivi de localisation
        }

        // Set initial zoom level
        controller = mMap.getController();
        controller.setZoom(6.0);

        // Add map listener
        mMap.addMapListener(this);
    }

    private void setupMyLocationOverlay() {
        mMyLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mMap);
        mMyLocationOverlay.enableMyLocation();
        mMyLocationOverlay.enableFollowLocation();
        mMyLocationOverlay.setDrawAccuracyEnabled(true);
        mMap.getOverlays().add(mMyLocationOverlay);

        // Centrer la carte sur la localisation
        mMyLocationOverlay.runOnFirstFix(() -> runOnUiThread(() -> {
            GeoPoint location = mMyLocationOverlay.getMyLocation();
            if (location != null) {
                controller.setCenter(location);
                controller.animateTo(location);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        if (mMyLocationOverlay != null) {
            mMyLocationOverlay.enableMyLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMyLocationOverlay != null) {
            mMyLocationOverlay.disableMyLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyLocationOverlay != null) {
            mMyLocationOverlay.disableMyLocation();
            mMap.getOverlays().remove(mMyLocationOverlay);
        }
    }

    @Override
    public boolean onScroll(ScrollEvent event) {
        Log.e("TAG", "onScroll latitude: " + event.getSource().getMapCenter().getLatitude());
        Log.e("TAG", "onScroll longitude: " + event.getSource().getMapCenter().getLongitude());
        return true;
    }

    @Override
    public boolean onZoom(ZoomEvent event) {
        Log.e("TAG", "onZoom zoom level: " + event.getZoomLevel() + " source: " + event.getSource());
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupMyLocationOverlay(); // Configurer le suivi de localisation après avoir obtenu la permission
            }
        }
    }
}
