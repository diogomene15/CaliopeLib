package br.ufms.progmobile.caliopelib.useCases;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String livro = intent.getStringExtra("livro");

        Notification notification = AlarmeDisplayer.createNotification(context, livro);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }
}
