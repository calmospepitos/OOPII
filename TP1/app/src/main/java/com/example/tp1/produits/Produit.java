package com.example.tp1.produits;

// Classe Produit (superclasse)
public abstract class Produit {
    protected String nom;
    protected double prixPetit;
    protected int caloriesPetit;

    public Produit(String nom, double prixPetit, int caloriesPetit) {
        this.nom = nom;
        this.prixPetit = prixPetit;
        this.caloriesPetit = caloriesPetit;
    }

    public abstract double getPrix(String taille);
    public abstract int getCalories(String taille);
}