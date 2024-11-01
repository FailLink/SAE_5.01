package com.example.sae501.Model.Entity;

import java.util.Set;

public class Competence {

    private Long id;
    private String nom;
    private String animation;
    private int attack;
    private Statut statut;
    private Type typeCompetence;
    private Set<Joueur> joueurs;

    public Competence() {
    }

    public Competence(Long id, String nom, String animation, int attack,
                      Statut statut, Type typeCompetence, Set<Joueur> joueurs) {
        this.id = id;
        this.nom = nom;
        this.animation = animation;
        this.attack = attack;
        this.statut = statut;
        this.typeCompetence = typeCompetence;
        this.joueurs = joueurs;
    }

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

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
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

    public Type getTypeCompetence() {
        return typeCompetence;
    }

    public void setTypeCompetence(Type typeCompetence) {
        this.typeCompetence = typeCompetence;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
}
