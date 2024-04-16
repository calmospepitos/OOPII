package ex.appex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ItemMinecraft {
    // Attributs
    private String nom;
    private int couleur;
    private int largeurTrait;

    // Constructeur
    public ItemMinecraft(String nom, int couleur, int largeurTrait) {
        this.nom = nom;
        this.couleur = couleur;
        this.largeurTrait = largeurTrait;
    }

    // Méthodes
    public void dessiner(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(couleur);
        paint.setStrokeWidth(largeurTrait);

        // Trouver le milieu de la surface de dessin
        int middleX = canvas.getWidth() / 2;
        int middleY = canvas.getHeight() / 2;

        // Trouver les points de départ et de fin pour dessiner une ligne verticale
        int startY = middleY - 200;
        int endY = middleY + 200;

        if (nom.equals("Bambou")) {
            canvas.drawLine(middleX, startY, middleX, endY, paint);
        }
        else if (nom.equals("Cloture")) {
            canvas.drawLine(middleX - 150, startY, middleX - 150, endY, paint);
            canvas.drawLine(middleX + 150, startY, middleX + 150, endY, paint);
            canvas.drawLine(middleX - 225, startY + 75, middleX + 225, startY + 75, paint);
        }
        else if (nom.equals("Echelle")) {
            canvas.drawLine(middleX - 150, startY, middleX - 150, endY, paint);
            canvas.drawLine(middleX + 150, startY, middleX + 150, endY, paint);
            canvas.drawLine(middleX - 150, startY + 50, middleX + 150, startY + 50, paint);
            canvas.drawLine(middleX - 150, startY + 100, middleX + 150, startY + 100, paint);
            canvas.drawLine(middleX - 150, startY + 150, middleX + 150, startY + 150, paint);
            canvas.drawLine(middleX - 150, startY + 200, middleX + 150, startY + 200, paint);
            canvas.drawLine(middleX - 150, startY + 250, middleX + 150, startY + 250, paint);
            canvas.drawLine(middleX - 150, startY + 300, middleX + 150, startY + 300, paint);
            canvas.drawLine(middleX - 150, startY + 350, middleX + 150, startY + 350, paint);
        }
    }
}
