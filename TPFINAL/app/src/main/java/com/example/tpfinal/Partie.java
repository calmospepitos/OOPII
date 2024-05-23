package com.example.tpfinal;

import android.content.Intent;
import android.widget.Chronometer;

public class Partie {
    // Attributs
    private MainActivity mainActivity;
    private SingletonDatabase db;
    private Chronometer chronometer, chronometerCoups;
    private int totalScore, lastScore;
    private boolean scoreInserted;

    // Constructeur
    public Partie(MainActivity mainActivity, SingletonDatabase db, Chronometer chronometer, Chronometer chronometerCoups) {
        this.mainActivity = mainActivity;
        this.db = db;
        this.scoreInserted = false;
        this.chronometer = chronometer;
        this.chronometerCoups = chronometerCoups;
        this.totalScore = 0;
    }

    // Getters
    public int getScore() {
        return this.totalScore;
    }

    // Méthodes
    public void calculerScore(Carte carteJouee, Carte carteSurSuite, long tempsPrit, int nbCartesRestantes) {
        // Calcule le score de proximité
        int difference = Math.abs(carteJouee.getValeur() - carteSurSuite.getValeur());
        double proximityScore = 1000.0 / (double) (difference + 1);
        // Calcule le score de temps
        double timeScore = (tempsPrit != 0) ? (10000.0 / tempsPrit) : 10000.0;
        // Calcule le score de cartes restantes
        int cardsRemainingScore = (97 - nbCartesRestantes) * 10;
        int score = (int) (proximityScore + timeScore + cardsRemainingScore);

        // Update le score total
        lastScore = score;
        totalScore += score;
    }

    public void undoLastScore() {
        totalScore -= lastScore;
    }

    public void regardeFinPartie(int cartesRestantesCount) {
        if (!mainActivity.isMouvementPossible() || cartesRestantesCount == 0) {
            // Insère le score dans la base de données
            if (!scoreInserted) {
                db.insererScore(getScore());
                scoreInserted = true;
            }

            // Arrête les chronomètres
            chronometer.stop();
            chronometerCoups.stop();

            // Affiche l'activité de fin de jeu
            Intent intent = new Intent(mainActivity, EndActivity.class);
            intent.putExtra("cartesRestantes", cartesRestantesCount);
            mainActivity.startActivity(intent);
            mainActivity.finish();
        }
    }
}
