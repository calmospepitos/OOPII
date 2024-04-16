package com.eric.appsql;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Créer une instance de la classe GestionBD
        GestionBD instance = GestionBD.getInstance(getApplicationContext());

        // Ouvrir la connexion à la base de données
        instance.ouvrirConnexion();

        // Appeler la méthode retournerInventions de la classe GestionBD
        Vector<String> inventions = instance.retournerInventions();

        for(String invention : inventions) {
            System.out.println(invention);
        }
    }
}