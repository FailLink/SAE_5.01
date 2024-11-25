package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Classe;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    Optional<Joueur> findByPseudo(String pseudo);
    Optional<Joueur> findByAdresseMail(String adresseMail);
    @Query("UPDATE Joueur j set j.classe=:classe where j.id=:id")
    int setJoueurClasse(@Param("id") Long id,@Param("classe") Classe classe);

}
