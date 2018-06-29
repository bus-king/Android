package com.example.paeng.busking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paeng.busking.InnerDB.DBHelper;
import com.example.paeng.busking.model.Res;
import com.example.paeng.busking.model.Show;
import com.example.paeng.busking.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/*
 * Created by paeng on 2018. 6. 29..
 */

public class RegisterShowActivity extends AppCompatActivity {
    private CompositeSubscription mSubscriptions;
    EditText etName, etTitle, etDesc;
    TextView tvLocation, tvDate, tvTime;
    Button btnRegister;
    LinearLayout llChooseLoc, llChooseDate, llChooseTime;
    String resultDate, resultTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindingview();
    }

    private void bindingview(){
        etName = (EditText)findViewById(R.id.et_name);
        etTitle = (EditText)findViewById(R.id.et_title);

        llChooseLoc = (LinearLayout)findViewById(R.id.choose_location);
        tvLocation = (TextView) findViewById(R.id.tv_location);

        llChooseDate = (LinearLayout)findViewById(R.id.choose_date);
        tvDate = (TextView)findViewById(R.id.tv_date);

        llChooseTime = (LinearLayout)findViewById(R.id.choose_time);
        tvTime = (TextView)findViewById(R.id.tv_time);

        etDesc = (EditText)findViewById(R.id.et_desc);
        btnRegister = (Button)findViewById(R.id.btn_register);
        mSubscriptions = new CompositeSubscription();
        btnRegister.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (checkjoin()) {

                    Show showTest = new Show();
                    showTest.setUserId("bkteam@naver.com");
                    showTest.setShowName(etName.getText().toString());
                    showTest.setShowTitle(etTitle.getText().toString());
                    showTest.setShowLocation("홍대");
                    showTest.setShowGenre(1);
                    showTest.setShowHeart(0);
                    showTest.setShowTime(resultDate+resultTime);
                    showTest.setShowDescription(etDesc.getText().toString());

                    registerProgress(showTest);

                    //registerProgressInnerDB(showTest);
                    Toast.makeText(RegisterShowActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        llChooseLoc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tvLocation.setText("홍대 입구역 9번출구 앞");
            }
        });

        llChooseDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar calendarDead = Calendar.getInstance();
                int todayYear = calendarDead.get(Calendar.YEAR);
                int todayMonth = calendarDead.get(Calendar.MONTH);
                int todayDay = calendarDead.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterShowActivity.this, listener, todayYear, todayMonth, todayDay);
                dialog.show();
            }
            private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear++;
                    Toast.makeText(getApplicationContext(), year + "." + monthOfYear + "." + dayOfMonth, Toast.LENGTH_SHORT).show();

                    tvDate.setText(year + "년 " + monthOfYear +"월 " + dayOfMonth + "일 ");

                    String todoDeadlineMonth, todoDeadlineDay;

                    if (monthOfYear>0 && monthOfYear<10){
                        todoDeadlineMonth = "0" + Integer.toString(monthOfYear);
                    }else{
                        todoDeadlineMonth = Integer.toString(monthOfYear);
                    }
                    if (dayOfMonth>0 && dayOfMonth<10){
                        todoDeadlineDay = "0" + Integer.toString(dayOfMonth);
                    }else{
                        todoDeadlineDay = Integer.toString(dayOfMonth);
                    }
                    resultDate =  Integer.toString(year)+todoDeadlineMonth+todoDeadlineDay;
                }
            };
        });

        llChooseTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerDialog dialog = new TimePickerDialog(RegisterShowActivity.this, listener, 00, 00, false);
                dialog.show();
            }
            private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tvTime.setText(hourOfDay + "시 " + minute+ "분");

                    String mHour, mMinute;
                    if (hourOfDay>0 && hourOfDay<10){
                        mHour = "0" + Integer.toString(hourOfDay);
                    }else if(hourOfDay==0){
                        mHour = "0" + Integer.toString(hourOfDay);
                    }else{
                        mHour = Integer.toString(hourOfDay);
                    }
                    if (minute>0 && minute<10){
                        mMinute = "0" + Integer.toString(minute);
                    }else if(minute==0){
                        mMinute = "00";
                    }else{
                        mMinute = Integer.toString(minute);
                    }
                    resultTime = mHour+mMinute;


                }
            };
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
            showMessage("서버에 문제가 발생했습니다 ㅠㅠ");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(RegisterShowActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkjoin(){
        if(etTitle.getText().toString().equals("")){
            Toast.makeText(RegisterShowActivity.this, "제목을 입력해주세요!", Toast.LENGTH_LONG).show();
            return false;
        }else if (etName.getText().toString().equals("")){
            Toast.makeText(RegisterShowActivity.this, "버스킹 팀 이름을 입력해주세요!", Toast.LENGTH_LONG).show();
            return false;
        }else if (tvLocation.getText().toString().equals("위치를 선택해주세요!")){
            Toast.makeText(RegisterShowActivity.this, "위치를 입력해주세요!", Toast.LENGTH_LONG).show();
            return false;
        }else if (tvDate.getText().toString().equals("날짜를 선택해 주세요!")) {
            Toast.makeText(RegisterShowActivity.this, "날짜를 선택해주세요!", Toast.LENGTH_LONG).show();
            return false;
        } else if (tvTime.getText().toString().equals("시간를 선택해 주세요!")) {
            Toast.makeText(RegisterShowActivity.this, "시간을 선택해주세요!", Toast.LENGTH_LONG).show();
            return false;
        } else if (etDesc.getText().toString().equals("")) {

            Toast.makeText(RegisterShowActivity.this, "설명을 써주세요!", Toast.LENGTH_LONG).show();
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

    private void registerProgressInnerDB(Show showDB){

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Busking.db", null, 1);
        dbHelper.insertShow(showDB.getUserId(),showDB.getShowName(), showDB.getShowTitle(),
                showDB.getShowLocation(), showDB.getShowGenre(), showDB.getShowHeart(), showDB.getShowTime(),
                showDB.getShowDescription());

    }
}
