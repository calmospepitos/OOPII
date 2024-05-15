package com.example.tpfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SingletonDatabase extends SQLiteOpenHelper {
    // Attributs
    public SQLiteDatabase database;

    // Constructeur
    public SingletonDatabase(Context context) {
        super(context, "app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Scores (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "score INTEGER, " + "date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Méthodes
    public void insererScore(int score) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("score", score);
        contentValues.put("date", new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date()));
        database.insert("Scores", null, contentValues);
    }

    public int getMeilleurScore() {
        Cursor cursor = database.rawQuery("SELECT score FROM Scores ORDER BY score DESC LIMIT 1", null);
        int score = 0;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("score");
            // Si la colonne existe
            if (columnIndex != -1) {
                score = cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return score;
    }

    public List<String> getTopScores() {
        List<String> scores = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT score, date FROM Scores ORDER BY score DESC LIMIT 3", null);
        while (cursor.moveToNext()) {
            int scoreIndex = cursor.getColumnIndex("score");
            int dateIndex = cursor.getColumnIndex("date");
            if (scoreIndex != -1 && dateIndex != -1) {
                int score = cursor.getInt(scoreIndex);
                String date = cursor.getString(dateIndex);
                scores.add(score + " (" + date + ")");
            }
        }
        cursor.close();
        return scores;
    }

    // Méthodes de connexion
    public void ouvrirConnexion() {
        this.database = getWritableDatabase();
    }

    public void fermerConnexion() { database.close(); }
}
