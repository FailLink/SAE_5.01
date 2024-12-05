package org.example.sae501serveur.Model.Game;

public class CombatResult {
    private String joueurId;
    private int degats;
    private int pdv;
    private int monstrePdv;

    public CombatResult(String joueurId, int degats, int pdv, int monstrePdv) {
        this.joueurId = joueurId;
        this.degats = degats;
        this.pdv = pdv;
        this.monstrePdv = monstrePdv;

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

    public int getPdv() {
        return pdv;
    }

    public void setPdv(int pdv) {
        this.pdv = pdv;
    }

}
