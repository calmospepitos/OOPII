package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    private Ecouteur ec;
    private SingletonDatabase db;
    private TextView texteQuestion;
    private LinearLayout barreCouleurs, drapeauContenants;
    private Button boutonConfirmer;
    private Hashtable<String, Integer> colorTable;
    private String paysAleatoire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la base de données
        db = new SingletonDatabase(this);
        db.ouvrirConnexion();

        // Initialisation des éléments de l'interface
        texteQuestion = findViewById(R.id.texteQuestion);
        barreCouleurs = findViewById(R.id.barreCouleurs);
        drapeauContenants = findViewById(R.id.drapeauContenants);
        boutonConfirmer = findViewById(R.id.boutonConfirmer);

        // Initialise le Hashmap et insère des valeurs
        colorTable = new Hashtable<>();
        colorTable.put("blanc", -329737);
        colorTable.put("bleu", -13022805);
        colorTable.put("rouge", -849912);
        colorTable.put("noir", -16448251);
        colorTable.put("jaune", -19712);

        // Création de l'instance de l'écouteur
        ec = new Ecouteur();

        // Ajout de l'écouteur aux éléments de l'interface
        for (int i = 0; i < barreCouleurs.getChildCount(); i++) {
            barreCouleurs.getChildAt(i).setOnTouchListener(ec);
        }

        for (int i = 0; i < drapeauContenants.getChildCount(); i++) {
            drapeauContenants.getChildAt(i).setOnDragListener(ec);
        }

        boutonConfirmer.setOnClickListener(ec);

        // Affiche la question
        paysAleatoire = db.getPaysAleatoire();
        texteQuestion.setText("Dessinez le drapeau de la " + paysAleatoire);
    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener, View.OnClickListener, com.example.examenfinal.Ecouteur {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }

        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    TextView draggedView = (TextView) event.getLocalState();
                    String nomCouleur = (String) draggedView.getTag();
                    Integer colorValue = colorTable.get(nomCouleur);

                    // Si le nom de la couleur n'est pas trouvée
                    if (colorValue == null) {
                        return false;
                    }

                    LinearLayout targetView = (LinearLayout) view;
                    targetView.setBackgroundColor(colorValue);
                    targetView.setTag(nomCouleur);
                    draggedView.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!event.getResult()) {
                        ((View) event.getLocalState()).setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View view) {
            if (view == boutonConfirmer) {
                String[] couleurs = new String[drapeauContenants.getChildCount()];

                for (int i = 0; i < drapeauContenants.getChildCount(); i++) {
                    LinearLayout conteneur = (LinearLayout) drapeauContenants.getChildAt(i);
                    couleurs[i] = (String) conteneur.getTag();

                    // Regarde si une couleur n'est pas remplie
                    if (couleurs[i] == null) {
                        texteQuestion.setText("Please fill all the colors before confirming.");
                        return;
                    }
                }

                boolean match = db.verifierCouleurPays(paysAleatoire, couleurs[0], couleurs[1], couleurs[2]);
                if (match) {
                    texteQuestion.setText("BRAVO!");
                }
                else {
                    texteQuestion.setText("MAUVAISE RÉPONSE.");
                }
            }
        }

        @Override
        public void onDestroy() {
            db.fermerConnexion();
        }
    }
}