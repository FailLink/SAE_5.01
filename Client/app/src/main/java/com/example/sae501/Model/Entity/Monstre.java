package com.example.sae501.Model.Entity;


public class Monstre {

    private Long id;

    private String nom;
    private int hp;
    private int attack;
    private int def;
    private Statut statut;
    private Type typeMonstre;

    public Monstre() {
    }

    public Monstre(Long id, String nom, int hp, int attack, int def, Statut statut, Type typeMonstre) {
        this.id = id;
        this.nom = nom;
        this.hp = hp;
        this.attack = attack;
        this.def = def;
        this.statut = statut;
        this.typeMonstre = typeMonstre;
    }

    public Monstre(String nom, int hp, int attack, int def, String monstreResistance, String monstreFaiblesse) {
        this.nom = nom;
        this.hp = hp;
        this.attack = attack;
        this.def = def;

        this.typeMonstre = new Type();

        this.typeMonstre.setResistance(monstreResistance);
        this.typeMonstre.setFaiblesse(monstreFaiblesse);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Type getTypeMonstre() {
        return typeMonstre;
    }

    public void setTypeMonstre(Type typeMonstre) {
        this.typeMonstre = typeMonstre;
    }


    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
