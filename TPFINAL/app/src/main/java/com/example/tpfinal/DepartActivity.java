package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DepartActivity extends AppCompatActivity {
    private Ecouteur ec;
    private SingletonDatabase db;
    private TextView highScore;
    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        // Initialisation de la base de données
        db = new SingletonDatabase(this);
        db.ouvrirConnexion();

        // Initialisation des éléments de l'interface
        highScore = findViewById(R.id.highScore);
        startGameButton = findViewById(R.id.startGameButton);

        // Écouteur pour le bouton
        ec = new Ecouteur();

        // Ajout de l'écouteur au bouton
        startGameButton.setOnClickListener(ec);

        // Affichage du highscore
        highScore.setText("HIGHSCORE: " + db.getMeilleurScore());
    }

    public class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == startGameButton) {
                Intent intent = new Intent(DepartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    // Fermeture de la connexion à la base de données
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.fermerConnexion();
    }
}