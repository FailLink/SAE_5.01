package com.example.sae501.Model.ScheduleTask;

import android.util.Log;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Game.AttaqueDefRequest;
import com.example.sae501.Model.Entity.Competence;
import com.example.sae501.Model.Entity.Monstre;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ScheduleCombat extends Thread {

    private static final long INTERVAL_MS = 5000; // 5 secondes
    private volatile boolean running = true;
    Gson gson = new Gson();


    public void attaque(Monstre monstre) {
        // Crée un nouveau thread qui exécutera la méthode run
        Thread combatThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (running) { // Le thread continue jusqu'à ce qu'il soit interrompu
                    try {
                        String monstreJson = gson.toJson(monstre);

                        // Crée une nouvelle instance de AttaqueDefRequest à envoyer
                        AttaqueDefRequest attaqueDefRequest = new AttaqueDefRequest(
                                MainActivity.joueur.getId(),
                                MainActivity.joueur.getClasse().getAttack(),
                                MainActivity.joueur.getClasse().getDef(),
                                MainActivity.joueur.getHpActuel(),
                                monstre,
                                "null",
                                1
                        );

                        String jsonMessage = "{ " +
                                "\"type\": \"attaqueDef\", " +
                                "\"joueurId\": " + attaqueDefRequest.getJoueurId() + ", " +
                                "\"degats\": " + attaqueDefRequest.getDegats() + ", " +
                                "\"def\": " + 0 + ", " +
                                "\"pdv\": " + attaqueDefRequest.getPdv() + ", " +
                                "\"monstre\": "  + monstreJson + ", " +
                                "\"competence\": " + "null" + ", " +
                                "\"tourCompetence\": " + attaqueDefRequest.getTour() +
                                "}";

                        // Vérifie que le WebSocket est ouvert
                        if (MainActivity.webSocketPartie != null) {
                            MainActivity.webSocketPartie.send(jsonMessage);
                        } else {
                            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
                        }

                        // Attendre 5 secondes avant d'envoyer à nouveau
                        Thread.sleep(INTERVAL_MS);

                    } catch (InterruptedException e) {
                        Log.e("WebSocket", "Thread interrupted.");
                        break; // Le thread a été interrompu
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending message: " + e.getMessage());
                    }
                }
            }
        });

        // Démarre le thread de combat
        combatThread.start();
    }

    public void defense(Monstre monstre) {
        // Crée un nouveau thread qui exécutera la méthode run
        Thread combatThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) { // Le thread continue jusqu'à ce qu'il soit interrompu
                    try {
                        String monstreJson = gson.toJson(monstre);

                        // Crée une nouvelle instance de AttaqueDefRequest à envoyer
                        AttaqueDefRequest attaqueDefRequest = new AttaqueDefRequest(
                                MainActivity.joueur.getId(),
                                MainActivity.joueur.getClasse().getAttack(),
                                MainActivity.joueur.getClasse().getDef(),
                                MainActivity.joueur.getHpActuel(),
                                monstre,
                                "null",
                                1
                        );

                        String jsonMessage = "{ " +
                                "\"type\": \"attaqueDef\", " +
                                "\"joueurId\": " + attaqueDefRequest.getJoueurId() + ", " +
                                "\"degats\": " + 0 + ", " +
                                "\"def\": " + attaqueDefRequest.getDef() + ", " +
                                "\"pdv\": " + attaqueDefRequest.getPdv() + ", " +
                                "\"monstre\": "  + monstreJson + ", " +
                                "\"competence\": " + "null" + ", " +
                                "\"tourCompetence\": " + attaqueDefRequest.getTour() +
                                "}";

                        // Vérifie que le WebSocket est ouvert
                        if (MainActivity.webSocketPartie != null) {
                            MainActivity.webSocketPartie.send(jsonMessage);
                        } else {
                            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
                        }

                        // Attendre 5 secondes avant d'envoyer à nouveau
                        Thread.sleep(INTERVAL_MS);

                    } catch (InterruptedException e) {
                        Log.e("WebSocket", "Thread interrupted.");
                        break; // Le thread a été interrompu
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending message: " + e.getMessage());
                    }
                }
            }
        });

        // Démarre le thread de combat
        combatThread.start();
    }

    public void competence1(Monstre monstre) {
        // Crée un nouveau thread qui exécutera la méthode run
        Thread combatThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) { // Le thread continue jusqu'à ce qu'il soit interrompu
                    try {
                        List<Competence> competencesListe = new ArrayList<>(MainActivity.joueur.getCompetences());
                        Competence competence = competencesListe.get(0) ;

                        String monstreJson = gson.toJson(monstre);

                        // Crée une nouvelle instance de AttaqueDefRequest à envoyer
                        AttaqueDefRequest attaqueDefRequest = new AttaqueDefRequest(
                                MainActivity.joueur.getId(),
                                competence.getAttack(),
                                MainActivity.joueur.getClasse().getDef(),
                                MainActivity.joueur.getHpActuel(),
                                monstre,
                                competence.getTypeCompetence().getNom(),
                                competence.getStatut().getNbTour()

                        );

                        String jsonMessage = "{ " +
                                "\"type\": \"attaqueDef\", " +
                                "\"joueurId\": " + attaqueDefRequest.getJoueurId() + ", " +
                                "\"degats\": " + attaqueDefRequest.getDegats() + ", " +
                                "\"def\": " + 0 + ", " +
                                "\"pdv\": " + attaqueDefRequest.getPdv() + ", " +
                                "\"monstre\": "  + monstreJson +  ", " +
                                "\"competenceNom\": \"" + competence.getTypeCompetence().getNom() + "\"" + ", " +
                                "\"tourCompetence\": " + attaqueDefRequest.getTour() +
                                "}";

                        // Vérifie que le WebSocket est ouvert
                        if (MainActivity.webSocketPartie != null) {
                            MainActivity.webSocketPartie.send(jsonMessage);
                        } else {
                            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
                        }

                        // Attendre 5 secondes avant d'envoyer à nouveau
                        Thread.sleep(INTERVAL_MS);

                    } catch (InterruptedException e) {
                        Log.e("WebSocket", "Thread interrupted.");
                        break; // Le thread a été interrompu
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending message: " + e.getMessage());
                    }
                }
            }
        });

        // Démarre le thread de combat
        combatThread.start();
    }

    public void competence2(Monstre monstre) {
        // Crée un nouveau thread qui exécutera la méthode run
        Thread combatThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) { // Le thread continue jusqu'à ce qu'il soit interrompu
                    try {
                        List<Competence> competencesListe = new ArrayList<>(MainActivity.joueur.getCompetences());
                        Competence competence = competencesListe.get(1) ;

                        String monstreJson = gson.toJson(monstre);

                        // Crée une nouvelle instance de AttaqueDefRequest à envoyer
                        AttaqueDefRequest attaqueDefRequest = new AttaqueDefRequest(
                                MainActivity.joueur.getId(),
                                competence.getAttack(),
                                MainActivity.joueur.getClasse().getDef(),
                                MainActivity.joueur.getHpActuel(),
                                monstre,
                                competence.getTypeCompetence().getNom(),
                                competence.getStatut().getNbTour()

                        );

                        String jsonMessage = "{ " +
                                "\"type\": \"attaqueDef\", " +
                                "\"joueurId\": " + attaqueDefRequest.getJoueurId() + ", " +
                                "\"degats\": " + attaqueDefRequest.getDegats() + ", " +
                                "\"def\": " + 0 + ", " +
                                "\"pdv\": " + attaqueDefRequest.getPdv() + ", " +
                                "\"monstre\": "  + monstreJson +  ", " +
                                "\"competenceNom\": \"" + competence.getTypeCompetence().getNom() + "\"" + ", " +
                                "\"tourCompetence\": " + attaqueDefRequest.getTour() +
                                "}";

                        // Vérifie que le WebSocket est ouvert
                        if (MainActivity.webSocketPartie != null) {
                            MainActivity.webSocketPartie.send(jsonMessage);
                        } else {
                            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
                        }

                        // Attendre 5 secondes avant d'envoyer à nouveau
                        Thread.sleep(INTERVAL_MS);

                    } catch (InterruptedException e) {
                        Log.e("WebSocket", "Thread interrupted.");
                        break; // Le thread a été interrompu
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending message: " + e.getMessage());
                    }
                }
            }
        });

        // Démarre le thread de combat
        combatThread.start();
    }

    public void mort(Monstre monstre) {
        // Crée un nouveau thread qui exécutera la méthode run
        Thread combatThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) { // Le thread continue jusqu'à ce qu'il soit interrompu
                    try {
                        List<Competence> competencesListe = new ArrayList<>(MainActivity.joueur.getCompetences());
                        Competence competence = competencesListe.get(1) ;

                        String monstreJson = gson.toJson(monstre);

                        // Crée une nouvelle instance de AttaqueDefRequest à envoyer
                        AttaqueDefRequest attaqueDefRequest = new AttaqueDefRequest(
                                MainActivity.joueur.getId(),
                                competence.getAttack(),
                                MainActivity.joueur.getClasse().getDef(),
                                MainActivity.joueur.getHpActuel(),
                                monstre,
                                competence.getTypeCompetence().getNom(),
                                competence.getStatut().getNbTour()

                        );

                        String jsonMessage = "{ " +
                                "\"type\": \"attaqueDef\", " +
                                "\"joueurId\": " + attaqueDefRequest.getJoueurId() + ", " +
                                "\"degats\": " + 0 + ", " +
                                "\"def\": " + 0 + ", " +
                                "\"pdv\": " + 0 + ", " +
                                "\"monstre\": "  + monstreJson +  ", " +
                                "\"competenceNom\": \"" + competence.getTypeCompetence().getNom() + "\"" + ", " +
                                "\"tourCompetence\": " + attaqueDefRequest.getTour() +
                                "}";

                        // Vérifie que le WebSocket est ouvert
                        if (MainActivity.webSocketPartie != null) {
                            MainActivity.webSocketPartie.send(jsonMessage);
                        } else {
                            Log.e("WebSocket", "WebSocket is not open. Cannot send message.");
                        }

                        // Attendre 5 secondes avant d'envoyer à nouveau
                        Thread.sleep(INTERVAL_MS);

                    } catch (InterruptedException e) {
                        Log.e("WebSocket", "Thread interrupted.");
                        break; // Le thread a été interrompu
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error while sending message: " + e.getMessage());
                    }
                }
            }
        });

        // Démarre le thread de combat
        combatThread.start();
    }

    // Méthode pour arrêter le combat
    public void stopCombat(  ) {
        running = false; // Indique au thread d'arrêter la boucle
        Log.e("WebSocket", "stop combat ");
    }
}
