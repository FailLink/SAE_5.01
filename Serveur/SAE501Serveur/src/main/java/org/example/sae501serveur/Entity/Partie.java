package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "partie")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temps;
    private boolean isFinsin;
    private int nbPoint;

    @ManyToMany
    private List<Joueur> joueurs;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "partie_id")
    private List<Monstre> monstres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public boolean isFinsin() {
        return isFinsin;
    }

    public void setFinsin(boolean finsin) {
        isFinsin = finsin;
    }

    public int getNbPoint() {
        return nbPoint;
    }

    public void setNbPoint(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    public List<Joueur> getJoueur() {
        return joueurs;
    }

    public void setJoueur(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstre> monstres) {
        this.monstres = monstres;
    }
}
