package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.CleCompositeMonstreLieux;
import org.example.sae501serveur.Model.Entity.MonstreLieux;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonstreLieuxRepository extends JpaRepository<MonstreLieux, CleCompositeMonstreLieux> {
}
