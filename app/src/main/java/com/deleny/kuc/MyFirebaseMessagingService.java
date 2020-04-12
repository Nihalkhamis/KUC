package com.deleny.kuc;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.hawk.Hawk;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM_Service";

    private onmsgRecived recived;
    String token = FirebaseInstanceId.getInstance().getToken();

    String getToken;

    public MyFirebaseMessagingService(onmsgRecived recived) {
        this.recived = recived;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().subscribeToTopic("updates");

        Hawk.init(this).build();

    }

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        else
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d(TAG, "onMessageReceived: " + cn.getClassName());


        getToken = Hawk.get("token");

        Log.d(TAG, "onCreate: "+getToken);

    }


//    private void showNotification(Map<String, String> data){
//
//        String title = data.get("title").toString();
//        String body = data.get("body").toString();
//
//
//        Log.d("TTTTTTTTTT", "showNotification: "+title);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATIO_CHANEL_ID = "com.example.smatech.ay5edma";
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIO_CHANEL_ID, "notification"
//                    , NotificationManager.IMPORTANCE_DEFAULT);
//            notificationChannel.setDescription("Channel");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.BLUE);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATIO_CHANEL_ID);
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setContentInfo("Info");
//        Log.d(TAG, "onMessageReceived: notContained");
//        Intent resultIntent = new Intent(this, SplashActivity.class).putExtra("go_to_fragment",true);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(SplashActivity.class);
//       stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        notificationBuilder.setContentIntent(resultPendingIntent);
//        notificationBuilder.setAutoCancel(true);
////
////        Map<String, String> notificationMessage = data;
////        if (notificationMessage.containsKey("targetScreen")) {
////            Intent resultIntent;
////            Log.d(TAG, "showNotification: " + notificationMessage.get("targetScreen"));
////            if (notificationMessage.get("targetScreen").equals("message")) {
////                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("chat_id"));
////                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("from_id"));
////                Log.d(TAG, "onMessageReceived: " + notificationMessage.get("to_id"));
////                resultIntent = new Intent(this, SplashActivity.class)
////                        .putExtra("to_id", notificationMessage.get("to_id"))
////                        .putExtra("req_id", notificationMessage.get("request_id"));
////            } else if (notificationMessage.get("targetScreen").equals("request")) {
////                resultIntent = new Intent(this, SplashActivity.class);
////            } else {
////                resultIntent = new Intent(this, SplashActivity.class);
////
////            }
////            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////            stackBuilder.addParentStack(SplashActivity.class);
////            stackBuilder.addNextIntent(resultIntent);
////
////            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
////            notificationBuilder.setContentIntent(resultPendingIntent);
////            notificationBuilder.setAutoCancel(true);
////
////
////        }
////        else {
////            Log.d(TAG, "onMessageReceived: notContained");
////            Intent resultIntent = new Intent(this, SplashActivity.class).putExtra("go_to_fragment",true);
////            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////            stackBuilder.addParentStack(SplashActivity.class);
////            stackBuilder.addNextIntent(resultIntent);
////            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
////            notificationBuilder.setContentIntent(resultPendingIntent);
////            notificationBuilder.setAutoCancel(true);
////
////        }
//
//        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
//    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATIO_CHANEL_ID = "com.smatech.rahmaapp";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATIO_CHANEL_ID, "notification"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATIO_CHANEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setContentInfo("Info")
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));


        Log.d(TAG, "onMessageReceived: notContained");
            Intent resultIntent = new Intent(this, SplashActivity.class).putExtra("go_to_fragment",true);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(SplashActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationBuilder.setContentIntent(resultPendingIntent);
            notificationBuilder.setAutoCancel(true);


        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

     Hawk.put("token",s);

        Log.d(TAG, "onNewToken: " + s);
//        SharedPreferences sharedpreferences = getSharedPreferences(Config.PREFS_NAME, this.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("FIRETOKEN", ""+s);
//        editor.commit();
       // Hawk.put(Constants.TOKEN, s);
    }





}
