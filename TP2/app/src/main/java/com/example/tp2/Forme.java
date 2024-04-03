package com.example.tp2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public abstract class Forme {
    // Attributes
    protected Path path;
    protected Paint paint;

    // Constructor
    public Forme(int couleur, int trait_epaisseur) {
        path = new Path();
        paint = new Paint();
        paint.setColor(couleur);
        paint.setStrokeWidth(trait_epaisseur);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    // Methods
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public void startLine(float x, float y) {
        path.moveTo(x, y);
    }

    public void continueLine(float x, float y) {
        path.lineTo(x, y);
    }
}
