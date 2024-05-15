package com.example.tpfinal;

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
    private Partie partie;
    private Deck deck;
    private TextView suite1, suite2, suite3, suite4, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, cartesRestantes, score;
    private TextView[] cartes, suites;
    private HashMap<TextView, Carte> carteMap;
    private HashMap<TextView, Suite> suiteMap;
    private ImageView retour;
    private Chronometer chronometer, chronometerCoups;
    private int cardCount = 0, cartesRestantesCount = 97;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la base de données
        db = new SingletonDatabase(this);
        db.ouvrirConnexion();

        // Initialisation des éléments de l'interface
        chronometer = findViewById(R.id.simpleChronometer);
        cartesRestantes = findViewById(R.id.cartes_restantes);
        score = findViewById(R.id.score);
        retour = findViewById(R.id.retour);

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
        cartes = new TextView[]{carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8};
        suites = new TextView[]{suite1, suite2, suite3, suite4};

        // HashMap pour les suites et cartes
        suiteMap = new HashMap<>();
        carteMap = new HashMap<>();

        // Part les chronomètres et crée une nouvelle partie
        chronometerCoups = new Chronometer(this);
        chronometerCoups.setBase(SystemClock.elapsedRealtime());
        chronometerCoups.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        partie = new Partie(this, db, chronometer, chronometerCoups);
        deck = new Deck();
        deck.assignCards(cartes);

        // Initialisation de l'écouteur
        ec = new Ecouteur();

        // Ajout de l'écouteur aux éléments de l'interface
        int i = 0;
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

        retour.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener, View.OnClickListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            TextView draggedCarte = (TextView) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    // Regarde si le view est une suite
                    if (suiteMap.containsKey(view)) {
                        // Récupère la suite et la carte
                        TextView targetSuite = (TextView) view;
                        Carte derniereCarte = new Carte(Integer.parseInt(targetSuite.getText().toString()));
                        Carte carte = carteMap.get(draggedCarte);
                        Suite suite = suiteMap.get(targetSuite);
                        // Si la carte peut être ajoutée à la suite
                        if (suite.receiveCarte(carte)) {
                            // Ajoute la carte à la suite et met à jour l'affichage
                            targetSuite.setText(draggedCarte.getText());
                            draggedCarte.setVisibility(View.INVISIBLE);
                            deck.setDernierMouvement(carte, derniereCarte, draggedCarte, targetSuite);
                            // Incrémente le nombre de cartes jouées et décrémente le nombre de cartes restantes
                            cardCount++;
                            cartesRestantesCount--;
                            cartesRestantes.setText(String.valueOf(cartesRestantesCount));
                            // Calcul du score
                            long tempsPrit = SystemClock.elapsedRealtime() - chronometerCoups.getBase();
                            partie.calculerScore(carte, derniereCarte, tempsPrit, cartesRestantesCount);
                            score.setText(String.valueOf(partie.getScore()));
                            chronometerCoups.setBase(SystemClock.elapsedRealtime());
                            // Si deux cartes ont été déposées, on tire deux nouvelles cartes
                            if (cardCount == 2) {
                                List<Carte> newCartes = deck.drawTwoCards();
                                deck.afficheNouvellesCartes(newCartes, cartes, carteMap);
                                cardCount = 0;
                            }
                        } else {
                            // Si le view n'est pas une suite, on le rend visible
                            draggedCarte.setVisibility(View.VISIBLE);
                        }
                    } else {
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
            Log.d("DEBUG", "Reached end of onDrag");
            partie.regardeFinPartie(cartesRestantesCount);
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
                    // Reset le chronomètre des coups
                    chronometerCoups.setBase(SystemClock.elapsedRealtime());
                    // Incrémente le nombre de cartes restantes et décrémente le nombre de cartes jouées
                    cardCount--;
                    cartesRestantesCount++;
                    // Met à jour l'affichage des stats
                    score.setText(String.valueOf(partie.getScore()));
                    cartesRestantes.setText(String.valueOf(cartesRestantesCount));
                }
            }
        }
    }

    public boolean isMouvementPossible() {
        for (TextView carteView : cartes) {
            if (carteView.getVisibility() == View.INVISIBLE) {
                continue;
            }
            Carte carte = carteMap.get(carteView);
            for (TextView suiteView : suites) {
                Suite suite = suiteMap.get(suiteView);
                if (suite.canAddCarte(carte)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Ferme la connexion à la base de données
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.fermerConnexion();
    }
}