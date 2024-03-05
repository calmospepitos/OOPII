package com.example.pratique;

public class Entrainement {
    private String type;
    private int heureDebut;
    private int heureFin;

    public Entrainement(String type, int heureDebut, int heureFin) {
        this.type = type;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // GETTERS
    public String getType() {
        return type;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }
}
