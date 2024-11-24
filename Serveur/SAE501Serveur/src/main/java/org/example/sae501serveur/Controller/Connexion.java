package org.example.sae501serveur.Controller;

import jakarta.persistence.Access;
import org.example.sae501serveur.Model.ConfigSecurity;
import org.example.sae501serveur.Model.Entity.Joueur;
import org.example.sae501serveur.Model.Entity.Role;
import org.example.sae501serveur.Model.Service.JoueurService;
import org.example.sae501serveur.Model.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class Connexion {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JoueurService joueurService;
    @Autowired
    RoleService roleService;
    @GetMapping("/testConnexion")
    public ResponseEntity<?> testConnexion(){
        return ResponseEntity.ok("Leserveurestjoignable");
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/creationCompte")
    public String creationCompte(){return "creationCompte";}
    @PostMapping("/ajoutUtilisateurBDD")
    @ResponseBody
    public ResponseEntity<?> ajoutJoueur(@RequestParam("username") String username,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password){
        if(joueurService.getJoueurByMail(email).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Email déjà utilisé."));
        }
        if (joueurService.getJoueurByPseudo(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Pseudo déjà utilisé."));
        }
            Joueur joueur = new Joueur();
            joueur.setMdp(passwordEncoder.encode(password));
            joueur.setPseudo(username);
            joueur.setAdresseMail(email);
            joueur.setRole(roleService.getStatutById(1L));
            joueurService.saveJoueur(joueur);
            return ResponseEntity.ok(Map.of("success", true, "message", "Compte créé avec succès."));
    }
    //test
}
