package com.example.annexe3;

public class HebergementHotel extends Produit{
    public HebergementHotel( int qteAchetee ) {
        super("1 semaine d'hôtel", qteAchetee, 989.99);
    }

    // getters et setters
    public int getQte() {
        return super.getQte();
    }

    public double getPrixUnitaire() {
        return super.getPrixUnitaire();
    }
}
