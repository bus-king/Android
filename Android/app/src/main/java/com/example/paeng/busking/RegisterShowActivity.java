package com.example.paeng.busking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by paeng on 2018. 6. 29..
 */

public class RegisterShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindingview();
    }

    private void bindingview(){
        EditText etTitle = (EditText)findViewById(R.id.et_title);


    }
}
