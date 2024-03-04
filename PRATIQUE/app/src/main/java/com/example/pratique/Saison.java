package com.example.pratique;

import java.util.Vector;

public class Saison {
    private Vector<Entrainement> liste;
    public static final int OBJECTIF = 12;

    public Saison() {
        liste = new Vector<>();
    }

    // MÉTHODES
    public void ajouterEntrainement(Entrainement e) {
        liste.add(e);
    }

    public int nbHeuresEntrainement() {
        int total = 0;
        for (Entrainement e : liste) {
            total += e.getHeureFin() - e.getHeureDebut();
        }
        return total;
    }
}
