package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.Role;
import org.example.sae501serveur.Model.Entity.Statut;
import org.example.sae501serveur.Model.Repository.RoleRepository;
import org.example.sae501serveur.Model.Repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllStatuts() {
        return roleRepository.findAll();
    }

    public Role saveStatut(Role role) {
        return roleRepository.save(role);
    }

    public Role getStatutById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void deleteStatut(Long id) {
        roleRepository.deleteById(id);
    }
}
