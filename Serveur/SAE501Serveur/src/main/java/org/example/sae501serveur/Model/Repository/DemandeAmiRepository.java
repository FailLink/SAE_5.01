package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.DemandeAmi;
import org.example.sae501serveur.Model.Entity.DemandeAmiId;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemandeAmiRepository extends JpaRepository<DemandeAmi, DemandeAmiId> {
    List<DemandeAmi> findAllByJoueurInvite(Joueur joueurInvite);
    List<DemandeAmi> findAllByJoueurInviteur(Joueur joueurInvitant);
}
