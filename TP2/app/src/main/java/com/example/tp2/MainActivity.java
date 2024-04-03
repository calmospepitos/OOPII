package com.example.tp2;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Ecouteur ec;
    Surface surf;
    LinearLayout drawingView;
    Button blackButton, greenButton, yellowButton, orangeButton, redButton, blueButton, whiteButton, brownButton;
    ImageView crayon, trait, efface, oval, triangle, rectangle, fill, pipette, palette, undo, redo, enregistrer;
    int couleur = Color.BLACK;
    int backgroundColor = Color.WHITE;
    int trait_epaisseur = 10;
    String isSelected = null;
    Stack<Forme> stack;
    TraceLibre traceLibreLine;
    Efface effaceLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise les éléments de l'interface
        drawingView = findViewById(R.id.drawingView);
        blackButton = findViewById(R.id.blackButton);
        greenButton = findViewById(R.id.greenButton);
        yellowButton = findViewById(R.id.yellowButton);
        orangeButton = findViewById(R.id.orangeButton);
        redButton = findViewById(R.id.redButton);
        blueButton = findViewById(R.id.blueButton);
        whiteButton = findViewById(R.id.whiteButton);
        brownButton = findViewById(R.id.brownButton);
        crayon = findViewById(R.id.crayon);
        trait = findViewById(R.id.trait);
        efface = findViewById(R.id.efface);
        oval = findViewById(R.id.oval);
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
        greenButton.setOnClickListener(ec);
        yellowButton.setOnClickListener(ec);
        orangeButton.setOnClickListener(ec);
        redButton.setOnClickListener(ec);
        blueButton.setOnClickListener(ec);
        whiteButton.setOnClickListener(ec);
        brownButton.setOnClickListener(ec);
        crayon.setOnClickListener(ec);
        trait.setOnClickListener(ec);
        efface.setOnClickListener(ec);
        oval.setOnClickListener(ec);
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
                isSelected = crayon.getTag().toString();
            }
            else if (source == trait) {
                showThicknessDialog();
            }
            else if (source == efface) {
                isSelected = efface.getTag().toString();
            }
            else if (source == oval) {
                isSelected = oval.getTag().toString();
            }
            else if (source == triangle) {
                isSelected = triangle.getTag().toString();
            }
            else if (source == rectangle) {
                isSelected = rectangle.getTag().toString();
            }
            else if (source == fill) {
                backgroundColor = couleur;
                surf.setBackgroundColor(backgroundColor);

                for (Forme forme : stack) {
                    if (forme instanceof Efface) {
                        forme.paint.setColor(backgroundColor);
                    }
                }
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
            if (isSelected == crayon.getTag().toString()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    traceLibreLine = new TraceLibre(couleur, trait_epaisseur);
                    traceLibreLine.startLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    traceLibreLine.continueLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stack.push(traceLibreLine);
                }
                return true;
            }
            else if (isSelected == efface.getTag().toString()) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    effaceLine = new Efface(backgroundColor, trait_epaisseur);
                    effaceLine.startLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    effaceLine.continueLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stack.push(effaceLine);
                }
                return true;
            }
            else if (isSelected == oval.getTag().toString()) {

            }
            else if (isSelected == triangle.getTag().toString()) {

            }
            else if (isSelected == rectangle.getTag().toString()) {

            }
            return true;
        }
    }

    private void showThicknessDialog() {
        final CharSequence[] items = {"Mince", "Moyen", "Large"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sélectionnez l'épaisseur du trait");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        trait_epaisseur = 10;
                        break;
                    case 1:
                        trait_epaisseur = 20;
                        break;
                    case 2:
                        trait_epaisseur = 30;
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class Surface extends View {
        public Surface(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (Forme forme : stack) {
                forme.draw(canvas);
            }

            if (traceLibreLine != null) {
                traceLibreLine.draw(canvas);
            }
            if (effaceLine != null) {
                effaceLine.draw(canvas);
            }
        }
    }

}
