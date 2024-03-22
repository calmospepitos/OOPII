package com.example.annexe7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout parent;
    Surface surf;
    Point depart, arrivee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parent);

        // Étape 1
        surf = new Surface(this);
        // Étape 2
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        surf.setBackgroundResource(R.drawable.carte);
        // Étape 3
        parent.addView(surf);

        // Gestion des evenements
        Ecouteur ec = new Ecouteur();
        surf.setOnTouchListener(ec);
    }

    private class Ecouteur implements View.OnTouchListener {
        @Override
        public boolean onTouch(View source, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                depart = new Point((int)motionEvent.getX(), (int)motionEvent.getY());
                surf.invalidate();
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                arrivee = new Point((int)motionEvent.getX(), (int)motionEvent.getY());
                surf.invalidate();
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                depart = null;
                arrivee = null;
            }

            return true; // Très important
        }
    }

    private class Surface extends View {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public Surface(Context context) {
            super(context);

            // Initialize paint for drawing the red square
            paint.setColor(Color.RED);
            paint.setStrokeWidth(15);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (depart != null) {
                canvas.drawRect(depart.x - 30, depart.y - 30, depart.x + 30, depart.y + 30, paint);
            }
            if (arrivee != null) {
                canvas.drawRect(arrivee.x - 30, arrivee.y - 30, arrivee.x + 30, arrivee.y + 30, paint);
                canvas.drawLine(depart.x, depart.y, arrivee.x, arrivee.y, paint);
            }
        }
    }
}