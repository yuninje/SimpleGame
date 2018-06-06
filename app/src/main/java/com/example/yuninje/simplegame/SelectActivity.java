package com.example.yuninje.simplegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yuninje.simplegame.ranking.RankingActivity;
import com.example.yuninje.simplegame.avoid_circle.Avoid_Circle_Difficulty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SelectActivity extends AppCompatActivity {

    static FirebaseFirestore db;
    static String id, name;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        getWindow().getDecorView().setBackgroundColor(Color.rgb(46,46,46));


        db = FirebaseFirestore.getInstance();

        final Intent login_intent = getIntent();
        id  = login_intent.getStringExtra("id");
        name = login_intent.getStringExtra("name");

        Button btn_AC = findViewById(R.id.btn_AC);
        Button btn_Ranking = findViewById(R.id.btn_Ranking);

        btn_AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SelectActivity.this, Avoid_Circle_Difficulty.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btn_Ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SelectActivity.this, RankingActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private static void setButtonText(final String difficulty, final Button btn){
        db.collection(difficulty)
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            btn.setText(difficulty + "\nSCORE : " + document.getLong("score").intValue());

                        }
                    }
                });
    }
}
