package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Statut, Long> {
}
