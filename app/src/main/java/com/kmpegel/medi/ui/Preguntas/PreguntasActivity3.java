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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kmpegel.egelmedi.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PreguntasActivity3 extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "preguntasActivity3";
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
        editor.putString("ultimaActividad", "preguntasActivity3");
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
                        {"¿Cuál es el lenguaje de programación orientado a objetos más popular?", "Java", "C++", "Python", "Java", "Java es actualmente el lenguaje de programación orientado a objetos más popular y ampliamente utilizado en el mundo de la programación debido a su facilidad de uso y la gran cantidad de recursos y herramientas disponibles."},
                        {"¿Qué lenguaje de programación se utiliza comúnmente para la creación de aplicaciones móviles?", "Swift", "Java", "C++", "Java", "Java es un lenguaje de programación muy utilizado en el desarrollo de aplicaciones móviles en la plataforma Android."},
                        {"¿Cuál es el lenguaje de programación utilizado para la creación de hojas de estilo en la web?", "HTML", "CSS", "JavaScript", "CSS", "CSS (Cascading Style Sheets) es el lenguaje de programación utilizado para la creación de hojas de estilo en la web, permitiendo la separación de la presentación y el contenido de una página web."},
                        {"¿Qué lenguaje de programación es más adecuado para el análisis de datos?", "R", "Python", "Java", "R", "R es un lenguaje de programación especializado en estadística y análisis de datos, y se utiliza en una amplia variedad de aplicaciones en campos como la biología, la economía y la ingeniería."},
                        {"¿Cuál es el lenguaje de programación utilizado para el desarrollo de videojuegos?", "C++", "Java", "Python", "C++", "C++ es uno de los lenguajes de programación más populares y utilizados en el desarrollo de videojuegos, gracias a su eficiencia y capacidad para trabajar con gráficos y animaciones."},
                        {"¿Qué lenguaje de programación se utiliza comúnmente para el desarrollo de aplicaciones de escritorio?",
                                "C#", "Java", "Python",
                                "Java",
                                "Java es un lenguaje de programación popular para el desarrollo de aplicaciones de escritorio, especialmente en el ámbito empresarial."},
                        {"¿Cuál de los siguientes lenguajes de programación se utiliza mejor para desarrollar aplicaciones de escritorio en Windows?", "Java", "C#", "Python", "C#", "C# es un lenguaje de programación específico para la plataforma Windows, mientras que Java y Python son lenguajes multiplataforma."},
                        {"¿Cuál de los siguientes lenguajes de programación es el más utilizado en el desarrollo de sitios web?", "HTML", "CSS", "JavaScript", "JavaScript", "JavaScript es un lenguaje de programación que se utiliza para hacer sitios web interactivos y dinámicos."},
                        {"¿Para qué tipo de aplicaciones se recomienda utilizar el lenguaje de programación Swift?", "Desarrollo de aplicaciones para iOS", "Programación de juegos", "Creación de aplicaciones para Android", "Desarrollo de aplicaciones para iOS", "Swift es un lenguaje de programación desarrollado por Apple para la creación de aplicaciones para iOS."},
                        {"¿Cuál de los siguientes lenguajes de programación se utiliza principalmente en el procesamiento de datos y la estadística?", "Java", "C++", "R", "R", "R es un lenguaje de programación que se utiliza principalmente en el análisis de datos y la estadística."},
                        {"¿Cuál de los siguientes lenguajes de programación se utiliza principalmente en el desarrollo de aplicaciones de inteligencia artificial?", "Python", "Java", "C++", "Python", "Python es un lenguaje de programación popular para el desarrollo de aplicaciones de inteligencia artificial, aprendizaje automático y minería de datos debido a sus bibliotecas y frameworks especializados."},
                        {"¿Cuál es el lenguaje de programación utilizado para la creación de aplicaciones de realidad virtual?", "C#", "Java", "Python", "C#", "C# es uno de los lenguajes de programación más utilizados en la creación de aplicaciones de realidad virtual, gracias a sus bibliotecas y herramientas especializadas."},
                        {"¿Cuál es la principal diferencia entre PHP y JavaScript?",
                                "PHP es un lenguaje de programación de backend y JavaScript es un lenguaje de programación de frontend", "PHP es un lenguaje de programación de frontend y JavaScript es un lenguaje de programación de backend", "PHP y JavaScript son lenguajes de programación de backend",
                                "PHP es un lenguaje de programación de backend y JavaScript es un lenguaje de programación de frontend",
                                "La principal diferencia entre PHP y JavaScript es que PHP es un lenguaje de programación de backend diseñado específicamente para el desarrollo web, mientras que JavaScript es un lenguaje de programación de frontend utilizado principalmente para crear interacciones dinámicas en la web."},
                        {"¿Qué es PHP?", "Un lenguaje de programación de backend", "Un lenguaje de programación de frontend", "Un lenguaje de programación de bases de datos", "Un lenguaje de programación de backend", "PHP es un lenguaje de programación de backend diseñado específicamente para el desarrollo web."},

                        {"¿Qué es JavaScript?", "Un lenguaje de programación de frontend", "Un lenguaje de programación de bases de datos", "Un lenguaje de programación de backend", "Un lenguaje de programación de frontend", "JavaScript es un lenguaje de programación de frontend que se utiliza principalmente para crear interacciones dinámicas en la web."},

                        {"¿Cuál es la principal diferencia entre Python y C++?", "Python es un lenguaje de programación interpretado y C++ es un lenguaje compilado", "Python es un lenguaje de programación compilado y C++ es un lenguaje interpretado", "Python y C++ son lenguajes de programación compilados", "Python es un lenguaje de programación interpretado y C++ es un lenguaje compilado", "Python ejecuta el código directamente sin una etapa de compilación previa, mientras que C++ requiere una compilación previa antes de la ejecución."},

                        {"¿En qué aspectos es superior Python a Perl?", "Legibilidad y mantenibilidad de código", "Velocidad de ejecución y uso de memoria", "Flexibilidad y capacidad de programación concurrente", "Legibilidad y mantenibilidad de código", "Python tiene una sintaxis limpia y legible, lo que facilita la escritura y mantenimiento de código, mientras que Perl puede tener una sintaxis más compleja. Además, Python tiene mejores características integradas para la programación orientada a objetos y el manejo de excepciones."},

                        {"¿Cuál es la principal ventaja de Python sobre Java?", "Facilidad de uso y legibilidad de código", "Rendimiento y eficiencia en el uso de recursos", "Capacidad de programación concurrente y distribuida", "Facilidad de uso y legibilidad de código", "Python tiene una sintaxis más clara y legible que Java, lo que facilita la escritura y mantenimiento de código. Además, Python tiene un conjunto de bibliotecas y herramientas más completo y diverso que Java."},

                        {"¿Qué características comparten Python y Ruby?", "Interpretación, orientación a objetos y manejo de excepciones", "Compilación, programación estructurada y eficiencia", "Capacidad de programación concurrente y distribuida", "Interpretación, orientación a objetos y manejo de excepciones", "Tanto Python como Ruby son lenguajes de programación interpretados y tienen una fuerte orientación a objetos. Además, ambos tienen características integradas para el manejo de excepciones y la programación funcional."},

                        {"¿En qué aspectos es inferior Python a Lisp?", "Manejo de macros y programación funcional avanzada", "Velocidad de ejecución y eficiencia en el uso de memoria", "Facilidad de uso y mantenibilidad de código", "Manejo de macros y programación funcional avanzada", "Lisp es un lenguaje de programación que tiene una sintaxis más abstracta, pero es muy flexible en cuanto a la programación funcional y la capacidad de definir macros para extender la sintaxis del lenguaje. Python tiene una sintaxis más fácil de usar y una mayor disponibilidad de bibliotecas y herramientas."},
                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"¿Qué paradigma de programación se centra en la reutilización de código a través de la creación de clases y objetos?", "Programación orientada a objetos", "Programación funcional", "Programación estructurada", "Programación orientada a objetos", "La programación orientada a objetos se centra en la creación de clases y objetos para la reutilización de código."},
                        {"¿Cuál es el paradigma de programación que se basa en la evaluación de funciones?", "Programación funcional", "Programación orientada a objetos", "Programación estructurada", "Programación funcional", "En la programación funcional se basa en la evaluación de funciones y no en la modificación de variables."},
                        {"¿Qué paradigma de programación se enfoca en la solución de problemas a través de la definición de funciones y procedimientos?", "Programación estructurada", "Programación funcional", "Programación orientada a objetos", "Programación estructurada", "La programación estructurada se enfoca en la solución de problemas a través de la definición de funciones y procedimientos."},
                        {"¿Cuál es el paradigma de programación que se enfoca en la creación de algoritmos basados en reglas lógicas?", "Programación lógica", "Programación orientada a objetos", "Programación funcional", "Programación lógica", "La programación lógica se enfoca en la creación de algoritmos basados en reglas lógicas."},
                        {"¿Qué paradigma de programación se basa en la definición de tipos de datos abstractos?", "Programación orientada a objetos", "Programación funcional", "Programación estructurada", "Programación orientada a objetos", "La programación orientada a objetos se basa en la definición de tipos de datos abstractos."},
                        {"¿Cuál es el paradigma de programación que se enfoca en la concurrencia y la comunicación entre procesos?", "Programación concurrente", "Programación estructurada", "Programación funcional", "Programación concurrente", "La programación concurrente se enfoca en la concurrencia y la comunicación entre procesos."},
                        {"¿Qué paradigma de programación se basa en la separación de datos y lógica de negocio?", "Programación basada en reglas", "Programación orientada a objetos", "Programación estructurada", "Programación basada en reglas", "La programación basada en reglas se basa en la separación de datos y lógica de negocio."},
                        {"¿Cuál es el paradigma de programación que se enfoca en la creación de sistemas distribuidos?", "Programación distribuida", "Programación estructurada", "Programación funcional", "Programación distribuida", "La programación distribuida se enfoca en la creación de sistemas distribuidos."},
                        {"¿Qué paradigma de programación se basa en la interacción entre el programa y el usuario a través de una interfaz gráfica?", "Programación visual", "Programación orientada a objetos", "Programación estructurada", "Programación visual", "La programación visual se basa en la interacción entre el programa y el usuario a través de una interfaz gráfica."},
                        {"¿Cuál de los siguientes paradigmas de programación se basa en la idea de declarar las cosas y sus relaciones?", "Programación declarativa", "Programación imperativa", "Programación funcional", "Programación declarativa", "La programación declarativa se basa en la declaración de cosas y sus relaciones, mientras que en la programación imperativa se describe cómo se deben realizar las tareas."},

                        {"¿Qué paradigma de programación se basa en la idea de que el programa es una serie de transformaciones de datos?", "Programación funcional", "Programación orientada a objetos", "Programación estructurada", "Programación funcional", "La programación funcional se basa en la idea de que el programa es una serie de transformaciones de datos inmutables."},

                        {"¿Cuál de los siguientes es un ejemplo de un lenguaje de programación multiparadigma?", "Python", "Java", "C++", "Python", "Python es un lenguaje de programación que soporta varios paradigmas, incluyendo programación orientada a objetos, programación funcional y programación imperativa."},

                        {"¿Qué paradigma de programación se basa en la idea de que el programa es un conjunto de entidades que interactúan entre sí?", "Programación orientada a objetos", "Programación estructurada", "Programación funcional", "Programación orientada a objetos", "La programación orientada a objetos se basa en la idea de que el programa es un conjunto de entidades que interactúan entre sí, y cada entidad tiene atributos y comportamientos."},

                        {"¿Cuál de los siguientes paradigmas de programación se centra en la resolución de problemas mediante la definición de reglas y hechos?", "Programación lógica", "Programación funcional", "Programación orientada a objetos", "Programación lógica", "La programación lógica se centra en la resolución de problemas mediante la definición de reglas y hechos, y utiliza la lógica matemática para encontrar soluciones."},

                        {"¿Cuál es el paradigma de programación que se enfoca en la manipulación de objetos?", "Programación orientada a objetos", "Programación estructurada", "Programación funcional", "Programación orientada a objetos", "La programación orientada a objetos se enfoca en la manipulación de objetos, permitiendo el encapsulamiento de datos y la definición de relaciones entre ellos."},

                        {"¿Qué paradigma de programación se basa en la ejecución de una serie de instrucciones secuenciales?", "Programación estructurada", "Programación orientada a objetos", "Programación funcional", "Programación estructurada", "La programación estructurada se basa en la ejecución de una serie de instrucciones secuenciales, y es uno de los paradigmas de programación más antiguos y ampliamente utilizados."},

                        {"¿Cuál es el paradigma de programación que se enfoca en la evaluación de expresiones matemáticas?", "Programación funcional", "Programación orientada a objetos", "Programación estructurada", "Programación funcional", "La programación funcional se enfoca en la evaluación de expresiones matemáticas, y se basa en el uso de funciones puras que no tienen efectos secundarios."},

                        {"¿Qué paradigma de programación se enfoca en la definición de reglas y relaciones entre elementos?", "Programación lógica", "Programación orientada a objetos", "Programación funcional", "Programación lógica", "La programación lógica se enfoca en la definición de reglas y relaciones entre elementos, y se basa en el uso de la lógica para resolver problemas."},

                        {"¿Cuál es el paradigma de programación que se enfoca en la gestión de flujos de datos?", "Programación reactiva", "Programación orientada a objetos", "Programación funcional", "Programación reactiva", "La programación reactiva se enfoca en la gestión de flujos de datos y eventos, y se basa en el uso de observables y streams para modelar la interacción con el usuario y otros sistemas."},

                        {"¿Qué paradigma de programación se basa en la definición de acciones y efectos secundarios?",
                                "Programación imperativa", "Programación orientada a objetos", "Programación funcional",
                                "Programación imperativa",
                                "La programación imperativa se basa en la definición de acciones y efectos secundarios, y se enfoca en el uso de variables y bucles para controlar el flujo del programa."},

                        {"¿Cuál es el paradigma de programación que se enfoca en la definición de comportamientos a través de la interacción con el entorno?", "Programación conductual", "Programación orientada a objetos", "Programación funcional", "Programación conductual", "La programación conductual se enfoca en la definición de comportamientos a través de la interacción con el entorno, y se basa en el uso de estímulos y respuestas para modelar la interacción con el usuario."}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"¿Qué es un IDE?", "Un conjunto de herramientas de software integradas para el desarrollo de aplicaciones", "Un lenguaje de programación específico para la web", "Un tipo de hardware especializado para el desarrollo de software", "Un conjunto de herramientas de software integradas para el desarrollo de aplicaciones", "Un IDE, o entorno de desarrollo integrado, es un software que combina diferentes herramientas y funcionalidades para facilitar el desarrollo de aplicaciones informáticas."},

                        {"¿Cuál es uno de los IDE más populares para el desarrollo de aplicaciones en lenguaje Java?", "Eclipse", "Visual Studio", "Atom", "Eclipse", "Eclipse es un IDE de código abierto muy popular entre los desarrolladores de aplicaciones en lenguaje Java."},

                        {"¿Cuál de los siguientes IDE es más utilizado para el desarrollo de aplicaciones móviles en la plataforma iOS?", "Xcode", "Android Studio", "Visual Studio", "Xcode", "Xcode es el IDE desarrollado por Apple para el desarrollo de aplicaciones móviles en la plataforma iOS."},

                        {"¿Cuál es el IDE más utilizado para el desarrollo de aplicaciones en lenguaje C++?", "Visual Studio", "Eclipse", "Code::Blocks", "Visual Studio", "Visual Studio es uno de los IDE más utilizados para el desarrollo de aplicaciones en lenguaje C++, con una amplia variedad de herramientas y funcionalidades integradas."},

                        {"¿Cuál es el IDE más utilizado para el desarrollo de aplicaciones web?", "Visual Studio Code", "NetBeans", "PHPStorm", "Visual Studio Code", "Visual Studio Code es un IDE de código abierto muy popular para el desarrollo de aplicaciones web, con una gran cantidad de extensiones y herramientas disponibles."},

                        {"¿Qué es un plugin en un IDE?",
                                "Una extensión que añade funcionalidades adicionales al IDE", "Un tipo de archivo ejecutable que se utiliza para crear aplicaciones", "Un archivo que contiene información sobre el código fuente de una aplicación",
                                "Una extensión que añade funcionalidades adicionales al IDE",
                                "Los plugins son extensiones que se pueden agregar a un IDE para añadir funcionalidades adicionales, como la integración con sistemas de control de versiones, la realización de pruebas unitarias y la implementación de herramientas de análisis de código."},

                        {"¿Cuál es el IDE más utilizado para el desarrollo de aplicaciones en lenguaje Python?",
                                "PyCharm", "Visual Studio Code", "Atom",
                                "PyCharm",
                                "PyCharm es uno de los IDE más utilizados para el desarrollo de aplicaciones en lenguaje Python, con una amplia variedad de herramientas y funcionalidades integradas."},

                        {"¿Cuál de los siguientes no es un tipo de entorno de desarrollo?",
                                "IDE", "API", "CLI",
                                "API",
                                "Los IDE (Integrated Development Environment) son entornos de desarrollo integrados que ofrecen herramientas para la edición, compilación, depuración y ejecución de programas."},

                        {"¿Qué es un lenguaje de programación utilizado para desarrollar entornos de desarrollo?",
                                "C#", "Java", "Python",
                                "Java",
                                "Java es un lenguaje de programación popularmente utilizado para desarrollar entornos de desarrollo debido a su facilidad de uso, robustez y portabilidad."},

                        {"¿Cuál es el objetivo principal de un entorno de desarrollo integrado (IDE)?",
                                "Desarrollar software", "Gestionar proyectos", "Facilitar el desarrollo de software",
                                "Facilitar el desarrollo de software",
                                "Un IDE es una herramienta que integra múltiples funciones en una sola interfaz de usuario para facilitar el desarrollo de software."},

                        {"¿Qué es un depurador?",
                                "Un programa que detecta y corrige errores de código", "Un programa que compila código fuente", "Un programa que ejecuta código fuente",
                                "Un programa que detecta y corrige errores de código",
                                "Un depurador es una herramienta que ayuda a detectar y corregir errores en el código fuente mientras se está desarrollando un programa."},

                        {"¿Cuál de los siguientes no es una característica de un entorno de desarrollo?",
                                "Interfaz de usuario amigable", "Compilación automática de código fuente", "Edición de código fuente solo en texto plano",
                                "Edición de código fuente solo en texto plano",
                                "Un entorno de desarrollo debe permitir la edición de código fuente de manera gráfica y no solo en texto plano."},

                        {"¿Qué es un control de versiones?",
                                "Una herramienta para la gestión de proyectos", "Un sistema para rastrear cambios en el código fuente", "Un lenguaje de programación",
                                "Un sistema para rastrear cambios en el código fuente",
                                "Un control de versiones es un sistema que permite rastrear y controlar los cambios realizados en el código fuente de un proyecto."},

                        {"¿Qué es un sistema de compilación?",
                                "Un sistema para la gestión de proyectos", "Un sistema para automatizar el proceso de compilación", "Un lenguaje de programación",
                                "Un sistema para automatizar el proceso de compilación",
                                "Un sistema de compilación es una herramienta que automatiza el proceso de compilación del código fuente de un proyecto."},

                        {"¿Cuál es el objetivo principal de un sistema de integración continua?",
                                "Automatizar la compilación y pruebas de un proyecto", "Gestionar el código fuente de un proyecto", "Facilitar el desarrollo de software",
                                "Automatizar la compilación y pruebas de un proyecto",
                                "Un sistema de integración continua es una herramienta que automatiza la compilación y pruebas de un proyecto para detectar errores tempranamente."},
                        {"¿Qué es Git?",
                                "Un sistema de control de versiones distribuido.", "Un IDE.", "Un lenguaje de programación.",
                                "Un sistema de control de versiones distribuido.", "Correcto: Git es un sistema de control de versiones distribuido."},

                        {"¿Qué es un repositorio en Git?",
                                "Un lugar donde se almacena el código fuente y los cambios realizados.", "Un archivo que contiene el código fuente de un programa.", "Una herramienta para hacer copias de seguridad del código.",
                                "Un lugar donde se almacena el código fuente y los cambios realizados.",
                                "Correcto: Un repositorio en Git es un lugar donde se almacena el código fuente y los cambios realizados."},
                        {"¿Qué es un pull request en Git?",
                                "Una solicitud para fusionar cambios realizados en una rama con otra.", "Una solicitud para revertir cambios realizados en una rama.", "Una solicitud para eliminar una rama.",
                                "Una solicitud para fusionar cambios realizados en una rama con otra.",
                                "Correcto: Un pull request en Git es una solicitud para fusionar cambios realizados en una rama con otra."},
                        {"¿Qué es un flujo de trabajo (workflow) en Git?",
                                "Una serie de pasos que se siguen para gestionar cambios en el código.", "Una herramienta para depurar el código.", "Una herramienta para ejecutar pruebas unitarias.",
                                "Una serie de pasos que se siguen para gestionar cambios en el código.", "Correcto: Un flujo de trabajo en Git es una serie de pasos que se siguen para gestionar cambios en el código."}
                };
                break;
            case 4:
                preguntas = new String[][]{
                        {"¿Qué es un sistema de gestión de bases de datos (DBMS)?", "Un software que permite gestionar y manipular datos en una base de datos.", "Un sistema operativo que permite el almacenamiento de datos.", "Un lenguaje de programación para el manejo de datos.", "Un software que permite gestionar y manipular datos en una base de datos.", "Un DBMS es una herramienta utilizada para gestionar y manipular datos en una base de datos."},

                        {"¿Cuál es la función principal de un DBMS?", "Almacenar y gestionar datos de manera eficiente y segura.", "Crear aplicaciones de software.", "Desarrollar páginas web.", "Almacenar y gestionar datos de manera eficiente y segura.", "El objetivo principal de un DBMS es permitir la gestión eficiente y segura de grandes cantidades de datos."},

                        {"¿Qué es una base de datos relacional?", "Un tipo de base de datos que utiliza tablas para organizar la información.", "Una base de datos que almacena imágenes y videos.", "Un sistema que permite la gestión de datos en la nube.", "Un tipo de base de datos que utiliza tablas para organizar la información.", "Una base de datos relacional utiliza tablas para almacenar y organizar los datos, y permite establecer relaciones entre ellas para obtener información de manera eficiente."},

                        {"¿Cuál es la diferencia entre una base de datos relacional y una base de datos no relacional?", "Una base de datos relacional utiliza tablas para almacenar datos y una no relacional utiliza documentos o grafos.", "Una base de datos relacional solo permite el acceso de un usuario a la vez y una no relacional permite el acceso simultáneo de varios usuarios.", "Una base de datos relacional solo permite el acceso a través de una conexión de red y una no relacional permite el acceso local y remoto.", "Una base de datos relacional utiliza tablas para almacenar datos y una no relacional utiliza documentos o grafos.", "La principal diferencia entre una base de datos relacional y una no relacional es la forma en que almacenan y organizan los datos."},

                        {"¿Qué es una consulta SQL?",
                                "Una solicitud de información a una base de datos relacional.", "Una herramienta de programación para el desarrollo de software.", "Un programa para la gestión de proyectos.",
                                "Una solicitud de información a una base de datos relacional.",
                                "Una consulta SQL es una solicitud de información a una base de datos relacional utilizando el lenguaje de programación SQL (Structured Query Language)."},

                        {"¿Qué es la normalización de bases de datos?", "Un proceso para reducir la redundancia y mejorar la eficiencia de una base de datos.", "Un proceso para crear copias de seguridad de una base de datos.", "Un proceso para eliminar la información obsoleta de una base de datos.", "Un proceso para reducir la redundancia y mejorar la eficiencia de una base de datos.", "La normalización es un proceso utilizado para reducir la redundancia de los datos y mejorar la eficiencia de una base de datos mediante la eliminación de duplicados y la organización de los datos en diferentes tablas."},
                        {"¿Cuál es el propósito de un modelo entidad-relación (ER)?", "Representar la estructura lógica de una base de datos y las relaciones entre sus entidades.", "Representar la estructura física de una base de datos y las relaciones entre sus tablas.", "Generar consultas complejas en una base de datos.", "Representar la estructura lógica de una base de datos y las relaciones entre sus entidades.", "Un modelo entidad-relación es una herramienta para diseñar y representar la estructura lógica de una base de datos, y visualizar las relaciones entre las entidades que la conforman."},

                        {"¿Qué es la cardinalidad en un modelo entidad-relación?", "Es la relación entre dos entidades en términos de su número de ocurrencias.", "Es la relación entre dos entidades en términos de su similitud estructural.", "Es la relación entre dos entidades en términos de su orden de aparición en la base de datos.", "Es la relación entre dos entidades en términos de su número de ocurrencias.", "La cardinalidad en un modelo entidad-relación se refiere a la relación entre dos entidades en términos de su número de ocurrencias, es decir, si una entidad está asociada con una o muchas instancias de otra entidad."},

                        {"¿Qué es la integridad referencial en una base de datos?", "Garantía de que los datos en una tabla que se refieren a los datos en otra tabla son válidos y consistentes.", "Capacidad de la base de datos para manejar múltiples usuarios simultáneamente.", "Capacidad de la base de datos para recuperar datos rápidamente.", "Garantía de que los datos en una tabla que se refieren a los datos en otra tabla son válidos y consistentes.", "La integridad referencial en una base de datos es importante para garantizar la coherencia y consistencia de los datos, asegurando que los datos en una tabla que se refieren a los datos en otra tabla son válidos."},

                        {"¿Qué es una clave primaria en una base de datos?", "Un campo que identifica de forma única cada registro en una tabla.", "Un campo que establece una relación entre dos tablas.", "Un campo que se utiliza para ordenar los registros de una tabla.", "Un campo que identifica de forma única cada registro en una tabla.", "Una clave primaria es un campo o conjunto de campos que identifica de forma única cada registro en una tabla y se utiliza para establecer relaciones con otras tablas."},

                        {"¿Qué es una transacción en una base de datos?",
                                "Una secuencia de operaciones que se realizan como una unidad atómica e indivisible.", "Una solicitud de datos realizada por un usuario o una aplicación.", "Una operación que modifica la estructura de una tabla.",
                                "Una secuencia de operaciones que se realizan como una unidad atómica e indivisible.",
                                "Una transacción es una secuencia de operaciones que se realizan como una unidad atómica e indivisible, lo que significa que todas las operaciones se realizan o ninguna de ellas se realiza."},

                        {"¿Qué es un índice en una base de datos?", "Una estructura de datos que mejora la velocidad de búsqueda de registros en una tabla.", "Una restricción que limita los valores que se pueden insertar en una tabla.", "Una tabla que se utiliza para almacenar datos temporales.", "Una estructura de datos que mejora la velocidad de búsqueda de registros en una tabla.", "Un índice es una estructura de datos que mejora la velocidad de búsqueda de registros en una tabla, acelerando las operaciones de consulta y ordenación de datos."},

                        {"¿Qué es una llave compuesta?", "Una clave primaria que consta de varios atributos.", "Una clave foránea que hace referencia a varias tablas.", "Una clave que consta de un solo atributo.", "Una clave primaria que consta de varios atributos.", "Una llave compuesta es una clave primaria que consta de varios atributos, lo que permite identificar de forma única una fila en una tabla."},

                        {"¿Qué es una transacción en una base de datos?", "Una serie de operaciones que se realizan en una base de datos como una unidad atómica.", "Una tabla que almacena información sobre las transacciones realizadas en una base de datos.", "Una herramienta para realizar copias de seguridad de una base de datos.", "Una serie de operaciones que se realizan en una base de datos como una unidad atómica.", "Una transacción es una serie de operaciones que se realizan en una base de datos como una unidad atómica, lo que significa que si una operación falla, todas las operaciones realizadas en la transacción se deshacen para evitar resultados inconsistentes."},

                        {"¿Qué es una vista materializada en una base de datos?", "Una vista que se actualiza automáticamente cuando se modifican los datos subyacentes.", "Una vista que se almacena como una tabla física en lugar de como una definición lógica.", "Una vista que permite a los usuarios editar los datos directamente.", "Una vista que se almacena como una tabla física en lugar de como una definición lógica.", "Una vista materializada es una vista que se almacena como una tabla física en lugar de como una definición lógica, lo que mejora el rendimiento de las consultas y reduce la carga en el servidor de la base de datos."},

                        {"¿Qué es la primera forma normal (1FN) en la normalización de bases de datos?",
                                "Cada tabla debe tener una clave primaria", "No se permite la existencia de valores nulos", "Los valores deben estar en un formato específico",
                                "Cada tabla debe tener una clave primaria",
                                "La 1FN exige que cada tabla tenga un identificador único o clave primaria que permita identificar de manera unívoca cada registro en la tabla."},
                        {"¿Qué es la tercera forma normal (3FN) en la normalización de bases de datos?", "Eliminación de dependencias transitivas entre columnas", "Eliminación de redundancia de datos", "Creación de tablas adicionales", "Eliminación de dependencias transitivas entre columnas", "La 3FN busca eliminar dependencias transitivas, lo que significa que una columna no debe depender de otra que a su vez dependa de otra, sino que debe depender únicamente de la clave primaria."},
                        {"¿Qué es la forma normal de Boyce-Codd (BCNF) en la normalización de bases de datos?", "Eliminación de dependencias funcionales no triviales", "Eliminación de redundancia de datos", "Creación de tablas adicionales", "Eliminación de dependencias funcionales no triviales", "La BCNF es una forma normal que busca eliminar las dependencias funcionales no triviales, es decir, aquellas en las que una columna no clave depende de otra no clave."},

                        {"¿Qué es la cuarta forma normal (4FN) en la normalización de bases de datos?",
                                "Eliminación de dependencias múltiples entre columnas", "Eliminación de redundancia de datos", "Creación de tablas adicionales",
                                "Eliminación de dependencias múltiples entre columnas",
                                "La 4FN busca evitar la existencia de dependencias múltiples, es decir, aquellas en las que una columna depende de varias columnas no clave, para evitar redundancias innecesarias en la base de datos."},

                        {"¿Qué tipo de base de datos es más adecuada para almacenar y procesar grandes cantidades de datos en línea?",
                                "Base de datos relacional",
                               "Base de datos NoSQL",
                               "Base de datos de red",
                               "Base de datos NoSQL",

                "Retroalimentación: Las bases de datos NoSQL son ideales para manejar grandes cantidades de datos en línea, ya que están diseñadas para escalar horizontalmente y ofrecen una mayor flexibilidad en el manejo de datos no estructurados."},

                                { "¿Cuál de los siguientes tipos de bases de datos está diseñada para manejar datos geoespaciales?",
                "Base de datos jerárquica",
                "Base de datos espacial",
                "Base de datos de grafo",
                "Base de datos espacial",
                "Las bases de datos espaciales son utilizadas para manejar datos geoespaciales como coordenadas geográficas, polígonos, mapas y otros tipos de información geográfica."},


                                {"¿Cuál de los siguientes tipos de bases de datos es conocida por su alta disponibilidad y escalabilidad?",
                    "Base de datos relacional",
                "Base de datos NoSQL",
                "Base de datos de red",
                "Base de datos NoSQL",
                "Las bases de datos NoSQL son altamente escalables y ofrecen una alta disponibilidad debido a su diseño distribuido y tolerancia a fallos."},

                                {"¿Qué tipo de base de datos utiliza un modelo de datos basado en objetos?",
                "Base de datos relacional",
                "Base de datos orientada a objetos",
                "Base de datos de red",
                "Base de datos orientada a objetos",
                "Las bases de datos orientadas a objetos utilizan un modelo de datos basado en objetos en lugar de un modelo basado en tablas como las bases de datos relacionales."},

                                {"¿Qué tipo de base de datos es más adecuada para almacenar y consultar grandes cantidades de datos relacionados?",
                "Base de datos jerárquica",
                "Base de datos relacional",
                "Base de datos de grafo",
                "Base de datos relacional",
                "Las bases de datos relacionales son ideales para manejar grandes cantidades de datos relacionales y para realizar consultas complejas."},

                                {"¿Qué tipo de base de datos utiliza una estructura de datos de tipo árbol?",
                "Base de datos jerárquica",
                "Base de datos relacional",
                "Base de datos de red",
                "Base de datos jerárquica",
                "Las bases de datos jerárquicas utilizan una estructura de datos de tipo árbol para organizar los datos y establecer las relaciones entre ellos."},


                        {"¿Qué es la gestión de datos en el contexto de los sistemas de software?",
                                "El proceso de administrar y controlar el acceso, almacenamiento, organización y recuperación de datos en un sistema de software."},

                        {"¿Cuál de las siguientes opciones describe mejor la función principal de un sistema de gestión de bases de datos (DBMS)?",
                                "Almacenar y administrar datos de manera eficiente y segura",
                                "Desarrollar interfaces de usuario",
                                "Crear algoritmos de búsqueda",
                                "Almacenar y administrar datos de manera eficiente y segura",
                                "Un sistema de gestión de bases de datos (DBMS) se utiliza para almacenar y administrar datos de manera eficiente y segura en un sistema de software."},

                        {"¿Cuál de las siguientes opciones describe una ventaja de utilizar una base de datos relacional en comparación con una base de datos no relacional?",
                                "Soporte para relaciones entre conjuntos de datos",
                                "Mayor flexibilidad en la estructura de datos",
                                "Mayor rendimiento en consultas complejas",
                                "Soporte para relaciones entre conjuntos de datos",
                                "Una base de datos relacional permite establecer relaciones entre conjuntos de datos y proporciona una estructura sólida para garantizar la integridad y coherencia de los datos."},

                        {"¿Qué es un modelo de datos?",
                                "Una representación lógica de cómo se organizan y relacionan los datos en una base de datos",
                                "Un lenguaje de programación utilizado para manipular datos",
                                "Una técnica para realizar consultas de bases de datos",
                                "Una representación lógica de cómo se organizan y relacionan los datos en una base de datos",
                                "Un modelo de datos define la estructura lógica de una base de datos y cómo se relacionan los diferentes conjuntos de datos."},

                        {"¿Cuál de las siguientes opciones describe mejor la función de un lenguaje de consulta estructurado (SQL) en la gestión de datos?",
                                "Permite realizar consultas y manipular datos en una base de datos relacional",
                                "Controla el acceso a la base de datos",
                                "Administra el almacenamiento físico de los datos",
                                "Permite realizar consultas y manipular datos en una base de datos relacional",
                                "SQL es un lenguaje utilizado para realizar consultas y manipular datos en una base de datos relacional."},

                        {"¿Qué es la normalización de bases de datos?",
                                "Un proceso para diseñar una base de datos de manera que los datos estén organizados y libres de redundancias",
                                "Un método de cifrado utilizado para proteger los datos en una base de datos",
                                "Una técnica para optimizar consultas en una base de datos",
                                "Un proceso para diseñar una base de datos de manera que los datos estén organizados y libres de redundancias",
                                "La normalización es un proceso que garantiza que una base de datos esté organizada de manera eficiente y libre de redundancias, lo que mejora la integridad y el rendimiento de los datos."},

                        {"¿Cuál de las siguientes opciones describe mejor la función de una transacción en la gestión de datos?",
                                "Una secuencia de operaciones que se ejecutan como una unidad indivisible y aseguran la consistencia de los datos",
                                "Un método para respaldar y recuperar datos en caso de falla del sistema",
                                "Una técnica para mejorar el rendimiento de las consultas en una base de datos",
                                "Una secuencia de operaciones que se ejecutan como una unidad indivisible y aseguran la consistencia de los datos",
                                "Una transacción es una secuencia de operaciones que se ejecutan como una unidad indivisible, asegurando que los datos se mantengan en un estado consistente."},

                        {"¿Cuál de las siguientes opciones describe mejor la función de la integridad referencial en una base de datos relacional?",
                                "Mantiene la coherencia de los datos al establecer relaciones entre tablas y garantizar la integridad de las referencias",
                                "Controla el acceso y los permisos de los usuarios a la base de datos",
                                "Realiza copias de seguridad de la base de datos para proteger los datos contra pérdidas",
                                "Mantiene la coherencia de los datos al establecer relaciones entre tablas y garantizar la integridad de las referencias",
                                "La integridad referencial garantiza que las relaciones entre tablas en una base de datos relacional sean coherentes y se mantengan las referencias correctas entre los datos."},

                        {"¿Cuál de las siguientes opciones describe mejor la función de un índice en la gestión de datos?",
                                "Mejora el rendimiento de las consultas al proporcionar un acceso más rápido a los datos",
                                "Controla la seguridad y los permisos de acceso a la base de datos",
                                "Realiza el respaldo y la recuperación de los datos en caso de falla del sistema",
                                "Mejora el rendimiento de las consultas al proporcionar un acceso más rápido a los datos",
                                "Un índice en una base de datos mejora el rendimiento de las consultas al proporcionar un acceso más rápido a los datos mediante la creación de una estructura de búsqueda optimizada."},

                        {"¿Qué es la integridad de datos en la gestión de bases de datos?",
                                "La garantía de que los datos en una base de datos sean precisos, consistentes y estén libres de errores",
                                "La capacidad de realizar copias de seguridad y recuperar los datos en caso de falla del sistema",
                                "Un método para controlar el acceso y los permisos de los usuarios a la base de datos",
                                "La garantía de que los datos en una base de datos sean precisos, consistentes y estén libres de errores",
                                "La integridad de datos se refiere a la garantía de que los datos en una base de datos sean precisos, consistentes y estén libres de errores, asegurando su calidad y confiabilidad."}
                        };
                break;


            case 5:
                preguntas = new String[][]{
                        {"¿Qué es una plataforma de desarrollo de bajo código?", "Una plataforma que permite a los usuarios crear aplicaciones sin escribir mucho código", "Una plataforma que solo admite ciertos lenguajes de programación", "Una plataforma que solo se usa para aplicaciones móviles", "Una plataforma que permite a los usuarios crear aplicaciones sin escribir mucho código", "Una plataforma de desarrollo de bajo código es una herramienta que permite a los usuarios crear aplicaciones con un mínimo de programación manual y se enfoca en la visualización y la automatización de procesos."},
                        {"¿Cuál de las siguientes es una plataforma de desarrollo para aplicaciones móviles?",
                                "Android Studio",
                                "Visual Studio Code",
                                "Eclipse",
                                "Android Studio",
                                "Android Studio es una plataforma de desarrollo para aplicaciones móviles. Es la herramienta oficial para desarrollar aplicaciones para Android."},

                        {"¿Qué plataforma de desarrollo de software es conocida por su uso en la creación de videojuegos?",
                                "Unity",
                                "React",
                                "Django",
                                "Unity",
                                "Unity es una plataforma de desarrollo de software conocida por su uso en la creación de videojuegos."},

                        {"¿Cuál es una plataforma de desarrollo de software para la creación de aplicaciones web en tiempo real?",
                                "Firebase",
                                "Angular",
                                "Flask",
                                "Firebase",
                                "Firebase es una plataforma de desarrollo de software para la creación de aplicaciones web en tiempo real."},

                        {"¿Cuál es una plataforma de desarrollo de software para la creación de aplicaciones de escritorio multiplataforma?",
                                "Electron",
                                "Xcode",
                                "Android Studio",
                                "Electron",
                                "Electron es una plataforma de desarrollo de software para la creación de aplicaciones de escritorio multiplataforma utilizando tecnologías web como HTML, CSS y JavaScript."},

                        {"¿Qué plataforma de desarrollo de software se utiliza para crear aplicaciones empresariales?",
                                "Salesforce",
                                "Ruby on Rails",
                                "Express",
                                "Salesforce",
                                "Salesforce es una plataforma de desarrollo de software utilizada para crear aplicaciones empresariales y aplicaciones de gestión de relaciones con los clientes (CRM)."},

                        {"¿Qué es un IDE?",
                                "Una plataforma de desarrollo de software.",
                                "Un lenguaje de programación.",
                                "Un entorno de desarrollo integrado.",
                                "Opción 3",
                                "Un IDE (Entorno de Desarrollo Integrado) es una herramienta que proporciona un entorno completo para el desarrollo de software."},

                        {"¿Cuál de las siguientes opciones es un lenguaje de programación utilizado en plataformas de desarrollo web?",
                                "Java",
                                "C++",
                                "Swift",
                                "Java",
                                "Java es un lenguaje de programación comúnmente utilizado en plataformas de desarrollo web."},

                        {"¿Qué es un framework en el contexto de las plataformas de desarrollo?",
                                "Una biblioteca de funciones y herramientas predefinidas para facilitar el desarrollo de aplicaciones.",
                                "Una interfaz gráfica para diseñar aplicaciones.",
                                "Un servidor de base de datos para almacenar información.",
                                "Una biblioteca de funciones y herramientas predefinidas para facilitar el desarrollo de aplicaciones.",
                                "Un framework es una biblioteca de funciones y herramientas predefinidas que facilitan el desarrollo de aplicaciones."},

                        {"¿Cuál de las siguientes opciones es una plataforma de desarrollo móvil ampliamente utilizada?",
                                "Xcode",
                                "Eclipse",
                                "Notepad++",
                                "Xcode",
                                "Xcode es una plataforma de desarrollo móvil ampliamente utilizada para desarrollar aplicaciones iOS."},

                        {"¿Cuál es una ventaja de utilizar una plataforma de desarrollo en la nube?",
                                "Mayor control sobre el hardware utilizado.",
                                "Mayor rendimiento en el desarrollo de aplicaciones.",
                                "Acceso a recursos escalables y flexibles.",
                                "Acceso a recursos escalables y flexibles.",
                                "Una ventaja de utilizar una plataforma de desarrollo en la nube es el acceso a recursos escalables y flexibles."},
                        {"¿Cuál es el propósito principal de un lenguaje de marcado en el desarrollo web?",
                                "Definir la estructura y el contenido de una página web.",
                                "Programar la lógica de negocio de una aplicación web.",
                                "Crear estilos visuales para una página web.",
                                "Definir la estructura y el contenido de una página web.",
                                "Un lenguaje de marcado se utiliza para definir la estructura y el contenido de una página web."},

                        {"¿Cuál de las siguientes opciones es una plataforma de desarrollo de software de escritorio?",
                                "React Native",
                                "Angular",
                                "JavaFX",
                                "JavaFX",
                                "JavaFX es una plataforma de desarrollo de software de escritorio."},
                        {"¿Cuál de las siguientes plataformas de desarrollo de software se utiliza principalmente para la creación de aplicaciones web?",
                                "Angular",
                                "Android Studio",
                                "Eclipse",
                                "Angular",
                                "Angular es una plataforma de desarrollo de software ampliamente utilizada para la creación de aplicaciones web utilizando TypeScript."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software es más adecuada para el desarrollo de aplicaciones de escritorio?",
                                "Electron",
                                "Firebase",
                                "Django",
                                "Electron",
                                "Electron es una plataforma de desarrollo de software que permite crear aplicaciones de escritorio multiplataforma utilizando tecnologías web como HTML, CSS y JavaScript."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software es un entorno de desarrollo integrado (IDE) ampliamente utilizado para Java?",
                                "Eclipse",
                                "Visual Studio Code",
                                "PyCharm",
                                "Eclipse",
                                "Eclipse es un IDE ampliamente utilizado para el desarrollo de aplicaciones Java."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software se utiliza para crear aplicaciones móviles nativas en iOS?",
                                "Xcode",
                                "Android Studio",
                                "Visual Studio",
                                "Xcode",
                                "Xcode es la plataforma de desarrollo de software utilizada para crear aplicaciones móviles nativas en iOS utilizando Swift o Objective-C."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software es una base de datos NoSQL muy popular?",
                                "MongoDB",
                                "MySQL",
                                "Oracle",
                                "MongoDB",
                                "MongoDB es una plataforma de desarrollo de software y una base de datos NoSQL muy popular utilizada para el almacenamiento y gestión de datos no estructurados."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software es ampliamente utilizada para la creación de aplicaciones de realidad virtual (VR)?",
                                "Unity",
                                "WordPress",
                                "Salesforce",
                                "Unity",
                                "Unity es una plataforma de desarrollo de software ampliamente utilizada para la creación de aplicaciones de realidad virtual (VR) y videojuegos."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software utiliza el lenguaje de programación C#?",
                                "Visual Studio",
                                "Android Studio",
                                "NetBeans",
                                "Visual Studio",
                                "Visual Studio es una plataforma de desarrollo de software que admite múltiples lenguajes, incluido C# para el desarrollo de aplicaciones de Microsoft."},

                        {"¿Cuál de las siguientes plataformas de desarrollo de software es conocida por su enfoque en el desarrollo de aplicaciones web basadas en PHP?",
                                "Laravel",
                                "Ruby on Rails",
                                "Express.js",
                                "Laravel",
                                "Laravel es una plataforma de desarrollo de software conocida por su enfoque en el desarrollo de aplicaciones web basadas en PHP y su framework elegante y expresivo."}
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

        // Agrega onClickListeners a los botones de opción para manejar la selección de respuesta
        opcion1Button.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                try {
                    verificarRespuesta(0, retroalimentacion, respuestaCorrecta);
                }catch (Exception e){
                    Toast.makeText(PreguntasActivity3.this, "Error en indices", Toast.LENGTH_SHORT).show();
                }

            }


        });
        opcion2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                verificarRespuesta(1, retroalimentacion, respuestaCorrecta);
                }catch (Exception e){
                    Toast.makeText(PreguntasActivity3.this, "Error en indices", Toast.LENGTH_SHORT).show();
                }
            }
        });
        opcion3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                verificarRespuesta(2, retroalimentacion, respuestaCorrecta);
                    }catch (Exception e){
                        Toast.makeText(PreguntasActivity3.this, "Error en indices", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void verificarRespuesta(int opcionSeleccionada, String retroalimentacion, String respuestaCorrecta) {
        // Detener el cronómetro
        countDownTimer.cancel();



      if (opcionSeleccionada == 0 && opcion1Button.getText().equals(respuestaCorrecta)) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity3.this);
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

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity3.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity3.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PreguntasActivity3.this);
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
        Intent intent = new Intent(PreguntasActivity3.this, FinalActivity.class);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();

        finish(); // Destruye la actividad actual (ActivityB) y regresa a la anterior (ActivityA)
    }

}
