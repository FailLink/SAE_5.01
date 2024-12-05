package org.example.sae501serveur.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.example.sae501serveur.Model.JsonViewEntity.Views;

import java.util.List;

@Entity
@Table(name = "type")

public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // Exemple: eau

    @JoinColumn(name = "resistance")
    private String resistance; // Exemple: feu bah oui mais non

    @JoinColumn(name = "faiblesse")
    private String faiblesse; // Exemple: terre

    @JsonBackReference
    @OneToMany(mappedBy = "typeCompetence")
    private List<Competence> competences;

    public Type(Long id, String nom, String resistance, String faiblesse, List<Competence> competences) {
        this.id = id;
        this.nom = nom;
        this.resistance = resistance;
        this.faiblesse = faiblesse;
        this.competences = competences;
    }

    public Type() {
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

    public String getResistance() {
        return resistance;
    }

    public void setResistance(String resistance) {
        this.resistance = resistance;
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
