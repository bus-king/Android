package com.example.paeng.busking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.paeng.busking.Common.Constants;
import com.example.paeng.busking.Logics.GenreLogic;

public class DetailActivity extends AppCompatActivity {

    static final String constant_title = "title";
    static final String constant_name = "name";
    static final String constant_date = "date";
    static final String constant_space = "space";
    static final String constant_suggest = "suggest";
    static final String constant_content = "content";
    static final String constant_genre = "genre";

    TextView txt_title;
    TextView txt_name;
    TextView txt_date;
    TextView txt_space;
    TextView txt_suggest;
    TextView txt_content;
    TextView txt_genre;
    //***** get *****

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init_text();


    }

    public void init_text() {
        txt_suggest = (TextView)findViewById(R.id.txt_suggest);
        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_content = (TextView)findViewById(R.id.txt_content);
        txt_date = (TextView)findViewById(R.id.txt_date);
        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_space = (TextView)findViewById(R.id.txt_space);
        txt_genre = (TextView)findViewById(R.id.txt_genre);
    }

    public void get_data() {
        Intent intent = new Intent();
        GenreLogic genre_logic = new GenreLogic(intent.getIntExtra(constant_genre, -1));
        String[] genres = new String[Constants.max_genre];
        txt_title.setText(intent.getStringExtra(constant_title));
        txt_name.setText(intent.getStringExtra(constant_name));
        txt_date.setText(intent.getStringExtra(constant_date));
        txt_space.setText(intent.getStringExtra(constant_space));
        String genres_str = "";

        for(int i = 0; i < Constants.max_genre; i++) {
            if(genres[i] != null)
                genres_str += (genres[i] +", ");
        }
        genre_logic.get_genre(genres);

        txt_suggest.setText(String.valueOf(intent.getIntExtra(constant_suggest, -1)));
        if(txt_suggest.equals("-1")){
            Log.e("get detail data error", "why default num");
            finish();
        }
        txt_content.setText(intent.getStringExtra(constant_content));
        txt_genre.setText(genres_str);
    }
}
