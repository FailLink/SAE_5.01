package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.AttaqueSynchronisee;
import org.example.sae501serveur.Model.Repository.AttaqueSynchroniseeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AttaqueSynchronyseeService {

    @Autowired
    private AttaqueSynchroniseeRepository attaqueSynchronyseeRepository;

    public List<AttaqueSynchronisee> getAllCompetences() {
        return attaqueSynchronyseeRepository.findAll();
    }

    public AttaqueSynchronisee saveCompetence(AttaqueSynchronisee attaqueSynchronisee) {
        return attaqueSynchronyseeRepository.save(attaqueSynchronisee);
    }

    public AttaqueSynchronisee getCompetenceById(Long id) {
        return attaqueSynchronyseeRepository.findById(id).orElse(null);
    }

    public void deleteAttaqueSynchronisee(Long id) {
        attaqueSynchronyseeRepository.deleteById(id);
    }


}
