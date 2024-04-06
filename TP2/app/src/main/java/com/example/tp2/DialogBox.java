package com.example.tp2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class DialogBox extends Dialog {
    // Attributs
    private SeekBar seekBar;
    private Button okButton;
    private OnThicknessSelectedListener listener;

    // Constructeur
    public DialogBox(@NonNull Context context, OnThicknessSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_box);

        // Initialisation des attributs
        okButton = findViewById(R.id.okButton);
        seekBar = findViewById(R.id.seekBar);

        // Écouteur pour le SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update le textView avec la valeur de l'épaisseur du trait
                TextView textView = findViewById(R.id.textView);
                textView.setText("Largeur du trait : " + progress + "px");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Écouteur pour le bouton OK
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Passer l'épaisseur du trait à l'activité
                listener.onThicknessSelected(seekBar.getProgress());
                // Ferme le dialogBox
                dismiss();
            }
        });
    }

    // Interface pour la communication entre le dialog et l'activité
    public interface OnThicknessSelectedListener {
        void onThicknessSelected(int thickness);
    }
}