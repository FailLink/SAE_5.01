package org.example.sae501serveur.Controller;

    import org.example.sae501serveur.Model.Repository.MonstreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.sae501serveur.Model.Service.MonstreService;

import java.util.List;


@RestController
@RequestMapping("/monstres")
public class MonstreTest {

    @Autowired
    private MonstreService monstreService;

    @Autowired
    private MonstreRepository monstreRepository;

    // Récupérer tous les monstres
    @GetMapping
    public ResponseEntity<List<Object>> getAllMonstres() {
        return new ResponseEntity<>( monstreRepository.findAllMonstreHP(), HttpStatus.OK);
    }
}
