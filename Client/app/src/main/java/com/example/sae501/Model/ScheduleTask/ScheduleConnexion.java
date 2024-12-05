package com.example.sae501.Model.ScheduleTask;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.Controller.Connexion.ConnexionRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleConnexion {
    //objet permettant l'exécution repété de ma fonction
    ScheduledExecutorService scheduleConnexion;
    //boolean servant à vérifier si j'ai déjà mon schedule connexion est actif ou non
    boolean isRunning = false;
    //objet nécessaire pour tester périodiquement la connexion au serveur
    ConnexionRepository connexionRepository;

    /**
     * constructeur de la classe
     *
     * @author Matisse Gallouin
     */
    public ScheduleConnexion() {
        this.connexionRepository = new ConnexionRepository(this);
    }

    /**
     * fonction pour lancer ma vérification périodique
     *
     * @author Matisse Gallouin
     */
    public void startVerification() {
        if (!isRunning) {
            isRunning = true;
            scheduleConnexion = Executors.newScheduledThreadPool(1);
            scheduleConnexion.scheduleWithFixedDelay(() -> {
                connexionRepository.getTestConnexion();
            }, 0, 15, TimeUnit.SECONDS);
        }
    }

    /**
     * fonction pour stopper ma vérification périodique
     *
     * @author Matisse Gallouin
     */
    public void stopVerification() {
        if (scheduleConnexion != null) {
            scheduleConnexion.shutdownNow();
            isRunning = false;
        }
    }
}
