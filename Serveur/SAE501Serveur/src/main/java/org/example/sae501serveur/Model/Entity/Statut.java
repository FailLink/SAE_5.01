package org.example.sae501serveur.Model.Entity;
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


    @OneToMany(mappedBy = "statut")
    private List<Competence> competences;
    @OneToMany(mappedBy = "statut")
    private List<AttaqueSynchronisee> attaqueSynchronisee;

    public Statut() {
    }

    public Statut(Long id, int def, int attack, int nbTour,
                  boolean isProvocation, List<Competence> competences, List<AttaqueSynchronisee> attaqueSynchronisee) {
        this.id = id;
        this.def = def;
        this.attack = attack;
        this.nbTour = nbTour;
        this.isProvocation = isProvocation;
        this.competences = competences;
        this.attaqueSynchronisee = attaqueSynchronisee;
    }

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

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public void setAttaqueSynchronisee(List<AttaqueSynchronisee> attaqueSynchronisee) {
        this.attaqueSynchronisee = attaqueSynchronisee;
    }
}
