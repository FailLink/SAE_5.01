package org.example.sae501serveur.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lieux")
public class Lieux {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private float x;
    private float y;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "lieux_monstre",
            joinColumns = @JoinColumn(name = "lieux_id"),
            inverseJoinColumns = @JoinColumn(name = "monstre_id")
    )
    private List<Monstre> monstres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstre> monstres) {
        this.monstres = monstres;
    }
}
