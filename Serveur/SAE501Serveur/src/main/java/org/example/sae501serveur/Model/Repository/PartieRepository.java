package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartieRepository extends JpaRepository<Partie, Long> {
}
