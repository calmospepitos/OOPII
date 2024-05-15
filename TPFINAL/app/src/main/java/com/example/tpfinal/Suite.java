package com.example.tpfinal;

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
        // Si la suite est vide, on peut ajouter n'importe quelle carte
        if (cartes.isEmpty()) {
            return true;
        }
        // Si la suite n'est pas vide, on vérifie si la carte peut être ajoutée
        int lastCardValue = cartes.get(cartes.size() - 1).getValeur();
        if (isIncreasing) {
            if (lastCardValue - carte.getValeur() == 10) {
                return true;
            }
            return carte.getValeur() > lastCardValue; // La carte doit être plus grande
        }
        else {
            if (carte.getValeur() - lastCardValue == 10) {
                return true;
            }
            return carte.getValeur() < lastCardValue;  // La carte doit être plus petite
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