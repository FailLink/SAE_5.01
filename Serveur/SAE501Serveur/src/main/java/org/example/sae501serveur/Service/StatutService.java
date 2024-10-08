package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Statut;
import org.example.sae501serveur.Repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatutService {
    @Autowired
    private StatutRepository statutRepository;

    public List<Statut> getAllStatuts() {
        return statutRepository.findAll();
    }

    public Statut saveStatut(Statut statut) {
        return statutRepository.save(statut);
    }

    public Statut getStatutById(Long id) {
        return statutRepository.findById(id).orElse(null);
    }

    public void deleteStatut(Long id) {
        statutRepository.deleteById(id);
    }
}

