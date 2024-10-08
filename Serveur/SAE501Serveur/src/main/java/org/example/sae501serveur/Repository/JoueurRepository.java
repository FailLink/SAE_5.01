package org.example.sae501serveur.Repository;

import org.example.sae501serveur.Entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
}
