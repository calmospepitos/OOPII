package com.example.tp2;

public class Efface extends Forme {
    // Constructeur
    public Efface(int couleur, int trait_epaisseur) {
        super(couleur, trait_epaisseur);
    }

    // Méthodes
    @Override
    public void startLine(float x, float y) {
        path.moveTo(x, y);
    }

    @Override
    public void continueLine(float x, float y) {
        path.lineTo(x, y);
    }
}
