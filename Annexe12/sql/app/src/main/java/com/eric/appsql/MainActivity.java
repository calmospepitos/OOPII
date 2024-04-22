package com.eric.appsql;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private TextView question, reponse;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    Vector<String> inventions;
    private Ecouteur ec;
    private GestionBD instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Créer une instance de la classe GestionBD
        instance = GestionBD.getInstance(getApplicationContext());

        // Ouvrir la connexion à la base de données
        instance.ouvrirConnexion();

        // Appeler la méthode retournerInventions de la classe GestionBD
        inventions = instance.retournerInventions();

        for(String invention : inventions) {
            System.out.println(invention);
        }

        // Initialiser les éléments de l'interface graphique
        question = findViewById(R.id.question);
        reponse = findViewById(R.id.reponse);
        listView = findViewById(R.id.listView);

        // 1ere étape: Créer une instance de Ecouteur
        ec = new Ecouteur();

        // Créer un ArrayAdapter pour afficher les inventions dans la ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, inventions);

        // Afficher les inventions dans la ListView
        listView.setOnItemClickListener(ec);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerConnexion();
    }

    private class Ecouteur implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String clickedItem = inventions.get(i);

            if (clickedItem.equals("Essuie-glace")) {
                reponse.setText("Bonne réponse.");
                view.setBackgroundColor(Color.GREEN);
            }
            else {
                reponse.setText("Mauvaise réponse.");
                view.setBackgroundColor(Color.RED);
                listView.getChildAt(2).setBackgroundColor(Color.parseColor("#00FF00"));
            }

            // Désactiver les clics sur les autres éléments de la ListView
            listView.setOnItemClickListener(null);
        }
    }
}