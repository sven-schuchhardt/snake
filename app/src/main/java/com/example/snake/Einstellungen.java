package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class Einstellungen extends AppCompatActivity {

    ImageButton buttonZuruck, buttonAccept;
    SeekBar seekBarVol;
    SeekBar seekBarSound;
    Spinner designSpinner;
    CheckBox links;
    View screenView;
    int music=50;
    int volume=50;
    int design=0;
    int[] back_images;
    public static boolean lHanded=false;
    TextView txtEinstellugnen;
    TextView txtDesign;
    TextView txtVol;
    TextView txtSounds;
    ConstraintLayout mConstraintLayout;
    int haken = 0;
    Boolean volChFirst = true;
    Boolean souChFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_einstellungen);

        designSpinner = findViewById(R.id.spinnerDesign);

        ArrayAdapter<String> adapterDesign = new ArrayAdapter<String>(Einstellungen.this, R.layout.layoutlistview, getResources().getStringArray(R.array.arrayDesign));
        adapterDesign.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designSpinner.setAdapter(adapterDesign);

        back_images  = new int[]{R.drawable.snake_standard, R.drawable.retro_background, R.drawable.snake_nightmode};

        loadData();
        findAllViewsById();
        applyData();
        Constants.boolLeftHanded = links.isChecked();
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.boolLeftHanded = links.isChecked();
                if(lHanded != links.isChecked()){
                    haken++;
                }
                else{
                    haken--;
                    if(haken<0){
                        haken = 0;
                    }
                }
                if(haken== 0 ){

                    buttonZuruck.setVisibility(View.VISIBLE);
                    buttonAccept.setVisibility(View.INVISIBLE);

                }
                else{
                    buttonZuruck.setVisibility(View.INVISIBLE);
                    buttonAccept.setVisibility(View.VISIBLE);
                }

            }
        });
        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(music != seekBarSound.getProgress()&&souChFirst== true){
                    souChFirst = false;
                    haken++;
                    buttonZuruck.setVisibility(View.INVISIBLE);
                    buttonAccept.setVisibility(View.VISIBLE);
                }
                else{
                    souChFirst = true;
                    haken--;
                    if(haken<0){
                        haken = 0;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(music != seekBarSound.getProgress()&& volChFirst){
                    volChFirst = false;
                    haken++;
                    buttonZuruck.setVisibility(View.INVISIBLE);
                    buttonAccept.setVisibility(View.VISIBLE);
                }
                else{
                    volChFirst = true;
                    haken--;
                    if(haken<0){
                        haken = 0;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        designSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(design != designSpinner.getSelectedItemPosition()){
                    haken++;
                }
                else{
                    haken--;
                    if(haken<0){
                        haken = 0;
                    }
                }
                if(haken== 0 ){
                    buttonZuruck.setVisibility(View.VISIBLE);
                    buttonAccept.setVisibility(View.INVISIBLE);
                }
                else{
                    buttonZuruck.setVisibility(View.INVISIBLE);
                    buttonAccept.setVisibility(View.VISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        
        buttonZuruck.setOnClickListener(v -> back());
        buttonAccept.setOnClickListener(v -> back());
    }


    public void back(){
        saveData();
        Constants.design = design;
        Constants.seekbarVolume = seekBarVol.getProgress();
        Constants.seekbarSound = seekBarSound.getProgress();
        MainActivity.player.setVolume(0,0);
        if(Constants.gameToSettings){
            Intent intent = new Intent(this,Spielen.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }

    public void loadData(){
        SharedPreferences music_SharedPrefs = getSharedPreferences("music",Context.MODE_PRIVATE);
        music = music_SharedPrefs.getInt("musicVolume",50);

        SharedPreferences sound_SharedPrefs = getSharedPreferences("sound",Context.MODE_PRIVATE);
        volume = sound_SharedPrefs.getInt("soundVolume",50);

        SharedPreferences design_SharedPrefs = getSharedPreferences("design",MODE_PRIVATE);
        design = design_SharedPrefs.getInt("designMode",-1);


        SharedPreferences lHanded_SharedPrefs = getSharedPreferences("lHanded",Context.MODE_PRIVATE);
        lHanded = lHanded_SharedPrefs.getBoolean("left",false);

    }

    public void saveData(){
        SharedPreferences music_sharedPrefs = getSharedPreferences("music",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = music_sharedPrefs.edit();
        editor.putInt("musicVolume",seekBarVol.getProgress());
        editor.apply();

        SharedPreferences sound_sharedPrefs = getSharedPreferences("sound",Context.MODE_PRIVATE);
        editor = sound_sharedPrefs.edit();
        editor.putInt("soundVolume",seekBarSound.getProgress());
        editor.apply();

        SharedPreferences design_sharedPrefs = getSharedPreferences("design",Context.MODE_PRIVATE);
        editor = design_sharedPrefs.edit();
        design = designSpinner.getSelectedItemPosition();
        editor.putInt("designMode",design);
        editor.apply();

        SharedPreferences lHandedPrefs = getSharedPreferences("lHanded",Context.MODE_PRIVATE);
        editor = lHandedPrefs.edit();
        editor.putBoolean("left",links.isChecked());
        editor.apply();

    }

    public void findAllViewsById(){
        seekBarVol = findViewById(R.id.seekBarVol);
        seekBarSound = findViewById(R.id.seekBarSounds);
        buttonZuruck = findViewById(R.id.button123);
        buttonAccept = findViewById(R.id.buttonAccept);
        links = findViewById(R.id.linksCheckBox);
        screenView = findViewById(R.id.relativeLayout);
        txtEinstellugnen = findViewById(R.id.txtEinstellungen);
        txtDesign = findViewById(R.id.txtDesign);
        txtVol = findViewById(R.id.txtVol);
        txtSounds = findViewById(R.id.txtSounds);
    }

    public void applyData(){
        seekBarVol.setProgress(music);
        seekBarSound.setProgress(volume);
        buttonZuruck.setBackgroundResource(0);
        buttonAccept.setBackgroundResource(0);
        buttonAccept.setVisibility(View.INVISIBLE);
        links.setChecked(lHanded);

        if(design != -1) {
            designSpinner.setSelection(design);
            changeDesign(design,back_images);
        }else{
            designSpinner.setSelection(0);
            changeDesign(0,back_images);
        }
    }

    public void changeDesign(int d , int[] back_images){

        mConstraintLayout = (ConstraintLayout)findViewById(R.id.relativeLayoutEinstellungen);

        mConstraintLayout.setBackgroundResource(back_images[d]);
        if(d == 0){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_standard_landscape);
            }
            txtEinstellugnen.setTextColor(0xFF000000);
            txtDesign.setTextColor(0xFF000000);
            txtVol.setTextColor(0xFF000000);
            txtSounds.setTextColor(0xFF000000);
            links.setTextColor(0xFF000000);
            buttonZuruck.setColorFilter(0xFF000000);
            seekBarVol.getProgressDrawable().setTint(0xFF0F259C);
            seekBarVol.getThumb().setTint(0xFF0F259C);
            seekBarSound.getProgressDrawable().setTint(0xFF0F259C);
            seekBarSound.getThumb().setTint(0xFF0F259C);
        }
        if(d == 1){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_retro_landscape);
            }
            txtEinstellugnen.setTextColor(Color.YELLOW);
            txtDesign.setTextColor(0xFFFFFFFF);
            txtVol.setTextColor(0xFFFFFFFF);
            txtSounds.setTextColor(0xFFFFFFFF);
            links.setTextColor(0xFFFFFFFF);
            buttonZuruck.setColorFilter(0xFFFFFFFF);
        }
        if(d == 2){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mConstraintLayout.setBackgroundResource(R.drawable.snake_nightmode_landscape);
            }
            txtEinstellugnen.setTextColor(0xFFFFFFFF);
            txtDesign.setTextColor(0xFFFFFFFF);
            txtVol.setTextColor(0xFFFFFFFF);
            txtSounds.setTextColor(0xFFFFFFFF);
            links.setTextColor(0xFFFFFFFF);
            buttonZuruck.setColorFilter(0xFFFFFFFF);
        }







    }
}