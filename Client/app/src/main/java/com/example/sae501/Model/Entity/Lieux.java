package com.example.sae501.Model.Entity;


import java.util.Map;

public class Lieux {
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
}
