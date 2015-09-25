package com.example.haria.uidesign;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity extends Activity {
    // initializing variables
    Button startButton, resetButton;
    TextView time;
    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int hours = 0;
    int milliseconds = 0;
    Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // identifying widgets
        startButton = (Button) findViewById(R.id.start);
        resetButton = (Button) findViewById(R.id.reset);
        time = (TextView) findViewById(R.id.timeDisplay);

        // setting onClickListener for start button
        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (t == 1) {
                    startButton.setText("Pause");
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                } else {
                    startButton.setText("Start");
                    time.setTextColor(Color.BLUE);
                    timeSwapBuff+=timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    t = 1;
                }
            }
        });

        // set onClickListener for reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;
                t = 1;
                secs = 0;
                mins = 0;
                milliseconds = 0;
                startButton.setText("Start");
                handler.removeCallbacks(updateTimer);
                time.setText("00:00:00");
            }
        });
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public Runnable updateTimer = new Runnable() {
       public void run() {
           timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
           updatedTime = timeSwapBuff + timeInMilliseconds;
           secs = (int)(updatedTime / 1000);
           mins = secs / 60;
           hours = mins / 60;
           secs = secs % 60;
           milliseconds = (int)(updatedTime % 1000);
           time.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", mins)
           + ":" + String.format("%02d", secs));
           time.setTextColor(Color.RED);
           handler.postDelayed(this, 0);
       }
    };
}
