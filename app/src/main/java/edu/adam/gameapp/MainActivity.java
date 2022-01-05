package edu.adam.gameapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final int Blue = 1;
    private final int Red =3;
    private final int Green=4;
    private final int Purple=2;
    View view1;
    Boolean pPressed = false;


    Button btnBlue,btnRed,btnGreen,btnPurple,btn;
    TextView score;

   public static int sequenceCount=4, scorev=0;
   int n=0;


    int[] gamesSequence= new int[120];
    int arrayIndex=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBlue = findViewById(R.id.btnBlue);
        btnGreen =findViewById(R.id.btnGreen);
        btnPurple = findViewById(R.id.btnPurple);
        btnRed = findViewById(R.id.btnRed);
        score = findViewById(R.id.tvScore);

        score.setText(String.valueOf(scorev));
        view1 = new View(this);
    }
    CountDownTimer ct = new CountDownTimer((1250*sequenceCount)+2500,1500) {
        @Override
        public void onTick(long millisUntilFinished) {
            Toast.makeText(view1.getContext(),String.valueOf(sequenceCount),Toast.LENGTH_SHORT).show();
            rndBTN();
        }

        @Override
        public void onFinish() {
            pPressed=false;
            for(int i =0;i< arrayIndex;i++)
                Log.d("game sequence",String.valueOf(gamesSequence[i]));

            Intent play = new Intent(view1.getContext(),PlayActivity.class);
            play.putExtra("sequenceCount",sequenceCount);
            play.putExtra("seqArray",gamesSequence);

            play.putExtra("score",Integer.valueOf(score.getText().toString()));
            startActivity(play);

        }
    };


    private int getRandom(int maxValue){return ((int)((Math.random()*maxValue)+1));}

    private void btnFlash(Button button)
    {
        btn = button;
        Handler handle = new Handler();
        Runnable r = new Runnable() {

            public void run() {
                btn.setPressed(true);
                btn.invalidate();
                btn.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {

                    public void run() {
                        btn.setPressed(false);
                        btn.invalidate();
                    }
                };
                handler1.postDelayed(r1,600);
            }
        };
        handle.postDelayed(r,600);
    }

    public void doPlay(View view)
    {
        if(!pPressed)
        {
            ct.start();
            pPressed=true;
        }

    }
    private void rndBTN()
    {
        n = getRandom(4);



        switch (n)
        {
            case 1:
                btnFlash(btnBlue);
                gamesSequence[arrayIndex++]=Blue;
                break;
            case 2:
                btnFlash(btnRed);
                gamesSequence[arrayIndex++]=Red;
                break;
            case 3:
                btnFlash(btnGreen);
                gamesSequence[arrayIndex++]=Green;
                break;
            case 4:
                btnFlash(btnPurple);
                gamesSequence[arrayIndex++]=Purple;
                break;
        }
    }



}