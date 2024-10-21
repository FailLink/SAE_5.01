package org.example.sae501serveur.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "monstre_lieux")
public class MonstreLieux {
    @Id
    @ManyToOne
    @JoinColumn(name = "monstre_id")
    private Monstre monstre;

    @Id
    @ManyToOne
    @JoinColumn(name = "lieux_id")
    private Lieux lieux;

    @ManyToMany(mappedBy = "monstreLieux")
    private Set<Partie> partie;

    public MonstreLieux() {
    }

    public MonstreLieux(Monstre monstre, Lieux lieux, Set<Partie> partie) {
        this.monstre = monstre;
        this.lieux = lieux;
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
}
