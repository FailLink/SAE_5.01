package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.CleCompositeMonstreLieux;
import org.example.sae501serveur.Model.Entity.MonstreLieux;
import org.example.sae501serveur.Model.Repository.MonstreLieuxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonstreLieuxService {
    @Autowired
    private MonstreLieuxRepository monstreLieuxRepository;

    public List<MonstreLieux> getAllMonstreLieux() {
        return monstreLieuxRepository.findAll();
    }

    public void saveMonstreLieux(MonstreLieux monstreLieux) {
        monstreLieuxRepository.save(monstreLieux);
    }

    public MonstreLieux getMonstreLieuxById(CleCompositeMonstreLieux cleCompositeMonstreLieux) {
        return monstreLieuxRepository.getReferenceById(cleCompositeMonstreLieux);
    }
}
