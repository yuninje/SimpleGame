package com.example.yuninje.simplegame.Ranking;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuninje.simplegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    FirebaseFirestore db;
    DocumentSnapshot document_easy, document_normal, document_hard, document_extreme;
    Task<DocumentSnapshot> task_easy, task_normal, task_hard, task_extreme;
    TabHost tabHost;
    ListView listView_easy, listView_normal, listView_hard, listView_extreme;
    List<ListViewItem> itemList_easy, itemList_normal, itemList_hard, itemList_extreme;
    ListView_Adapter listViewAdapter_easy,listViewAdapter_normal,listViewAdapter_hard,listViewAdapter_extreme;

    String difficulty;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_ranking);

        //getWindow().getDecorView().setBackgroundColor(Color.rgb(46,46,46));

        db = FirebaseFirestore.getInstance();
        Log.w("ㅋㅋㅋ","db = FirebaseFirestore.getInstance();");

        listView_easy = findViewById(R.id.listView_easy);
        listView_normal = findViewById(R.id.listView_normal);
        listView_hard = findViewById(R.id.listView_hard);
        listView_extreme = findViewById(R.id.listView_extreme);
        itemList_easy = new ArrayList<ListViewItem>();
        itemList_normal = new ArrayList<ListViewItem>();
        itemList_hard = new ArrayList<ListViewItem>();
        itemList_extreme = new ArrayList<ListViewItem>();

        listViewAdapter_easy = new ListView_Adapter(this, itemList_easy);
        listViewAdapter_normal = new ListView_Adapter(this, itemList_normal);
        listViewAdapter_hard = new ListView_Adapter(this, itemList_hard);
        listViewAdapter_extreme = new ListView_Adapter(this, itemList_extreme);

        input_data(itemList_easy,"EASY", listViewAdapter_easy);
        input_data(itemList_normal,"NORMAL", listViewAdapter_normal);
        input_data(itemList_hard,"HARD", listViewAdapter_hard);
        input_data(itemList_extreme,"EXTREME", listViewAdapter_extreme);


        tabHost = findViewById(R.id.tabHost);


        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("1").setContent(R.id.tab1).setIndicator("EASY"));
        tabHost.addTab(tabHost.newTabSpec("2").setContent(R.id.tab2).setIndicator("NORMAL"));
        tabHost.addTab(tabHost.newTabSpec("3").setContent(R.id.tab3).setIndicator("HARD"));
        tabHost.addTab(tabHost.newTabSpec("4").setContent(R.id.tab4).setIndicator("EXTREME"));

        tabHost.setOnTabChangedListener(onTabChangeListener);
        Log.w("ㅋㅋㅋ","tabHost.setOnTabChangedListener(onTabChangeListener);");



        link_listView_adapter(listView_easy, listViewAdapter_easy, itemList_easy,document_easy, "EASY");
        link_listView_adapter(listView_normal, listViewAdapter_normal, itemList_normal, document_normal, "NORMAL");
        link_listView_adapter(listView_hard, listViewAdapter_hard, itemList_hard, document_hard, "HARD");
        link_listView_adapter(listView_extreme, listViewAdapter_extreme, itemList_extreme, document_extreme, "EXTREME");

        Log.w("ㅋㅋㅋ","Every link_ListView_Adapter success");

    }

    @Override
    protected void onStart() {
        Log.w("ㅋㅋㅋ","onStart");
        super.onStart();
    }

    private void input_data(final List<ListViewItem> itemList, final String difficulty, final ListView_Adapter myAdapter){

        Log.w("ㅋㅋㅋ","input_data");
        db.collection(difficulty).orderBy("score", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int ranking = 1;

                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()){

                                String id = document.getId();
                                int score = document.getLong("score").intValue();
                                String date = document.getString("date");

                                ListViewItem item = new ListViewItem(ranking, id, score, date);
                                itemList.add(item);
                                Log.w("ㅋㅋㅋ",difficulty+"itemList.add success :"+id);
                                myAdapter.notifyDataSetChanged();
                                ranking ++;
                            }
                        }
                    }
                });
    }

    private  void link_listView_adapter(ListView listView, ListView_Adapter listViewAdapter, List<ListViewItem> itemList,DocumentSnapshot document, String difficulty){
        Log.w("ㅋㅋㅋ","link_listView_adapter");
        listView.setAdapter(listViewAdapter);
        Log.w("ㅋㅋㅋ","listView.setAdapter(listViewAdapter) success");
    }




    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            switch (tabId) {
                case "1":
                    //difficulty = "EASY";
                    break;
                case "2":
                    //difficulty = "NORMAL";
                    break;
                case "3":
                    //difficulty = "HARD";
                    break;
                case "4":
                    //difficulty = "EXTREME";
                    break;
            }
        }
    };



}
