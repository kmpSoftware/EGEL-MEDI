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
                                "Depresión",
                                "Manejo del estrés",
                                "Abuso de sustancias",
                                "Manejo del estrés",
                                "Es importante explorar el manejo del estrés de la paciente, ya que los síntomas que presenta pueden estar relacionados con el estrés emocional. Brindarle herramientas para manejar el estrés puede ayudar a mejorar su bienestar."},

                        {"Una paciente de 75 años es llevada a consulta por su hija, ya que sufrió una caída desde el plano de sustentación. Además, la hija refiere que percibe en su madre aislamiento social, descuido personal y olvidos recurrentes en el último año. Se observa que la paciente está orientada, cooperadora, aunque levemente distraída. Durante la exploración, se registran signos vitales y neurológicos normales, pero se observa una pequeña abrasión en la rodilla derecha debido a la caída. Además, se registra disminución en la visión lejana y en la agudeza auditiva. ¿Cuál es el cambio biológico que está relacionado con las alteraciones en la paciente?",
                                "Espesor y rigidez del cristalino",
                                "Aumento de hueso esponjoso en oído medio",
                                "Colección de sangre subdural",
                                "Aumento de hueso esponjoso en oído medio",
                                "El aumento de hueso esponjoso en el oído medio puede ser el cambio biológico relacionado con las alteraciones en la paciente, lo cual puede contribuir a la disminución de la visión lejana y la agudeza auditiva que se observan en la evaluación."},

                        {"Una paciente de 68 años presenta un cuadro de tos con expectoración blanquecina y ortopnea. Menciona que el día de hoy se agudizan los síntomas y presenta disnea. Reside en una comunidad rural y vive con cuatro familiares más. Su casa habitación cuenta con dos cuartos, piso de tierra, techo de lámina, autoconsumo y cocina con leña. Durante la exploración física, se registran los siguientes valores: TA 120/80 mmHg, FC de 78 lpm, FR 22 lpm, T 36.9°C y saturación de oxígeno (sato2) de 98%. ¿Cuál es el factor del entorno de la paciente que se asocia con su problema de salud?",
                                "Exposición a biomasa",
                                "Hacinamiento",
                                "Zoonosis",
                                "Exposición a biomasa",
                                "La exposición a biomasa, como la cocina con leña, puede estar asociada con los síntomas respiratorios que la paciente presenta, como la tos y la disnea. Es importante evaluar y abordar este factor del entorno para mejorar su salud."},

                        {"Una paciente de 55 años acude a consulta por control debido a que tiene antecedentes de diabetes mellitus de 5 años de evolución. En la exploración física, se registran signos normales, con un IMC de 31 kg/m2 y un perímetro de cintura de 102 cm. Al preguntarle sobre los ajustes de estilo de vida, comenta que no ha logrado incorporar actividad física constante en la rutina ni reducir el número de veces que come en la calle. Esto se debe a que trabaja como conductor de una aplicación por casi 12 horas al día y llega a su casa en la madrugada para cumplir con las horas requeridas por el dueño del vehículo. El paciente menciona que, a partir de su cita de control anterior, intentó caminar por 30 minutos junto con su esposa durante las mañanas antes de iniciar sus viajes, pero lo asaltaron cerca de su domicilio y lo despojaron de su teléfono celular, que es parte de sus herramientas de trabajo. Como resultado, tuvo que trabajar más horas para subsanar la pérdida y suspendió las caminatas. Además, su esposa manifiesta el deseo de mudarse debido a la inseguridad en su colonia a raíz del asalto. ¿Qué aspecto del contexto social del paciente afecta su estado actual de salud?",
                                "Precarización laboral",
                                "Ambiente familiar",
                                "Inseguridad en su colonia",
                                "Precarización laboral",
                                "La precarización laboral del paciente, debido a sus largas jornadas de trabajo y la necesidad de cubrir una cantidad específica de horas, está afectando su capacidad para realizar cambios en su estilo de vida y cuidar de su salud. Esta situación puede tener un impacto negativo en su bienestar general."},

                        {"¿Qué enfoque de desarrollo implica una planificación cuidadosa de las fases del proyecto y una secuencia de etapas lineales?",
                                "Flujo de proceso en cascada",
                                "Flujo de proceso en espiral",
                                "Flujo de proceso evolutivo",
                                "Flujo de proceso en cascada",
                                "El flujo de proceso en cascada implica una planificación cuidadosa y una secuencia de etapas lineales para completar un proyecto de software."},

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
                        "Polígono de frecuencia",
                        "Polígono de frecuencia"},


            {"Una mujer de 35 años con diagnóstico de depresión y ansiedad en tratamiento con sertralina y clonazepam tiene dolor en el cuello después de haber cargado una bolsa pesada. Después de la exploración física, el médico concluye que se trata de un dolor muscular e inicia tratamiento con meloxicam y cansoprodol.¿Cuál es el riesgo más frecuente de combinar los fármacos de base con el medicamento recetado?",
                "Depresión del sistema nervioso central",
                        "Sangrado de tubo digestivo alto",
                        "Insomnio de mantenimiento",
                    "Insomnio de mantenimiento",
                        "Insomnio de mantenimiento"},

            {"Un paciente de 7 años acude con la madre por 3 días con fiebre de 38 °C, que no cede con paracetamol, presenta disfagia, rinorrea, mialgias, artralgias y anorexia. En la exploración física se observa peso 22 kg, talla 90 cm, temperatura 38.5°C cuello con adenomegalias de 0.5 mm, en la cadena cervical, oídos normales, orofaringe hiperémica edematosa, exudado purulento, grisáceo y amígdalas Hipertróficas III.¿Cuál es el tratamiento farmacológico en este paciente, según el Centor score?",
                    "Penicilina V, 250 mg vía oral por 7 días o penicilina benzatínica, 600 000 de unidad IM dosis única.",
                "Trimetoprim sulfametoxazol, 10 mg/kg/día por 10 días o amoxicilina, 50 mg/kg/día por 7 días",
                        "Cefotaxima, 50 mg/kg/día por 5 días o azitromicina, 40 mg/kg/día por 5 días",
                        "Penicilina V, 250 mg vía oral por 7 días o penicilina benzatínica, 600 000 de unidad IM dosis única.",
                        "Penicilina V, 250 mg vía oral por 7 días o penicilina benzatínica, 600 000 de unidad IM dosis única."},

            {"Un paciente de 40 años acude a consulta porque ha sentido, desde hace 1 mes, una masa en la espalda que ha incrementado de tamaño y que actualmente lo produce dolor al acostarse en la exploración física se palpa tumoración subcutánea esférica blanda en región infraescapular izquierda, móvil y de aproximadamente 8 cm. No se observan cambios de coloración ni temperatura en la piel Se realiza ultrasonido, en el que se reporta imagen hipogénica subcutánea, elíptica bien delimitada, compresible y con estrías lineales reflectantes cortas paralelas a la piel.¿Cuál es el plan inicial de manejo quirúrgico para el paciente?",
                "Resección completa",
                        "Biopsia por incisión",
                        "Resección con márgenes quirúrgicos",
                        "Resección completa",
                        "Resección completa"},

            {"Una paciente de 31 años acude al servicio de Urgencias por presentar odinofagia, cefalea y ataque al estado general, además de fiebre no cuantificada de 2 días de evolución entre sus antecedentes comenta que su abuela es diabética, menciona tener alergia a AINE y aumento de peso desde hace 2 años atribuido al sedentarismo. En la exploración física se registra TA de 130/85 mmHg: FC de 80/min, FR de 18/min, T de 37 5 °C IMC 30.3 kg/m2 y circunferencia abdominal de 90 cm. Se observa hiperemia conjuntival, labios y mucosa oral secos, faringe y amígdalas hiperémicas, criptas amigdalinas con secreción purulenta y se palpan ganglios submaxilares bilaterales. En los resultados de laboratorio se reportan leucocitos de 10.500/mm, neutrófilos de 62%, hemoglobina de 12.7 mg/dL, hematocrito de 37.2%, glucosa de 100 mg/dL, HbA1c de 6%, colesterol total de 181 mg/dL, HDL de 48 mg/dL, LDL de 120 mg/dL y triglicéridos de 187 mg/dL. Se inicia manejo para cuadro de faringoamigdalitis y se indican medidas higiénico-dietéticas de actividad física y metformina.",
                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                "Monitorear cada 5 años la progresión metabólica de la glucosa para revalorar riesgos",
                        "Establecer un objetivo de peso y considerar terapia dual para control de glucosa",
                    "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                        "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso"},


            {"Niño de 4 años, previamente sano y con esquema de inmunizaciones completo, inicia padecimiento hace 4 días con hipertermia cuantificada en 38.5 °C, odinofagia y tos escasa no productiva. En la EF se encuentra hiperemia faríngea, exudado blanquecino amigdalino y sin datos de dificultad respiratoria, el resto, sin aparente alteración. Inicia tratamiento empírico a base de amoxicilina. A las 48 horas, el cultivo faríngeo muestra estreptococo beta hemolítico",
                "Cambiar a penicilina G 50.000 unidades/kg por dosis IV cada 12 horas por 10 días",
                        "Cambiar a eritromicina vía oral de 30 a 50 mg/kg/día, durante 10 días",
                        "Cambiar a amikacina intramuscular de 7.5 mg/kg cada 12 horas por 3 días",
                        "Cambiar a penicilina G 50.000 unidades/kg por dosis IV cada 12 horas por 10 días",
                        "Cambiar a penicilina G 50.000 unidades/kg por dosis IV cada 12 horas por 10 días"},

            {"Una paciente de 58 años acude al servicio de Urgencias porque presenta hematemesis franca abundante en dos ocasiones con presencia de coágulos, se acompaña de dolor abdominal de intensidad 8/10, diaforesis, astenia y adinamia. Menciona haber presentado datos de evacuaciones melénicas hace 5 días. Entre sus antecedentes refiere tabaquismo positivo, enfermedad ácido péptica con Helicobacter pylori diagnosticada hace 3 años, pero sin manejo médico, En la exploración física se registra TA de 100/50, FC de 120/min, FR de 18/min, T de 36 ºC y SaO2 de 94%. Se observa con mucosas pálidas ++, diaforética, con abdomen blando, dolor generalizado a la palpación, más acentuado en epigastrio e hipocondrio derecho, peristalsis disminuida, sin datos de irritación peritoneal y tacto rectal positivo a sangrado. Además de iniciar reanimación hídrica, ¿Cuál es el plan inicial para el manejo médico de la paciente?",
                "Valorar hemotransfusión, administrar inhibidores de bomba de protones y mantener vía aérea permeable",
                "Administrar antibióticos de amplio espectro, manejo con betabloqueadores y ligadura.",
                "Terapia farmacológica con terlipresina y antibióticos profilácticos, escleroterapia endoscópica.",
                    "Valorar hemotransfusión, administrar inhibidores de bomba de protones y mantener vía aérea permeable",
                "Valorar hemotransfusión, administrar inhibidores de bomba de protones y mantener vía aérea permeable"},



                        {
                                "Un paciente de 28 años es llevado al Servicio de Urgencias debido a que cerca de la clínica sufrió un colapso súbito. La primera valoración es que está inconsciente, con movimientos respiratorios de lucha, cianótico y con pulso carotídeo presente. ¿Cuál es la prioridad terapéutica en este caso? ",
                                "Colocar en plano duro e iniciar compresiones torácicas",
                                "Verificar que la vía aérea esté permeable",
                                "Iniciar respiración con bolsa-válvula-mascarilla",
                                "Verificar que la vía aérea esté permeable",
                                "Verificar que la vía aérea esté permeable"
                        },
                        {
                                "Al revisar una investigación se identifica la siguiente información: \"Para evaluar el efecto del tabaco en la aparición de la enfermedad se crearon dos grupos uno de pacientes del Hospital General de México con tiroiditis de Hashimoto, que fumaban y que acudieron a la consulta del Servicio de Endocrinología, y otro control de pacientes de la misma población, pero que no presentaban el factor de riesgo”. ¿Cuál es el sesgo en este estudio? ",
                                "Selección",
                                "Información",
                                "Confusión",
                                "Selección",
                                "Selección"
                        },
                        {
                                "Con base en las mediciones de glucosa capilar de las personas con diabetes en una población, un médico detecta que la mayoría de las tomas de uno de sus pacientes (X) se encuentran por encima del promedio general. La media general de la población es de 85.5 mg/dL de glucosa con una desviación estándar de 4.65 mg/dl mientras que la última toma del paciente x es de 110 mg/dL. ¿Cuál es el valor Z del paciente x con base en los datos de la población?",
                                "2.118",
                                "3.118 ",
                                "4.118",
                                "4.118",
                                "4.118"
                        },
                        {
                                "En un estudio se aplicó una prueba de anticuerpos IgM/IgG para detectar Covid-19. La confirmación del diagnóstico fue mediante PCR, con una n=45, la tasa total de detección de anticuerpos fue de 92%, en pacientes hospitalizados, y de 79% en los no hospitalizados. Los resultados mostraron que la detección total de las inmunoglobulinas IgM e IgG fue de 63% en pacientes con < 2 semanas desde el inicio de la enfermedad, de 85% en los no hospitalizados con > 2 semanas de duración de la enfermedad, y de 91% en hospitalizados con > 2 semanas de duración de la enfermedad. Como resultado final, se determinó que la especificidad de la prueba de anticuerpos fue de 97% en 69 muestras de suero/plasma. Identifique la opción que interpreta los resultados del estudio",
                                "Proporciona una validación de la prueba de covid-19 IgM/IgG y, a su vez, determina que existe mayor especificidad en el diagnóstico por RT-PCR",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblacional",
                                "Demuestra que la detección de anticuerpos de covid-19 IgM/IgG tiene mayor especificidad y sensibilidad en los pacientes con una evolución menor a 2 semanas",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblaciona",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblaciona"
                        },
                        {
                                "Se realizó un estudio en el que se registró el peso al nacimiento de una muestra de 300 bebés que a su vez, fueron divididos en dos grupos, los nacidos de madres que tomaron un complemento vitamínico especial y aquellos cuya madre solo tomo el recomendado por su médico tratante. Se desea determinar si hay diferencia entre el peso al nacimiento y el uso del multivitamínico especial. Tome en cuenta que, al analizar los datos de peso en los grupos, este tiene una distribución normal y se asumen varianzas iguales. ¿Qué prueba estadística se debe implementar?",
                                "Prueba paramétrica t de student",
                                "Prueba estadística U de Mann-Whitney",
                                "El test de Kruskal-Wallis",
                                "Prueba paramétrica t de student",
                                "Prueba paramétrica t de student"
                        },
                        {
                                "Un grupo de estudiantes de medicina lleva a cabo una investigación con la que pretende evaluar la prevalencia de tabaquismo en una universidad pública. Hasta este momento, han realizado el protocolo, que presentaron ante el comité de ética en investigación, aplicaron los instrumentos para la recolección de datos y comenzaron el análisis estadístico. Dado el avance de trabajo. ¿Cuál es el tipo de reporte de investigación por presentar?",
                                "Cartel",
                                "Ponencia",
                                "Artículo",
                                "Artículo",
                                "Artículo"
                        },
                        {
                                "Se planea realizar un ensayo clínico multicéntrico para comprobar la eficacia de una nueva vacuna en la población adulta. Se reclutarán 20 000 adultos de entre 18 y 59 años, sin que las mujeres que participen estén embarazadas. Se asignarán a uno de tres grupos; en dos se administrarán esquemas diferentes con la sustancia activa y en el tercero se suministrará placebo como control. Se empleará el método doble ciego para minimizar la posibilidad de que algún sesgo interfiera en los resultados. ¿Qué estrategia de muestreo se debe emplear en el estudio?",
                                "Sistemático",
                                "Estratificado",
                                "Aleatorio simple",
                                "Aleatorio simple",
                                "Aleatorio simple"
                        },
                        {
                                "En el Servicio de Neurología de un hospital de segundo nivel de atención, se ha observado un aumento de la consulta de pacientes con migraña Crónica. El reporte del último semestre demuestra que cada mes se diagnostican por lo menos 50 nuevos casos. La edad oscila entre 35 y 50 años. El medicamento que se utiliza como profilaxis para evitar los episodios migrañosos es un anticonvulsivante llamado topiramato. la enfermedad tanto a nivel individual como poblacional Para sustentar la decisión del uso del fármaco en la investigación basada en evidencia, ¿qué estrategia debe seguirse?",
                                "O Revisión sistemática en Cochrane de diez estudios controlados aleatorizados, con 100 pacientes ",
                                " Análisis de 20 artículos de revistas indexadas y diez libros de texto especializados en neurología ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales ",
                                "Revisión basada en la experiencia y la evidencia de los resultados de los 300 pacientes atendidos por los profesionales "
                        },
                        {
                                "El 31 de diciembre de 2019 se identificó un nuevo coronavirus (SARS-CoV. 2), que causa la enfermedad clínica covid-19. Si se desea conocer las características de todas las pruebas de diagnóstico disponibles para el SARS-CoV-2, incluidos los parámetros de sensibilidad y especificidad, así como cocientes de probabilidad positivos y negativos, ¿qué estudio se debe consultar?",
                                "Descriptivo",
                                "De casos y controles",
                                "Metaanálisis",
                                "De casos y controles",
                                "De casos y controles"
                        },
                        {
                                "Una paciente de 20 años asiste a consulta médica debido a que hace 1 año su madre fue diagnosticada con cáncer de mama en etapa clínica IV y falleció hace 3 meses a causa de éste. Su abuela materna, también falleció de cáncer de mama, por lo que está preocupada y quiere saber qué riesgo tiene ella de padecer la enfermedad. Se requiere realizar una búsqueda de información al respecto. ¿Qué medio se debe utilizar para la búsqueda de información y orientar a la paciente? ",
                                "PubMed",
                                "Cochrane",
                                "UptoDate",
                                "Cochrane",
                                "Cochrane"
                        },
                        {
                                " Una mujer de 25 años con disuria, polaquiuria y urgencia miccional es diagnosticada por el médico con infección de vías urinarias. Con base en la revisión de resultados de intervención se le receta un tratamiento con trimetoprim por únicamente 3 días y se le explica que las revisiones sistemáticas de ensayos clínicos con información al respecto indican que el tratamiento de 3 días es tan efectivo como el de 7 días y tienen muy poco riesgo de sesgo. ¿Qué nivel de evidencia tiene la decisión terapéutica del médico?",
                                "II",
                                "Ib",
                                "Ia",
                                "Ib",
                                "Ib"
                        },
                        {
                                "Un hombre de 56 años acude a consulta por acúfenos y cefalea de 1 mes de evolución. Tiene antecedente do ser fumador y manejar mucho estrés laboral. Su padre es finado por IAM. La toma de TA es de 160/90 mmHg y los laboratorios reportan glucosa capilar de 125 mg/dL, albúmina de 150 mg/dL en orina y tasa de filtración glomerular (calculado) de 50 ml/min Con base en las manifestaciones clínicas y hallazgos de laboratorio, ¿cuál es el diagnóstico según la Guía de Practica Clínica sobre el diagnóstico y tratamiento de la hipertensión arterial en el primer nivel de atención?",
                                "Hipertensión arterial sistémica y diabetes mellitus tipo 2 de reciente diagnóstico con macroalbuminuria",
                                "Urgencia hipertensiva con intolerancia a la glucosa y síndrome nefrítico por la elevación de la tasa de filtración glomerular (calculado). ",
                                "Emergencia hipertensiva con daño renal por la microalbuminuria y disminución de la tasa de filtración glomerular",
                                "Urgencia hipertensiva con intolerancia a la glucosa y síndrome nefrítico por la elevación de la tasa de filtración glomerular (calculado). ",
                                "Urgencia hipertensiva con intolerancia a la glucosa y síndrome nefrítico por la elevación de la tasa de filtración glomerular (calculado). "
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
                                "Un hombre de 71 años llega al Servicio de Urgencias con dolor precordial de 3 horas de evolución que se ha ido intensificando hasta llegar a 10/10 en la escala visual analógica y que irradia hacia el brazo izquierdo. El paciente se encuentra diaforético y taquicárdico, en los estudios de laboratorio se detecta elevación de troponinas y en el electrocardiograma, elevación del segmento ST. Para integrar la mejor evidencia que sustente el manejo farmacológico del paciente, ¿qué acción se debe realizar según la metodología de la medicina basada en evidencia?",
                                "Aplicar la evidencia a la experiencia clínica y a las características del paciente",
                                "Identificar y jerarquizar la evidencia disponible",
                                " Evaluar la evidencia por calidad, relevancia y aplicabilidad",
                                "Identificar y jerarquizar la evidencia disponible",
                                "Identificar y jerarquizar la evidencia disponible"
                        },
                        {
                                "Una mujer de 38 años acude a consulta anual al centro de salud de su localidad. Como antecedentes menciona que su madre y su hermana tienen diabetes mellitus tipo 2. Su IMC es de 31 kg/m2 y tiene una glucemia en ayunas de 115 mg/dL. ¿Qué acción de formación le permite al médico integrar la evidencia de mayor impacto para el manejo del caso?",
                                "Revisar el material presentado en un congreso nacional ",
                                "Analizar información de la industria farmacéutica",
                                "Hacer una lectura crítica de metaanálisis sobre el tema clínico",
                                "Hacer una lectura crítica de metaanálisis sobre el tema clínico",
                                "Hacer una lectura crítica de metaanálisis sobre el tema clínico"
                        },
                        {
                                "Un paciente de 79 años es ingresado al servicio de Urgencias por presentar dolor y debilidad en las piernas que en los últimos días le imposibilita caminar e incluso moverlas. Su familiar informa que en la última semana presentó de forma súbita incontinencia urinaria e intestinal y pérdida de peso. En los resultados de laboratorio destaca hipercalcemia y en la radiografía de tórax se observan masas con aspecto irregular que indican malignidad y que orientan a la sospecha de cáncer o metástasis ósea. Se requiere solicitar biopsia para confirmar y estudios adicionales para la estatificación. A informar al paciente, se percibe agobiado y éste rechaza los procedimientos exploratorios adicionales. De acuerdo con el principio de no maleficencia del acto médico, ¿qué decisión se debe tomar?",
                                "Consultar con el familiar y enfatizar los riesgos de no realizar las pruebas ",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar",
                                "Reiterar la necesidad de realizar más pruebas y aceptar la decisión del paciente",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar"
                        },
                        {
                                "Un paciente de 55 años, con tabaquismo positivo (de cinco a siete cigarrillos al día desde hace 30 años), obesidad, sedentarismo, consumo de alcohol positivo de dos a tres copas diariamente además de llegar a la intoxicación etílica una o dos veces a la semana) e ingesta de carne 5 días por semana, presenta hipertensión arterial, hiperuricemia y gota. El hombre se niega a modificar su estilo de vida y prefiere un manejo médico exclusivamente. ¿Cuál es la conducta ética en este caso?",
                                "Llevar a cabo exclusivamente el manejo médico y respetar ante todo la autonomía del paciente.",
                                "Negar los medicamentos al paciente con la premisa de que el tratamiento abarca tanto lo médico como los cambios de estilo de vida",
                                "Establecer una estrategia médica integral que incluya grupos de apoyo y manejo nutricional.",
                                "Establecer una estrategia médica integral que incluya grupos de apoyo y manejo nutricional.",
                                "Establecer una estrategia médica integral que incluya grupos de apoyo y manejo nutricional."
                        },
                        {
                                "Una mujer de 65 años con antecedente de cáncer de mama hace 3 años que requirió manejo con quimioterapia y radioterapia, actualmente presenta cáncer de mama recurrente. Se informa a la paciente su diagnóstico, pronóstico y tratamiento. Se le propone reiniciar el manejo con quimioterapia, pero ella lo rechaza e indica que ya no quiere recibir tratamiento alguno y solicita el alta hospitalaria. ¿Cuál es la decisión médica que privilegia el derecho de la paciente? O Dar de alta a la paciente para que tome las decisiones que más le convengan sin involucrar al personal de salud",
                                "ofrecer distintas opciones de tratamiento de tipo paliativo, en tanto respeta su decisión y  autodeterminación",
                                "Administrar la quimioterapia como una medida de atención en correspondencia con el principio de respeto a la vida",
                                "Dar de alta a la paciente para que tome las decisiones que más le convengan sin  involucrar al personal de salud ",
                                "ofrecer distintas opciones de tratamiento de tipo paliativo, en tanto respeta su decisión y  autodeterminación",
                                "ofrecer distintas opciones de tratamiento de tipo paliativo, en tanto respeta su decisión y  autodeterminación"
                        },
                        {
                                "Una paciente de 40 años, con IMC de 35 kg/m2, acude a consulta para solicitar un análisis general a fin de atender su problema de obesidad. Comenta que hace 4 semanas acudió a su centro de salud y fue remitida al Servicio de Nutrición, donde le aplicaron un protocolo consistente en cambios a su estilo de vida. En la misma consulta le realizaron un perfil lipídico, una medición de glucosa y un perfil tiroideo, y los resultados fueron normales. Como ya pasó 1 mes y no perdió peso, la paciente pide que se le vuelvan a hacer todos los análisis para solucionar su problema. Atendiendo al principio de justicia, ¿cómo se debe proceder?",
                                "O Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve",
                                "O Respetando la decisión de la paciente, se le debe repetir el análisis",
                                "O con el objetivo de analizar detalladamente su padecimiento, se deben agregar los mismos estudios",
                                "O Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve",
                                "O Al tener un análisis similar realizado recientemente, no se debe repetir después de un lapso tan breve"
                        },
                        {
                                "Una paciente de 17 años acude a consulta por ausencia de menstruación desde hace 35 días. Manifiesta ciclos regulares de 28 x 5. La paciente es sexualmente activa, no utiliza ningún método de planificación familiar, cuenta con una prueba positiva de embarazo y desea interrumpirlo. Esto ocurre en la Ciudad de México, donde el aborto es legal, libre y a petición antes de las 12 SDG. El médico tratante no está de acuerdo con la decisión de la paciente, por lo que decide no intervenir en el procedimiento y la canaliza al grupo de especialistas correspondiente. ¿Cuál es la justificación que sustenta la decisión del médico?",
                                "O Aludir a la omisión médica",
                                "O Salvaguardar su prestigio profesional",
                                "O Apelar a la objeción de conciencia",
                                "O Aludir a la omisión médica",
                                "O Aludir a la omisión médica"
                        },
                        {
                                "En el Servicio de Ginecología y Obstetricia de un hospital hay un incremento en la atención de pacientes, por lo que se toma la decisión de que los médicos internos de pregrado valoren y atiendan todos los partos normales, mientras que los médicos residentes valorarán y atenderán a todas las pacientes con partos anormales y cesáreas. En la última guardia, una mujer fue valorada con una evolución normal, sin embargo, al momento de la atención del parto, se encontró un bebé macrosómico, lo que derivó en una distocia y atención de parto complicado. ¿Cuál es el aspecto que dio origen a la mala praxis?",
                                "Impericia",
                                "Imprudencia",
                                "Negligencia",
                                "Negligencia",
                                "Negligencia"
                        },
                        {
                                "Acude a consulta a un centro de salud un paciente con síndrome ulceroso que cumple criterios para iniciar tratamiento con omeprazol. En días previos, el médico tratante recibió la indicación por parte de la Dirección de únicamente prescribir medicamentos bloqueadores de H2 en pacientes con esta patología, ya que en farmacia hay un lote próximo a caducar. lapso tan breve ¿Cuál es el derecho del médico que se vulnera en este caso?",
                                "O Ejercer la profesión en forme libre y sin presiones de cualquier naturaleza",
                                "O Tener a su disposición los recursos que requiere su práctica profesional",
                                "O Salvaguardar su prestigio profesional",
                                "O Tener a su disposición los recursos que requiere su práctica profesional",
                                "O Tener a su disposición los recursos que requiere su práctica profesional"
                        },
                        {
                                "Un paciente de 42 años es llevado por personal del Servicio de Urgencias Médicas a una unidad hospitalaria pública. Se reporta en calidad de desconocido y que al deambular por la vía pública fue atropellado por un vehículo automotor (un camión), Pierde el estado de alerta de forma inmediata y personal de Urgencias lo atiende, lo intuban por Glasgow de 7, se canaliza vena periférica y lo llevan al hospital. Se solicita tomografía de cráneo por traumatismo craneoencefálico severo. Tiene un hematoma epidural occipitotemporal derecho, con desplazamiento de estructuras de línea media. Requiere manejo quirúrgico de urgencia. ¿Cuál es la obligación del médico en este caso?",
                                "O Solicitar de forma urgente apoyo a Trabajo Social para localizar a familiares que autoricen el evento quirúrgico, para respetar en todo momento los principios de autonomía y beneficencia",
                                "O iniciar el apoyo con cuidados paliativos con el objetivo de respetar el principio de beneficencia, ya que el pronóstico es malo para el paciente y no se cuenta con familiares",
                                "O Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia",
                                "O Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia",
                                "O Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia"
                        },
                        {
                                "Al valorar a una persona en el triage respiratorio de una unidad de reconversión covid, se encuentra que el paciente tiene una prueba positiva a SARS-CoV-2 sin embargo, también presenta un cuadro compatible con apendicitis aguda. Se indica que se debe canalizar al paciente a otra unidad que cuente con el servicio de cirugía general, por lo que sus padres deciden llevarlo por sus propios medios. Como parte del expediente clínico, la nota que se debe dar al paciente es de...",
                                "urgencias",
                                "interconsulta",
                                "traslado",
                                "traslado",
                                "traslado"
                        },
                        {
                                "Una paciente de 55 años fue derivada al Servicio de Ginecología por sospecha de cáncer de mama. El médico especialista le confirma el diagnóstico y de acuerdo con los resultados es necesario programarla para una mastectomía, por lo que se le pide que firme el consentimiento informado. La mujer se muestra abrumada ante las noticias y, al ser analfabeta funcional, no comprende claramente los términos empleados ni lo indicado en el consentimiento escrito de manera que se niega a firmar. ¿Cómo debe proceder el médico ante esta situación?",
                                "O Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar",
                                "O Explicar nuevamente a la paciente tanto el diagnostico como las opciones terapéuticas con palabras sencillas y, una vez que comprenda la información, solicitar que firme el consentimiento",
                                "O Solicitar al Departamento de Psicología acompañamiento para la paciente en su proceso de toma de decisiones para su tratamiento",
                                "O Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar",
                                "O Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar"
                        },
                        {
                                "Una mujer de 20 años, originaria de Chiapas acude a un hospital de la Ciudad de México para realizarse la interrupción legal del embarazo (ILE). Es atendida por un médico urgenciólogo quien calcula 11 SDG por FUM; le entrega la hoja de consentimiento informado e inmediatamente la ingresa para realizarle el procedimiento con técnica médica. Al término, se realiza dictamen médico de anomalías genéticas o congénitas y la refiere al módulo de consejería. El expediente clínico fue conformado por la historia clínica, hoja de ingreso y egreso hospitalarios, nota medica de atención de Urgencias y carta de consentimiento informado Con base en el artículo 144 del Código Penal de la Ciudad de México, ¿qué acciones de la práctica médica son legales?",
                                "O Realizar la ILE a las 11 SDG, entregar la hoja de consentimiento informado y llevar a cabo el procedimiento con técnica médica",
                                "O Calcular las SDG por FUM, realizar el procedimiento inmediatamente y al término hacer un dictamen médico de anomalías genéticas o congénitas",
                                "O Ser atendida por un médico urgenciólogo, canalizarla al módulo de consejería al término del procedimiento y conformar su expediente clínico.",
                                "O Realizar la ILE a las 11 SDG, entregar la hoja de consentimiento informado y llevar a cabo el procedimiento con técnica médica",
                                "O Realizar la ILE a las 11 SDG, entregar la hoja de consentimiento informado y llevar a cabo el procedimiento con técnica médica"
                        },
                        {
                                "En julio de 2020, ingresa al Servicio de Urgencias en estado comatoso una paciente de 50 años. Tiene antecedentes de diabetes mellitus tipo 2 desde hace 20 años y obesidad mórbida hace 30. A las 48 horas del internamiento, inicio con insuficiencia respiratoria aguda y falleció minutos después, asimismo, había presentado neumonía atípica las 24 horas previas. El cuadro clínico y debido al contexto epidemiológico, sugería una infección por el virus SARS-CoV-2, pero falleció antes de la prueba confirmatoria. ¿Qué enfermedad debe anotarse como causa básica de la muerte, con base en las características del certificado de defunción y la normativa vigente?",
                                "O Neumonía atípica",
                                "O Probable SARS-CoV-2",
                                "O Diabetes mellitus tipo 2",
                                "O Probable SARS-CoV-2",
                                "O Probable SARS-CoV-2"
                        },
                        {
                                "Una paciente de 23 años, primigesta, con 39 SDG, acude a centro de salud con actividad uterina regular de 2 horas. Tras ser evaluada se decide que el médico responsable del centro de salud le brinde atención del trabajo de parto, pues el hospital más cercano se encuentra a 2 horas; se obtiene un recién nacido de sexo masculino. ¿Quién debe expedir el certificado de nacimiento?",
                                "O La unidad médica de segundo nivel",
                                "O El médico tratante que acredita el nacimiento",
                                "O El médico pediatra que decida la madre",
                                "O El médico tratante que acredita el nacimiento",
                                "O El médico tratante que acredita el nacimiento"
                        },
                        {
                                "Se presenta en el Servicio de Urgencias una pareja que indica que su hijo de 2 meses lleva 15 minutos sin respirar. Se confirman la falta de signos vitales y se inician las maniobras de resucitación, que se extienden por 40 minutos, sin haber respuesta. Al ser interrogados, los padres mencionan que el lactante no tenía ninguna enfermedad previa diagnosticada y llevaba un seguimiento de niño sano normal en sus consultas de medicina familiar ¿Cómo se clasifica la muerte del infante según la medicina forense?",
                                "O Muerte real",
                                "O Muerte súbita",
                                "O Muerte natural",
                                "O Muerte súbita",
                                "O Muerte súbita"
                        },
                        {
                                "Un lactante de 7 meses presentó fiebre, malestar general y pérdida del apetito; fue tratado en casa con acetaminofén. A la mañana siguiente presentó fiebre alta, rash cutáneo, rechazo del alimento y de líquidos, y sus padres no pudieron despertarlo. Entonces, acudieron a consulta con un médico en una farmacia popular, el doctor, al término de su turno y ya por retirarse, los atendió de manera rápida y diagnosticó infección respiratoria alta, lo trato con medicación antifebril y les indicó que consultaran al Departamento de Emergencias si los síntomas empeoraban. En la tarde presentó convulsiones y fue llevado al Servicio de Urgencias. Se diagnosticó meningitis por neumococo que tuvo como secuela una sordera definitiva Con base en el caso ¿cuál es el tipo de responsabilidad civil del profesional en salud?",
                                "O Negligencia",
                                "O Impericia",
                                "O Por dolo",
                                "O Negligencia",
                                "O Negligencia"
                        },
                        {
                                "En una comunidad, una paciente de 34 años acude por presentar dolor abdominal agudo, intenso y persistente localizado en el epigastrio, el hipocondrio izquierdo y el derecho, que se irradia a la espalda. El dolor se acompaña de nausea y vómito que ha continuado hasta el momento. En la EF se encuentra diaforética, TA de 130/90 mm/Hg FC de 98/min, FR de 22/min y el abdomen doloroso a la palpación en toda la porción superior sin rigidez ni rebote. La sospecha diagnostica es pancreatitis. La paciente no es derechohabiente de ninguna institución y no Cuenta con los recursos para recibir atención médica privada. De acuerdo con el sistema de salud en México dirigido a las personas sin seguridad social, ¿a qué unidad se debe referir a la paciente?",
                                "O Primer nivel de atención",
                                "O Segundo nivel de atención",
                                "O Tercer nivel de atención",
                                "O Segundo nivel de atención",
                                "O Segundo nivel de atención"
                        },
                        {
                                "Se presenta en consulta una mujer con datos clínicos compatibles con anemia ferropénica, por lo que se decide hacer una biometría hemática con reticulocitos para evaluarla. Según la guía Prevención, diagnóstico y tratamiento de la anemia por deficiencia de hierro en nos y adultos, ¿cuál es el siguiente paso en la atención de la paciente",
                                "O Iniciar tratamiento con sulfato ferroso y citar en 1 mes con una nueva biometría hemática",
                                "O Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar",
                                "O Hacer un estudio de frotis sanguíneo y canalizar al Servicio de Hematología",
                                "O Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar",
                                "O Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar"
                        },
                        {
                                "Un paciente de 52 años, identificado en un módulo de prevención con diagnóstico probable de hipertensión arterial sistémica, acude a su primera consulta en el primer nivel de atención con TA de 140/90 mmhg; no reporta sintomatología, ni otros antecedentes familiares o patológicos. De acuerdo con las guías de práctica clínica, ¿qué conducta médica se debe seguir?",
                                "O modificar el estilo de vida",
                                "O iniciar tratamiento farmacológico",
                                "O enviar a especialista de segundo nivel de atención",
                                "O modificar el estilo de vida",
                                "O modificar el estilo de vida"
                        },
                        {
                                "El calentamiento global ha desencadenado una serie de cambios en el medio ambiente que repercuten en la aparición de enfermedades infecciosas fuera de sus zonas endémicas tradicionales, lo cual dificulta la prevención de padecimientos como el dengue, el chikungunya o la fiebre amarilla. Además de estas enfermedades, ¿qué otra repercusión ha tenido el cambio climático en la salud global?",
                                "O Reemergencia de enfermedades por el cambio en las temperaturas",
                                "O Alteración de la alimentación por cambios en la producción alimentaria",
                                "O Transmisión de enfermedades por vector debido al aumento del tránsito entre países",
                                "O Reemergencia de enfermedades por el cambio en las temperaturas",
                                "O Reemergencia de enfermedades por el cambio en las temperaturas"
                        },
                        {
                                "De acuerdo con una evaluación de los sistemas sanitarios en América Latina, 80% de los padecimientos son atendidos en sedes hospitalarias de tercer nivel, 15% en Segundo nivel y solo 5% en primer nivel de atención, lo que detona un rezago y una deficiencia en la salud pública nacional e internacional que se traducen en mayores tasas de morbimortalidad. Se realizar un análisis para diseñar una estrategia que permita incluir positivamente en la calidad y el costo beneficio de la atención sanitaria, y mejorar la salud pública global. ¿Qué factor de riesgo origina el problema de salud pública global identificado?",
                                "O Gasto público insuficiente, en relación con el PIB, que se destina al segundo y tercer nivel de atención.",
                                "O Inversión económica reducida en infraestructura, recursos y capital humano en el tercer nivel de atención.",
                                "O inversión focalizada en la atención sanitaria del tercer nivel, en lugar de incrementar la del primero",
                                "O inversión focalizada en la atención sanitaria del tercer nivel, en lugar de incrementar la del primero",
                                "O inversión focalizada en la atención sanitaria del tercer nivel, en lugar de incrementar la del primero"
                        },
                        {
                                "La aparición de eventos de interés epidemiológico, como la pandemia reciente por el SARS-COV-2, implica que los países miembros de la Organización Mundial de la Salud deben cumplir con lo estipulado por el Reglamento Sanitario Internacional, que tiene como finalidad...",
                                "O proporcionar atención médica a los países que lo requieran",
                                "O prevenir la propagación internacional de enfermedades",
                                "O iniciar estudios de investigación clínica en animales y en seres humanos",
                                "O prevenir la propagación internacional de enfermedades",
                                "O prevenir la propagación internacional de enfermedades"
                        },
                        {
                                "Una paciente de 29 años de edad acude a consulta de urgencia por presentar dolor torácico, taquicardia y sensación de muerte inminente al estar en el transporte público. Menciona que ha tenido episodios iguales por lo menos cuatro veces; éstos son desencadenados al exponerse a lugares cerrados y con mucha gente o al tener que hablar en público. Comenta que su madre también padece los mismos episodios La medida asociada al tratamiento médico para disminuir los episodios y la ansiedad en la paciente es la terapia.",
                                "O cognitivo-conductual",
                                "O de desensibilización ocular",
                                "O psicoanalítica",
                                "O cognitivo-conductual",
                                "O cognitivo-conductual"
                        },
                        {
                                "Un paciente de 35 años acude a su centro de Salud para solicitar un certificado médico, Presenta dolores de cabeza frecuentes, dificultad para conciliar el sueño por las noches y somnolencia excesiva durante el día, comenta que estos síntomas eran frecuentes en los últimos 8 meses en los que trabajo como vigilante, intensificándose en el último mes. Entre sus antecedentes heredofamiliares destaca que su madre presentaba insomnio con regularidad. Como medida para mejorar los hábitos de sueño, le recomendación es…",
                                "O iniciar el uso de medicamentos como benzodiacepinas",
                                "O realizar actividad física con un programa gradual de ejercicio vigoroso por las tardes",
                                "O programar la misma hora para despertarse todos los días y evitar siestas durante el día.",
                                "O iniciar el uso de medicamentos como benzodiacepinas",
                                "O iniciar el uso de medicamentos como benzodiacepinas"
                        },
                        {
                                "Un paciente de 16 años acude a consulta de control anual. Entre sus antecedentes familiares se menciona a ambos abuelos finados por complicaciones cardíacas, su madre y abuela materna padecen hipertensión arterial y su padre, diabetes mellitus. En la exploración física, se registra peso de 68 kg, talla de 1.67 m y un IMC de 24.4 kg/m2, el resto de la exploración por aparatos y sistemas se encuentra sin alteraciones. ¿Qué recomendación de actividad física se debe indicar para promover un estilo de vida saludable?",
                                "O 60 minutos al día de ejercicio aeróbico de intensidad moderada",
                                "O Entre 75 y 150 minutos al día de ejercicio aeróbico de alta intensidad",
                                "O 180 minutos al día de actividad física diversa de intensidad variable",
                                "O 60 minutos al día de ejercicio aeróbico de intensidad moderada",
                                "O 60 minutos al día de ejercicio aeróbico de intensidad moderada"
                        },
                        {
                                "Un joven de 14 años es llevado a consulta por dolor en el muslo izquierdo desde hace 2 meses, el cual tiene predominio nocturno y en ocasiones lo imposibilita dormir, además, indica pérdida de peso de 4 kg. Tiene antecedente de padre con retinoblastoma de niño. En la exploración física se observa aumento de volumen en el muslo izquierdo, se palpa tumoración inmóvil e indolora, aparentemente fija a planos profundos, sin alteraciones tegumentarias visibles. De acuerdo con el cuadro actual, ¿qué acción de prevención secundaria debe llevarse a cabo?",
                                "O Realizar ultrasonido",
                                "O Solicitar radiografía",
                                "O Programar biopsia",
                                "O Realizar ultrasonido",
                                "O Realizar ultrasonido"
                        },
                        {
                                "Una paciente de 65 años, atendida en consulta externa y acompañada por su nieto, menciona que hay ocasiones en que no recuerda el nombre de sus hijos y tiene cierta limitación para llevar a cabo sus actividades de la vida diaria; asimismo, presenta cambios en su comportamiento. Ante la sospecha diagnóstica de demencia, ¿cuál es la recomendación nutricional para prevenir la progresión de la enfermedad?",
                                "O Adicionar alimentos con folatos, vitaminas C, E y omega 3",
                                "O Aumentar consumo de carne roja rica en grasa, homocisteína y flavonoides",
                                "O Incluir suplementos con Ginkgo biloba, magnesio y zinc",
                                "O Adicionar alimentos con folatos, vitaminas C, E y omega 3",
                                "O Adicionar alimentos con folatos, vitaminas C, E y omega 3"
                        },
                        {
                                "Un paciente de 65 años acude al centro de Salud acompañado de un familiar porque sufrió una caída desde su propia altura debido a que Últimamente presenta dificultad para ponerse de pie y caminar, lo que ha provocado que permanezca en cama. El paciente tiene antecedente de diabetes mellitus tipo 2 e hipertensión arterial sistémica controladas, diagnosticadas hace 5 años, así como dermatitis actínica crónica inactiva, de 10 años de diagnóstico. En la EF, registra TA de 120/80 mmHg, FC de 89/min, FR de 16/min, T 36.5 °C, peso de 65 kg, talla de 160 cm y cara con placas liquenificadas en frente y v del cuello; asimismo, se observa hematoma de gran dimensión en glúteo derecho y limitación de movimiento. Con base en el cuadro actual, ¿cuál es la acción de prevención primaria para evitar complicaciones?",
                                "O Prescribir esteroides tópicos",
                                "O Referir a médico internista",
                                "O Prescribir movilidad funcional",
                                "O Prescribir esteroides tópicos",
                                "O Prescribir esteroides tópicos"
                        },
                        {
                                "Un paciente de 51 años acude a consulta porque presenta malestar epigástrico y saciedad temprana, además de que ha percibido pérdida involuntaria de peso, 5 kg aproximadamente, en los últimos 3 meses. Entre sus antecedentes refiere tabaquismo a razón de 15 cigarros día y enfermedad acidopéptica para la que en ocasiones toma omeprazol. Se sospecha de neoplasia gástrica. ¿Qué prueba se debe realizar para la detección oportuna?",
                                "O Test del aliento para Helicobacter pylori",
                                "O Endoscopia con toma de muestra",
                                "O Tomografía de alta resolución",
                                "O Endoscopia con toma de muestra",
                                "O Endoscopia con toma de muestra"
                        },
                        {
                                "En una comunidad rural se desbordó el río. Lo que provocó un gran número de damnificados, heridos y muertos, así como daños a la infraestructura de servicios básicos y de vivienda. El personal de salud ha iniciado los trabajos de atención. ¿Cuál es la acción prioritaria de salud ambiental en esta situación de desastre?",
                                "O Asegurar el acceso y el abastecimiento de agua para consumo humano",
                                "O Asegurar el suministro de equipo y materiales para la atención de heridos",
                                "O identificar los daños estructurales en las viviendas de los damnificados",
                                "O Asegurar el acceso y el abastecimiento de agua para consumo humano",
                                "O Asegurar el acceso y el abastecimiento de agua para consumo humano"
                        },
                        {
                                "Un hombre de 67 años tiene cansancio y dificultad para concentrarse. Durante el interrogatorio menciona que su alimentación no ha sido completa ni balanceada en el último mes y que dormido 5 horas diarias, pues ha trabajado más de normal para dejar todo en orden antes de jubilarse. ¿Qué medida preventiva se debe indicar para favorecer la salud mental del paciente?",
                                "O Solicitar interconsulta con Psicología para tratar el insomnio secundario",
                                "O Sugerir que en su estilo de vida incluya actividades físicas y recreativas",
                                "O Iniciar tratamiento con complejo B para compensar la alimentación deficiente",
                                "O Sugerir que en su estilo de vida incluya actividades físicas y recreativas",
                                "O Sugerir que en su estilo de vida incluya actividades físicas y recreativas"
                        },
                        {
                                "Una paciente de 37 años acude a orientación clínica porque hace 1 mes nació su hijo y en el hospital le recomendaron alimentarlo únicamente con lecho materna. Sin embargo, hace 3 días fue diagnosticada con hipertensión arterial sistémica e inició tratamiento con hidralazina. Por ello, quiere saber si debe suspender la lactancia. ¿Qué recomendación se debe dar a la paciente?",
                                "O Suspender la lactancia materna e iniciar alimentación a base de fórmula",
                                "O Continuar con lactancia materna exclusiva y mantener en observación al lactante",
                                "O Disminuir la lactancia materna a dos veces al día e iniciar fórmula a libre demanda",
                                "O Continuar con lactancia materna exclusiva y mantener en observación al lactante",
                                "O Continuar con lactancia materna exclusiva y mantener en observación al lactante"
                        },
                        {
                                "Una mujer de 28 años, G2, A1, con 20 SDG por USG, acude a su consulta de control prenatal. Actualmente se observa asintomática. En la EF se registra FC de 90/min, FR de 18/min, TA de 110/80 mmHg, altura uterina de 16 cm, asimismo cérvix cerrado y formado. ¿Cuál es el esquema de vacunación indicado para la paciente?",
                                "O Vacuna Td en este momento",
                                "O Vacuna Td después del embarazo",
                                "O Vacuna Tdpa en este momento",
                                "O Vacuna Tdpa en este momento",
                                "O Vacuna Tdpa en este momento"
                        },
                        {
                                "En una empresa de servicios se incrementa el ausentismo entre los trabajadores y se reciben diferentes quejas por parte de los usuarios sobre conductas y actitudes inadecuadas y poco empáticas de los empleados. Se analizan los certificados de incapacidad y se detecta que las enfermedades generales registradas incluyen cefalea tensional, migraña, lumbalgia y crisis hipertensivas. ¿Qué acción se debe implementar para atender los riesgos a la salud descritos?",
                                "O Programar sesiones grupales guiadas para reiterar los valores de la empresa que incentiven el compromiso de los empleados",
                                "O Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física",
                                "O Difundir entre todos los empleados las acciones incluidas en la política de prevención de riesgos psicosociales",
                                "O Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física",
                                "O Valorar médicamente a los empleados que presentan factores psicosociales con repercusión en su salud física"
                        },
                        {
                                "Una paciente de 61 años, con antecedente familiar de primer grado con cáncer de mama, menopausia a los 55 años y tabaquismo positivo hace 20 años, a razón de un cigarro por día, acude a consulta por una masa dolorosa en la parte superior externa de la mama derecha. En la EF, los signos vitales se encuentran dentro de los parámetros normales, la mama derecha sin alteraciones, no se logra palpar nódulo o tumoración. ¿Cuál es el estudio de elección para descartar patología maligna en este caso?",
                                "O Resonancia magnética",
                                "O Ultrasonido",
                                "O Mastografía",
                                "O Mastografía",
                                "O Mastografía"
                        },
                        {
                                "Acude a consulta madre con recién nacido de 3 semanas. Indica que nació en casa con una partera, pero llama la atención el llanto ronco, que duerme mucho, lo tiene que despertar para que coma y evacua una vez al día. En la EF, se detecta un tinte ictérico, hernia umbilical, piel seca y letargia. ¿Cuál es el estudio para obtener el diagnóstico definitivo del paciente?",
                                "O Tamiz metabólico básico",
                                "O Prueba de función hepática",
                                "O Tamiz cardiaco",
                                "O Tamiz metabólico básico",
                                "O Tamiz metabólico básico"
                        },
                        {
                                "Un hombre de 58 años presenta pirosis, tos regurgitaciones desde hace 1 mes. En el interrogatorio menciona un alto consumo de carbohidratos y grasas Saturadas, tabaquismo positivo a razón de ocho cigarrillos diarios desde hace 15 años, consumo de alcohol una vez a la semana hasta llegar a la embriaguez y sedentarismo. En la exploración física se registra IMC de 30.4 kg/m2, abdomen globoso a expensas de panículo adiposo, doloroso a la palpación media en epigastrio y marco cólico. ¿Qué medida debe indicarse al paciente para disminuir el riesgo de padecer cáncer esofágico?",
                                "O Iniciar tratamiento con antiácidos y alginatos",
                                "O Evitar el consumo de tabaco y bebidas alcohólicas",
                                "O Realizar una endoscopia para descartar esófago de Barret",
                                "O Evitar el consumo de tabaco y bebidas alcohólicas",
                                "O Evitar el consumo de tabaco y bebidas alcohólicas"
                        },
                        {
                                "Un paciente de 45 años de edad acude a consulta por presentar nicturia y tenesmo vesical de 1 mes de evolución. Tiene antecedentes heredofamiliares de cáncer de próstata en su padre y tío paterno, indica tabaquismo positivo con índice tabáquico de 25 paquetes/año, alcoholismo y litiasis vesicular. ¿Cuál es la prueba de tamizaje prioritaria para la detección oportuna de cáncer de próstata en este paciente?",
                                "O Ultrasonido prostático",
                                "O Biopsia transrectal",
                                "O Antígeno prostático específico",
                                "O Antígeno prostático específico",
                                "O Antígeno prostático específico"
                        },
                        {
                                "Una paciente de 30 años con antecedente familiar de primer grado con cáncer de mama acude a consulta por dolor cíclico en ambas mamas Indica que se le colocaron implantes mamarios con fines estéticos a los 26 años de edad. En la exploración física se registran signos vitales dentro de los parámetros normales, mama derecha sin alteraciones, implantes mamarios íntegros y no se logra palpar nódulo o tumoración. ¿Qué medida de autocuidado se debe recomendar?",
                                "O Retirar implantes mamarios",
                                "O Evitar consumo de folatos",
                                "O Realizar autoexploración mamaria",
                                "O Realizar autoexploración mamaria",
                                "O Realizar autoexploración mamaria"
                        },
                        {
                                "En una comunidad rural hubo casos de dengue el año pasado, por lo que este año, en el centro de salud y con apoyo de la presidencia municipal, se realizó un programa educativo de prevención y promoción dirigido a los pobladores, con los siguientes objetivos: • Dar a conocer la enfermedad y sus complicaciones por medio de pláticas informativas • Realizar descacharrización en las zonas con mayor riesgo • Usar larvicidas en zonas con menos riesgo • Uso de mosquiteros en puertas y ventanas Después de aplicar el programa, se presentaron casos de dengue. ¿Qué se requiere modificar para restaurar la salud de la población?",
                                "O Cloración de agua para consumo humano",
                                "O Uso de malatión en zonas con mayor riesgo",
                                "O Participación de la comunidad para limpieza a corto plazo",
                                "O Participación de la comunidad para limpieza a corto plazo",
                                "O Participación de la comunidad para limpieza a corto plazo"
                        },
                        {
                                "Como parte de las normas de seguridad del paciente, ¿cuál es el orden correcto en el que deben frotarse las manos para la desinfección? 1. Las palmas de las manos entre sí 2. La punta de los dedos de la mano derecha contra la palma de la mano izquierda en un movimiento de rotación, y al contrario 3. La palma de la mano derecha contra el dorso de la mano izquierda entrelazando los dedos y viceversa 4. Con un movimiento de rotación del pulgar izquierdo, atraparlo con la palma de la mano derecha y lo mismo con la otra mano 5 Las palmas de las manos entre sí, con los dedos entrelazados 6 El dorso de los dedos de una mano con la palma de la mano opuesta, mientras se agarran los dedos",
                                "o 1, 3, 2, 5, 6, 4",
                                "o 1,3,5, 6, 4, 2",
                                "O 3, 1, 6, 5, 4,2",
                                "o 1,3,5, 6, 4, 2",
                                "o 1,3,5, 6, 4, 2"
                        },
                        {
                                "Paciente femenino de 32 años acude por cefalea opresiva en región occipital de 5 días de evolución acompañada de acúfenos en algunas ocasiones, mareos, astenia y adinamia. Desconoce antecedentes personales de enfermedades crónicas y niega ingesta de medicamentos de manera habitual. Tiene padres con diabetes mellitus tipo 2 e hipertensión arterial. Antecedentes personales no patológicos, con dieta a base de harinas procesadas y carbohidratos refinados, sedentarismo, tabaquismo positivo, a razón de cuatro cigarrillos al día y alcoholismo de patrón social. Las alteraciones halladas en la exploración física son: IMC de 34.2 kg/m2, TA de 139/79 mmHg y glucosa postpandrial de 126 mg/dl. ¿Cuáles son los cambios necesarios en el estilo de vida de la paciente para restaurar su salud?",
                                "O Actividad física de moderada a intensa, supervisada medicamente, de 3 a 5 días por semana, restricción de carbohidratos de la dieta para reducción de peso corporal; consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "O Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina del peso corporal, Consumo de sodio menor a 1.8g al día y consumo de proteínas menos de 0.6 g/kg/día",
                                "O Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "O Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día.",
                                "O Actividad física de moderada a intensa, no supervisada, de 3 a 5 días por semana, consumo de carbohidratos de índice glicémico bajo para disminución paulatina de peso corporal, consumo de sodio de entre 1.8 y 2.4 g al día y consumo de proteínas de 0.8 g/kg/día, sin disminuir de 0.6 g/kg/día."
                        },
                        {
                                "En una comunidad rural ha aumentado tres veces la incidencia de infecciones de transmisión sexual en mujeres en los últimos 6 meses. Debido a la escasez de recursos y medicamentos, el médico decide pedir apoyo a la unidad médica más cercana, a la que le solicita preservativos, antibióticos y material para realizar el papanicolaou. ¿Qué intervención favorece la protección de la salud sexual de la comunidad?",
                                "O Capacitar para el uso de preservativos",
                                "O Instruir acerca del consumo de antibióticos",
                                "O Informar sobre el procedimiento del papanicolaou",
                                "O Capacitar para el uso de preservativos",
                                "O Capacitar para el uso de preservativos"
                        },
                        {
                                "Se detecta que un grupo de 25 personas del personal de salud que se recuperó de la covid-19 requiere orientación para manejo de tensión emocional al reintegrarse a labores y percibir el alto riesgo de volver a infectarse. ¿Cuál es la intervención educativa sobre el manejo del estrés que se debe implementar?",
                                "O Diplomado presencial de 120 horas con apoyo de un tutor",
                                "O Taller virtual autoadministrado de 120 horas con apoyo de la plataforma hospitalaria",
                                "O Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública",
                                "O Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública",
                                "O Curso virtual sincrónico de 30 horas con apoyo de plataforma virtual de salud pública"
                        },
                        {
                                "En un municipio, se realizó el diagnóstico situacional de salud en 2020, en el que se reportaron 473 personas de 65 años y más, con una proyección de aumento de 55% para 2025. Por este incremento, el centro de salud decide implementar intervenciones educativas para favorecer la protección de las personas de la tercera edad. ¿Cuál de las siguientes intervenciones favorece la protección para el síndrome de fragilidad de esta población?",
                                "O Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado",
                                "O lncluir a los familiares o cuidadores en la hora de comer para socializar y apoyar, fomentar ejercicios de resistencia 30-45 minutos por día, mínimo tres veces por semana, asistir al centro de salud para valoración médica y para determinar niveles séricos de vitamina D",
                                "O Realizar actividades de recreación grupales o individuales como taichí o bailar, ejercitarse regularmente con pesas, previa valoración médica, incluir en la dieta alimentos ricos en Ca y proteína e instalar pasamanos en baños, pasillos y escaleras.",
                                "O Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado",
                                "O Estimular comunicación con visitas de familiares, coloca relojes y Calendarios en la vivienda para facilitar orientación, incluir suplementos vitamínicos y estimular movilidad con actividades de autocuidado"
                        },
                        {
                                "En una comunidad rural de Campeche se registra una tasa de mortalidad materna cercana a 20% y se identifica que las tres principales causas incluyen enfermedades preexistentes o surgidas durante el embarazo, infecciones y hemorragias posparto. En la comunidad es muy recurrente que las mujeres prefieran que sus partos sean atendidos por matronas porque, aunque hay una clínica cercana, el hospital se encuentra a 1 hora de distancia. ¿Qué aspecto se debe enfatizar en todas las intervenciones de educación para la salud que se implementen en la comunidad?",
                                "O Recibir atención prenatal durante el embarazo",
                                "O Promover la atención profesional del parto",
                                "O Aumentar las medidas de higiene durante el parto",
                                "O Recibir atención prenatal durante el embarazo",
                                "O Recibir atención prenatal durante el embarazo"
                        },
                        {
                                "Llega al Servicio de Urgencias, por cuarta ocasión en 1 mes, un adolescente de 18 años con signos de intoxicación por Cannabis. El joven menciona que sus padres lo presionan mucho con los estudios y lo único que lo calma es fumar mariguana. ¿Cuáles son las acciones educativas para manejar el abuso de sustancias en este caso?",
                                "O implementar estrategias de moderación y retroalimentación personalizadas acerca del consumo, sus riesgos y consecuencias.",
                                "O Enfatizar la inexistencia de un nivel seguro de consumo de drogas y aconsejar mientras se destacan los factores protectores del paciente.",
                                "O Informar sobre actividades cognitivo-conductuales por medio de grupos de ayuda mutua para evitar el consumo",
                                "O Informar sobre actividades cognitivo-conductuales por medio de grupos de ayuda mutua para evitar el consumo",
                                "O Informar sobre actividades cognitivo-conductuales por medio de grupos de ayuda mutua para evitar el consumo"
                        },
                        {
                                "En una secundaria, se detecta un aumento de las infecciones por transmisión sexual en un grupo de adolescentes de entre 12 y 15 años. Identifique la acción educativa por implementar",
                                "O Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales",
                                "O Promover la abstinencia y el reconocimiento de enfermedades de transmisión sexual",
                                "O Informar sobre sexualidad de forma diferenciada a grupos de hombres y mujeres",
                                "O Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales",
                                "O Fortalecer las habilidades para la salud y los conocimientos acerca a las relaciones sexuales"
                        },
                        {
                                "Paciente masculino de 48 años acude a consulta por náusea y vómito por ingesta de destilados en cantidad abundante la noche previa. Niega antecedentes de enfermedades crónicas y alergias, tabaquismo positivo a razón de cinco a siete cigarros al día: Al interrogar más a fondo, se detecta que desde hace 1 mes aumentó la ingesta de bebidas alcohólicas hasta hacerlo casi diariamente, incluso solo en casa, llegando a la embriaguez de manera ocasional. El paciente menciona que ha presentado retardos recurrentes en su trabajo por las molestias en la mañana y que en la última semana lo han amonestado por esta situación. ¿Cuál de las siguientes opciones de prevención y educación en salud se recomienda en este caso?",
                                "O Disminuir el consumo de bebidas alcohólicas hasta suspenderlas por completo",
                                "O Remitir a consulta de psiquiatría o a una clínica de control de adicciones",
                                "O Iniciar tratamiento con una benzodiacepina de acción corta y analgésicos",
                                "O Remitir a consulta de psiquiatría o a una clínica de control de adicciones",
                                "O Remitir a consulta de psiquiatría o a una clínica de control de adicciones"
                        },
                        {
                                "Una paciente de 68 años presenta un cuadro de tos con expectoración blanquecina y ortopnea, menciona que el día de hoy se agudizaron los síntomas y que presenta disnea. Reside en una comunidad rural y vive con cuatro familiares más; su casa habitación cuenta con dos cuartos, piso de tierra, techo de lámina, cocina rustica de leña y fosa séptica; indica que tienen aves de corral para autoconsumo. En la exploración física se registra TA de 120/80 mmHg, FC de 78/min, FR de 22/min, T de 36.9°C y sato2 de 96%. ¿Cuál es el factor de entorno de la paciente que se asocia a su problema de salud?",
                                "Exposición a biomasa",
                                "Hacinamiento",
                                "Zoonosis",
                                "Exposición a biomasa",
                                "Exposición a biomasa"
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
                                "Un paciente de 68 años acude a consulta por presentar nauseas acompañada de vómitos ocasionales, así como saciedad temprana de 1 semana de evolución. Informa sobre ataque al estado general y niega pérdida de peso. Entre sus antecedentes se identifica grupo sanguíneo A+ y refiere tabaquismo dese los 20 años, a razón de 4 cigarros al día, ingesta de alcohol, una copa de vino diariamente, alimentación rica en carbohidratos, fruta y verduras, así como consumo frecuente de alimentos encurtidos y ahumados. Además, ingiere complementos de vitamina C y E. indica que hace 1 año le diagnosticaron infección por Helicobacter pylori, sin tratamiento, así como hipertensión arterial desde hace 15 años, controlada con valsartán y ácido acetilsalicílico. En la exploración física solo destaca palidez de tegumentos, linfadenopatías cervicales y supraclaviculares. De acuerdo con la sospecha diagnostica ¿Qué antecedentes son de importancia para el padecimiento?",
                                "Tipo de sangre y consumo de alimentos encurtidos y ahumados",
                                "Alimentación rica en carbohidratos y consumo de AAS",
                                "Ingesta de alcohol y consumo de valsartan",
                                "Tipo de sangre y consumo de alimentos encurtidos y ahumados",
                                "Tipo de sangre y consumo de alimentos encurtidos y ahumados"
                        },
                        {
                                "Un paciente de 65 años es atendido por cuadro infeccioso pulmonar agudo, con fiebre, tos productiva con expectoración purulenta, disnea y dolor torácico. Refiere antecedentes de tabaquismo a razón de un cigarro a la semana desde los 20 años e hipotiroidismo subclínico. En la exploración física se registra IMC de 25 kg/m2, se escucha matidez a la percusión y se ausculta broncofonía y crepitaciones subescapulares. ¿Cuál es el antecedente epidemiológico relacionado con la enfermedad del paciente?",
                                "Edad",
                                "Tabaquismo",
                                "Comorbilidades",
                                "Edad",
                                "Edad"
                        },
                        {
                                "Un paciente de 67 años acude al servicio de urgencias por presentar un cuadro de 12 horas de evolución caracterizado por dolor en epigastrio de tipo transfictivo, de inicio súbito e intenso y que se irradia hacia el abdomen bajo. Presenta antecedentes de tabaquismo positivo desde los 20 años, a razón de seis cigarros diarios y tiene diagnóstico de artritis reumatoide, pero sin apego al tratamiento. En la exploración física se registra TA de 125/58 mmHg, FC de 120/min, FR de 27/min y T de 37°C. Se encuentra consciente, aunque desorientado, con mal estado general, ruidos cardiacos rítmicos, abdomen plano, peristalsis ausente, abdomen en madera, dolor a la palpación superficial y profunda con datos de irritación peritoneal y al tacto rectal refiere dolor. ¿Qué datos se debe buscar por su relevancia para el padecimiento del paciente?",
                                "Dieta alta en grasa",
                                "Palidez de tegumentos",
                                "Consumo de AINE",
                                "Consumo de AINE",
                                "Consumo de AINE"
                        },
                        {
                                "Una paciente de 8 años es llevada a consulta por presentar fiebre no cuantificada intermitente en el último mes. La madre refiere que inicia de manera súbita y se acompaña de escalofríos y que la menor presenta astenia y adinamia. En la exploración física se encuentra en mal estado general, se registra TA de 110/75 mmHg, FC de 90/min, FR de 23/min y T de 39.5°C. Se detecta datos de artritis migratoria que afecta rodillas y codos, disnea de medianos esfuerzos y se ausculta soplo Holosistólico apical de tono alto en área precordial, con intensidad grado III-IV e irradiado hacia la axila; el resto de la exploración normal. ¿Cuál es la interpretación de los hallazgos clínicos de la menor?",
                                "Insuficiencia aortica",
                                "Mixoma auricular",
                                "Insuficiencia mitral",
                                "Insuficiencia mitral",
                                "Insuficiencia mitral"
                        },
                        {
                                "Un paciente de 65 años acude a consulta externa por presentar edema en el lado derecho del rostro que se ha hecho más evidente en las últimas 2 semanas. Refiere, además, presentar tos crónica y pérdida de peso involuntaria, ambas en el último mes. Entre sus antecedentes menciona tabaquismo positivo desde hace 40 años. Durante el interrogatorio se detecta disfonía leve y en la exploración física se observa edema y flebectasias en rostro, cuello y miembro torácico derecho. De acuerdo con el cuadro clínico, se integra síndrome de…",
                                "Vena vaca inferior",
                                "Vena cava superior",
                                "Budd-chiar",
                                "Vena cava superior",
                                "Vena cava superior"
                        },
                        {
                                "Una paciente de 34 años es referida por el servicio de medicina preventiva por presentar una citología cervical LEIAG, sin reporte de resultado de VPH y que se encuentra asintomática. Como antecedente G0, P0, A0, IVSA de 17 años, dos parejas sexuales y uso de anticonceptivos orales. En el examen ginecológico se observa anormalidades morfológicas en el cuello del útero. ¿Qué estudio se debe indicar para el seguimiento de la paciente?",
                                "Citología cervical de base liquida",
                                "Prueba VPH",
                                "Biopsia dirigida por colposcopia",
                                "Biopsia dirigida por colposcopia",
                                "Biopsia dirigida por colposcopia"
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
                                "En una comunidad se ha incrementado el número de cuadros asociados con dermatitis, nausea, vomito, astenia, irritación de las mucosas respiratorias y conjuntivales, cefaleas, vértigo y perdida de equilibro entre las personas que trabajan en los campos de cultivo. Entre la población de detecta que la mayoría de los trabajadores de los cultivos no tenía conocimiento de que se cuenta con equipo de protección personas y que quienes lo utilizan no lo portan adecuadamente. Esto debido a que se integraron a la labor de campo mecanizada solo con la experiencia previa de agricultura de subsistencia. En los últimos años la comunidad ha crecido en incluso algunas prácticas se han visto rebasadas, por ejemplo se dejó de quemar basura y ésta ahora se acumula en un espacio designado en la comunidad, pera la recolección es quincenal. ¿Qué aspectos integran el diagnostico de salud de la comunidad?",
                                "Desconocimiento sobre el uso correcto del EPP y de los plaguicidas",
                                "Intoxicación por plaguicidas y recolección insuficiente de basura",
                                "Falta de capacitación sobre mecanización y antecedente de quema de basura",
                                "Intoxicación por plaguicidas y recolección insuficiente de basura",
                                "Intoxicación por plaguicidas y recolección insuficiente de basura"
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
                                "Un paciente de 42 años acude a consulta por presentar debilidad en hemicara derecha de inicio súbito, dolor retroauricular derecho y parestesias en carrillo derecho de 12 horas de evolución. En la exploración física se detecta parálisis facial periférica derecha de grado 2 en escala House- Brackmann y el resto de la exploración neurológica sin alteraciones. Se determina diagnóstico de parálisis facial idiopática. ¿Cuál es el tratamiento farmacológico de primera elección para el paciente?",
                                "Gabapentina",
                                "Prednisona",
                                "Aciclovir",
                                "Prednisona",
                                "Prednisona"
                        },
                        {
                                "Un paciente de 37 años acude a consulta porque busca orientación para controlar su peso, ya que, desde hace 5 años y debido a su trabajo, no tiene oportunidad de realizar ejercicio y su dieta no es balanceada. Entre sus antecedentes presenta diagnóstico de hipertensión arterial desde hace 2 años con manejo farmacológico. En la exploración física se registra constitución robusta, con IMC de 32 kg/m2 y TA de 150/90 mmHg. ¿Cuál es la recomendación dietético-nutricional que se le debe indicar al paciente para perder peso y mejorar su calidad de vida?",
                                "Cambiar a un patrón mediterráneo con consumo de ácidos grasos poliinsaturados",
                                "Disminuir el consumo promedio de 300 kcal/día durante 12 meses",
                                "Llevar una dieta hipoproteica e hipocalórcia con preferencia por grasas vegetales",
                                "Cambiar a un patrón mediterráneo con consumo de ácidos grasos poliinsaturados",
                                "Cambiar a un patrón mediterráneo con consumo de ácidos grasos poliinsaturados"
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
                                "Una paciente de 33 años con 34 sdg acude al servicio de urgneicas por presentar dolor en epigastrio, náuseas y cefalea frontal. Refiere como antecedente ginecobstetricos G3, P2, A0. En la exploración física se registra TA de 150/90 mmHg, por lo que se sospecha de preeclampsia. Se solicitan estudios de laboratorio y en los resultados destaca Hb de 12 g/dl, HTO de 38%, plaquetas de 150,000/mm3, creatinina sérica de 0.9 mg/dl, proteína en tira reactiva ++, ALT de 510 UI/L, AST de 600 UI/l, LDH de 500 UI/l y bilirrubina sérica de 1 mg/dl. ¿Qué datos de alarma presenta la paciente?",
                                "Epigastralgia e hipertransaminasemia",
                                "Tensión arterial y proteinuria",
                                "Nivel de creatinina y de bilirrubina",
                                "Epigastralgia e hipertransaminasemia",
                                "Epigastralgia e hipertransaminasemia"
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
                                "Una paciente de 31 años acude al servicio de urgencias por presentar odinofagia, cefalea y ataque al estado general, además de fiebre no cuantificada de 2 días de evolución. Entre sus antecedentes comenta que su abuela es diabética, menciona tener alergia a AINE y aumento de peso desde hace 2 años atribuido al sedentarismo. En la exploración física se registra TA de 130/85 mmHg, FC de 80/min, FR de 18/min, T de 37.5ºC, IMC 30.3 kg/m2 y circunferencia abdominal de 90 cm. Se observa hiperemia conjuntival, labios y mucosa oral secos, faringe y amígdalas hiperémicas, criptas amigdalinas con secreción purulenta y se palpan ganglios submaxilares bilaterales. En los resultados de laboratorio se reportan leucocitos de 10.500/mm, neutrófilos de 62%, hb 12.7 mg/dl, hto 37.2%, glucosa de 100 mg/dl, HbA1c de 6%, colesterol total de 181 mg/dl, HDL 48 mg/dl, LDL 120 mg/dl y triglicéridos de 187 mg/dl. Se inicia manejo para cuadro de faringoamigdalitis y se indican medidas higiénicodietéticas, de actividad física y metformina. ¿Cuál es el plan de seguimiento por establecer con la paciente?",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                                "Monitorear cada 5 años la progresión metabólica de la glucosa para revalorar riesgos",
                                "Establecer un objetivo de peso y considerar terapia dual para control de glucosa",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso",
                                "Escalar progresivamente el programa de ejercicio aeróbico para mantener la pérdida de peso"
                        },
                        {
                                "Un paciente de 64 años acude al servicio de urgencias porque desde hace 3 días presenta una lesión en el pie derecho, sin antecedente traumático. También menciona tener desde hace 10 años diabetes mellitus controlada con 16 unidades de insulina al día; dislipidemia con pravastatina; hipertensión arterial con enalapril; tabaquismo desde los 16 años a razón de fumar 10 cigarros al día y obesidad. En la exploración física se observa marcha antiálgica con descarga de peso en el talón del pie derecho; lesión tipo ulcera hiperqueratósica de 1x2 cm en cara plantar, en la base del primer ortejo, con hiperemia perilesional, hipertermia, dolor a la manipulación y flexoextensión del primer dedo, con presencia de exudado purulento, fétido, de cantidad moderada; pulso femoral 2/2 sin soplo, poplíteo 2/2, pedio 2/2 y tibial posterior 2/2, además de monofilamento Semmes-Weinstein 2/10 De acuerdo con el cuadro clínico del paciente y la escala de Wagner, el manejo inicial de urgencias indicado es…",
                                "Lavado abundante de la lesión con solución salina al 0.9% seguido de medicina física y rehabilitación",
                                "Desbridamiento quirúrgico en las zonas con celulitis, abscesos o signos de sepsis y tratamiento con antibióticos",
                                "Desbridamiento cortante de esfácelos y del tejido necrótico, de ser necesario, enzimas proteolíticos o hidrogeles",
                                "Desbridamiento quirúrgico en las zonas con celulitis, abscesos o signos de sepsis y tratamiento con antibióticos",
                                "Desbridamiento quirúrgico en las zonas con celulitis, abscesos o signos de sepsis y tratamiento con antibióticos"
                        },
                        {
                                "Un paciente de 48 años acude a consulta debido a que presenta dolor intenso en el primer dedo del pie derecho. Comenta antecedentes de diabetes mellitus de 10 años de evolución, con manejo farmacológico. En la exploración física se observa incremento de volumen y eritema en el primer ortejo de pie derecho, por lo que se solicitan estudios de laboratorio y se confirma ataque agudo de gota. ¿Qué se le debe indicar al paciente para resolver la complicación?",
                                "Alopurinol, indometacina y dieta baja en purinas",
                                "Prednisona y consumo de lácteos al menos tres veces al día",
                                "Diclofenaco sódico, colchicina y beber 3 litros de agua al día",
                                "Diclofenaco sódico, colchicina y beber 3 litros de agua al día",
                                "Diclofenaco sódico, colchicina y beber 3 litros de agua al día"
                        },
                        {
                                "En una unidad de salud se detectan numerosos casos de anemia en menores de 8 años, a pesar de que se han llevado a cabo las medidas de control del niño sano. Se plantea un estudio de investigación para determinar la prevalencia del padecimiento y detectar sus factores asociados. ¿Qué diseño se debe emplear para el estudio?",
                                "Transversal",
                                "Series de casos",
                                "Cohorte",
                                "Transversal",
                                "Transversal"
                        },
                        {
                                "En un grupo de investigación del área de cardiología se busca evaluar la validez del autorregistro de la presión arterial como técnica diagnóstica complementaria la valoración clínica. Esto en comparación con la manera tradicional mediante la monitorización ambulatoria en el primer nivel de atención. ¿Qué pregunta de investigación debe orientar el estudio?",
                                "¿se correlaciona la tasa de efectividad para el logro de los objetivos terapéuticos del autorregistro con el monitoreo ambulatorio de presión arterial?",
                                "¿existen diferencias en la sensibilidad y la especificidad del autorregistro y el monitoreo ambulatorio de la presión arterial?",
                                "¿es mayor el número de pacientes diagnosticados con hipertensión arterial mediante el autorregistro que por medio del monitoreo ambulatorio?",
                                "¿existen diferencias en la sensibilidad y la especificidad del autorregistro y el monitoreo ambulatorio de la presión arterial?",
                                "¿existen diferencias en la sensibilidad y la especificidad del autorregistro y el monitoreo ambulatorio de la presión arterial?"
                        },
                        {
                                "En un protocolo de investigación se busca analizar las características clinicopatológicas y la supervivencia de pacientes con cáncer de mama a las que se les ha dado seguimiento en un hospital. Se extraerá la información de los expedientes clínicos de las personas con este diagnóstico cuya consulta de primera vez fue hace 5 años y hasta la fecha, se excluirán aquellos que presenten datos incompletos, así como registros duplicados. ¿Qué aspecto determina que la muestra sea representativa?",
                                "Exclusión de los registros duplicados e incompletos",
                                "Revisión y análisis aleatorios de los expedientes",
                                "Periodo para ser considerado en la revisión",
                                "Exclusión de los registros duplicados e incompletos",
                                "Exclusión de los registros duplicados e incompletos"
                        },
                        {
                                "Es un hospital se realizará un estudio de investigación cuyo objetivo es identificar la eficacia de dos analgésicos en relación con el grado de inhibición del dolor en pacientes adultos que se encuentran en posoperatorio de apendicetomía. Se subdividirá a los pacientes con esta condición en dos grupos. A cada uno se le administrará el analgésico correspondiente. Para determinar el número de participantes se requiere estimar el tamaño de la muestra para…",
                                "Calculo de promedio",
                                "Estimación de proporción",
                                "Prueba de hipótesis",
                                "Estimación de proporción",
                                "Estimación de proporción"
                        },
                        {
                                "En un centro de salud se busca monitorear el seguimiento de los pacientes de entre 2 y 5 años que han sido atendidos por desnutrición, durante el último año. De manera inicial, se analizarán los estadísticos descriptivos del peso, la talla y el pliegue tricipital; sin embargo, en los registros de la consulta inicial se detecta que los datos de talla no presentan una distribución normal. ¿Qué medida de dispersión se debe utilizar para analizar la talla de estos pacientes?",
                                "Desviación media",
                                "Rango intercuartílico",
                                "Varianza",
                                "Varianza",
                                "Varianza"
                        },
                        {
                                "Se planea realizar un ensayo clínico multicéntrico para comprobar la eficacia de una nueva vacuna en la población adulta. Se reclutaran 20 000 adultos de entre 18 y 59 años, sin que las mujeres que participen estén embarazadas. Se asignarán a uno de tres grupos; en dos se administraran esquemas diferentes con la sustancia activa y en el tercero se suministrará placebo como control. Se empleará el método doble ciego para minimizar la posibilidad de que algún sesgo interfiera en los resultados. ¿Qué estrategia de muestreo se debe emplear en el estudio?",
                                "Sistemático",
                                "Estratificado",
                                "Aleatorio simple",
                                "Aleatorio simple",
                                "Aleatorio simple"
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
                                "Una paciente de 20 años asiste a consulta médica debido a que hace 1 año su madre fue diagnosticada con cáncer de mama en etapa clínica IV y falleció hace 3 meses a causa de esto. Su abuela materna, también falleció de cáncer de mama, por lo que está preocupada y quiere saber qué riesgo tiene ella de padecer la enfermedad. Se requiere realizar una búsqueda de información al respecto. ¿Qué medio se debe utilizar para la búsqueda de información y orientar a la paciente?",
                                "PubMed",
                                "Cochrane",
                                "UptoDate",
                                "Cochrane",
                                "Cochrane"
                        },
                        {
                                "Una paciente de 15 años es llevada a consulta porque presentó sincope refiere que ha presentado náusea sin vómito en las últimas semanas y, de acuerdo a su FUM tiene retraso de mes y medio. Niega antecedente de coito sin protección, pero se realiza una prueba de embarazo y el resultado es positivo. Solicita la interrupción de su embarazo sin que su familiar sea notificada, ya que teme que su padre reaccione de manera violenta pues tiene problemas de alcoholismo. ¿Qué información se le debe proporcionar de acuerdo a lo solicitado por la paciente?",
                                "Obligación de notificar a los padres antes de tomar cualquier decisión, por ser menor de edad",
                                "Riesgos y posibles complicaciones implicadas en practicarse un aborto a su edad, sin acompañamiento",
                                "Complicaciones psicosociales asociadas con la maternidad adolescente y alternativas como la adopción",
                                "Riesgos y posibles complicaciones implicadas en practicarse un aborto a su edad, sin acompañamiento",
                                "Riesgos y posibles complicaciones implicadas en practicarse un aborto a su edad, sin acompañamiento"
                        },
                        {
                                "Se realiza un metaanálisis para evaluar los efectos de los principales enfoques para el tratamiento de coledocolitiasis que consiste en: colecistectomía laparoscópica (CL) más exploración laparoscópica del colédoco (ELC), o colangiopancreatografía retrógrada endoscópica (CPRE). Se incluyeron todos los ensayos clínicos aleatorios que compararon los resultados de la cirugía laparoscópica, así como de la depuración endoscópica de los cálculos en el colédoco. Además, se identificaron 16 ensayos con 1 758 participantes, todos con defectos en el diseño del estudio que pueden dar lugar a la sobreestimación de los beneficios o a la subestimación de los efectos perjudiciales. En cuanto a los hallazgos, no hubo diferencias significativas en la mortalidad entre la CL + ELC versus CPRE preoperatoria + CL. ¿Qué aspecto se debe considerar para la valoración crítica de la información obtenida?",
                                "Relevancia de los resultados",
                                "Aplicabilidad de los hallazgos",
                                "Representatividad de las muestras",
                                "Relevancia de los resultados",
                                "Relevancia de los resultados"
                        },
                        {
                                "En una análisis de meta-investigación sobre la veracidad de los estudios publicados se revela que las simulaciones muestras que, para la mayoría de los diseños y entornos de estudios, es más probable que una afirmación de investigación sea falsa que verdadera. Esto, de acuerdo con los resultados en los que 1 de cada 10 de las hipótesis de interés por poner a prueba es verdadera, aunado a la tasa de 5% de falsos positivos y que el poder de detección implicado en la confirmación de la veracidad es de 80%, lo que deriva en la conclusión de que los resultados negativos o nulos son más confiables, aunque menos probable que se pretenda publicarlos. ¿Qué aspecto de estos hallazgos se debe integrar a la formación continua del personal de salud?",
                                "La información publicada en la mayoría de los casos integra una subrepresentación de los falsos positivos",
                                "La veracidad de las publicaciones debe fundamentarse con indicadores adicionales a los propios resultados",
                                "La confiabilidad de los resultados publicados es invalida por el más uso sistemático de la estadística",
                                "La veracidad de las publicaciones debe fundamentarse con indicadores adicionales a los propios resultados",
                                "La veracidad de las publicaciones debe fundamentarse con indicadores adicionales a los propios resultados"
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
                                "Es llevado a consulta un niño de 2 años y medio porque presenta cuadro clínico caracterizado por otalgia, fiebre y ataque al estado general. Durante la exploración física se detectan signos clínicos que orientan a otitis media aguda y destaca conducta esterotípica verbal en el menor. Se interroga a la madre respecto a la conducta habitual del paciente y se describe incapacidad para jugar de manera simbólica. Se indica tratamiento antimicrobiano. Con base en la normativa vigente, ¿Qué indicación se le debe brindar a la madre respecto al neurodesarrollo del menor?",
                                "Referencia para valoración diagnóstica",
                                "Taller de estimulación temprana",
                                "Lectura de material sobre estimulación",
                                "Referencia para valoración diagnóstica",
                                "Referencia para valoración diagnóstica"
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
                                "Una paciente de 30 años es referida por un módulo de prevención de la misma unidad para consultar sobre la convivencia de realizarse toma de citología cervical para la detección de cáncer cérvico uterino, ya que nunca se ha realizado este estudio coma pero cuenta con el esquema completo de vacuna anti VPH a los 11 años punto como antecedentes informa menarca los 12 años coma inicio de la vida sexual activa a los 17 años coma sin antecedentes de abortos o embarazos y no indica patologías. Actualmente, con pareja sexual estable y sin antecedentes heredo familiares de importancia. Con base en las características de la paciente la acción por seguir es.",
                                "Realizar la citología cervical en cuanto sea posible",
                                "Aplicar cuestionario para identificar factores de riesgo",
                                "Informar que no necesita ninguna prueba de detección",
                                "Realizar la citología cervical en cuanto sea posible",
                                "Realizar la citología cervical en cuanto sea posible"
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
                                "Asiste a consulta una paciente de 23 años porque presenta dolor y congestión mamaria. Menciona haber tenido un parto eutócico hace 1 semana; el bebé nació con labio leporino y paladar hendido unilateral, por lo que tiene gran dificultad para la succión. En la exploración física se registran signos vitales normales, mamas secretantes congestivas, con zonas induradas y dolorosas en el cuadrante superoexterno de ambas mamas, sin cambios en la coloración de la piel y abundante secreción láctea por ambos pezones. En la exploración ginecológica se detecta útero bien involucionado, loquios normales y episiorrafia en vías de cicatrización. ¿Qué manejo se le debe recomendar para el proceso de lactancia?",
                                "Extraer la leche materna y extraerla con biberón",
                                "Utilizar obturador palatino durante la alimentación",
                                "Acudir a asesoría para modificar técnica de lactancia",
                                "Acudir a asesoría para modificar técnica de lactancia",
                                "Acudir a asesoría para modificar técnica de lactancia"
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
