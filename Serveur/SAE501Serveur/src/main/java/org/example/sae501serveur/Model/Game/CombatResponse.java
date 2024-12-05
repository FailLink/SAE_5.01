package org.example.sae501serveur.Model.Game;

import java.util.List;

public class CombatResponse {
    private List<CombatResult> results;
    private String type;

    public CombatResponse(String type, List<CombatResult> results) {
        this.type = type;
        this.results = results;
    }

    // Getters et Setters
    public List<CombatResult> getResults() {
        return results;
    }

    public void setResults(List<CombatResult> results) {
        this.results = results;
    }
}

