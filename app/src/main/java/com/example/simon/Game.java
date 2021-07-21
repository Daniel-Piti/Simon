package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game extends AppCompatActivity {

    private TextView levelNumber;
    private TextView mainTitle;

    private final Handler handler = new Handler();

    private final MediaPlayer[] players = new MediaPlayer[4];

    private final int MS_OF_COLOR_CHANGE = 300;
    private int level;
    private int turn;

    private Button start;

    private Button yellow;  //0
    private Button blue  ;  //1
    private Button green ;  //2
    private Button red   ;  //3

    private List<Integer> levelList;
    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        levelList = new ArrayList<>();
        rand = new Random();

        yellow = findViewById(R.id.yellowBtn);   // 0
        blue   = findViewById(R.id.blueBtn);     // 1
        green  = findViewById(R.id.greenBtn);    // 2
        red    = findViewById(R.id.redBtn);      // 3

        levelNumber = findViewById(R.id.levelNumber);
        mainTitle = findViewById(R.id.gameTitle);
        start = findViewById(R.id.startBtn);

        players[0] = MediaPlayer.create(this,R.raw.r1);
        players[1] = MediaPlayer.create(this,R.raw.r2);
        players[2] = MediaPlayer.create(this,R.raw.r3);
        players[3] = MediaPlayer.create(this,R.raw.r4);

        initValues();

        setClickHandlers();

        // RESET NAMES
        //prefsHandle();
    }


    private void prefsHandle(){
        String SHARED_PREFS;
        String DATA;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        SHARED_PREFS = "SHARED_PREFS";
        DATA = "TOP";
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String[] n = {"Nathan-10-0", "Yoav-5-1", "Dan-2-2", "Ez-7-3", "Tom-9-4"};
        Set<String> set = new HashSet<>(Arrays.asList(n));

        editor.putStringSet(DATA, set);

        editor.putInt("ID", 5);

        editor.apply();
    }

    private void initValues() {
        level = 1;
        turn = 0;

        yellow.setEnabled(false);
        blue.setEnabled(false);
        red.setEnabled(false);
        green.setEnabled(false);

        levelList.clear();
    }

    private void gameOver(){
        final Intent gameOverActicity = new Intent(this, GameOver.class);
        gameOverActicity.putExtra("score", level);
        initValues();
        start.setEnabled(true);
        startActivity(gameOverActicity);
    }

    private void runList() {
        turn = 0;
        runListRec(0, levelList.size());
        handler.postDelayed(() -> {
            yellow.setEnabled(true);
            blue.setEnabled(true);
            red.setEnabled(true);
            green.setEnabled(true);
        }, (int) (1.3 * (MS_OF_COLOR_CHANGE) * level) + MS_OF_COLOR_CHANGE);
    }

    private void runListRec(int i, int n) {            //TODO switch case
        handler.postDelayed(() -> {
            if (i < n) {
                int color = levelList.get(i);
                switch (color){
                    case 0:
                        animateBtn(yellow);
                        break;
                    case 1:
                        animateBtn(blue);
                        break;
                    case 2:
                        animateBtn(green);
                        break;
                    default:
                        animateBtn(red);
                }
                players[color].seekTo(0);
                players[color].start();
                runListRec(i + 1, n);
            }
        }, (int) (MS_OF_COLOR_CHANGE * 1.3));
    }

    private void start() {
        mainTitle.setText(R.string.app_name);
        levelNumber.setText(String.valueOf(level));
        levelList.add(rand.nextInt(4));
        runList();
        start.setEnabled(false);
    }

    private void checkIfPressedTrue(int t) {
        if (levelList.get(turn++) != t) {
            gameOver();
            return;
        }
        if (turn == levelList.size()) {
            levelList.add(rand.nextInt(4));
            turn = 0;
            level++;
            levelNumber.setText(String.valueOf(level));
            yellow.setEnabled(false);
            blue.setEnabled(false);
            red.setEnabled(false);
            green.setEnabled(false);
            handler.postDelayed(() -> runList(), MS_OF_COLOR_CHANGE);
        }
    }

    private void animateBtn(final Button button){
        button.setBackgroundColor(Color.MAGENTA);
        int colorId = button.getDrawingCacheBackgroundColor();
        handler.postDelayed(() -> button.setBackgroundColor(colorId), MS_OF_COLOR_CHANGE);
    }

    private void setClickHandlers(){
        start.setOnClickListener(v -> start());
        // 0
        yellow.setOnClickListener(v -> checkIfPressedTrue(0));
        // 1
        blue.setOnClickListener(v -> checkIfPressedTrue(1));
        // 2
        green.setOnClickListener(v -> checkIfPressedTrue(2));
        // 3
        red.setOnClickListener(v -> checkIfPressedTrue(3));
    }
}