package com.example.egelisoft2.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egelisoft2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PreguntasActivity extends AppCompatActivity {

    private TextView preguntaTextView;
    private Button opcion1Button, opcion2Button, opcion3Button;
    private ProgressBar progressBar;

    private String[][] preguntas;

    private int preguntaActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);

        preguntaTextView = findViewById(R.id.preguntaTextView);
        opcion1Button = findViewById(R.id.opcion1Button);
        opcion2Button = findViewById(R.id.opcion2Button);
        opcion3Button = findViewById(R.id.opcion3Button);
        progressBar = findViewById(R.id.progressBar);

        // Lee el identificador del botón presionado del Intent
        Intent intent = getIntent();
        int botonPresionado = intent.getIntExtra("BOTON_PRESIONADO", 1);

        // Elige las preguntas correspondientes según el botón presionado
        switch (botonPresionado) {
            case 1:
                preguntas = new String[][]{
                        {"¿Qué son los requerimientos de software?", "Conjunto de especificaciones que describen lo que debe hacer el software", "Documentación que describe cómo fue diseñado el software", "Programas que permiten a los usuarios interactuar con el software", "Conjunto de especificaciones que describen lo que debe hacer el software"},
                        {"¿Por qué es importante definir los requerimientos de software?", "Para evitar malinterpretaciones y errores en el diseño del software", "Para crear documentación para futuras mejoras del software", "Para mantener a los programadores ocupados", "Para evitar malinterpretaciones y errores en el diseño del software"},
                        {"¿Qué es un requerimiento funcional?", "Un requerimiento que especifica una función específica que debe ser realizada por el software", "Un requerimiento que especifica cómo debe ser la apariencia visual del software", "Un requerimiento que especifica el hardware necesario para ejecutar el software", "Un requerimiento que especifica una función específica que debe ser realizada por el software"},
                        {"¿Qué es un requerimiento no funcional?", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones", "Un requerimiento que especifica una función específica que debe ser realizada por el software", "Un requerimiento que especifica el hardware necesario para ejecutar el software", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones"},
                        {"¿Qué es un requerimiento de usuario?", "Un requerimiento que especifica las necesidades del usuario", "Un requerimiento que especifica cómo debe ser la apariencia visual del software", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones", "Un requerimiento que especifica las necesidades del usuario"}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4"},
                        {"Pregunta 2 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4"},
                        {"Pregunta 3 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4"},
                        {"Pregunta 4 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4"},
                        {"Pregunta 5 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4"}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2"},
                        {"Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3"},
                        {"Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1"},
                        {"Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2"},
                        {"Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3"}
                };
                break;
        }

        // Muestra la primera pregunta
        mostrarPregunta(preguntaActual);

        // Agrega un listener a los botones de opción
        opcion1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(0);
            }
        });

        opcion2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(1);
            }
        });

        opcion3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(2);
            }
        });
    }

    private void mostrarPregunta(int indicePregunta) {
        // Muestra la pregunta y opciones correspondientes
        preguntaTextView.setText(preguntas[indicePregunta][0]);

        List<String> opciones = Arrays.asList(preguntas[indicePregunta][1], preguntas[indicePregunta][2], preguntas[indicePregunta][3]);
        Collections.shuffle(opciones);

        opcion1Button.setText(opciones.get(0));
        opcion2Button.setText(opciones.get(1));
        opcion3Button.setText(opciones.get(2));
    }

    private void verificarRespuesta(int opcionSeleccionada) {
        // Verifica si la respuesta seleccionada es correcta
        String respuestaCorrecta = preguntas[preguntaActual][4];

        System.out.println("respuesta correcta: " + respuestaCorrecta);
        System.out.println("respuesta seleccionada: " + preguntas[opcionSeleccionada][1]);

        if (opcionSeleccionada == 0 && opcion1Button.getText().equals(respuestaCorrecta)) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto");

        } else if (opcionSeleccionada == 1 && opcion2Button.getText().equals(respuestaCorrecta)) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto");

        } else if (opcionSeleccionada == 2 && opcion3Button.getText().equals(respuestaCorrecta)) {

           //mostrar el bottom sheet y mostrar el mensaje de respuesta correcta
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();


            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto");

        } else {
            //muestra el bottom sheet
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();

            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Incorrecto");
        }
        // Muestra la siguiente pregunta o finaliza la actividad
        preguntaActual++;
        if (preguntaActual < preguntas.length) {
            mostrarPregunta(preguntaActual);
        } else {
            finish();


        }
    }
}






