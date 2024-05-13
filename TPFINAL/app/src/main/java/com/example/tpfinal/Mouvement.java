package com.example.tpfinal;

import android.view.View;
import android.widget.TextView;

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

    public TextView getOriginalView() {
        return originalView;
    }

    public TextView getTargetView() {
        return targetView;
    }

    public Carte getCarte() {
        return carte;
    }

    public Carte getDerniereCarte() {
        return derniereCarte;
    }

    public void annulerDernierMouvement() {
        originalView.setText(String.valueOf(carte.getValeur()));
        originalView.setVisibility(View.VISIBLE);
        targetView.setText("");
    }

}
