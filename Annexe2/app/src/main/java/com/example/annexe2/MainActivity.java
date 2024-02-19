package com.example.annexe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Ecouteur ec;
    DecimalFormat df;
    Spinner spinnerCompte;
    Button boutonEnvoyer;
    EditText champCourrielDestinataire;
    EditText champMontant;
    TextView champSolde;
    Vector<String> choix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser les variables
        boutonEnvoyer = findViewById(R.id.envoyer);
        spinnerCompte = findViewById(R.id.nomCompte);
        champCourrielDestinataire = findViewById(R.id.courrielDestinataire);
        champMontant = findViewById(R.id.montant);
        champSolde = findViewById(R.id.solde);

        df = new DecimalFormat("0.00$");

        choix = new Vector();
        choix.add("CHEQUE");
        choix.add("EPARGNE");
        choix.add("EPARGNEPLUS");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choix);
        spinnerCompte.setAdapter(adapter);

        // 1ère étape: Création de l'écouteur
        ec = new Ecouteur();

        // 2ème étape: Inscrire la source à l'écouteur
        boutonEnvoyer.setOnClickListener(ec);
        spinnerCompte.setOnItemSelectedListener(ec);
    }

    // 3ème étape: Coder la classe interne
    private class Ecouteur implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        double soldeCompte = 500.00;

        @Override
        public void onClick(View source) {
            if (source == boutonEnvoyer) {
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
                    champSolde.setText(df.format(soldeCompte));
                    champMontant.setText("");
                }
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String compte = choix.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}