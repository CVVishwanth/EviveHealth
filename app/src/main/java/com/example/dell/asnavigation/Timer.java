package com.example.dell.asnavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dell on 8/2/2016.
 */
public class Timer extends ActionBarActivity {
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private Button startB;
    public TextView text;
    private final long startTime = 10 * 1000;
    private final long interval = 1 * 1000;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String phno ="phno";
    public static String scurrentlatitute="scurrentlatitude";
    public static String scurrentlongitude="scurrentlongitude";
    public String numget,getlat,getlong;
    public String defalt="9994288540";
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        startB = (Button) this.findViewById(R.id.button);
      //  text = (TextView) this.findViewById(R.id.timer);
       // text.setText("10");
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        numget=(sharedPreferences).getString(phno, defalt);
        getlat=(sharedPreferences).getString(scurrentlatitute,"11.000000");
        getlong=(sharedPreferences).getString(scurrentlongitude,"77.00000");
      //  Toast.makeText(Timer.this,getlat+"",Toast.LENGTH_LONG).show();
        //countDownTimer.start();
      /*  if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("STOP");
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("RESTART");
        }*/
      //  Toast.makeText(Timer.this,numget+"inside timer",Toast.LENGTH_LONG).show();


        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerHasStarted) {
                    countDownTimer.start();
                    timerHasStarted = true;
                    startB.setText("Cancel");
                } else {
                    countDownTimer.cancel();
                    timerHasStarted = false;
                    startB.setText("RESTART");
                }
            }
        });
       text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime / 1000));
    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            sendSMS(numget,"http://maps.google.com/?q="+getlat+","+getlong);
            text.setText("Alert SMS SENT to "+numget);
        }

    }}
