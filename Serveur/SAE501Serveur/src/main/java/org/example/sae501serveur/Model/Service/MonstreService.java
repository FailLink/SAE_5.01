package org.example.sae501serveur.Model.Service;


import org.example.sae501serveur.Model.Entity.Monstre;
import org.example.sae501serveur.Model.Repository.MonstreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
