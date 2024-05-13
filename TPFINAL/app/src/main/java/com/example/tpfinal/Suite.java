package com.example.tpfinal;

import java.util.ArrayList;
import java.util.List;

public class Suite {
    private List<Carte> cartes;
    private boolean isIncreasing;

    public Suite(boolean isIncreasing) {
        cartes = new ArrayList<>();
        this.isIncreasing = isIncreasing;
    }

    public void addCarte(Carte carte) {
        cartes.add(carte);
    }

    public boolean canAddCarte(Carte carte) {
        if (cartes.isEmpty()) {
            return true;
        }
        if (isIncreasing) {
            return carte.getValeur() > cartes.get(cartes.size() - 1).getValeur();
        }
        else {
            return carte.getValeur() < cartes.get(cartes.size() - 1).getValeur();
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