package org.example.sae501serveur.Model.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "monstre_lieux")
@IdClass(CleCompositeMonstreLieux.class)
public class MonstreLieux {
    @Id
    @Column(name = "monstre_id")
    private Long monstreId;

    @Id
    @Column(name = "lieux_id")
    private Long lieuxId;


    @ManyToOne
    @JoinColumn(name = "monstre_id", insertable = false, updatable = false)
    private Monstre monstre;

    @ManyToOne
    @JoinColumn(name = "lieux_id", insertable = false, updatable = false)
    private Lieux lieux;

    @ManyToMany(mappedBy = "monstreLieux")
    private Set<Partie> partie;

    public MonstreLieux() {
    }

    public MonstreLieux(Long monstreId, Long lieuxId, Monstre monstre, Lieux lieux, Set<Partie> partie) {
        this.monstreId = monstreId;
        this.lieuxId = lieuxId;
        this.monstre = monstre;
        this.lieux = lieux;
        this.partie = partie;
    }

    public MonstreLieux(Long monstreId, Long lieuxId, Set<Partie> partie) {
        this.monstreId = monstreId;
        this.lieuxId = lieuxId;
        this.partie = partie;
    }

    public Monstre getMonstre() {
        return monstre;
    }

    public void setMonstre(Monstre monstre) {
        this.monstre = monstre;
    }

    public Lieux getLieux() {
        return lieux;
    }

    public void setLieux(Lieux lieux) {
        this.lieux = lieux;
    }

    public Set<Partie> getPartie() {
        return partie;
    }

    public void setPartie(Set<Partie> partie) {
        this.partie = partie;
    }

    public Long getMonstreId() {
        return monstreId;
    }

    public void setMonstreId(Long monstreId) {
        this.monstreId = monstreId;
    }

    public Long getLieuxId() {
        return lieuxId;
    }

    public void setLieuxId(Long lieuxId) {
        this.lieuxId = lieuxId;
    }
}
