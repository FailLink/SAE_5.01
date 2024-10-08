package org.example.sae501serveur.Dto;



import java.util.List;

public class MonstreDTO {
    private Long id;
    private int hp;
    private int attack;
    private int def;
    private Long typeMonstreId;
    private Long partie;
    private Long statut;



    // Constructeur par défaut
    public MonstreDTO() {}

    // Getters et Setters
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

    public Long getTypeMonstreId() {
        return typeMonstreId;
    }

    public void setTypeMonstreId(Long typeMonstreId) {
        this.typeMonstreId = typeMonstreId;
    }

    public Long getPartie() {
        return partie;
    }

    public void setPartie(Long partie) {
        this.partie = partie;
    }

    public Long getStatut() {
        return statut;
    }

    public void setStatut(Long statut) {
        this.statut = statut;
    }

}

