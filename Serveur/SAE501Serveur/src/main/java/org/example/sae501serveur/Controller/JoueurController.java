package org.example.sae501serveur.Controller;

import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class JoueurController {
    @Autowired
    JoueurService joueurService;

    /**
     * fonction permettant à l'aide du jsessionID de récupérer le joueur lié
     *
     * @return le joueur lié au session id
     * @author Matisse Gallouin
     */
    @GetMapping("/joueurSession")
    @ResponseBody
    public ResponseEntity<?> getJoueurBySessionId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
        Optional<Joueur> joueur = joueurService.getJoueurByPseudo(authentication.getName());

        return ResponseEntity.ok(joueur.get());
    }

    @PostMapping("/joueurId")
    @ResponseBody
    public ResponseEntity<?> getJoueurById(@RequestParam("id") Long id) {
        Optional<Joueur> joueur = joueurService.getJoueurById(id);
        if (joueur.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le joueur demandé n'existe pas");
        } else {
            return ResponseEntity.ok(joueur.get());
        }
    }

    @PostMapping("/setJoueurClasse")
    @ResponseBody
    public ResponseEntity<?> setJoueurClasse(@RequestParam("id") Long id, @RequestParam("classe") String classe) {
        Optional<Joueur> joueur = joueurService.setClasseJoueur(id, classe);
        if (joueur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le joueur demandé n'existe pas");
        } else {
            return ResponseEntity.ok(joueur.get());
        }
    }
}
