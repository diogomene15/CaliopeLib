package br.ufms.progmobile.caliopelib.useCases;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import br.ufms.progmobile.caliopelib.database.AppDatabase;
import br.ufms.progmobile.caliopelib.entities.Alarme;

public class AlarmeNotificacao {
    public static void agendarAlarme(Context context, Alarme alarm) {
        AppDatabase db = AppDatabase.getDatabase(context);

        int hora = alarm.getHora();
        int minuto = alarm.getMinuto();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int pendingIntentId = (int) System.currentTimeMillis();
        alarm.setPendingIntentId(pendingIntentId);


        db.alarmeDao().insert(alarm);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmeReceiver.class);
        intent.putExtra("livro", alarm.getLivro());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingIntentId, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void atualizarAlarme(Context context, Alarme alarm) {
        AppDatabase db = AppDatabase.getDatabase(context);

        int hora = alarm.getHora();
        int minuto = alarm.getMinuto();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int pendingIntentId = (int) System.currentTimeMillis();
        alarm.setPendingIntentId(pendingIntentId);


        db.alarmeDao().update(alarm);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmeNotificacao.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingIntentId, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelarAlarme(Context context, int alarmId) {
        AppDatabase db = AppDatabase.getDatabase(context);
        Alarme alarm = db.alarmeDao().getAlarme(alarmId);
        if (alarm != null) {
            int pendingIntentId = (int) alarm.getPendingIntentId();

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmeReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, pendingIntentId, intent, 0);

            alarmManager.cancel(pendingIntent);

            db.alarmeDao().delete(alarm);
        }
    }


}
