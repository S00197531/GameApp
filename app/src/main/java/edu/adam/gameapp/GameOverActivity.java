package edu.adam.gameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    TextView score;
    int scoreV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        score = findViewById(R.id.tvScores);
        scoreV = getIntent().getIntExtra("score",-1);
        score.setText(String.valueOf(scoreV));
    }

    public void doRetry(View view)
    {
        Intent start = new Intent(this,MainActivity.class);
        MainActivity.sequenceCount = 4;
        startActivity(start);
    }

    public void doScores(View view)
    {
        Intent hiScore = new Intent(this,HighScoreActivity.class);
        hiScore.putExtra("score",scoreV);
        startActivity(hiScore);
        finish();
    }
}