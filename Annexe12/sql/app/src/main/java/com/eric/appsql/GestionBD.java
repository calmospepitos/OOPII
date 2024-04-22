package com.eric.appsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Vector;

public class GestionBD extends SQLiteOpenHelper {

    //instance unique de la classe Singleton GestionBD
    private static GestionBD instance;
    private SQLiteDatabase database;

    // méthode de base pour un Singleton
    public static GestionBD getInstance(Context contexte) {
        if (instance == null)
            instance = new GestionBD(contexte);
        return instance;
    }

    private GestionBD(Context context) {
        super(context, "db", null, 1);
    }

    // Appeler seulement une fois lorsqu'on installe l'application
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la table inventeur
        db.execSQL("CREATE TABLE inventeur (_id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, origine TEXT, invention TEXT, annee INTEGER)");
        // Ajouter 5 inventeurs
        ajouterInventeur(new Inventeur("Laszlo Biro", "Hongrie", "Stylo à bille", 1938), db);
        ajouterInventeur(new Inventeur("Benjamin Franklin", "Etats-Unis", "Paratonnerre", 1752), db);
        ajouterInventeur(new Inventeur("Mary Anderson", "Etats-Unis", "Essuie-glace", 1903), db);
        ajouterInventeur(new Inventeur("Grace Hopper", "Etats-Unis", "Compilateur", 1952), db);
        ajouterInventeur(new Inventeur("Benoit Rouquayrot", "France", "Scaphandre", 1864), db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer la table inventeur
        db.execSQL("DROP TABLE IF EXISTS inventeur");
        // Recréer la table inventeur
        onCreate(db);
    }

    public void ajouterInventeur(Inventeur inventeur, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        // Mettre les valeurs dans l'objet ContentValues
        values.put("nom", inventeur.getNom());
        values.put("origine", inventeur.getOrigine());
        values.put("invention", inventeur.getInvention());
        values.put("annee", inventeur.getAnnee());
        // Insérer les valeurs dans la table inventeur
        db.insert("inventeur", null, values);
    }

    public Vector<String> retournerInventions() {
        Vector<String> inventions = new Vector<String>();

        Cursor requete = database.rawQuery("SELECT invention FROM inventeur", null);

        requete.moveToFirst();

        while (!requete.isAfterLast()) {
            inventions.add(requete.getString(0));
            requete.moveToNext();
        }

        requete.close();
        return inventions;
    }

    public boolean aBonneReponse(String nom, String invention) {
        String[] tab = {nom, invention};
        Cursor c = database.rawQuery("SELECT nom FROM inventeur WHERE nom = ? AND invention = ?", tab);
        // ? signifie valeur en parametre commencant par le premier, donc premier ? = nom et deuxieme ? = invention
        return c.moveToFirst();
    }

    public void ouvrirConnexion() {
        database = this.getWritableDatabase();
    }

    public void fermerConnexion() {
        database.close();
    }
}
