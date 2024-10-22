package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
}
