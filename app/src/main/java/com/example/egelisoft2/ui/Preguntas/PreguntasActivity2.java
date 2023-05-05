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
                        {"¿Qué es un módulo en el diseño de software?", "Un conjunto de componentes interconectados que realizan una función específica", "Un conjunto de datos organizados que se utilizan para almacenar información", "Un tipo de lenguaje de programación utilizado para desarrollar aplicaciones", "Un conjunto de componentes interconectados que realizan una función específica", "Un módulo es un elemento básico de la estructura modular de un sistema de software."},
                        {"¿Qué son los componentes en el diseño de software?", "Unidades funcionales independientes que se pueden conectar para formar módulos", "Archivos que contienen información estructurada en un formato específico", "Partes del sistema operativo que se encargan de la gestión de recursos del sistema", "Unidades funcionales independientes que se pueden conectar para formar módulos", "Los componentes son elementos básicos en la construcción de módulos y sistemas de software."},
                        {"¿Qué es la abstracción en el diseño de software?", "El proceso de ocultar detalles complejos y mostrar solo la información esencial", "El proceso de dividir un sistema en módulos independientes", "El proceso de diseñar componentes de software reutilizables", "El proceso de ocultar detalles complejos y mostrar solo la información esencial", "La abstracción es una técnica de diseño que permite a los programadores centrarse en los aspectos esenciales del sistema sin preocuparse por los detalles innecesarios."},
                        {"¿Qué es la cohesión en el diseño de software?", "La medida en que los componentes de un módulo están relacionados y trabajan juntos para lograr una tarea específica", "La medida en que los módulos de un sistema están interconectados y se comunican entre sí", "La medida en que los datos en un sistema están organizados y relacionados entre sí", "La medida en que los componentes de un módulo están relacionados y trabajan juntos para lograr una tarea específica", "La cohesión es un principio de diseño de software que se refiere a la relación entre los componentes de un módulo."},
                        {"¿Qué es el acoplamiento en el diseño de software?", "La medida en que los módulos de un sistema dependen entre sí", "La medida en que los datos en un sistema están organizados y relacionados entre sí", "La medida en que los componentes de un módulo están relacionados y trabajan juntos para lograr una tarea específica", "La medida en que los módulos de un sistema dependen entre sí", "El acoplamiento es un principio de diseño de software que se refiere a la interdependencia entre los módulos de un sistema."},
                        {"¿Cuál es el objetivo principal del diseño modular de software?", "Facilitar la comprensión y el mantenimiento del software", "Reducir el tamaño del software", "Mejorar la eficiencia del software", "Facilitar la comprensión y el mantenimiento del software", "El diseño modular ayuda a dividir el software en módulos más pequeños y manejables que pueden ser fácilmente comprendidos y mantenidos."},
                        {"¿Qué es un componente de software?", "Una unidad lógica y funcional de software", "Un programa completo", "Un conjunto de datos", "Una unidad lógica y funcional de software", "Un componente de software es una unidad lógica y funcional de software que puede ser utilizado en diferentes contextos y sistemas."},
                        {"¿Cuál es el objetivo del diseño de datos en un sistema de software?", "Definir y organizar la información necesaria para el software", "Mejorar el rendimiento del software", "Asegurar la compatibilidad del software", "Definir y organizar la información necesaria para el software", "El diseño de datos se enfoca en la definición y organización de la información necesaria para el software para que pueda ser almacenada y utilizada de manera efectiva."},
                        {"¿Qué es un diagrama de clases en el diseño de software orientado a objetos?", "Un diagrama que muestra la estructura de las clases y sus relaciones", "Un diagrama que muestra el flujo de control en un programa", "Un diagrama que muestra la distribución de componentes en un sistema", "Un diagrama que muestra la estructura de las clases y sus relaciones", "Un diagrama de clases es una herramienta de modelado que muestra la estructura de las clases en un sistema y cómo se relacionan entre sí."},
                        {"¿Cuál es el objetivo del diseño de interfaces de usuario?", "Crear interfaces intuitivas y fáciles de usar para el usuario", "Reducir el tamaño del software", "Mejorar la seguridad del software", "Crear interfaces intuitivas y fáciles de usar para el usuario", "El diseño de interfaces de usuario se enfoca en crear interfaces intuitivas y fáciles de usar para los usuarios finales del software."},
                        {"¿Qué es un patrón de diseño de software?", "Una solución reutilizable para un problema común en el diseño de software", "Un conjunto de reglas de codificación para un lenguaje de programación específico", "Un conjunto de requisitos para un proyecto de software", "Una solución reutilizable para un problema común en el diseño de software", "Los patrones de diseño son soluciones reutilizables para problemas comunes en el diseño de software que se han probado y documentado a lo largo del tiempo."},
                        {"¿Qué es un módulo de software?", "Una unidad funcional y ejecutable de software", "Un conjunto de clases relacionadas en un sistema", "Un conjunto de datos", "Una unidad funcional y ejecutable de software", "Un módulo de software es una unidad funcional y ejecutable de software que puede ser utilizado en diferentes contextos y sistemas."}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"¿Cuál es el propósito principal del diseño de interfaces?", "Mejorar la apariencia visual del software", "Mejorar la experiencia del usuario", "Mejorar la eficiencia del software", "Mejorar la experiencia del usuario", "El diseño de interfaces tiene como objetivo principal mejorar la experiencia del usuario al interactuar con el software."},

                        {"¿Cuál es el principio básico del diseño centrado en el usuario?", "Centrarse en la estética del diseño", "Centrarse en la funcionalidad del software", "Centrarse en las necesidades del usuario", "Centrarse en las necesidades del usuario", "El diseño centrado en el usuario se enfoca en comprender las necesidades, metas y tareas del usuario para diseñar una interfaz que sea fácil de usar y satisfaga sus requerimientos."},

                        {"¿Cuál es el objetivo principal del diseño responsivo?", "Mejorar la apariencia visual del software", "Mejorar la velocidad de carga del software", "Mejorar la experiencia del usuario en diferentes dispositivos", "Mejorar la experiencia del usuario en diferentes dispositivos", "El diseño responsivo se enfoca en crear interfaces que se adapten a diferentes tamaños de pantalla y dispositivos, proporcionando una experiencia de usuario consistente en cualquier dispositivo."},

                        {"¿Qué es el patrón de diseño 'Tarjeta'?", "Un patrón de diseño para la estructura de una base de datos", "Un patrón de diseño para el diseño de interfaces de usuario", "Un patrón de diseño para la arquitectura de software", "Un patrón de diseño para el diseño de interfaces de usuario", "El patrón de diseño 'Tarjeta' se refiere a la presentación de información en bloques separados y organizados, como una tarjeta en un tablero de Pinterest."},

                        {"¿Cuál es el propósito de los patrones de diseño?", "Establecer estándares de diseño para la industria", "Proporcionar soluciones probadas para problemas comunes de diseño", "Mejorar la estética de las interfaces de usuario", "Proporcionar soluciones probadas para problemas comunes de diseño", "Los patrones de diseño son soluciones probadas para problemas comunes de diseño que se han desarrollado a lo largo del tiempo y se han establecido como buenas prácticas en la industria."},

                        {"¿Qué es la heurística de usabilidad?", "Una técnica de diseño para la creación de interfaces de usuario", "Un conjunto de principios de diseño centrado en el usuario", "Un método para evaluar la usabilidad de una interfaz de usuario", "Un método para evaluar la usabilidad de una interfaz de usuario", "La heurística de usabilidad es un conjunto de principios que se utilizan para evaluar la calidad de una interfaz de usuario, centrándose en la facilidad de uso, la eficiencia y la satisfacción del usuario."},
                        {"¿Cuál es el objetivo principal del diseño centrado en el usuario en la interfaz de usuario?",
                                "Mejorar la usabilidad de la interfaz de usuario",
                                "Hacer la interfaz de usuario más atractiva",
                                "Aumentar la velocidad de procesamiento de la interfaz de usuario",
                                "Mejorar la usabilidad de la interfaz de usuario se logra al enfocarse en las necesidades y deseos de los usuarios finales durante todo el proceso de diseño de la interfaz."},

                        {"¿Qué es un wireframe en el diseño de interfaces de usuario?",
                                "Un esquema de la disposición de los elementos de la interfaz de usuario",
                                "Un modelo en 3D de la interfaz de usuario",
                                "Una herramienta para probar la velocidad de procesamiento de la interfaz de usuario",
                                "Un wireframe es un esquema de la disposición de los elementos de la interfaz de usuario, que ayuda a visualizar y organizar los componentes de la interfaz antes de crear una versión más detallada."},

                        {"¿Cuál es el propósito del diseño visual en la interfaz de usuario?",
                                "Hacer la interfaz de usuario atractiva y fácil de usar",
                                "Crear la estructura de la interfaz de usuario",
                                "Determinar los requisitos de la interfaz de usuario",
                                "El propósito del diseño visual en la interfaz de usuario es hacerla más atractiva y fácil de usar para los usuarios finales."},
                        {"¿Qué técnica de diseño de interfaces utiliza la creación de modelos visuales de los elementos de la interfaz?",
                                "Diseño de prototipos", "Diagramación", "Wireframing", "Wireframing es una técnica de diseño de interfaces que utiliza la creación de modelos visuales de los elementos de la interfaz para facilitar su diseño y evaluación."},

                        {"¿Qué elemento de diseño de interfaces se refiere al uso de la misma forma o estilo visual en todo el sistema?",
                                "Consistencia", "Legibilidad", "Alineación", "La consistencia en el diseño de interfaces se refiere al uso de la misma forma o estilo visual en todo el sistema para crear una experiencia de usuario coherente."},

                        {"¿Cuál de los siguientes NO es un principio de diseño de interfaces?",
                                "Innovación", "Usabilidad", "Accesibilidad", "Innovación no es un principio de diseño de interfaces, aunque puede ser una parte importante del proceso de diseño en general."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la forma en que los elementos visuales se organizan en la pantalla?",
                                "Composición", "Espaciado", "Proporción", "La composición en el diseño de interfaces se refiere a la forma en que los elementos visuales se organizan en la pantalla para crear una estructura visual coherente y atractiva."},

                        {"¿Qué principio de diseño de interfaces se refiere a la capacidad del diseño para ser comprensible y fácil de aprender para el usuario?",
                                "Usabilidad", "Atractividad", "Accesibilidad", "La usabilidad en el diseño de interfaces se refiere a la capacidad del diseño para ser comprensible y fácil de aprender para el usuario, para que pueda realizar sus tareas de manera eficiente y efectiva."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la relación visual entre los elementos en la pantalla?",
                                "Espaciado", "Proporción", "Composición", "El espaciado en el diseño de interfaces se refiere a la relación visual entre los elementos en la pantalla, y puede afectar la legibilidad y el equilibrio visual del diseño."},

                        {"¿Qué técnica de diseño de interfaces utiliza la prueba del diseño con usuarios reales?",
                                "Pruebas de usabilidad", "Wireframing", "Diagramación", "Las pruebas de usabilidad son una técnica de diseño de interfaces que utiliza la prueba del diseño con usuarios reales para evaluar la eficacia y la eficiencia del diseño."},

                        {"¿Cuál de las siguientes NO es una regla de diseño de interfaces según las normas ISO?",
                                "Uso de fuentes complejas", "Consistencia", "Legibilidad", "El uso de fuentes complejas no es una regla de diseño de interfaces según las normas ISO, ya que esto puede dificultar la legibilidad del diseño."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la capacidad del diseño para llamar la atención del usuario?",
                                "Atractividad", "Usabilidad", "Accesibilidad", "La atractividad en el diseño de interfaces se refiere a la capacidad del diseño para llamar la atención del usuario y crear una experiencia visualmente atractiva."},
                        {"¿Qué es la usabilidad en el diseño de interfaces?", "La capacidad de la interfaz para permitir al usuario realizar tareas con eficiencia y satisfacción", "El aspecto visual de la interfaz", "La capacidad de la interfaz para ser compatible con diferentes sistemas operativos", "La capacidad de la interfaz para permitir al usuario realizar tareas con eficiencia y satisfacción", "La usabilidad se enfoca en la facilidad de uso y la experiencia del usuario al interactuar con la interfaz."},

                        {"¿Qué es la accesibilidad en el diseño de interfaces?", "La capacidad de la interfaz para ser compatible con diferentes sistemas operativos", "La capacidad de la interfaz para ser utilizada por personas con discapacidades", "La capacidad de la interfaz para funcionar en diferentes idiomas", "La capacidad de la interfaz para ser utilizada por personas con discapacidades", "La accesibilidad se enfoca en hacer que la interfaz sea utilizable por personas con discapacidades y necesidades especiales."},

                        {"¿Cuál es el propósito de los wireframes en el diseño de interfaces?", "Visualizar la estructura y el contenido de la interfaz", "Elegir colores y fuentes para la interfaz", "Crear una versión interactiva de la interfaz", "Visualizar la estructura y el contenido de la interfaz", "Los wireframes son una herramienta para planificar la estructura y el contenido de la interfaz antes de diseñarla visualmente."},

                        {"¿Qué es un prototipo en el diseño de interfaces?", "Una versión interactiva de la interfaz que simula su funcionamiento", "Una imagen estática de la interfaz final", "Un boceto a lápiz de la interfaz", "Una versión interactiva de la interfaz que simula su funcionamiento", "Los prototipos son una forma de probar y validar el diseño de la interfaz antes de su implementación final."},

                        {"¿Cuál es el propósito de las pruebas de usabilidad en el diseño de interfaces?", "Evaluar la eficiencia y satisfacción del usuario al interactuar con la interfaz", "Evaluar la calidad del código utilizado en la implementación de la interfaz", "Evaluar la compatibilidad de la interfaz con diferentes sistemas operativos", "Evaluar la eficiencia y satisfacción del usuario al interactuar con la interfaz", "Las pruebas de usabilidad permiten detectar problemas y mejorar la experiencia del usuario al interactuar con la interfaz."},

                        {"¿Qué es el diseño responsivo en el diseño de interfaces?", "El diseño de la interfaz para que se adapte a diferentes tamaños de pantalla y dispositivos", "El diseño de la interfaz para que sea compatible con diferentes navegadores web", "El diseño de la interfaz para que se vea bien en pantallas de alta resolución", "El diseño de la interfaz para que se adapte a diferentes tamaños de pantalla y dispositivos", "El diseño responsivo es una técnica de diseño que permite que la interfaz se adapte a diferentes tamaños de pantalla y dispositivos."},

                        {"¿Cuál es el principio de diseño que sugiere que las partes de una interfaz deben estar organizadas en grupos coherentes?", "Proximidad", "Equilibrio", "Alineación", "Proximidad", "La proximidad sugiere que los elementos que están relacionados deben ser colocados cerca uno del otro en una interfaz para que sea fácil de entender."},
                        {"¿Qué es el uso de los patrones de diseño de interfaz de usuario?", "Mejorar la eficiencia del diseño de interfaz", "Mejorar la calidad del código", "Mejorar la seguridad del sistema", "Mejorar la eficiencia del diseño de interfaz", "Los patrones de diseño de interfaz de usuario proporcionan soluciones predefinidas a problemas comunes en el diseño de interfaz."},
                        {"¿Qué patrón de diseño se utiliza para agrupar componentes relacionados en una sola ventana de la interfaz?", "Patrón de pestañas", "Patrón de árbol", "Patrón de lista", "Patrón de pestañas", "El patrón de pestañas es utilizado para agrupar elementos relacionados en una sola ventana de la interfaz."},
                        {"¿Qué patrón de diseño se utiliza para organizar los elementos de la interfaz en una estructura jerárquica?", "Patrón de árbol", "Patrón de pestañas", "Patrón de lista", "Patrón de árbol", "El patrón de árbol se utiliza para organizar los elementos de la interfaz en una estructura jerárquica."},
                        {"¿Qué principio de diseño sugiere que los objetos en una interfaz deben ser lo suficientemente grandes como para ser fáciles de seleccionar?", "Tamaño objetivo", "Alineación", "Equilibrio", "Tamaño objetivo", "El tamaño objetivo sugiere que los objetos en una interfaz deben ser lo suficientemente grandes como para ser fáciles de seleccionar."},
                        {"¿Qué principio de diseño sugiere que la interfaz debe ser fácil de entender para los usuarios?", "Simplicidad", "Complejidad", "Claridad", "Simplicidad", "El principio de simplicidad sugiere que la interfaz debe ser fácil de entender para los usuarios."},
                        {"¿Qué es el contraste en el diseño de interfaces?", "La diferencia entre los elementos de la interfaz", "La similitud entre los elementos de la interfaz", "El equilibrio entre los elementos de la interfaz", "La diferencia entre los elementos de la interfaz", "El contraste en el diseño de interfaces se refiere a la diferencia entre los elementos de la interfaz."},
                        {"¿Qué es el equilibrio en el diseño de interfaces?", "Distribución equitativa de los elementos de la interfaz", "Simetría de los elementos de la interfaz", "Tamaño uniforme de los elementos de la interfaz", "Distribución equitativa de los elementos de la interfaz", "El equilibrio en el diseño de interfaces se refiere a la distribución equitativa de los elementos de la interfaz."}
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
