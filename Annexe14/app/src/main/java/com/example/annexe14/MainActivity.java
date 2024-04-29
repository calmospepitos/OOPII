package com.example.annexe14;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private Ecouteur ec;
    private LinearLayout principal;
    private View jeton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des éléments de l'interface
        principal = findViewById(R.id.principal);

        // 1ere étape: Créer une instance de Ecouteur
        ec = new Ecouteur();

        // 2e étape: Ajouter les écouteurs aux colonnes
        for (int i = 0; i < principal.getChildCount(); i++) {
            LinearLayout colonne = (LinearLayout) principal.getChildAt(i);
            colonne.setOnDragListener(ec);
            colonne.getChildAt(0).setOnTouchListener(ec);
        }
    }

    public class Ecouteur implements View.OnTouchListener, View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            // view = les linearLayouts/colonnes
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    // On change la couleur de fond de la colonne pour sélectionné
                    view.setBackground(getDrawable(R.drawable.background_contenant_selectionne));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    // On change la couleur de fond de la colonne pour non sélectionné
                    view.setBackground(getDrawable(R.drawable.background_contenant));
                    break;
                case DragEvent.ACTION_DROP:
                    jeton = (View) dragEvent.getLocalState();
                    LinearLayout parent = (LinearLayout) jeton.getParent();
                    parent.removeView(jeton);
                    LinearLayout contenant = (LinearLayout) view;
                    contenant.addView(jeton);
                    jeton.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // On change la couleur de fond de la colonne pour non sélectionné
                    view.setBackground(getDrawable(R.drawable.background_contenant));
                    break;
            }
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            // view = imageView à bouger
            // C'est l'ombre qu'on va déplacer d'un endroit à un autre
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE); // On cache l'original
            return true;
        }
    }

}