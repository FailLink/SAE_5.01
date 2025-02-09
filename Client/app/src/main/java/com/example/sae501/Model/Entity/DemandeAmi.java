package com.example.sae501.Model.Entity;
import java.time.LocalDateTime;


public class DemandeAmi {
    private Joueur joueurInviteur;
    private Joueur joueurInvite;
    private LocalDateTime dateCreation;

    public DemandeAmi() {}

    public DemandeAmi(Joueur joueurInviteur, Joueur joueurInvite, LocalDateTime dateCreation) {
        this.joueurInviteur = joueurInviteur;
        this.joueurInvite = joueurInvite;
        this.dateCreation = dateCreation;
    }

    public Joueur getJoueurInviteur() {
        return joueurInviteur;
    }

    public void setJoueurInviteur(Joueur joueurInviteur) {
        this.joueurInviteur = joueurInviteur;
    }

    public Joueur getJoueurInvite() {
        return joueurInvite;
    }

    public void setJoueurInvite(Joueur joueurInvite) {
        this.joueurInvite = joueurInvite;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
