package com.example.annexe2;

import androidx.appcompat.app.AlertDialog;
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
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Ecouteur ec;
    DecimalFormat df;
    Spinner spinnerCompte;
    Button boutonEnvoyer;
    EditText champCourrielDestinataire;
    EditText champMontant;
    TextView champSolde;
    Hashtable<String, Compte> listeComptes;
    Vector<String> choix;
    Compte c;
    AlertDialog.Builder boiteAlerte;

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
        boiteAlerte = new AlertDialog.Builder(this);

        df = new DecimalFormat("0.00$");
        listeComptes = new Hashtable<>();

        // Création des comptes
        listeComptes.put("Chèque", new Compte("Chèque", 1400.00));
        listeComptes.put("Épargne", new Compte("Épargne", 100.00));
        listeComptes.put("Épargne Plus", new Compte("Épargne Plus", 500.00));
        Set<String> enCles = listeComptes.keySet();

        choix = new Vector<>(enCles);

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
        double soldeCompte;

        @Override
        public void onClick(View source) {
            if (source == boutonEnvoyer) {
                String courrielDestinataire = champCourrielDestinataire.getText().toString();
                String montant = champMontant.getText().toString();

                int value = Integer.parseInt(montant);

                if (courrielDestinataire.isBlank()) {
                    boiteAlerte.setMessage("Courriel manquant").setTitle("Erreur");
                    AlertDialog dialog = boiteAlerte.create();
                    dialog.show();
                }
                else {
                    if (c.transfert(value)) {
                        boiteAlerte.setMessage("Fonds insuffisants").setTitle("Erreur");
                        AlertDialog dialog = boiteAlerte.create();
                        dialog.show();
                        champMontant.setText("");
                    }
                    else {
                        c.transfert(value);
                        champSolde.setText(df.format(c.getSoldeCompte()));
                    }
                }
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String compte = choix.get(position);
            c = listeComptes.get(compte);
            soldeCompte = c.getSoldeCompte();
            champSolde.setText(df.format(soldeCompte));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}