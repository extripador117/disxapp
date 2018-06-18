package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView juegos;
    ImageView puntaje;
    ImageView Diagnostico;
    ImageView sabiasQue;
    Intent Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        puntaje=(ImageView)findViewById(R.id.puntaje);
        puntaje.setOnClickListener(this);

        juegos=(ImageView)findViewById(R.id.juegos);
        juegos.setOnClickListener(this);

        Diagnostico=(ImageView)findViewById(R.id.diagnostico);
        Diagnostico.setOnClickListener(this);

        sabiasQue=(ImageView)findViewById(R.id.sabiasQue);
        sabiasQue.setOnClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.juegos:
                Activity = new Intent( view.getContext(),activityMenuJuegos.class);
                startActivity(Activity);
                break;

            case R.id.puntaje:
                Activity = new Intent( view.getContext(),activityPuntaje.class);
                startActivity(Activity);
                break;

            case R.id.sabiasQue:
                Activity = new Intent( view.getContext(),activitySabiasQue.class);
                startActivity(Activity);
                break;

            case R.id.diagnostico:
                Activity = new Intent(view.getContext(),activityDiagnostico.class);
                startActivity(Activity);
                break;

            default:
                break;
        }
    }
}
