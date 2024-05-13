package com.example.tpfinal;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Carte> cartes;

    public Deck() {
        cartes = new ArrayList<>();
        for (int i = 1; i <= 97; i++) {
            cartes.add(new Carte(i));
        }
        Collections.shuffle(cartes);
    }

    public Carte drawCard() {
        if (!cartes.isEmpty()) {
            return cartes.remove(cartes.size() - 1);
        } else {
            return null;
        }
    }

    public void assignCards(TextView[] cards) {
        for (TextView card : cards) {
            card.setText(String.valueOf(drawCard().getValeur()));
        }
    }
}