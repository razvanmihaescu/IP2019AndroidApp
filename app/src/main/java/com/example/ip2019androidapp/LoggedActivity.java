package com.example.ip2019androidapp;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class LoggedActivity extends AppCompatActivity {

    Button btn_remote_control,btn_local_control,btn_BTconnect;
    TextView mTVTitle;
    public static String drName=null;
    BluetoothSocket btSocket=DeviceList.btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        drName = getIntent().getStringExtra("drName");

        mTVTitle = findViewById(R.id.actLoggedText);
        mTVTitle.setText("Dr. " + drName);

        btn_BTconnect=(Button) findViewById(R.id.btnBTconnect);
        btn_remote_control =(Button) findViewById(R.id.btnAutomat);
        btn_local_control = (Button) findViewById(R.id.btnLocal);
        btn_local_control.setClickable(true);
        btn_remote_control.setClickable(true);

    }

    public void doLaunchBT_ConnectingProcedure(View view) {
    Intent intent = new Intent(this,DeviceList.class);
    startActivity(intent);
    }

    public void doLaunchLocalMoveActivity(View view) {
        Intent intent = new Intent(this,MoveActivity.class);
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("5".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
        startActivity(intent);
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public void doLaunchRemoteMoveActivity(View view) {
        Intent intent = new Intent(this,RemoteActivity.class);
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("6".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
        startActivity(intent);
    }
}
