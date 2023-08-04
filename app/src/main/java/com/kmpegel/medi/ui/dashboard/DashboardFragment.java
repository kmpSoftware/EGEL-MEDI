package com.kmpegel.medi.ui.dashboard;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kmpegel.egelmedi.R;
import com.kmpegel.egelmedi.databinding.FragmentDashboardBinding;
import com.kmpegel.medi.ui.Preguntas.ExamenCompleto;
import com.kmpegel.medi.ui.Subtemas.subtemas;
import com.kmpegel.medi.ui.Subtemas.subtemas2;
import com.kmpegel.medi.ui.Subtemas.subtemas3;
import com.kmpegel.medi.ui.Subtemas.subtemas4;
import com.kmpegel.medi.ui.Subtemas.subtemas5;
import com.kmpegel.medi.ui.Subtemas.subtemas6;
import com.kmpegel.medi.ui.tarjetas.Tarjetas;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private Button Button1;
    private Button Button2;
    private Button Button3;
    private Button Button4;
    private Button Button5;
    private Button Button6;

    private Button Button7;
    private Button Button8;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        com.kmpegel.medi.ui.dashboard.DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(com.kmpegel.medi.ui.dashboard.DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false); // Inflate the layout for this fragment
        View root = binding.getRoot();


        Button1 = root.findViewById(R.id.button1);
        Button2 = root.findViewById(R.id.button2);
        Button3 = root.findViewById(R.id.button3);
        Button4 = root.findViewById(R.id.button4);
        Button5 = root.findViewById(R.id.button5);
        Button6 = root.findViewById(R.id.button6);
        Button7 = root.findViewById(R.id.button7);
        Button8 = root.findViewById(R.id.button8);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas.class);
                startActivity(intent);
            }
        });

    Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas2.class);
                startActivity(intent);
            }
        });

    Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas3.class);
                startActivity(intent);
            }
        });

    Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas4.class);
                startActivity(intent);
            }
        });

    Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas5.class);
                startActivity(intent);
            }
        });

    Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), subtemas6.class);
                startActivity(intent);
            }
        });

        Button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                Intent intent = new Intent(getActivity(), ExamenCompleto.class);
                intent.putExtra("BOTON_PRESIONADO", 1);
                startActivity(intent);
            }
        });
        Button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //abrir nueva actividad con varios botones para seleccionar el tipo de reporte
                    Intent intent = new Intent(getActivity(), Tarjetas.class);
                    intent.putExtra("BOTON_PRESIONADO", 2);
                    startActivity(intent);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}