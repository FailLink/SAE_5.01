package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByNom(String nom);
}
