package com.example.yuninje.simplegame.avoid_circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yuninje.simplegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AC_Draw_View extends View {



    Paint myCircle_Paint;
    MyCircle myCircle;

    int OBSTACLE_COUNT;
    float distance;
    int width;
    int height;

    String difficulty;
    int diifficulty_speedY;

    String id;
    String name;
    int score;

    Context context;
    ArrayList<Obstacle> list;

    FirebaseFirestore db;
    Map<String, Object> data;
    DocumentSnapshot document;
    DocumentReference contact;

    boolean pause_flag = false;
    boolean gameover_flag = false;

    int color_black = Color.BLACK;
    int color_white = Color.WHITE;
    int color_background = Color.rgb(46,46,46);

    @Override
    protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        width  = getMeasuredWidth();
        height = getMeasuredHeight();
        db = FirebaseFirestore.getInstance();
        data = new HashMap<>();
        contact = db.collection(difficulty).document(id);
        Task<DocumentSnapshot> task1= db.collection(difficulty).document(id).get();
        task1.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    document = task.getResult();
                    score = document.getLong("score").intValue();
                }
            }
        });

        myCircle = new MyCircle(200, (int)height/2, 40, diifficulty_speedY);
        list = null;
        gameover_flag = false;
        Obstacle.score = 0;
    }

    @Override
    protected void onDraw(Canvas canvas){

        super.onDraw(canvas);
        game(canvas);

        if(gameover_flag == true || pause_flag == true){
        }else{
            invalidate();
        }
        /*
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.pause);

        canvas.drawBitmap(image, 0, 0, null);
        */
        Paint textPaint = new Paint();
        textPaint.setColor(color_white);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setTextSize(120);
        canvas.drawText(String.valueOf(Obstacle.score),width - 30, 140, textPaint);

        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("BEST : "+score,30, 140, textPaint);
    }

    public AC_Draw_View(Context context, String difficulty, String id, String name) {
        super(context);

        this.context = context;
        this.difficulty = difficulty;
        this.id = id;
        this.name = name;

        setBackgroundColor(color_background);
        myCircle_Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myCircle_Paint.setStyle(Paint.Style.FILL);

        switch (difficulty){
            case "EASY":
                OBSTACLE_COUNT = 20;
                diifficulty_speedY = 10;
                break;
            case "NORMAL":
                OBSTACLE_COUNT = 30;
                diifficulty_speedY = 14;
                break;
            case "HARD":
                OBSTACLE_COUNT = 35;
                diifficulty_speedY = 17;
                break;
            case "EXTREME":
                OBSTACLE_COUNT = 45;
                diifficulty_speedY = 20;
                break;
        }
    }

    void initData(){
        list = new ArrayList<Obstacle>();

        for(int x = 0; x < OBSTACLE_COUNT; x++)
            list.add(new Obstacle(width, height));
    }

    void game(Canvas canvas) {
        myCircle.drawCircle(canvas,myCircle_Paint);

        if(list == null)
            initData();

        for(int x = 0; x < OBSTACLE_COUNT;x++) {
            distance = list.get(x).radius + myCircle.radius;
            list.get(x).draw(canvas, myCircle_Paint);

            if((list.get(x).x - myCircle.x)*(list.get(x).x - myCircle.x)
                    + (list.get(x).y-myCircle.y)*(list.get(x).y-myCircle.y)
                    <= distance*distance ){
                gameover_flag = true;


                if((document.getLong("score").intValue()) < Obstacle.score) {
                    Toast.makeText(context, Obstacle.score + " 갱신 !!!", Toast.LENGTH_LONG).show();
                    data.put("score", Obstacle.score);
                    Date today = new Date();
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                    data.put("date", date.format(today).toString());

                    contact.set(data);
                }
                Activity activity = (Activity)getContext();
                activity.recreate();
            }
        }
        for(int x= 0; x<OBSTACLE_COUNT; x++)
            list.get(x).update();

        myCircle.update(canvas);

        Activity activity = (Activity)getContext();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.w("onTouchEvent", "start");
        int action = event.getAction();
        if(action ==MotionEvent.ACTION_DOWN){
            if(event.getX() < 140 && event.getY() < 140) {
                if(pause_flag == true) {
                    pause_flag = false;
                    invalidate();
                }
                else {// pause_flag == false
                    pause_flag = true;
                    invalidate();
                    return true;
                }
            }else {
                if(pause_flag == true){

                }
                else {
                    if ((myCircle.y <= myCircle.radius) && (myCircle.speedY == 0))
                        myCircle.speedY = diifficulty_speedY;
                    else if ((myCircle.y >= height - myCircle.radius) && (myCircle.speedY == 0))
                        myCircle.speedY = -1 * diifficulty_speedY;
                    else
                        myCircle.speedY *= -1;
                }
            }
        }

        if(gameover_flag == true){
            invalidate();
        }
        return true;
    }



}
