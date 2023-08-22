package com.kmpegel.medi.ui.Preguntas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.kmpegel.egelmedi.R;
import com.kmpegel.medi.ui.Subtemas.subtemas;
import com.kmpegel.medi.ui.Subtemas.subtemas2;
import com.kmpegel.medi.ui.Subtemas.subtemas3;
import com.kmpegel.medi.ui.Subtemas.subtemas4;
import com.kmpegel.medi.ui.Subtemas.subtemas5;
import com.kmpegel.medi.ui.Subtemas.subtemas6;
import com.kmpegel.medi.ui.dashboard.DashboardFragment;

public class FinalActivity extends AppCompatActivity {
    Button button1;
    TextView textView;

    private int tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscreen);


        // Establecer el modo de pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Ocultar la barra de navegación
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Obtener la puntuación del Intent
        button1 = findViewById(R.id.buttonSalir);
        textView = findViewById(R.id.puntuacionTextView);
        double puntuacion = getIntent().getDoubleExtra("puntuacion", 0);
        tipo = getIntent().getIntExtra("tipo", 0);
        int puntuacionRedondeada = (int) Math.round(puntuacion);

        textView.setText("Tu puntuación es:  " + puntuacionRedondeada);

        if (puntuacion >= 6 && tipo == 0) {
            textView.setText("¡Felicidades! Has aprobado \nTu puntuación es: " + puntuacionRedondeada);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif2).into(gifImageView);
        } else if(puntuacion < 6 && tipo == 0) {
            textView.setText("¡Lo siento! Has reprobado\nTu puntuación es: " + puntuacionRedondeada);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif).into(gifImageView);
        } else if (puntuacion > 15 && tipo ==1 ) {
            textView.setText("¡Felicidades! Has aprobado \nTu puntuación es: " + puntuacionRedondeada);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif2).into(gifImageView);
        }else{
            textView.setText("¡Lo siento! Has reprobado\nTu puntuación es: " + puntuacionRedondeada);
            ImageView gifImageView = findViewById(R.id.gifImageView);
            Glide.with(this).load(R.raw.my_gif).into(gifImageView);
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Navegar hacia el fragmento DashboardFragment
        NavController navController = Navigation.findNavController(FinalActivity.this, R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_dashboard);
        finish();
    }


    // Obtiene el Intent de la siguiente actividad según el nombre de la actividad anterior

}
