package com.example.annexe3;

import java.util.Vector;

public class Commande {

    private Vector<Produit> listeCommande;

    public Commande ( )
    {
        listeCommande = new Vector();
    }

    public void ajouterProduit ( Produit p )
    {
        listeCommande.add(p);
    }

    public double total () {
	    double total = 0;

        for (int i = 0; i < listeCommande.size(); i++) {
            total += listeCommande.get(i).getPrixUnitaire() * listeCommande.get(i).getQte();
        }

	    return total;
    }

    public double taxes() {
        double taxes = 0;

        taxes = total() * 0.14975;

        return taxes;
    }

    public double grandTotal() {
	    double grTotal = 0;

        grTotal = total() + taxes();

	    return grTotal;
    }
}
