package com.example.ip2019androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedActivity extends AppCompatActivity {

    Button btn_remote_control,btn_local_control,btn_BTconnect;
    TextView mTVTitle;
    public static String drName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        drName = getIntent().getStringExtra("drName");

        mTVTitle = findViewById(R.id.actLoggedText);
        mTVTitle.setText(getString(R.string.drWelcome) + drName);

        btn_BTconnect=(Button) findViewById(R.id.btnBTconnect);
        btn_remote_control =(Button) findViewById(R.id.btnAutomat);
        btn_local_control = (Button) findViewById(R.id.btnLocal);
        btn_local_control.setClickable(false);
        btn_remote_control.setClickable(false);

    }
    public void doLaunchBT_ConnectingProcedure(View view) {
    Intent intent = new Intent(this,DeviceList.class);
    startActivity(intent);
    }
}
