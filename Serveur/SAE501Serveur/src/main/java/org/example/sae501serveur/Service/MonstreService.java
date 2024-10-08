package org.example.sae501serveur.Service;


import org.example.sae501serveur.Entity.Monstre;
import org.example.sae501serveur.Repository.MonstreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonstreService {
    @Autowired
    private MonstreRepository monstreRepository;

    public List<Monstre> getAllMonstres() {
        return monstreRepository.findAll();

    }

    public Monstre saveMonstre(Monstre monstre) {
        return monstreRepository.save(monstre);
    }

    public Monstre getMonstreById(Long id) {
        return monstreRepository.findById(id).orElse(null);
    }

    public void deleteMonstre(Long id) {
        monstreRepository.deleteById(id);
    }



}
