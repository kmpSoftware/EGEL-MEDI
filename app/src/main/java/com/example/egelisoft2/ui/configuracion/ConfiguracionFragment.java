package com.example.egelisoft2.ui.configuracion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.egelisoft2.MainActivity;
import com.example.egelisoft2.R;
import com.example.egelisoft2.databinding.FragmentNotificationsBinding;
import com.example.egelisoft2.ui.Preguntas.PreguntasActivity;

import java.util.Calendar;

public class ConfiguracionFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private String horaSeleccionada = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ConfiguracionViewModel configuracionViewModel = new ViewModelProvider(this).get(ConfiguracionViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        configuracionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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

        return root;

    }


    private void configurarAlarma(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(requireContext(), NotificacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0);

        // Configurar la alarma para que se repita todos los días a la hora seleccionada
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
