package org.example.sae501serveur.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Connexion {
    @GetMapping("/testConnexion")
    public ResponseEntity<?> testConnexion(){
        return ResponseEntity.ok("Leserveurestjoignable");
    }
}
