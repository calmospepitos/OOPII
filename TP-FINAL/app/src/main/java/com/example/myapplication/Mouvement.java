package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class Mouvement {
    private Carte carte;
    private Carte derniereCarte;
    private TextView originalView;
    private TextView targetView;

    public Mouvement(Carte carte, Carte derniereCarte, TextView originalView, TextView targetView) {
        this.carte = carte;
        this.derniereCarte = derniereCarte;
        this.originalView = originalView;
        this.targetView = targetView;
    }

    public void annulerDernierMouvement(HashMap<TextView, Carte> carteMap, HashMap<TextView, Suite> suiteMap, Partie partie) {
        Suite targetSuite = suiteMap.get(targetView);
        if (targetSuite != null && derniereCarte != null) {
            // Ajouter la carte à la suite d'origine
            targetSuite.getCartes().remove(carte);
            originalView.setVisibility(View.VISIBLE);
            targetView.setText(String.valueOf(derniereCarte.getValeur()));
            carteMap.put(originalView, carte);

            // Update le score
            partie.undoLastScore();
        }
    }
}
