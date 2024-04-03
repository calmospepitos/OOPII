package com.example.tp2;

import android.graphics.Path;
import android.graphics.RectF;

public class Oval extends Forme {
    // Attributs
    protected float startX, startY;
    protected float endX, endY;
    protected RectF boundingBox;

    // Constructeur
    public Oval(int couleur, int trait_epaisseur) {
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

        float left = Math.min(startX, endX);
        float right = Math.max(startX, endX);
        float top = Math.min(startY, endY);
        float bottom = Math.max(startY, endY);

        boundingBox = new RectF(left, top, right, bottom);

        path.reset();
        path.addOval(boundingBox, Path.Direction.CW);
    }
}