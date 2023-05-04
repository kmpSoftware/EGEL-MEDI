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
                        {"¿Cuál es la diferencia entre un requisito funcional y un requisito no funcional? ", "Un requisito funcional define una función o característica específica que el sistema debe cumplir, mientras que un requisito no funcional se refiere a una restricción o limitación del sistema.", "Un requisito funcional se refiere a una restricción o limitación del sistema, mientras que un requisito no funcional define una función o característica específica que el sistema debe cumplir.", "No hay diferencia entre un requisito funcional y un requisito no funcional.", "Un requisito funcional define una función o característica específica que el sistema debe cumplir, mientras que un requisito no funcional se refiere a una restricción o limitación del sistema.", "Un requisito funcional es una descripción de las funciones o características que un sistema debe tener para cumplir con las necesidades del usuario, mientras que un requisito no funcional es una descripción de las restricciones o limitaciones que un sistema debe tener para cumplir con los requisitos del usuario. Es importante distinguir entre ambos tipos de requisitos para asegurar que el sistema cumpla con todas las necesidades y expectativas del usuario."},
                        {"Pregunta 2", "2Opción 1", "2Opción 2", "2Opción 3","2respuesta correcta", "retroalimentacion2"},
                        {"Pregunta 3", "3Opción 1", "3Opción 2", "3respuesta correcta","3respuesta correcta", "retroalimentacion3"},
                        {"Pregunta 4", "4Opción 1", "4Opción 2", "4Opción 3","4respuesta correcta", "retroalimentacion4"},
                        {"Pregunta 5", "5Opción 1", "5Opción 2", "5Opción 3","5respuesta correcta", "retroalimentacion5"},
                        {"Pregunta 6", "6respuesta correcta", "6Opción 2", "6Opción 3","6respuesta correcta", "retroalimentacion6"},
                        {"Pregunta 7", "7Opción 1", "7Opción 2", "7Opción 3","7respuesta correcta", "retroalimentacion7"},
                        {"Pregunta 8", "8Opción 1", "8Opción 2", "8Opción 3","8respuesta correcta", "retroalimentacion8"},
                        {"Pregunta 9", "9Opción 1", "9Opción 2", "9Opción 3","9respuesta correcta", "retroalimentacion9"},
                        {"Pregunta 10","10Opción 1", "10Opción 2", "10Opción 3","10respuesta correcta", "retroalimentacion10"}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 2", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
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





