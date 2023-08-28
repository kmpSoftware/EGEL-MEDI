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
    private static final long TIEMPO_TOTAL = 4000;
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

                        {"Una paciente de 68 años presenta un cuadro de tos con expectoración blanquecina y ortopnea. Menciona que el día de hoy se agudizan los síntomas y presenta disnea. Reside en una comunidad rural y vive con cuatro familiares más. Su casa habitación cuenta con dos cuartos, piso de tierra, techo de lámina, autoconsumo y cocina con leña. Durante la exploración física, se registran los siguientes valores: TA 120/80 mmHg, FC de 78 lpm, FR 22 lpm, T 36.9°C y saturación de oxígeno (sato2) de 98%. ¿Cuál es el factor del entorno de la paciente que se asocia con su problema de salud?",
                                "Exposición a biomasa",
                                "Hacinamiento",
                                "Zoonosis",
                                "Exposición a biomasa",
                                "La exposición a biomasa, como la cocina con leña, puede estar asociada con los síntomas respiratorios que la paciente presenta, como la tos y la disnea. Es importante evaluar y abordar este factor del entorno para mejorar su salud."},
                        { "En una población rural aislada en la sierra sur de Oaxaca, cuyo nivel educativo es bajo, hay desnutrición, es de difícil acceso al primer nivel de atención en salud y con baja cobertura de programas de prevención y vacunación, se presenta un brote de hepatitis A. El origen de éste se ubica en la escuela primaria, donde se comparte un tambo de agua para el lavado de manos, lo que permite que se propague posteriormente por medio del contacto familiar.",
                                "Bajo nivel educacional",
                                "Servicios básicos de sanidad ineficientes",
                                "Difícil acceso al sistema de salud",
                                "Difícil acceso al sistema de salud",
                                "El factor condicionante ligado a la ineficiente contención en la propagación de casos es el difícil acceso al sistema de salud. La población rural aislada en la sierra sur de Oaxaca enfrenta dificultades para acceder al primer nivel de atención en salud, lo que dificulta el control y manejo adecuado del brote de hepatitis A. La falta de acceso a servicios de salud y programas de prevención y vacunación adecuados puede llevar a una mayor propagación de la enfermedad, especialmente cuando hay condiciones de higiene precarias en la comunidad, como compartir un tambo de agua para el lavado de manos en la escuela primaria."
                        },
                        { "Una pareja de 22 años de edad habita en un medio rural, son campesinos con estudios de primaria incompleta y viven con sus cuatro hijos en una sola habitación. Tres de los niños se encuentran sanos, mientras que el paciente de 3 años, que cuenta con esquema de vacunación incompleto, presenta infecciones respiratorias de repetición y gastroenteritis. Los padres llevan a consulta al niño de 3 años, ya que presenta vómito y diarrea con 5 días de evolución. Ellos mencionan que el menor ha presentado seis evacuaciones esteatorréicas al día y vómito de contenido gastroalimentario dos o tres veces al día. Durante la EF se encontró peso de 10.5 kg, talla de 88 cm, FC de 65/min, FR de 18/min, T de 36.6 ºC, SaO2 de 93% y TA de 90/60 mmHg.",
                                "Ocupación de los padres e infecciones respiratorias de repetición",
                                "Número de hermanos y probable cardiopatía congénita",
                                "Contexto socioeconómico familiar y enfermedades gastrointestinales",
                                "Ocupación de los padres e infecciones respiratorias de repetición",
                                "Los factores de riesgo para el desarrollo del estado nutricional alterado del paciente son la ocupación de los padres, que son campesinos con estudios de primaria incompleta y el antecedente de infecciones respiratorias de repetición. La ocupación de los padres puede influir en la disponibilidad y acceso a una alimentación adecuada para el niño. Además, las infecciones respiratorias de repetición pueden afectar la ingesta y absorción de nutrientes, lo que puede contribuir al estado nutricional alterado del paciente. Es importante considerar estos factores en la evaluación y manejo del niño con problemas gastrointestinales y nutricionales."
                        },
                        { "Un lactante de 13 meses es llevado a consulta por presentar fiebre intermitente de 38 °C en las últimas 24 horas que cede con antipiréticos, además presenta rinorrea hialina abundante y los seca progresiva en accesos. En la exploración física se encuentra hidratado, afebril, aunque con dificultad respiratoria manifestada por tiraje intercostal leve y retracción xifoidea también leve, se auscultan estertores crepitantes finos y sibilancias al final de la espiración, por lo que se sospecha de bronquiolitis.",
                                "Neumonía",
                                "Laringotraqueobronquitis",
                                "Crisis asmática",
                                "Neumonía",
                                "El diagnóstico diferencial del cuadro se inclina hacia la neumonía. Aunque la bronquiolitis es una sospecha inicial debido a la presencia de sibilancias y dificultad respiratoria, la fiebre intermitente y la abundante rinorrea hialina también pueden ser indicativos de neumonía. La presencia de estertores crepitantes finos puede sugerir una consolidación pulmonar, lo que refuerza la posibilidad de neumonía como causa del cuadro clínico en este lactante de 13 meses."
                        },            {"En una localidad de 10,000 habitantes se realizó un estudio para conocer la  prevalencia de insuficiencia renal y sus factores asociados en personas o más. Se recolectó información sobre variables sociodemográficas, sexo, edad, estado civil y nivel socioeconómico. Si en la descripción estadística se representa la distribución por edad, se midió en 'años cumplidos', se puede utilizar el grafico…",
                        "Diagrama de dispersión",
                        "Barras simples",
                        "Polígono de frecuencia",
                        "Polígono de frecuencia",
                        "Polígono de frecuencia"},
                        {"Una paciente de 31 años acude al servicio de Urgencias por presentar odinofagia, cefalea y ataque al estado general, además de fiebre no cuantificada de 2 días de evolución entre sus antecedentes comenta que su abuela es diabética, menciona tener alergia a AINE y aumento de peso desde hace 2 años atribuido al sedentarismo. En la exploración física se registra TA de 130/85 mmHg: FC de 80/min, FR de 18/min, T de 37 5 °C IMC 30.3 kg/m2 y circunferencia abdominal de 90 cm. Se observa hiperemia conjuntival, labios y mucosa oral secos, faringe y amígdalas hiperémicas, criptas amigdalinas con secreción purulenta y se palpan ganglios submaxilares bilaterales. En los resultados de laboratorio se reportan leucocitos de 10.500/mm, neutrófilos de 62%, hemoglobina de 12.7 mg/dL, hematocrito de 37.2%, glucosa de 100 mg/dL, HbA1c de 6%, colesterol total de 181 mg/dL, HDL de 48 mg/dL, LDL de 120 mg/dL y triglicéridos de 187 mg/dL. Se inicia manejo para cuadro de faringoamigdalitis y se indican medidas higiénico-dietéticas de actividad física y metformina.",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                                "Monitorear cada 5 años la progresión metabólica de la glucosa para revalorar riesgos",
                                "Establecer un objetivo de peso y considerar terapia dual para control de glucosa",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso"},
                        {
                                "Un grupo de estudiantes de medicina lleva a cabo una investigación con la que pretende evaluar la prevalencia de tabaquismo en una universidad pública. Hasta este momento, han realizado el protocolo, que presentaron ante el comité de ética en investigación, aplicaron los instrumentos para la recolección de datos y comenzaron el análisis estadístico. Dado el avance de trabajo. ¿Cuál es el tipo de reporte de investigación por presentar?",
                                "Cartel",
                                "Ponencia",
                                "Artículo",
                                "Artículo",
                                "Artículo"
                        },
                        {
                                "En el Servicio de Neurología de un hospital de segundo nivel de atención, se ha observado un aumento de la consulta de pacientes con migraña Crónica. El reporte del último semestre demuestra que cada mes se diagnostican por lo menos 50 nuevos casos. La edad oscila entre 35 y 50 años. El medicamento que se utiliza como profilaxis para evitar los episodios migrañosos es un anticonvulsivante llamado topiramato. la enfermedad tanto a nivel individual como poblacional Para sustentar la decisión del uso del fármaco en la investigación basada en evidencia, ¿qué estrategia debe seguirse?",
                                "Revisión sistemática en Cochrane de diez estudios controlados aleatorizados, con 100 pacientes ",
                                " Análisis de 20 artículos de revistas indexadas y diez libros de texto especializados en neurología ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales "
                        },
                        {
                                "Una mujer de 45 años acude a consulta debido a un cuadro de 1 mes de evolución caracterizado por polidipsia, poliuria y pérdida de peso. Manifiesta antecedentes de diabetes mellitus en padre y madre y tíos maternos. En la exploración física se encuentran acantosis nigricans en el cuello y un IMC de 29 kg/m2. La sospecha diagnóstica es diabetes mellitus. Se le toma glucosa al azar y se obtiene un resultado de 200 mg/dL. De acuerdo con la evidencia médica que existe, ¿cuál es el criterio para confirmar el diagnóstico de la paciente? ",
                                "Glucosa en ayuno superior a 100 mg/dL",
                                "Hemoglobina glucosilada mayor a 6.5%",
                                " Curva de tolerancia a la glucosa de 2 horas mayor o igual a 140 mg/dl",
                                "Hemoglobina glucosilada mayor a 6.5%",
                                "Hemoglobina glucosilada mayor a 6.5%"
                        },
                        {
                                "Una paciente de 40 años, con IMC de 35 kg/m2, acude a consulta para solicitar un análisis general a fin de atender su problema de obesidad. Comenta que hace 4 semanas acudió a su centro de salud y fue remitida al Servicio de Nutrición, donde le aplicaron un protocolo consistente en cambios a su estilo de vida. En la misma consulta le realizaron un perfil lipídico, una medición de glucosa y un perfil tiroideo, y los resultados fueron normales. Como ya pasó 1 mes y no perdió peso, la paciente pide que se le vuelvan a hacer todos los análisis para solucionar su problema. Atendiendo al principio de justicia, ¿cómo se debe proceder?",
                                "Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve",
                                "Respetando la decisión de la paciente, se le debe repetir el análisis",
                                "con el objetivo de analizar detalladamente su padecimiento, se deben agregar los mismos estudios",
                                "Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve",
                                "Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve"
                        },
                        {
                                "En una comunidad, una paciente de 34 años acude por presentar dolor abdominal agudo, intenso y persistente localizado en el epigastrio, el hipocondrio izquierdo y el derecho, que se irradia a la espalda. El dolor se acompaña de nausea y vómito que ha continuado hasta el momento. En la EF se encuentra diaforética, TA de 130/90 mm/Hg FC de 98/min, FR de 22/min y el abdomen doloroso a la palpación en toda la porción superior sin rigidez ni rebote. La sospecha diagnostica es pancreatitis. La paciente no es derechohabiente de ninguna institución y no Cuenta con los recursos para recibir atención médica privada. De acuerdo con el sistema de salud en México dirigido a las personas sin seguridad social, ¿a qué unidad se debe referir a la paciente?",
                                "Primer nivel de atención",
                                "Segundo nivel de atención",
                                "Tercer nivel de atención",
                                "Segundo nivel de atención",
                                "Segundo nivel de atención"
                        },
                        {
                                "El calentamiento global ha desencadenado una serie de cambios en el medio ambiente que repercuten en la aparición de enfermedades infecciosas fuera de sus zonas endémicas tradicionales, lo cual dificulta la prevención de padecimientos como el dengue, el chikungunya o la fiebre amarilla. Además de estas enfermedades, ¿qué otra repercusión ha tenido el cambio climático en la salud global?",
                                "Reemergencia de enfermedades por el cambio en las temperaturas",
                                "Alteración de la alimentación por cambios en la producción alimentaria",
                                "Transmisión de enfermedades por vector debido al aumento del tránsito entre países",
                                "Reemergencia de enfermedades por el cambio en las temperaturas",
                                "Reemergencia de enfermedades por el cambio en las temperaturas"
                        },
                        {
                                "Un paciente de 16 años acude a consulta de control anual. Entre sus antecedentes familiares se menciona a ambos abuelos finados por complicaciones cardíacas, su madre y abuela materna padecen hipertensión arterial y su padre, diabetes mellitus. En la exploración física, se registra peso de 68 kg, talla de 1.67 m y un IMC de 24.4 kg/m2, el resto de la exploración por aparatos y sistemas se encuentra sin alteraciones. ¿Qué recomendación de actividad física se debe indicar para promover un estilo de vida saludable?",
                                "60 minutos al día de ejercicio aeróbico de intensidad moderada",
                                "Entre 75 y 150 minutos al día de ejercicio aeróbico de alta intensidad",
                                "180 minutos al día de actividad física diversa de intensidad variable",
                                "60 minutos al día de ejercicio aeróbico de intensidad moderada",
                                "60 minutos al día de ejercicio aeróbico de intensidad moderada"
                        },
                        {
                                "Un joven de 14 años es llevado a consulta por dolor en el muslo izquierdo desde hace 2 meses, el cual tiene predominio nocturno y en ocasiones lo imposibilita dormir, además, indica pérdida de peso de 4 kg. Tiene antecedente de padre con retinoblastoma de niño. En la exploración física se observa aumento de volumen en el muslo izquierdo, se palpa tumoración inmóvil e indolora, aparentemente fija a planos profundos, sin alteraciones tegumentarias visibles. De acuerdo con el cuadro actual, ¿qué acción de prevención secundaria debe llevarse a cabo?",
                                "Realizar ultrasonido",
                                "Solicitar radiografía",
                                "Programar biopsia",
                                "Realizar ultrasonido",
                                "Realizar ultrasonido"
                        },
                        {
                                "En una comunidad rural se desbordó el río. Lo que provocó un gran número de damnificados, heridos y muertos, así como daños a la infraestructura de servicios básicos y de vivienda. El personal de salud ha iniciado los trabajos de atención. ¿Cuál es la acción prioritaria de salud ambiental en esta situación de desastre?",
                                "Asegurar el acceso y el abastecimiento de agua para consumo humano",
                                "Asegurar el suministro de equipo y materiales para la atención de heridos",
                                "identificar los daños estructurales en las viviendas de los damnificados",
                                "Asegurar el acceso y el abastecimiento de agua para consumo humano",
                                "Asegurar el acceso y el abastecimiento de agua para consumo humano"
                        },
                        {
                                "Un hombre de 67 años tiene cansancio y dificultad para concentrarse. Durante el interrogatorio menciona que su alimentación no ha sido completa ni balanceada en el último mes y que dormido 5 horas diarias, pues ha trabajado más de normal para dejar todo en orden antes de jubilarse. ¿Qué medida preventiva se debe indicar para favorecer la salud mental del paciente?",
                                "Solicitar interconsulta con Psicología para tratar el insomnio secundario",
                                "Sugerir que en su estilo de vida incluya actividades físicas y recreativas",
                                "Iniciar tratamiento con complejo B para compensar la alimentación deficiente",
                                "Sugerir que en su estilo de vida incluya actividades físicas y recreativas",
                                "Sugerir que en su estilo de vida incluya actividades físicas y recreativas"
                        },
                        {
                                "Una mujer de 28 años, G2, A1, con 20 SDG por USG, acude a su consulta de control prenatal. Actualmente se observa asintomática. En la EF se registra FC de 90/min, FR de 18/min, TA de 110/80 mmHg, altura uterina de 16 cm, asimismo cérvix cerrado y formado. ¿Cuál es el esquema de vacunación indicado para la paciente?",
                                "Vacuna Td en este momento",
                                "Vacuna Td después del embarazo",
                                "Vacuna Tdpa en este momento",
                                "Vacuna Tdpa en este momento",
                                "Vacuna Tdpa en este momento"
                        },
                        {
                                "En una empresa de servicios se incrementa el ausentismo entre los trabajadores y se reciben diferentes quejas por parte de los usuarios sobre conductas y actitudes inadecuadas y poco empáticas de los empleados. Se analizan los certificados de incapacidad y se detecta que las enfermedades generales registradas incluyen cefalea tensional, migraña, lumbalgia y crisis hipertensivas. ¿Qué acción se debe implementar para atender los riesgos a la salud descritos?",
                                "Programar sesiones grupales guiadas para reiterar los valores de la empresa que incentiven el compromiso de los empleados",
                                "Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física",
                                "Difundir entre todos los empleados las acciones incluidas en la política de prevención de riesgos psicosociales",
                                "Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física",
                                "Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física"
                        },{
                        "Un hombre de 58 años presenta pirosis, tos regurgitaciones desde hace 1 mes. En el interrogatorio menciona un alto consumo de carbohidratos y grasas Saturadas, tabaquismo positivo a razón de ocho cigarrillos diarios desde hace 15 años, consumo de alcohol una vez a la semana hasta llegar a la embriaguez y sedentarismo. En la exploración física se registra IMC de 30.4 kg/m2, abdomen globoso a expensas de panículo adiposo, doloroso a la palpación media en epigastrio y marco cólico. ¿Qué medida debe indicarse al paciente para disminuir el riesgo de padecer cáncer esofágico?",
                        "Iniciar tratamiento con antiácidos y alginatos",
                        "Evitar el consumo de tabaco y bebidas alcohólicas",
                        "Realizar una endoscopia para descartar esófago de Barret",
                        "Evitar el consumo de tabaco y bebidas alcohólicas",
                        "Evitar el consumo de tabaco y bebidas alcohólicas"
                },
                        {
                                "Un paciente de 45 años de edad acude a consulta por presentar nicturia y tenesmo vesical de 1 mes de evolución. Tiene antecedentes heredofamiliares de cáncer de próstata en su padre y tío paterno, indica tabaquismo positivo con índice tabáquico de 25 paquetes/año, alcoholismo y litiasis vesicular. ¿Cuál es la prueba de tamizaje prioritaria para la detección oportuna de cáncer de próstata en este paciente?",
                                "Ultrasonido prostático",
                                "Biopsia transrectal",
                                "Antígeno prostático específico",
                                "Antígeno prostático específico",
                                "Antígeno prostático específico"
                        },
                        {
                                "Una paciente de 30 años con antecedente familiar de primer grado con cáncer de mama acude a consulta por dolor cíclico en ambas mamas Indica que se le colocaron implantes mamarios con fines estéticos a los 26 años de edad. En la exploración física se registran signos vitales dentro de los parámetros normales, mama derecha sin alteraciones, implantes mamarios íntegros y no se logra palpar nódulo o tumoración. ¿Qué medida de autocuidado se debe recomendar?",
                                "Retirar implantes mamarios",
                                "Evitar consumo de folatos",
                                "Realizar autoexploración mamaria",
                                "Realizar autoexploración mamaria",
                                "Realizar autoexploración mamaria"
                        },
                        {
                                "En una comunidad rural hubo casos de dengue el año pasado, por lo que este año, en el centro de salud y con apoyo de la presidencia municipal, se realizó un programa educativo de prevención y promoción dirigido a los pobladores, con los siguientes objetivos: • Dar a conocer la enfermedad y sus complicaciones por medio de pláticas informativas • Realizar descacharrización en las zonas con mayor riesgo • Usar larvicidas en zonas con menos riesgo • Uso de mosquiteros en puertas y ventanas Después de aplicar el programa, se presentaron casos de dengue. ¿Qué se requiere modificar para restaurar la salud de la población?",
                                "Cloración de agua para consumo humano",
                                "Uso de malatión en zonas con mayor riesgo",
                                "Participación de la comunidad para limpieza a corto plazo",
                                "Participación de la comunidad para limpieza a corto plazo",
                                "Participación de la comunidad para limpieza a corto plazo"
                        },
                        {
                                "Paciente femenino de 32 años acude por cefalea opresiva en región occipital de 5 días de evolución acompañada de acúfenos en algunas ocasiones, mareos, astenia y adinamia. Desconoce antecedentes personales de enfermedades crónicas y niega ingesta de medicamentos de manera habitual. Tiene padres con diabetes mellitus tipo 2 e hipertensión arterial. Antecedentes personales no patológicos, con dieta a base de harinas procesadas y carbohidratos refinados, sedentarismo, tabaquismo positivo, a razón de cuatro cigarrillos al día y alcoholismo de patrón social. Las alteraciones halladas en la exploración física son: IMC de 34.2 kg/m2, TA de 139/79 mmHg y glucosa postpandrial de 126 mg/dl. ¿Cuáles son los cambios necesarios en el estilo de vida de la paciente para restaurar su salud?",
                                "Actividad física de moderada a intensa, supervisada medicamente, de 3 a 5 días por semana, restricción de carbohidratos de la dieta para reducción de peso corporal; consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina del peso corporal, Consumo de sodio menor a 1.8g al día y consumo de proteínas menos de 0.6 g/kg/día",
                                "Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día."
                        },
                        {
                                "En una comunidad rural ha aumentado tres veces la incidencia de infecciones de transmisión sexual en mujeres en los últimos 6 meses. Debido a la escasez de recursos y medicamentos, el médico decide pedir apoyo a la unidad médica más cercana, a la que le solicita preservativos, antibióticos y material para realizar el papanicolaou. ¿Qué intervención favorece la protección de la salud sexual de la comunidad?",
                                "Capacitar para el uso de preservativos",
                                "Instruir acerca del consumo de antibióticos",
                                "Informar sobre el procedimiento del papanicolaou",
                                "Capacitar para el uso de preservativos",
                                "Capacitar para el uso de preservativos"
                        },
                        {
                                "Se detecta que un grupo de 25 personas del personal de salud que se recuperó de la covid-19 requiere orientación para manejo de tensión emocional al reintegrarse a labores y percibir el alto riesgo de volver a infectarse. ¿Cuál es la intervención educativa sobre el manejo del estrés que se debe implementar?",
                                "Diplomado presencial de 120 horas con apoyo de un tutor",
                                "Taller virtual autoadministrado de 120 horas con apoyo de la plataforma hospitalaria",
                                "Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública",
                                "Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública",
                                "Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública"
                        },
                        {
                                "En un municipio, se realizó el diagnóstico situacional de salud en 2020, en el que se reportaron 473 personas de 65 años y más, con una proyección de aumento de 55% para 2025. Por este incremento, el centro de salud decide implementar intervenciones educativas para favorecer la protección de las personas de la tercera edad. ¿Cuál de las siguientes intervenciones favorece la protección para el síndrome de fragilidad de esta población?",
                                "Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado",
                                "lncluir a los familiares o cuidadores en la hora de comer para socializar y apoyar, fomentar ejercicios de resistencia 30-45 minutos por día, mínimo tres veces por semana, asistir al centro de salud para valoración médica y para determinar niveles séricos de vitamina D",
                                "Realizar actividades de recreación grupales o individuales como taichí o bailar, ejercitarse regularmente con pesas, previa valoración médica, incluir en la dieta alimentos ricos en Ca y proteína e instalar pasamanos en baños, pasillos y escaleras.",
                                "Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado",
                                "Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado"
                        },
                        {
                                "En una comunidad rural de Campeche se registra una tasa de mortalidad materna cercana a 20% y se identifica que las tres principales causas incluyen enfermedades preexistentes o surgidas durante el embarazo, infecciones y hemorragias posparto. En la comunidad es muy recurrente que las mujeres prefieran que sus partos sean atendidos por matronas porque, aunque hay una clínica cercana, el hospital se encuentra a 1 hora de distancia. ¿Qué aspecto se debe enfatizar en todas las intervenciones de educación para la salud que se implementen en la comunidad?",
                                "Recibir atención prenatal durante el embarazo",
                                "Promover la atención profesional del parto",
                                "Aumentar las medidas de higiene durante el parto",
                                "Recibir atención prenatal durante el embarazo",
                                "Recibir atención prenatal durante el embarazo"
                        },
                        {
                                "En una secundaria, se detecta un aumento de las infecciones por transmisión sexual en un grupo de adolescentes de entre 12 y 15 años. Identifique la acción educativa por implementar",
                                "Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales",
                                "Promover la abstinencia y el reconocimiento de enfermedades de transmisión sexual",
                                "Informar sobre sexualidad de forma diferenciada a grupos de hombres y mujeres",
                                "Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales",
                                "Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales"
                        },
                        {
                                "Paciente masculino de 48 años acude a consulta por náusea y vómito por ingesta de destilados en cantidad abundante la noche previa. Niega antecedentes de enfermedades crónicas y alergias, tabaquismo positivo a razón de cinco a siete cigarros al día: Al interrogar más a fondo, se detecta que desde hace 1 mes aumentó la ingesta de bebidas alcohólicas hasta hacerlo casi diariamente, incluso solo en casa, llegando a la embriaguez de manera ocasional. El paciente menciona que ha presentado retardos recurrentes en su trabajo por las molestias en la mañana y que en la última semana lo han amonestado por esta situación. ¿Cuál de las siguientes opciones de prevención y educación en salud se recomienda en este caso?",
                                "Disminuir el consumo de bebidas alcohólicas hasta suspenderlas por completo",
                                "Remitir a consulta de psiquiatría o a una clínica de control de adicciones",
                                "Iniciar tratamiento con una benzodiacepina de acción corta y analgésicos",
                                "Remitir a consulta de psiquiatría o a una clínica de control de adicciones",
                                "Remitir a consulta de psiquiatría o a una clínica de control de adicciones"
                        },
                        {
                                "Una familia de una comunidad rural acude a consulta porque sus dos hijos presentaron, con un par de días de diferencia, pero de manera súbita, odinofagia, disfagia, dolor abdominal y evacuaciones diarreicas, durante la exploración física, en uno se registra fiebre de 38°c y en ambos se observa ictericia. La madre menciona que su vivienda es de adobe con dos cuartos y en ella viven con los padres de su esposo, tienen aves de corral y la fuente de agua en la comunidad es un pozo común. ¿Qué aspecto del contexto de los pacientes se asocia con su padecimiento?",
                                "Vivienda con hacinamiento",
                                "Fuente de agua potable",
                                "Contacto con aves de corral",
                                "Fuente de agua potable",
                                "Fuente de agua potable"
                        },
                        {
                                "En un municipio se reporta que las infecciones respiratorias agudas fueron la primera causa de morbilidad en todos los grupos de edad durante los meses de más bajas temperaturas en el último año. Se identifica que la influenza representa la mayor frecuenta, por la que niños y adultos mayores acudieron a los servicios de salud y alrededor de 40% presentó cuadro moderado o grave. ¿Qué necesidad sanitaria se debe atender?",
                                "Promoción de las medidas de higiene",
                                "Aislamiento domiciliario de nuevos casos",
                                "Monitoreo de la cobertura de vacunación",
                                "Monitoreo de la cobertura de vacunación",
                                "Monitoreo de la cobertura de vacunación"
                        },
                        {
                                "En la comunidad rural se reporta un brote de diarrea aguda. Las evacuaciones son acuosas y blanquecinas, y se acompañan de nausea y vomito. ¿Cuál es el recurso con el que se cuenta en la comunidad para controlar el brote de la enfermedad?",
                                "Método para cloración de agua en todos los abastecimientos",
                                "Capacidad de identificación de casos en las escuelas",
                                "Lavado de tinacos y depósitos de agua intradomiciliaria",
                                "Método para cloración de agua en todos los abastecimientos",
                                "Método para cloración de agua en todos los abastecimientos"
                        },
                        {
                                "Una paciente de 37 años acude a consulta porque presenta pesadez, calambres y dolor en miembro inferior izquierdo de 12 horas de evolución. Entre sus antecedentes presenta sedestación de más de 7 horas al día por su actividad laboral. En la exploración física se observa palidez leve en la piel, sin presencia de cianosis; se registra TA de 94/61 mmHg, FC de 88/min, FR de 18/min, T 36.9ºC, peso de 72 kg y talla de 1.60 m; se destaca ligero edema en ambos miembros inferiores y se observan venas varicosas. Se le indica compresoterapia, elevación frecuente y medidas dietéticas para control de peso que favorezcan el retorno venoso. ¿Qué recomendación de actividad física se debe indicar a la paciente?",
                                "Sentadillas con peso en tobillos o mancuernas",
                                "Deambulación durante inmersión en alberca",
                                "Entrenamiento funcional con intensidad alternada",
                                "Deambulación durante inmersión en alberca",
                                "Deambulación durante inmersión en alberca"
                        },
                        {
                                "Una paciente de 51 años acude a consulta porque en la última semana ha presentado sensación urente palmar y cefalea, pero desde el día anterior ha presenta mareo al caminar. Indica antecedente de hipertensión arterial desde hace 6 meses, tratada con metoprolol y con indicación de incremento en actividad física, por lo que practica caminata vigorosa por media hora al día, pero al hacerlo ha presentado aturdimiento. En la exploración física se registra TA de 112/68 mmHg, FC de 60/min, FR de 20/min y T de 36.9ºC. se toma ECG y se registra FC 65/min, complejos QRS precedidos de ondas P, intervalo PR de 0.28 s, complejo QRSde 0.10 s, ondas P positivas en DI, DII y aVF, ondas T negativas en AVR, eje eléctrico de +60º e índice de Sokolow (SV1+RV6) de 20 mm. ¿Qué dato del cuadro de la paciente es indicador de que se debe derivar al siguiente nivel de atención?",
                                "Aturdimiento durante actividad física",
                                "Valor del índice Sokolow",
                                "Duración del intervalo PR",
                                "Duración del intervalo PR",
                                "Duración del intervalo PR"
                        },
                        {
                                "En un centro de salud en el que un gran número de pacientes asiste para el control de padecimientos crónicos, se pretende explorar si el ayuno intermitente controlado influye en la estabilización de los niveles de glucosa en personas con diabetes mellitus. ¿Qué pregunta clínica de investigación dirige la búsqueda de evidencia?",
                                "¿el control del nivel de glucemia en pacientes con diabetes mellitus es más eficiente con un programa de ayuno intermitente que con otros tipos de dieta?",
                                "¿los programas de ayuno intermitente mejoran el control de la diabetes mellitus en comparación con pacientes con otros padecimientos crónicos?",
                                "¿La reducción del riesgo de complicaciones por falta de control de la glucosa en pacientes con diabetes mellitus es favorecida por los programas de ayuno intermitente",
                                "¿el control del nivel de glucemia en pacientes con diabetes mellitus es más eficiente con un programa de ayuno intermitente que con otros tipos de dieta?",
                                "¿el control del nivel de glucemia en pacientes con diabetes mellitus es más eficiente con un programa de ayuno intermitente que con otros tipos de dieta?"
                        },
                        {
                                "Se presenta a consulta una paciente con cuadro de faringoamigdalitis bacteriana, por lo que se indica manejo sintomático y con antimicrobianos orales. Sin embargo, el paciente explica que por su ocupación le es indispensable reanudar sus actividades y le solicita inyección de dexametasona, ya que en otras ocasiones se le ha prescrito ante síntomas del mismo tipo y presenta mejora inmediata. Se le indica que no es recomendado su uso para estos casos y por lo que no será posible prescribirlo. ¿qué obligación del médico se ve involucrada en esta situación?",
                                "Corresponsabilizar al paciente para la toma de decisiones terapéuticas",
                                "Documentar el consentimiento informado para estas prescripciones",
                                "Emitir el juicio clínico para prescribir con base en el sustento científico",
                                "Emitir el juicio clínico para prescribir con base en el sustento científico",
                                "Emitir el juicio clínico para prescribir con base en el sustento científico"
                        },
                        {
                                "Le solicita el llenado del certificado de defunción de un paciente de 59 años que falleció luego de sufrir el impacto de un vehículo automotor a alta velocidad. De acuerdo con la causa de muerte del paciente, ¿Qué información es indispensable integrar en el certificado?",
                                "Ubicación del siniestro",
                                "Nombre del responsable del siniestro",
                                "Tipo de vehículo involucrado",
                                "Ubicación del siniestro",
                                "Ubicación del siniestro"
                        },
                        {
                                "Se pretende evaluar los resultados de la ejecución de las acciones del Programa de Acción Específico de Vacunación Universal en diversas comunidades rurales del suroeste del país, debido a que se identificó que, aunque existe una creciente demanda poblacional, el sistema en primer nivel de atención no ha tenido la capacidad de satisfacer esta demanda. ¿Qué indicador permite evaluar la efectividad del programa en esas comunidades?",
                                "Tasa de ocupación de camas hospitalarias debido a causas prevenibles por vacunación.",
                                "Proporción de personas de la comunidad que solicitan la administración de vacunas.",
                                "Rendimiento costo-beneficio del gasto devengado durante el proceso de vacunación",
                                "Tasa de ocupación de camas hospitalarias debido a causas prevenibles por vacunación.",
                                "Tasa de ocupación de camas hospitalarias debido a causas prevenibles por vacunación."
                        },
                        {
                                "Ante las diversas situaciones de desastre producidas por fenómenos hidrometeorológicos como los ciclones en los últimos años, se requiere implementar acciones coordinadas a escalas federal, estatal y municipal que permitan minimizar el impacto en la salud de las comunidades afectadas. ¿Qué acción de salud pública permite reducir el impacto en salud de la población?",
                                "Monitoreo ambiental continuo",
                                "Estimación de superación de capacidad análisis y vulnerabilidad",
                                "Análisis de vulnerabilidad",
                                "Análisis de vulnerabilidad",
                                "Análisis de vulnerabilidad"
                        },
                        {
                                "Un lactante de dos meses es llevado a consulta de control por su madre, a quien se le solicito verificar el esquema de vacunación del menor esta al día, en la guardería a la que asiste se reportó un caso de neumonía por haemophilius influenzae tipo b. ¿Qué inmunización se debe aplicar al menor para protegerlo de esta enfermedad?",
                                "Triple viral",
                                "Antineumocócica conjugada",
                                "Pentavalente acelular",
                                "Pentavalente acelular",
                                "Pentavalente acelular"
                        },
                        {
                                "Un paciente de 48 años acude a consulta en primer nivel de atención por presentar cefalea constante que no mejora con la administración de energéticos. Así como taquicardia y agorafobia punto el paciente informa tener 3 años consumiendo un litro de tequila diario coma luego de que falleció su esposa punto en la exploración física se observa resequedad en mucosas oral y en la entrevista se detecta que el Estado nutricional del paciente es deficiente. Ante la dependencia al alcohol del paciente cuál es la acción por seguir para el manejo de este problema?",
                                "Referir al paciente a segundo nivel de atención",
                                "Iniciar intervenciones breves en el primer nivel de atención",
                                "Enviar al servicio de urgencias",
                                "Referir al paciente a segundo nivel de atención",
                                "Referir al paciente a segundo nivel de atención"
                        },
                        {
                                "Un paciente de 6 años es llevado por su madre a consulta externa por parasitosis intestinal a expensas de Áscaris lumbricoides. Además del manejo farmacológico, ¿qué medida de autocuidado se debe implementar para prevenir ésta y otras parasitosis intestinales?",
                                "Fomentar el baño diario y el cambio de ropa limpia",
                                "Orientar al menor sobre la aplicación del alcohol-gel",
                                "Informar a la madre sobre la técnica de lavado de manos",
                                "Informar a la madre sobre la técnica de lavado de manos",
                                "Informar a la madre sobre la técnica de lavado de manos"
                        },
                        {
                                "En un centro de salud que atiende a una población urbana de 130 000 habitantes se ha identificado que la tasa de incidencia de infección por VIH se ha incrementado 18% en los últimos 10 años y en consecuencia, la mortalidad por esta causa. Se requiere implementar una estrategia en el primer nivel de atención con la finalidad de reducir la transmisión y mejorar la oportunidad del diagnóstico en esta población. La estrategia debe incrementar el acceso para la realización de tamizaje de.",
                                "Anticuerpos",
                                "Antígenos",
                                "Carga viral",
                                "Antígenos",
                                "Antígenos"
                        },
                        {
                                "en una localidad rural del estado de México con un alto nivel de marginación se presentaron en los últimos 6 meses, 3 casos de tétanos neonatal por lo que después de confirmar que existe una baja cobertura de inmunización se decide iniciar una campaña de vacunación de toxoide tetánico a…",
                                "Mujeres en edad fértil",
                                "Mujeres con embarazo a término",
                                "Recién nacidos",
                                "Mujeres con embarazo a término",
                                "Mujeres con embarazo a término"
                        },
                        {
                                "en una comunidad urbana se identifican las enfermedades diarreicas agudas fueron la segunda causa de hospitalización en niños menores de 1 año y la segunda causa en niños de entre 1 y 4 años en el último semestre. Se analizaron diversos aspectos de la comunidad y se determina que su población tiene fácil acceso a los servicios de salud y que se cuenta con los recursos sanitarios básicos. ¿Qué acción de educación para la salud se debe difundir entre los habitantes de la comunidad para favorecer los factores de protección de su población pediátrica?",
                                "Efecto del acceso oportuno al diagnóstico y del tratamiento",
                                "Impacto de la lactancia materna y ablactación oportuna",
                                "Fortalecimiento del suministro de agua potable y saneamiento",
                                "Impacto de la lactancia materna y ablactación oportuna",
                                "Impacto de la lactancia materna y ablactación oportuna"
                        },
                        {
                                "En una ranchería se registran algunos casos de brucelosis en los trabajadores y sus familias. Se maneja cada caso con esquema tradicional de antimicrobianos, en función de las particularidades y sólo en 2 pacientes es necesario utilizar esquema alternativo por experiencias previas. Se decide implementar un programa de educación sanitaria para mitigar la propagación futura entre los trabajadores. ¿Qué acción educativa se debe difundir para promover las medidas higiénicas en esta comunidad?",
                                "Evitar el consumo de leche de vaca y sus derivados",
                                "Promover el aislamiento de bovinos con anticuerpos",
                                "Regularizar el entierro de óbitos, fetos y abortos bovinos",
                                "Regularizar el entierro de óbitos, fetos y abortos bovinos",
                                "Regularizar el entierro de óbitos, fetos y abortos bovinos"
                        },
                        {
                                "Acude a la unidad de salud la madre de una menor de 2 meses para cita de control de crecimiento. De acuerdo con el interrogatorio y la exploración física no se detectan casos que sugieran alguna patología. Se le administran las inmunizaciones de acuerdo con la edad y se le programa cita de seguimiento en 1 mes. ¿Qué orientación se debe brindar para prevenir accidentes?",
                                "Método de portabilidad y uso de canguro para la bebé",
                                "Postura de la lactante al dormir",
                                "Técnica de alimentación al seno materno",
                                "Postura de la lactante al dormir",
                                "Postura de la lactante al dormir"
                        },
                        {
                                "Durante una campaña para promover la correcta higiene de manos para evitar la transmisión de enfermedades infectocontagiosas en la comunidad de adscripción, se lleva a cabo un taller práctico del método. ¿Qué estrategia educativa se debe implementar para que la comunidad se apropie de la técnica?",
                                "Repetir el taller con la técnica y aplicar una encuesta",
                                "Profundizar en la técnica y regresar a la práctica",
                                "Distribuir trípticos sobre técnica de higiene de manos",
                                "Profundizar en la técnica y regresar a la práctica",
                                "Profundizar en la técnica y regresar a la práctica"
                        }


                };
                break;
            case 2:
                preguntas = new String[][]{

                        {"¿Qué es el acoplamiento?", "La medida de la independencia entre dos módulos.", "La medida de la dependencia entre dos módulos.", "La medida de la cohesión de un módulo.", "La medida de la dependencia entre dos módulos.", "El acoplamiento se refiere a la medida de dependencia entre dos módulos, es decir, cuanto uno necesita del otro para funcionar correctamente."},
                        {"¿Qué es la cohesión?", "La medida de la independencia entre dos módulos.", "La medida de la dependencia entre dos módulos.", "La medida de la unidad de un módulo.", "La medida de la unidad de un módulo.", "La cohesión se refiere a la medida de la unidad de un módulo, es decir, la relación entre los elementos del mismo y qué tan relacionados están en términos de funcionalidad."},
                        {"¿Por qué es importante el acoplamiento y la cohesión en el desarrollo de software?", "Porque ayudan a garantizar la calidad del software.", "Porque hacen que el software sea más fácil de entender y mantener.", "Ambas opciones son correctas.", "Ambas opciones son correctas.", "Tanto el acoplamiento como la cohesión son importantes para garantizar la calidad del software y hacer que sea más fácil de entender y mantener."},
                        {"¿Cómo se puede medir el acoplamiento y la cohesión de un sistema?", "A través de métricas específicas.", "Observando el código fuente del sistema.", "A través de pruebas de usuario.", "A través de métricas específicas.", "Se pueden utilizar métricas específicas para medir el acoplamiento y la cohesión de un sistema, lo que permite evaluar su calidad y determinar si es necesario hacer cambios para mejorarlo."},
                        {"¿Qué es la ley de Demeter y cómo se relaciona con el acoplamiento?", "Es un principio de programación que establece que un objeto no debería conocer los detalles internos de otros objetos.", "Es un principio de programación que establece que un objeto debe conocer los detalles internos de otros objetos.", "Es un principio de programación que establece que los objetos deben comunicarse directamente entre sí.", "Es un principio de programación que establece que un objeto no debería conocer los detalles internos de otros objetos.", "La ley de Demeter es un principio de programación que busca reducir el acoplamiento al limitar el conocimiento que un objeto tiene sobre otros objetos. Esto ayuda a reducir la dependencia entre los distintos módulos y hace que el software sea más fácil de entender y mantener."},
                        {"¿Qué es el acoplamiento en el contexto de la programación orientada a objetos?", "La relación entre dos clases que deben trabajar juntas para cumplir una tarea", "La relación entre dos clases que tienen muy poca interacción entre sí", "La relación entre dos clases que no tienen ninguna interacción entre sí", "La relación entre dos clases que deben trabajar juntas para cumplir una tarea", "El acoplamiento se refiere al grado de interdependencia entre dos clases. Un acoplamiento fuerte indica una alta interdependencia y puede dificultar la reutilización y el mantenimiento del código."},
                        {"¿Qué es la cohesión en el contexto de la programación orientada a objetos?", "El grado en que los métodos y atributos de una clase están relacionados entre sí", "La medida en que dos clases están relacionadas entre sí", "La medida en que una clase puede ser reutilizada en diferentes contextos", "El grado en que los métodos y atributos de una clase están relacionados entre sí", "La cohesión se refiere a la medida en que los métodos y atributos de una clase están relacionados entre sí y trabajan juntos para cumplir una tarea específica. Una alta cohesión puede facilitar la comprensión y el mantenimiento del código."},
                        {"¿Cuál es la diferencia entre el acoplamiento y la cohesión?", "El acoplamiento se refiere a la relación entre dos clases, mientras que la cohesión se refiere a la relación entre los métodos y atributos de una clase", "El acoplamiento se refiere a la relación entre los métodos y atributos de una clase, mientras que la cohesión se refiere a la relación entre dos clases", "El acoplamiento y la cohesión son términos intercambiables que se refieren a lo mismo", "El acoplamiento se refiere a la relación entre dos clases, mientras que la cohesión se refiere a la relación entre los métodos y atributos de una clase", "El acoplamiento y la cohesión son dos conceptos diferentes en la programación orientada a objetos. El acoplamiento se refiere a la relación entre dos clases, mientras que la cohesión se refiere a la relación entre los métodos y atributos de una clase."},
                        {"¿Por qué es importante tener un bajo acoplamiento en un sistema de software?", "Un bajo acoplamiento puede hacer que el código sea más fácil de entender, reutilizar y mantener", "Un bajo acoplamiento puede hacer que el código sea más difícil de entender, reutilizar y mantener", "El acoplamiento no tiene un impacto significativo en la calidad del código", "Un bajo acoplamiento puede hacer que el código sea más fácil de entender, reutilizar y mantener", "Un bajo acoplamiento puede reducir la interdependencia entre clases y hacer que el código sea más modular, lo que a su vez puede facilitar la reutilización y el mantenimiento."},
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
                        {"¿Cuál es el propósito principal del diseño de interfaces?",
                                "Mejorar la apariencia visual del software", "Mejorar la experiencia del usuario", "Mejorar la eficiencia del software",
                                "Mejorar la experiencia del usuario",
                                "El diseño de interfaces tiene como objetivo principal mejorar la experiencia del usuario al interactuar con el software."},

                        {"¿Cuál es el principio básico del diseño centrado en el usuario?",
                                "Centrarse en la estética del diseño", "Centrarse en la funcionalidad del software", "Centrarse en las necesidades del usuario",
                                "Centrarse en las necesidades del usuario",
                                "El diseño centrado en el usuario se enfoca en comprender las necesidades, metas y tareas del usuario para diseñar una interfaz que sea fácil de usar y satisfaga sus requerimientos."},

                        {"¿Cuál es el objetivo principal del diseño responsivo?",
                                "Mejorar la apariencia visual del software", "Mejorar la velocidad de carga del software", "Mejorar la experiencia del usuario en diferentes dispositivos",
                                "Mejorar la experiencia del usuario en diferentes dispositivos",
                                "El diseño responsivo se enfoca en crear interfaces que se adapten a diferentes tamaños de pantalla y dispositivos, proporcionando una experiencia de usuario consistente en cualquier dispositivo."},

                        {"¿Qué es el patrón de diseño 'Tarjeta'?",
                                "Un patrón de diseño para la estructura de una base de datos", "Un patrón de diseño para el diseño de interfaces de usuario", "Un patrón de diseño para la arquitectura de software",
                                "Un patrón de diseño para el diseño de interfaces de usuario",
                                "El patrón de diseño 'Tarjeta' se refiere a la presentación de información en bloques separados y organizados, como una tarjeta en un tablero de Pinterest."},

                        {"¿Cuál es el propósito de los patrones de diseño?",
                                "Establecer estándares de diseño para la industria", "Proporcionar soluciones probadas para problemas comunes de diseño", "Mejorar la estética de las interfaces de usuario",
                                "Proporcionar soluciones probadas para problemas comunes de diseño",
                                "Los patrones de diseño son soluciones probadas para problemas comunes de diseño que se han desarrollado a lo largo del tiempo y se han establecido como buenas prácticas en la industria."},

                        {"¿Qué es la heurística de usabilidad?",
                                "Una técnica de diseño para la creación de interfaces de usuario", "Un conjunto de principios de diseño centrado en el usuario", "Un método para evaluar la usabilidad de una interfaz de usuario",
                                "Un método para evaluar la usabilidad de una interfaz de usuario",
                                "La heurística de usabilidad es un conjunto de principios que se utilizan para evaluar la calidad de una interfaz de usuario, centrándose en la facilidad de uso, la eficiencia y la satisfacción del usuario."},

                        {"¿Cuál es el objetivo principal del diseño centrado en el usuario en la interfaz de usuario?",
                                "Mejorar la usabilidad de la interfaz de usuario",
                                "Hacer la interfaz de usuario más atractiva",
                                "Aumentar la velocidad de procesamiento de la interfaz de usuario",
                                "Mejorar la usabilidad de la interfaz de usuario",
                                "Mejorar la usabilidad de la interfaz de usuario se logra al enfocarse en las necesidades y deseos de los usuarios finales durante todo el proceso de diseño de la interfaz."},

                        {"¿Qué es un wireframe en el diseño de interfaces de usuario?",
                                "Un esquema de la disposición de los elementos de la interfaz de usuario",
                                "Un modelo en 3D de la interfaz de usuario",
                                "Una herramienta para probar la velocidad de procesamiento de la interfaz de usuario",
                                "Un esquema de la disposición de los elementos de la interfaz de usuario",
                                "Un wireframe es un esquema de la disposición de los elementos de la interfaz de usuario, que ayuda a visualizar y organizar los componentes de la interfaz antes de crear una versión más detallada."},

                        {"¿Cuál es el propósito del diseño visual en la interfaz de usuario?",
                                "Hacer la interfaz de usuario atractiva y fácil de usar",
                                "Crear la estructura de la interfaz de usuario",
                                "Determinar los requisitos de la interfaz de usuario",
                                "Hacer la interfaz de usuario atractiva y fácil de usar",
                                "El propósito del diseño visual en la interfaz de usuario es hacerla más atractiva y fácil de usar para los usuarios finales."},

                        {"¿Qué técnica de diseño de interfaces utiliza la creación de modelos visuales de los elementos de la interfaz?",
                                "Diseño de prototipos", "Diagramación", "Wireframing",
                                "Wireframing",
                                "Wireframing es una técnica de diseño de interfaces que utiliza la creación de modelos visuales de los elementos de la interfaz para facilitar su diseño y evaluación."},

                        {"¿Qué elemento de diseño de interfaces se refiere al uso de la misma forma o estilo visual en todo el sistema?",
                                "Consistencia", "Legibilidad", "Alineación",
                                "Consistencia",
                                "La consistencia en el diseño de interfaces se refiere al uso de la misma forma o estilo visual en todo el sistema para crear una experiencia de usuario coherente."},

                        {"¿Cuál de los siguientes NO es un principio de diseño de interfaces?",
                                "Innovación", "Usabilidad", "Accesibilidad",
                                "Innovación",
                                "Innovación no es un principio de diseño de interfaces, aunque puede ser una parte importante del proceso de diseño en general."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la forma en que los elementos visuales se organizan en la pantalla?",
                                "Composición", "Espaciado", "Proporción",
                                "Composición",
                                "La composición en el diseño de interfaces se refiere a la forma en que los elementos visuales se organizan en la pantalla para crear una estructura visual coherente y atractiva."},

                        {"¿Qué principio de diseño de interfaces se refiere a la capacidad del diseño para ser comprensible y fácil de aprender para el usuario?",
                                "Usabilidad", "Atractividad", "Accesibilidad",
                                "Usabilidad",
                                "La usabilidad en el diseño de interfaces se refiere a la capacidad del diseño para ser comprensible y fácil de aprender para el usuario, para que pueda realizar sus tareas de manera eficiente y efectiva."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la relación visual entre los elementos en la pantalla?",
                                "Espaciado", "Proporción", "Composición",
                                "Espaciado",
                                "El espaciado en el diseño de interfaces se refiere a la relación visual entre los elementos en la pantalla, y puede afectar la legibilidad y el equilibrio visual del diseño."},

                        {"¿Qué técnica de diseño de interfaces utiliza la prueba del diseño con usuarios reales?",
                                "Pruebas de usabilidad", "Wireframing", "Diagramación",
                                "Pruebas de usabilidad",
                                "Las pruebas de usabilidad son una técnica de diseño de interfaces que utiliza la prueba del diseño con usuarios reales para evaluar la eficacia y la eficiencia del diseño."},

                        {"¿Cuál de las siguientes NO es una regla de diseño de interfaces según las normas ISO?",
                                "Uso de fuentes complejas", "Consistencia", "Legibilidad",
                                "Uso de fuentes complejas",
                                "El uso de fuentes complejas no es una regla de diseño de interfaces según las normas ISO, ya que esto puede dificultar la legibilidad del diseño."},

                        {"¿Qué elemento de diseño de interfaces se refiere a la capacidad del diseño para llamar la atención del usuario?",
                                "Atractividad", "Usabilidad", "Accesibilidad",
                                "Atractividad",
                                "La atractividad en el diseño de interfaces se refiere a la capacidad del diseño para llamar la atención del usuario y crear una experiencia visualmente atractiva."},
                        {"¿Qué es la usabilidad en el diseño de interfaces?",
                                "La capacidad de la interfaz para permitir al usuario realizar tareas con eficiencia y satisfacción", "El aspecto visual de la interfaz", "La capacidad de la interfaz para ser compatible con diferentes sistemas operativos",
                                "La capacidad de la interfaz para permitir al usuario realizar tareas con eficiencia y satisfacción",
                                "La usabilidad se enfoca en la facilidad de uso y la experiencia del usuario al interactuar con la interfaz."},

                        {"¿Qué es la accesibilidad en el diseño de interfaces?",
                                "La capacidad de la interfaz para ser compatible con diferentes sistemas operativos", "La capacidad de la interfaz para ser utilizada por personas con discapacidades", "La capacidad de la interfaz para funcionar en diferentes idiomas",
                                "La capacidad de la interfaz para ser utilizada por personas con discapacidades",
                                "La accesibilidad se enfoca en hacer que la interfaz sea utilizable por personas con discapacidades y necesidades especiales."},

                        {"¿Cuál es el propósito de los wireframes en el diseño de interfaces?",
                                "Visualizar la estructura y el contenido de la interfaz", "Elegir colores y fuentes para la interfaz", "Crear una versión interactiva de la interfaz",
                                "Visualizar la estructura y el contenido de la interfaz",
                                "Los wireframes son una herramienta para planificar la estructura y el contenido de la interfaz antes de diseñarla visualmente."},

                        {"¿Qué es un prototipo en el diseño de interfaces?",
                                "Una versión interactiva de la interfaz que simula su funcionamiento", "Una imagen estática de la interfaz final", "Un boceto a lápiz de la interfaz",
                                "Una versión interactiva de la interfaz que simula su funcionamiento",
                                "Los prototipos son una forma de probar y validar el diseño de la interfaz antes de su implementación final."},

                        {"¿Cuál es el propósito de las pruebas de usabilidad en el diseño de interfaces?", "Evaluar la eficiencia y satisfacción del usuario al interactuar con la interfaz", "Evaluar la calidad del código utilizado en la implementación de la interfaz", "Evaluar la compatibilidad de la interfaz con diferentes sistemas operativos", "Evaluar la eficiencia y satisfacción del usuario al interactuar con la interfaz", "Las pruebas de usabilidad permiten detectar problemas y mejorar la experiencia del usuario al interactuar con la interfaz."},

                        {"¿Qué es el diseño responsivo en el diseño de interfaces?", "El diseño de la interfaz para que se adapte a diferentes tamaños de pantalla y dispositivos", "El diseño de la interfaz para que sea compatible con diferentes navegadores web", "El diseño de la interfaz para que se vea bien en pantallas de alta resolución", "El diseño de la interfaz para que se adapte a diferentes tamaños de pantalla y dispositivos", "El diseño responsivo es una técnica de diseño que permite que la interfaz se adapte a diferentes tamaños de pantalla y dispositivos."},

                        {"¿Cuál es el principio de diseño que sugiere que las partes de una interfaz deben estar organizadas en grupos coherentes?",
                                "Proximidad", "Equilibrio", "Alineación", "Proximidad",
                                "La proximidad sugiere que los elementos que están relacionados deben ser colocados cerca uno del otro en una interfaz para que sea fácil de entender."},
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
