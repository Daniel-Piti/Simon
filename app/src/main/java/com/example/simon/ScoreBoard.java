package com.example.simon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ScoreBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);


        String SHARED_PREFS = "SHARED_PREFS";
        String DATA = "TOP";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Set<String> topSet = sharedPreferences.getStringSet(DATA,null);
        List<String> topList = new ArrayList<>(topSet);

        topList.sort((s1, s2) -> {
            Integer a = Integer.parseInt(s1.split("-")[1]);
            Integer b = Integer.parseInt(s2.split("-")[1]);
            return a.compareTo(b);
        });
        Collections.reverse(topList);

        ArrayList<String> arrayList =  new ArrayList<>();

        for (String s: topList) {
            String[] temp = s.split("-");
            arrayList.add(temp[0] + "  " + temp[1]);
        }

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList));
    }
}