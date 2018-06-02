package com.example.yuninje.simplegame.Ranking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListViewItem {
    // ranking , id, score, date
    static int ranking_easy = 1;
    static int ranking_normal = 1;
    static int ranking_hard = 1;
    static int ranking_extreme = 1;

    private int ranking;
    private String id;
    private int score;
    private String date;

    ListViewItem(int ranking, String id, int score, String date){
        this.ranking = ranking;
        this.id = id;
        this.score = score;
        this.date = date;
    }

    public int getRanking(){
        return ranking;
    }
    public String getID(){
        return id;
    }
    public int getScore(){
        return score;
    }
    public String getDate(){
        return date;
    }
}
