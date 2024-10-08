package org.example.sae501serveur.Service;


import org.example.sae501serveur.Dto.MonstreDTO;
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

    public List<MonstreDTO> getAllMonstres() {
        List<Monstre> monstres = monstreRepository.findAll();
        return monstres.stream().map(monstre -> {
            MonstreDTO dto = new MonstreDTO();
            dto.setId(monstre.getId());
            dto.setHp(monstre.getHp());
            dto.setAttack(monstre.getAttack());
            dto.setDef(monstre.getDef());
            dto.setPartie(monstre.getParties() != null ? monstre.getParties().getId() : null);
            dto.setTypeMonstreId(monstre.getTypeMonstre() != null ? monstre.getTypeMonstre().getId() : null);
            dto.setStatut(monstre.getStatut() != null ? monstre.getStatut().getId() : null);
            return dto;
        }).collect(Collectors.toList());
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
