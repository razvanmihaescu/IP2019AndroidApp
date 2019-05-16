package com.example.ip2019androidapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MoveActivity extends AppCompatActivity {
    // Button btnOn, btnOff, btnDis;
    ImageButton Forward, Backward, Left, Right;
    String address = null;
    BluetoothSocket btSocket=DeviceList.btSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_move);

        //call the widgets
        Forward = (ImageButton) findViewById(R.id.Forward);
        Backward = (ImageButton)findViewById(R.id.Inapoi);
        Left = (ImageButton)findViewById(R.id.Stanga);
        Right = (ImageButton)findViewById(R.id.Dreapta);

        //commands to be sent to bluetooth
        Forward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                moveForward();      //method to turn on
            }
        });

        Backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                stop();
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                moveLeft();
            }
        });

        Right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                moveRight();
            }
        });


    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void stop()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void moveLeft(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("2".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void moveRight(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("3".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void moveForward()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public void doStop(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
}
