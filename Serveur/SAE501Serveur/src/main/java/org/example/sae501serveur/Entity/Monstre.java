package org.example.sae501serveur.Entity;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "monstre")
public class Monstre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int hp;
    private int attack;
    private int def;



    @ManyToMany(mappedBy = "monstres")
    private List<Lieux> lieux;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statut_id")  // Clé étrangère vers le statut
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "type_id") // Clé étrangère vers Type
    private Type typeMonstre;


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


    public List<Lieux> getLieux() {
        return lieux;
    }

    public void setLieux(List<Lieux> lieux) {
        this.lieux = lieux;
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
