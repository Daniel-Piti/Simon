package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent startGame = new Intent(this, Game.class);

        final Button start = findViewById(R.id.GameBtn);
        start.setOnClickListener(v -> startActivity(startGame));

        final Intent scoreBoard = new Intent(this, ScoreBoard.class);

        final Button scoreBoardBtn = findViewById(R.id.scoreBoardBtn);
        scoreBoardBtn.setOnClickListener(v -> startActivity(scoreBoard));

    }
}