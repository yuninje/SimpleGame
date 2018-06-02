package com.example.yuninje.simplegame.avoid_circle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import java.util.Random;

public class MyCircle {
    Random r;
    int x;
    int y;
    int radius;
    int color;
    int difficulty_speedY;
    int speedY;
    MyCircle(int x, int y, int radius, int difficulty_speedY) {
        r = new Random();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speedY = difficulty_speedY;
        this.difficulty_speedY = difficulty_speedY;
    }


    void drawCircle(Canvas canvas, Paint paint){
        Log.w("MyCircle drawCircle", "start");

        this.color = Color.rgb( r.nextInt(255), r.nextInt(255), r.nextInt(255));
        paint.setColor(color);

        canvas.drawCircle(x, y, radius, paint);
    }

    void update(Canvas canvas){
        Log.w("MyCircle.update","start");
        if((y <= radius)&& (speedY == -1 * difficulty_speedY))
            speedY = 0;
        else if((y >= canvas.getHeight()-radius) && (speedY == difficulty_speedY))
            speedY = 0;
        y += speedY;
    }
}
