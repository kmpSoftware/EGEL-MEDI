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
    private static final long TIEMPO_TOTAL = 4000;
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
                        {"Una paciente de 55 años acude a consulta por control debido a que tiene antecedentes de diabetes mellitus de 5 años de evolución. En la exploración física, se registran signos normales, con un IMC de 31 kg/m2 y un perímetro de cintura de 102 cm. Al preguntarle sobre los ajustes de estilo de vida, comenta que no ha logrado incorporar actividad física constante en la rutina ni reducir el número de veces que come en la calle. Esto se debe a que trabaja como conductor de una aplicación por casi 12 horas al día y llega a su casa en la madrugada para cumplir con las horas requeridas por el dueño del vehículo. El paciente menciona que, a partir de su cita de control anterior, intentó caminar por 30 minutos junto con su esposa durante las mañanas antes de iniciar sus viajes, pero lo asaltaron cerca de su domicilio y lo despojaron de su teléfono celular, que es parte de sus herramientas de trabajo. Como resultado, tuvo que trabajar más horas para subsanar la pérdida y suspendió las caminatas. Además, su esposa manifiesta el deseo de mudarse debido a la inseguridad en su colonia a raíz del asalto. ¿Qué aspecto del contexto social del paciente afecta su estado actual de salud?",
                                "Precarización laboral",
                                "Ambiente familiar",
                                "Inseguridad en su colonia",
                                "Precarización laboral",
                                "La precarización laboral del paciente, debido a sus largas jornadas de trabajo y la necesidad de cubrir una cantidad específica de horas, está afectando su capacidad para realizar cambios en su estilo de vida y cuidar de su salud. Esta situación puede tener un impacto negativo en su bienestar general."},
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
                        },                        { "Un paciente de 70 años acude a consulta por presentar desde hace 2 días una erupción cutánea que confluye en tronco y extremidades. Entre sus antecedentes está padecer hipertensión arterial e hiperuricemia, las cuales trata con amlodipino y alopurinol, menciona que hace 2 semanas presentó lumbalgia, que trató con diclofenaco. En la exploración física se observan lesiones maculares eritematovioláceas muy extensas, de casi un 70% de la superficie cutánea, sobre las que hay ampollas y erosiones. Se identifican lesiones erosivo-costrosas de la mucosa labial, oral y conjuntiva, y al pasar el dedo sobre la piel ésta se despega (signo de Nikolsky positivo).",
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
                        { "Acude a consulta una paciente con erupciones cutáneas de 48 horas de evolución en tronco y extremidades, se observan lesiones maculares eritematovioláceas extensas con ampollas y erosiones, así como lesiones erosivo costrosas de mucosa labial y oral. Tiene antecedentes de hipertensión e hiperuricemia, tratadas con amlodipino y alopurinol. Hace 1 semana presentó dolor en el área lumbar, que trato con diclofenaco. En la exploración física, se identifica que al pasar los dedos sobre la piel ésta se despega fácilmente.",
                                "Desprendimiento de la piel e inicio del tratamiento con diclofenaco",
                                "Aparición de erupciones cutáneas 48 horas antes, asociada a hiperuricemia",
                                "Presencia de lesiones erosivo-costrosas en mucosa y consumo crónico de alopurinol",
                                "Desprendimiento de la piel e inicio del tratamiento con diclofenaco",
                                "Los datos que orientan el diagnóstico de este paciente son el desprendimiento de la piel y el inicio del tratamiento con diclofenaco. La presencia de lesiones cutáneas extensas con ampollas y erosiones, así como el desprendimiento de la piel al pasar los dedos, sugieren la posibilidad de un efecto adverso al medicamento diclofenaco. Es importante considerar esta asociación para el diagnóstico y manejo adecuado de la paciente, especialmente en presencia de antecedentes de hipertensión e hiperuricemia, y el uso concomitante de amlodipino y alopurinol."
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
                                "En un estudio se aplicó una prueba de anticuerpos IgM/IgG para detectar Covid-19. La confirmación del diagnóstico fue mediante PCR, con una n=45, la tasa total de detección de anticuerpos fue de 92%, en pacientes hospitalizados, y de 79% en los no hospitalizados. Los resultados mostraron que la detección total de las inmunoglobulinas IgM e IgG fue de 63% en pacientes con < 2 semanas desde el inicio de la enfermedad, de 85% en los no hospitalizados con > 2 semanas de duración de la enfermedad, y de 91% en hospitalizados con > 2 semanas de duración de la enfermedad. Como resultado final, se determinó que la especificidad de la prueba de anticuerpos fue de 97% en 69 muestras de suero/plasma. Identifique la opción que interpreta los resultados del estudio",
                                "Proporciona una validación de la prueba de covid-19 IgM/IgG y, a su vez, determina que existe mayor especificidad en el diagnóstico por RT-PCR",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblacional",
                                "Demuestra que la detección de anticuerpos de covid-19 IgM/IgG tiene mayor especificidad y sensibilidad en los pacientes con una evolución menor a 2 semanas",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblaciona",
                                "Muestra que la prueba de covid-19 IgM/IgG puede aplicarse para evaluar el estado de la enfermedad tanto a nivel individual como poblaciona"
                        },{
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
                                "Un paciente de 79 años es ingresado al servicio de Urgencias por presentar dolor y debilidad en las piernas que en los últimos días le imposibilita caminar e incluso moverlas. Su familiar informa que en la última semana presentó de forma súbita incontinencia urinaria e intestinal y pérdida de peso. En los resultados de laboratorio destaca hipercalcemia y en la radiografía de tórax se observan masas con aspecto irregular que indican malignidad y que orientan a la sospecha de cáncer o metástasis ósea. Se requiere solicitar biopsia para confirmar y estudios adicionales para la estatificación. A informar al paciente, se percibe agobiado y éste rechaza los procedimientos exploratorios adicionales. De acuerdo con el principio de no maleficencia del acto médico, ¿qué decisión se debe tomar?",
                                "Consultar con el familiar y enfatizar los riesgos de no realizar las pruebas ",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar",
                                "Reiterar la necesidad de realizar más pruebas y aceptar la decisión del paciente",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar",
                                "Acatar la negativa del paciente de primera intención y comunicar al familiar"
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
                                "Un paciente de 42 años es llevado por personal del Servicio de Urgencias Médicas a una unidad hospitalaria pública. Se reporta en calidad de desconocido y que al deambular por la vía pública fue atropellado por un vehículo automotor (un camión), Pierde el estado de alerta de forma inmediata y personal de Urgencias lo atiende, lo intuban por Glasgow de 7, se canaliza vena periférica y lo llevan al hospital. Se solicita tomografía de cráneo por traumatismo craneoencefálico severo. Tiene un hematoma epidural occipitotemporal derecho, con desplazamiento de estructuras de línea media. Requiere manejo quirúrgico de urgencia. ¿Cuál es la obligación del médico en este caso?",
                                "Solicitar de forma urgente apoyo a Trabajo Social para localizar a familiares que autoricen el evento quirúrgico, para respetar en todo momento los principios de autonomía y beneficencia",
                                "iniciar el apoyo con cuidados paliativos con el objetivo de respetar el principio de beneficencia, ya que el pronóstico es malo para el paciente y no se cuenta con familiares",
                                "Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia",
                                "Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia",
                                "Autorizar al Cirujano el tratamiento quirúrgico debido a la urgencia que representa, a pesar de no contar con familiares de modo que se respete el principio de beneficencia"
                        },
                        {
                                "Una paciente de 55 años fue derivada al Servicio de Ginecología por sospecha de cáncer de mama. El médico especialista le confirma el diagnóstico y de acuerdo con los resultados es necesario programarla para una mastectomía, por lo que se le pide que firme el consentimiento informado. La mujer se muestra abrumada ante las noticias y, al ser analfabeta funcional, no comprende claramente los términos empleados ni lo indicado en el consentimiento escrito de manera que se niega a firmar. ¿Cómo debe proceder el médico ante esta situación?",
                                "Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar",
                                "Explicar nuevamente a la paciente tanto el diagnostico como las opciones terapéuticas con palabras sencillas y, una vez que comprenda la información, solicitar que firme el consentimiento",
                                "Solicitar al Departamento de Psicología acompañamiento para la paciente en su proceso de toma de decisiones para su tratamiento",
                                "Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar",
                                "Llamar a un familiar para explicarle con detalle el diagnóstico, así como los riesgos y beneficios de las opciones terapéuticas, posteriormente solicitar la firma del consentimiento informado por la paciente y su familiar"
                        },{
                        "Una paciente de 23 años, primigesta, con 39 SDG, acude a centro de salud con actividad uterina regular de 2 horas. Tras ser evaluada se decide que el médico responsable del centro de salud le brinde atención del trabajo de parto, pues el hospital más cercano se encuentra a 2 horas; se obtiene un recién nacido de sexo masculino. ¿Quién debe expedir el certificado de nacimiento?",
                        "La unidad médica de segundo nivel",
                        "El médico tratante que acredita el nacimiento",
                        "El médico pediatra que decida la madre",
                        "El médico tratante que acredita el nacimiento",
                        "El médico tratante que acredita el nacimiento"
                },
                        {
                                "Un lactante de 7 meses presentó fiebre, malestar general y pérdida del apetito; fue tratado en casa con acetaminofén. A la mañana siguiente presentó fiebre alta, rash cutáneo, rechazo del alimento y de líquidos, y sus padres no pudieron despertarlo. Entonces, acudieron a consulta con un médico en una farmacia popular, el doctor, al término de su turno y ya por retirarse, los atendió de manera rápida y diagnosticó infección respiratoria alta, lo trato con medicación antifebril y les indicó que consultaran al Departamento de Emergencias si los síntomas empeoraban. En la tarde presentó convulsiones y fue llevado al Servicio de Urgencias. Se diagnosticó meningitis por neumococo que tuvo como secuela una sordera definitiva Con base en el caso ¿cuál es el tipo de responsabilidad civil del profesional en salud?",
                                "Negligencia",
                                "Impericia",
                                "Por dolo",
                                "Negligencia",
                                "Negligencia"
                        },
                        {
                                "Se presenta en consulta una mujer con datos clínicos compatibles con anemia ferropénica, por lo que se decide hacer una biometría hemática con reticulocitos para evaluarla. Según la guía Prevención, diagnóstico y tratamiento de la anemia por deficiencia de hierro en nos y adultos, ¿cuál es el siguiente paso en la atención de la paciente",
                                "Iniciar tratamiento con sulfato ferroso y citar en 1 mes con una nueva biometría hemática",
                                "Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar",
                                "Hacer un estudio de frotis sanguíneo y canalizar al Servicio de Hematología",
                                "Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar",
                                "Solicitar un perfil de hierro y ferritina sérica y citar para volver a valorar"
                        },
                        {
                                "Un paciente de 52 años, identificado en un módulo de prevención con diagnóstico probable de hipertensión arterial sistémica, acude a su primera consulta en el primer nivel de atención con TA de 140/90 mmhg; no reporta sintomatología, ni otros antecedentes familiares o patológicos. De acuerdo con las guías de práctica clínica, ¿qué conducta médica se debe seguir?",
                                "modificar el estilo de vida",
                                "iniciar tratamiento farmacológico",
                                "enviar a especialista de segundo nivel de atención",
                                "modificar el estilo de vida",
                                "modificar el estilo de vida"
                        },
                        {
                                "Una paciente de 29 años de edad acude a consulta de urgencia por presentar dolor torácico, taquicardia y sensación de muerte inminente al estar en el transporte público. Menciona que ha tenido episodios iguales por lo menos cuatro veces; éstos son desencadenados al exponerse a lugares cerrados y con mucha gente o al tener que hablar en público. Comenta que su madre también padece los mismos episodios La medida asociada al tratamiento médico para disminuir los episodios y la ansiedad en la paciente es la terapia.",
                                "cognitivo-conductual",
                                "de desensibilización ocular",
                                "psicoanalítica",
                                "cognitivo-conductual",
                                "cognitivo-conductual"
                        },
                        {
                                "Un paciente de 35 años acude a su centro de Salud para solicitar un certificado médico, Presenta dolores de cabeza frecuentes, dificultad para conciliar el sueño por las noches y somnolencia excesiva durante el día, comenta que estos síntomas eran frecuentes en los últimos 8 meses en los que trabajo como vigilante, intensificándose en el último mes. Entre sus antecedentes heredofamiliares destaca que su madre presentaba insomnio con regularidad. Como medida para mejorar los hábitos de sueño, le recomendación es…",
                                "iniciar el uso de medicamentos como benzodiacepinas",
                                "realizar actividad física con un programa gradual de ejercicio vigoroso por las tardes",
                                "programar la misma hora para despertarse todos los días y evitar siestas durante el día.",
                                "iniciar el uso de medicamentos como benzodiacepinas",
                                "iniciar el uso de medicamentos como benzodiacepinas"
                        },
                        {
                                "Una paciente de 65 años, atendida en consulta externa y acompañada por su nieto, menciona que hay ocasiones en que no recuerda el nombre de sus hijos y tiene cierta limitación para llevar a cabo sus actividades de la vida diaria; asimismo, presenta cambios en su comportamiento. Ante la sospecha diagnóstica de demencia, ¿cuál es la recomendación nutricional para prevenir la progresión de la enfermedad?",
                                "Adicionar alimentos con folatos, vitaminas C, E y omega 3",
                                "Aumentar consumo de carne roja rica en grasa, homocisteína y flavonoides",
                                "Incluir suplementos con Ginkgo biloba, magnesio y zinc",
                                "Adicionar alimentos con folatos, vitaminas C, E y omega 3",
                                "Adicionar alimentos con folatos, vitaminas C, E y omega 3"
                        },
                        {
                                "Un paciente de 65 años acude al centro de Salud acompañado de un familiar porque sufrió una caída desde su propia altura debido a que Últimamente presenta dificultad para ponerse de pie y caminar, lo que ha provocado que permanezca en cama. El paciente tiene antecedente de diabetes mellitus tipo 2 e hipertensión arterial sistémica controladas, diagnosticadas hace 5 años, así como dermatitis actínica crónica inactiva, de 10 años de diagnóstico. En la EF, registra TA de 120/80 mmHg, FC de 89/min, FR de 16/min, T 36.5 °C, peso de 65 kg, talla de 160 cm y cara con placas liquenificadas en frente y v del cuello; asimismo, se observa hematoma de gran dimensión en glúteo derecho y limitación de movimiento. Con base en el cuadro actual, ¿cuál es la acción de prevención primaria para evitar complicaciones?",
                                "Prescribir esteroides tópicos",
                                "Referir a médico internista",
                                "Prescribir movilidad funcional",
                                "Prescribir esteroides tópicos",
                                "Prescribir esteroides tópicos"
                        },
                        {
                                "Un paciente de 51 años acude a consulta porque presenta malestar epigástrico y saciedad temprana, además de que ha percibido pérdida involuntaria de peso, 5 kg aproximadamente, en los últimos 3 meses. Entre sus antecedentes refiere tabaquismo a razón de 15 cigarros día y enfermedad acidopéptica para la que en ocasiones toma omeprazol. Se sospecha de neoplasia gástrica. ¿Qué prueba se debe realizar para la detección oportuna?",
                                "Test del aliento para Helicobacter pylori",
                                "Endoscopia con toma de muestra",
                                "Tomografía de alta resolución",
                                "Endoscopia con toma de muestra",
                                "Endoscopia con toma de muestra"
                        },
                        {
                                "Una paciente de 61 años, con antecedente familiar de primer grado con cáncer de mama, menopausia a los 55 años y tabaquismo positivo hace 20 años, a razón de un cigarro por día, acude a consulta por una masa dolorosa en la parte superior externa de la mama derecha. En la EF, los signos vitales se encuentran dentro de los parámetros normales, la mama derecha sin alteraciones, no se logra palpar nódulo o tumoración. ¿Cuál es el estudio de elección para descartar patología maligna en este caso?",
                                "Resonancia magnética",
                                "Ultrasonido",
                                "Mastografía",
                                "Mastografía",
                                "Mastografía"
                        },
                        {
                                "Acude a consulta madre con recién nacido de 3 semanas. Indica que nació en casa con una partera, pero llama la atención el llanto ronco, que duerme mucho, lo tiene que despertar para que coma y evacua una vez al día. En la EF, se detecta un tinte ictérico, hernia umbilical, piel seca y letargia. ¿Cuál es el estudio para obtener el diagnóstico definitivo del paciente?",
                                "Tamiz metabólico básico",
                                "Prueba de función hepática",
                                "Tamiz cardiaco",
                                "Tamiz metabólico básico",
                                "Tamiz metabólico básico"
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
                                "Un paciente de 42 años acude a consulta por presentar debilidad en hemicara derecha de inicio súbito, dolor retroauricular derecho y parestesias en carrillo derecho de 12 horas de evolución. En la exploración física se detecta parálisis facial periférica derecha de grado 2 en escala House- Brackmann y el resto de la exploración neurológica sin alteraciones. Se determina diagnóstico de parálisis facial idiopática. ¿Cuál es el tratamiento farmacológico de primera elección para el paciente?",
                                "Gabapentina",
                                "Prednisona",
                                "Aciclovir",
                                "Prednisona",
                                "Prednisona"
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
                                "Es llevado a consulta un niño de 2 años y medio porque presenta cuadro clínico caracterizado por otalgia, fiebre y ataque al estado general. Durante la exploración física se detectan signos clínicos que orientan a otitis media aguda y destaca conducta esterotípica verbal en el menor. Se interroga a la madre respecto a la conducta habitual del paciente y se describe incapacidad para jugar de manera simbólica. Se indica tratamiento antimicrobiano. Con base en la normativa vigente, ¿Qué indicación se le debe brindar a la madre respecto al neurodesarrollo del menor?",
                                "Referencia para valoración diagnóstica",
                                "Taller de estimulación temprana",
                                "Lectura de material sobre estimulación",
                                "Referencia para valoración diagnóstica",
                                "Referencia para valoración diagnóstica"
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
                                "Asiste a consulta una paciente de 23 años porque presenta dolor y congestión mamaria. Menciona haber tenido un parto eutócico hace 1 semana; el bebé nació con labio leporino y paladar hendido unilateral, por lo que tiene gran dificultad para la succión. En la exploración física se registran signos vitales normales, mamas secretantes congestivas, con zonas induradas y dolorosas en el cuadrante superoexterno de ambas mamas, sin cambios en la coloración de la piel y abundante secreción láctea por ambos pezones. En la exploración ginecológica se detecta útero bien involucionado, loquios normales y episiorrafia en vías de cicatrización. ¿Qué manejo se le debe recomendar para el proceso de lactancia?",
                                "Extraer la leche materna y extraerla con biberón",
                                "Utilizar obturador palatino durante la alimentación",
                                "Acudir a asesoría para modificar técnica de lactancia",
                                "Acudir a asesoría para modificar técnica de lactancia",
                                "Acudir a asesoría para modificar técnica de lactancia"
                        },

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
                        {"¿Qué técnica de documentación de requerimientos se enfoca en crear una representación visual de los requisitos y su relación?",
                                "Diagramas de casos de uso", "Entrevistas", "Prototipos",
                                "Diagramas de casos de uso",
                                "Los diagramas de casos de uso son una técnica de documentación que permite crear una representación visual de los requisitos y su relación, lo que facilita su comprensión y análisis."},

                        {"¿Qué herramienta de documentación de requerimientos permite especificar los requisitos de un software utilizando lenguaje natural estructurado?",
                                "Lenguaje de Modelado Unificado (UML)", "Requisitos en lenguaje natural (NLR)", "Hojas de cálculo",
                                "Requisitos en lenguaje natural (NLR)",
                                "Los requisitos en lenguaje natural son una herramienta de documentación de requerimientos que permiten especificar los requisitos de un software utilizando lenguaje natural estructurado, lo que facilita su comprensión y análisis."},

                        {"¿Qué técnica de documentación de requerimientos se enfoca en identificar las entradas, salidas y procesos de un sistema?",
                                "Diagramas de flujo de datos", "Casos de uso", "Prototipos",
                                "Diagramas de flujo de datos",
                                "Los diagramas de flujo de datos son una técnica de documentación que permite identificar las entradas, salidas y procesos de un sistema, lo que facilita su comprensión y análisis."},

                        {"¿Qué herramienta de documentación de requerimientos permite crear un modelo visual de los requisitos utilizando iconos y símbolos estandarizados?",
                                "Lenguaje de Modelado Unificado (UML)", "Lenguaje de descripción de interfaces de usuario (UIDL)", "Hojas de cálculo",
                                "Lenguaje de Modelado Unificado (UML)",
                                "El Lenguaje de Modelado Unificado (UML) es una herramienta de documentación de requerimientos que permite crear un modelo visual de los requisitos utilizando iconos y símbolos estandarizados, lo que facilita su comprensión y análisis."},

                        {"¿Qué técnica de documentación de requerimientos se enfoca en identificar y describir los requisitos funcionales y no funcionales de un sistema?",
                                "Especificación de requisitos", "Diagramas de flujo de datos", "Casos de uso",
                                "Especificación de requisitos", "La especificación de requisitos es una técnica de documentación que se enfoca en identificar y describir los requisitos funcionales y no funcionales de un sistema, lo que facilita su comprensión y análisis."},

                        {"¿Cuál es el propósito de la técnica de casos de uso en la documentación de requerimientos?",
                                "Para representar cómo los usuarios interactúan con el sistema", "Para identificar los requerimientos no funcionales", "Para definir el alcance del proyecto",
                                "Para representar cómo los usuarios interactúan con el sistema",
                                "La técnica de casos de uso se utiliza para representar cómo los usuarios interactúan con el sistema y para definir los objetivos y necesidades del usuario."},

                        {"¿Cuál de las siguientes herramientas es útil para la documentación de requerimientos en un entorno colaborativo?",
                                "Diagramas de flujo", "Tablas de especificación", "Tableros Kanban",
                                "Tableros Kanban",
                                "Los tableros Kanban son herramientas visuales que permiten a los equipos de desarrollo de software colaborar y coordinar las tareas de manera eficiente."},

                        {"¿Qué es una matriz de trazabilidad de requerimientos?",
                                "Una herramienta que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto", "Una herramienta para identificar los requerimientos no funcionales", "Una herramienta para definir el alcance del proyecto",
                                "Una herramienta que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto",
                                "La matriz de trazabilidad de requerimientos es una herramienta útil que ayuda a rastrear la relación entre los requerimientos y otras áreas del proyecto, como los casos de prueba, el diseño y la implementación."},



                        {"¿Qué es un prototipo de software?",
                                "Una representación temprana y simplificada de un sistema que ayuda a validar los requerimientos", "Una lista de requerimientos de alto nivel", "Un documento detallado que describe los requerimientos",
                                "Una representación temprana y simplificada de un sistema que ayuda a validar los requerimientos",
                                "Un prototipo de software es una versión temprana y simplificada de un sistema que se utiliza para validar los requerimientos y recibir retroalimentación temprana de los usuarios."},

                        {"¿Qué técnica de documentación de requerimientos es útil para mostrar la estructura jerárquica de un conjunto de requerimientos?",
                                "Diagrama de estructura de requerimientos (DSR)", "Matriz de trazabilidad", "Diagrama de flujo de datos (DFD)", "Diagrama de estructura de requerimientos (DSR)",
                                "El DSR es una técnica que muestra la estructura jerárquica de un conjunto de requerimientos, proporcionando una visión general del sistema."},

                        {"¿Qué herramienta de documentación de requerimientos se utiliza para capturar, rastrear y gestionar los cambios en los requerimientos?",
                                "Herramientas de gestión de requerimientos", "Hojas de cálculo", "Diagramas de flujo",
                                "Herramientas de gestión de requerimientos",
                                "Estas herramientas permiten capturar, rastrear y gestionar los cambios en los requerimientos, y garantizan la trazabilidad de los mismos."},

                        {"¿Qué técnica de documentación de requerimientos se utiliza para describir las acciones que el usuario puede realizar en el sistema?",
                                "Casos de uso", "Diagrama de flujo de datos (DFD)", "Modelado de procesos de negocio (BPM)",
                                "Casos de uso",
                                "Los casos de uso se utilizan para describir las acciones que el usuario puede realizar en el sistema y cómo el sistema responde a ellas."},

                        {"¿Qué herramienta de documentación de requerimientos se utiliza para definir y clasificar los requerimientos de manera estructurada?",
                                "Esquemas de clasificación de requerimientos", "Herramientas de gestión de configuración", "Matrices de trazabilidad",
                                "Esquemas de clasificación de requerimientos",
                                "Los esquemas de clasificación de requerimientos se utilizan para definir y clasificar los requerimientos de manera estructurada, lo que facilita su gestión y seguimiento."},

                        {"¿Qué técnica de documentación de requerimientos se utiliza para representar los requerimientos en términos de entradas, procesos y salidas?",
                                "Diagrama de flujo de datos (DFD)", "Casos de uso", "Matriz de trazabilidad",
                                "Diagrama de flujo de datos (DFD)",
                                "Los DFD se utilizan para representar los requerimientos en términos de entradas, procesos y salidas, lo que ayuda a visualizar el sistema desde una perspectiva funcional."},
                        {"¿Qué técnica se utiliza para representar gráficamente los flujos de trabajo y la interacción entre los usuarios y el sistema?",
                                "Diagrama de flujo", "Diagrama de casos de uso", "Diagrama de secuencia",
                                "Diagrama de casos de uso",
                                "El diagrama de casos de uso se utiliza para representar los flujos de trabajo y la interacción entre los usuarios y el sistema, mostrando los actores involucrados y las funcionalidades del sistema."},
                        {"¿Qué herramienta se utiliza para crear modelos de requerimientos y documentación de especificaciones de software?",
                                "Microsoft Word", "Microsoft Excel", "Microsoft Visio",
                                "Microsoft Visio",
                                "Microsoft Visio es una herramienta utilizada para crear modelos de requerimientos y documentación de especificaciones de software, permitiendo crear diagramas de flujo, diagramas de clases, entre otros."},

                        {"¿Qué técnica se utiliza para identificar los requerimientos mediante la observación de los usuarios y su entorno?",
                                "Entrevistas", "Encuestas", "Observación",
                                "Observación",
                                "La observación es una técnica utilizada para identificar los requerimientos mediante la observación directa de los usuarios y su entorno, permitiendo identificar sus necesidades y comportamientos."},

                        {"¿Qué técnica se utiliza para identificar los requerimientos mediante la discusión y el debate entre los miembros del equipo de desarrollo y los stakeholders?",
                                "Entrevistas", "Reuniones de grupo", "Entrevistas a grupos",
                                "Reuniones de grupo",
                                "Las reuniones de grupo son una técnica utilizada para identificar los requerimientos mediante la discusión y el debate entre los miembros del equipo de desarrollo y los stakeholders, permitiendo llegar a un consenso sobre los requerimientos."},

                        {"¿Qué herramienta se utiliza para gestionar y trazar los requerimientos durante todo el ciclo de vida del software?",
                                "Microsoft Excel", "Microsoft Word", "JIRA",
                                "JIRA",
                                "JIRA es una herramienta utilizada para gestionar y trazar los requerimientos durante todo el ciclo de vida del software, permitiendo asignar tareas, hacer seguimiento y priorizar los requerimientos."}
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





