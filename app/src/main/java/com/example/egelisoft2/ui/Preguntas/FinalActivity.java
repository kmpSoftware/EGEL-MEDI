package com.example.egelisoft2.ui.Preguntas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egelisoft2.R;
import com.example.egelisoft2.ui.Subtemas.subtemas;
import com.example.egelisoft2.ui.Subtemas.subtemas2;

public class FinalActivity extends AppCompatActivity {

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscreen);

        button1 = findViewById(R.id.buttonSalir);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityName = getActivityName(); // Obtiene el nombre de la actividad anterior
                Intent intent = getNextActivityIntent(activityName); // Obtiene el Intent de la siguiente actividad

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
        } else {
            return new Intent(FinalActivity.this, subtemas2.class);
        }
    }
}
