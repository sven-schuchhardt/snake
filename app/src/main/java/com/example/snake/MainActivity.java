package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSpielen;
    private ListView listviewHighscore;
    private Button buttonHighscore;
    private static ImageButton buttonH, buttonE;
    private TextView txtSnake;
    ConstraintLayout mConstraintLayout;
    //private ArrayAdapter arrayAdapter;
    //private ArrayList arrayList;
    int[] back_images;
    int design;
    public static MediaPlayer player;
    boolean playing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        player = MediaPlayer.create(MainActivity.this, R.raw.peacefulambiancemusic);
        player.start();
       if(Constants.seekbarVolume == 100){
           player.setVolume(1, 1);
       }else{
           player.setVolume(0.0f, 0.0f);
       }

        //player.setLooping(true);

        setContentView(R.layout.activity_main);

        back_images = new int[]{R.drawable.snake_standard, R.drawable.retro_background, R.drawable.snake_nightmode};

        findAllViewsById();
        loadData();
        applyData();
        String[] tmp = loadNewScores();


        ArrayList<String> arrayHighscoreList = new ArrayList<>();
        arrayHighscoreList.add(tmp[2]);
        arrayHighscoreList.add(tmp[1]);
        arrayHighscoreList.add(tmp[0]);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.layoutlistview, arrayHighscoreList);
        //listviewHighscore.setBackgroundColor(Color.GRAY);
        listviewHighscore.setAdapter(arrayAdapter);
    }

    public void openSpielen() {
        Intent intent = new Intent(this, Spielen.class);
        startActivity(intent);
    }

    public void openHighscore() {
        Intent intent = new Intent(this, Highscore.class);
        startActivity(intent);
    }

    public void openEinstellungen() {
        Intent intent = new Intent(this, Einstellungen.class);
        startActivity(intent);
    }

    public void openHilfe() {
        Intent intent = new Intent(this, Hilfe.class);
        startActivity(intent);
    }

    public void loadData() {
        SharedPreferences design_SharedPrefs = getSharedPreferences("design", MODE_PRIVATE);
        design = design_SharedPrefs.getInt("designMode", -1);
    }

    public void applyData() {
        if (design != -1) {
            changeDesign(design, back_images);
        } else {
            changeDesign(0, back_images);
        }
    }

    public void changeDesign(int d, int[] back_images) {
        Constants.design = d;
        mConstraintLayout.setBackgroundResource(back_images[d]);
        if (d == 0) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_standard_landscape);
            }
        }
        if (d == 1) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_retro_landscape);
            }
            txtSnake.setTextColor(Color.YELLOW);
            buttonSpielen.setTextColor(Color.BLACK);
            buttonHighscore.setTextColor(Color.BLACK);
        }
        if (d == 2) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_nightmode_landscape);
            }
            listviewHighscore.setBackgroundResource(R.drawable.transparten_fully);

            txtSnake.setTextColor(Color.WHITE);
            buttonSpielen.setTextColor(Color.WHITE);
            buttonSpielen.setBackgroundResource(R.drawable.transparten_fully);
            buttonHighscore.setTextColor(Color.WHITE);
            buttonHighscore.setBackgroundResource(R.drawable.transparten_fully);
            buttonE.setBackgroundResource(R.drawable.transparten_fully);
            buttonH.setBackgroundResource(R.drawable.transparten_fully);
            buttonE.setColorFilter(Color.WHITE);
            buttonH.setColorFilter(Color.WHITE);
        }
    }

    public void findAllViewsById() {
        txtSnake = findViewById(R.id.txtSnake);
        buttonSpielen = findViewById(R.id.buttonSpielen);
        buttonSpielen.setOnClickListener(this);
        //buttonSpielen.getBackground().setAlpha(255);
        buttonHighscore = findViewById(R.id.buttonHighscore);
        buttonHighscore.setOnClickListener(this);
        buttonE = findViewById(R.id.buttonEinstellungenImg);
        buttonE.setOnClickListener(this);
        buttonH = findViewById(R.id.buttonHilfeImg);
        buttonH.setOnClickListener(this);
        //arrayList = new ArrayList<String>();
        //arrayAdapter = new ArrayAdapter<String>(this, R.layout.layoutlistview, arrayList);
        listviewHighscore = findViewById(R.id.listViewHighscore);
        listviewHighscore.setBackgroundResource(R.drawable.transparent_bg_bordered);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.relativeLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSpielen:
                openSpielen();
                break;
            case R.id.buttonHighscore:
                openHighscore();
                break;
            case R.id.buttonEinstellungenImg:
                openEinstellungen();
                break;
            case R.id.buttonHilfeImg:
                openHilfe();
                break;
        }
    }

    public String[] loadNewScores() {
        SharedPreferences highscore1_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        String tmp1 = highscore1_sharedPrefs.getString("new1", "leer");

        SharedPreferences highscore2_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        String tmp2 = highscore2_sharedPrefs.getString("new2", "leer");

        SharedPreferences highscore3_sharedPrefs = getSharedPreferences("newScores", MODE_PRIVATE);
        String tmp3 = highscore3_sharedPrefs.getString("new3", "leer");
        String[] tmp = new String[3];
        tmp[0] = tmp1;
        tmp[1] = tmp2;
        tmp[2] = tmp3;

        return tmp;
    }
}
