package com.example.egelisoft2.ui.Subtemas;

import static android.app.PendingIntent.getActivity;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.egelisoft2.R;
import com.example.egelisoft2.databinding.ActivityMainBinding;
import com.example.egelisoft2.databinding.FragmentNotificationsBinding;
import com.example.egelisoft2.ui.Preguntas.PreguntasActivity;
import com.example.egelisoft2.ui.dashboard.DashboardFragment;
import com.example.egelisoft2.ui.home.HomeFragment;
import com.example.egelisoft2.ui.notifications.NotificationsFragment;
import com.example.egelisoft2.ui.notifications.NotificationsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class subtemas extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;

    private BottomNavigationView bottomNavigationView;
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.subtemas);



        // Busca el botón por su id
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        // Agrega un listener al botón
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para abrir la nueva actividad
                Intent intent = new Intent(subtemas.this, PreguntasActivity.class);

                // Agrega un identificador al Intent para indicar que se presionó el botón 1
                intent.putExtra("BOTON_PRESIONADO", 1);

                // Inicia la nueva actividad
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
                startActivity(intent);
            }
        });
    }
}
