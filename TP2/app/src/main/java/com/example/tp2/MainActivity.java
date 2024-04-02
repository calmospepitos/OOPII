package com.example.tp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Ecouteur ec;
    Surface surf;
    LinearLayout drawingView;
    Button blackButton, brownButton, yellowButton, orangeButton, redButton, blueButton;
    ImageView crayon, trait, efface, cercle, triangle, rectangle, fill, pipette, palette, undo, redo, enregistrer;
    Stack<TraceLibre> stack;
    TraceLibre traceLibre;
    Boolean isTraceLibre = false, isOval = false, isTriangle = false, isRectangle = false, isFill = false, isPipette = false, isPalette = false;
    int couleur = Color.BLACK;
    int trait_epaisseur = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise les éléments de l'interface
        drawingView = findViewById(R.id.drawingView);
        blackButton = findViewById(R.id.blackButton);
        brownButton = findViewById(R.id.brownButton);
        yellowButton = findViewById(R.id.yellowButton);
        orangeButton = findViewById(R.id.orangeButton);
        redButton = findViewById(R.id.redButton);
        blueButton = findViewById(R.id.blueButton);
        crayon = findViewById(R.id.crayon);
        trait = findViewById(R.id.trait);
        efface = findViewById(R.id.efface);
        cercle = findViewById(R.id.cercle);
        triangle = findViewById(R.id.triangle);
        rectangle = findViewById(R.id.rectangle);
        fill = findViewById(R.id.fill);
        pipette = findViewById(R.id.pipette);
        palette = findViewById(R.id.palette);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        enregistrer = findViewById(R.id.enregistrer);

        // 1ere étape: Créer une instance de Ecouteur
        ec = new Ecouteur();

        // 2e étape: Créer une instance de Surface
        surf = new Surface(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        drawingView.addView(surf);

        // Initialisation des path Stacks
        stack = new Stack<>();

        // 3e étape: Gestion des événements
        // Clique sur les boutons
        blackButton.setOnClickListener(ec);
        brownButton.setOnClickListener(ec);
        yellowButton.setOnClickListener(ec);
        orangeButton.setOnClickListener(ec);
        redButton.setOnClickListener(ec);
        blueButton.setOnClickListener(ec);
        crayon.setOnClickListener(ec);
        trait.setOnClickListener(ec);
        efface.setOnClickListener(ec);
        cercle.setOnClickListener(ec);
        triangle.setOnClickListener(ec);
        rectangle.setOnClickListener(ec);
        fill.setOnClickListener(ec);
        pipette.setOnClickListener(ec);
        palette.setOnClickListener(ec);
        undo.setOnClickListener(ec);
        redo.setOnClickListener(ec);
        enregistrer.setOnClickListener(ec);
        // Touch sur la surface de dessin
        surf.setOnTouchListener(ec);
    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {
        @Override
        public void onClick(View source) {
            if (source instanceof Button) {
                couleur = Color.parseColor(source.getTag().toString());
            }
            else if (source == crayon) {
                isTraceLibre = !isTraceLibre;
            }
            else if (source == trait) {
                // Faire défiler les valeurs d'épaisseur de trait entre mince, moyen et large
                if (trait_epaisseur == 10) {
                    trait_epaisseur = 15;
                }
                else if (trait_epaisseur == 15) {
                    trait_epaisseur = 20;
                }
                else {
                    trait_epaisseur = 10;
                }
            }
            else if (source == efface) {

            }
            else if (source == cercle) {

            }
            else if (source == triangle) {

            }
            else if (source == rectangle) {

            }
            else if (source == fill) {

            }
            else if (source == pipette) {

            }
            else if (source == palette) {

            }
            else if (source == undo) {

            }
            else if (source == redo) {

            }
            else if (source == enregistrer) {

            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isTraceLibre && event.getAction() == MotionEvent.ACTION_DOWN) {
                traceLibre = new TraceLibre(couleur, trait_epaisseur);
                traceLibre.startLine(event.getX(), event.getY());
                surf.invalidate();
                return true;
            }
            else if (isTraceLibre && event.getAction() == MotionEvent.ACTION_MOVE) {
                traceLibre.continueLine(event.getX(), event.getY());
                surf.invalidate();
                return true;
            }
            else if (isTraceLibre && event.getAction() == MotionEvent.ACTION_UP) {
                stack.push(traceLibre);
            }
            return true;
        }
    }

    public class Surface extends View {
        public Surface(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (TraceLibre traceLibre : stack) {
                traceLibre.draw(canvas);
            }
            if (traceLibre != null) {
                traceLibre.draw(canvas);
            }
        }
    }

}
