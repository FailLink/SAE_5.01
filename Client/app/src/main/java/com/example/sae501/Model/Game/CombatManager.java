package com.example.sae501.Model.Game;

import com.example.sae501.Model.Entity.Monstre;
import com.example.sae501.Model.ScheduleTask.ScheduleCombat;

public class CombatManager {
    private static ScheduleCombat scheduleCombat;

    // Démarrer le combat
    public static void startCombatAttaque(Monstre monstre) {
        if (scheduleCombat == null || !scheduleCombat.isAlive()) {
            scheduleCombat = new ScheduleCombat();
            scheduleCombat.attaque(monstre);
        }
    }

    public static void startCombatDefense(Monstre monstre) {
        if (scheduleCombat == null || !scheduleCombat.isAlive()) {
            scheduleCombat = new ScheduleCombat();
            scheduleCombat.defense(monstre);
        }
    }

    public static void startCombatCompetence1(Monstre monstre) {
        if (scheduleCombat == null || !scheduleCombat.isAlive()) {
            scheduleCombat = new ScheduleCombat();
            scheduleCombat.competence1(monstre);
        }
    }
    public static void startCombatCompetence2(Monstre monstre) {
        if (scheduleCombat == null || !scheduleCombat.isAlive()) {
            scheduleCombat = new ScheduleCombat();
            scheduleCombat.competence2(monstre);
        }
    }

    public static void startCombatMort(Monstre monstre) {
        if (scheduleCombat == null || !scheduleCombat.isAlive()) {
            scheduleCombat = new ScheduleCombat();
            scheduleCombat.mort(monstre);
        }
    }


        // Arrêter le combat
    public static void stopCombat(  ) {
        if (scheduleCombat != null) {
            scheduleCombat.stopCombat();
            scheduleCombat = null;  // Libérer la référence
        }
    }
}

