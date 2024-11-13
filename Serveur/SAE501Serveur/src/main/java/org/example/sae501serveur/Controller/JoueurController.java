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

    @GetMapping("/joueurSession")
    @ResponseBody
    public ResponseEntity<?> getJoueurBySessionId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
        Optional<Joueur> joueur=joueurService.getJoueurByPseudo(authentication.getName());

        return ResponseEntity.ok(joueur.get()); // Renvoie les informations de l'utilisateur en JSON
    }
    @PostMapping("/joueurId")
    @ResponseBody
    public ResponseEntity<?> getJoueurById(@RequestParam("id")Long id){
        return ResponseEntity.ok(joueurService.getJoueurById(id));
    }
}
