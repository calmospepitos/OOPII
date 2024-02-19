package com.example.annexe2;

public class Compte {
    // Attributs
    private String nomCompte;
    private double soldeCompte;

    // Constructeur
    public Compte(String nomCompte, double soldeCompte) {
        this.nomCompte = nomCompte;
        this.soldeCompte = soldeCompte;
    }

    // Getters et Setters
    public String getNomCompte() {
        return nomCompte;
    }
    public double getSoldeCompte() {
        return soldeCompte;
    }
    public void setSoldeCompte(double soldeCompte) {
        this.soldeCompte = soldeCompte;
    }
}
