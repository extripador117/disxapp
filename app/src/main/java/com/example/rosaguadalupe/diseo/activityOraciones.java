package com.example.rosaguadalupe.diseo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class activityOraciones extends AppCompatActivity  {
    Button botonPalabra;
    Button botonPalabra2;
    LinearLayout contenedor;
    LinearLayout.LayoutParams boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraciones);
        contenedor = (LinearLayout)findViewById(R.id.contenedor);

        botonPalabra = new Button(this);
        botonPalabra.setText("perro");
        botonPalabra.setTextColor(Color.WHITE);
        botonPalabra.setTextSize(18);
        botonPalabra.setBackgroundColor(Color.parseColor("#6543e2"));
        boton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        boton.setMargins(25,25,25,25);
        botonPalabra.setLayoutParams(boton);

        contenedor.addView(botonPalabra);
    }
}
