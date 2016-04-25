package com.projectdolphin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projectdolphin.R;
import com.projectdolphin.data.database.DBAccessHelper;
import com.projectdolphin.data.model.Assignment;

public class TimerActivity extends AppCompatActivity {
    public static final String ACTIVITY_TITLE = "Activity_Title";
    public static final String ACTIVITY_ID = "Activity_Id";
    public static final String START_TIME = "Start_Time";

    private TextView timerView;

    private Button startButton;
    private Button stopButton;

    private long startTime;
    private long elapsedTime;

    private Handler mHandler;
    private Runnable startTimer;

    private DBAccessHelper db;
    private long assignmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        db = DBAccessHelper.getInstance(this);

        timerView = (TextView) findViewById(R.id.timerTextView);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        mHandler = new Handler();
        startTimer = new Runnable() {
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;
                updateTimer(elapsedTime);
                mHandler.postDelayed(this, 100);
            }
        };

        Intent intent = getIntent();

        Log.i("BCR", intent.getStringExtra(ACTIVITY_TITLE));

//        assignmentId = Long.parseLong(intent.getStringExtra(ACTIVITY_ID));
//        Assignment assignment = db.getAssignmentByID(assignmentId);
//        elapsedTime = assignment.getTimeSpentMillis();

        startTime = intent.getLongExtra(START_TIME, -1);
//        startTime = System.currentTimeMillis()-(9900*60*60);

        if(startTime == -1) {
            startTime = System.currentTimeMillis();
        }
        else {
            showStopButton();
            mHandler.removeCallbacks(startTimer);
            mHandler.postDelayed(startTimer, 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    public void onResetTimer(View view) {
        timerView.setText(R.string.timer_default_text);

        startTime = System.currentTimeMillis();
    }

    public void onStartTimer(View view) {
        showStopButton();

        startTime = System.currentTimeMillis();
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    public void onStopTimer(View view) {
        showStartButton();

        startTime = System.currentTimeMillis() - elapsedTime;
        mHandler.removeCallbacks(startTimer);
    }

    private void updateTimer(float time) {
        long secs = (long) (time / 1000);
        long mins = secs / 60;
        long hrs = mins / 60;

        secs = secs % 60;
        String seconds = String.valueOf(secs);
        if (secs == 0)
            seconds = "00";
        if (secs < 10 && secs > 0)
            seconds = "0" + seconds;

        mins = mins % 60;
        String minutes = String.valueOf(mins);
        if (mins == 0)
            minutes = "00";
        if (mins < 10 && mins > 0)
            minutes = "0" + minutes;

        String hours = String.valueOf(hrs);
        if (hrs == 0)
            hours = "00";
        if (hrs < 10 && hrs > 0)
            hours = "0" + hours;

        String updatedTime = hours + ":" + minutes + ":" + seconds;
        timerView.setText(updatedTime);
    }

    private void showStartButton() {
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
    }

    private void showStopButton() {
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
    }
}
