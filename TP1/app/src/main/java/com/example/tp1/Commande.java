package com.example.tp1;

import com.example.tp1.produits.Produit;
import java.util.Vector;

public class Commande {
    private Vector<Produit> boissons;
    private double total;

    public Commande() {
        boissons = new Vector<>();
        total = 0.0;
    }

    public void ajouterBoisson(Produit boisson, String taille) {
        boissons.add(boisson);
        total += boisson.getPrix(taille);
    }

    // Méthodes d'accès au total de la commande
    public double getTotal() {
        return total;
    }

    // Méthode d'accès au vecteur de boissons
    public Vector<Produit> getBoissons() {
        return boissons;
    }

    public void resetCommande() {
        boissons.clear();
        total = 0.0;
    }
}
