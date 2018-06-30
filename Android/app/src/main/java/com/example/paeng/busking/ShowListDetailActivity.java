package com.example.paeng.busking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.paeng.busking.model.Show;

import java.util.ArrayList;

/*
 * Created by paeng on 2018. 6. 30..
 */

public class ShowListDetailActivity extends AppCompatActivity {

    private ArrayList<Show> showArrayList;
    private int position;
    private TextView tvId, tvName, tvTitle, tvDesc, tvTime, tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        bindingView();

        showArrayList = new ArrayList<Show>();
        Intent intent = getIntent();
        showArrayList = (ArrayList<Show>) intent.getSerializableExtra("showSet");
        position = Integer.parseInt(intent.getStringExtra("position"));
        setInformation();


    }

    private void bindingView(){
        tvId = (TextView)findViewById(R.id.tv_id);
        tvName = (TextView)findViewById(R.id.tv_name);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvLocation = (TextView)findViewById(R.id.tv_location);
        tvDesc = (TextView)findViewById(R.id.tv_description);


    }
    private void setInformation(){
        tvId.setText(showArrayList.get(position).getUserId());
        tvName.setText(showArrayList.get(position).getShowName());
        tvTitle.setText(showArrayList.get(position).getShowTitle());
        tvLocation.setText(showArrayList.get(position).getShowLocation());
        tvTime.setText(showArrayList.get(position).getShowTime());
        tvDesc.setText(showArrayList.get(position).getShowDescription());


    }
}
