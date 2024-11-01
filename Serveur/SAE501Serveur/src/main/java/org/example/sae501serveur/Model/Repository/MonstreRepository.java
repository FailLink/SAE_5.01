package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Monstre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonstreRepository extends JpaRepository<Monstre, Long> {
    @Query(value = "SELECT * FROM monstre", nativeQuery = true)
    List<Object> findAllMonstreHP();
}
