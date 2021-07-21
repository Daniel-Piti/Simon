package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameOver extends AppCompatActivity {
    private TextView scoreTV;
    private Button addBtn;
    private Button back;

    // INIT PREFS
    private String SHARED_PREFS;
    private String DATA;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Set<String> topSet;
    private List<String> toplist;

    private Dialog dialog;

    private void addToScoreBoard(int score){
        topSet = sharedPreferences.getStringSet(DATA,null);
        toplist = new ArrayList<>(topSet);

        int id = sharedPreferences.getInt("ID", 0);

        EditText nameEditText = findViewById(R.id.editTextTextPersonName);
        String name = nameEditText.getText().toString();

        String combine = name + "-" + score + "-" + id;
        toplist.add(combine);
        topSet = new HashSet<>(toplist);
        editor.putStringSet(DATA,topSet);
        editor.putInt("ID", id+1);
        editor.apply();
    }

    private void checkIfScoreIsEnough(int score){
        if(score > getPlaceScore())
            addToScoreBoard(score);
    }

    private int getPlaceScore() {
        Set<String> top = sharedPreferences.getStringSet(DATA,null);
        List<String> topList = new ArrayList<>(top);

        toplist.sort((s1, s2) -> {
            Integer a = Integer.parseInt(s1.split("-")[1]);
            Integer b = Integer.parseInt(s2.split("-")[1]);
            return a.compareTo(b);
        });

        return Integer.parseInt(toplist.get(0).split("-")[1]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scoreTV = findViewById(R.id.scoreNumTitle);

        int score = getIntent().getIntExtra("score",-1);

        scoreTV.setText(String.valueOf(score));

        addBtn = findViewById(R.id.addToScoreBtn);

        SHARED_PREFS = "SHARED_PREFS";
        DATA = "TOP";
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dialog = new Dialog(GameOver.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        addBtn.setOnClickListener(v -> {
            EditText nameET = findViewById(R.id.editTextTextPersonName);
            String name = nameET.getText().toString();
            if(name.isEmpty()) {
                Toast.makeText(this, "Name cant be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            addToScoreBoard(score);
            dialog.show();
        });

        dialog.setOnDismissListener(dialog -> finish());
    }
}