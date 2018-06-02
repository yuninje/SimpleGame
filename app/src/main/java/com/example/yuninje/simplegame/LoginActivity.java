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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    Intent intent;
    static int NEWACCOUNTACTIVITY_REQUEST_CODE = 1;

    EditText et_ID;
    EditText et_password;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getWindow().getDecorView().setBackgroundColor(Color.rgb(46,46,46));

        db = FirebaseFirestore.getInstance();

        et_ID = findViewById(R.id.login_et_ID);
        et_password = findViewById(R.id.login_et_password);

        Button btn_login = findViewById(R.id.login_btn_login);
        Button btn_newAccount = findViewById(R.id.login_btn_newAccount);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ID.getText().toString() != "") {
                    DocumentReference contact = db.collection("USER").document(et_ID.getText().toString());
                    contact.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document != null && document.exists()) {
                                    //Toast.makeText(LoginActivity.this, "아이디 있음 !!!!! ", Toast.LENGTH_LONG).show();
                                    if(document.getString("password").equals(et_password.getText().toString())){
                                        Toast.makeText(LoginActivity.this, "로그인 성공!!!!!", Toast.LENGTH_SHORT).show();
                                        String id = et_ID.getText().toString();
                                        intent = new Intent(LoginActivity.this, SelectActivity.class);
                                        intent.putExtra("name",document.getString("name"));
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(LoginActivity.this, "로그인 실패 패스워드 틀림!!!!!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "아이디 없음 !!!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        btn_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, NewAccountActivity.class);
                startActivityForResult(intent, NEWACCOUNTACTIVITY_REQUEST_CODE);

            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == NEWACCOUNTACTIVITY_REQUEST_CODE){
            if(resultCode == 1){
               Toast.makeText(LoginActivity.this,"MAKE NEW ACCOUNT SUCCED",Toast.LENGTH_SHORT).show();
            }else{  // resultCode == 0
                Toast.makeText(LoginActivity.this, "MAKE NEW ACCOUNT CANCELED",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
