package com.example.tp1;

import com.example.tp1.produits.*;

import java.util.Hashtable;

public class ListeProduits {

    private Hashtable<String, Produit> liste;

    public ListeProduits() {
        liste = new Hashtable<>();
        liste.put("Café filtre Petit", new CafeFiltre());
        liste.put("Café filtre Moyen", new CafeFiltre());
        liste.put("Café filtre Grand", new CafeFiltre());
        liste.put("Americano Petit", new Americano());
        liste.put("Americano Moyen", new Americano());
        liste.put("Americano Grand", new Americano());
        liste.put("Café glacé Petit", new CafeGlace());
        liste.put("Café glacé Moyen", new CafeGlace());
        liste.put("Café glacé Grand", new CafeGlace());
        liste.put("Latté Petit", new Latte());
        liste.put("Latté Moyen", new Latte());
        liste.put("Latté Grand", new Latte());
    }

    public Produit recupererProduit(String cle) {
        return liste.get(cle);
    }
}