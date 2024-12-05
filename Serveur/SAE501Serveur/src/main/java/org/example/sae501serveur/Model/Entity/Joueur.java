package org.example.sae501serveur.Model.Entity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.example.sae501serveur.Model.JsonViewEntity.Views;

import java.net.http.WebSocket;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Joueur")
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.JoueurView.class)
    private Long id;
    @Column(name = "pseudo",unique = true)
    @JsonView(Views.JoueurView.class)
    private String pseudo;
    @Column(name = "adresseMail",unique = true)
    @JsonView(Views.JoueurView.class)
    private String adresseMail;
    @JsonView(Views.JoueurView.class)
    private String mdp;


    @ManyToOne
    @JoinColumn(name = "classe_id")
    @JsonView(Views.JoueurView.class)
    private Classe classe;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Competences_Joueur",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    @JsonView(Views.JoueurView.class)
    private Set<Competence> competences;

    @ManyToMany
    @JoinTable(
            name = "Amis",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "ami_id")
    )
    @JsonView(Views.JoueurView.class)
    private Set<Joueur> joueurs;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonView(Views.JoueurView.class)
    @JsonManagedReference
    private Role role;


    @ManyToMany(mappedBy = "joueurs")
    @JsonView(Views.JoueurView.class)
    @JsonManagedReference
    private Set<Joueur> amis;

    @ManyToMany(mappedBy = "joueurs")
    @JsonView(Views.JoueurView.class)
    @JsonBackReference
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
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return Objects.equals(id, joueur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
