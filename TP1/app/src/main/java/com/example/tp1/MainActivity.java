package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tp1.produits.Produit;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Ecouteur ec;
    DecimalFormat df;
    ImageView filtreIcon, americanoIcon, glaceIcon, latteIcon;
    ChipGroup chipGroup;
    Chip petitChip, moyenChip, grandChip;
    Button ajouterButton, commanderButton, effacerButton;
    TextView total, info;
    Commande commande;
    ListeProduits listeProduits;
    String selectedProduct;
    String selectedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise les éléments de l'interface
        filtreIcon = findViewById(R.id.filtreIcon);
        americanoIcon = findViewById(R.id.americanoIcon);
        glaceIcon = findViewById(R.id.glaceIcon);
        latteIcon = findViewById(R.id.latteIcon);
        ajouterButton = findViewById(R.id.ajouterButton);
        commanderButton = findViewById(R.id.commanderButton);
        effacerButton = findViewById(R.id.effacerButton);
        total = findViewById(R.id.total);
        info = findViewById(R.id.info);
        chipGroup = findViewById(R.id.chipGroup);
        petitChip = findViewById(R.id.petitChip);
        moyenChip = findViewById(R.id.moyenChip);
        grandChip = findViewById(R.id.grandChip);

        // 1ere étape: Créer une instance de Commande et ListeProduits
        commande = new Commande();
        listeProduits = new ListeProduits();
        df = new DecimalFormat("0.00");

        // 2e étape: Créer un écouteur pour les éléments de l'interface
        ec = new Ecouteur();
        filtreIcon.setOnClickListener(ec);
        americanoIcon.setOnClickListener(ec);
        glaceIcon.setOnClickListener(ec);
        latteIcon.setOnClickListener(ec);
        petitChip.setOnClickListener(ec);
        moyenChip.setOnClickListener(ec);
        grandChip.setOnClickListener(ec);
        ajouterButton.setOnClickListener(ec);
        commanderButton.setOnClickListener(ec);
        effacerButton.setOnClickListener(ec);
    }

    // 3e étape: Créer une classe Ecouteur pour gérer les événements
    private class Ecouteur implements View.OnClickListener {
        boolean imageSelected = false;
        boolean chipSelected = false;
        public void onClick(View source) {
            if (imageSelected && chipSelected) {
                String infoText = selectedProduct + " " + selectedProduct.get + " " + selectedSize;
                info.setText(infoText);
            }

            // Si clique sur une image
            if (source == filtreIcon || source == americanoIcon || source == glaceIcon || source == latteIcon) {
                if (source == filtreIcon) {
                    selectedProduct = "Café filtre";
                }
                else if (source == americanoIcon) {
                    selectedProduct = "Americano";
                }
                else if (source == glaceIcon) {
                    selectedProduct = "Café glacé";
                }
                else if (source == latteIcon) {
                    selectedProduct = "Latté";
                }
            }   // Si clique sur un chip
            else if (source == petitChip || source == moyenChip || source == grandChip) {
                if (source == petitChip) {
                    selectedSize = "Petit";
                }
                else if (source == moyenChip) {
                    selectedSize = "Moyen";
                }
                else if (source == grandChip) {
                    selectedSize = "Grand";
                }
                ajouterButton.setEnabled(true); // Enable le bouton "Ajouter"
            } // Si clique sur le bouton ajouter
            else if (source == ajouterButton) {
                Produit produit = listeProduits.recupererProduit(selectedProduct + " " + selectedSize);
                commande.ajouterBoisson(produit, selectedSize);
                updateTotal();
            } // Si clique sur le bouton commander
            else if (source == commanderButton) {
                // Handle the "Commander" button click
                // Add your logic here to handle the order submission
            } // Si clique sur le bouton effacer
            else if (source == effacerButton) {
                commande.resetCommande();
                updateTotal();
                ajouterButton.setEnabled(false); // Disable le bouton "Ajouter"
            }
        }
    }

    // Méthodes
    private void updateTotal() {
        // Update le total des achats incluant les taxes
        double totalAmount = commande.getTotal();
        double totalWithTaxes = calculateTotalWithTaxes(totalAmount);
        total.setText("Total: " + df.format(totalWithTaxes) + "$");
    }

    private double calculateTotalWithTaxes(double totalAmount) {
        // Cacule les taxes du total des achats
        return totalAmount * 1.14975;
    }
}