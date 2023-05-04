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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class PreguntasActivity2 extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "preguntasActivity2";
    private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private ImageView gifImageView;
    private TextView preguntaTextView;
    private Button opcion1Button, opcion2Button, opcion3Button;
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
    private List<Integer> listaIndicesPreguntas;
    private   String retroalimentacion;
    private   String respuestaCorrecta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //guardar cual fue la ultima actividad
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ultimaActividad", "preguntasActivity2");
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
                        {"¿Cuál es el objetivo principal del diseño arquitectónico de software?",
                                "Proporcionar una visión general de la estructura del software", "Escribir código para implementar características específicas", "Definir pruebas para validar el software", "Proporcionar una visión general de la estructura del software",
                                "El diseño arquitectónico de software tiene como objetivo proporcionar una visión general de la estructura del software y su organización."},

                        {"¿Qué tipo de patrón de diseño arquitectónico se utiliza para separar la presentación de la lógica de negocio en una aplicación web?",
                                "Patrón Modelo-Vista-Controlador (MVC)", "Patrón Modelo de Negocio - Vista - Controlador (MNBC)", "Patrón Vista - Controlador - Modelo (VCM)", "Patrón Modelo-Vista-Controlador (MVC)",
                                "El patrón Modelo-Vista-Controlador (MVC) se utiliza comúnmente para separar la presentación de la lógica de negocio en una aplicación web."},

                        {"¿Qué es un componente en el diseño arquitectónico de software?",
                                "Un módulo independiente que realiza una función específica", "Una librería de funciones utilizada por todo el sistema", "Un archivo que contiene código fuente", "Un módulo independiente que realiza una función específica",
                                "Un componente en el diseño arquitectónico de software es un módulo independiente que realiza una función específica y puede ser reutilizado en diferentes partes del sistema."},

                        {"¿Qué es la cohesión en el diseño arquitectónico de software?",
                                "La medida en que los elementos de un módulo se relacionan entre sí", "La medida en que un módulo realiza una sola función", "La medida en que un módulo depende de otros módulos", "La medida en que un módulo realiza una sola función",
                                "La cohesión en el diseño arquitectónico de software se refiere a la medida en que un módulo realiza una sola función y sus elementos están relacionados entre sí."},

                        {"¿Qué es el acoplamiento en el diseño arquitectónico de software?",
                                "La medida en que un módulo depende de otros módulos", "La medida en que los elementos de un módulo se relacionan entre sí", "La medida en que un módulo realiza una sola función", "La medida en que un módulo depende de otros módulos",
                                "El acoplamiento en el diseño arquitectónico de software se refiere a la medida en que un módulo depende de otros módulos. Un bajo acoplamiento se considera deseable, ya que significa que los cambios en un módulo tienen poco efecto sobre otros módulos."},

                        {"¿Qué patrón de diseño se utiliza para separar la lógica de presentación de la lógica de negocio en una aplicación web?", "Modelo-Vista-Controlador (MVC)", "Capas", "Flujo de Datos", "Modelo-Vista-Controlador (MVC)", "El patrón MVC es una técnica de diseño que separa la lógica de presentación de la lógica de negocio en una aplicación web."},

                        {"¿Qué patrón de diseño se utiliza para dividir una aplicación en módulos interconectados?", "Arquitectura en capas", "Inyección de dependencias", "Patrón Decorador", "Arquitectura en capas", "La arquitectura en capas es un patrón de diseño que divide una aplicación en módulos interconectados."},

                        {"¿Qué patrón de diseño se utiliza para construir sistemas distribuidos escalables y tolerantes a fallos?", "Arquitectura de Microservicios", "Arquitectura basada en Eventos", "Patrón Puente", "Arquitectura de Microservicios", "La arquitectura de Microservicios es un patrón de diseño que se utiliza para construir sistemas distribuidos escalables y tolerantes a fallos."},

                        {"¿Qué patrón de diseño se utiliza para definir una interfaz para crear un objeto, pero permitiendo que las subclases decidan qué clase instanciar?", "Patrón Factory Method", "Patrón Singleton", "Patrón Decorador", "Patrón Factory Method", "El patrón Factory Method es utilizado para definir una interfaz para crear un objeto, pero permitiendo que las subclases decidan qué clase instanciar."},

                        {"¿Qué patrón de diseño se utiliza para proporcionar una forma de acceder a un objeto sin exponer su implementación?", "Patrón Proxy", "Patrón Adaptador", "Patrón Decorador", "Patrón Proxy", "El patrón Proxy se utiliza para proporcionar una forma de acceder a un objeto sin exponer su implementación."},

                        {"¿Qué patrón de arquitectura de software permite dividir el sistema en componentes y definir sus interacciones a través de interfaces?", "Arquitectura basada en componentes", "Arquitectura de microservicios", "Arquitectura cliente-servidor", "Arquitectura basada en componentes", "La arquitectura basada en componentes permite dividir el sistema en componentes autónomos y definir sus interacciones a través de interfaces bien definidas."},

                        {"¿Qué patrón de arquitectura de software se enfoca en la creación de servicios independientes y autónomos que pueden ser escalados y actualizados de manera independiente?", "Arquitectura de microservicios", "Arquitectura basada en componentes", "Arquitectura cliente-servidor", "Arquitectura de microservicios", "La arquitectura de microservicios se enfoca en la creación de servicios independientes y autónomos que pueden ser escalados y actualizados de manera independiente, lo que permite una mayor flexibilidad y adaptabilidad del sistema."},

                        {"¿Qué patrón de arquitectura de software se enfoca en la separación de las capas de presentación, lógica de negocio y almacenamiento de datos?", "Arquitectura en capas", "Arquitectura cliente-servidor", "Arquitectura basada en eventos", "Arquitectura en capas", "La arquitectura en capas se enfoca en la separación de las capas de presentación, lógica de negocio y almacenamiento de datos, lo que permite una mayor modularidad y mantenibilidad del sistema."},

                        {"¿Qué patrón de arquitectura de software se enfoca en la creación de componentes reutilizables que pueden ser combinados para formar diferentes soluciones?", "Arquitectura orientada a servicios", "Arquitectura basada en eventos", "Arquitectura basada en componentes", "Arquitectura basada en componentes", "La arquitectura basada en componentes se enfoca en la creación de componentes reutilizables que pueden ser combinados para formar diferentes soluciones, lo que permite una mayor flexibilidad y adaptabilidad del sistema."},

                        {"¿Qué patrón de arquitectura de software se enfoca en la creación de sistemas altamente escalables y tolerantes a fallos?", "Arquitectura basada en eventos", "Arquitectura de microservicios", "Arquitectura cliente-servidor", "Arquitectura de microservicios", "La arquitectura de microservicios se enfoca en la creación de sistemas altamente escalables y tolerantes a fallos, lo que permite una mayor confiabilidad y capacidad de respuesta del sistema."},

                        {"¿Cuál de los siguientes es un patrón de diseño que se utiliza para limitar la creación de instancias de una clase a un solo objeto?",
                                "Singleton", "Factory Method", "Observer","Singleton",
                                "El patrón Singleton se utiliza para asegurarse de que solo se pueda crear una instancia de una clase en toda la aplicación."},

                        {"¿Qué patrón de diseño se utiliza para separar la presentación de la lógica de negocio en una aplicación?",
                                "MVC", "Adapter", "Decorator","MVC",
                                "El patrón MVC (Modelo-Vista-Controlador) se utiliza para separar la lógica de negocio de una aplicación de su presentación."},

                        {"¿Cuál de los siguientes patrones de diseño se utiliza para permitir que un objeto cambie su comportamiento cuando su estado interno cambia?",
                                "State", "Strategy", "Template Method","State",
                                "El patrón State se utiliza para permitir que un objeto cambie su comportamiento cuando su estado interno cambia."},

                        {"¿Qué patrón de diseño se utiliza para separar el proceso de creación de un objeto complejo de su representación?",
                                "Builder", "Prototype", "Abstract Factory","Builder",
                                "El patrón Builder se utiliza para separar el proceso de creación de un objeto complejo de su representación, permitiendo diferentes representaciones para el mismo objeto."},

                        {"¿Cuál de los siguientes patrones de diseño se utiliza para permitir que un objeto actúe como un proxy o representante de otro objeto?",
                                "Proxy", "Adapter", "Facade","Proxy",
                                "El patrón Proxy se utiliza para permitir que un objeto actúe como un proxy o representante de otro objeto, controlando el acceso a éste y añadiendo funcionalidad adicional si es necesario."}

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
        for (int i = 0; i < 10; i++) {
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

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity2.this);
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

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity2.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity2.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity2.this);
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
        Intent intent = new Intent(PreguntasActivity2.this, FinalActivity.class);
        intent.putExtra("puntuacion", puntuacionActual);
        startActivity(intent);
    }

    public void salir (View view){
        countDownTimer.cancel();
        finish();
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
}
