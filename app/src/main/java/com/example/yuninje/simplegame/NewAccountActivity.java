package com.example.yuninje.simplegame;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewAccountActivity extends AppCompatActivity {

    static int RESULT_SUCCESS = 1;
    static int RESULT_FAILED = 0;

    EditText et_ID;
    FirebaseFirestore db;
    EditText et_name;
    EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);


        getWindow().getDecorView().setBackgroundColor(Color.rgb(46,46,46));
        db = FirebaseFirestore.getInstance();

        final Map<String,Object> user = new HashMap<>();
        final Map<String,Object> score = new HashMap<>();

        Button btn_add = findViewById(R.id.na_btn_add);
        Button btn_cancel = findViewById(R.id.na_btn_cancel);

        et_ID = findViewById(R.id.na_et_ID);
        et_name = findViewById(R.id.na_et_name);
        et_password = findViewById(R.id.na_et_password);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_ID.getText().toString() !="" && et_name.getText().toString() !="" && et_password.getText().toString() !="") {

                    DocumentReference contact = db.collection("USER").document(et_ID.getText().toString());
                    contact.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();

                                if( document != null && document.exists()){
                                    // 아이디 존재
                                    Toast.makeText(NewAccountActivity.this, "아이디 있음 ", Toast.LENGTH_SHORT).show();

                                    et_ID.setText("");
                                    et_name.setText("");
                                    et_password.setText("");
                                }else{
                                    user.put("name", et_name.getText().toString());
                                    user.put("password", et_password.getText().toString());
                                    score.put("score", Integer.valueOf("0"));
                                    Date today = new Date();
                                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                                    score.put("date", date.format(today).toString());

                                    db.collection("EASY").document(et_ID.getText().toString())
                                            .set(score);
                                    db.collection("NORMAL").document(et_ID.getText().toString())
                                            .set(score);
                                    db.collection("HARD").document(et_ID.getText().toString())
                                            .set(score);
                                    db.collection("EXTREME").document(et_ID.getText().toString())
                                            .set(score);

                                    db.collection("USER").document(et_ID.getText().toString())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(NewAccountActivity.this, "ADD SUCCESS", Toast.LENGTH_SHORT);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(NewAccountActivity.this, "ADD FAILED", Toast.LENGTH_SHORT);
                                                }
                                            });

                                    Intent intent = new Intent();
                                    setResult(RESULT_SUCCESS, intent);
                                    finish();
                                }
                            }
                        }
                    });


                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_FAILED, intent);
                finish();
            }
        });
    }


}
