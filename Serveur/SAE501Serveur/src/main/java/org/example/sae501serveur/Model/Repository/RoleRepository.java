package org.example.sae501serveur.Model.Repository;

import org.example.sae501serveur.Model.Entity.AttaqueSynchronisee;
import org.example.sae501serveur.Model.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
