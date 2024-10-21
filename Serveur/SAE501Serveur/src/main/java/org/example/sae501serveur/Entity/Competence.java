package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "competence")
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String animation;
    private int attack;


    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type typeCompetence;

    @ManyToMany (mappedBy = "competences")
    private Set<Joueur> joueurs;

    public Competence() {
    }

    public Competence(Long id, String nom, String animation, int attack,
                      Statut statut, Type typeCompetence, Set<Joueur> joueurs) {
        this.id = id;
        this.nom = nom;
        this.animation = animation;
        this.attack = attack;
        this.statut = statut;
        this.typeCompetence = typeCompetence;
        this.joueurs = joueurs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Type getTypeCompetence() {
        return typeCompetence;
    }

    public void setTypeCompetence(Type typeCompetence) {
        this.typeCompetence = typeCompetence;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
}
