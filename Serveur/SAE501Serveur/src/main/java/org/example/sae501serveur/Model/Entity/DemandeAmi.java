package org.example.sae501serveur.Model.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "demande_ami")
@IdClass(DemandeAmiId.class)
public class DemandeAmi {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_joueur_inviteur")  // Clé étrangère vers le joueur invitant
    private Joueur joueurInviteur;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_joueur_invite")  // Clé étrangère vers le joueur invité
    private Joueur joueurInvite;

    @Column(name = "date_creation")
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
