package com.kmpegel.medi.ui.configuracion;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kmpegel.egelmedi.R;
import com.kmpegel.medi.MainActivity;


public class NotificacionReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "channel_name";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtener la hora seleccionada desde el intent
        String horaSeleccionada = intent.getStringExtra("horaSeleccionada");

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Recordatorio")
                .setContentText("Es hora de estudiar")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Crear un intent para abrir la app cuando se toque la notificación
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());


    }


}

