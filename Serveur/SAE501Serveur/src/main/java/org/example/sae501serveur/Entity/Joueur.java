package org.example.sae501serveur.Entity;
import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Joueur")
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String adresseMail;
    private String mdp;


    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToMany
    @JoinTable(
            name = "Competences_Joueur",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private Set<Competence> competences;

    @ManyToMany
    @JoinTable(
            name = "Amis",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "ami_id")
    )
    private Set<Joueur> joueurs;

    @ManyToMany(mappedBy = "joueurs")
    private Set<Joueur> amis;

    @ManyToMany(mappedBy = "joueurs")
    private Set<Partie> parties;

    public Joueur(Long id, String pseudo, String adresseMail, String mdp,
                  Classe classe, Set<Competence> competences, Set<Joueur> joueurs, Set<Joueur> amis, Set<Partie> parties) {
        this.id = id;
        this.pseudo = pseudo;
        this.adresseMail = adresseMail;
        this.mdp = mdp;
        this.classe = classe;
        this.competences = competences;
        this.joueurs = joueurs;
        this.amis = amis;
        this.parties=parties;
    }

    public Joueur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMdp() {
        return mdp;
    }

    public Set<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Set<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Set<Joueur> getAmis() {
        return amis;
    }

    public void setAmis(Set<Joueur> amis) {
        this.amis = amis;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Set<Partie> getParties() {
        return parties;
    }

    public void setParties(Set<Partie> parties) {
        this.parties = parties;
    }
}
