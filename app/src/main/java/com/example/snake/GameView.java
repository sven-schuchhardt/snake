package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameView extends View {


    public  static Snake snake;
    private Bitmap bmItem, bmItem2;

    private Bitmap bmSnake, bmSpielfeld, bmSpielfeld1;
    private final ArrayList<Spielfeld> arraySpielfeld = new ArrayList<>();
    public static int sizeOfMap = 75*Constants.SCREEN_WIDTH/1080;
    private int startCount;
    private boolean beendet = false;
    private boolean move = false;
    private float mx, my;
    private final Handler handler;
    private final Runnable runnable;
    int itemCounter = 0;
    private final Highscore h = new Highscore();
    private final Spielen s = new Spielen();
    private boolean action_up,action_down,action_left,action_right;
    private int test = 0;
    private final Item item;
    public static long timeSeconds;

    private Item2 item2;
    boolean flag = false;
    MediaPlayer mediaItem2;

    private static int score;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Constants.start = false;
        score = 0;
        int test = Constants.design;
        if(Constants.design == 0){
            bmSpielfeld = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
            bmSpielfeld = Bitmap.createScaledBitmap(bmSpielfeld, sizeOfMap, sizeOfMap,true);
            bmSpielfeld1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
            bmSpielfeld1 = Bitmap.createScaledBitmap(bmSpielfeld1, sizeOfMap, sizeOfMap,true);
            bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snakebrown);
            bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeOfMap, sizeOfMap, true);
        }else if(Constants.design == 1){
            bmSpielfeld = BitmapFactory.decodeResource(this.getResources(), R.drawable.yellow);
            bmSpielfeld = Bitmap.createScaledBitmap(bmSpielfeld, sizeOfMap, sizeOfMap,true);
            bmSpielfeld1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grey2);
            bmSpielfeld1 = Bitmap.createScaledBitmap(bmSpielfeld1, sizeOfMap, sizeOfMap,true);
            bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snakepink);
            bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeOfMap, sizeOfMap, true);
        }else if(Constants.design == 2){
            bmSpielfeld = BitmapFactory.decodeResource(this.getResources(), R.drawable.grey1);
            bmSpielfeld = Bitmap.createScaledBitmap(bmSpielfeld, sizeOfMap, sizeOfMap,true);
            bmSpielfeld1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grey2);
            bmSpielfeld1 = Bitmap.createScaledBitmap(bmSpielfeld1, sizeOfMap, sizeOfMap,true);
            bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snakeorange);
            bmSnake = Bitmap.createScaledBitmap(bmSnake, 14*sizeOfMap, sizeOfMap, true);
        }

        bmItem = BitmapFactory.decodeResource(this.getResources(), R.drawable.star);
        bmItem = Bitmap.createScaledBitmap(bmItem, sizeOfMap, sizeOfMap, true);

        bmItem2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.plus2);
        bmItem2 = Bitmap.createScaledBitmap(bmItem2, sizeOfMap, sizeOfMap, true);


        extractedDrawLoop();
        /*if(Constants.landscape){
            snake = new Snake(bmSnake, arraySpielfeld.get(40).getX(), arraySpielfeld.get(40).getY(), 5);
        }*/
        if(Constants.gameToSettings){
            snake = Constants.snake;
            Constants.gameToSettings = false;
        }else {
            snake = new Snake(bmSnake, arraySpielfeld.get(40).getX(), arraySpielfeld.get(40).getY(), 3);
        }

        item = new Item(bmItem, arraySpielfeld.get(generatePosition()[0]).getX(), arraySpielfeld.get(generatePosition()[1]).getY());



        item2 = new Item2(bmItem2, arraySpielfeld.get(generatePosition()[0]).getX(), arraySpielfeld.get(generatePosition()[1]).getY());


        Constants.startTime = System.currentTimeMillis();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    public void extractedDrawLoop() {
        for(int i = 0; i < Constants.h; i++){
            for(int j = 0; j < Constants.w; j++){
                if((i+j)%2==0) {
                    arraySpielfeld.add(new Spielfeld(bmSpielfeld, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - (Constants.w / 2) * sizeOfMap,
                            i * sizeOfMap + 100 * Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }else{
                   arraySpielfeld.add(new Spielfeld(bmSpielfeld1, j * sizeOfMap + Constants.SCREEN_WIDTH / 2 - (Constants.w / 2) * sizeOfMap,
                            i * sizeOfMap + 100 * Constants.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        if(startCount >= 1 && !beendet){ Constants.start = Constants.noPause;}
      if(Constants.start){
          if(Constants.up || Constants.down ||Constants.left ||Constants.right){
              test = 2;
              move();
          }
          startCount++;
          //canvas.drawColor(Color.BLACK);
          for(int i = 0; i < arraySpielfeld.size(); i++){
              canvas.drawBitmap(arraySpielfeld.get(i).getBitMap(), arraySpielfeld.get(i).getX(), arraySpielfeld.get(i).getY(), null);
          }
          //Score erhöhen
          //----
          //
          //----
          Constants.saveScore++;
          score++;
          long endTime = System.currentTimeMillis();
          long time = endTime - Constants.startTime;
          Spielen.txt_score.setText(""+score);
          Spielen.txt_time.setText("");
          timeSeconds = TimeUnit.MILLISECONDS.toSeconds(time) + Constants.savveTime;
          Spielen.txt_time.setText(Long.toString(timeSeconds));
          snake.refresh();

          if(snake.getArraySnakeParts().get(0).getX() < this.arraySpielfeld.get(0).getX()
                  ||snake.getArraySnakeParts().get(0).getY() < this.arraySpielfeld.get(0).getY()
                  ||snake.getArraySnakeParts().get(0).getY()+sizeOfMap>this.arraySpielfeld.get(this.arraySpielfeld.size()-1).getY() + sizeOfMap
                  ||snake.getArraySnakeParts().get(0).getX()+sizeOfMap>this.arraySpielfeld.get(this.arraySpielfeld.size()-1).getX() + sizeOfMap){
              Spielen.rl_gameOver.setVisibility(VISIBLE);
              Spielen.gridLayout.setVisibility(INVISIBLE);
              Spielen.rl_pause_mute.setVisibility(INVISIBLE);
              Constants.start = false;
              beendet = true;
              Constants.overallScore += score;
              Constants.score = score;
              Constants.saveScore=0;
          }
          for (int i = 1; i < snake.getArraySnakeParts().size(); i++){
              if (snake.getArraySnakeParts().get(0).getrBody().intersect(snake.getArraySnakeParts().get(i).getrBody())){
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  Spielen.rl_gameOver.setVisibility(VISIBLE);
                  Spielen.gridLayout.setVisibility(INVISIBLE);
                  Spielen.rl_pause_mute.setVisibility(INVISIBLE);
                  Constants.start = false;
                  beendet = true;
                  Constants.overallScore += score;
                  Constants.score = score;
                  Constants.saveScore=0;
                  Constants.savveTime =0;
              }
          }
          snake.drawSnake(canvas);
          item.draw(canvas);

          item2.draw(canvas);



          if(snake.getArraySnakeParts().get(0).getrBody().intersect(item.getRec())){
            Spielen.mediaItem.start();
            score = score + 5000;
            Constants.saveScore += 5000;
            score += 5000;
            generatePosition();
            item.reset(arraySpielfeld.get(generatePosition()[0]).getX(), arraySpielfeld.get(generatePosition()[1]).getY());
            itemCounter++;
            Constants.overallItemCounter++;
            if(itemCounter == 5){
                snake.addPart();
                itemCounter = 0;
            }
          }
          if(snake.getArraySnakeParts().get(0).getrBody().intersect(item2.getRec())){
              Spielen.mediaItem2.start();
              Constants.overallItemCounter++;
              generatePosition();
              item2.reset(arraySpielfeld.get(generatePosition()[0]).getX(), arraySpielfeld.get(generatePosition()[1]).getY());
              snake.addPart();
              snake.addPart();
          }
      }


        handler.postDelayed(runnable, 150);

    }

    public boolean move(){
        switch(test){
            case 2: {
                if (move == false) {
                    move = true;
                } else {
                    if (Constants.left) {
                        snake.setMove_left(true);
                        Constants.left = false;
                    } else if (Constants.right) {
                        snake.setMove_right(true);
                        Constants.right = false;
                    } else if (Constants.up) {
                        snake.setMove_top(true);
                        Constants.up = false;
                    } else if (Constants.down) {
                        snake.setMove_bottom(true);
                        Constants.down = false;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }

        return true;
    }

 @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch(a){
            case MotionEvent.ACTION_MOVE: {
                if (move == false) {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                } else {
                    if (mx - event.getX() > 100 * Constants.SCREEN_WIDTH / 1080
                            && !snake.isMove_right()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                    } else if (event.getX() - mx > 100 * Constants.SCREEN_WIDTH / 1080
                            && !snake.isMove_left()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                    } else if (my - event.getY() > 100 * Constants.SCREEN_WIDTH / 1080
                            && !snake.isMove_bottom()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_top(true);
                    } else if (event.getY() - my > 100 * Constants.SCREEN_WIDTH / 1080
                            && !snake.isMove_top()) {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }

        return true;
    }

    public boolean isStart() {
        return Constants.start;
    }

    public void setStart(boolean start) {
        Constants.start = start;
    }

    public void reset() throws IOException {
        for(int i = 0; i < Constants.h; i++){
            for (int j = 0; j < Constants.w; j++){
                if((j+i)%2==0){
                    arraySpielfeld.add(new Spielfeld(bmSpielfeld, j*bmSpielfeld.getWidth() + Constants.SCREEN_WIDTH/2 - (Constants.w/2)*bmSpielfeld.getWidth(), i*bmSpielfeld.getHeight()+50*Constants.SCREEN_HEIGHT/1920, bmSpielfeld.getWidth(), bmSpielfeld.getHeight()));
                }else{
                   arraySpielfeld.add(new Spielfeld(bmSpielfeld1, j*bmSpielfeld1.getWidth() + Constants.SCREEN_WIDTH/2 - (Constants.w/2)*bmSpielfeld1.getWidth(), i*bmSpielfeld1.getHeight()+50*Constants.SCREEN_HEIGHT/1920, bmSpielfeld1.getWidth(), bmSpielfeld1.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake,arraySpielfeld.get(126).getX(),arraySpielfeld.get(126).getY(), 4);
        score = 0;
    }

    public int[] generatePosition(){
        int[] xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arraySpielfeld.size() - 1);
        xy[1] = r.nextInt(arraySpielfeld.size() - 1);

        Rect rect = new Rect(arraySpielfeld.get(xy[0]).getX(), arraySpielfeld.get(xy[1]).getY(), arraySpielfeld.get(xy[0]).getX()+sizeOfMap, arraySpielfeld.get(xy[1]).getY()+sizeOfMap);

        boolean check = true;
        while(check){
            check = false;
            for(int i = 0; i < snake.getArraySnakeParts().size(); i++){
                if(rect.intersect(snake.getArraySnakeParts().get(i).getrBody())){
                    check = true;
                    xy[0] = r.nextInt(arraySpielfeld.size()-1);
                    xy[1] = r.nextInt(arraySpielfeld.size()-1);
                    rect = new Rect(arraySpielfeld.get(xy[0]).getX(), arraySpielfeld.get(xy[1]).getY(), arraySpielfeld.get(xy[0]).getX()+sizeOfMap, arraySpielfeld.get(xy[1]).getY()+sizeOfMap);

                }
            }
        }
        return xy;
    }



}











