package com.example.annexe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Initialiser les variables
    Ecouteur ec;
    ImageView bouteille, verre, bidon;
    TextView quantiteBu;
    ProgressBar progressBar;
    int qteBu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les variables
        bouteille = findViewById(R.id.bouteille);
        verre = findViewById(R.id.verre);
        bidon = findViewById(R.id.bidon);

        // 1e étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2e étape: Inscrire la source à l'écouteur
        bouteille.setOnClickListener(ec);
        verre.setOnClickListener(ec);
        bidon.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {
        public void onClick(View source) {
            if (source == bouteille) {
                qteBu += 330;
            } else if (source == verre) {
                qteBu += 150;
            } else if (source == bidon) {
                qteBu += 1500;
            }
            quantiteBu.setText(qteBu + " ml");
            progressBar.setProgress(qteBu);
        }
    }
}

