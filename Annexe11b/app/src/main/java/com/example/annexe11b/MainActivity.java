package com.example.annexe11b;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.AlertDialog;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;
import bla.HashtableAssociation;

public class MainActivity extends AppCompatActivity {
    EditText champPrenom, champNom, champAdresse, champZip;
    Spinner spinnerCapitale, spinnerEtat;
    Button bouton;
    Set<String> ensCapitales;
    Collection<String> ensEtats;
    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        champPrenom = findViewById(R.id.champPrenom);
        champNom= findViewById(R.id.champNom);
        champAdresse = findViewById(R.id.champAdresse);
        champZip = findViewById(R.id.champZip);
        spinnerCapitale = findViewById(R.id.spinnerCapitale);
        spinnerEtat = findViewById(R.id.spinnerEtat);
        bouton = findViewById(R.id.boutonInscrire);

        // Instancier l'écouteur
        ec = new Ecouteur();

        // remplir les spinner à l'aide de la Hashtable
        HashtableAssociation ha = new HashtableAssociation();

        ensCapitales = ha.keySet();
        ensEtats = ha.values();

        // Méthode Mathieu Bourassa (merde)
        // for (String capitale : ensCapitales) {
        //   capitales.add(capitale);
        // }

        // transformer les ensembles en vecteurs
        Vector<String> capitales = new Vector<>(ensCapitales);
        Vector<String> etats = new Vector<>(ensEtats);

        // setter les adapteurs
        ArrayAdapter<String> capitaleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, capitales);
        ArrayAdapter<String> etatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, etats);

        spinnerCapitale.setAdapter(capitaleAdapter);
        spinnerEtat.setAdapter(etatAdapter);
    }
    private class Ecouteur implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            try {
                Inscrit inscrit = new Inscrit(champNom.getText().toString(), champPrenom.getText().toString(), champAdresse.getText().toString(), spinnerCapitale.getSelectedItem().toString(), spinnerEtat.getSelectedItem().toString(), champZip.getText().toString());
                builder.setMessage("Inscription réussie").setTitle("Succès");
            }
            catch (AdresseException e) {
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(MainActivity.this);
                boiteAlerte.setTitle("Érreur d'inscription");
                boiteAlerte.setMessage(e.getMessage());
                boiteAlerte.show();
            }

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}