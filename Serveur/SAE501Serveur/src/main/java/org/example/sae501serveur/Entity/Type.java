package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // Exemple: eau
    private String resistance; // Exemple: feu
    private String faiblesse; // Exemple: terre


    @OneToMany(mappedBy = "typeMonstre")
    private List<Monstre> monstres;

    @OneToMany(mappedBy = "typeCompetence")
    private List<Competence> competences;

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

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public void setMonstres(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    public String getFaiblesse() {
        return faiblesse;
    }

    public void setFaiblesse(String faiblesse) {
        this.faiblesse = faiblesse;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
