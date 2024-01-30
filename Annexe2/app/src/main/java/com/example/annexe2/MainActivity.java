package com.example.annexe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Ecouteur ec;
    Button boutonValider;
    EditText champNomCompte;
    TextView champSolde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les variables
        boutonValider = findViewById(R.id.valider);
        champNomCompte = findViewById(R.id.nomCompte);
        champSolde = findViewById(R.id.solde);

        // 1ère étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2ème étape: Inscrire la source à l'écouteur
        boutonValider.setOnClickListener(ec);
    }

    // 3ème étape: Coder la classe interne
    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            champSolde.setText("190.00$");
        }
    }

}