package org.example.sae501serveur.Controller;

import org.example.sae501serveur.Dto.MonstreDTO;
import org.example.sae501serveur.Entity.Monstre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.sae501serveur.Service.MonstreService;

import java.util.List;


@RestController
@RequestMapping("/monstres")
public class MonstreTest {

    @Autowired
    private MonstreService monstreService;

    // Récupérer tous les monstres
    @GetMapping
    public ResponseEntity<List<MonstreDTO>> getAllMonstres() {
        List<MonstreDTO> monstres = monstreService.getAllMonstres();
        return new ResponseEntity<>(monstres, HttpStatus.OK);
    }
}
