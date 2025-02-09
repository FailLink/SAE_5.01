package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.Classe;
import org.example.sae501serveur.Model.Entity.Competence;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Repository.ClasseRepository;
import org.example.sae501serveur.Model.Repository.CompetenceRepository;
import org.example.sae501serveur.Model.Repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class JoueurService implements UserDetailsService {
    @Autowired
    private JoueurRepository joueurRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private CompetenceRepository competenceRepository;

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
    }

    ;

    public List<Joueur> getAllJoueurs() {
        return joueurRepository.findAll();
    }

    public Joueur saveJoueur(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    public Optional<Joueur> getJoueurById(Long id) {
        return joueurRepository.findById(id);
    }

    public Optional<Joueur> getJoueurByMail(String mail) {
        return joueurRepository.findByAdresseMail(mail);
    }

    public Optional<Joueur> getJoueurByPseudo(String pseudo) {
        return joueurRepository.findByPseudo(pseudo);
    }

    public Optional<Joueur> setClasseJoueur(Long id, String classeName) {
        Optional<Classe> classe = classeRepository.findByNom(classeName);
        if (classe.isEmpty()) {
            return null;
        } else {
            int nbLignesModif = joueurRepository.setJoueurClasse(id, classe.get());
            if (nbLignesModif == 1) {
                if(classe.get().getNom().equalsIgnoreCase("Musclow")){
                    setCompetencesJoueur(id, 1L);
                    setCompetencesJoueur(id,2L);
                }else if(classe.get().getNom().equalsIgnoreCase("Guewier")){
                    setCompetencesJoueur(id,2L);
                    setCompetencesJoueur(id,3L);
                }
                else if(classe.get().getNom().equalsIgnoreCase("Healer")){
                    setCompetencesJoueur(id,1L);
                    setCompetencesJoueur(id,3L);
                }
                else if(classe.get().getNom().equalsIgnoreCase("mawabou")){
                    setCompetencesJoueur(id,2L);
                    setCompetencesJoueur(id,3L);
                }
                return getJoueurById(id);
            } else {
                return null;
            }
        }
    }
    public Joueur setCompetencesJoueur(Long idJoueur,Long idCompetences){
        Joueur joueur=joueurRepository.getReferenceById(idJoueur);
        Competence competence=competenceRepository.getReferenceById(idCompetences);
        joueur.addCompetence(competence);
        return joueurRepository.save(joueur);
    }
    public List<Joueur> getAllAmi(Long idJoueur){
        Joueur joueur=joueurRepository.getReferenceById(idJoueur);
        joueur.setAmisFinaux();
        return joueur.getAmisFinaux();
    }
}

