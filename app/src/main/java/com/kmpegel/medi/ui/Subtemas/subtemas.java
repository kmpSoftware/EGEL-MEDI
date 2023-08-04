package com.kmpegel.medi.ui.Subtemas;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import com.kmpegel.egelmedi.R;
import com.kmpegel.medi.ui.Preguntas.PreguntasActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class subtemas extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button btnVolver;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtemas);

        // Establecer el modo de pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Busca el botón por su id
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        btnVolver = findViewById(R.id.btnVolver);

        // Agrega un listener al botón
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 1
                intent.putExtra("BOTON_PRESIONADO", 1);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 2
                intent.putExtra("BOTON_PRESIONADO", 2);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 3
                intent.putExtra("BOTON_PRESIONADO", 3);

                // Inicia la nueva actividad
                finish();
                startActivity(intent);
            }
        });


    }

    //funcion para volver
    public void volver(View v){
        //cerrar la actividad actual

        // Navegar hacia el fragmento DashboardFragment
        NavController navController = Navigation.findNavController(subtemas.this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_dashboard);
        finish();
    }
    public void abrirDialogo(View v){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Información");
        dialogo.setMessage("Ten en cuenta que estos subtemas están sujetos a cambios y actualizaciones a medida que avanza la planificación de los años venideros. Si hay alguna modificación en los temas mencionados, te pedimos que nos lo hagas saber enviando un correo electrónico a softwarekmp@gmail.com. También estamos disponibles para responder cualquier pregunta o comentario que puedas tener. \n " +
                "                 \n" +
                "¡Gracias por tu interés en nuestra guía oficial! \n" +
                "                 \n" +
                "                 Atentamente, \n" +
                "                  Guias KMP");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(), "Botón Aceptar pulsado", Toast.LENGTH_SHORT).show();
            }
        });
        dialogo.show();
    }
}
