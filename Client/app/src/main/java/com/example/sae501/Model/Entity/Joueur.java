package com.example.sae501.Model.Entity;
import java.util.Objects;
import java.util.Set;


public class Joueur {
    private Long id;

    private String pseudo;
    private String adresseMail;
    private String mdp;

    private Classe classe;
    private Set<Competence> competences;
    private Set<Joueur> joueurs;
    private Set<Joueur> amis;
    private Set<Partie> parties;

    private int hpActuel;

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
        this.hpActuel=this.classe.getHp();
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

    public int getHpActuel() {
        return hpActuel;
    }

    public void setHpActuel(int hpActuel) {
        this.hpActuel = hpActuel;
    }

    public void setParties(Set<Partie> parties) {
        this.parties = parties;
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
