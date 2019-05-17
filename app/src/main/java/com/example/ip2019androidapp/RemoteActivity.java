package com.example.ip2019androidapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class RemoteActivity extends AppCompatActivity {
    BluetoothSocket btSocket = DeviceList.btSocket;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);


    }

    public void doVaruAutomat(View view) {
        new CheckLogin().execute();
    }

    public void Report()
    {

    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";

        @Override
        protected String doInBackground(String... strings) {
                con = connectionCreate(MainActivity.DB_USERNAME, MainActivity.DB_PASSWORD, MainActivity.DB_NAME, MainActivity.DB_IP);
                if (con == null) {
                    z = "Check your internet";
                } else{
                    // the mysql insert statement
                    String query = "insert into WARNINGS (MESAJ)"
                            + " values (?)";
                    ;
                        try {
                            PreparedStatement statement = con.prepareStatement(query);
                            statement.setString(1, "mesajasdsa");

                            statement.execute();

                            con.close();
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(RemoteActivity.this, s, Toast.LENGTH_SHORT).show();

        }
    }

    public Connection connectionCreate(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            ConnectionURL = "jdbc:mysql://" + server + ":3306/" + database;
            connection = DriverManager.getConnection(ConnectionURL, user, password);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());

        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
