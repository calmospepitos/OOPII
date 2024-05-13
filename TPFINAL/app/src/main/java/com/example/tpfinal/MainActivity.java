package com.example.tpfinal;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Ecouteur ec;
    private ImageView retour;
    private TextView suite1, suite2, suite3, suite4, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8;
    private TextView[] cartes, suites;
    private HashMap<TextView, Carte> carteMap;
    private HashMap<TextView, Suite> suiteMap;
    private Deck deck;
    private int cardCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        retour = findViewById(R.id.retour);

        // Création d'un tableau de cartes
        cartes = new TextView[]{carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8};
        suites = new TextView[]{suite1, suite2, suite3, suite4};

        // HashMap pour les suites et cartes
        suiteMap = new HashMap<>();
        carteMap = new HashMap<>();

        // Création du deck et pour chaque carte dans le tableau de cartes
        deck = new Deck();
        deck.assignCards(cartes);

        // Initialisation de l'écouteur
        ec = new Ecouteur();

        // Ajout de l'écouteur aux suites
        int i = 0;
        for (TextView suite : suites) {
            suite.setOnDragListener(ec);
            Suite newSuite;
            Carte startingCard;
            if (i <2) {
                newSuite = new Suite(false);
                startingCard = new Carte(97);
                suiteMap.put(suite, new Suite(false));

            }
            else {
                newSuite = new Suite(true);
                startingCard = new Carte(0);
            }
            newSuite.getCartes().add(startingCard);
            suiteMap.put(suite, newSuite);
            i++;
        }

        // Ajout de l'écouteur aux cartes
        for (TextView card : cartes) {
            card.setOnTouchListener(ec);
            carteMap.put(card, new Carte(Integer.parseInt(card.getText().toString())));
        }

        // Ajout de l'écouteur au bouton retour
        retour.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener, View.OnClickListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            int action = event.getAction();
            TextView draggedCard = (TextView) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DROP:
                    // Regarde si le view est une suite
                    if (suiteMap.containsKey(view)) {
                        TextView targetSuite = (TextView) view;
                        Carte carte = carteMap.get(draggedCard);
                        Suite suite = suiteMap.get(targetSuite);
                        if (suite.receiveCarte(carte)) {
                            targetSuite.setText(draggedCard.getText());
                            draggedCard.setVisibility(View.INVISIBLE);
                            Carte derniereCarte = suite.getCartes().get(suite.getCartes().size() - 1);
                            deck.setDernierMouvement(carte, derniereCarte, draggedCard, targetSuite);
                            cardCount++;
                            // Si deux cartes ont été déposées, on tire deux nouvelles cartes
                            if (cardCount == 2) {
                                List<Carte> newCards = deck.drawTwoCards();
                                displayNewCards(newCards);
                                cardCount = 0;
                            }
                        }
                        else {
                            // Si le drop n'a pas été effectué, on rend la carte visible
                            draggedCard.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        // Si le view n'est pas une suite, on le rend visible
                        draggedCard.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Si le drop n'a pas été effectué, on rend la carte visible
                    if (!event.getResult()) {
                        draggedCard.setVisibility(View.VISIBLE);
                    }
                    break;
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

        @Override
        public void onClick(View view) {
            if (view == retour) {
                if (cardCount == 1) {
                    annulerDernierMouvement();
                }
            }
        }
    }

    public void displayNewCards(List<Carte> newCards) {
        for (TextView carte : cartes) {
            if (carte.getVisibility() == View.INVISIBLE) {
                Carte newCard = newCards.remove(0);
                carte.setText(String.valueOf(newCard.getValeur()));
                carte.setVisibility(View.VISIBLE);
                carteMap.put(carte, newCard);
                if (newCards.isEmpty()) {
                    break;
                }
            }
        }
    }

    public void annulerDernierMouvement() {
        if (cardCount == 1) {
            Mouvement dernierMouvement = deck.getLastMove();
            if (dernierMouvement != null) {
                dernierMouvement.annulerDernierMouvement();
                TextView originalView = dernierMouvement.getOriginalView();
                TextView targetView = dernierMouvement.getTargetView();
                Carte carte = dernierMouvement.getCarte();
                Carte derniereCarte = dernierMouvement.getDerniereCarte();
                carteMap.put(originalView, carte);
                Suite targetSuite = suiteMap.get(targetView);
                if (targetSuite != null) {
                    targetSuite.getCartes().add(derniereCarte);
                }
                cardCount--;
            }
        }
    }
}