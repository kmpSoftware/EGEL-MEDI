package com.example.egelisoft2.ui.Preguntas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egelisoft2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
                        {"¿Qué son los requerimientos de software?", "Conjunto de especificaciones que describen lo que debe hacer el software", "Documentación que describe cómo fue diseñado el software", "Programas que permiten a los usuarios interactuar con el software", "Conjunto de especificaciones que describen lo que debe hacer el software", "Los requerimientos de software son una parte crucial del proceso de desarrollo de software. Son un conjunto de especificaciones que describen las necesidades, deseos y expectativas del cliente y los usuarios para el software. Estos requerimientos son la base para el diseño, la implementación y las pruebas del software. Una comprensión clara de los requerimientos de software es esencial para el éxito del proyecto, ya que ayuda a evitar malinterpretaciones y errores en el diseño y la construcción del software."},
                        {"¿Por qué es importante definir los requerimientos de software?", "Para evitar malinterpretaciones y errores en el diseño del software", "Para crear documentación para futuras mejoras del software", "Para mantener a los programadores ocupados", "Para evitar malinterpretaciones y errores en el diseño del software", "Definir correctamente los requerimientos de software es fundamental para el éxito del proyecto. Es importante porque permite a los desarrolladores comprender lo que se espera del software, evita malentendidos y errores en el diseño y construcción, y proporciona una base sólida para el plan de desarrollo del software. Al definir los requerimientos de software, se establece una visión clara y coherente de lo que se quiere lograr, lo que ayuda a mantener enfocado el proyecto y a asegurarse de que se alcancen los objetivos deseados."},
                        {"¿Qué es un requerimiento funcional?", "Un requerimiento que especifica una función específica que debe ser realizada por el software", "Un requerimiento que especifica cómo debe ser la apariencia visual del software", "Un requerimiento que especifica el hardware necesario para ejecutar el software", "Un requerimiento que especifica una función específica que debe ser realizada por el software", "Un requerimiento funcional es aquel que describe una función específica que debe ser realizada por el software. Los requerimientos funcionales describen lo que el software debe hacer, y suelen estar relacionados con tareas específicas que el usuario desea realizar con el software. Estos requerimientos son importantes porque describen la funcionalidad del software y son la base para el diseño, la implementación y las pruebas del software."},
                        {"¿Qué es un requerimiento no funcional?", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones", "Un requerimiento que especifica una función específica que debe ser realizada por el software", "Un requerimiento que especifica el hardware necesario para ejecutar el software", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones",  "Un requerimiento no funcional es aquel que describe cómo debe comportarse el software bajo ciertas condiciones. Estos requerimientos se centran en aspectos que no están directamente relacionados con la funcionalidad del software, como la seguridad, la escalabilidad, el rendimiento, la usabilidad, entre otros. Los requerimientos no funcionales son importantes porque aseguran que el software se comporte de la manera esperada en diferentes condiciones, y son la base para la evaluación del software en términos de calidad y eficacia."},
                        {"¿Qué es un requerimiento de usuario?", "Un requerimiento que especifica las necesidades del usuario", "Un requerimiento que especifica cómo debe ser la apariencia visual del software", "Un requerimiento que especifica cómo debe comportarse el software bajo ciertas condiciones", "Un requerimiento que especifica las necesidades del usuario","Un requerimiento de usuario es una descripción de las necesidades, deseos y expectativas de los usuarios finales del software. Estos requerimientos pueden ser explícitos o implícitos, y a menudo son recopilados mediante encuestas, entrevistas y otras técnicas de investigación de usuario. Es importante tener en cuenta que los requerimientos de usuario pueden ser muy diferentes a los requerimientos técnicos del software, y es necesario encontrar un equilibrio entre las necesidades del usuario y las limitaciones técnicas del software. Además, los requerimientos de usuario pueden evolucionar a lo largo del tiempo y pueden requerir actualizaciones periódicas para mantenerse alineados con las necesidades de los usuarios a medida que cambian. Por lo tanto, es fundamental tener en cuenta los requerimientos de usuario al diseñar y desarrollar un software exitoso y que cumpla con las necesidades de los usuarios."}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4","retroalimentacion"},
                        {"Pregunta 2 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4", "retroalimentacion"},
                        {"Pregunta 3 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4" , "retroalimentacion"},
                        {"Pregunta 4 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4" , "retroalimentacion"},
                        {"Pregunta 5 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 4" , "retroalimentacion"}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2"  , "retroalimentacion"},
                        {"Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3" , "retroalimentacion"},
                        {"Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1"   , "retroalimentacion"},
                        {"Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2" , "retroalimentacion"},
                        {"Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3" , "retroalimentacion"}
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

        String retroalimentacion = preguntas[preguntaActual][5];

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
            mensajeTextView.setText("Correcto\n"+ retroalimentacion);

            //set background color of bottom sheet
            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde

        } else if (opcionSeleccionada == 1 && opcion2Button.getText().equals(respuestaCorrecta)) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto\n"+ retroalimentacion);


            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde



        } else if (opcionSeleccionada == 2 && opcion3Button.getText().equals(respuestaCorrecta)) {

           //mostrar el bottom sheet y mostrar el mensaje de respuesta correcta
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();


            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto\n"+ retroalimentacion);


            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde
        } else {
            //muestra el bottom sheet
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();

            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView

            mensajeTextView.setText("Incorrecto\n"+ retroalimentacion);

            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_red_light); // Establece el color de fondo a verde

        }

        //guardar cual fue la ultima actividad
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", "preguntasActivity");
        editor.apply();

        // Muestra la siguiente pregunta o finaliza la actividad
        preguntaActual++;
        if (preguntaActual < preguntas.length) {
            mostrarPregunta(preguntaActual);
        } else {


            Intent intent = new Intent(PreguntasActivity.this, FinalActivity.class);
            startActivity(intent);


        }
    }
}






