package com.example.tp2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class Forme { // Superclasse abstraite Forme
    // Attributs
    protected Path path;
    protected Paint paint;

    // Constructeur
    public Forme(int couleur, int trait_epaisseur) {
        path = new Path();
        paint = new Paint();
        paint.setColor(couleur);
        paint.setStrokeWidth(trait_epaisseur);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    // Méthodes à Override
    public abstract void startLine(float x, float y);

    public abstract void continueLine(float x, float y);
}
