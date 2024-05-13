package com.example.tpfinal;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Ecouteur ec;
    private TextView suite1, suite2, suite3, suite4, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Création du deck
        deck = new Deck();

        // Initialisation des éléments de l'interface
        suite1 = findViewById(R.id.suite1);
        suite2 = findViewById(R.id.suite2);
        suite3 = findViewById(R.id.suite3);
        suite4 = findViewById(R.id.suite4);
        carte1 = findViewById(R.id.choix_carte1);
        carte2 = findViewById(R.id.choix_carte2);
        carte3 = findViewById(R.id.choix_carte3);
        carte4 = findViewById(R.id.choix_carte4);
        carte5 = findViewById(R.id.choix_carte5);
        carte6 = findViewById(R.id.choix_carte6);
        carte7 = findViewById(R.id.choix_carte7);
        carte8 = findViewById(R.id.choix_carte8);

        // Création d'un tableau de cartes
        TextView[] cards = new TextView[]{carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8};

        // Pour chaque carte dans le tableau de cartes
        deck.assignCards(cards);

        // Initialisation de l'écouteur
        ec = new Ecouteur();

        // Ajout de l'écouteur aux suites
        suite1.setOnDragListener(ec);
        suite2.setOnDragListener(ec);
        suite3.setOnDragListener(ec);
        suite4.setOnDragListener(ec);
        carte1.setOnTouchListener(ec);
        carte2.setOnTouchListener(ec);
        carte3.setOnTouchListener(ec);
        carte4.setOnTouchListener(ec);
        carte5.setOnTouchListener(ec);
        carte6.setOnTouchListener(ec);
        carte7.setOnTouchListener(ec);
        carte8.setOnTouchListener(ec);

    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    // Get the card that is being dragged
                    TextView draggedCard = (TextView) event.getLocalState();
                    // Get the suite where the card is being dropped
                    TextView targetSuite = (TextView) view;
                    // Set the text of the target suite to the text of the dragged card
                    targetSuite.setText(draggedCard.getText());
                    // Make the dragged card invisible
                    draggedCard.setVisibility(View.INVISIBLE);
                    return true;
                default:
                    break;
            }
            return true;
        }


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }
}