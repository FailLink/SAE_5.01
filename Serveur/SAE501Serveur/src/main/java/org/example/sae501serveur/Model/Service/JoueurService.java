package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JoueurService implements UserDetailsService {
    @Autowired
    private JoueurRepository joueurRepository;

    /**
     * Charge un utilisateur par son pseudo.
     *
     * @param pseudo le pseudo de l'utilisateur à rechercher
     * @return les détails de l'utilisateur contenant son pseudo, son mot de passe et son role
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé ou si l'utilisateur n'a pas de rôle assigné
     */

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        Joueur joueur = joueurRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + joueur.getRole().getName())); // Récupère le role de l'utilisateur
        System.out.print(joueur.getRole().getName().toString());

        return new org.springframework.security.core.userdetails.User(joueur.getPseudo(), joueur.getMdp(), authorities);
    };

    public List<Joueur> getAllJoueurs() {
        return joueurRepository.findAll();
    }

    public Joueur saveJoueur(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    public Optional<Joueur> getJoueurById(Long id){
        return joueurRepository.findById(id);
    }
    public Optional<Joueur> getJoueurByMail(String mail){return joueurRepository.findByAdresseMail(mail);}
    public Optional<Joueur> getJoueurByPseudo(String pseudo){return joueurRepository.findByPseudo(pseudo);}
}

