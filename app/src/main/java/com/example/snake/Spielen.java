package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ContentInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Spielen extends AppCompatActivity {

    public static TextView txt_score, txt_time, txt_highscore,txt_name;
    public static RelativeLayout rl_gameOver;
    public static ImageButton buttonStart, buttonStartAfterPause;
    public static GridLayout gridLayout;
    public static ImageButton buttonUp, buttonDown, buttonLeft, buttonRight ;
    public static ImageButton buttonPause;
    public static ImageButton buttonReset;
    public static ImageButton buttonBeendet, buttonEinstellung;
    public static RelativeLayout rl_pause_mute;
    public static ListView listViewAchievements;
    public static EditText editName;
    public ImageButton up,down,left,right, mute, pause;
    private GameView gameView;
    RelativeLayout mConstraintLayout;
    int[] back_images;
    int design;
    public static MediaPlayer mediaItem2;
    public static MediaPlayer mediaItem;
    Set<String> HighscoreSet = new HashSet<>();

    private Timer timer = new Timer();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mediaItem = MediaPlayer.create(Spielen.this, R.raw.blowingoutcandlewav);
        mediaItem.setVolume(Constants.seekbarSound, Constants.seekbarSound);
        mediaItem2 = MediaPlayer.create(Spielen.this, R.raw.warningsound);
        mediaItem2.setVolume(Constants.seekbarSound, Constants.seekbarSound);

        DisplayMetrics d = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(d);
        Constants.SCREEN_WIDTH = d.widthPixels;
        Constants.SCREEN_HEIGHT = d.heightPixels;

        HighscoreSet.add("Harald 50000000 Pkt");
        HighscoreSet.add("TestPerson1 1034 Pkt");
        HighscoreSet.add("TestPerson2 507 Pkt");

        back_images  = new int[]{R.drawable.snake_standard, R.drawable.retro_background, R.drawable.snake_nightmode};


        int orientation = getResources().getConfiguration().orientation;
        if(orientation == 1){
            Constants.h = 14;
            Constants.w = 12;
        }else{
            Constants.landscape = true;
            Constants.h = 8;
            Constants.w = 20;
        }


        if(Constants.boolLeftHanded){
            setContentView(R.layout.activity_spielen_left);
        }else {
            setContentView(R.layout.activity_spielen);
        }
        findAllViewsById();
        loadData();
        applyData();

        ArrayList<String> arrayAchievementsList = new ArrayList<>();
        arrayAchievementsList.add("Start a game - Done");
        arrayAchievementsList.add("earn 500.000 Points in one Session - Current Points before game: " + Constants.overallScore);
        arrayAchievementsList.add("collect 30 Items - " + Constants.overallItemCounter + " Items");
        listViewAchievements = findViewById(R.id.listViewAchievements);
        listViewAchievements.setBackgroundResource(R.drawable.transparent_bg_bordered);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.layoutlistview,arrayAchievementsList);
        listViewAchievements.setAdapter(arrayAdapter);



        up.setBackgroundResource(0);
        down.setBackgroundResource(0);
        left.setBackgroundResource(0);
        right.setBackgroundResource(0);
        pause.setBackgroundResource(0);
        buttonStart.setBackgroundResource(0);
        buttonEinstellung.setBackgroundResource(0);
        buttonReset.setBackgroundResource(R.drawable.transparent_bg_bordered);
        buttonBeendet.setBackgroundResource(R.drawable.transparent_bg_bordered);
        buttonStartAfterPause.setBackgroundResource(0);
        buttonStartAfterPause.setVisibility(View.INVISIBLE);



        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setStart(true);
                buttonStart.setVisibility(View.INVISIBLE);
            }
        });
        buttonStartAfterPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.noPause = true;
                buttonStartAfterPause.setVisibility(View.INVISIBLE);
            }
        });
        rl_gameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setVisibility(View.VISIBLE);
                rl_gameOver.setVisibility(View.INVISIBLE);
                gameView.setStart(false);
                //gameView.reset();
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.name = editName.getText().toString();
                Constants.newScore = loadHighscores();
                Constants.newScore.put(Constants.name, Constants.score);
                saveNewScore();
                try {
                    Constants.newScore = loadHighscores();
                    saveHighscores(Constants.newScore);
                    gameView.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openSpielen();
            }
        });
        buttonBeendet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.leftVol = 0.0f;
                Constants.rightVol = 0.0f;
                MainActivity.player.setVolume(Constants.leftVol,Constants.rightVol);
                Constants.name = editName.getText().toString();
                Constants.newScore = loadHighscores();
                //Constants.newScore.put(Constants.score,txt_name.getText().toString());
                Constants.newScore.put(Constants.name, Constants.score);
                saveNewScore();
                try {
                    saveHighscores(Constants.newScore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openMain();
            }
        });
        buttonEinstellung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.savveTime = GameView.timeSeconds;
                Constants.snake = GameView.snake;
                back();
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.up = true;
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.down = true;
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.left = true;
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.right = true;
            }
        });



    }
    public void back(){
        Constants.gameToSettings = true;
        Intent intent = new Intent(this,Einstellungen.class);
        startActivity(intent);
    }
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openSpielen(){
        Intent intent = new Intent(this, Spielen.class);
        startActivity(intent);
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
            txt_score.setTextColor(Color.WHITE);
            txt_time.setTextColor(Color.WHITE);
            txt_highscore.setTextColor(Color.WHITE);
            up.setColorFilter(Color.WHITE);
            right.setColorFilter(Color.WHITE);
            down.setColorFilter(Color.WHITE);
            left.setColorFilter(Color.WHITE);
            pause.setColorFilter(Color.WHITE);
            buttonEinstellung.setColorFilter(Color.WHITE);

        }
        if(d == 2){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_nightmode_landscape);
            }
            txt_score.setTextColor(Color.WHITE);
            txt_time.setTextColor(Color.WHITE);
            txt_highscore.setTextColor(Color.WHITE);
            up.setColorFilter(Color.WHITE);
            right.setColorFilter(Color.WHITE);
            down.setColorFilter(Color.WHITE);
            left.setColorFilter(Color.WHITE);
            pause.setColorFilter(Color.WHITE);
            buttonEinstellung.setColorFilter(Color.WHITE);

        }
    }
    public void loadData(){
        SharedPreferences design_SharedPrefs = getSharedPreferences("design",MODE_PRIVATE);
        design = design_SharedPrefs.getInt("designMode",-1);
    }

    public void applyData(){
        if(design != -1) {
            changeDesign(design,back_images);
        }else{
            changeDesign(0,back_images);
        }
    }

    public void findAllViewsById(){
        mConstraintLayout = findViewById(R.id.relativeLayoutSpielen);
        up = findViewById(R.id.buttonUp);
        down = findViewById(R.id.buttonDown);
        left = findViewById(R.id.buttonLeft);
        right = findViewById(R.id.buttonRight);
        buttonEinstellung = findViewById(R.id.buttonEinstellungen);
        pause = findViewById(R.id.buttonPause);
        buttonPause = findViewById(R.id.buttonPause);
        buttonBeendet = findViewById(R.id.buttonBeendet);
        buttonReset = findViewById(R.id.buttonReset);
        txt_score = findViewById(R.id.txtScore);
        txt_time = findViewById(R.id.txtTime);
        txt_highscore = findViewById(R.id.txthighScore);
        txt_name = findViewById(R.id.textView2);
        rl_gameOver = findViewById(R.id.rl_game_over);
        rl_pause_mute = findViewById(R.id.rl_pause_mute);
        gridLayout = findViewById(R.id.GridLayout);
        gameView = findViewById(R.id.GameView);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStartAfterPause = findViewById(R.id.buttonStartAfterPause);
        editName = findViewById(R.id.editPersonName);

    }

    public boolean pause(){
        if(Constants.noPause = true){
            buttonStartAfterPause.setVisibility(View.VISIBLE);
            Constants.noPause = false;
        }
        return Constants.noPause;
    }
    public Map<String,Integer> setToMap(Set<String> tmp){
        Map<String, Integer> ret = new HashMap<String, Integer>();
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
    public void saveHighscores(Map<String,Integer> tmp) throws IOException {

        Map<String,Integer> temp = new HashMap<String,Integer>();
        temp.putAll(tmp);
        Set<String> scores = new HashSet<String>();
        int i = 0;
        for(Map.Entry<String,Integer> entry : temp.entrySet()){
            scores.add(entry.getKey() + ":" + entry.getValue());
            i++;
        }

        SharedPreferences highscore_sharedPrefs = getSharedPreferences("highscores", MODE_PRIVATE);
        SharedPreferences.Editor editor = highscore_sharedPrefs.edit();
        editor.putStringSet("scores",scores);
        editor.apply();
    }
    public Map<String, Integer> loadHighscores() {
        SharedPreferences highscore_sharedPrefs = getSharedPreferences("highscores",MODE_PRIVATE);
        Set<String> tmp = highscore_sharedPrefs.getStringSet("scores", null);
        return setToMap(tmp);
    }

    public String[] loadNewScores(){
        SharedPreferences highscore1_sharedPrefs = getSharedPreferences("newScores",MODE_PRIVATE);
        String tmp1 = highscore1_sharedPrefs.getString("new1", "leer");

        SharedPreferences highscore2_sharedPrefs = getSharedPreferences("newScores",MODE_PRIVATE);
        String tmp2 = highscore2_sharedPrefs.getString("new2", "leer");

        SharedPreferences highscore3_sharedPrefs = getSharedPreferences("newScores",MODE_PRIVATE);
        String tmp3 = highscore3_sharedPrefs.getString("new3", "leer");
        String[] tmp = new String[3];
        tmp[0]=tmp1;
        tmp[1]=tmp2;
        tmp[2]=tmp3;

        return tmp;
    }

    public void saveNewScore(){

        String[] tmp = new String[3];

        tmp = loadNewScores();

        String tmp1 = tmp[1];
        String tmp2 = tmp[2];
        String tmp3 = Constants.name.toString() + " " + txt_score.getText().toString();

        SharedPreferences highscore1_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = highscore1_sharedPrefs.edit();
        editor1.putString("new1",tmp1);
        editor1.apply();

        SharedPreferences highscore2_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = highscore2_sharedPrefs.edit();
        editor2.putString("new2",tmp2);
        editor2.apply();

        SharedPreferences highscore3_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = highscore3_sharedPrefs.edit();
        editor3.putString("new3",tmp3);
        editor3.apply();
    }
}
