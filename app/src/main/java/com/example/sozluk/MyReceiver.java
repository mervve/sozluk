package com.example.sozluk;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {



    public void onReceive(Context context, Intent intent) {
        Log.e("buraya geldimi","yes");
        bildirimGoster(context);


    }
    private void bildirimGoster(Context context) {

        NotificationCompat.Builder builder;

        NotificationManager bildirimYoneticisi =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(context,MainActivity.class);

        PendingIntent gidilecekIntent = PendingIntent.getActivity(context,1,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Oreo sürümü için bu kod çalışacak.

            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(context, kanalId);

            builder.setContentTitle("Training")  // gerekli
                    .setContentText("calisma zamani")  // gerekli
                    .setSmallIcon(R.drawable.dumbbell) // gerekli
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setContentIntent(gidilecekIntent);

        } else { // OREO Sürümü haricinde bu kod çalışacak.

            builder = new NotificationCompat.Builder(context);

            builder.setContentTitle("Training")  // gerekli
                    .setContentText("calisma zamani")  // gerekli
                    .setSmallIcon(R.drawable.dumbbell) // gerekli
                    .setContentIntent(gidilecekIntent)
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        bildirimYoneticisi.notify(1,builder.build());

    }
}
