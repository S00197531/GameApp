package edu.adam.gameapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity implements SensorEventListener
{

    private final double fullPos= 5.0;
    private final double fullNeg = -5.0;
    int scoreV;
    boolean highlimit = false;

    TextView scores;
    View view;
    int sequenceCount=4,n=0;
    int[] gamesSequence= new int[120];
    Button btnblue,btnpurple,btngreen,btnred;
    private SensorManager msesnorManager;
    private Sensor mSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnblue = findViewById(R.id.btnBlue);
        btnpurple = findViewById(R.id.btnPurple);
        btngreen = findViewById(R.id.btnGreen);
        btnred = findViewById(R.id.btnRed);
        scores = findViewById(R.id.tvScore);
        msesnorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = msesnorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        scoreV = getIntent().getIntExtra("score",-1);

        sequenceCount = getIntent().getIntExtra("sequenceCount",-1);
        gamesSequence = getIntent().getIntArrayExtra("seqArray");

        view = new View(this);
    }
    protected void onResume()
    {
        super.onResume();
        msesnorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause()
    {
        super.onPause();
        msesnorManager.unregisterListener(this);
    }

    public void doBlue(View view)
    {
        Input(1);
    }

    public void doPurple(View view)
    {
        Input(2);
    }
    public void doRed(View view)
    {
        Input(3);
    }
    public void doGreen(View view)
    {
        Input(4);
    }



    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(z>= fullPos && highlimit==false)
        {
            highlimit =true;
            btnblue.setPressed(true);
            doBlue(view);
        }
        else if(z < fullPos)
        {
            btnblue.setPressed(false);
        }
        if(z <= fullNeg && highlimit==false)
        {
            highlimit = true;
            btnred.setPressed(true);
            doRed(view);
        }
        else if(z > fullNeg)
        {
            btnred.setPressed(false);
        }
        if(y > fullPos && highlimit == false)
        {
            highlimit = true;
            btnpurple.setPressed(true);
            doPurple(view);
        }
        else if(y < fullPos)
        {
            btnpurple.setPressed(false);
        }
        if(y <= fullNeg && highlimit == false)
        {
            highlimit = true;
            btngreen.setPressed(true);
            doGreen(view);
        }
        else if(y > fullNeg)
        {
            btngreen.setPressed(false);
        }
        if(z < fullPos && z > fullNeg && y < fullPos && y > fullNeg )
        {
            highlimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void Input(int value)
    {
        if(n+1 < sequenceCount)
        {
            if(gamesSequence[n]==value)
            {
                scoreV++;
                scores.setText(String.valueOf(scoreV));
                n++;
            }
            else
            {
                Intent gameOver = new Intent(view.getContext(),GameOverActivity.class);
                gameOver.putExtra("score",scoreV);
                startActivity(gameOver);
            }
        }
        else if(n+1 >=sequenceCount)
        {
            if(gamesSequence[n]==value)
            {
                scoreV++;
                scores.setText(String.valueOf(scoreV));
                Intent start = new Intent(view.getContext(),MainActivity.class);

                MainActivity.scorev = scoreV;
                MainActivity.sequenceCount = sequenceCount+2;
                startActivity(start);
                finish();
            }
            else
            {
                Intent gameOver = new Intent(view.getContext(),GameOverActivity.class);
                gameOver.putExtra("score",scoreV);
                startActivity(gameOver);
            }
        }
    }

}