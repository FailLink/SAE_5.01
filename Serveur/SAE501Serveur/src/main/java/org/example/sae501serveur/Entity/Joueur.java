package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "joueur")
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String adresseMail;
    private String mdp;


    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;  // Plusieurs joueurs peuvent appartenir à une même classe
}
