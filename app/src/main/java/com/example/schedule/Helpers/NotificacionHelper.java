package com.example.schedule.Helpers;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificacionHelper extends Application {
    public  static final String CANAL_1_ID = "canal1";
    private  static final String CANAL_1_NOMBRE = "Canal 1";
    private  static final String CANAL_1_DESCRIPCION = "Este es el canal 1";

    @Override
    public void onCreate() {
        super.onCreate();
        crearCanalesNotificaciones();
    }

    private void crearCanalesNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            NotificationChannel canal1 = new NotificationChannel(
                    CANAL_1_ID,
                    CANAL_1_NOMBRE,
                    NotificationManager.IMPORTANCE_HIGH
            );
            canal1.setDescription(CANAL_1_DESCRIPCION);
            //Configurar notificaciones
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(canal1);
        }
    }
}
