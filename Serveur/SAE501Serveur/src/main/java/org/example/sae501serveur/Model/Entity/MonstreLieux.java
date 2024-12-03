package org.example.sae501serveur.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.example.sae501serveur.Model.JsonViewEntity.Views;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "monstre_lieux")
@IdClass(CleCompositeMonstreLieux.class)
public class MonstreLieux {
    @Id
    @Column(name = "monstre_id")
    @JsonView(Views.MonstreLieuxView.class)
    private Long monstreId;

    @Id
    @Column(name = "lieux_id")
    @JsonView(Views.MonstreLieuxView.class)
    private Long lieuxId;


    @ManyToOne
    @JoinColumn(name = "monstre_id", insertable = false, updatable = false)
    @JsonView(Views.MonstreLieuxView.class)
    private Monstre monstre;

    @ManyToOne
    @JoinColumn(name = "lieux_id", insertable = false, updatable = false)
    @JsonView(Views.MonstreLieuxView.class)
    private Lieux lieux;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonView(Views.MonstreLieuxView.class)
    @JsonBackReference
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

    public MonstreLieux(Monstre monstre, Lieux lieux) {
        this.monstre = monstre;
        this.lieux = lieux;
        this.lieuxId=lieux.getId();
        this.monstreId=monstre.getId();
        this.partie=new HashSet<>();
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
    public void addPartie(Partie partie){
        this.partie.add(partie);
        if(partie.getMonstreLieux()!=null) {
            if (!partie.getMonstreLieux().contains(this)) {
                partie.addMonstreLieux(this);
            }
        }else{
            partie.setMonstreLieux(new HashSet<>());
            partie.addMonstreLieux(this);
        }
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
