package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Classe;
import org.example.sae501serveur.Repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Classe saveClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public Classe getClasseById(Long id) {
        return classeRepository.findById(id).orElse(null);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

}

