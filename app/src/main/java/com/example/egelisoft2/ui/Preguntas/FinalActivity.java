package com.example.egelisoft2.ui.Preguntas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.example.egelisoft2.R;
import com.example.egelisoft2.ui.Subtemas.subtemas;
import com.example.egelisoft2.ui.Subtemas.subtemas2;
import com.example.egelisoft2.ui.Subtemas.subtemas3;
import com.example.egelisoft2.ui.Subtemas.subtemas4;
import com.example.egelisoft2.ui.Subtemas.subtemas5;
import com.example.egelisoft2.ui.Subtemas.subtemas6;

public class FinalActivity extends AppCompatActivity {
    Button button1;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscreen);


        // Establecer el modo de pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Obtener la puntuación del Intent
        button1 = findViewById(R.id.buttonSalir);
        textView = findViewById(R.id.puntuacionTextView);
        double puntuacion = getIntent().getDoubleExtra("puntuacion", 0.0);


        textView.setText("Tu puntuación es: " + puntuacion);

        if (puntuacion >= 3) {
            textView.setText("¡Felicidades! Has aprobado \nTu puntuación es: " + puntuacion);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif2).into(gifImageView);
        } else {
            textView.setText("¡Lo siento! Has reprobado\nTu puntuación es: " + puntuacion);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif).into(gifImageView);
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityName = getActivityName(); // Obtiene el nombre de la actividad anterior
                Intent intent = getNextActivityIntent(activityName); // Obtiene el Intent de la siguiente actividad

                //destruye la actividad actual
                finish();

                // Inicia la nueva actividad
                startActivity(intent);
            }
        });
    }

    // Obtiene el nombre de la actividad anterior desde SharedPreferences
    private String getActivityName() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        return sharedPreferences.getString("ultimaActividad", "");
    }

    // Obtiene el Intent de la siguiente actividad según el nombre de la actividad anterior
    private Intent getNextActivityIntent(String activityName) {
        if (activityName.equals("preguntasActivity")) {
            return new Intent(FinalActivity.this, subtemas.class);
        } else if (activityName.equals("preguntasActivity2")) {
            return new Intent(FinalActivity.this, subtemas2.class);
        } else if (activityName.equals("preguntasActivity3")) {
            return new Intent(FinalActivity.this, subtemas3.class);
        } else if (activityName.equals("preguntasActivity4")) {
            return new Intent(FinalActivity.this, subtemas4.class);
        } else if (activityName.equals("preguntasActivity5")) {
            return new Intent(FinalActivity.this, subtemas5.class);
        } else if (activityName.equals("preguntasActivity6")) {
            return new Intent(FinalActivity.this, subtemas6.class);
        } else {
            return new Intent(FinalActivity.this, subtemas6.class);
        }
    }
}
