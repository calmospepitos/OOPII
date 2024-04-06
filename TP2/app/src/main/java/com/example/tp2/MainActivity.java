package com.example.tp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private Ecouteur ec;
    private Surface surf;
    private LinearLayout drawingView, barreOutils, barreCouleurs;
    private Button blackButton, greenButton, yellowButton, orangeButton, redButton, pinkButton, blueButton, whiteButton, brownButton, videButton;
    private ImageView crayon, trait, efface, oval, triangle, rectangle, fill, pipette, palette, undo, redo, enregistrer;
    private DialogBox dialogBox;
    private Stack<Forme> stack, undoStack;
    private String isSelected = null;

    // Les objets tracés
    private TraceLibre traceLibreLine;
    private Efface effaceLine;
    private Rectangle rectangleLine;
    private Triangle triangleLine;
    private Oval ovalLine;

    // Attributs des crayons
    private int couleur = Color.BLACK; // Valeur de défaut est noir
    private int backgroundColor = Color.WHITE; // Valeur de défaut est blanc
    private int trait_epaisseur = 10; // Valeur de défaut est 10px

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise les éléments de l'interface
        barreCouleurs = findViewById(R.id.barre_couleurs);
        barreOutils = findViewById(R.id.barre_outils);
        drawingView = findViewById(R.id.drawingView);
        blackButton = findViewById(R.id.blackButton);
        greenButton = findViewById(R.id.greenButton);
        yellowButton = findViewById(R.id.yellowButton);
        orangeButton = findViewById(R.id.orangeButton);
        redButton = findViewById(R.id.redButton);
        pinkButton = findViewById(R.id.pinkButton);
        blueButton = findViewById(R.id.blueButton);
        whiteButton = findViewById(R.id.whiteButton);
        brownButton = findViewById(R.id.brownButton);
        videButton = findViewById(R.id.videButton);
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

        // Initialisation du dialogBox
        dialogBox = new DialogBox(this, new DialogBox.OnThicknessSelectedListener() {
            @Override
            public void onThicknessSelected(int thickness) {
                trait_epaisseur = thickness;
            }
        });

        // Initialisation des path Stacks
        stack = new Stack<>();
        undoStack = new Stack<>();

        // 3e étape: Gestion des événements
        for (int i = 0; i < barreCouleurs.getChildCount(); i++) { // Écouteurs des couleurs
            barreCouleurs.getChildAt(i).setOnClickListener(ec);
        }
        for (int i = 0; i < barreOutils.getChildCount(); i++) { // Écouteurs des outils
            barreOutils.getChildAt(i).setOnClickListener(ec);
        }
        surf.setOnTouchListener(ec); // Touch sur la surface de dessin
    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {
        @Override
        public void onClick(View source) {
            if (source instanceof Button) { // Si clique sur une couleur
                couleur = Color.parseColor(source.getTag().toString());
            }
            else if (source == fill) { // Si clique sur le bouton de remplissage
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
            else if (source == trait) { // Si clique sur le bouton de trait
                dialogBox.show();
            }
            else if (source == undo) { // Si clique sur le bouton retour
                if (!stack.isEmpty()) {
                    Forme lastAction = stack.pop(); // Pop the last action from the regular stack
                    undoStack.push(lastAction); // Push it onto the undo stack
                    surf.invalidate(); // Redraw the canvas
                }
            }
            else if (source == redo) { // Si clique sur le bouton refaire
                if (!undoStack.isEmpty()) {
                    Forme lastUndoAction = undoStack.pop(); // Pop the last action from the undo stack
                    stack.push(lastUndoAction); // Push it onto the regular stack
                    surf.invalidate(); // Redraw the canvas
                }
            }
            else if (source == enregistrer) { // Si clique sur le bouton enregistrer
                // A faire en bonus
            }
            else if (source == palette) {
                // A faire en bonus
            }
            else { // Si clique sur les autres boutons
                isSelected = source.getTag().toString();
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (isSelected != null) {
                switch (isSelected) {
                    case "Crayon":
                        traceLibreLine = handleDrawingEvent(traceLibreLine, TraceLibre.class, event);
                        break;
                    case "Efface":
                        effaceLine = handleDrawingEvent(effaceLine, Efface.class, event);
                        break;
                    case "Oval":
                        ovalLine = handleDrawingEvent(ovalLine, Oval.class, event);
                        break;
                    case "Triangle":
                        triangleLine = handleDrawingEvent(triangleLine, Triangle.class, event);
                        break;
                    case "Rectangle":
                        rectangleLine = handleDrawingEvent(rectangleLine, Rectangle.class, event);
                        break;
                    case "Pipette":
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Récupère la couleur du pixel
                            int x = (int) event.getX();
                            int y = (int) event.getY();
                            int pixelColor = surf.getPixelColor(x, y);

                            // Utilise la couleur récupérée
                            couleur = pixelColor;
                            videButton.setTag("#" + Integer.toHexString(couleur)); // Change le tag pour la couleur du bouton
                            videButton.setBackgroundTintList(ColorStateList.valueOf(couleur)); // Change la couleur du bouton
                        }
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    // Méthode qui s'occupe de dessiner les lignes de toutes les Formes
    private <T extends Forme> T handleDrawingEvent(T currentShape, Class<T> shapeClass, MotionEvent event) {
        T updatedShape = currentShape;

        if (event.getAction() == MotionEvent.ACTION_DOWN) { // Clique la souris
            try {
                if (shapeClass == Efface.class) { // Si c'est un Efface
                    updatedShape = shapeClass.getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(backgroundColor, trait_epaisseur);
                }
                else { // Si c'est une autre Forme
                    updatedShape = shapeClass.getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(couleur, trait_epaisseur);
                }
                updatedShape.startLine(event.getX(), event.getY());
                surf.invalidate();
            }
            catch (Exception e) {
                e.printStackTrace(); // Affiche l'erreur
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) { // Bouge la souris
            if (updatedShape != null) {
                updatedShape.continueLine(event.getX(), event.getY());
                surf.invalidate();
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) { // Relache la souris
            if (updatedShape != null) {
                stack.push(updatedShape);
            }
        }
        return updatedShape;
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

        // Méthode qui trouve la couleur du pixel où tu clique
        public int getPixelColor(int x, int y) {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888); // Crée un bitmap
            Canvas canvas = new Canvas(bitmap); // Crée un canvas du bitmap
            draw(canvas); // Dessine sur le canvas
            return bitmap.getPixel(x, y); // Retourne la couleur du pixel
        }
    }

}
