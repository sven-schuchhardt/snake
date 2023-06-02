package com.example.snake;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int h;
    public static int w;
    public static boolean landscape = false;
    public static long startTime;
    public static boolean noPause = true;
    public static boolean start = false;
    public static Map<String,Integer> newScore = new HashMap<>();
    public static int score;
    public static int design;
    public static boolean up,down,left,right;
    public static boolean gameToSettings = false;
    public static Snake snake;
    public static String name;
    public static float leftVol, rightVol = 1f;
    public static int saveScore=0;
    public static long savveTime=0;
    public static int leftHanded = 0;
    public static boolean boolLeftHanded = false;
    public static int seekbarVolume = 0;
    public static int seekbarSound = 1;
    public static int overallScore = 0;
    public static int overallItemCounter = 0;



}
