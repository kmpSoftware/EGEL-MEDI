package com.example.egelisoft2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egelisoft2.R;

public class subtemas2 extends AppCompatActivity{



        Button button1;
        Button button2;
        Button button3;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.subtemas);

            // Busca el botón por su id
            button1 = findViewById(R.id.button1);
            button2 = findViewById(R.id.button2);
            button3 = findViewById(R.id.button3);



            // Agrega un listener al botón
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crea un intent para abrir la nueva actividad
                    Intent intent = new Intent(com.example.egelisoft2.ui.dashboard.subtemas2.this, PreguntasActivity2.class);

                    // Agrega un identificador al Intent para indicar que se presionó el botón 1
                    intent.putExtra("BOTON_PRESIONADO", 1);

                    // Inicia la nueva actividad
                    startActivity(intent);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crea un intent para abrir la nueva actividad
                    Intent intent = new Intent(com.example.egelisoft2.ui.dashboard.subtemas2.this, PreguntasActivity2.class);

                    // Agrega un identificador al Intent para indicar que se presionó el botón 2
                    intent.putExtra("BOTON_PRESIONADO", 2);

                    // Inicia la nueva actividad
                    startActivity(intent);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crea un intent para abrir la nueva actividad
                    Intent intent = new Intent(com.example.egelisoft2.ui.dashboard.subtemas2.this, PreguntasActivity2.class);

                    // Agrega un identificador al Intent para indicar que se presionó el botón 3
                    intent.putExtra("BOTON_PRESIONADO", 3);

                    // Inicia la nueva actividad
                    startActivity(intent);
                }
            });
        }
    }


