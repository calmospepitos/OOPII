package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartActivity extends AppCompatActivity {
    Ecouteur ec;
    Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        // Bouton pour démarrer le jeu
        startGameButton = findViewById(R.id.startGameButton);

        // Écouteur pour le bouton
        ec = new Ecouteur();

        // Ajout de l'écouteur au bouton
        startGameButton.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == startGameButton) {
                Intent intent = new Intent(DepartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}