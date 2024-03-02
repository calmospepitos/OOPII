package com.example.tp1.produits;

// Sous-classe Latte
public class Latte extends Produit {

    public Latte() {
        super("Latté", 4.00, 125);
    }

    @Override
    public double getPrix(String taille) {
        switch (taille) {
            case "Petit":
                return prixPetit;
            case "Moyen":
                return prixPetit * (5.0 / 3.0); // 5/3 du prix d'un petit café Latte
            case "Grand":
                return prixPetit * 2.5; // 2.5 fois le prix d'un petit café Latte
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
                return (int) (caloriesPetit * (5.0 / 3.0)); // 5/3 du nombre de calories d'un petit Latte
            case "Grand":
                return caloriesPetit * 2; // 2 fois le nombre de calories d'un petit Latte
            default:
                return 0; // Return 0 if taille is not recognized
        }
    }
}