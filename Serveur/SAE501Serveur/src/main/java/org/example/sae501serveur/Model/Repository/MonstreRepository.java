package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Monstre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonstreRepository extends JpaRepository<Monstre, Long> {
}
