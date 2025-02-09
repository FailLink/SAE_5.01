package org.example.sae501serveur.Controller;

import org.example.sae501serveur.Model.Entity.DemandeAmi;
import org.example.sae501serveur.Model.Entity.DemandeAmiId;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Service.DemandeAmiService;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class DemandeAmiController {
    @Autowired
    public DemandeAmiService demandeAmiService;
    @Autowired
    public JoueurService joueurService;
    @PostMapping("/sendInvitationAmi")
    @ResponseBody
    public ResponseEntity<?> sendInvitationAmi(@RequestParam("idJoueurInviteur") Long idJoueurInviteur,
                                               @RequestParam("idJoueurInvite")Long idJoueurInvite){
        Joueur joueurInviteur=joueurService.getJoueurById(idJoueurInviteur).get();
        Joueur joueurInvite=joueurService.getJoueurById(idJoueurInvite).get();
        DemandeAmi demandeAmi=new DemandeAmi(joueurInviteur,joueurInvite, LocalDateTime.now());
        demandeAmiService.saveDemandeAmi(demandeAmi);
        return ResponseEntity.ok(demandeAmi);
    }

    @PostMapping("/getAllInvitationReceipt")
    @ResponseBody
    public ResponseEntity<?> getAllInvitationReceipt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
        Optional<Joueur> joueur = joueurService.getJoueurByPseudo(authentication.getName());
        return ResponseEntity.ok(demandeAmiService.getDemandeAmiByIdJoueurInvite(joueur.get()));
    }
    @PostMapping("/getAllInvitationSend")
    @ResponseBody
    public ResponseEntity<?> getAllInvitationSend(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
        Optional<Joueur> joueur = joueurService.getJoueurByPseudo(authentication.getName());
        return ResponseEntity.ok(demandeAmiService.getDemandeAmiByIdJoueurInviteur(joueur.get()));
    }
    @PostMapping("/deleteInvitation")
    public ResponseEntity<?> deleteInvitation(@RequestParam("joueurInviteur") Long joueurInviteur){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
        Optional<Joueur> joueur = joueurService.getJoueurByPseudo(authentication.getName());
        demandeAmiService.deleteDemandeAmi(joueurInviteur,joueur.get().getId());
        return ResponseEntity.ok("suppr");
    }
}
