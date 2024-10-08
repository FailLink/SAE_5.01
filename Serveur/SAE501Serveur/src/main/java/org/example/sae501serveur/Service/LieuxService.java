package org.example.sae501serveur.Service;

import org.example.sae501serveur.Entity.Lieux;
import org.example.sae501serveur.Repository.LieuxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LieuxService {
    @Autowired
    private LieuxRepository lieuxRepository;

    public List<Lieux> getAllLieux() {
        return lieuxRepository.findAll();
    }

    public Lieux saveLieux(Lieux lieux) {
        return lieuxRepository.save(lieux);
    }

    public Lieux getLieuxById(Long id) {
        return lieuxRepository.findById(id).orElse(null);
    }

    public void deleteLieux(Long id) {
        lieuxRepository.deleteById(id);
    }

}
