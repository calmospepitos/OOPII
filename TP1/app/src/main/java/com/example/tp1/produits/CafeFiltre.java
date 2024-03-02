package com.example.tp1.produits;

// Sous-classe CaféFiltre
public class CafeFiltre extends Produit {

    public CafeFiltre() {
        super("Café filtre", 1.80, 5);
    }

    @Override
    public double getPrix(String taille) {
        switch (taille) {
            case "Petit":
                return prixPetit;
            case "Moyen":
                return prixPetit * (5.0 / 3.0); // 5/3 du prix d'un petit café filtre
            case "Grand":
                return prixPetit * 2.2; // 2.2 fois le prix d'un petit café filtre
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
                return (int) Math.round(caloriesPetit * (5.0 / 3.0)); // 5/3 du nombre de calories d'un petit café filtre
            case "Grand":
                return caloriesPetit * 2; // 2 fois le nombre de calories d'un petit café filtre
            default:
                return 0; // Return 0 if taille is not recognized
        }
    }
}
