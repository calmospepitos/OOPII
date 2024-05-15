package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Attributs
    private Ecouteur ec;
    private SingletonDatabase db;
    private ImageView retour;
    private TextView suite1, suite2, suite3, suite4, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, cartesRestantes, score;
    private TextView[] cartes, suites;
    private Chronometer chronometer, chronometerCoups;
    private HashMap<TextView, Carte> carteMap;
    private HashMap<TextView, Suite> suiteMap;
    private Deck deck;
    private Partie partie;
    private int cardCount = 0, cartesRestantesCount = 97, i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la base de données
        db = new SingletonDatabase(this);
        db.ouvrirConnexion();

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
        chronometer = findViewById(R.id.simpleChronometer);
        cartesRestantes = findViewById(R.id.cartes_restantes);
        score = findViewById(R.id.score);
        retour = findViewById(R.id.retour);

        // Création d'un tableau de cartes
        cartes = new TextView[]{carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8};
        suites = new TextView[]{suite1, suite2, suite3, suite4};

        // HashMap pour les suites et cartes
        suiteMap = new HashMap<>();
        carteMap = new HashMap<>();

        // Créer une partie et part le chronomètre
        partie = new Partie(cartes);
        deck = partie.getDeck();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        // Créer un chronomètre pour les coups
        chronometerCoups = new Chronometer(this);
        chronometerCoups.setBase(SystemClock.elapsedRealtime());
        chronometerCoups.start();

        // Initialisation de l'écouteur
        ec = new Ecouteur();

        // Ajout de l'écouteur aux éléments de l'interface
        retour.setOnClickListener(ec);

        for (TextView suite : suites) {
            suite.setOnDragListener(ec);
            Suite newSuite;
            Carte startingCard;
            if (i < 2) {
                newSuite = new Suite(false);
                startingCard = new Carte(98);
                suiteMap.put(suite, new Suite(false));
            } else {
                newSuite = new Suite(true);
                startingCard = new Carte(0);
            }
            newSuite.getCartes().add(startingCard);
            suiteMap.put(suite, newSuite);
            i++;
        }

        for (TextView card : cartes) {
            card.setOnTouchListener(ec);
            carteMap.put(card, new Carte(Integer.parseInt(card.getText().toString())));
        }
    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener, View.OnClickListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            int action = event.getAction();
            TextView draggedCarte = (TextView) event.getLocalState();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    // Regarde si le view est une suite
                    if (suiteMap.containsKey(view)) {
                        TextView targetSuite = (TextView) view;
                        Carte derniereCarte = new Carte(Integer.parseInt(targetSuite.getText().toString()));
                        Carte carte = carteMap.get(draggedCarte);
                        Suite suite = suiteMap.get(targetSuite);
                        // Si la carte peut être ajoutée à la suite
                        if (suite.receiveCarte(carte)) {
                            targetSuite.setText(draggedCarte.getText());
                            draggedCarte.setVisibility(View.INVISIBLE);
                            deck.setDernierMouvement(carte, derniereCarte, draggedCarte, targetSuite);
                            cardCount++;
                            Log.d("DEBUG", "cardCount: " + cardCount);
                            // Décrémente le nombre de cartes restantes
                            cartesRestantesCount--;
                            cartesRestantes.setText(String.valueOf(cartesRestantesCount));
                            // Calcul du score
                            long tempsPrit = SystemClock.elapsedRealtime() - chronometerCoups.getBase();
                            partie.calculerScore(carte, derniereCarte, tempsPrit, cartesRestantesCount);
                            score.setText(String.valueOf(partie.getScore()));
                            chronometerCoups.setBase(SystemClock.elapsedRealtime());
                            // Si deux cartes ont été déposées, on tire deux nouvelles cartes
                            if (cardCount == 2) {
                                List<Carte> newCards = deck.drawTwoCards();
                                afficheNouvellesCartes(newCards);
                                cardCount = 0;
                            }
                        }
                        else {
                            // Si le drop n'a pas été effectué, on rend la carte visible
                            draggedCarte.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        // Si le view n'est pas une suite, on le rend visible
                        draggedCarte.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Si le drop n'a pas été effectué, on rend la carte visible
                    if (!event.getResult()) {
                        draggedCarte.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
            regardeFinJeu();
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
                Mouvement dernierMouvement = deck.getDernierMouvement();
                if (cardCount == 1 && dernierMouvement != null) {
                    dernierMouvement.annulerDernierMouvement(carteMap, suiteMap, partie);
                    // Ajuste le score affiché
                    score.setText(String.valueOf(partie.getScore()));
                    // Reset le chronomètre des coups
                    chronometerCoups.setBase(SystemClock.elapsedRealtime());
                    // Incrémente le nombre de cartes restantes et décrémente le nombre de cartes jouées
                    cardCount--;
                    cartesRestantesCount++;
                    cartesRestantes.setText(String.valueOf(cartesRestantesCount));
                }
            }
        }
    }

    public void afficheNouvellesCartes(List<Carte> newCards) {
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

    public boolean isMouvementPossible() {
        for (TextView carteView : cartes) {
            Carte carte = carteMap.get(carteView);
            for (TextView suiteView : suites) {
                Suite suite = suiteMap.get(suiteView);
                Log.d("DEBUG", "Regarde si le mouvement est possible.");
                if (suite.canAddCarte(carte)) {
                    Log.d("DEBUG", "Mouvement possible.");
                    return true;
                }
            }
        }
        Log.d("DEBUG", "Mouvement impossible.");
        return false;
    }

    public void regardeFinJeu() {
        if (cartesRestantesCount == 0 || !isMouvementPossible()) {
            Log.d("DEBUG", "Fin de jeu triggered.");
            // Insère le score dans la base de données
            db.insererScore(partie.getScore());

            // Arrête les chronomètres
            chronometer.stop();
            chronometerCoups.stop();

            // Affiche l'activité de fin de jeu
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("cartesRestantes", cartesRestantesCount);
            startActivity(intent);
            finish();
        }
    }
}