package org.example.sae501serveur.Service;


import org.example.sae501serveur.Entity.Partie;
import org.example.sae501serveur.Repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartieService {
    @Autowired
    private PartieRepository partieRepository;

    public List<Partie> getAllParties() {
        return partieRepository.findAll();
    }

    public Partie savePartie(Partie partie) {
        return partieRepository.save(partie);
    }

    public Partie getPartieById(Long id) {
        return partieRepository.findById(id).orElse(null);
    }

    public void deletePartie(Long id) {
        partieRepository.deleteById(id);
    }

}

