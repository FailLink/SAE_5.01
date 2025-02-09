package org.example.sae501serveur.Model.Service;

import org.example.sae501serveur.Model.Entity.DemandeAmi;
import org.example.sae501serveur.Model.Entity.DemandeAmiId;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Repository.DemandeAmiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeAmiService {
    @Autowired
    public DemandeAmiRepository demandeAmiRepository;
    public DemandeAmi getDemandeAmiById(DemandeAmiId demandeAmiId){
        return demandeAmiRepository.getReferenceById(demandeAmiId);
    }

    public List<DemandeAmi> getDemandeAmiByIdJoueurInvite(Joueur joueurInvite){
        return demandeAmiRepository.findAllByJoueurInvite(joueurInvite);
    }
    public List<DemandeAmi> getDemandeAmiByIdJoueurInviteur(Joueur joueurInviteur){
        return demandeAmiRepository.findAllByJoueurInviteur(joueurInviteur);
    }
    public DemandeAmi saveDemandeAmi(DemandeAmi demandeAmi) {
        return demandeAmiRepository.save(demandeAmi);
    }
    public void deleteDemandeAmi(Long joueurInviteur,Long joueurInvite){
        demandeAmiRepository.deleteById(new DemandeAmiId(joueurInviteur,joueurInvite));
    }
}
