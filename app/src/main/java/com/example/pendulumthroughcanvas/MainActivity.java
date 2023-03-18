package com.example.pendulumthroughcanvas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {


    PendulumViewSensor pendulumViewSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* PendulumView pendulumView = findViewById(R.id.pendulumView);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                pendulumView.update(0.1f); // update the pendulum state
                new Handler(Looper.getMainLooper()).postDelayed(this, 1000); // schedule the next update
            }
        }, 1000);*/

        pendulumViewSensor = findViewById(R.id.pendulumView);

        pendulumViewSensor.start();

    }
    @Override
    protected void onPause() {
        super.onPause();
        pendulumViewSensor.stop();
    }
}