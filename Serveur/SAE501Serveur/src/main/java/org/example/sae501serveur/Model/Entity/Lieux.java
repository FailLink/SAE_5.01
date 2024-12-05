package org.example.sae501serveur.Model.Entity;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "lieux")
public class Lieux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float longitude;
    private float latitude;

    public Lieux() {
    }

    public Lieux(Long id, float longitude, float latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * permet de calculer la distance entre deux lieux à l'aide de leur coordonnées polaires
     * @param lieux deuxième lieux avec qui calculer la distance
     * @return distance entre les deux lieux
     */
    public double distance(Lieux lieux){
        return 6378137*Math.acos(Math.sin(Math.toRadians(this.latitude))*
                Math.sin(Math.toRadians(lieux.latitude))+Math.cos(Math.toRadians(this.latitude))*
                Math.cos(Math.toRadians(lieux.latitude))*Math.cos(Math.toRadians(this.longitude)-Math.toRadians(lieux.longitude)));
    }
}
