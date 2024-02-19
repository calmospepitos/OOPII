package com.example.annexe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Voyage extends AppCompatActivity {
    // Attributs
    Ecouteur ec;
    ImageView avion, hotel;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage);

        // Initialiser les variables
        avion = findViewById(R.id.avion);
        hotel = findViewById(R.id.hotel);

        Commande commande = new Commande();

        // 1e étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2e étape: Inscrire la source à l'écouteur
        avion.setOnClickListener(ec);
        hotel.setOnClickListener(ec);

        // 3e étape: Création de la classe Ecouteur
    }

    private class Ecouteur implements View.OnClickListener {
        public void onClick(View source) {

        }
    }
}