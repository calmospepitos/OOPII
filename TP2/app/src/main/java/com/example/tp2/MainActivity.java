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
import android.widget.Switch;

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
    Stack<Forme> stack;
    String isSelected = null;

    // Les objets tracés
    TraceLibre traceLibreLine;
    Efface effaceLine;
    Rectangle rectangleLine;
    Triangle triangleLine;
    Oval ovalLine;

    // Attributs des crayons
    int couleur = Color.BLACK; // Valeur de défaut est noir
    int backgroundColor = Color.WHITE; // Valeur de défaut est blanc
    int trait_epaisseur = 10; // Valeur de défaut est 10px

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
            if (source instanceof Button) { // Si clique sur une couleur
                couleur = Color.parseColor(source.getTag().toString());
            }
            else if (source == crayon) { // Si clique sur le crayon
                isSelected = crayon.getTag().toString();
            }
            else if (source == trait) { // Si clique sur la largeur d'un trait
                showThicknessDialog();
            }
            else if (source == efface) { // Si clique sur l'efface
                isSelected = efface.getTag().toString();
            }
            else if (source == oval) { // Si clique sur l'oval
                isSelected = oval.getTag().toString();
            }
            else if (source == triangle) { // Si clique sur triangle
                isSelected = triangle.getTag().toString();
            }
            else if (source == rectangle) { // Si clique sur rectangle
                isSelected = rectangle.getTag().toString();
            }
            else if (source == fill) { // Si clique sur fill
                // Change la couleur du canvas
                backgroundColor = couleur;
                surf.setBackgroundColor(backgroundColor);

                // Change la couleur des traits d'efface
                for (Forme forme : stack) {
                    if (forme instanceof Efface) {
                        forme.paint.setColor(backgroundColor);
                    }
                }
            }
            else if (source == pipette) { // Si clique sur pipette

            }
            else if (source == palette) { // Si clique sur palette
                // À faire en bonus...
            }
            else if (source == undo) { // Si clique sur undo
                // À faire en bonus...
            }
            else if (source == redo) { // Si clique sur redo
                // À faire en bonus...
            }
            else if (source == enregistrer) { // Si clique sur enregistrer
                // À faire en bonus...
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isSelected == crayon.getTag().toString()) { // Si sélectionne le crayon
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
            else if (isSelected == efface.getTag().toString()) { // Si sélectionne l'efface
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
            else if (isSelected == oval.getTag().toString()) { // Si sélectionne l'oval
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ovalLine = new Oval(couleur, trait_epaisseur);
                    ovalLine.startLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    ovalLine.continueLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stack.push(ovalLine);
                }
                return true;
            }
            else if (isSelected == triangle.getTag().toString()) { // Si sélectionne le triangle
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    triangleLine = new Triangle(couleur, trait_epaisseur);
                    triangleLine.startLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    triangleLine.continueLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stack.push(triangleLine);
                }
                return true;
            }
            else if (isSelected == rectangle.getTag().toString()) { // Si sélectionne le rectangle
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    rectangleLine = new Rectangle(couleur, trait_epaisseur);
                    rectangleLine.startLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    rectangleLine.continueLine(event.getX(), event.getY());
                    surf.invalidate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stack.push(rectangleLine);
                }
                return true;
            }
            return true;
        }
    }

    // Boite de dialogue pour choisir la largeur de trait des crayons
    private void showThicknessDialog() {
        // Choix de largeur de trait
        CharSequence[] items = {"Mince", "Moyen", "Large"};

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
                        trait_epaisseur = 40;
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

            // Dessine toutes les formes
            for (Forme forme : stack) {
                forme.draw(canvas);
            }

            // Si dessine avec TraceLibre
            if (traceLibreLine != null && isSelected == crayon.getTag().toString()) {
                traceLibreLine.draw(canvas);
            }
            // Si dessine avec Efface
            if (effaceLine != null && isSelected == efface.getTag().toString()) {
                effaceLine.draw(canvas);
            }
            // Si dessine un Oval
            if (ovalLine != null && isSelected == oval.getTag().toString()) {
                ovalLine.draw(canvas);
            }
            // Si dessine un triangle
            if (triangleLine != null && isSelected == triangle.getTag().toString()) {
                triangleLine.draw(canvas);
            }
            // Si dessine avec Rectangle
            if (rectangleLine != null && isSelected == rectangle.getTag().toString()) {
                rectangleLine.draw(canvas);
            }
        }
    }

}
