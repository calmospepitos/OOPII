package com.example.tp1.produits;

// Sous-classe Americano
public class Americano extends Produit {

    public Americano() {
        super("Americano", 2.40, 9);
    }

    @Override
    public double getPrix(String taille) {
        switch (taille) {
            case "Petit":
                return prixPetit;
            case "Moyen":
                return prixPetit * (5.0 / 3.0); // 5/3 du prix d'un petit americano
            case "Grand":
                return prixPetit * 2.2; // 2.2 fois le prix d'un petit americano
            default:
                return 0.0; // Return 0 if taille is not recognized
        }
    }

    @Override
    public int getCalories(String taille) {
        switch (taille) {
            case "Petit":
                return caloriesPetit;
            case "Moyen":
                return caloriesPetit + 2; // 2 calories de plus qu'un petit americano
            case "Grand":
                return caloriesPetit * 2; // 2 fois le nombre de calories d'un petit americano
            default:
                return 0; // Return 0 if taille is not recognized
        }
    }

    // On peut ajouter des méthodes spécifiques à Americano si nécessaire
}