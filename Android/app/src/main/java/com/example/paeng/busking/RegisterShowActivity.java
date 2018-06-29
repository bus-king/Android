package com.example.paeng.busking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paeng.busking.InnerDB.DBHelper;
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
    EditText etTitle, etTime, etLoc, etGenre, etDesc;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        bindingview();
    }

    private void bindingview(){

        etTitle = (EditText)findViewById(R.id.et_title);
        etTime = (EditText)findViewById(R.id.et_time);
        etLoc = (EditText)findViewById(R.id.et_loc);
        etGenre = (EditText)findViewById(R.id.et_genre);
        etDesc = (EditText)findViewById(R.id.et_desc);
        btnRegister = (Button)findViewById(R.id.btn_register);
        mSubscriptions = new CompositeSubscription();
        btnRegister.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (checkjoin()) {

                    Show showTest = new Show();
                    showTest.setUserId("nuggy875");
                    showTest.setShowName("팽진욱");
                    showTest.setShowTitle(etTitle.getText().toString());
                    showTest.setShowLocation(etLoc.getText().toString());
                    showTest.setShowGenre(Integer.valueOf(etGenre.getText().toString()));
                    showTest.setShowHeart(0);
                    showTest.setShowTime(etTime.getText().toString());
                    showTest.setShowDescription(etDesc.getText().toString());

                    registerProgress(showTest);

                    //registerProgressInnerDB(showTest);
                    Toast.makeText(RegisterShowActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void registerProgressInnerDB(Show showDB){

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Busking.db", null, 1);
        dbHelper.insertShow(showDB.getUserId(),showDB.getShowName(), showDB.getShowTitle(),
                showDB.getShowLocation(), showDB.getShowGenre(), showDB.getShowHeart(), showDB.getShowTime(),
                showDB.getShowDescription());

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
        if(etTitle.getText().toString().equals("")){
            Toast.makeText(RegisterShowActivity.this, "제목을 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        }else if (etLoc.getText().toString().equals("")){
            Toast.makeText(RegisterShowActivity.this, "위치을 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        }else if (etTime.getText().toString().equals("")) {
            Toast.makeText(RegisterShowActivity.this, "시간을 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        } else if (etGenre.getText().toString().equals("")) {
            Toast.makeText(RegisterShowActivity.this, "장르를 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        } else if (etDesc.getText().toString().equals("")) {

            Toast.makeText(RegisterShowActivity.this, "설명을 입력해주세요", Toast.LENGTH_LONG).show();
            return false;
        } else{
            return true;

        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
