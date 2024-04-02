package com.example.tp2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class TraceLibre {
    private Path path;
    private Paint paint;

    public TraceLibre(int couleur, int trait_epaisseur) {
        path = new Path();
        paint = new Paint();
        paint.setColor(couleur);
        paint.setStrokeWidth(trait_epaisseur);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    public void startLine(float x, float y) {
        path.moveTo(x, y);
    }

    public void continueLine(float x, float y) {
        path.lineTo(x, y);
    }

    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
