package com.example.ip2019androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class LoggedActivity extends AppCompatActivity {


    public static String drName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        drName = getIntent().getStringExtra(drName);
//        Button btn_remote_control =(Button) findViewById(R.id.btnAutomat);
//        Button btn_local_control = (Button) findViewById(R.id.btnLocal);
//        btn_local_control.setActivated(false);
//        btn_remote_control.setActivated(false);

    }
}
