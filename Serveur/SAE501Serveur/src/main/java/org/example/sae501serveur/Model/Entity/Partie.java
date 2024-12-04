package org.example.sae501serveur.Model.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.example.sae501serveur.Model.JsonViewEntity.Views;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "partie")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.PartieView.class)
    private Long id;

    @JsonView(Views.PartieView.class)
    private String temps;
    @JsonView(Views.PartieView.class)
    private boolean isFinsin;
    @JsonView(Views.PartieView.class)
    private int nbPoint;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(Views.PartieView.class)
    @JsonManagedReference
    private Set<Joueur> joueurs;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView(Views.PartieView.class)
    @JsonManagedReference
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

    public void addJoueur(Joueur joueur){
        this.joueurs.add(joueur);
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Set<MonstreLieux> getMonstreLieux() {
        return monstreLieux;
    }

    public void setMonstreLieux(Set<MonstreLieux> monstreLieux) {
        this.monstreLieux = monstreLieux;
    }
    public void addMonstreLieux(MonstreLieux monstreLieux) {
        this.monstreLieux.add(monstreLieux);
        // Assurez-vous que la relation bidirectionnelle est aussi bien gérée
        if(monstreLieux.getPartie()!=(null)){
            if (!monstreLieux.getPartie().contains(this)) {
                monstreLieux.addPartie(this);
            }
        }else{
          monstreLieux.setPartie(new HashSet<>());
          monstreLieux.addPartie(this);
        }
    }
}
