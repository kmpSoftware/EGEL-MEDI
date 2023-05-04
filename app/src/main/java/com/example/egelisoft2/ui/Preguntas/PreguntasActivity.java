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
                                "Un requerimiento funcional define una función o característica específica que el sistema debe cumplir, mientras que un requerimiento no funcional se refiere a una restricción o limitación del sistema.", "Un requerimiento funcional se refiere a una restricción o limitación del sistema, mientras que un requerimiento no funcional define una función o característica específica que el sistema debe cumplir.", "No hay diferencia entre un requerimiento funcional y un requerimiento no funcional.", "Un requerimiento funcional define una función o característica específica que el sistema debe cumplir, mientras que un requerimiento no funcional se refiere a una restricción o limitación del sistema.", "Los requerimientos funcionales describen qué debe hacer el sistema, es decir, las funciones y tareas que el sistema debe ser capaz de realizar para cumplir con los objetivos del usuario. Por ejemplo, la funcionalidad de un sistema de compras en línea podría incluir la capacidad de agregar artículos al carrito de compras, seleccionar opciones de envío y realizar pagos con tarjeta de crédito.\n" +
                                "\n" +
                                "Por otro lado, los requerimientos no funcionales describen cómo debe hacerse algo, es decir, las características y restricciones que el sistema debe cumplir para satisfacer las necesidades del usuario en términos de calidad, seguridad, rendimiento, usabilidad, compatibilidad, entre otros aspectos."},

                        {"¿Cuál de los siguientes requisitos es un requisito funcional?",
                                "El software debe ser fácil de instalar en cualquier sistema operativo.", "El software debe tener una tasa de error menor al 1%.", "El software debe permitir a los usuarios imprimir los resultados.","El software debe permitir a los usuarios imprimir los resultados.",
                                "Un requisito funcional se refiere a una función específica que debe realizar el software, como procesar datos, enviar notificaciones, mostrar información, etc. En contraste, los requisitos no funcionales se refieren a características del software que no están directamente relacionadas con su funcionalidad, como la seguridad, el rendimiento, la usabilidad, la compatibilidad, etc. Las opciones a y b se refieren a requisitos no funcionales, ya que se refieren a la calidad del software, pero no describen una función específica que deba realizar el software."},

                        {" ¿Qué tipo de requerimiento es \"La aplicación debe permitir a los usuarios iniciar sesión con una dirección de correo electrónico y una contraseña\"?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de usuario","Requerimientos funcionales",
                                "Requerimiento funcional, ya que especifica una funcionalidad que el sistema debe tener."},

                        {"¿Cuál de los siguientes es un ejemplo de un requerimiento de dominio en un software de contabilidad financiera??",
                                "El software debe ser capaz de importar y exportar datos en formato CSV.", "El software debe permitir la edición de transacciones en una pantalla separada.", "El software debe cumplir con las regulaciones contables locales y tener un sistema de seguimiento de auditoría","El software debe cumplir con las regulaciones contables locales y tener un sistema de seguimiento de auditoría",
                                "los requerimientos de dominio se refieren a las características y restricciones que se derivan del conocimiento del dominio de aplicación del software, es decir, de la industria, negocio, área de conocimiento o tecnología específica en la que se va a utilizar el software.\n" +
                                        "\n" +
                                        "Los requerimientos de dominio son importantes porque permiten asegurar que el software cumpla con las necesidades y expectativas de los usuarios y se adapte a las particularidades y restricciones del contexto en el que se va a utilizar."},

                        {"¿Qué tipo de requerimiento se refiere a la capacidad del sistema para adaptarse a diferentes tamaños de pantalla y dispositivos?",
                                "Requerimientos funcionales", "Requerimientos no funcionales", "Requerimientos de usuario","Requerimientos no funcionales", "La capacidad del sistema para adaptarse a diferentes tamaños de pantalla y dispositivos es un requerimiento de portabilidad, que es un subtipo de Requerimientos no funcionales."},

                        {"¿Cuál de los siguientes requisitos no es un requisito funcional?",
                                "El software debe permitir a los usuarios editar sus perfiles.", "El software debe ser capaz de manejar 1000 usuarios concurrentes.", "El software debe tener una interfaz de usuario atractiva.","El software debe tener una interfaz de usuario atractiva.", "El software debe tener una interfaz de usuario atractiva. Este requisito se refiere a un aspecto visual del software, no a una funcionalidad específica."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para cumplir con los estándares de la industria?",
                                "Requerimientos de calidad", "Requerimientos regulatorios", "Requerimientos de interoperabilidad","Requerimientos regulatorios",
                                "Los Requerimientos regulatorios se enfocan en la capacidad del software para cumplir con las regulaciones, leyes y estándares de la industria en la que se desarrolla."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para recuperar los datos en caso de una falla del sistema?",
                                "Requerimientos de confiabilidad", "Requerimientos de mantenimiento", "Requerimientos de seguridad","Requerimientos de confiabilidad",
                                "Los Requerimientos de confiabilidad se enfocan en la capacidad del software para funcionar sin fallas y para recuperarse de las fallas que puedan ocurrir."},

                        {"¿Qué son los Requerimientos de calidad?", "Los Requerimientos que definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento.",
                                "Los Requerimientos que describen las características y funcionalidades que el software debe proporcionar.", "Los Requerimientos que especifican los límites de los recursos del hardware y del sistema operativo.","Los Requerimientos que definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento.",
                                "Los Requerimientos de calidad definen la calidad del software en términos de fiabilidad, mantenibilidad, usabilidad y rendimiento. Estos Requerimientos son importantes para garantizar que el software sea funcional, fácil de usar y de alta calidad."},

                        {"¿Cuál es el propósito de los Requerimientos de usuario?","Describir las necesidades del usuario en términos de funciones y características del software.",
                                "Proporcionar información detallada sobre la arquitectura del software.", "Describir las limitaciones del hardware y el sistema operativo.","Describir las necesidades del usuario en términos de funciones y características del software.",
                                "Los Requerimientos de usuario describen las necesidades del usuario en términos de funciones y características del software. Estos Requerimientos son importantes porque ayudan a los desarrolladores a comprender las necesidades del usuario y a diseñar un software que satisfaga esas necesidades."},
                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para interactuar con otros sistemas?",
                                "Requerimientos de interoperabilidad", "Requerimientos de rendimiento", "Requerimientos de usabilidad", "Requerimientos de interoperabilidad",
                                "Los requerimientos de interoperabilidad se enfocan en la capacidad del software para interactuar con otros sistemas y trabajar en conjunto con ellos."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para manejar grandes cantidades de datos?",
                                "Requerimientos de rendimiento", "Requerimientos de seguridad", "Requerimientos de mantenimiento", "Requerimientos de rendimiento",
                                "Los requerimientos de rendimiento se enfocan en la capacidad del software para manejar grandes cantidades de datos y realizar tareas complejas de manera eficiente."},

                        {"¿Qué tipo de requerimiento se enfoca en la apariencia visual y la facilidad de uso del software?",
                                "Requerimientos de usabilidad", "Requerimientos de interoperabilidad", "Requerimientos de calidad", "Requerimientos de usabilidad",
                                "Los requerimientos de usabilidad se enfocan en la apariencia visual y la facilidad de uso del software para mejorar la experiencia del usuario."},

                        {"¿Qué tipo de requerimiento se enfoca en la seguridad y protección de los datos del usuario?",
                                "Requerimientos de seguridad", "Requerimientos de calidad", "Requerimientos de confiabilidad", "Requerimientos de seguridad",
                                "Los requerimientos de seguridad se enfocan en la protección y privacidad de los datos del usuario y la prevención de vulnerabilidades y ataques."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para adaptarse a diferentes plataformas y sistemas operativos?",
                                "Requerimientos de portabilidad", "Requerimientos de interoperabilidad", "Requerimientos de mantenimiento", "Requerimientos de portabilidad",
                                "Los requerimientos de portabilidad se enfocan en la capacidad del software para adaptarse a diferentes plataformas y sistemas operativos sin perder su funcionalidad."},
                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para realizar operaciones dentro de ciertos tiempos límite?",
                                "Requerimientos de tiempo de respuesta", "Requerimientos de rendimiento", "Requerimientos de seguridad",
                                "Requerimientos de tiempo de respuesta",
                                "Los requerimientos de tiempo de respuesta establecen el tiempo máximo permitido para que el software responda a una entrada del usuario."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para manejar grandes cantidades de datos?",
                                "Requerimientos de escalabilidad", "Requerimientos de portabilidad", "Requerimientos de mantenibilidad",
                                "Requerimientos de escalabilidad",
                                "Los requerimientos de escalabilidad se refieren a la capacidad del software para manejar grandes volúmenes de datos o un alto número de usuarios simultáneamente."},

                        {"¿Qué tipo de requerimiento se enfoca en la facilidad de uso del software para el usuario?",
                                "Requerimientos de usabilidad", "Requerimientos de interoperabilidad", "Requerimientos de escalabilidad",
                                "Requerimientos de usabilidad",
                                "Los requerimientos de usabilidad se enfocan en la facilidad de uso y la accesibilidad del software para el usuario final."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para funcionar en diferentes entornos de hardware o software?",
                                "Requerimientos de portabilidad", "Requerimientos de confiabilidad", "Requerimientos de interoperabilidad",
                                "Requerimientos de portabilidad",
                                "Los requerimientos de portabilidad se refieren a la capacidad del software para funcionar en diferentes plataformas de hardware o software."},

                        {"¿Qué tipo de requerimiento se enfoca en la capacidad del software para resistir ataques malintencionados?",
                                "Requerimientos de seguridad", "Requerimientos de usabilidad", "Requerimientos de escalabilidad",
                                "Requerimientos de seguridad",
                                "Los requerimientos de seguridad se enfocan en la capacidad del software para resistir ataques malintencionados y proteger la información y los recursos del sistema."}
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"¿Qué técnica de obtención de requerimientos se enfoca en la observación directa de los usuarios en su ambiente de trabajo?", "Estudio de campo", "Entrevistas", "Cuestionarios", "Estudio de campo", "El estudio de campo implica la observación directa de los usuarios en su ambiente de trabajo para comprender cómo trabajan y cuáles son sus necesidades."},

                        {"¿Qué técnica de análisis de requerimientos se enfoca en la creación de diagramas de flujo de datos?", "Análisis estructurado", "Análisis de casos de uso", "Análisis de riesgos", "Análisis estructurado", "El análisis estructurado se enfoca en la creación de diagramas de flujo de datos para entender cómo los datos fluyen a través del sistema."},

                        {"¿Cuál de las siguientes herramientas se utiliza para la priorización de requerimientos?", "Matriz de decisión", "Diagrama de Gantt", "Diagrama de Pareto", "Matriz de decisión", "La matriz de decisión se utiliza para la priorización de requerimientos al evaluar los criterios de decisión y asignar un puntaje a cada opción."},

                        {"¿Qué técnica de validación de requerimientos se enfoca en la revisión detallada de los requisitos por parte de un equipo de expertos?", "Revisión por pares", "Pruebas unitarias", "Pruebas de aceptación", "Revisión por pares", "La revisión por pares es una técnica de validación de requerimientos en la que un equipo de expertos revisa detalladamente los requisitos para detectar posibles problemas o errores."},
                        {"¿Qué técnica de obtención de requerimientos se enfoca en la recopilación de información de los usuarios mediante la observación y el análisis de sus actividades diarias?", "Etnografía", "Prototipado rápido", "Taller de requerimientos", "Etnografía", "La etnografía se enfoca en la recopilación de información de los usuarios mediante la observación y el análisis de sus actividades diarias para identificar sus necesidades y comportamientos."},
                        {"¿Cuál es el objetivo principal de la técnica de entrevistas en la obtención de requerimientos?", "Entender las necesidades y expectativas del usuario", "Generar prototipos rápidos del software", "Evaluar el desempeño del software", "Entender las necesidades y expectativas del usuario", "Las entrevistas son una técnica útil para comprender las necesidades y expectativas del usuario y generar una lista de requerimientos."},

                        {"¿Qué es la técnica de benchmarking en la validación de requerimientos?", "Comparar los requerimientos con los de otros productos similares", "Evaluar el desempeño del software en situaciones límite", "Verificar la consistencia de los requerimientos", "Comparar los requerimientos con los de otros productos similares", "El benchmarking es una técnica que implica comparar los requerimientos de un producto con los de otros productos similares en el mercado."},
                        {"¿Qué herramienta se utiliza para priorizar los requerimientos según su importancia para el negocio?", "Análisis costo-beneficio", "Diagrama de flujo", "Modelado de procesos de negocio", "Análisis costo-beneficio", "El análisis costo-beneficio es una herramienta útil para priorizar los requerimientos en función de su importancia para el negocio."},

                        {"¿Qué es un caso de uso en el análisis de requerimientos?", "Un escenario de interacción entre el usuario y el sistema", "Una lista de requerimientos funcionales", "Una descripción detallada del diseño del sistema", "Un escenario de interacción entre el usuario y el sistema", "Un caso de uso es una descripción detallada de una interacción entre el usuario y el sistema que ayuda a comprender los requerimientos funcionales y no funcionales."},

                        {"¿Qué técnica se utiliza para verificar la consistencia de los requerimientos?", "Análisis de trazabilidad", "Pruebas de aceptación", "Modelado de procesos de negocio", "Análisis de trazabilidad", "El análisis de trazabilidad es una técnica útil para verificar la consistencia de los requerimientos a lo largo del ciclo de vida del software."},
                        {"¿Qué técnica de obtención de requerimientos se enfoca en la interacción con usuarios y stakeholders?", "Entrevistas", "Cuestionarios", "Encuestas", "Entrevistas", "Las entrevistas son una técnica de obtención de requerimientos que permite interactuar con los usuarios y stakeholders para entender sus necesidades y expectativas."},

                        {"¿Cuál es el objetivo principal del análisis de requerimientos?", "Identificar las necesidades y expectativas de los usuarios", "Definir la arquitectura del software", "Escribir el código del software", "Identificar las necesidades y expectativas de los usuarios", "El análisis de requerimientos tiene como objetivo principal identificar las necesidades y expectativas de los usuarios para poder desarrollar un software que satisfaga sus requerimientos."},

                        {"¿Qué técnica de priorización de requerimientos se basa en la evaluación de la importancia y la dificultad de cada requerimiento?", "Matriz de valoración de requerimientos", "Análisis de costo-beneficio", "Análisis de riesgos", "Matriz de valoración de requerimientos", "La matriz de valoración de requerimientos es una técnica de priorización que permite evaluar la importancia y la dificultad de cada requerimiento para establecer su prioridad en el proceso de desarrollo."},

                        {"¿Qué herramienta de validación de requerimientos permite simular el comportamiento del software antes de su implementación?", "Prototipos", "Casos de prueba", "Modelos de negocio", "Prototipos", "Los prototipos son herramientas de validación de requerimientos que permiten simular el comportamiento del software antes de su implementación para validar su funcionalidad y usabilidad."},

                        {"¿Cuál es el objetivo principal de la validación de requerimientos?", "Verificar que los requerimientos sean correctos y completos", "Evaluar la calidad del software", "Medir el rendimiento del software", "Verificar que los requerimientos sean correctos y completos", "La validación de requerimientos tiene como objetivo principal verificar que los requerimientos sean correctos y completos para asegurar que el software desarrollado cumpla con las necesidades y expectativas de los usuarios."},
                        {"¿Cuál es la técnica de obtención de requerimientos que se basa en la observación directa de cómo los usuarios realizan su trabajo?", "Observación", "Entrevista", "Cuestionario", "Observación", "La técnica de observación se basa en ver directamente cómo los usuarios realizan sus tareas y puede proporcionar información valiosa sobre el contexto en el que se utilizará el software."},

                        {"¿Qué técnica de priorización de requerimientos se basa en la estimación de la importancia de un requerimiento en función de su impacto en los objetivos del negocio?", "Análisis de valor", "Análisis de riesgos", "Análisis de costo-beneficio", "Análisis de valor", "El análisis de valor se basa en la estimación de la importancia de un requerimiento en función de su impacto en los objetivos del negocio y se utiliza para priorizar requerimientos en función de su valor para el negocio."},

                        {"¿Qué técnica de validación de requerimientos implica la creación de un modelo del sistema para probar la consistencia y la completitud de los requerimientos?", "Modelado de sistemas", "Pruebas de aceptación", "Análisis de trazabilidad", "Modelado de sistemas", "El modelado de sistemas implica la creación de un modelo del sistema para probar la consistencia y la completitud de los requerimientos, lo que ayuda a validar los requerimientos y a detectar posibles problemas temprano en el proceso de desarrollo."},

                        {"¿Qué técnica de análisis de requerimientos se enfoca en identificar los escenarios de uso del sistema?", "Casos de uso", "Diagramas de flujo", "Diagramas de clases", "Casos de uso", "Los casos de uso se enfocan en identificar los escenarios de uso del sistema, lo que ayuda a comprender cómo los usuarios interactúan con el sistema y a identificar los requerimientos funcionales y no funcionales."},

                        {"¿Qué herramienta de análisis de requerimientos se utiliza para visualizar la relación entre los requerimientos y las diferentes fases del ciclo de vida del software?", "Matriz de trazabilidad", "Diagrama de Gantt", "Mapa mental", "Matriz de trazabilidad", "La matriz de trazabilidad se utiliza para visualizar la relación entre los requerimientos y las diferentes fases del ciclo de vida del software, lo que ayuda a garantizar que todos los requerimientos estén cubiertos y a detectar posibles problemas de trazabilidad."}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"¿Qué técnica de documentación de requerimientos se enfoca en crear una representación visual de los requisitos y su relación?", "Diagramas de casos de uso", "Entrevistas", "Prototipos", "Diagramas de casos de uso", "Los diagramas de casos de uso son una técnica de documentación que permite crear una representación visual de los requisitos y su relación, lo que facilita su comprensión y análisis."},

                        {"¿Qué herramienta de documentación de requerimientos permite especificar los requisitos de un software utilizando lenguaje natural estructurado?", "Lenguaje de Modelado Unificado (UML)", "Requisitos en lenguaje natural (NLR)", "Hojas de cálculo", "Requisitos en lenguaje natural (NLR)", "Los requisitos en lenguaje natural son una herramienta de documentación de requerimientos que permiten especificar los requisitos de un software utilizando lenguaje natural estructurado, lo que facilita su comprensión y análisis."},

                        {"¿Qué técnica de documentación de requerimientos se enfoca en identificar las entradas, salidas y procesos de un sistema?", "Diagramas de flujo de datos", "Casos de uso", "Prototipos", "Diagramas de flujo de datos", "Los diagramas de flujo de datos son una técnica de documentación que permite identificar las entradas, salidas y procesos de un sistema, lo que facilita su comprensión y análisis."},

                        {"¿Qué herramienta de documentación de requerimientos permite crear un modelo visual de los requisitos utilizando iconos y símbolos estandarizados?", "Lenguaje de Modelado Unificado (UML)", "Lenguaje de descripción de interfaces de usuario (UIDL)", "Hojas de cálculo", "Lenguaje de Modelado Unificado (UML)", "El Lenguaje de Modelado Unificado (UML) es una herramienta de documentación de requerimientos que permite crear un modelo visual de los requisitos utilizando iconos y símbolos estandarizados, lo que facilita su comprensión y análisis."},

                        {"¿Qué técnica de documentación de requerimientos se enfoca en identificar y describir los requisitos funcionales y no funcionales de un sistema?", "Especificación de requisitos", "Diagramas de flujo de datos", "Casos de uso", "Especificación de requisitos", "La especificación de requisitos es una técnica de documentación que se enfoca en identificar y describir los requisitos funcionales y no funcionales de un sistema, lo que facilita su comprensión y análisis."},

                        {"¿Cuál es el propósito de la técnica de casos de uso en la documentación de requerimientos?", "Para representar cómo los usuarios interactúan con el sistema", "Para identificar los requerimientos no funcionales", "Para definir el alcance del proyecto", "Para representar cómo los usuarios interactúan con el sistema", "La técnica de casos de uso se utiliza para representar cómo los usuarios interactúan con el sistema y para definir los objetivos y necesidades del usuario."},

                        {"¿Cuál de las siguientes herramientas es útil para la documentación de requerimientos en un entorno colaborativo?", "Diagramas de flujo", "Tablas de especificación", "Tableros Kanban", "Tableros Kanban", "Los tableros Kanban son herramientas visuales que permiten a los equipos de desarrollo de software colaborar y coordinar las tareas de manera eficiente."},

                        {"¿Qué es una matriz de trazabilidad de requerimientos?", "Una herramienta que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto", "Una herramienta para identificar los requerimientos no funcionales", "Una herramienta para definir el alcance del proyecto", "Una herramienta que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto", "La matriz de trazabilidad de requerimientos es una herramienta útil que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto, como los casos de prueba, el diseño y la implementación."},

                        {"¿Cuál es la principal diferencia entre los documentos de requerimientos funcionales y no funcionales?", "Los requerimientos funcionales se centran en lo que debe hacer el sistema, mientras que los no funcionales se centran en cómo debe hacerlo", "Los requerimientos funcionales se centran en cómo debe hacerlo el sistema, mientras que los no funcionales se centran en lo que debe hacer", "No hay diferencia", "Los requerimientos funcionales se centran en lo que debe hacer el sistema, mientras que los no funcionales se centran en cómo debe hacerlo, como la eficiencia, la usabilidad y la seguridad."},

                        {"¿Qué es un prototipo de software?", "Una representación temprana y simplificada de un sistema que ayuda a validar los requerimientos", "Una lista de requerimientos de alto nivel", "Un documento detallado que describe los requerimientos", "Una representación temprana y simplificada de un sistema que ayuda a validar los requerimientos", "Un prototipo de software es una versión temprana y simplificada de un sistema que se utiliza para validar los requerimientos y recibir retroalimentación temprana de los usuarios."},

                        {"¿Qué técnica de documentación de requerimientos es útil para mostrar la estructura jerárquica de un conjunto de requerimientos?", "Diagrama de estructura de requerimientos (DSR)", "Matriz de trazabilidad", "Diagrama de flujo de datos (DFD)", "Diagrama de estructura de requerimientos (DSR)", "El DSR es una técnica que muestra la estructura jerárquica de un conjunto de requerimientos, proporcionando una visión general del sistema."},

                        {"¿Qué herramienta de documentación de requerimientos se utiliza para capturar, rastrear y gestionar los cambios en los requerimientos?", "Herramientas de gestión de requerimientos", "Hojas de cálculo", "Diagramas de flujo", "Herramientas de gestión de requerimientos", "Estas herramientas permiten capturar, rastrear y gestionar los cambios en los requerimientos, y garantizan la trazabilidad de los mismos."},

                        {"¿Qué técnica de documentación de requerimientos se utiliza para describir las acciones que el usuario puede realizar en el sistema?", "Casos de uso", "Diagrama de flujo de datos (DFD)", "Modelado de procesos de negocio (BPM)", "Casos de uso", "Los casos de uso se utilizan para describir las acciones que el usuario puede realizar en el sistema y cómo el sistema responde a ellas."},

                        {"¿Qué herramienta de documentación de requerimientos se utiliza para definir y clasificar los requerimientos de manera estructurada?", "Esquemas de clasificación de requerimientos", "Herramientas de gestión de configuración", "Matrices de trazabilidad", "Esquemas de clasificación de requerimientos", "Los esquemas de clasificación de requerimientos se utilizan para definir y clasificar los requerimientos de manera estructurada, lo que facilita su gestión y seguimiento."},

                        {"¿Qué técnica de documentación de requerimientos se utiliza para representar los requerimientos en términos de entradas, procesos y salidas?", "Diagrama de flujo de datos (DFD)", "Casos de uso", "Matriz de trazabilidad", "Diagrama de flujo de datos (DFD)", "Los DFD se utilizan para representar los requerimientos en términos de entradas, procesos y salidas, lo que ayuda a visualizar el sistema desde una perspectiva funcional."},
                        {"¿Qué técnica se utiliza para representar gráficamente los flujos de trabajo y la interacción entre los usuarios y el sistema?", "Diagrama de flujo", "Diagrama de casos de uso", "Diagrama de secuencia", "Diagrama de casos de uso", "El diagrama de casos de uso se utiliza para representar los flujos de trabajo y la interacción entre los usuarios y el sistema, mostrando los actores involucrados y las funcionalidades del sistema."},
                        {"¿Qué herramienta se utiliza para crear modelos de requerimientos y documentación de especificaciones de software?", "Microsoft Word", "Microsoft Excel", "Microsoft Visio", "Microsoft Visio", "Microsoft Visio es una herramienta utilizada para crear modelos de requerimientos y documentación de especificaciones de software, permitiendo crear diagramas de flujo, diagramas de clases, entre otros."},

                        {"¿Qué técnica se utiliza para identificar los requerimientos mediante la observación de los usuarios y su entorno?", "Entrevistas", "Encuestas", "Observación", "Observación", "La observación es una técnica utilizada para identificar los requerimientos mediante la observación directa de los usuarios y su entorno, permitiendo identificar sus necesidades y comportamientos."},

                        {"¿Qué técnica se utiliza para identificar los requerimientos mediante la discusión y el debate entre los miembros del equipo de desarrollo y los stakeholders?", "Entrevistas", "Reuniones de grupo", "Entrevistas a grupos", "Reuniones de grupo", "Las reuniones de grupo son una técnica utilizada para identificar los requerimientos mediante la discusión y el debate entre los miembros del equipo de desarrollo y los stakeholders, permitiendo llegar a un consenso sobre los requerimientos."},

                        {"¿Qué herramienta se utiliza para gestionar y trazar los requerimientos durante todo el ciclo de vida del software?", "Microsoft Excel", "Microsoft Word", "JIRA", "JIRA", "JIRA es una herramienta utilizada para gestionar y trazar los requerimientos durante todo el ciclo de vida del software, permitiendo asignar tareas, hacer seguimiento y priorizar los requerimientos."}
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





