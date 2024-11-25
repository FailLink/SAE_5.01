package org.example.sae501serveur.Model.Service;

import org.aspectj.apache.bcel.classfile.Module;
import org.example.sae501serveur.Model.Entity.Classe;
import org.example.sae501serveur.Model.Repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Classe> getClasseByName(String nom){
        return classeRepository.findByNom(nom);
    }

}

