package com.example.sae501.Model.ScheduleTask;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.example.sae501.Controller.ConnexionRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleConnexion {
    ScheduledExecutorService scheduleConnexion;
    boolean isRunning=false;
    ConnexionRepository connexionRepository;

    public ScheduleConnexion(Activity activity) {
        this.connexionRepository=new ConnexionRepository((FragmentActivity) activity,this);
    }

    public void startVerification() {
        if (!isRunning) {
            isRunning = true;
            scheduleConnexion = Executors.newScheduledThreadPool(1);
            scheduleConnexion.scheduleWithFixedDelay(() -> {
                connexionRepository.getTestConnexion();
            }, 0, 15, TimeUnit.SECONDS);
        }
    }

    public void stopVerification() {
        if (scheduleConnexion != null) {
            scheduleConnexion.shutdownNow();
            isRunning = false;
        }
    }
}
