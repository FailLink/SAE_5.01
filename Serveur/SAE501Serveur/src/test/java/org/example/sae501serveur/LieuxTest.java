package org.example.sae501serveur;

import org.example.sae501serveur.Model.Entity.Lieux;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LieuxTest {
    @Test
    public void calculDistance1(){
        Lieux lieux=new Lieux(1L,90,10);
        double distance=lieux.distance(new Lieux(2L,100,10));
        assertEquals(1096240,distance,1);
    }
}