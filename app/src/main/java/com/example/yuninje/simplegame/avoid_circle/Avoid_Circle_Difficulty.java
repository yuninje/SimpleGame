package com.example.yuninje.simplegame.avoid_circle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yuninje.simplegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Avoid_Circle_Difficulty extends AppCompatActivity {

    String difficulty;
    Button btn_easy;
    Button btn_normal;
    Button btn_hard;
    Button btn_extreme;
    static DocumentSnapshot document;
    static FirebaseFirestore db;
    static String id;
    static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_avoid_circle_difficulty);

        getWindow().getDecorView().setBackgroundColor(Color.rgb(46,46,46));

        db = FirebaseFirestore.getInstance();

        btn_easy = findViewById(R.id.btn_easy);
        btn_normal = findViewById(R.id.btn_normal);
        btn_hard = findViewById(R.id.btn_hard);
        btn_extreme = findViewById(R.id.btn_extreme);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_easy:
                        difficulty = "EASY";
                        break;
                    case R.id.btn_normal:
                        difficulty = "NORMAL";
                        break;
                    case R.id.btn_hard:
                        difficulty = "HARD";
                        break;
                    case R.id.btn_extreme:
                        difficulty = "EXTREME";
                        break;
                    default:
                        break;
                }


                Intent intent = new Intent(Avoid_Circle_Difficulty.this, AC_Draw_View_Execute.class);
                intent.putExtra("difficulty",difficulty);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        };

        btn_easy.setOnClickListener(onClickListener);
        btn_normal.setOnClickListener(onClickListener);
        btn_hard.setOnClickListener(onClickListener);
        btn_extreme.setOnClickListener(onClickListener);

    }

    private static void setButtonText(final String difficulty, final Button btn){
        Task<DocumentSnapshot> task= db.collection(difficulty).document(id).get();
        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            document = task.getResult();
                            btn.setText(difficulty + "\nSCORE : " + document.getLong("score").toString());
                        }
                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();

        setButtonText("EASY", btn_easy);
        setButtonText("NORMAL", btn_normal);
        setButtonText("HARD", btn_hard);
        setButtonText("EXTREME", btn_extreme);

    }
}