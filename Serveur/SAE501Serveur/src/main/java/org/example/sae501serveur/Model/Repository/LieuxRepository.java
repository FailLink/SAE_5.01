package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.Lieux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LieuxRepository extends JpaRepository<Lieux, Long> {

    @Query("SELECT l FROM Lieux l WHERE (6371 * acos(" +
            "sin(radians(:latitude)) * sin(radians(l.latitude)) " +
            "+ cos(radians(:latitude)) * cos(radians(l.latitude)) * cos(radians(:longitude)-radians(l.longitude)))) " +
            "<=3")
    List<Lieux> getLieuxByPlayerPosition(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude
    );
}
