package com.example.sae501;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;

import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Entity.Lieux;
import com.example.sae501.Model.Entity.Monstre;
import com.example.sae501.Model.Entity.MonstreLieux;
import com.example.sae501.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    private MapView mMap;
    private IMapController controller;
    private MyLocationNewOverlay mMyLocationOverlay;

    // Coordonnées des arènes
    private static final double ARENA1_LATITUDE = 50.279056549072266;
    private static final double ARENA1_LONGITUDE = 3.967270851135254;
    private static final double ARENA2_LATITUDE = 50.2708082;
    private static final double ARENA2_LONGITUDE = 3.9892575;
    private final List<Marker> indicateurRebordArene = new ArrayList<>();
    public static List<Marker> markers = new ArrayList<>();

    public static Marker clickedMarker ;
    public static MapView carte ;

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
        mMap.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMap.setMultiTouchControls(false);  // Désactive les gestes pour zoomer
        mMap.setBuiltInZoomControls(false); // Désactive les boutons de zoom intégrés

        Configuration.getInstance().setCacheMapTileCount((short) 9);  // Définit le cache des tuiles en mémoire
        Configuration.getInstance().setCacheMapTileOvershoot((short) 3);
        mMap.invalidate();//recharge le cache

        // Empêcher l'utilisateur de zoomer ou faire défiler la carte
        mMap.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                addRebordIndicatorArene();
                return true;  // Empêche le scroll
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                return false;  // Empêche le zoom via gestes
            }

        });

        // Initialisation du MyLocationOverlay pour obtenir la position de l'utilisateur
        GpsMyLocationProvider gpsMyLocationProvider=new GpsMyLocationProvider(this);
        gpsMyLocationProvider.setLocationUpdateMinTime(100);
        mMyLocationOverlay = new MyLocationNewOverlay(gpsMyLocationProvider, mMap);
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
                double zoomLevel = 19.0;
                controller.setZoom(zoomLevel);
            }
        }));

        // Récupérer le bouton via le binding et gérer l'événement de clic
        binding.recenterButton.setOnClickListener(v -> recentrerCarte());

        //chargement des infos de la partie dans la maps

        //infos joueurs
        ArrayList<Joueur> listJoueurSansUser=new ArrayList<>();
        for(int i=0;i<MainActivity.joueursPartie.size();i++){
            Joueur joueur=MainActivity.joueursPartie.get(i);
            if(!joueur.equals(MainActivity.joueur)){
                listJoueurSansUser.add(joueur);
            }
        }
        for (int i=0;i<listJoueurSansUser.size();i++){
            Joueur joueur=listJoueurSansUser.get(i);
            String id="player_"+(i+1)+"_health_label";
            TextView textViewPseudo = (TextView) findViewById(getResources().getIdentifier(id,"id",this.getPackageName()));
            textViewPseudo.setText(joueur.getPseudo());

            id="health_bar_player_"+(i+1);
            ProgressBar progressBar=(ProgressBar) findViewById(getResources().getIdentifier(id,"id",this.getPackageName()));
            progressBar.setMax(joueur.getClasse().getHp());
            progressBar.setProgress(joueur.getHpActuel());

            id="health_text_player_"+(i+1);
            TextView textViewHp =(TextView) findViewById(getResources().getIdentifier(id,"id",this.getPackageName()));
            textViewHp.setText(joueur.getHpActuel()+"/"+joueur.getClasse().getHp());
        }
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.health_bar_player);
        progressBar.setMax(MainActivity.joueur.getClasse().getHp());
        progressBar.setProgress(MainActivity.joueur.getHpActuel());

        TextView textViewHp =(TextView) findViewById(R.id.health_text_player);
        textViewHp.setText(MainActivity.joueur.getHpActuel()+"/"+MainActivity.joueur.getClasse().getHp());

        //info lieux
        for(MonstreLieux monstreLieux:MainActivity.partie.getMonstreLieux()){
            Lieux lieux=monstreLieux.getLieux();
            addArenaMarker(lieux.getLatitude(),lieux.getLongitude(),monstreLieux.getMonstre().getNom(), monstreLieux.getMonstre());
        }
    }

    // Méthode pour ajouter des marqueurs pour les arènes
    private void addArenaMarker(double latitude, double longitude, String title, Monstre monstre) {
        GeoPoint arenaLocation = new GeoPoint(latitude, longitude);
        Marker arenaMarker = new Marker(mMap);
        arenaMarker.setPosition(arenaLocation);
        arenaMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        arenaMarker.setTitle(title);

        Drawable icon = getResources().getDrawable(R.drawable.icone_boss);
        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        int width = 75;
        int height = 75;
        Bitmap iconeRedimensionne = Bitmap.createScaledBitmap(bitmap, width, height, false);
        arenaMarker.setIcon(new BitmapDrawable(getResources(), iconeRedimensionne));// Définit le titre de l'arène

        // Listener pour gérer le clic sur les marqueurs
        arenaMarker.setOnMarkerClickListener((marker, mapView) -> {
            clickedMarker = marker;
            carte = mapView ;

            GeoPoint geoPointJoueur= mMyLocationOverlay.getMyLocation();
            if(distance(arenaLocation.getLatitude(),arenaLocation.getLongitude()
                    ,geoPointJoueur.getLatitude(),geoPointJoueur.getLongitude())<=50) {
                showBattleConfirmationDialog(title,monstre);
            }else{
                Toast.makeText(this,"vous êtes trop loin",Toast.LENGTH_SHORT).show();
            }
            return true;  // Empêche la propagation de l'événement
        });

        mMap.getOverlays().add(arenaMarker);
        markers.add(arenaMarker);
    }

    // Méthode pour afficher un dialogue de confirmation avant de commencer un combat
    private void showBattleConfirmationDialog(String arenaName, Monstre monstre) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Combat contre " + arenaName)
                .setMessage("Voulez-vous combattre contre "+arenaName+" ?")
                .setPositiveButton("Oui", (dialog, id) -> {
                    startCombat(monstre);
                })
                .setNegativeButton("Non", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    // Méthode pour démarrer le combat (exemple, peut être personnalisé selon votre logique)
    private void startCombat(Monstre monstre) {
        // Logique pour démarrer le combat (exemple avec une nouvelle activité)
        // Vous pouvez remplacer cela par la logique de combat réelle de votre application

        MainActivity.joueur.setHpActuel(MainActivity.joueur.getClasse().getHp());
        Intent intent = new Intent(this, Combat.class);
        intent.putExtra("monstreAttaque", monstre.getAttack());
        intent.putExtra("monstreNom", monstre.getNom());
        intent.putExtra("monstreHp", monstre.getHp());
        intent.putExtra("monstreDef", monstre.getDef());
        intent.putExtra("monstreResistance", monstre.getTypeMonstre().getResistance());
        intent.putExtra("monstreFaiblesse", monstre.getTypeMonstre().getFaiblesse());
        startActivity(intent);

    }

    // Méthode pour recentrer la carte sur la position actuelle de l'utilisateur
    private void recentrerCarte() {
        GeoPoint location = mMyLocationOverlay.getMyLocation();
        if (location != null) {
            controller.animateTo(location);  // Recentre la carte sur la position actuelle
        }
    }
    public double distance(double latitudePoint1,double longitudePoint1,double latitudePoint2,double longitudePoint2){
        return 6378137*Math.acos(Math.sin(Math.toRadians(latitudePoint1))*
                Math.sin(Math.toRadians(latitudePoint2))+Math.cos(Math.toRadians(latitudePoint1))*
                Math.cos(Math.toRadians(latitudePoint2))*Math.cos(Math.toRadians(longitudePoint1)-Math.toRadians(longitudePoint2)));
    }


    private void addRebordIndicatorArene() {

        for (Marker marker : indicateurRebordArene) {
            mMap.getOverlays().remove(marker);
        }
        indicateurRebordArene.clear();

        BoundingBox boundingBox = mMap.getBoundingBox();
        for (MonstreLieux monstreLieux : MainActivity.partie.getMonstreLieux()) {
            Lieux lieux = monstreLieux.getLieux();
            GeoPoint arene = new GeoPoint(lieux.getLatitude(), lieux.getLongitude());

            if (!boundingBox.contains(arene)) {
                GeoPoint borderPoint = calculatePointRebord(boundingBox, arene);
                Marker borderMarker = addRebordArene(borderPoint, monstreLieux.getMonstre().getNom());
                indicateurRebordArene.add(borderMarker);
            }
        }
        mMap.invalidate();
    }

    private GeoPoint calculatePointRebord(BoundingBox boundingBox, GeoPoint arene) {
        double centerLat = boundingBox.getCenterLatitude();
        double centerLon = boundingBox.getCenterLongitude();
        double angle = Math.atan2(arene.getLatitude() - centerLat, arene.getLongitude() - centerLon);


        double latitude, longitude;
        if (Math.abs(Math.sin(angle))>Math.abs(Math.cos(angle))) {
            if(angle>0){
                latitude =boundingBox.getLatNorth();
            }else{
                latitude =boundingBox.getLatSouth();
            }
            longitude = centerLon + (arene.getLongitude() - centerLon) * (latitude - centerLat) / (arene.getLatitude() - centerLat);
        } else {
            if(angle>Math.PI/2 || angle<-Math.PI/2){
                longitude =boundingBox.getLonWest();
            }else{
                longitude =boundingBox.getLonEast();
            }
            latitude = centerLat + (arene.getLatitude() - centerLat) * (longitude - centerLon) / (arene.getLongitude() - centerLon);
        }
        return new GeoPoint(latitude, longitude);
    }

    // Ajouter un marqueur pour indiquer un lieu hors écran
    private Marker addRebordArene(GeoPoint location, String title) {
        Marker areneMarker = new Marker(mMap);
        areneMarker.setPosition(location);
        areneMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        areneMarker.setTitle(title);

        Drawable icon = getResources().getDrawable(R.drawable.icone_boss);
        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        int width = 50;
        int height = 50;
        Bitmap iconeRedimensionne = Bitmap.createScaledBitmap(bitmap, width, height, false);
        areneMarker.setIcon(new BitmapDrawable(getResources(), iconeRedimensionne));

        mMap.getOverlays().add(areneMarker);
        return areneMarker; // Retourne le marqueur ajouté
    }


}
