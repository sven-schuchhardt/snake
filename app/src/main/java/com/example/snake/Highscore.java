package com.example.snake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Highscore extends AppCompatActivity {
    ImageButton buttonZurueck;
    ListView listView;
    int design;
    int[] back_images;
    ConstraintLayout mConstraintLayout;
    String[] highscoreString;
    int j = 0;
    Map<Integer,String> highscores = new HashMap<Integer,String>();
    Map<String,Integer> high = new HashMap<String,Integer>();
    TextView title;
    Set<String> highscore = new HashSet<String>();
    Map<Integer,String> gV = new HashMap<Integer,String>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        highscores.put(500000,"Harald");
        highscores.put(400000,"Sven");
        highscores.put(12000,"Martin");
        highscores.put(300000,"Tobias");
        highscores.put(100000,"Andreu");
        highscores.put(200000,"Test");


        high.putAll(loadHighscores());

        final Map<String,Integer> sortedMap;
        sortedMap = high.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        highscoreString = new String[sortedMap.size()];

        String[] key = sortedMap.keySet().toArray(new String[0]);

        Integer[] value = sortedMap.values().toArray(new Integer[0]);

        if(sortedMap.size()>0){
            for( int i = sortedMap.size()-1 ; i >= 0 ; i--){
                if(i<0){
                    i=0;
                }
                highscoreString[j] = key[i].toString() + "     " +value[i];
                j++;
            }
        }

        ArrayAdapter<String> itemAdapter;
        itemAdapter = new ArrayAdapter<String>(this, R.layout.layoutlistview,highscoreString);
        back_images  = new int[]{R.drawable.snake_standard, R.drawable.retro_background, R.drawable.snake_nightmode};

        findAllViewsById();
        loadData();
        applyData();



        listView.setAdapter(itemAdapter);
        buttonZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
    public void back(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void applyData(){
        if(design != -1) {
            changeDesign(design,back_images);
        }else{
            changeDesign(0,back_images);
        }
    }
    public void changeDesign(int d , int[] back_images){

        mConstraintLayout.setBackgroundResource(back_images[d]);
        if(d == 0){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_standard_landscape);
            }
        }
        if(d == 1){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_retro_landscape);
            }
            title.setTextColor(Color.YELLOW);
            buttonZurueck.setColorFilter(Color.WHITE);
        }
        if(d == 2){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_nightmode_landscape);
            }
            title.setTextColor(Color.WHITE);
            buttonZurueck.setColorFilter(Color.WHITE);
        }
    }
    public void loadData(){
        SharedPreferences design_SharedPrefs = getSharedPreferences("design",MODE_PRIVATE);
        design = design_SharedPrefs.getInt("designMode",-1);
    }

    public void findAllViewsById(){
        buttonZurueck = findViewById(R.id.buttonZurueckHighscore);
        buttonZurueck.setBackgroundResource(0);
        listView = (ListView) findViewById(R.id.listViewHighscore);
        listView.setBackgroundResource(R.drawable.transparent_bg_bordered);
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.relativeLayoutHighscore);
        title =  findViewById(R.id.textView2);

    }

    public Map<String, Integer> loadHighscores() {
        SharedPreferences highscore_sharedPrefs = getSharedPreferences("highscores",MODE_PRIVATE);
        Set<String> tmp = highscore_sharedPrefs.getStringSet("scores", null);

        return setToMap(tmp);
    }

    public void saveHighscores(Map<Integer,String> tmp) throws IOException {

        Map<Integer,String> temp = new HashMap<Integer,String>();
        temp.putAll(tmp);
        Set<String> scores = new HashSet<String>();
        int i = 0;
        for(Map.Entry<Integer,String> entry : temp.entrySet()){
            scores.add(entry.getKey() + ":" + entry.getValue());
            i++;
        }

        SharedPreferences highscore_sharedPrefs = getSharedPreferences("highscores", MODE_PRIVATE);
        SharedPreferences.Editor editor = highscore_sharedPrefs.edit();
        editor.putStringSet("scores",scores);
        editor.apply();
    }

    public void addScore(int score,String name) throws IOException {

        String value = score + ":" + name;

        SharedPreferences highscore_sharedPrefs = getSharedPreferences("highscores",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = highscore_sharedPrefs.edit();
        editor.putString("newScore",value);
        editor.apply();

    }



    public Map<String,Integer> setToMap(Set<String> tmp){
        Map<String,Integer> ret = new HashMap<String,Integer>();
        if(tmp != null) {
            for (String value : tmp) {
                String[] comp = value.split(":");
                String key = comp[0];
                String val = comp[1];
                ret.put(key, Integer.valueOf(val));
            }
            return ret;
        }
        return ret;
    }




}