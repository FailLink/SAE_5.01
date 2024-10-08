package org.example.sae501serveur.Repository;

import org.example.sae501serveur.Entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Statut, Long> {
}
