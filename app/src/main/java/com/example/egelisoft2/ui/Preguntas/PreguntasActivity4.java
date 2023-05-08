package com.example.egelisoft2.ui.Preguntas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.egelisoft2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PreguntasActivity4 extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "preguntasActivity4";
    private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private TextView preguntaTextView;
    private Button opcion1Button, opcion2Button, opcion3Button;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private long tiempoRestante = TIEMPO_TOTAL;
    private static final long TIEMPO_TOTAL = 4000;
    private double puntuacionActual = 0;
    double puntuacion = 0;
    private ImageView gifImageView;
    private String[][] preguntas;
    private int preguntaActual = 0;
    private TextView tiempoTextView;
    private Button siguienteButton;
    private   String retroalimentacion;
    private   String respuestaCorrecta;

    // Lista de índices de preguntas para mostrar en un orden aleatorio
    private List<Integer> listaIndicesPreguntas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //guardar cual fue la ultima actividad
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", "preguntasActivity4");
        editor.apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas);

        establecerPantallaCompleta();
        inicializarVistas();

        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarRespuesta(3, retroalimentacion, respuestaCorrecta);
            }
        });

        Glide.with(this).load(R.raw.timer).into(gifImageView);

        // Lee el identificador del botón presionado del Intent
        Intent intent = getIntent();
        int botonPresionado = intent.getIntExtra("BOTON_PRESIONADO", 0);

        // Elige las preguntas correspondientes según el botón presionado
        switch (botonPresionado) {
            case 1:
                preguntas = new String[][]{
                        {"4Pregunta 1 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 2 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 3 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 4 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 5 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 6 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 7 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 8 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 9 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 10 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"¿Cuál es el objetivo principal de la calidad de software?",
                                "Satisfacer las necesidades y expectativas del cliente",
                                "Maximizar el beneficio económico de la empresa",
                                "Acelerar el proceso de desarrollo del software",
                                "Satisfacer las necesidades y expectativas del cliente",
                                "La calidad de software busca satisfacer las necesidades y expectativas del cliente, lo cual incluye factores como fiabilidad, eficiencia, usabilidad y seguridad."},

                        {"¿Qué es la prueba de software?",
                                "Un proceso de ejecución manual del software para detectar errores",
                                "Un proceso de análisis estático del código fuente",
                                "Un proceso de validación del software para comprobar su cumplimiento de los requisitos",
                                "Un proceso de validación del software para comprobar su cumplimiento de los requisitos",
                                "La prueba de software es el proceso de validación del software para comprobar su cumplimiento de los requisitos especificados, mediante la detección de errores, defectos o fallos en el comportamiento del software."},

                        {"¿Cuál es la diferencia entre verificación y validación?",
                                "La verificación se enfoca en confirmar que el software cumple con los requisitos, mientras que la validación se enfoca en confirmar que el software cumple con las necesidades del cliente",
                                "La verificación se enfoca en confirmar que el software cumple con las necesidades del cliente, mientras que la validación se enfoca en confirmar que el software cumple con los requisitos",
                                "La verificación y la validación son lo mismo",
                                "La verificación se enfoca en confirmar que el software cumple con los requisitos, mientras que la validación se enfoca en confirmar que el software cumple con las necesidades del cliente",
                                "La verificación se enfoca en confirmar que el software cumple con los requisitos especificados, mientras que la validación se enfoca en confirmar que el software cumple con las necesidades y expectativas del cliente."},


                        {"¿Qué es la norma ISO 9001 en relación con el software ?",
                                "Una norma que establece los requisitos para un sistema de gestión de calidad en cualquier organización",
                                "Una norma que establece los requisitos específicos para la gestión de calidad en el desarrollo de software",
                                "Una norma que establece los requisitos específicos para la gestión de calidad en la producción de hardware",
                                "Una norma que establece los requisitos para un sistema de gestión de calidad en cualquier organización",
                "La norma ISO 9001 establece los requisitos para la gestión de calidad en cualquier organización, incluyendo requisitos específicos para el desarrollo de software",
},
            {"¿Qué es el modelo de madurez de capacidad (CMM) en relación con el software?",
                "Un modelo que establece los requisitos para la certificación de software",
                "Un modelo que establece los niveles de madurez de la organización en cuanto a la calidad del software",
                "Un modelo que establece los requisitos para la gestión de proyectos de software",
                    "Un modelo que establece los niveles de madurez de la organización en cuanto a la calidad del software",
                "El modelo de madurez de capacidad (CMM) establece los niveles de madurez de una organización en cuanto a la calidad del software que produce",
},
            {"¿Qué es la prueba de aceptación del usuario en el ciclo de vida del software?",
                    "La prueba realizada por el desarrollador antes de entregar el software al cliente",
                "La prueba realizada por el usuario final antes de la aceptación del software",
                "La prueba realizada por el equipo de aseguramiento de calidad antes de la liberación del software",
                    "La prueba realizada por el usuario final antes de la aceptación del software",
                "La prueba de aceptación del usuario es una prueba realizada por el usuario final antes de la aceptación del software"
        },
        {"¿Cuál es el objetivo de la norma ISO/IEC 12207 en relación con el software?",
                    "Establecer los requisitos para la certificación de software",
                "Establecer los requisitos para el desarrollo y mantenimiento de software",
                "Establecer los requisitos para la gestión de proyectos de software",
                "Establecer los requisitos para el desarrollo y mantenimiento de software",
                "La norma ISO/IEC 12207 establece los requisitos para el desarrollo y mantenimiento de software"
    },
    {"¿Qué es la inspección de código en el aseguramiento de la calidad del software?",
                "La revisión del código fuente para detectar errores",
                "La revisión del software para detectar errores funcionales",
                "La prueba del software para detectar errores de integración",
            "La revisión del código fuente para detectar errores",
                "La inspección de código es la revisión del código fuente para detectar errores y violaciones de estándares de programación",
},
        {"¿Qué es la prueba de estrés en el aseguramiento de la calidad del software?",
                "La prueba del software para detectar errores de integración",
                "La prueba del software para detectar errores funcionales",
                "La prueba del software para evaluar su rendimiento en condiciones extremas",
                "La prueba del software para evaluar su rendimiento en condiciones extremas",
                "La prueba de estrés es la prueba del software para evaluar su rendimiento en condiciones extremas, como cargas máximas y entornos inestables"
        },
                        {"4Pregunta 5 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 6 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 7 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 8 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 9 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 10 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"}
                };
                break;
            case 3:
                preguntas = new String[][]{

                        {"¿Cuál de las siguientes metodologías se enfoca en la entrega continua de software y la colaboración estrecha entre desarrolladores y clientes?",

                    "Metodología Scrum",
                "Modelo en cascada",
                "Metodología ágil",
                "Metodología Scrum",
                "La metodología Scrum se enfoca en la entrega continua de software, la colaboración entre los miembros del equipo y los clientes, y la adaptación a los cambios en los requerimientos del proyecto."},

                        {"¿Cuál de las siguientes metodologías se enfoca en la planificación detallada del proyecto antes de comenzar la etapa de desarrollo?",
                                "Modelo en cascada",
                "Metodología ágil",
                "Metodología Scrum",
                "Modelo en cascada",
                "El modelo en cascada se enfoca en la planificación detallada del proyecto antes de comenzar la etapa de desarrollo, lo que puede ser útil en proyectos con requerimientos estables y bien definidos."},

                        {"¿Cuál de las siguientes metodologías se enfoca en la entrega temprana y frecuente de pequeñas partes funcionales del software?",
                                "Modelo en cascada",
                "Metodología Scrum",
                "Metodología ágil",
                "Metodología ágil",
                "Las metodologías ágiles se enfocan en la entrega temprana y frecuente de pequeñas partes funcionales del software, la colaboración entre los miembros del equipo y los clientes, y la adaptación a los cambios en los requerimientos del proyecto."},

                        {"¿Cuál de las siguientes metodologías se enfoca en la mejoramiento continuo del proceso de desarrollo de software?",
                                "Modelo en cascada",
                "Metodología Scrum",
                "Metodología cascada",
                "Metodología ágil",
                                "metodo agil",
                "Las metodologías ágiles se enfocan en la mejora continua del proceso de desarrollo de software, lo que puede ayudar a incrementar la eficiencia y calidad del equipo de desarrollo."},

                        {"¿Cuál de las siguientes metodologías se enfoca en la definición y validación de los requerimientos del proyecto antes de comenzar la etapa de desarrollo?",
                                "Modelo en agil",
                "Metodología cascada",
                "Metodología Scrum",
                "Modelo en cascada",
                "El modelo en cascada se enfoca en la definición y validación de los requerimientos del proyecto antes de comenzar la etapa de desarrollo, lo que puede ser útil en proyectos con requerimientos estables y bien definidos."},

                        {"¿Cuál es la principal ventaja de la metodología en espiral en la gestión de proyectos de software?",
                                "Mayor eficiencia en el uso de recursos",
                "Mayor capacidad de adaptación a los cambios",
                "Reducción del costo total del proyecto",
                "Mayor capacidad de adaptación a los cambios",
                "La metodología en espiral se centra en la adaptabilidad y la flexibilidad del proceso de desarrollo de software, lo que permite una mayor capacidad de adaptación a los cambios durante todo el ciclo de vida del proyecto."},

                        {"¿Cuál es la principal diferencia entre la metodología Scrum y la metodología Kanban?",
                                "La metodología Scrum es más adecuada para equipos pequeños y Kanban para equipos grandes",
                "La metodología Scrum se centra en entregables específicos y Kanban en el flujo de trabajo continuo",
                "La metodología Scrum utiliza reuniones diarias y Kanban no",
                "La metodología Scrum se centra en entregables específicos y Kanban en el flujo de trabajo continuo",
                "La metodología Scrum se centra en entregables específicos y Kanban en el flujo de trabajo continuo"},

                        {"¿Cuál es el enfoque de desarrollo que se caracteriza por una secuencia de etapas lineales y secuenciales?", "Flujo de proceso iterativo", "Flujo de proceso evolutivo", "Flujo de proceso lineal", "Flujo de proceso lineal", "El flujo de proceso lineal es una secuencia de etapas en orden secuencial."},
                        {"¿Qué enfoque de desarrollo implica un proceso continuo de planificación, diseño, construcción y evaluación?", "Flujo de proceso en cascada", "Flujo de proceso iterativo", "Flujo de proceso en espiral", "Flujo de proceso iterativo", "El flujo de proceso iterativo implica un ciclo continuo de planificación, diseño, construcción y evaluación."},
                        {"¿Qué flujo de proceso involucra múltiples ciclos de desarrollo, cada uno de los cuales agrega funcionalidad al sistema?", "Flujo de proceso en cascada", "Flujo de proceso iterativo", "Flujo de proceso evolutivo", "Flujo de proceso evolutivo", "El flujo de proceso evolutivo implica múltiples ciclos de desarrollo que agregan funcionalidad al sistema en cada iteración."},
                        {"¿Cuál es el enfoque de desarrollo que implica la repetición de ciclos de planificación, diseño, construcción y evaluación?", "Flujo de proceso iterativo", "Flujo de proceso en cascada", "Flujo de proceso en espiral", "Flujo de proceso iterativo", "El flujo de proceso iterativo implica la repetición de ciclos de planificación, diseño, construcción y evaluación."},
                        {"¿Qué enfoque de desarrollo se centra en la identificación temprana y la corrección de problemas?", "Flujo de proceso en cascada", "Flujo de proceso en espiral", "Flujo de proceso evolutivo", "Flujo de proceso en espiral", "El flujo de proceso en espiral se centra en la identificación temprana y la corrección de problemas a través de múltiples ciclos de desarrollo."},
                        {"¿Qué flujo de proceso implica una serie de etapas de desarrollo en las que se agregan y prueban nuevas funcionalidades en cada iteración?", "Flujo de proceso en cascada", "Flujo de proceso evolutivo", "Flujo de proceso iterativo", "Flujo de proceso iterativo", "El flujo de proceso iterativo implica la adición y prueba de nuevas funcionalidades en cada iteración de desarrollo."},
                        {"¿Qué enfoque de desarrollo implica una planificación cuidadosa de las fases del proyecto y una secuencia de etapas lineales?", "Flujo de proceso en cascada", "Flujo de proceso en espiral", "Flujo de proceso evolutivo", "Flujo de proceso en cascada", "El flujo de proceso en cascada implica una planificación cuidadosa y una secuencia de etapas lineales para completar un proyecto de software."},



                        {"4Pregunta 1 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 6 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 7 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"4Pregunta 8 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"4Pregunta 9 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"4Pregunta 10 del botón 1", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"}
                };
                break;
        }
        // Inicializa la lista de índices de preguntas y la mezcla
        listaIndicesPreguntas = new ArrayList<>();
        for (int i = 0; i < preguntas.length; i++) {
            listaIndicesPreguntas.add(i);
        }
        Collections.shuffle(listaIndicesPreguntas);

        // Muestra la primera pregunta
        mostrarPregunta(preguntaActual);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();

        finish(); // Destruye la actividad actual (ActivityB) y regresa a la anterior (ActivityA)
    }


    private void mostrarPregunta(int indicePregunta) {
        cronometro();
        // Obtiene el índice de la pregunta a mostrar
        int indiceRealPregunta = listaIndicesPreguntas.get(indicePregunta);

        // Muestra la pregunta y opciones correspondientes
//        preguntaTextView.setText(preguntas[indicePregunta][0]);
        preguntaTextView.setText(preguntas[indiceRealPregunta][0]);
//        List<String> opciones = Arrays.asList(preguntas[indicePregunta][1], preguntas[indicePregunta][2], preguntas[indicePregunta][3]);
        List<String> opciones = Arrays.asList(preguntas[indiceRealPregunta][1], preguntas[indiceRealPregunta][2], preguntas[indiceRealPregunta][3]);
        Collections.shuffle(opciones);
        opcion1Button.setText(opciones.get(0));
        opcion2Button.setText(opciones.get(1));
        opcion3Button.setText(opciones.get(2));
        retroalimentacion = preguntas[indiceRealPregunta][5];
        respuestaCorrecta = preguntas[indiceRealPregunta][4];

        // Agrega onClickListeners a los botones de opción para manejar la selección de respuesta
        opcion1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarRespuesta(0, retroalimentacion, respuestaCorrecta);
            }
        });
        opcion2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarRespuesta(1, retroalimentacion, respuestaCorrecta);
            }
        });
        opcion3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarRespuesta(2, retroalimentacion, respuestaCorrecta);
            }
        });

    }

    private void verificarRespuesta(int opcionSeleccionada, String retroalimentacion, String respuestaCorrecta) {
        // Detener el cronómetro
        countDownTimer.cancel();


        if (opcionSeleccionada == 0 && opcion1Button.getText().equals(respuestaCorrecta)) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity4.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Establecer el listener de cancelación
            bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //Aquí es donde se ejecuta la función cuando se oculta el BottomSheetDialog
                    mostrarSiguientePregunta(null, puntuacion);
                }
            });
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto\n" + retroalimentacion);
            puntuacion += 1;

            //set background color of bottom sheet
            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde


        } else if (opcionSeleccionada == 1 && opcion2Button.getText().equals(respuestaCorrecta)) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity4.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Establecer el listener de cancelación
            bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //Aquí es donde se ejecuta la función cuando se oculta el BottomSheetDialog
                    mostrarSiguientePregunta(null, puntuacion);
                }
            });
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto\n" + retroalimentacion);
            puntuacion += 1;

            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde

        } else if (opcionSeleccionada == 2 && opcion3Button.getText().equals(respuestaCorrecta)) {

            //mostrar el bottom sheet y mostrar el mensaje de respuesta correcta
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity4.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Establecer el listener de cancelación
            bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //Aquí es donde se ejecuta la función cuando se oculta el BottomSheetDialog
                    mostrarSiguientePregunta(null, puntuacion);
                }
            });
            bottomSheetDialog.show();
            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Correcto\n" + retroalimentacion);
            puntuacion += 1;

            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde

        } else {
            //muestra el bottom sheet
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity4.this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Establecer el listener de cancelación

            bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //Aquí es donde se ejecuta la función cuando se oculta el BottomSheetDialog
                    mostrarSiguientePregunta(null, puntuacion);
                }
            });
            bottomSheetDialog.show();

            //Obtener el TextView donde se mostrará el mensaje
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            //Asignar el mensaje al TextView
            mensajeTextView.setText("Incorrecto\n" + retroalimentacion);
            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_red_light); // Establece el color de fondo a verde
        }
    }
    public void mostrarSiguientePregunta (View view,double puntuacion){

        puntuacionActual = puntuacion;
        // Muestra la siguiente pregunta o finaliza la actividad
        //progres bar
        progressBar.setProgress(preguntaActual + 1);
        preguntaActual++;
        if (preguntaActual < 10) {
            mostrarPregunta(preguntaActual);
        } else {
            countDownTimer.cancel();
            finish();
            mostrarFinalActivity(null);
        }
    }

    public void mostrarFinalActivity (View view){
        countDownTimer.cancel();
        Intent intent = new Intent(PreguntasActivity4.this, FinalActivity.class);
        intent.putExtra("puntuacion", puntuacionActual);
        startActivity(intent);
    }

    private void inicializarVistas() {
        preguntaTextView = findViewById(R.id.preguntaTextView);
        opcion1Button = findViewById(R.id.opcion1Button);
        opcion2Button = findViewById(R.id.opcion2Button);
        opcion3Button = findViewById(R.id.opcion3Button);
        progressBar = findViewById(R.id.progressBar);
        tiempoTextView = findViewById(R.id.tiempoTextView);
        gifImageView = findViewById(R.id.gifImageView);
        siguienteButton = findViewById(R.id.siguienteButton);
    }

    private void establecerPantallaCompleta() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(UI_OPTIONS);
    }
    public void salir (View view){
        countDownTimer.cancel();
        finish();
    }

    public void cronometro () {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                tiempoTextView.setText("Tiempo restante: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                tiempoTextView.setText("Tiempo restante: 0");
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator.hasVibrator()) {
                    // Crear un efecto de vibración
                    VibrationEffect vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                    // Vibra el teléfono con el efecto creado
                    vibrator.vibrate(vibrationEffect);
                }
                verificarRespuesta(-1, retroalimentacion, respuestaCorrecta); // indica que no se seleccionó ninguna respuesta
            }
        }.start();
    }
}
