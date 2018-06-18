package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityMenuJuegos extends AppCompatActivity  implements View.OnClickListener{
    Intent Activity;
    TextView opcionOrtografia,opcionOraciones,opcionPalabras,opcionEntendimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);

        opcionOrtografia =(TextView)findViewById(R.id.opcionOrtografia);
        opcionOrtografia.setOnClickListener(this);


        opcionOraciones =(TextView)findViewById(R.id.opcionOraciones);
        opcionOraciones.setOnClickListener(this);

        opcionPalabras =(TextView)findViewById(R.id.opcionPalabras);
        opcionPalabras.setOnClickListener(this);

        opcionEntendimiento = (TextView)findViewById(R.id.opcionEntendimiento);
        opcionEntendimiento.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.opcionOraciones:
                Activity = new Intent( view.getContext(),activityOraciones.class);
                startActivity(Activity);
                break;

            case R.id.opcionOrtografia:

                break;

            case R.id.opcionPalabras:

                break;

            case R.id.opcionEntendimiento:

                break;

            default:
                break;
        }
    }
}
