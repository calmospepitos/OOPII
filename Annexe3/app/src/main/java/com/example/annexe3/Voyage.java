package com.example.annexe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Voyage extends AppCompatActivity {
    // Attributs
    Ecouteur ec;
    ImageView avion, hotel;
    Commande commande;
    TextView total, qteAvion, qteHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage);

        // Initialiser les variables
        avion = findViewById(R.id.avion);
        hotel = findViewById(R.id.hotel);
        qteAvion = findViewById(R.id.nbAvion);
        qteHotel = findViewById(R.id.nbHotel);

        commande = new Commande();

        // 1e étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2e étape: Inscrire la source à l'écouteur
        avion.setOnClickListener(ec);
        hotel.setOnClickListener(ec);
    }

    // 3e étape: Création de la classe Ecouteur
    private class Ecouteur implements View.OnClickListener {
        int qteA = 0, qteH = 0;

        public void onClick(View source) {
            if (source == avion) {
                commande.ajouterProduit(new BilletAvion(1));
                qteA++;
                qteAvion.setText(Integer.toString(qteA));
            }
            else if (source == hotel) {
                commande.ajouterProduit(new HebergementHotel(1));
                qteH++;
                qteHotel.setText(Integer.toString(qteH));
            }
        }
    }

}