package com.example.myapplication;

import android.widget.TextView;

public class Partie {
    // Attributs
    private Deck deck;
    private int totalScore;
    private int lastScore;

    // Constructeur
    public Partie(TextView[] cartes) {
        this.totalScore = 0;
        this.deck = new Deck();
        deck.assignCards(cartes);
    }

    // Getters
    public int getScore() {
        return this.totalScore;
    }
    public Deck getDeck() {
        return this.deck;
    }


    // Méthodes
    public void calculerScore(Carte carteJouee, Carte carteSurSuite, long tempsPrit, int nbCartesRestantes) {
        int difference = Math.abs(carteJouee.getValeur() - carteSurSuite.getValeur());
        double proximityScore;
        if (difference == 0) {
            proximityScore = 1000.0;
        } else {
            proximityScore = 1000.0 / (double) (difference + 1);
        }
        double timeScore = (tempsPrit != 0) ? (10000.0 / tempsPrit) : 10000.0;
        int cardsRemainingScore = (97 - nbCartesRestantes) * 10;
        int score = (int) (proximityScore + timeScore + cardsRemainingScore);
        lastScore = score;

        // Update le score total
        totalScore += score;
    }

    public void undoLastScore() {
        totalScore -= lastScore;
    }
}
