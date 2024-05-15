package com.example.examenfinal;

public class Drapeau {
    // Attributs
    private String nomPays;
    private String couleurGauche;
    private String couleurCentre;
    private String couleurDroite;

    // Constructeur
    public Drapeau(String nomPays, String couleurGauche, String couleurCentre, String couleurDroite) {
        this.nomPays = nomPays;
        this.couleurGauche = couleurGauche;
        this.couleurCentre = couleurCentre;
        this.couleurDroite = couleurDroite;
    }

    // Getters
    public String getNomPays() {
        return this.nomPays;
    }
    public String getCouleurGauche() {
        return this.couleurGauche;
    }

    public String getCouleurCentre() {
        return this.couleurCentre;
    }

    public String getCouleurDroite() { return this.couleurDroite;}
}
