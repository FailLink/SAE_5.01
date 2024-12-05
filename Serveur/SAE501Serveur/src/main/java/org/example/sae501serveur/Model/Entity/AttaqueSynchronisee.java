package org.example.sae501serveur.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attaqueSynchronisee")
public class AttaqueSynchronisee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int attack;

    @ManyToOne
    @JoinColumn(name = "classe1_id")
    private Classe classe1;
    @ManyToOne
    @JoinColumn(name = "classe2_id")
    private Classe classe2;

    @ManyToOne
    @JoinColumn(name = "statut")
    private Statut statut;

    public AttaqueSynchronisee(Long id, String nom, Classe classe1, Classe classe2, int attack, Statut statut) {
        this.id = id;
        this.nom = nom;
        this.classe1 = classe1;
        this.classe2 = classe2;
        this.attack = attack;
        this.statut = statut;
    }

    public AttaqueSynchronisee() {
    }

    public Long getId() {
        return id;
    }

    public Classe getClasse1() {
        return classe1;
    }

    public void setClasse1(Classe classe1) {
        this.classe1 = classe1;
    }

    public Classe getClasse2() {
        return classe2;
    }

    public void setClasse2(Classe classe2) {
        this.classe2 = classe2;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classe getClasse() {
        return classe1;
    }

    public void setClasse(Classe classe) {
        this.classe1 = classe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
