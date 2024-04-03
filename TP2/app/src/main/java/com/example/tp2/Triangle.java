package com.example.tp2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle extends Forme {
    // Attributs
    protected float startX, startY;
    protected float endX, endY;

    // Constructeur
    public Triangle(int couleur, int trait_epaisseur) {
        super(couleur, trait_epaisseur);
    }

    // Méthodes
    public void startLine(float x, float y) {
        startX = x;
        startY = y;
        endX = x;
        endY = y;
    }

    public void continueLine(float x, float y) {
        endX = x;
        endY = y;

        path.reset();
        path.moveTo(startX, startY);
        path.lineTo((startX + endX) / 2, endY);
        path.lineTo(endX, startY);
        path.lineTo(startX, startY);
    }
}