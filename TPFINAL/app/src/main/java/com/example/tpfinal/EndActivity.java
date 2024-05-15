package com.example.tpfinal;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class EndActivity extends AppCompatActivity {
    // Attributs
    private SingletonDatabase db;
    private int cartesRestantes;
    private TextView messageFin, highScore1, highScore2, highScore3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        // Initialisation de la base de données
        db = new SingletonDatabase(this);
        db.ouvrirConnexion();

        // Initialisation des éléments de l'interface
        messageFin = findViewById(R.id.messageFin);
        highScore1 = findViewById(R.id.highscore1);
        highScore2 = findViewById(R.id.highscore2);
        highScore3 = findViewById(R.id.highscore3);

        // Récupération des données de la partie
        cartesRestantes = getIntent().getIntExtra("cartesRestantes", 97);
        List<String> topScores = db.getTopScores();

        // Affichage du message de fin
        if (cartesRestantes == 0) {
            messageFin.setText("BIEN JOUÉ!");
        }
        else {
            messageFin.setText("DÉSOLÉ, VOUS AVEZ PERDU.");
        }

        // Affichage des scores
        if (topScores.size() > 0) {
            highScore1.setText("1 - " + topScores.get(0));
        }
        if (topScores.size() > 1) {
            highScore2.setText("2 - " + topScores.get(1));
        }
        if (topScores.size() > 2) {
            highScore3.setText("3 - " + topScores.get(2));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.fermerConnexion();
    }
}
