package com.example.tp2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    // Attributs
    private Ecouteur ec;
    private Surface surf;
    private LinearLayout drawingView, barreOutils, barreCouleurs;
    private Button videButton;
    private DialogBox dialogBox;
    private Stack<Forme> stack, undoStack;
    private String isSelected = null;

    // Attributs des crayons
    private int couleur = Color.BLACK; // Valeur de défaut est noir
    private int couleurFond = Color.WHITE; // Valeur de défaut est blanc
    private int trait_epaisseur = 10; // Valeur de défaut est 10px

    // Les objets tracés
    private TraceLibre traceLibreLine;
    private Efface effaceLine;
    private Rectangle rectangleLine;
    private Triangle triangleLine;
    private Oval ovalLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des éléments de l'interface
        barreCouleurs = findViewById(R.id.barre_couleurs);
        barreOutils = findViewById(R.id.barre_outils);
        drawingView = findViewById(R.id.drawingView);
        videButton = findViewById(R.id.videButton);

        // 1ere étape: Créer une instance de Ecouteur
        ec = new Ecouteur();

        // 2e étape: Créer une instance de Surface
        surf = new Surface(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        drawingView.addView(surf);

        // Initialisation des Stacks
        stack = new Stack<>();
        undoStack = new Stack<>();

        // Initialisation du dialogBox
        dialogBox = new DialogBox(this, new DialogBox.OnThicknessSelectedListener() {
            @Override
            public void onThicknessSelected(int thickness) {
                trait_epaisseur = thickness;
            }
        });

        // 3e étape: Gestion des événements
        surf.setOnTouchListener(ec); // Touch sur la surface de dessin
        for (int i = 0; i < barreCouleurs.getChildCount(); i++) { barreCouleurs.getChildAt(i).setOnClickListener(ec); }
        for (int i = 0; i < barreOutils.getChildCount(); i++) { barreOutils.getChildAt(i).setOnClickListener(ec); }
    }

    private class Ecouteur implements View.OnClickListener, View.OnTouchListener {
        @Override
        public void onClick(View source) {
            String choix = source.getTag().toString();

            if (source instanceof Button) { // Si clique sur une couleur
                couleur = Color.parseColor(source.getTag().toString());
            }
            else if (choix.equals("Fill")) { // Si clique sur le bouton de remplissage
                // Change la couleur du canvas
                couleurFond = couleur;
                surf.setBackgroundColor(couleurFond);

                // Change la couleur des traits d'efface
                for (Forme forme : stack) {
                    if (forme instanceof Efface) {
                        forme.paint.setColor(couleurFond);
                    }
                }
            }
            else if (choix.equals("Trait")) { // Si clique sur le bouton de trait
                dialogBox.show();
            }
            else if (choix.equals("Undo")) { // Si clique sur le bouton retour
                // A faire en bonus
            }
            else if (choix.equals("Redo")) { // Si clique sur le bouton refaire
                // A faire en bonus
            }
            else if (choix.equals("Enregistrer")) { // Si clique sur le bouton enregistrer
                // A faire en bonus
            }
            else if (choix.equals("Palette")) {
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
            if (traceLibreLine != null && isSelected.equals("Crayon")) {
                traceLibreLine.draw(canvas);
            }
            // Si dessine avec Efface
            if (effaceLine != null && isSelected.equals("Efface")) {
                effaceLine.draw(canvas);
            }
            // Si dessine un Oval
            if (ovalLine != null && isSelected.equals("Oval")) {
                ovalLine.draw(canvas);
            }
            // Si dessine un triangle
            if (triangleLine != null && isSelected.equals("Triangle")) {
                triangleLine.draw(canvas);
            }
            // Si dessine avec Rectangle
            if (rectangleLine != null && isSelected.equals("Rectangle")) {
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

    // Méthode qui s'occupe de dessiner les lignes de toutes les Formes
    private <T extends Forme> T handleDrawingEvent(T currentShape, Class<T> shapeClass, MotionEvent event) {
        T updatedShape = currentShape;

        if (event.getAction() == MotionEvent.ACTION_DOWN) { // Clique la souris
            try {
                if (shapeClass == Efface.class) { // Si c'est un Efface
                    updatedShape = shapeClass.getDeclaredConstructor(Integer.TYPE, Integer.TYPE).newInstance(couleurFond, trait_epaisseur);
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

}
