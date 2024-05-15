package com.example.examenfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SingletonDatabase extends SQLiteOpenHelper {
    public SQLiteDatabase database;
    public SingletonDatabase(Context context) { super(context, "app", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL("create table drapeau ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nomPays TEXT, couleurGauche TEXT, couleurCentre TEXT, couleurDroite TEXT);");
       insererDrapeau(sqLiteDatabase, new Drapeau("France", "bleu", "blanc", "rouge"));
       insererDrapeau(sqLiteDatabase, new Drapeau("Pérou", "rouge", "blanc", "rouge"));
       insererDrapeau(sqLiteDatabase, new Drapeau("Roumanie", "bleu", "jaune", "rouge"));
       insererDrapeau(sqLiteDatabase, new Drapeau("Belgique", "noir", "jaune", "rouge"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table drapeau");
        onCreate(sqLiteDatabase);
    }

    public void ouvrirConnexion() {
        this.database = getWritableDatabase();
    }

    public void fermerConnexion() {
        database.close();
    }

    public void insererDrapeau (SQLiteDatabase sqLiteDatabase, Drapeau d )
    {
        ContentValues cv = new ContentValues();
        cv.put("nomPays", d.getNomPays());
        cv.put("couleurGauche", d.getCouleurGauche());
        cv.put("couleurCentre", d.getCouleurCentre());
        cv.put("couleurDroite", d.getCouleurDroite());
        sqLiteDatabase.insert("drapeau", null, cv );
    }

    public String getPaysAleatoire() {
        Cursor cursor = database.rawQuery("SELECT nomPays FROM drapeau ORDER BY RANDOM() LIMIT 1", null);
        if (cursor != null && cursor.moveToFirst()) {
            String pays = cursor.getString(0);
            cursor.close();
            return pays;
        }
        return null;
    }

    public boolean verifierCouleurPays(String nomPays, String couleurGauche, String couleurCentre, String couleurDroite) {
        String[] arguments = {nomPays, couleurGauche, couleurCentre, couleurDroite};
        Cursor cursor = database.rawQuery("SELECT * FROM drapeau WHERE nomPays = ? AND couleurGauche = ? AND couleurCentre = ? AND couleurDroite = ?", arguments);
        boolean match = cursor.getCount() > 0;
        cursor.close();
        return match;
    }
}
