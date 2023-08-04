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


public class ExamenCompleto  extends AppCompatActivity {
    private static final String PREFS_NAME = "MisPreferencias";
    private static final String ULTIMA_ACTIVIDAD = "preguntasActivity";
    private static final int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
    private TextView preguntaTextView;
    private Button opcion1Button, opcion2Button, opcion3Button;
    private ImageView gifImageView;
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
    private   String retroalimentacion;
    private   String respuestaCorrecta;
    private int tipo;

    // Lista de índices de preguntas para mostrar en un orden aleatorio
    private List<Integer> listaIndicesPreguntas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
                        {"Una paciente de 27 años acude a consulta porque en los últimos meses ha presentado dolor y distensión abdominal que mejora al evacuar, así como alteraciones en la frecuencia de las evacuaciones y urgencias miccionales. Indica sentirse sumamente incómoda, ya que las molestias afectan su vida sexual. Además, varias veces por semana no puede conciliar el sueño y se siente constantemente cansada. Recientemente, ha empezado a autoadministrarse gomitas de melatonina y ha incrementado el número de cigarros que fuma de 4 a 10 al día. ¿Qué aspecto psicológico asociado al motivo de la paciente se debe explorar?",
                                "Depresión", "Manejo del estrés", "Abuso de sustancias", "Manejo del estrés", "Es importante explorar el manejo del estrés de la paciente, ya que los síntomas que presenta pueden estar relacionados con el estrés emocional. Brindarle herramientas para manejar el estrés puede ayudar a mejorar su bienestar."},

                        {"Una paciente de 75 años es llevada a consulta por su hija, ya que sufrió una caída desde el plano de sustentación. Además, la hija refiere que percibe en su madre aislamiento social, descuido personal y olvidos recurrentes en el último año. Se observa que la paciente está orientada, cooperadora, aunque levemente distraída. Durante la exploración, se registran signos vitales y neurológicos normales, pero se observa una pequeña abrasión en la rodilla derecha debido a la caída. Además, se registra disminución en la visión lejana y en la agudeza auditiva. ¿Cuál es el cambio biológico que está relacionado con las alteraciones en la paciente?",
                                "Espesor y rigidez del cristalino", "Aumento de hueso esponjoso en oído medio", "Colección de sangre subdural", "Aumento de hueso esponjoso en oído medio", "El aumento de hueso esponjoso en el oído medio puede ser el cambio biológico relacionado con las alteraciones en la paciente, lo cual puede contribuir a la disminución de la visión lejana y la agudeza auditiva que se observan en la evaluación."},

                        {"Una paciente de 68 años presenta un cuadro de tos con expectoración blanquecina y ortopnea. Menciona que el día de hoy se agudizan los síntomas y presenta disnea. Reside en una comunidad rural y vive con cuatro familiares más. Su casa habitación cuenta con dos cuartos, piso de tierra, techo de lámina, autoconsumo y cocina con leña. Durante la exploración física, se registran los siguientes valores: TA 120/80 mmHg, FC de 78 lpm, FR 22 lpm, T 36.9°C y saturación de oxígeno (sato2) de 98%. ¿Cuál es el factor del entorno de la paciente que se asocia con su problema de salud?",
                                "Exposición a biomasa", "Hacinamiento", "Zoonosis", "Exposición a biomasa", "La exposición a biomasa, como la cocina con leña, puede estar asociada con los síntomas respiratorios que la paciente presenta, como la tos y la disnea. Es importante evaluar y abordar este factor del entorno para mejorar su salud."},

                        {"Una paciente de 55 años acude a consulta por control debido a que tiene antecedentes de diabetes mellitus de 5 años de evolución. En la exploración física, se registran signos normales, con un IMC de 31 kg/m2 y un perímetro de cintura de 102 cm. Al preguntarle sobre los ajustes de estilo de vida, comenta que no ha logrado incorporar actividad física constante en la rutina ni reducir el número de veces que come en la calle. Esto se debe a que trabaja como conductor de una aplicación por casi 12 horas al día y llega a su casa en la madrugada para cumplir con las horas requeridas por el dueño del vehículo. El paciente menciona que, a partir de su cita de control anterior, intentó caminar por 30 minutos junto con su esposa durante las mañanas antes de iniciar sus viajes, pero lo asaltaron cerca de su domicilio y lo despojaron de su teléfono celular, que es parte de sus herramientas de trabajo. Como resultado, tuvo que trabajar más horas para subsanar la pérdida y suspendió las caminatas. Además, su esposa manifiesta el deseo de mudarse debido a la inseguridad en su colonia a raíz del asalto. ¿Qué aspecto del contexto social del paciente afecta su estado actual de salud?",
                                "Precarización laboral", "Ambiente familiar", "Inseguridad en su colonia", "Precarización laboral", "La precarización laboral del paciente, debido a sus largas jornadas de trabajo y la necesidad de cubrir una cantidad específica de horas, está afectando su capacidad para realizar cambios en su estilo de vida y cuidar de su salud. Esta situación puede tener un impacto negativo en su bienestar general."},

                        {"¿Qué enfoque de desarrollo implica una planificación cuidadosa de las fases del proyecto y una secuencia de etapas lineales?", "Flujo de proceso en cascada", "Flujo de proceso en espiral", "Flujo de proceso evolutivo", "Flujo de proceso en cascada", "El flujo de proceso en cascada implica una planificación cuidadosa y una secuencia de etapas lineales para completar un proyecto de software."},

                        { "Paciente masculino de 82 años hospitalizado por reagudización de EPOC y JS es incapaz de caminar sin ayuda tras 1 semana de reposo en cama. Previo a hospitalización podía deambular independientemente con apoyo de un bastón que empezó a usar hace 7 años, posterior a un ACV. Actualmente, continúa con tratamiento con broncodilatadores inhalados; asimismo, desde su ingreso permanece con tratamiento con teofilina IV, metilprednisolona y eritromicina. En la EF, se registra una fuerza grado 4/5 en musculatura proximal de ambas piernas y disminución en la extensión de la cadera izquierda.\n¿Cuál es la causa de la incapacidad?",
                                "Atrofia por desuso",
                                "Toxicidad por teofilinas",
                                "Miopatía esteroidea",
                                "Atrofia por desuso",
                                "La incapacidad del paciente está relacionada con la atrofia por desuso. El reposo en cama prolongado y la falta de uso de los músculos pueden llevar a la atrofia, debilitando la musculatura y reduciendo la fuerza. Esto se agrava en este caso debido al ACV previo que ya afectó la movilidad del paciente, lo que contribuye a la incapacidad actual."
                        },

                        { "Un lactante menor de 4 meses es llevado a consulta por presentar, durante las últimas 3 semanas, más de dos regurgitaciones postprandiales al día, sin irritabilidad, ni llanto, ni precedidas de náuseas. El menor muestra un adecuado incremento de peso.",
                                "Enfermedad por reflujo gastroesofágico",
                                "Reflujo gastroesofágico",
                                "Estenosis hipertrófica de píloro",
                                "Reflujo gastroesofágico",
                                "El problema de salud del lactante es el reflujo gastroesofágico. Las regurgitaciones postprandiales sin otros síntomas como irritabilidad o llanto sugieren que el lactante tiene un reflujo gastroesofágico fisiológico, que es común en esta edad y generalmente no representa un problema de salud importante. Además, el adecuado incremento de peso es una señal positiva de que el lactante está recibiendo suficiente nutrición a pesar de las regurgitaciones."
                        },

                        { "En una población rural aislada en la sierra sur de Oaxaca, cuyo nivel educativo es bajo, hay desnutrición, es de difícil acceso al primer nivel de atención en salud y con baja cobertura de programas de prevención y vacunación, se presenta un brote de hepatitis A. El origen de éste se ubica en la escuela primaria, donde se comparte un tambo de agua para el lavado de manos, lo que permite que se propague posteriormente por medio del contacto familiar.",
                                "Bajo nivel educacional",
                                "Servicios básicos de sanidad ineficientes",
                                "Difícil acceso al sistema de salud",
                                "Difícil acceso al sistema de salud",
                                "El factor condicionante ligado a la ineficiente contención en la propagación de casos es el difícil acceso al sistema de salud. La población rural aislada en la sierra sur de Oaxaca enfrenta dificultades para acceder al primer nivel de atención en salud, lo que dificulta el control y manejo adecuado del brote de hepatitis A. La falta de acceso a servicios de salud y programas de prevención y vacunación adecuados puede llevar a una mayor propagación de la enfermedad, especialmente cuando hay condiciones de higiene precarias en la comunidad, como compartir un tambo de agua para el lavado de manos en la escuela primaria."
                        },
                        { "Un paciente de 70 años acude a consulta por presentar desde hace 2 días una erupción cutánea que confluye en tronco y extremidades. Entre sus antecedentes está padecer hipertensión arterial e hiperuricemia, las cuales trata con amlodipino y alopurinol, menciona que hace 2 semanas presentó lumbalgia, que trató con diclofenaco. En la exploración física se observan lesiones maculares eritematovioláceas muy extensas, de casi un 70% de la superficie cutánea, sobre las que hay ampollas y erosiones. Se identifican lesiones erosivo-costrosas de la mucosa labial, oral y conjuntiva, y al pasar el dedo sobre la piel ésta se despega (signo de Nikolsky positivo).",
                                "Pénfigo vulgar",
                                "Liquen ampolloso",
                                "Necrólisis tóxica epidérmica",
                                "Necrólisis tóxica epidérmica",
                                "El problema de salud del paciente es la necrólisis tóxica epidérmica. Los síntomas y signos descritos, como las lesiones extensas en la piel, ampollas, erosiones, y el signo de Nikolsky positivo, son característicos de esta enfermedad grave y potencialmente mortal. La necrólisis tóxica epidérmica es una reacción adversa grave a medicamentos, y en este caso, es probable que haya sido desencadenada por el diclofenaco, ya que es un fármaco conocido por asociarse con esta reacción cutánea."
                        },
                        { "Mujer de 66 años, costurera en una fábrica de ropa desde hace 40 años acude a consulta por dificultad visual progresiva para coser y bordar desde hace 1 año. En el interrogatorio se obtiene la siguiente información: padre con glaucoma, madre con hipertensión arterial y un hermano con catarata, alimentación preparada en casa a base de leguminosas y tortilla, con alto consumo de refresco. Presentó ambliopía en los primeros años de vida, tratada con lentes correctivos, miopía desde los 10 años con uso de lentes correctivos e hipertensión arterial de 10 años de evolución, en tratamiento.",
                                "Profesión, antecedente familiar con catarata e hipertensión arterial",
                                "Antecedente familiar de glaucoma, ambliopatía en la infancia y alimentación",
                                "Edad, dificultad para ver objetos pequeños y evolución lenta y progresiva",
                                "Edad, dificultad para ver objetos pequeños y evolución lenta y progresiva",
                                "Los datos relevantes para el diagnóstico de presbicia en la paciente son la edad, la dificultad para ver objetos pequeños de cerca y la evolución lenta y progresiva de la dificultad visual para coser y bordar. La presbicia es una condición natural asociada al envejecimiento del ojo, que generalmente comienza a manifestarse alrededor de los 40-50 años. La dificultad para enfocar objetos cercanos, como los detalles de la costura y el bordado, es un síntoma característico de la presbicia. Además, la evolución gradual de este problema a lo largo de un año es consistente con esta condición visual relacionada con la edad."
                        },

                        { "Una pareja de 22 años de edad habita en un medio rural, son campesinos con estudios de primaria incompleta y viven con sus cuatro hijos en una sola habitación. Tres de los niños se encuentran sanos, mientras que el paciente de 3 años, que cuenta con esquema de vacunación incompleto, presenta infecciones respiratorias de repetición y gastroenteritis. Los padres llevan a consulta al niño de 3 años, ya que presenta vómito y diarrea con 5 días de evolución. Ellos mencionan que el menor ha presentado seis evacuaciones esteatorréicas al día y vómito de contenido gastroalimentario dos o tres veces al día. Durante la EF se encontró peso de 10.5 kg, talla de 88 cm, FC de 65/min, FR de 18/min, T de 36.6 ºC, SaO2 de 93% y TA de 90/60 mmHg.",
                                "Ocupación de los padres e infecciones respiratorias de repetición",
                                "Número de hermanos y probable cardiopatía congénita",
                                "Contexto socioeconómico familiar y enfermedades gastrointestinales",
                                "Ocupación de los padres e infecciones respiratorias de repetición",
                                "Los factores de riesgo para el desarrollo del estado nutricional alterado del paciente son la ocupación de los padres, que son campesinos con estudios de primaria incompleta y el antecedente de infecciones respiratorias de repetición. La ocupación de los padres puede influir en la disponibilidad y acceso a una alimentación adecuada para el niño. Además, las infecciones respiratorias de repetición pueden afectar la ingesta y absorción de nutrientes, lo que puede contribuir al estado nutricional alterado del paciente. Es importante considerar estos factores en la evaluación y manejo del niño con problemas gastrointestinales y nutricionales."
                        },

                        { "Acude a consulta una paciente con erupciones cutáneas de 48 horas de evolución en tronco y extremidades, se observan lesiones maculares eritematovioláceas extensas con ampollas y erosiones, así como lesiones erosivo costrosas de mucosa labial y oral. Tiene antecedentes de hipertensión e hiperuricemia, tratadas con amlodipino y alopurinol. Hace 1 semana presentó dolor en el área lumbar, que trato con diclofenaco. En la exploración física, se identifica que al pasar los dedos sobre la piel ésta se despega fácilmente.",
                                "Desprendimiento de la piel e inicio del tratamiento con diclofenaco",
                                "Aparición de erupciones cutáneas 48 horas antes, asociada a hiperuricemia",
                                "Presencia de lesiones erosivo-costrosas en mucosa y consumo crónico de alopurinol",
                                "Desprendimiento de la piel e inicio del tratamiento con diclofenaco",
                                "Los datos que orientan el diagnóstico de este paciente son el desprendimiento de la piel y el inicio del tratamiento con diclofenaco. La presencia de lesiones cutáneas extensas con ampollas y erosiones, así como el desprendimiento de la piel al pasar los dedos, sugieren la posibilidad de un efecto adverso al medicamento diclofenaco. Es importante considerar esta asociación para el diagnóstico y manejo adecuado de la paciente, especialmente en presencia de antecedentes de hipertensión e hiperuricemia, y el uso concomitante de amlodipino y alopurinol."
                        },
                        { "Un lactante de 13 meses es llevado a consulta por presentar fiebre intermitente de 38 °C en las últimas 24 horas que cede con antipiréticos, además presenta rinorrea hialina abundante y los seca progresiva en accesos. En la exploración física se encuentra hidratado, afebril, aunque con dificultad respiratoria manifestada por tiraje intercostal leve y retracción xifoidea también leve, se auscultan estertores crepitantes finos y sibilancias al final de la espiración, por lo que se sospecha de bronquiolitis.",
                                "Neumonía",
                                "Laringotraqueobronquitis",
                                "Crisis asmática",
                                "Neumonía",
                                "El diagnóstico diferencial del cuadro se inclina hacia la neumonía. Aunque la bronquiolitis es una sospecha inicial debido a la presencia de sibilancias y dificultad respiratoria, la fiebre intermitente y la abundante rinorrea hialina también pueden ser indicativos de neumonía. La presencia de estertores crepitantes finos puede sugerir una consolidación pulmonar, lo que refuerza la posibilidad de neumonía como causa del cuadro clínico en este lactante de 13 meses."
                        },

                        { "Una paciente de 21 años previamente sana acude por dolor abdominal en fosa iliaca derecha, intenso y asociado a náuseas sin vómito. Sus antecedentes son menarca a los 12 años ciclos regulares sin dolor de 28x5, inicio de la vida sexual activa a los 19 años, con una pareja sexual, sin uso de método de planificación familiar y última menstruación hace 3 semanas. En la EF, se registra taquicardia leve, eutérmica, discreta palidez de tegumentos, mucosa oral regularmente hidratada, buen patrón respiratorio. Abdomen blando, depresible, no visceromegalias y peristalsis presente. Signo de Rovsing y Blumberg. Signos de Hegar, Godell y Giordano negativos.",
                                "Apendicitis aguda",
                                "Embarazo ectópico",
                                "Infección de vías urinarias",
                                "Embarazo ectópico",
                                "De acuerdo con los hallazgos clínicos, el diagnóstico presuntivo en esta paciente de 21 años es un embarazo ectópico. Los síntomas de dolor abdominal intenso en fosa iliaca derecha y la presencia de taquicardia pueden estar asociados a un embarazo ectópico, una condición en la que el óvulo fertilizado se implanta fuera del útero. Dado el inicio de la vida sexual activa sin uso de planificación familiar y la última menstruación hace 3 semanas, existe un alto riesgo de embarazo ectópico en esta situación clínica."
                        },
                        {
                                "Se atiende a un recién nacido en sus primeros minutos de vida extrauterina. Cuenta con antecedentes de padres sanos de 25 años control prenatal iniciado en el segundo trimestre, en el que la madre tuvo infección de vías urinarias, sin remisión al tratamiento se presenta en unidad hospitalaria en fase expulsiva El paciente nace por vía vaginal Ilora y respira al nacimiento. Presenta un peso al nacimiento de 1 500 g. por Ballard se considera 32 SDG A los 5 minutos del nacimiento inicia dificultad respiratoria a expensas de tiraje intercostal, aleteo nasal y quejido espiratorio",
                                "Ultrasonido",
                                "Radiografía simple",
                                "Tomografía simple",
                                "Radiografía simple",
                                "El estudio de imagen que se debe realizar en la región del tórax es una Radiografía simple."
                        },

                        {"Un paciente de 8 meses sin antecedentes de importancia para su padecimiento actual presenta hace 3 días rinorrea hialina y rechazo parcial a la vía oral con remisión espontánea. Hoy por la madrugada inicia de forma súbita tos referida como perruna, fiebre y disfonía. En la exploración física, se encuentra orofaringe con hiperemia, estridor laringeo y dificultad respiratoria a expensas de aleteo nasal. El resto de la exploración, sin datos patológicos aparentes.¿Cuál es el diagnóstico presuntivo?",
                "Laringotraqueitis",
                        "Epiglotitis",
                        "Cuerpo extraño en la vía aérea",
                        "Laringotraqueitis",
                        "El diagnóstico presuntivo en este caso es laringotraqueitis. Los síntomas de tos perruna, fiebre y estridor laringeo son característicos de esta enfermedad viral que afecta las vías respiratorias superiores, comúnmente conocida como la tos ferina. Es importante realizar una evaluación médica para confirmar el diagnóstico y brindar el tratamiento adecuado para aliviar los síntomas y evitar complicaciones."


                },

            {"En una localidad de 10,000 habitantes se realizó un estudio para conocer la  prevalencia de insuficiencia renal y sus factores asociados en personas o más. Se recolectó información sobre variables sociodemográficas, sexo, edad, estado civil y nivel socioeconómico. Si en la descripción estadística se representa la distribución por edad, se midió en 'años cumplidos', se puede utilizar el grafico…",
                "Diagrama de dispersión",
                    "Barras simples",
                        "Polígono de frecuencia",
                        "Polígono de frecuencia"},


            {"Una mujer de 35 años con diagnóstico de depresión y ansiedad en tratamiento con sertralina y clonazepam tiene dolor en el cuello después de haber cargado una bolsa pesada. Después de la exploración física, el médico concluye que se trata de un dolor muscular e inicia tratamiento con meloxicam y cansoprodol.¿Cuál es el riesgo más frecuente de combinar los fármacos de base con el medicamento recetado?",
                "Depresión del sistema nervioso central",
                        "Sangrado de tubo digestivo alto",
                        "Insomnio de mantenimiento",
                        "Insomnio de mantenimiento"},

            {"Un paciente de 7 años acude con la madre por 3 días con fiebre de 38 °C, que no cede con paracetamol, presenta disfagia, rinorrea, mialgias, artralgias y anorexia. En la exploración física se observa peso 22 kg, talla 90 cm, temperatura 38.5°C cuello con adenomegalias de 0.5 mm, en la cadena cervical, oídos normales, orofaringe hiperémica edematosa, exudado purulento, grisáceo y amígdalas Hipertróficas III.¿Cuál es el tratamiento farmacológico en este paciente, según el Centor score?",
                    "Penicilina V, 250 mg vía oral por 7 días o penicilina benzatínica, 600 000 de unidad IM dosis única.",
                "Trimetoprim sulfametoxazol, 10 mg/kg/día por 10 días o amoxicilina, 50 mg/kg/día por 7 días",
                        "Cefotaxima, 50 mg/kg/día por 5 días o azitromicina, 40 mg/kg/día por 5 días",
                        "Penicilina V, 250 mg vía oral por 7 días o penicilina benzatínica, 600 000 de unidad IM dosis única."},

            {"Un paciente de 40 años acude a consulta porque ha sentido, desde hace 1 mes, una masa en la espalda que ha incrementado de tamaño y que actualmente lo produce dolor al acostarse en la exploración física se palpa tumoración subcutánea esférica blanda en región infraescapular izquierda, móvil y de aproximadamente 8 cm. No se observan cambios de coloración ni temperatura en la piel Se realiza ultrasonido, en el que se reporta imagen hipogénica subcutánea, elíptica bien delimitada, compresible y con estrías lineales reflectantes cortas paralelas a la piel.¿Cuál es el plan inicial de manejo quirúrgico para el paciente?",
                "Resección completa",
                        "Biopsia por incisión",
                        "Resección con márgenes quirúrgicos",
                        "Resección completa"},

            {"Una paciente de 31 años acude al servicio de Urgencias por presentar odinofagia, cefalea y ataque al estado general, además de fiebre no cuantificada de 2 días de evolución entre sus antecedentes comenta que su abuela es diabética, menciona tener alergia a AINE y aumento de peso desde hace 2 años atribuido al sedentarismo. En la exploración física se registra TA de 130/85 mmHg: FC de 80/min, FR de 18/min, T de 37 5 °C IMC 30.3 kg/m2 y circunferencia abdominal de 90 cm. Se observa hiperemia conjuntival, labios y mucosa oral secos, faringe y amígdalas hiperémicas, criptas amigdalinas con secreción purulenta y se palpan ganglios submaxilares bilaterales. En los resultados de laboratorio se reportan leucocitos de 10.500/mm, neutrófilos de 62%, hemoglobina de 12.7 mg/dL, hematocrito de 37.2%, glucosa de 100 mg/dL, HbA1c de 6%, colesterol total de 181 mg/dL, HDL de 48 mg/dL, LDL de 120 mg/dL y triglicéridos de 187 mg/dL. Se inicia manejo para cuadro de faringoamigdalitis y se indican medidas higiénico-dietéticas de actividad física y metformina.",
                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                "Monitorear cada 5 años la progresión metabólica de la glucosa para revalorar riesgos",
                        "Establecer un objetivo de peso y considerar terapia dual para control de glucosa",
                        "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso"},


            {"Niño de 4 años, previamente sano y con esquema de inmunizaciones completo, inicia padecimiento hace 4 días con hipertermia cuantificada en 38.5 °C, odinofagia y tos escasa no productiva. En la EF se encuentra hiperemia faríngea, exudado blanquecino amigdalino y sin datos de dificultad respiratoria, el resto, sin aparente alteración. Inicia tratamiento empírico a base de amoxicilina. A las 48 horas, el cultivo faríngeo muestra estreptococo beta hemolítico",
                "Cambiar a penicilina G 50.000 unidades/kg por dosis IV cada 12 horas por 10 días",
                        "Cambiar a eritromicina vía oral de 30 a 50 mg/kg/día, durante 10 días",
                        "Cambiar a amikacina intramuscular de 7.5 mg/kg cada 12 horas por 3 días",
                        "Cambiar a penicilina G 50.000 unidades/kg por dosis IV cada 12 horas por 10 días"},

            {"Una paciente de 58 años acude al servicio de Urgencias porque presenta hematemesis franca abundante en dos ocasiones con presencia de coágulos, se acompaña de dolor abdominal de intensidad 8/10, diaforesis, astenia y adinamia. Menciona haber presentado datos de evacuaciones melénicas hace 5 días. Entre sus antecedentes refiere tabaquismo positivo, enfermedad ácido péptica con Helicobacter pylori diagnosticada hace 3 años, pero sin manejo médico, En la exploración física se registra TA de 100/50, FC de 120/min, FR de 18/min, T de 36 ºC y SaO2 de 94%. Se observa con mucosas pálidas ++, diaforética, con abdomen blando, dolor generalizado a la palpación, más acentuado en epigastrio e hipocondrio derecho, peristalsis disminuida, sin datos de irritación peritoneal y tacto rectal positivo a sangrado. Además de iniciar reanimación hídrica, ¿Cuál es el plan inicial para el manejo médico de la paciente?",
                "Valorar hemotransfusión, administrar inhibidores de bomba de protones y mantener vía aérea permeable",
                "Administrar antibióticos de amplio espectro, manejo con betabloqueadores y ligadura.",
                "Terapia farmacológica con terlipresina y antibióticos profilácticos, escleroterapia endoscópica.",
                "Valorar hemotransfusión, administrar inhibidores de bomba de protones y mantener vía aérea permeable"}

                };
                break;
            case 2:
                preguntas = new String[][]{
                        {"Pregunta", "Opción 1", "Opción 2", "Opción 3", "repetir opcion correcta", "retroalimentacion"},
                        {"2Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"2Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"2Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"2Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"}
                };
                break;
            case 3:
                preguntas = new String[][]{
                        {"Pregunta 1 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 2 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 3 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 4 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 5 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 6 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 7 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
                        {"Pregunta 8 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 3", "retroalimentacion"},
                        {"Pregunta 9 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 1", "retroalimentacion"},
                        {"Pregunta 10 del botón 3", "Opción 1", "Opción 2", "Opción 3", "Opción 2", "retroalimentacion"},
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

        preguntaTextView.setText(preguntas[indiceRealPregunta][0]);

        List<String> opciones = Arrays.asList(preguntas[indiceRealPregunta][1], preguntas[indiceRealPregunta][2], preguntas[indiceRealPregunta][3]);
        Collections.shuffle(opciones);
        opcion1Button.setText(opciones.get(0));
        opcion2Button.setText(opciones.get(1));
        opcion3Button.setText(opciones.get(2));
        retroalimentacion = preguntas[indiceRealPregunta][5];
        respuestaCorrecta = preguntas[indiceRealPregunta][4];

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

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExamenCompleto.this);
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

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExamenCompleto.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExamenCompleto.this);
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
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ExamenCompleto.this);
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
        progressBar.setMax(35);
        puntuacionActual = puntuacion;
        //progres bar
        progressBar.setProgress(preguntaActual + 1);
        preguntaActual++;
        if (preguntaActual < 35) {
            mostrarPregunta(preguntaActual);
        } else {
            countDownTimer.cancel();
            finish();
            mostrarFinalActivity(null);
        }
    }
    public void mostrarFinalActivity (View view){
        countDownTimer.cancel();
        Intent intent = new Intent(ExamenCompleto.this, com.kmpegel.medi.ui.Preguntas.FinalActivity.class);
        intent.putExtra("puntuacion", puntuacionActual);
        tipo=1;
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }
    public void cronometro () {
        countDownTimer = new CountDownTimer(90000, 1000) {
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
