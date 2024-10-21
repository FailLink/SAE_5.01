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
    @OneToOne
    @JoinColumn(name = "resistance")
    private Type resistance; // Exemple: feu bah oui mais non
    @OneToOne
    @JoinColumn(name = "faiblesse")
    private Type faiblesse; // Exemple: terre


    @OneToMany(mappedBy = "typeCompetence")
    private List<Competence> competences;

    public Type(Long id, String nom, Type resistance, Type faiblesse, List<Competence> competences) {
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

    public Type getResistance() {
        return resistance;
    }

    public void setResistance(Type resistance) {
        this.resistance = resistance;
    }

    public Type getFaiblesse() {
        return faiblesse;
    }

    public void setFaiblesse(Type faiblesse) {
        this.faiblesse = faiblesse;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
