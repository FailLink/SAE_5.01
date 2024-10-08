package org.example.sae501serveur.Repository;

import org.example.sae501serveur.Entity.Monstre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonstreRepository extends JpaRepository<Monstre, Long> {
}
