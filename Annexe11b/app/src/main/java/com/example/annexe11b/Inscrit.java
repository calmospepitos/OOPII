package com.example.annexe11b;

import java.util.Hashtable;
import bla.HashtableAssociation;

public class Inscrit {
    private String nom;
    private String prenom;
    private String adresse;
    private String capitale;
    private String etat;
    private String codeZip;

    public Inscrit(String nom, String prenom, String adresse, String capitale, String etat, String codeZip) throws AdresseException{
        // Vérifier si la capitale fait partie de l'état à l'aide d'une Hashtable secrète ( classe HashtableAssociation )
        HashtableAssociation ht = new HashtableAssociation();

        if(!etat.equals(ht.get(capitale))) { // si la capitale n'est pas dans l'état
            throw new AdresseException(capitale, etat);
        }

        // Mettre les valeurs dans les attributs
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.capitale = capitale;
        this.etat = etat;
        this.codeZip = codeZip;












    }
}
