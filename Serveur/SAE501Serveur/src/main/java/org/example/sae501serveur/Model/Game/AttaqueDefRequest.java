package org.example.sae501serveur.Model.Game;

import org.example.sae501serveur.Model.Entity.Monstre;

public class AttaqueDefRequest {
    private String joueurId;  // Identifiant du joueur
    private int degats;       // Dégâts infligés par l'attaque
    private int def;
    private int pdv; // Dégâts défensifs ou autre logique que vous voulez ajouter
    private Monstre monstre;
    private String competenceNom;



    private int tourCompetence;



    // Constructeur
    public AttaqueDefRequest(String joueurId, int degats, int def,Monstre monstre,String competenceNom, int tourCompetence ) {
        this.joueurId = joueurId;
        this.degats = degats;
        this.def = def;
        this.monstre = monstre;
        this.competenceNom = competenceNom;
        this.tourCompetence = tourCompetence;
    }

    // Getters et Setters
    public String getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(String joueurId) {
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


    public Monstre getMonstre() {
        return monstre;
    }

    public void setMonstre(Monstre monstre) {
        this.monstre = monstre;
    }

    public String getCompetenceNom() {
        return competenceNom;
    }

    public void setCompetenceNom(String competenceNom) {
        this.competenceNom = competenceNom;
    }

    public int getTour() {
        return tourCompetence;
    }

    public void setTour(int tour) {
        this.tourCompetence = tour;
    }



    public String toString() {
        return "AttaqueDefRequest{" +
                "joueurId='" + joueurId + '\'' +
                ", degats=" + degats +
                ", def=" + def +
                ", pdv=" + pdv +
                ", monstre=" + monstre +
                ", competenceNom=" + competenceNom +
                ", tourCompetence=" + tourCompetence +
                '}';
    }
}

