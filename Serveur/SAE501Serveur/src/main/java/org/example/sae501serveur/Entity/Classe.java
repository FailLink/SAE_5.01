package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int hp;
    private int attack;
    private int def;
    private int attackSpe;

    @OneToMany(mappedBy = "classe")
    private List<Competence> competences;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<AttaqueSynchronisee> attaquesSynchronisees; // Liste des attaques synchronisées

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<Joueur> joueurs;  // Une classe peut avoir plusieurs joueurs

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAttackSpe() {
        return attackSpe;
    }

    public void setAttackSpe(int attackSpe) {
        this.attackSpe = attackSpe;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<AttaqueSynchronisee> getAttaquesSynchronisees() {
        return attaquesSynchronisees;
    }

    public void setAttaquesSynchronisees(List<AttaqueSynchronisee> attaquesSynchronisees) {
        this.attaquesSynchronisees = attaquesSynchronisees;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
}
