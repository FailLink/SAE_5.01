package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Competence;
import org.example.sae501serveur.Repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;

    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    public Competence saveCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    public Competence getCompetenceById(Long id) {
        return competenceRepository.findById(id).orElse(null);
    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }

}

