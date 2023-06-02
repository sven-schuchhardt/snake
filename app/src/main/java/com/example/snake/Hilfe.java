package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Hilfe extends AppCompatActivity {

    ImageButton buttonZurueck;
    ListView listView;
    ConstraintLayout mConstraintLayout;
    CharSequence cs[];
    int[] back_images;
    int design;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilfe);
        Context context = this;
        Resources res = context.getResources();
        back_images  = new int[]{R.drawable.snake_standard, R.drawable.retro_background, R.drawable.snake_nightmode};
        ArrayList<String> array = new ArrayList<>();
        cs = getResources().getTextArray(R.array.hilfeText);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this, R.layout.layouthilfe, getResources().getStringArray(R.array.hilfeText));
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

    public void findAllViewsById(){
        buttonZurueck = findViewById(R.id.buttonZurueckHilfe);
        buttonZurueck.setBackgroundResource(0);
        listView = (ListView) findViewById(R.id.listViewHelp);
        listView.setBackgroundResource(R.drawable.transparent_bg_bordered);
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.relativeLayoutHilfe);
        title = findViewById(R.id.textView);

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
}