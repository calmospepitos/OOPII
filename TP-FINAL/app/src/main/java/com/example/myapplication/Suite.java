package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Suite {
    // Attributs
    private List<Carte> cartes;
    private boolean isIncreasing;

    // Constructeur
    public Suite(boolean isIncreasing) {
        cartes = new ArrayList<>();
        this.isIncreasing = isIncreasing;
    }

    // Getters et Setters
    public List<Carte> getCartes() {
        return cartes;
    }

    // Méthodes
    public void addCarte(Carte carte) {
        cartes.add(carte);
    }

    public boolean canAddCarte(Carte carte) {
        if (cartes.isEmpty()) {
            return true;
        }
        int lastCardValue = cartes.get(cartes.size() - 1).getValeur();
        if (Math.abs(carte.getValeur() - lastCardValue) == 10) {
            return true;
        }
        if (isIncreasing) {
            return carte.getValeur() > lastCardValue;
        }
        else {
            return carte.getValeur() < lastCardValue;
        }
    }

    public boolean receiveCarte(Carte carte) {
        if (canAddCarte(carte)) {
            addCarte(carte);
            return true;
        }
        return false;
    }
}