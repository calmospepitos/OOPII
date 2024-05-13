package com.example.tpfinal;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    // Attributs
    private List<Carte> cartes;
    private Mouvement dernierMouvement;

    // Constructeur
    public Deck() {
        cartes = new ArrayList<>();
        for (int i = 1; i <= 97; i++) {
            cartes.add(new Carte(i));
        }
        Collections.shuffle(cartes);
    }

    // Méthodes
    public Carte drawCard() {
        if (!cartes.isEmpty()) {
            return cartes.remove(cartes.size() - 1);
        }
        else {
            return null;
        }
    }

    public List<Carte> drawTwoCards() {
        List<Carte> twoCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            twoCards.add(drawCard());
        }
        return twoCards;
    }

    public void assignCards(TextView[] cards) {
        for (TextView card : cards) {
            card.setText(String.valueOf(drawCard().getValeur()));
        }
    }

    public void setDernierMouvement(Carte carte, Carte derniereCarte, TextView originalView, TextView targetView) {
        dernierMouvement = new Mouvement(carte, derniereCarte, originalView, targetView);
    }

    public Mouvement getLastMove() {
        return dernierMouvement;
    }
}