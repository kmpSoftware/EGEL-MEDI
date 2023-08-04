package com.kmpegel.medi.ui.tarjetas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kmpegel.egelmedi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class Tarjetas extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "Tarjetas";
    private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private TextView preguntaTextView;
    private Button verRespuesta;
    private String[][] preguntas;
    private int preguntaActual = 0;
    private   String retroalimentacion;
    private ImageView verde;
    private ImageView rojo;
    private ImageView amarillo;
    private TextView colorTextView;
    private List<Integer> listaIndicesPreguntas;

    private static final int PROB_ROJO = 10;     // Probabilidad del color rojo (ejemplo: 10%)
    private static final int PROB_VERDE = 60;    // Probabilidad del color verde (ejemplo: 60%)
    private static final int PROB_AMARILLO = 30; // Probabilidad del color amarillo (ejemplo: 30%)
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        random = new Random();

        //guardar cual fue la ultima actividad
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", "preguntasActivity");
        editor.apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tarjetas);

        establecerPantallaCompleta();
        inicializarVistas();

                preguntas = new String[][]{

                        {"PREGUNTA 1",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 2",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 3",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 4",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 5",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 6",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 7",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 8",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 9",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 10",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 11",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 12",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 13",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 14",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 15",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 16",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 17",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 18",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 19",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 20",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 21",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 22",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 23",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 24",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 25",
                                "RESPUESTA",
                                "verde"},
                        {"PREGUNTA 26",
                                "RESPUESTA",
                                "rojo"},
                        {"PREGUNTA 27",
                                "RESPUESTA",
                                "rojo"},
                        {"PREGUNTA 28",
                                "RESPUESTA",
                                "rojo"},
                        {"PREGUNTA 29",
                                "RESPUESTA",
                                "rojo"},

                };

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
        int indiceRealPregunta = listaIndicesPreguntas.get(indicePregunta);

        preguntaTextView.setText(preguntas[indiceRealPregunta][0]);
        retroalimentacion = preguntas[indiceRealPregunta][1];

        String color = preguntas[indiceRealPregunta][2]; // Obtener el color actual de la pregunta
        //mandar ese color a u texview
        colorTextView.setText(color);

        verRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MostrarRespuesta( retroalimentacion);
            }
        });

        rojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verde.setVisibility(View.INVISIBLE);
                amarillo.setVisibility(View.INVISIBLE);
                rojo.setVisibility(View.INVISIBLE);
                preguntas[indiceRealPregunta][2] = "rojo";
                mostrarSiguientePregunta( null);
            }
        });
        verde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verde.setVisibility(View.INVISIBLE);
                amarillo.setVisibility(View.INVISIBLE);
                rojo.setVisibility(View.INVISIBLE);
                preguntas[indiceRealPregunta][2] = "verde";
                mostrarSiguientePregunta( null);
            }
        });
        amarillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verde.setVisibility(View.INVISIBLE);
                amarillo.setVisibility(View.INVISIBLE);
                rojo.setVisibility(View.INVISIBLE);
                preguntas[indiceRealPregunta][2] = "amarillo";
                mostrarSiguientePregunta( null);
            }
        });
    }
    private void MostrarRespuesta(String retroalimentacion) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Establecer el listener de cancelación
            bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                    verde.setVisibility(View.INVISIBLE);
                    amarillo.setVisibility(View.INVISIBLE);
                    rojo.setVisibility(View.INVISIBLE);
                    //Aquí es donde se ejecuta la función cuando se oculta el BottomSheetDialog
                    mostrarSiguientePregunta( null);
                }
            });
            bottomSheetDialog.show();
            TextView mensajeTextView = bottomSheetDialog.findViewById(R.id.mensajeTextView);
            LinearLayout bottomSheetLayout = bottomSheetDialog.findViewById(R.id.bottom_sheet);
            bottomSheetLayout.setBackgroundResource(android.R.color.holo_green_light); // Establece el color de fondo a verde
             mensajeTextView.setText(retroalimentacion);
    }
    public void mostrarSiguientePregunta(View view) {
            preguntaActual++;
        if (preguntaActual >= listaIndicesPreguntas.size()) {
            preguntaActual = 0; // Reiniciar al primer índice cuando se llega al final de la lista
            Collections.shuffle(listaIndicesPreguntas);
        }
        int indicePregunta = listaIndicesPreguntas.get(preguntaActual);
        mostrarPregunta(indicePregunta);

    }

    private void inicializarVistas() {
        rojo = findViewById(R.id.rojo);
        amarillo = findViewById(R.id.amarillo);
        verde = findViewById(R.id.verde);
        preguntaTextView = findViewById(R.id.preguntaTextView);
        verRespuesta = findViewById(R.id.verRespuesta);
        colorTextView = findViewById(R.id.colorTextView);
    }
    private void establecerPantallaCompleta() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(UI_OPTIONS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // Destruye la actividad actual (ActivityB) y regresa a la anterior (ActivityA)
    }

    public void salir (View view){
        finish();
    }

}
