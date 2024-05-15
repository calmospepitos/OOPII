package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

        // Affichage du highscore
        highScore.setText("HIGHSCORE: " + db.getMeilleurScore());

        // Écouteur pour le bouton
        ec = new Ecouteur();

        // Ajout de l'écouteur au bouton
        startGameButton.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == startGameButton) {
                Context context = DepartActivity.this;
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}