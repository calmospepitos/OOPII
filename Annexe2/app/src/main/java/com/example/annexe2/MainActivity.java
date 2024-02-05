package com.example.annexe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Ecouteur ec;
    Button boutonValider;
    Button boutonEnvoyer;
    EditText champNomCompte;
    EditText champCourrielDestinataire;
    EditText champMontant;
    TextView champSolde;
    Vector<String> choix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les variables
        boutonValider = findViewById(R.id.valider);
        boutonEnvoyer = findViewById(R.id.envoyer);
        champNomCompte = findViewById(R.id.nomCompte);
        champCourrielDestinataire = findViewById(R.id.courrielDestinataire);
        champMontant = findViewById(R.id.montant);
        champSolde = findViewById(R.id.solde);

        choix = new Vector();
        choix.add("CHEQUE");
        choix.add("EPARGNE");
        choix.add("EPARGNEPLUS");

        // 1ère étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2ème étape: Inscrire la source à l'écouteur
        boutonValider.setOnClickListener(ec);
        boutonEnvoyer.setOnClickListener(ec);
    }

    // 3ème étape: Coder la classe interne
    private class Ecouteur implements View.OnClickListener {
        double soldeCompte = 500.00;

        @Override
        public void onClick(View source) {
            if (source == boutonValider) {
                String nom = champNomCompte.getText().toString();
                nom = nom.trim().toUpperCase();

                if (choix.contains(nom)) {
                    champSolde.setText(String.valueOf(soldeCompte));
                }
                else {
                    champSolde.setText("Pas un bon nom de compte.");
                    champNomCompte.setText("");
                }
            }
            else if (source == boutonEnvoyer) {
                String courrielDestinataire = champCourrielDestinataire.getText().toString();
                String montant = champMontant.getText().toString();
                int value = Integer.parseInt(montant);

                if (courrielDestinataire.isBlank()) {
                    champCourrielDestinataire.setText("Indiquez un destinataire");
                }
                else if (value > soldeCompte) {
                    champMontant.setText("Fonds insuffisants");
                }
                else {
                    soldeCompte -= value;
                    champSolde.setText(String.valueOf(soldeCompte));
                    champMontant.setText("");
                }
            }
        }
    }

}