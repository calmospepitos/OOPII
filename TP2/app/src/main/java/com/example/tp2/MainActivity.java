package com.example.tp2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Surface surf;
    LinearLayout parent;
    Path p;
    Ecouteur ec;
    EditText champX, champY;
    Button entreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the attributes
        parent = findViewById(R.id.parent);
        entreeButton = findViewById(R.id.entreeButton);
        champX = findViewById(R.id.champX);
        champY = findViewById(R.id.champY);

        // Initialize the surface
        surf = new Surface(this);
        surf.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        surf.setBackgroundColor(Color.LTGRAY);
        parent.addView(surf);

        p = new Path();
        ec = new Ecouteur();
    }

    public class Surface extends View {
        Paint crayon;
        public Surface(Context context) {
            super(context);

            // Initialisation du crayon
            crayon = new Paint(Paint.ANTI_ALIAS_FLAG);
            crayon.setStyle(Paint.Style.STROKE);
            crayon.setStrokeWidth(10);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(p, crayon);
        }
    }



    public class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String texteX = champX.getText().toString();
            String texteY = champY.getText().toString();
            // Transtypage des valeurs
            int intX = Integer.parseInt(texteX);
            int intY = Integer.parseInt(texteY);

            if (p.isEmpty()) {
                p.moveTo(intX, intY);
            }
            else {
                p.lineTo(intX, intY);
            }

            // Redessiner le nouveau path
            surf.invalidate();
        }
    }
}