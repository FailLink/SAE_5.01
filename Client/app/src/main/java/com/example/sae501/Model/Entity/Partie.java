package com.example.sae501.Model.Entity;
import java.util.Set;


public class Partie {
    private Long id;
    private String temps;
    private boolean isFinsin;
    private int nbPoint;
    private Set<Joueur> joueurs;
    private Set<MonstreLieux> monstreLieux;

    public Partie() {
    }

    public Partie(Long id, String temps, boolean isFinsin, int nbPoint, Set<Joueur> joueurs, Set<MonstreLieux> monstreLieux) {
        this.id = id;
        this.temps = temps;
        this.isFinsin = isFinsin;
        this.nbPoint = nbPoint;
        this.joueurs = joueurs;
        this.monstreLieux = monstreLieux;
    }

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

    public Set<Joueur> getJoueur() {
        return joueurs;
    }

    public void setJoueur(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

}
