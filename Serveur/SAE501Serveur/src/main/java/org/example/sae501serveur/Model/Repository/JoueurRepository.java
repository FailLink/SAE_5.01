package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    Optional<Joueur> findByPseudo(String pseudo);
    Optional<Joueur> findByAdresseMail(String adresseMail);

}
