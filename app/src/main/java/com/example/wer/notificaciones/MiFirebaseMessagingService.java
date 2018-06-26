package com.example.wer.notificaciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MiFirebaseMessagingService extends FirebaseMessagingService{
    public static  final String TAG = "Hola";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de :"+ from);

        remoteMessage.getNotification();

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Notificacion: "+ remoteMessage.getNotification().getBody());

            mostrarNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG,"Data " + remoteMessage.getData());
        }


    }

    private void mostrarNotificacion(String title, String body) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificacionBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.gimp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(new long[]{0, 300, 200, 300})
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacionBuilder.build());


    }

}
