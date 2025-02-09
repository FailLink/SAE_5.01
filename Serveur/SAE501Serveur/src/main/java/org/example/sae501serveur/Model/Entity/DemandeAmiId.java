package org.example.sae501serveur.Model.Entity;

import java.io.Serializable;
import java.util.Objects;

public class DemandeAmiId implements Serializable {
    private Long joueurInviteur;
    private Long joueurInvite;

    public DemandeAmiId() {}

    public DemandeAmiId(Long joueurInviteur, Long joueurInvite) {
        this.joueurInviteur = joueurInviteur;
        this.joueurInvite = joueurInvite;
    }

    public Long getJoueurInviteur() {
        return joueurInviteur;
    }

    public void setJoueurInviteur(Long joueurInviteur) {
        this.joueurInviteur = joueurInviteur;
    }

    public Long getJoueurInvite() {
        return joueurInvite;
    }

    public void setJoueurInvite(Long joueurInvite) {
        this.joueurInvite = joueurInvite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemandeAmiId that = (DemandeAmiId) o;
        return Objects.equals(joueurInviteur, that.joueurInviteur) &&
                Objects.equals(joueurInvite, that.joueurInvite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joueurInviteur, joueurInvite);
    }
}
