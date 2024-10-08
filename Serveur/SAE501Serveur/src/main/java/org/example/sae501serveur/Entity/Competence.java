package org.example.sae501serveur.Entity;
import jakarta.persistence.*;
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
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "statut_id")  // Chaque compétence peut affecter un statut
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type typeCompetence;

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

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
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
}
