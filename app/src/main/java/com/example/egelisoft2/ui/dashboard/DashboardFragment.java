package com.example.egelisoft2.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egelisoft2.R;
import com.example.egelisoft2.databinding.FragmentDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private Button Button1;
    private Button Button2;
    private Button Button3;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false); // Inflate the layout for this fragment
        View root = binding.getRoot();

        Button1 = root.findViewById(R.id.button1);
        Button2 = root.findViewById(R.id.button2);
        Button3 = root.findViewById(R.id.button3);



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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}