package com.example.rosaguadalupe.diseo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class activityPuntajeActualHombre extends AppCompatActivity {
    TextView puntajeHombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_actual_hombre);
        puntajeHombre = (TextView) findViewById(R.id.puntajeHombre);
        dbConexion mod = new dbConexion(this, "dbDisxapp", null, 1);
        SQLiteDatabase db = mod.getWritableDatabase();
        Cursor puntos = db.rawQuery("SELECT  puntos FROM puntajes", null);

        if (puntos.moveToLast()) {
            puntajeHombre.setText(puntos.getString(0));
        }
        db.close();
    }
}
