package com.example.SAE501;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.SAE501.Controller.ConnexionRepository;
import com.example.SAE501.Model.ScheduleTask.ScheduleConnexion;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public ConnexionRepository connexionRepository;
    private ScheduledExecutorService scheduler;
    private boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScheduleConnexion scheduleConnexion=new ScheduleConnexion(this);
        scheduleConnexion.startVerification();
    }
}