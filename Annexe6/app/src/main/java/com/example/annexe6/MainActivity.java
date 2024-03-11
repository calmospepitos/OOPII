package com.example.annexe6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout parent;
    Surface surf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parent);

        // 1
        surf = new Surface(this);
        // 2
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1)); // -1,-1 est la même chose que MATCH_PARENT, MATCH_PARENT
        surf.setBackgroundColor(Color.RED);
        // 3
        parent.addView(surf);
    }

    // Convertir des dp en px
    public int convertirDpEnPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private class Surface extends View {

        Paint crayon, crayon2, crayon3, crayon4;

        public Surface(Context context) {   // Contexte est un synonyme de l'activité
            super(context);
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG); // Anti-aliasing pour lisser les bords
            crayon.setColor(Color.GREEN);
            crayon.setStyle(Paint.Style.STROKE); // Par défaut, le style est FILL (plein)
            crayon.setStrokeWidth(10);

            crayon2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon2.setColor(Color.YELLOW);
            crayon2.setStyle(Paint.Style.FILL);

            crayon3 = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon3.setColor(Color.BLUE);
            crayon3.setStyle(Paint.Style.FILL);

            crayon4 = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon4.setColor(Color.BLACK);
            crayon4.setStyle(Paint.Style.FILL);
        }

        // Appelé au départ et à chaque fois que j'appelle la méthode invalidate()
        // La méthode s'appelle automatiquement
        // Appelée également lorsque l'app est endommagée et qu'elle doit être redessinée
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // Dessiner deux cercle
            canvas.drawCircle(100, 100, 50, crayon);
            canvas.drawCircle(250, 100, 55, crayon2);

            // Dessiner un graphe en pointe de tarte
            canvas.drawArc(400, 50, 550, 200, 0, 120, true, crayon2);
            canvas.drawArc(400, 50, 550, 200, 120, 120, true, crayon3);
            canvas.drawArc(400, 50, 550, 200, 240, 120, true, crayon4);
        }
    }
}