package com.example.sae501.Model.Game;

import com.example.sae501.Model.Entity.Monstre;

public class AttaqueDefRequest {
    private Long joueurId;  // Identifiant du joueur
    private int degats;       // Dégâts infligés par l'attaque
    private int def;
    private int pdv; // Dégâts défensifs ou autre logique que vous voulez ajouter
    private Monstre monstre;

    private String competenceNom;


    private int tour ;

    // Constructeur
    public AttaqueDefRequest(Long joueurId, int degats, int def, int pdv, Monstre monstre, String competenceNom, int tour) {
        this.joueurId = joueurId;
        this.degats = degats;
        this.def = def;
        this.pdv = pdv;
        this.monstre = monstre;
        this.competenceNom = competenceNom;
        this.tour = tour;
    }

    // Getters et Setters
    public long getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(long joueurId) {
        this.joueurId = joueurId;
    }

    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getPdv() {
        return pdv;
    }

    public void setPdv(int pdv) {
        this.pdv = pdv;
    }

    public String getCompetenceNom() {
        return competenceNom;
    }

    public void setCompetenceNom(String competenceNom) {
        this.competenceNom = competenceNom;
    }

    public Monstre getMonstre() {
        return monstre;
    }

    public void setMonstre(Monstre monstre) {
        this.monstre = monstre;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }


}
