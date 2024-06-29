package br.ufms.progmobile.caliopelib.useCases;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import br.ufms.progmobile.caliopelib.R;

public class AlarmeDisplayer {
    public static final String CHANNEL_ID = "CaliopeLibNotificationChannel";
    public static final String CHANNEL_NAME = "Calíope Lib";

    public static void createNotificationChannel(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            nChannel.setDescription("Calíope Lib");
            NotificationManager sysManager = context.getSystemService(NotificationManager.class);
            sysManager.createNotificationChannel(nChannel);
        }
    }

    public static Notification createNotification(Context context, String livro) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_menu_book_24)
                .setContentTitle("Hora de ler!")
                .setContentText(livro)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat nManager = NotificationManagerCompat.from(context);

        return nBuilder.build();
    }
}
