package org.example.sae501serveur.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int hp;
    private int attack;
    private int def;

    @OneToMany(mappedBy = "classe1")
    private List<AttaqueSynchronisee> attaquesSynchronisees1;

    @OneToMany(mappedBy = "classe2")
    private List<AttaqueSynchronisee> attaquesSynchronisees2;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @OneToMany(mappedBy = "classe")
    private List<Joueur> joueurs;


    public Classe(Long id, String nom, int hp, int attack, int def, List<AttaqueSynchronisee> attaquesSynchronisees1,
                  List<AttaqueSynchronisee> attaquesSynchronisees2, List<Joueur> joueurs, Type type) {
        this.id = id;
        this.nom = nom;
        this.hp = hp;
        this.attack = attack;
        this.def = def;
        this.attaquesSynchronisees1 = attaquesSynchronisees1;
        this.attaquesSynchronisees2 = attaquesSynchronisees2;
        this.joueurs = joueurs;
        this.type = type;
    }

    public Classe() {
    }

    public List<AttaqueSynchronisee> getAttaquesSynchronisees1() {
        return attaquesSynchronisees1;
    }

    public void setAttaquesSynchronisees1(List<AttaqueSynchronisee> attaquesSynchronisees1) {
        this.attaquesSynchronisees1 = attaquesSynchronisees1;
    }

    public List<AttaqueSynchronisee> getAttaquesSynchronisees2() {
        return attaquesSynchronisees2;
    }

    public void setAttaquesSynchronisees2(List<AttaqueSynchronisee> attaquesSynchronisees2) {
        this.attaquesSynchronisees2 = attaquesSynchronisees2;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
