package com.example.annexe8;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.text.DecimalFormat;

public class PlacementActivity extends AppCompatActivity {

    EditText champMontant;
    NumberPicker numberPicker;
    TextView labelReponse;
    Button bouton;
    Ecouteur ec;
    DecimalFormat d = new DecimalFormat("0.00$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        // Initialisation des composants
        champMontant =  findViewById(R.id.champMontant);
        numberPicker = findViewById(R.id.numberPicker);
        labelReponse =  findViewById(R.id.labelReponse);
        bouton = findViewById(R.id.bouton);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);

        NumberPicker.Formatter formatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                int temp = value * 12;
                return "" + temp;
            }
        };

        numberPicker.setFormatter(formatter);

        // 1e étape: Créer un écouteur
        ec = new Ecouteur();
        // 2e étape: Associer l'écouteur au bouton
        bouton.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 1. Récupérer le montant et le nombre de mois
            String montant = champMontant.getText().toString();
            int nbMois = numberPicker.getValue() * 12;
            // 2. Valider les données
            if (montant.isEmpty()) {
                creerAlertDialog("Le montant est obligatoire");
                return;
            }
            double montantDouble = Double.parseDouble(montant);
            if (montantDouble <= 0) {
                creerAlertDialog("Le montant doit être supérieur à 0");
                return;
            }

            // try-catching the NumberFormatException
            try {
                // 3. Calculer le montant final
                Placement placement = new Placement(montantDouble, nbMois);
                double montantFinal = placement.calculerMontantFinal();
                // 4. Afficher le montant final
                labelReponse.setText("Montant final : " + d.format(montantFinal));
            }
            catch (RuntimeException nfe){
                champMontant.setText("");
                creerAlertDialog("Le montant doit être un nombre" + champMontant.getText().toString());
                champMontant.setHint("Ex: 1000 ou 1000.00");
                champMontant.requestFocus();
            }
        }
    }

    //pour créer une boite de dialogue simple
    public void creerAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlacementActivity.this);

        //on peut faire ca !!
        builder.setMessage(message).setTitle("Erreur");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}








