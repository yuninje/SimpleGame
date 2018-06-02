package com.example.yuninje.simplegame.Ranking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuninje.simplegame.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ListView_Adapter extends ArrayAdapter{

    public int ranking;
    public List<ListViewItem> itemList;
    Context context;
    String difficulty;

    public ListView_Adapter(@NonNull Context context, List<ListViewItem> listViewItemList) {
        super(context, R.layout.r_activity_ranking, listViewItemList);
        itemList = listViewItemList;
        Log.w("ㅋㅋㅋ","ListView_Adapter");
        this.context = context;
        ranking = 1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Log.w("ㅋㅋㅋ","getView");
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.r_activity_list_view_item, parent, false);
        }

        ListViewItem item = (ListViewItem)getItem(position);
        TextView tv_ranking = convertView.findViewById(R.id.item_tv_ranking);
        TextView tv_id = convertView.findViewById(R.id.item_tv_id);
        TextView tv_score = convertView.findViewById(R.id.item_tv_score);
        TextView tv_date = convertView.findViewById(R.id.item_tv_date);

        tv_ranking.setText(String.valueOf(item.getRanking()));
        tv_id.setText(item.getID());
        tv_score.setText(String.valueOf(item.getScore()));
        tv_date.setText(item.getDate());

        ranking++;

        return convertView;
    }
}
