package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "statut")
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int def;
    private int attack;
    private int nbTour;
    private boolean isProvocation;


    @OneToMany(mappedBy = "statut", cascade = CascadeType.ALL)
    private List<Competence> competences;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getNbTour() {
        return nbTour;
    }

    public void setNbTour(int nbTour) {
        this.nbTour = nbTour;
    }

    public boolean isProvocation() {
        return isProvocation;
    }

    public void setProvocation(boolean provocation) {
        isProvocation = provocation;
    }


    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
