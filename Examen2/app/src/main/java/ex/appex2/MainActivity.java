package ex.appex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Attributs
    private Ecouteur ec;
    private Surface surf;
    private ConstraintLayout conteneur;
    private TextView texteCouleur, texteLargeur;
    private Button redButton, greenButton, appercuButton;
    private Spinner spinner;
    private SeekBar seekBar;
    private ArrayList<String> items;
    private ItemMinecraft item;
    private int couleurSelectionnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des attributs
        texteCouleur = findViewById(R.id.texteCouleur);
        texteLargeur = findViewById(R.id.texteLargeur);
        redButton = findViewById(R.id.boutonRouge);
        greenButton = findViewById(R.id.boutonVert);
        appercuButton = findViewById(R.id.boutonApercu);
        spinner = findViewById(R.id.spinner);
        seekBar = findViewById(R.id.seekBar);
        conteneur = findViewById(R.id.conteneur);
        items = new ArrayList<>();

        // Ajout des items dans le ArrayList
        items.add("Bambou");
        items.add("Cloture");
        items.add("Echelle");

        // Initialisation de l'écouteur
        ec = new Ecouteur();

        // Initialisation du spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initialisation de la surface
        surf = new Surface(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        conteneur.addView(surf);

        // Ajout des écouteurs
        redButton.setOnClickListener(ec);
        greenButton.setOnClickListener(ec);
        appercuButton.setOnClickListener(ec);
        seekBar.setOnSeekBarChangeListener(ec);
    }

    public class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        @Override
        public void onClick(View v) {
            if (v == redButton) {
                texteCouleur.setText("Couleur de l'item : Rouge");
                couleurSelectionnee = Color.parseColor("#E91E63");
            }
            else if (v == greenButton) {
                texteCouleur.setText("Couleur de l'item : Vert");
                couleurSelectionnee = Color.parseColor("#4CAF50");
            }
            else if( v == appercuButton) {
                String selectedItem = spinner.getSelectedItem().toString();
                if (couleurSelectionnee == Color.parseColor("#E91E63") && selectedItem.equals("Bambou")) {
                    Toast.makeText(MainActivity.this, "Un bambou ne peut pas être rouge.", Toast.LENGTH_SHORT).show();
                } else {
                    item = new ItemMinecraft(selectedItem, couleurSelectionnee, seekBar.getProgress());
                    surf.invalidate();
                }
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            texteLargeur.setText("Largeur : " + seekBar.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public class Surface extends View {
        // Constructeur
        public Surface(Context context) { super(context); }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (item != null) {
                item.dessiner(canvas);
            }
        }
    }
}