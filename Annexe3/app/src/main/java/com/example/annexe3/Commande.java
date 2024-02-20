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

        // compléter : montant total de la commande
        for (int i = 0; i < listeCommande.size(); i++) {
            total += listeCommande.get(i).getPrixUnitaire() * listeCommande.get(i).getQte();
        }

	    return total;
    }

    public double taxes() {
        double taxes = 0;
  
	    // compléter : montant des taxes sur le total de la commande

        // tps sur le montant avant taxes ( 5% )
        
        // tvq sur le montant avant taxes ( 9.975% )
        
        // taxes total = tps + tvq

        return taxes;
    }

    public double grandTotal() {
	    double grTotal = 0;
	
	    // compléter

	    return grTotal;
    }
}
