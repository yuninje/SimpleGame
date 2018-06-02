package com.example.yuninje.simplegame.avoid_circle;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.yuninje.simplegame.LoginActivity;
import com.example.yuninje.simplegame.SelectActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AC_Draw_View_Execute extends AppCompatActivity {

    FirebaseFirestore db;
    DocumentSnapshot document;

    String difficulty;
    String id;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        difficulty = intent.getStringExtra("difficulty");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        View myView = new AC_Draw_View(this, difficulty, id, name);
        setContentView(myView);

    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}
