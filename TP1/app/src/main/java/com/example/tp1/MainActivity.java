package com.example.tp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LinearLayout thumbnailsLayout;
    Drawable select;
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
        thumbnailsLayout = findViewById(R.id.listeChoixRow);

        // 1ere étape: Créer une instance de Commande et ListeProduits
        commande = new Commande();
        listeProduits = new ListeProduits();
        df = new DecimalFormat("0.00$");

        // Set petitChip comme sélectionné par défaut
        petitChip.setChecked(true);
        selectedSize = "Petit";

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
        public void onClick(View source) {
            // Si clique sur une image
            if (source instanceof ImageView) {
                ImageView imageView = (ImageView) source;
                selectedProduct = imageView.getTag().toString();
                select = imageView.getDrawable();
            }   // Si clique sur un chip
            else if (source instanceof Chip) {
                Chip chip = (Chip) source;
                // Prévenir une déselection d'une taille
                if (chip.getTag().toString() == selectedSize) {
                    chip.setChecked(true);
                }
                else {
                    selectedSize = chip.getText().toString();
                }
            } // Si clique sur le bouton ajouter
            else if (source == ajouterButton) {
                ImageView i = new ImageView(MainActivity.this);
                i.setImageDrawable(select);
                i.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
                thumbnailsLayout.addView(i);

                Produit produit = listeProduits.recupererProduit(selectedProduct + " " + selectedSize);
                commande.ajouterBoisson(produit, selectedSize);
                updateTotal();
            } // Si clique sur le bouton commander
            else if (source == commanderButton) {
                // Handle the "Commander" button click
                double totalAmount = calculateTotalWithTaxes(commande.getTotal());
                String message = "Paiement de " + df.format(totalAmount) + " en cours...";
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Commande envoyée")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();

                commande.resetCommande();
                thumbnailsLayout.removeAllViews();
                updateTotal();
            } // Si clique sur le bouton effacer
            else if (source == effacerButton) {
                commande.resetCommande();
                thumbnailsLayout.removeAllViews();
                updateTotal();
                ajouterButton.setEnabled(false); // Disable le bouton "Ajouter"
            }

            // Si un produit et une taille sélectionnés -> update les infos
            if (selectedProduct != null && selectedSize != null) {
                Produit produit = listeProduits.recupererProduit(selectedProduct + " " + selectedSize);
                if (produit != null) {
                    String priceFormatted = df.format(produit.getPrix(selectedSize));
                    String infoText = selectedProduct + " " + produit.getCalories(selectedSize) + " cal " + priceFormatted;
                    info.setText(infoText);
                }
                ajouterButton.setEnabled(true); // Enable le bouton "Ajouter"
            }
        }
    }

    // Méthodes
    private void updateTotal() {
        // Update le total des achats incluant les taxes
        double totalAmount = commande.getTotal();
        double totalWithTaxes = calculateTotalWithTaxes(totalAmount);
        total.setText("Total: " + df.format(totalWithTaxes));
    }

    private double calculateTotalWithTaxes(double totalAmount) {
        // Cacule les taxes du total des achats
        return totalAmount * 1.14975;
    }
}