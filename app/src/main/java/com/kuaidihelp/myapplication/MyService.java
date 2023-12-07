package com.kuaidihelp.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "ForegroundServiceChannel";

    public MyService() {
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.ic_launcher);

        return builder.build();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);

        // 执行您的任务逻辑

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            execCommand(new String[]{"/system/bin/sh", "-c", "ifconfig"});
        }catch(Exception e){
            android.util.Log.d("wxw","isAllowScreenShotChordEnabled e=="+e);
        }
/*        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            execCommand(new String[]{"/system/bin/sh", "-c", "logcat -s hw_monitor > /sdcard/hw_log.txt &"});
        }catch(Exception e){
            android.util.Log.d("wxw","isAllowScreenShotChordEnabled e=="+e);
        }*/
    }
    private static StringBuilder sResult = new StringBuilder("");
    public static int execCommand(String[] command) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        java.lang.Process proc = runtime.exec(command);
        InputStream inputstream = proc.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        sResult.delete(0, sResult.length());
        Log.e("WXW", "---execCommand-----");
        try {
            if (proc.waitFor() == 0) {
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    sResult.append(line);
                }
                return 0;
            } else {
                return -1;
            }
        } catch (InterruptedException e) {
            Log.e("WXW", "---execCommand-----"+e);
            return -1;
        } finally {
            if (null != bufferedreader) {
                try {
                    bufferedreader.close();
                } catch (IOException e) {

                }
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}