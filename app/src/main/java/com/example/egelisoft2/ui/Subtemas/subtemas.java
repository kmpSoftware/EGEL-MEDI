package com.example.egelisoft2.ui.Subtemas;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.egelisoft2.R;

import com.example.egelisoft2.ui.Preguntas.PreguntasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class subtemas extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button btnVolver;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtemas);


        // Busca el botón por su id
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        btnVolver = findViewById(R.id.btnVolver);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cerrar la actividad actual

                // Navegar hacia el fragmento DashboardFragment
                NavController navController = Navigation.findNavController(subtemas.this, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_dashboard);
                finish();
            }
        });

        // Agrega un listener al botón
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 1
                intent.putExtra("BOTON_PRESIONADO", 1);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 2
                intent.putExtra("BOTON_PRESIONADO", 2);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 3
                intent.putExtra("BOTON_PRESIONADO", 3);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });
    }
}
