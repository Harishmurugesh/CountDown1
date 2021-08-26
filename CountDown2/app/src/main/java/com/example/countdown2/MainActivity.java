package com.example.countdown2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.countdown2.ExampleService.g;
import static com.example.countdown2.ExampleService.h;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    double prog1;

    TextView mTextViewCountDown ;
    static TextView title;
    Button mButtonStartPause;
    Button mButtonReset;
    Button EnterTime;
    ImageView front , back;

    Boolean mTimerRunning = false;
    CountDownTimer mCountDownTimer;
    long mTimeLeftInSec;
    long start;
    Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress_bar);
        mButtonReset = findViewById(R.id.reset);
        mButtonStartPause = findViewById(R.id.startpause);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        title = findViewById(R.id.title);
        EnterTime = findViewById(R.id.submit);
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setEnabled(false);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
        front.setEnabled(false);
        front.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        back.setEnabled(false);
        title.setVisibility(View.INVISIBLE);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout(MATCH_PARENT,WRAP_CONTENT);
        dialog.hide();


        ImageView hrup = (ImageView) dialog.findViewById(R.id.hrup);
        ImageView minup = (ImageView) dialog.findViewById(R.id.minup);
        ImageView secup = (ImageView) dialog.findViewById(R.id.secup);
        ImageView hrdown = (ImageView) dialog.findViewById(R.id.hrdown);
        ImageView mindown = (ImageView) dialog.findViewById(R.id.mindown);
        ImageView secdown = (ImageView) dialog.findViewById(R.id.secdown);

        TextView hhedit = (TextView) dialog.findViewById(R.id.hhedit);
        TextView mmedit = (TextView) dialog.findViewById(R.id.mmedit);
        TextView ssedit = (TextView) dialog.findViewById(R.id.ssedit);

        TextView submitdialog = (TextView) dialog.findViewById(R.id.submitdialogue);



        Intent serviceIntent = new Intent(this , ExampleService.class);
        stopService(serviceIntent);


        if(g[0]!=0){
            mTimeLeftInSec = g[0];
            start = h;
            mTimerRunning = true;
            startTimer();
            mButtonStartPause.performClick();
            EnterTime.setEnabled(false);
            mButtonStartPause.setEnabled(true);
            mButtonStartPause.setVisibility(View.VISIBLE);

        }



        EnterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });



        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                front.setEnabled(true);
                back.setEnabled(true);
                EnterTime.setEnabled(false);


                if(mTimerRunning){
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });



        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });



        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimeLeftInSec >= 10)
                    mTimeLeftInSec = mTimeLeftInSec - 10;
                else
                    mTimeLeftInSec = 0;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((start - mTimeLeftInSec) >= 10)
                    mTimeLeftInSec = mTimeLeftInSec + 10;
                else
                    mTimeLeftInSec = start;
            }
        });

        updateCountDownText();



        hrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p = Integer.parseInt(hhedit.getText().toString());
                p = p + 1;
                if((String.valueOf(p)).length() == 1){
                    hhedit.setText("0"+p);
                }
                else{
                    hhedit.setText(String.valueOf(p));
                }

            }
        });

        minup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(mmedit.getText().toString()) < 59) {
                    int p = Integer.parseInt(mmedit.getText().toString());
                    p = p + 1;
                    if((String.valueOf(p)).length() == 1){
                        mmedit.setText("0"+p);
                    }
                    else{
                        mmedit.setText(String.valueOf(p));
                    }
                }
                else{
                    int p = 0;
                    mmedit.setText("0"+p);
                }
            }
        });

        secup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(ssedit.getText().toString()) < 59) {
                    int p = Integer.parseInt(ssedit.getText().toString());
                    p = p + 1;
                    if((String.valueOf(p)).length() == 1){
                        ssedit.setText("0"+p);
                    }
                    else{
                        ssedit.setText(String.valueOf(p));
                    }
                }
                else{
                    int p = 0;
                    ssedit.setText("0"+p);
                }
            }
        });


        hrdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(hhedit.getText().toString())>0){
                    int p = Integer.parseInt(hhedit.getText().toString());
                    p = p - 1;
                    if((String.valueOf(p)).length() == 1){
                        hhedit.setText("0"+p);
                    }
                    else{
                        hhedit.setText(String.valueOf(p));
                    }
                }
            }
        });


        mindown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(mmedit.getText().toString()) > 0){
                    int p = Integer.parseInt(mmedit.getText().toString());
                    p = p - 1;
                    if((String.valueOf(p)).length() == 1){
                        mmedit.setText("0"+p);
                    }
                    else{
                        mmedit.setText(String.valueOf(p));
                    }
                }
                else{
                    int p = 59;
                    mmedit.setText(String.valueOf(p));
                }
            }
        });

        secdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(ssedit.getText().toString()) > 0){
                    int p = Integer.parseInt(ssedit.getText().toString());
                    p = p - 1;
                    if((String.valueOf(p)).length() == 1){
                        ssedit.setText("0"+p);
                    }
                    else{
                        ssedit.setText(String.valueOf(p));
                    }
                }
                else{
                    int p = 59;
                    ssedit.setText(String.valueOf(p));
                }
            }
        });



        submitdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start = (Integer.parseInt(hhedit.getText().toString()) * 3600) + (Integer.parseInt(mmedit.getText().toString()) * 60) + Integer.parseInt(ssedit.getText().toString()) ;
                mTimeLeftInSec = start;
                mButtonStartPause.setEnabled(true);
                mButtonStartPause.setVisibility(View.VISIBLE);
                title.setVisibility(View.INVISIBLE);
                mButtonStartPause.setText("start");
                mTimerRunning = false;
                updateCountDownText();
                dialog.hide();

            }
        });


    }




    private void startTimer(){

        mCountDownTimer = new CountDownTimer((mTimeLeftInSec*1000) , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mTimeLeftInSec > 0)
                    mTimeLeftInSec = mTimeLeftInSec - 1;
                front.setEnabled(true);
                front.setVisibility(View.VISIBLE);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                updateCountDownText();
            }

            @Override
            public void onFinish() {

                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                title.setVisibility(View.VISIBLE);
                mButtonReset.setEnabled(true);
                EnterTime.setEnabled(true);
                mButtonStartPause.setVisibility(View.INVISIBLE);
                front.setEnabled(false);
                front.setVisibility(View.INVISIBLE);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);

    }


    private void updateCountDownText(){

        int hr = (int) (mTimeLeftInSec / 3600);
        int q = (int) (mTimeLeftInSec % 3600);
        int min = (int) (q/60);
        int sec = (int) (q % 60);
        prog1 = ((double)mTimeLeftInSec/start) * 100;

        String timeLeftFormatted = String.format(Locale.getDefault() , "%02d:%02d:%02d" , hr , min , sec);

        mTextViewCountDown.setText(timeLeftFormatted);
        progressBar.setProgress((int) prog1);

    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Resume");
        mButtonReset.setVisibility(View.VISIBLE);
        back.setEnabled(false);
        front.setEnabled(false);
        front.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
    }

    private void resetTimer(){

        mTimeLeftInSec = start;
        updateCountDownText();
        mButtonStartPause.setText("start");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        title.setVisibility(View.INVISIBLE);
        EnterTime.setEnabled(true);


    }


    @Override
    protected void onStop() {
        super.onStop();

        if(mTimerRunning) {

            long g = mTimeLeftInSec;
            long h = start;

            Log.i("Tag", "stopped");

            Intent serviceIntent = new Intent(this, ExampleService.class);
            serviceIntent.putExtra("start", h);
            serviceIntent.putExtra("mtimeleftinsec", g);

            startService(serviceIntent);
        }


    }
}