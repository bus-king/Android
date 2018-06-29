package com.example.paeng.busking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paeng.busking.model.Res;
import com.example.paeng.busking.model.Show;
import com.example.paeng.busking.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by paeng on 2018. 6. 29..
 */

public class RegisterShowActivity extends AppCompatActivity {
    private CompositeSubscription mSubscriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        bindingview();
    }

    private void bindingview(){

        EditText etTitle = (EditText)findViewById(R.id.et_title);
        EditText etTime = (EditText)findViewById(R.id.et_time);
        EditText etLoc = (EditText)findViewById(R.id.et_loc);
        EditText etGenre = (EditText)findViewById(R.id.et_genre);
        EditText etDesc = (EditText)findViewById(R.id.et_desc);
        Button btnRegister = (Button)findViewById(R.id.btn_register);
        mSubscriptions = new CompositeSubscription();
        btnRegister.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (checkjoin()) {

                    Show showTest = new Show();
                    showTest.setId(1);
                    showTest.setUserId("nuggy875");
                    showTest.setShowName("팽진욱");
                    showTest.setShowTitle("공연합니다");
                    showTest.setShowLocation("홍대거리");
                    showTest.setShowGenre(1);
                    showTest.setShowHeart(4);
                    showTest.setShowTime("18070118000");
                    showTest.setShowDescription("눈물짜는발라드공연합니다");

                    //registerProgress(showTest);


                }
            }
        });
    }

    private void registerProgress(Show showDB) {

        mSubscriptions.add(NetworkUtil.getRetrofit().registerShow(showDB)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(Res response) {

        showMessage(response.getMessage());
    }

    private void handleError(Throwable error) {

        if (error instanceof HttpException) {
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                Res response = gson.fromJson(errorBody,Res.class);
                showMessage(response.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showMessage("Network Error !");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(RegisterShowActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private boolean checkjoin(){

        return true;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
