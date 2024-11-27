package org.example.sae501serveur.Model.Entity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "partie")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temps;
    private boolean isFinsin;
    private int nbPoint;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Joueur> joueurs;
    @ManyToMany
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
