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


public class PreguntasActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "preguntasActivity";
    private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private TextView preguntaTextView;
    private Button opcion1Button, opcion2Button, opcion3Button;
    private ImageView gifImageView;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private long tiempoRestante = TIEMPO_TOTAL;
    private static final long TIEMPO_TOTAL = 3000;
    private double puntuacionActual = 0;
    double puntuacion = 0;
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
//        cronometro();

        //guardar cual fue la ultima actividad
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", "preguntasActivity");
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
                        {"¿Cuál es la diferencia entre un requerimiento funcional y un requerimiento no funcional? ",
                                "Un requerimiento funcional define una función o característica específica que el sistema debe cumplir, mientras que un requerimiento no funcional se refiere a una restricción o limitación del sistema.", "Un requerimiento funcional se refiere a una restricción o limitación del sistema, mientras que un requerimiento no funcional define una función o característica específica que el sistema debe cumplir.", "No hay diferencia entre un requerimiento funcional y un requerimiento no funcional.", "Un requerimiento funcional define una función o característica específica que el sistema debe cumplir, mientras que un requerimiento no funcional se refiere a una restricción o limitación del sistema.", "Un requerimiento funcional es una descripción de las funciones o características que un sistema debe tener para cumplir con las necesidades del usuario, mientras que un requerimiento no funcional es una descripción de las restricciones o limitaciones que un sistema debe tener para cumplir con los Requerimientos del usuario. Es importante distinguir entre ambos tipos de Requerimientos para asegurar que el sistema cumpla con todas las necesidades y expectativas del usuario."},

                        {"¿Qué tipo de requerimiento se refiere a las capacidades del sistema para interactuar con otros sistemas?",
                                "requerimiento funcionales", "requerimiento no funcionales", "requerimiento de usuario","requerimento no funcionales", "Los Requerimientos no funcionales se refieren a los atributos de calidad del sistema, como la eficiencia, la escalabilidad, la seguridad, etc. En este caso, la capacidad del sistema para interactuar con otros sistemas es un requerimiento de interoperabilidad, que es un subtipo de Requerimientos no funcionales."},

                        {" ¿Qué tipo de requerimiento se refiere a la capacidad del sistema para procesar grandes volúmenes de datos en poco tiempo?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de usuario","Requerimientos no funcionales.", "La capacidad del sistema para procesar grandes volúmenes de datos en poco tiempo es un requerimiento de rendimiento, que es un subtipo de Requerimientos no funcionales."},

                        {"¿Qué tipo de requerimiento se refiere a las restricciones legales, regulatorias o contractuales que debe cumplir el sistema?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de dominio","Requerimientos de dominio",
                                " Los Requerimientos de dominio se refieren a las restricciones legales, regulatorias o contractuales que debe cumplir el sistema, así como a las características específicas del dominio en el que se utilizará el sistema."},

                        {"¿Qué tipo de requerimiento se refiere a la capacidad del sistema para adaptarse a diferentes tamaños de pantalla y dispositivos?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de usuario","Requerimientos no funcionales", "La capacidad del sistema para adaptarse a diferentes tamaños de pantalla y dispositivos es un requerimiento de portabilidad, que es un subtipo de Requerimientos no funcionales."},

                        {"¿Qué tipo de requerimiento se refiere a la capacidad del sistema para manejar múltiples usuarios que acceden simultáneamente?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de usuario","Requerimientos no funcionales", "Retroalimentación: La capacidad del sistema para manejar múltiples usuarios que acceden simultáneamente es un requerimiento de escalabilidad, que es un subtipo de Requerimientos no funcionales."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para cumplir con los estándares de la industria?",
                                "Requerimientos de calidad", "Requerimientos regulatorios", "Requerimientos de interoperabilidad","Requerimientos regulatorios",
                                "Los Requerimientos regulatorios se enfocan en la capacidad del software para cumplir con las regulaciones, leyes y estándares de la industria en la que se desarrolla."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para recuperar los datos en caso de una falla del sistema?",
                                "Requerimientos de confiabilidad", "Requerimientos de mantenimiento", "Requerimientos de seguridad"," Requerimientos de confiabilidad",
                                "Los Requerimientos de confiabilidad se enfocan en la capacidad del software para funcionar sin fallas y para recuperarse de las fallas que puedan ocurrir."},

                        {"¿Qué son los Requerimientos de calidad?", "Los Requerimientos que definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento.",
                                "Los Requerimientos que describen las características y funcionalidades que el software debe proporcionar.", "Los Requerimientos que especifican los límites de los recursos del hardware y del sistema operativo."," Los Requerimientos que definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento.", "Los Requerimientos de calidad definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento. Estos Requerimientos son importantes para garantizar que el software sea funcional, fácil de usar y de alta calidad."},

                        {"¿Cuál es el propósito de los Requerimientos de usuario?","Describir las necesidades del usuario en términos de funciones y características del software.",
                                "Proporcionar información detallada sobre la arquitectura del software.", "Describir las limitaciones del hardware y el sistema operativo.","Describir las necesidades del usuario en términos de funciones y características del software.", "Los Requerimientos de usuario describen las necesidades del usuario en términos de funciones y características del software. Estos Requerimientos son importantes porque ayudan a los desarrolladores a comprender las necesidades del usuario y a diseñar un software que satisfaga esas necesidades."}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 2", "Opción 1", "Opción 2", "Opción 3", "opcion 2", "retroalimentacion"},
                        {"Pregunta 2 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 3 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 4 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 5 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 6 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 7 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 8 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 9 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 10 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 6 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 7 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 8 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 9 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 10 del boton 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"}
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
//        opcion4Button.setText(opciones.get(3));

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

        // Verifica si la respuesta seleccionada es correcta
//        String respuestaCorrecta = preguntas[preguntaActual][4];
//        String retroalimentacion = preguntas[preguntaActual][5];

//        String retroalimentacion;
//        this.retroalimentacion = retroalimentacion;

        if (opcionSeleccionada == 0 && opcion1Button.getText().equals(respuestaCorrecta)) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
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

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
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
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity.this);
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
            if (preguntaActual < preguntas.length) {
                mostrarPregunta(preguntaActual);
            } else {
                countDownTimer.cancel();
                finish();
                mostrarFinalActivity(null);
            }
        }

        public void mostrarFinalActivity (View view){
            countDownTimer.cancel();
            Intent intent = new Intent(PreguntasActivity.this, FinalActivity.class);
            intent.putExtra("puntuacion", puntuacionActual);
            startActivity(intent);
        }

        public void cronometro () {
            countDownTimer = new CountDownTimer(20000, 1000) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();

        finish(); // Destruye la actividad actual (ActivityB) y regresa a la anterior (ActivityA)
    }

    public void salir (View view){
        countDownTimer.cancel();
        finish();
    }

}





