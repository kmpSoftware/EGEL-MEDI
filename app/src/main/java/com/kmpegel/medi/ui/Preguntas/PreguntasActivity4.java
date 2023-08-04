package com.kmpegel.medi.ui.Preguntas;

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
import com.kmpegel.egelmedi.R;
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
                        {"¿Qué es el método PERT en la gestión de proyectos de software?",
                                "Un método para estimar la duración de las tareas del proyecto.",
                                "Una técnica para asignar recursos a las actividades del proyecto.",
                                "Un enfoque para identificar y gestionar los riesgos del proyecto.",
                                "Un método para estimar la duración de las tareas del proyecto.",
                                "El método PERT (Program Evaluation and Review Technique) es una técnica utilizada para estimar la duración de las tareas del proyecto considerando la incertidumbre asociada."},

                        {"¿Cuál de los siguientes es un ejemplo de una técnica de estimación de costos en el desarrollo de software?",
                                "Análisis FODA",
                                "Análisis de Puntos de Función",
                                "Diagrama de Gantt",
                                "Análisis de Puntos de Función",
                                "El Análisis de Puntos de Función es una técnica utilizada para estimar los costos del desarrollo de software basándose en la funcionalidad proporcionada por el sistema."},

                        {"¿Qué es el análisis de sensibilidad en la gestión de riesgos de proyectos de software?",
                                "Un método para identificar y clasificar los riesgos del proyecto.",
                                "Una técnica para evaluar el impacto de los riesgos en los objetivos del proyecto.",
                                "Un enfoque para determinar la probabilidad de ocurrencia de los riesgos del proyecto.",
                                "Una técnica para evaluar el impacto de los riesgos en los objetivos del proyecto.",
                                "El análisis de sensibilidad es una técnica utilizada en la gestión de riesgos para evaluar el impacto de los riesgos en los objetivos del proyecto y determinar su importancia relativa."},

                        {"¿Cuál de los siguientes es un ejemplo de una herramienta utilizada en la gestión de tiempos de proyectos de software?",
                                "Diagrama de Ishikawa",
                                "Diagrama de Pareto",
                                "Diagrama de Gantt",
                                "Diagrama de Gantt",
                                "El Diagrama de Gantt es una herramienta utilizada en la gestión de tiempos de proyectos de software para planificar y visualizar las tareas, su duración y las dependencias entre ellas."},

                        {"¿Cuál de las siguientes técnicas se utiliza para asignar recursos humanos a las actividades del proyecto de software?",
                                "Diagrama de flujo",
                                "Diagrama de red",
                                "Matriz de asignación de recursos",
                                "Matriz de asignación de recursos",
                                "La matriz de asignación de recursos es una técnica utilizada para asignar recursos humanos específicos a las actividades del proyecto de software, teniendo en cuenta las habilidades y disponibilidad del personal."},

                        {"¿Qué es el valor ganado (EV) en la gestión de costos de proyectos de software?",
                                "La cantidad real de dinero gastado en el proyecto hasta el momento.",
                                "El valor monetario de las actividades completadas hasta el momento.",
                                "El valor estimado de las actividades completadas hasta el momento.",
                                "El valor estimado de las actividades completadas hasta el momento.",
                                "El valor ganado (EV) es el valor estimado de las actividades completadas hasta el momento en relación con el presupuesto planificado para esas actividades."},

                        {"¿Cuál de las siguientes estrategias se utiliza para mitigar los riesgos en proyectos de desarrollo de software?",
                                "Aceptar los riesgos y no tomar medidas adicionales.",
                                "Transferir los riesgos a terceros mediante seguros o contratos.",
                                "Ignorar los riesgos y enfocarse en los objetivos del proyecto.",
                                "Transferir los riesgos a terceros mediante seguros o contratos.",
                                "La estrategia de transferencia de riesgos implica transferir la responsabilidad de los riesgos a terceros mediante seguros, contratos u otros acuerdos."},

                        {"¿Cuál de las siguientes técnicas se utiliza para estimar la duración de las tareas del proyecto de software?",
                                "Análisis de Puntos de Función",
                                "Diagrama de Gantt",
                                "Técnica de tres puntos",
                                "Técnica de tres puntos",
                                "La técnica de tres puntos, también conocida como estimación de tres puntos o estimación PERT, se utiliza para estimar la duración de las tareas del proyecto de software considerando una estimación optimista, una estimación pesimista y una estimación más probable."},

                        {"¿Cuál de las siguientes métricas se utiliza para evaluar el desempeño de los recursos humanos en un proyecto de software?",
                                "Productividad",
                                "Eficiencia",
                                "Competencia",
                                "Productividad",
                                "La productividad es una métrica utilizada para evaluar el desempeño de los recursos humanos en términos de la cantidad de trabajo realizado en relación con los recursos utilizados."},

                        {"¿Cuál de los siguientes es un ejemplo de un riesgo técnico en proyectos de desarrollo de software?",
                                "Cambios en los requisitos del cliente",
                                "Falta de experiencia del equipo de desarrollo",
                                "Problemas de comunicación con los interesados",
                                "Falta de experiencia del equipo de desarrollo",
                                "La falta de experiencia del equipo de desarrollo es un ejemplo de un riesgo técnico que puede afectar negativamente la calidad y el rendimiento del software."},

                        {"¿Cuál de los siguientes es un ejemplo de una técnica utilizada para la estimación de costos en la gestión de proyectos de software?",
                                "Análisis de impacto",
                                "Método Delphi",
                                "Diagrama de flujo",
                                "Método Delphi",
                                "El Método Delphi es una técnica utilizada para la estimación de costos en la gestión de proyectos de software, donde se recopilan las opiniones de expertos de manera anónima y se realiza un análisis y consenso para obtener una estimación precisa."},

                        {"¿Qué es el análisis de riesgos en la gestión de proyectos de software?",
                                "Un proceso para eliminar por completo los riesgos del proyecto.",
                                "Una técnica para identificar y evaluar los riesgos potenciales del proyecto.",
                                "Un enfoque para minimizar el impacto de los riesgos en el presupuesto del proyecto.",
                                "Una técnica para identificar y evaluar los riesgos potenciales del proyecto.",
                                "El análisis de riesgos es una técnica utilizada en la gestión de proyectos de software para identificar y evaluar los riesgos potenciales que podrían afectar el éxito del proyecto."},

                        {"¿Cuál de las siguientes actividades forma parte de la gestión de recursos humanos en un proyecto de desarrollo de software?",
                                "Planificación de la infraestructura de hardware",
                                "Selección y contratación de personal",
                                "Evaluación de la calidad del código fuente",
                                "Selección y contratación de personal",
                                "La selección y contratación de personal es una actividad clave en la gestión de recursos humanos, ya que se deben identificar y contratar a los profesionales adecuados con las habilidades necesarias para el proyecto de desarrollo de software."},

                        {"¿Cuál de los siguientes es un ejemplo de un riesgo externo en proyectos de desarrollo de software?",
                                "Cambios en los requisitos del cliente",
                                "Falta de experiencia del equipo de desarrollo",
                                "Inestabilidad económica del país",
                                "Inestabilidad económica del país",
                                "La inestabilidad económica del país es un ejemplo de un riesgo externo que puede tener un impacto significativo en el desarrollo de software, afectando los presupuestos, la disponibilidad de recursos y la viabilidad del proyecto."},

                        {"¿Qué es la gestión de cambios en un proyecto de desarrollo de software?",
                                "Un proceso para evitar cualquier cambio durante el proyecto",
                                "Un enfoque para gestionar y controlar los cambios en los requisitos y el alcance del proyecto",
                                "Un método para asignar recursos a las actividades del proyecto",
                                "Un enfoque para gestionar y controlar los cambios en los requisitos y el alcance del proyecto",
                                "La gestión de cambios es un enfoque utilizado para gestionar y controlar los cambios que ocurren en los requisitos y el alcance del proyecto de desarrollo de software, asegurando que los cambios sean evaluados, aprobados y gestionados adecuadamente."},

                        {"¿Cuál de los siguientes es un ejemplo de una métrica utilizada para medir el rendimiento en la gestión de proyectos de software?",
                                "Tiempo de respuesta",
                                "Tamaño del código fuente",
                                "Calidad del diseño",
                                "Tiempo de respuesta",
                                "El tiempo de respuesta es una métrica utilizada para medir el rendimiento en la gestión de proyectos de software, especialmente en sistemas que requieren respuestas rápidas y eficientes."},

                        {"¿Cuál de los siguientes es un ejemplo de una técnica utilizada para la estimación de tiempos en la gestión de proyectos de software?",
                                "Análisis FODA",
                                "Diagrama de Gantt",
                                "Método de los puntos de función",
                                "Método de los puntos de función",
                                "El método de los puntos de función es una técnica utilizada para la estimación de tiempos en la gestión de proyectos de software, donde se evalúa la funcionalidad del software para determinar su tamaño y, posteriormente, estimar el tiempo requerido para su desarrollo."},
                        {"¿Cuál de los siguientes es un ejemplo de un recurso humano en un proyecto de desarrollo de software?",
                                "Servidores de base de datos",
                                "Licencias de software",
                                "Desarrolladores de software",
                                "Desarrolladores de software",
                                "Los desarrolladores de software son ejemplos de recursos humanos en un proyecto de desarrollo de software, ya que son responsables de diseñar, programar y probar el software."},

                        {"¿Cuál de las siguientes técnicas se utiliza para priorizar las actividades en la gestión de proyectos de software?",
                                "Diagrama de Pareto",
                                "Técnica de la cadena crítica",
                                "Método Delphi",
                                "Técnica de la cadena crítica",
                                "La técnica de la cadena crítica se utiliza para priorizar las actividades en la gestión de proyectos de software, identificando las dependencias y restricciones que afectan el orden de ejecución de las actividades."},

                        {"¿Qué es la reserva de contingencia en la gestión de proyectos de software?",
                                "Un espacio físico donde se guardan los documentos del proyecto",
                                "Un presupuesto adicional para cubrir riesgos no identificados",
                                "Una técnica para estimar la duración de las actividades del proyecto",
                                "Un presupuesto adicional para cubrir riesgos no identificados",
                                "La reserva de contingencia es un presupuesto adicional reservado para cubrir los riesgos no identificados o imprevistos en la gestión de proyectos de software, brindando flexibilidad financiera para abordar problemas que puedan surgir durante el proyecto."}

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
                        {"¿Qué es el principio de división del poder en el contexto de la calidad de software?",
                                "Separar las responsabilidades y funciones para evitar conflictos de interés.",
                                "Distribuir el poder de decisión entre los miembros del equipo de desarrollo.",
                                "Establecer un sistema de control jerárquico para garantizar la calidad del software.",
                                "Separar las responsabilidades y funciones para evitar conflictos de interés.",
                                "El principio de división del poder en la calidad de software se refiere a separar las responsabilidades y funciones entre diferentes roles para evitar conflictos de interés y mejorar la calidad del producto final."},

                        {"¿Cuál de las siguientes actividades NO está relacionada con la garantía de calidad de software?",
                                "Pruebas de unidad.",
                                "Auditorías de código.",
                                "Diseño de la arquitectura del software.",
                                "Diseño de la arquitectura del software.",
                                "El diseño de la arquitectura del software no está directamente relacionado con la garantía de calidad de software, aunque una arquitectura bien diseñada puede contribuir a la calidad del producto final."},

                        {"¿Cuál de los siguientes estándares se utiliza para evaluar la madurez de los procesos de desarrollo de software?",
                                "ISO 9001",
                                "CMMI",
                                "ISO/IEC 27001",
                                "CMMI",
                                "El modelo CMMI (Capability Maturity Model Integration) se utiliza para evaluar la madurez de los procesos de desarrollo de software y mejorar continuamente la calidad de los productos y servicios."},

                        {"¿Cuál de los siguientes atributos de calidad se refiere a la capacidad del software de mantener su rendimiento bajo cargas de trabajo específicas?",
                                "Disponibilidad",
                                "Escalabilidad",
                                "Eficiencia",
                                "Escalabilidad",
                                "La escalabilidad es el atributo de calidad que se refiere a la capacidad del software de mantener su rendimiento bajo cargas de trabajo específicas, escalando adecuadamente los recursos."},

                        {"¿Qué es la inspección de software?",
                                "Un proceso formal para revisar y evaluar el software en busca de defectos.",
                                "Una técnica de pruebas que verifica el comportamiento del software en diferentes escenarios.",
                                "Un método para medir la usabilidad del software a través de pruebas de usuario.",
                                "Un proceso formal para revisar y evaluar el software en busca de defectos.",
                                "La inspección de software es un proceso formal que implica revisar y evaluar el software en busca de defectos, anomalías o incumplimientos de estándares de calidad."},

                        {"¿Cuál de los siguientes es un enfoque utilizado para mejorar la calidad del software mediante la identificación y corrección temprana de defectos?",
                                "Inspección de código",
                                "Pruebas de regresión",
                                "Análisis estático",
                                "Análisis estático",
                                "El análisis estático es un enfoque utilizado para mejorar la calidad del software mediante la identificación y corrección temprana de defectos mediante el examen del código fuente sin ejecutar el programa."},

                        {"¿Cuál de los siguientes es un ejemplo de una métrica de calidad de software?",
                                "Líneas de código escritas por día",
                                "Tiempo total de ejecución del programa",
                                "Número de casos de prueba ejecutados",
                                "Líneas de código escritas por día",
                                "El número de líneas de código escritas por día es una métrica de calidad de software que puede ayudar a evaluar la productividad y la complejidad del código."},

                        {"¿Cuál de los siguientes es un principio clave de la gestión de la calidad de software?",
                                "Mejora continua",
                                "Entrega rápida",
                                "Reducción de costos",
                                "Mejora continua",
                                "La mejora continua es un principio clave de la gestión de la calidad de software, que implica identificar áreas de mejora y aplicar acciones correctivas y preventivas de manera constante."},

                        {"¿Cuál de los siguientes estándares proporciona directrices para la gestión de la calidad del software?",
                                "ISO 27001",
                                "ISO 9001",
                                "ISO 14001",
                                "ISO 9001",
                                "El estándar ISO 9001 proporciona directrices para la gestión de la calidad del software y establece requisitos para un sistema de gestión de calidad efectivo."},

                        {"¿Cuál de los siguientes modelos se utiliza para evaluar la calidad interna del software?",
                                "Modelo de calidad ISO/IEC 9126",
                                "Modelo de calidad ISO/IEC 25010",
                                "Modelo de calidad ISO/IEC 15504",
                                "Modelo de calidad ISO/IEC 15504",
                                "El modelo de calidad ISO/IEC 15504, también conocido como SPICE (Software Process Improvement and Capability Determination), se utiliza para evaluar la calidad interna del software y la madurez de los procesos de desarrollo."}
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


                        {"¿Qué es una metodología de desarrollo de software?",
                                "Un conjunto de principios, técnicas y prácticas para planificar, diseñar y construir software de manera efectiva",
                                "Un lenguaje de programación utilizado para desarrollar aplicaciones",
                                "Una herramienta para el control de calidad en el desarrollo de software",
                                "Un conjunto de principios, técnicas y prácticas para planificar, diseñar y construir software de manera efectiva",
                                "Las metodologías de desarrollo de software son enfoques estructurados que proporcionan principios, técnicas y prácticas para el desarrollo efectivo de software."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología de desarrollo de software en cascada?",
                                "Un enfoque secuencial y lineal donde las etapas de desarrollo se completan en orden",
                                "Un enfoque iterativo que enfatiza la adaptabilidad y la colaboración",
                                "Un enfoque que combina diferentes metodologías en un proceso personalizado",
                                "Un enfoque secuencial y lineal donde las etapas de desarrollo se completan en orden",
                                "La metodología en cascada sigue una secuencia lineal de etapas, donde cada etapa se completa antes de pasar a la siguiente."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología ágil de desarrollo de software?",
                                "Un enfoque iterativo e incremental que enfatiza la colaboración y la respuesta al cambio",
                                "Un enfoque secuencial y predictivo que enfatiza la planificación detallada",
                                "Un enfoque que se centra en la documentación exhaustiva del software",
                                "Un enfoque iterativo e incremental que enfatiza la colaboración y la respuesta al cambio",
                                "La metodología ágil se basa en un enfoque iterativo e incremental, donde los requisitos y soluciones evolucionan a través de la colaboración entre equipos multidisciplinarios."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología Scrum?",
                                "Un marco de trabajo ágil que se centra en la colaboración, la comunicación y la entrega de valor en iteraciones cortas",
                                "Una técnica de estimación de costos utilizada en el desarrollo de software",
                                "Un método para medir la calidad del software",
                                "Un marco de trabajo ágil que se centra en la colaboración, la comunicación y la entrega de valor en iteraciones cortas",
                                "Scrum es una metodología ágil que enfatiza la colaboración, la comunicación y la entrega de software en incrementos cortos y frecuentes llamados sprints."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología Extreme Programming (XP)?",
                                "Un enfoque ágil que se centra en la calidad del código, la retroalimentación continua y las pruebas automatizadas",
                                "Un enfoque que utiliza modelos matemáticos para predecir el tiempo y los recursos necesarios para el desarrollo de software",
                                "Un método para el diseño arquitectónico de sistemas de software",
                                "Un enfoque ágil que se centra en la calidad del código, la retroalimentación continua y las pruebas automatizadas",
                                "Extreme Programming (XP) es una metodología ágil que se enfoca en la calidad del código, la colaboración constante con los clientes y las pruebas automatizadas."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología Kanban?",
                                "Un enfoque visual que se utiliza para gestionar y optimizar el flujo de trabajo en el desarrollo de software",
                                "Una técnica de estimación de costos utilizada en el desarrollo de software",
                                "Un método para medir la calidad del software",
                                "Un enfoque visual que se utiliza para gestionar y optimizar el flujo de trabajo en el desarrollo de software",
                                "Kanban es una metodología que utiliza tableros visuales para visualizar y gestionar el flujo de trabajo en el desarrollo de software, permitiendo una mayor transparencia y eficiencia en el proceso."},

                        {"¿Qué es la refactorización en el contexto de las metodologías de desarrollo de software?",
                                "El proceso de mejorar la estructura interna del código sin cambiar su comportamiento externo",
                                "El proceso de agregar nuevas funcionalidades a un software existente",
                                "El proceso de planificar y estimar los recursos necesarios para un proyecto de desarrollo de software",
                                "El proceso de mejorar la estructura interna del código sin cambiar su comportamiento externo",
                                "La refactorización es el proceso de mejorar la estructura interna del código sin cambiar su comportamiento externo, lo que ayuda a mantener y mejorar la calidad del software a lo largo del tiempo."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología de desarrollo en espiral?",
                                "Un enfoque iterativo y escalable que combina elementos de desarrollo en cascada y prototipado",
                                "Un enfoque secuencial y lineal donde las etapas de desarrollo se completan en orden",
                                "Un enfoque que se enfoca en la gestión de riesgos en el desarrollo de software",
                                "Un enfoque iterativo y escalable que combina elementos de desarrollo en cascada y prototipado",
                                "La metodología en espiral es un enfoque iterativo y escalable que combina elementos de desarrollo en cascada y prototipado, con un enfoque en la gestión de riesgos en el desarrollo de software."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología de desarrollo incremental?",
                                "Un enfoque que divide el desarrollo del software en pequeñas entregas incrementales",
                                "Un enfoque que enfatiza la planificación y la documentación exhaustiva antes de la implementación",
                                "Un enfoque que utiliza técnicas estadísticas para medir la calidad del software",
                                "Un enfoque que divide el desarrollo del software en pequeñas entregas incrementales",
                                "La metodología de desarrollo incremental divide el desarrollo del software en entregas incrementales y funcionales, lo que permite una rápida retroalimentación y adaptación a medida que se construye el sistema."},

                        {"¿Cuál de las siguientes opciones describe mejor la metodología de desarrollo en prototipos?",
                                "Un enfoque que utiliza prototipos rápidos para obtener retroalimentación y refinar los requisitos del sistema",
                                "Un enfoque que enfatiza la planificación y la documentación exhaustiva antes de la implementación",
                                "Un enfoque que se basa en modelos matemáticos para predecir el tiempo y los recursos necesarios para el desarrollo de software",
                                "Un enfoque que utiliza prototipos rápidos para obtener retroalimentación y refinar los requisitos del sistema",
                                "La metodología de desarrollo en prototipos utiliza prototipos rápidos para obtener retroalimentación temprana de los usuarios y refinar los requisitos del sistema antes de su implementación final."}
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
