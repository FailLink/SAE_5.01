package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Joueur;
import org.example.sae501serveur.Repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class JoueurService {
    @Autowired
    private JoueurRepository joueurRepository;

    public List<Joueur> getAllJoueurs() {
        return joueurRepository.findAll();
    }

    public Joueur saveJoueur(Joueur joueur) {
        return joueurRepository.save(joueur);
    }
}

