package com.example.tp2;

import android.graphics.Path;

public class Rectangle extends Forme {
    // Attributs
    protected float startX, startY;

    // Constructeur
    public Rectangle(int couleur, int trait_epaisseur) {
        super(couleur, trait_epaisseur);
    }

    // Méthodes
    public void startLine(float x, float y) {
        startX = x;
        startY = y;
    }

    public void continueLine(float x, float y) {
        // Nécessaire pour faire des rectangles avec des valeurs négatives (droite à gauche)
        float left = Math.min(startX, x);
        float right = Math.max(startX, x);
        float top = Math.min(startY, y);
        float bottom = Math.max(startY, y);

        // Ajout du rectangle
        path.reset();
        path.addRect(left, top, right, bottom, Path.Direction.CW);
    }

}
