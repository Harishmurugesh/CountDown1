package com.example.countdown2;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Locale;

import static com.example.countdown2.App.CHANNEL_ID;
import static com.example.countdown2.MainActivity.title;


public class ExampleService extends Service {


    String text;
    CountDownTimer mCountDownTimer;
    static long[] g = {0};
    static long h;



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        g[0] = intent.getLongExtra("mtimeleftinsec", 0);
        h = intent.getLongExtra("start",0);


            mCountDownTimer = new CountDownTimer((g[0] *1000) , 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(g[0] > 0)
                        g[0] = g[0] - 1;
                        updateCountDownText(g[0],h);

                    Intent notificationIntent = new Intent(getApplicationContext() , MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext() , 0 , notificationIntent , 0);

                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setContentTitle("Example Service")
                            .setContentText(text)
                            .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
                            .setContentIntent(pendingIntent)
                            .setSilent(true)
                            .build();


                    startForeground(1,notification);



                }

                @Override
                public void onFinish() {
                    text = "FINISHED";
                    Intent notificationIntent = new Intent(getApplicationContext() , MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext() , 0 , notificationIntent , 0);

                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setContentTitle("Example Service")
                            .setContentText(text)
                            .setSmallIcon(R.drawable.ic_baseline_add_alarm_24)
                            .setContentIntent(pendingIntent)
                            .build();


                    startForeground(1,notification);

                    title.setVisibility(View.VISIBLE);

                }
            }.start();

            return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private void updateCountDownText(long g , long h){

        int hr = (int) (g / 3600);
        int q = (int) (g % 3600);
        int min = (int) (q/60);
        int sec = (int) (q % 60);

        String timeLeftFormatted = String.format(Locale.getDefault() , "%02d:%02d:%02d" , hr , min , sec);

        text = timeLeftFormatted;

    }
}
