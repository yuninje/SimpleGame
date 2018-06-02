package com.example.yuninje.simplegame.avoid_circle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Obstacle {

    Random r;
    int x;
    int y;
    int radius;
    int color;
    int speedX;
    int width;
    int height;
    static int score = 0;

    public Obstacle(int width, int height){

        r= new Random();
        this.width = width;
        this.height = height;
        this.radius = r.nextInt(50)+30;
        speedX = r.nextInt(10)+5;
        this.x = r. nextInt(width)+width;
        this.y = r.nextInt(height);

        this.color = Color.rgb(  r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    void draw(Canvas canvas, Paint paint){
        paint.setColor(this.color);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }

    void update(){
        this.x -= this.speedX;
        if(this.x+radius <0){
            this.x = r.nextInt(width)+width;
            this.y = r.nextInt(height);
            score ++;
        }
    }
}
