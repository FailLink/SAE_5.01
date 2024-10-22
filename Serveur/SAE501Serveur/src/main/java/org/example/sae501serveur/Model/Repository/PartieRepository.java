package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartieRepository extends JpaRepository<Partie, Long> {
    @Query("select p from Partie p join p.joueurs j where p.isFinsin=false and j.id=:idJoueur")
    public Partie getPartieNotFinishedByIdJoueur(@Param("idJoueur") Long idJoueur);

    @Query("select p from Partie p where p.isFinsin=false")
    public List<Partie> getPartieNotFinished();
}
