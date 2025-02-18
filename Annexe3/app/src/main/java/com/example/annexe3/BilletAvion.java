package com.example.annexe3;

public class BilletAvion extends Produit{

    public BilletAvion( int qteAchetee ) {
        super("Billet d'avion", qteAchetee, 599.99);
    }

    // getters et setters
    @Override
    public int getQte() {
        return super.getQte();
    }

    @Override
    public double getPrixUnitaire() {
        return super.getPrixUnitaire();
    }
}
