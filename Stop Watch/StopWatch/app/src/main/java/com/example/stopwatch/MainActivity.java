package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Tracking variables
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restoring state if the screen is rotated
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        // Initializing views
        TextView tvTime = findViewById(R.id.tv_time);
        Button btnStart = findViewById(R.id.btn_start);
        Button btnHold = findViewById(R.id.btn_hold);
        Button btnStop = findViewById(R.id.btn_stop);

        // Start Button Logic
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
            }
        });

        // Hold (Pause) Button Logic
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });

        // Stop (Reset) Button Logic
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;
            }
        });

        // Start the background timer loop
        runTimer(tvTime);
    }

    // Save state when the activity is paused/destroyed (like rotation)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    // Pause the timer if the app goes to the background
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Resume the timer if it was running before the app was paused
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // The core loop handler for counting time
    private void runTimer(final TextView tvTime) {
        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format string to look like 00:00:00
                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                tvTime.setText(time);

                if (running) {
                    seconds++;
                }

                // Run this code again after a 1-second (1000ms) delay
                handler.postDelayed(this, 1000);
            }
        });
    }
}