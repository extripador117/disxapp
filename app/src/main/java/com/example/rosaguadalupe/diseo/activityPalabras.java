package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class activityPalabras extends AppCompatActivity  implements View.OnClickListener{
    LinearLayout contenedorLetras;
    TextView letra;

    LinearLayout.LayoutParams paramsLetra;
    int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabras);
        contenedorLetras = (LinearLayout)findViewById(R.id.contenedorLetras);
        letra = new TextView(this);
        letra.setId(id);
        letra.setText("P");
        letra.setTextSize(24);
        letra.setTextColor(Color.BLACK);
        letra.setOnClickListener(this);
        paramsLetra =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsLetra.setMargins(25,25,25,25);
        letra.setLayoutParams(paramsLetra);
        contenedorLetras.addView(letra);

        letra = new TextView(this);
        id+=1;
        letra.setId(id);
        letra.setText("E");
        letra.setTextSize(24);
        letra.setTextColor(Color.BLACK);
        paramsLetra =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsLetra.setMargins(25,25,25,25);
        letra.setLayoutParams(paramsLetra);
        contenedorLetras.addView(letra);


    }

    @Override
    public void onClick(View view) {
        TextView Campo=findViewById(R.id.campoPalabras);;
        TextView textoPresionado = findViewById(view.getId());
        Campo.setText(Campo.getText().toString()+ textoPresionado.getText().toString());
    }
}
