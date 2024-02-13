package com.example.annexe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Eau extends AppCompatActivity {
    // Initialiser les variables
    Ecouteur ec;
    ImageView bouteille, verre, bidon;
    TextView quantiteBu;
    ProgressBar progressBar;
    Toast toast;
    int qteBu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les variables
        bouteille = findViewById(R.id.bouteille);
        verre = findViewById(R.id.verre);
        bidon = findViewById(R.id.bidon);
        quantiteBu = findViewById(R.id.quantiteBu);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setMax(2000);

        // 1e étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2e étape: Inscrire la source à l'écouteur
        bouteille.setOnClickListener(ec);
        verre.setOnClickListener(ec);
        bidon.setOnClickListener(ec);
    }

    // 3e étape: Création de la classe Ecouteur
    private class Ecouteur implements View.OnClickListener {
        public void onClick(View source) {
            if (qteBu >= 2000) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getApplicationContext(), "Vous avez atteint la limite de consommation", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            else {
                if (source == bouteille) {
                    qteBu += 500;
                }
                else if (source == verre) {
                    qteBu += 250;
                }
                else if (source == bidon) {
                    qteBu += 1500;
                }

                quantiteBu.setText(qteBu + " ml");
                progressBar.setProgress(qteBu);
            }
        }
    }
}

