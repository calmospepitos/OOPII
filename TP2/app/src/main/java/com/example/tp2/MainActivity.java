package com.example.tp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Ecouteur ec;
    Surface surf;
    DrawingView drawingView;
    Button blackButton, brownButton, yellowButton, orangeButton, redButton, blueButton;
    ImageView crayon, trait, efface, cercle, triangle, rectangle, fill, pipette, palette, undo, redo, enregistrer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise les éléments de l'interface
        blackButton = findViewById(R.id.blackButton);
        brownButton = findViewById(R.id.brownButton);
        yellowButton = findViewById(R.id.yellowButton);
        orangeButton = findViewById(R.id.orangeButton);
        redButton = findViewById(R.id.redButton);
        blueButton = findViewById(R.id.blueButton);
        crayon = findViewById(R.id.crayon);
        trait = findViewById(R.id.trait);
        efface = findViewById(R.id.efface);
        cercle = findViewById(R.id.cercle);
        triangle = findViewById(R.id.triangle);
        rectangle = findViewById(R.id.rectangle);
        fill = findViewById(R.id.fill);
        pipette = findViewById(R.id.pipette);
        palette = findViewById(R.id.palette);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        enregistrer = findViewById(R.id.enregistrer);

        // 1ere étape: Créer une instance de Ecouteur
        ec = new Ecouteur();

        // 2e étape: Ajouter l'écouteur à tous les boutons
        blackButton.setOnClickListener(ec);
        brownButton.setOnClickListener(ec);
        yellowButton.setOnClickListener(ec);
        orangeButton.setOnClickListener(ec);
        redButton.setOnClickListener(ec);
        blueButton.setOnClickListener(ec);
        crayon.setOnClickListener(ec);
        trait.setOnClickListener(ec);
        efface.setOnClickListener(ec);
        cercle.setOnClickListener(ec);
        triangle.setOnClickListener(ec);
        rectangle.setOnClickListener(ec);
        fill.setOnClickListener(ec);
        pipette.setOnClickListener(ec);
        palette.setOnClickListener(ec);
        undo.setOnClickListener(ec);
        redo.setOnClickListener(ec);
        enregistrer.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View source) {
            if (source instanceof Button) {
                switch(source.getId()) {
                    case R.id.blackButton:
                        drawingView.setColor(Color.BLACK);
                        break;
                    case R.id.brownButton:
                        drawingView.setColor(Color.rgb(139, 69, 19));
                        break;
                    case R.id.yellowButton:
                        drawingView.setColor(Color.YELLOW);
                        break;
                    case R.id.orangeButton:
                        drawingView.setColor(Color.rgb(255, 165, 0));
                        break;
                    case R.id.redButton:
                        drawingView.setColor(Color.RED);
                        break;
                    case R.id.blueButton:
                        drawingView.setColor(Color.BLUE);
                        break;
                }
            }
            else if (source == crayon) {

            }
            else if (source == trait) {

            }
            else if (source == efface) {

            }
            else if (source == cercle) {

            }
            else if (source == triangle) {

            }
            else if (source == rectangle) {

            }
            else if (source == fill) {

            }
            else if (source == pipette) {

            }
            else if (source == palette) {

            }
            else if (source == undo) {

            }
            else if (source == redo) {

            }
            else if (source == enregistrer) {

            }
        }
    }

    public class Surface extends View {
        public Surface(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }
    }

}
