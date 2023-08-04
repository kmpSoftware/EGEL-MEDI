package com.kmpegel.medi.ui.configuracion;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.kmpegel.egelmedi.databinding.FragmentNotificationsBinding;

import java.util.Calendar;

public class ConfiguracionFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private String horaSeleccionada = "";



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        com.kmpegel.medi.ui.configuracion.ConfiguracionViewModel configuracionViewModel = new ViewModelProvider(this).get(com.kmpegel.medi.ui.configuracion.ConfiguracionViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ImageView imageView = binding.imageView3;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConfiguracionFragment.this.getContext());
                alertDialogBuilder.setTitle("Ayuda");
                alertDialogBuilder.setMessage("Aquí puedes seleccionar una hora para recibir un recordatorio diario para estudiar");
                alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Opcional: cerrar el cuadro de diálogo al hacer clic en el botón "Aceptar"
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        Button selectTimeButton = binding.selectTimeButton;
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                                configurarAlarma(c);
                                Toast.makeText(getActivity(), "Hora seleccionada: " + horaSeleccionada, Toast.LENGTH_SHORT).show();
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        Button disableNotificationsButton = binding.disableNotificationsButton;
        disableNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar la alarma de las notificaciones
                AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(requireContext(), com.kmpegel.medi.ui.configuracion.NotificacionReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(),  0, intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.cancel(pendingIntent);

                // Mostrar un toast para indicar que se han desactivado las notificaciones
                Toast.makeText(getActivity(), "Notificaciones desactivadas", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }



    private void configurarAlarma(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(requireContext(), com.kmpegel.medi.ui.configuracion.NotificacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(),  0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Configurar la alarma para que se repita todos los días a la hora seleccionada
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        //ocultar la animacion donde desaparece la action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        //ocultar la animacion donde desaparece la action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
