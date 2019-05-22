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
    public static final String SALON_1 = "S";
    public static final String SALON_2 = "D";
    public static final String SALON_3 = "AS";
    public static final String SALON_4 = "AD";
    public static final String SALON_5 = "AAD";
    public static final String FILIPESCU = SALON_1 + "D";
    public static final String POPESCU = SALON_3 + "AS";
    public static final String IONESCU = SALON_3+"S";
    //char[] path =new char[10];

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

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void doSendCommandPop(View view) {
        //Testarea comenzii pentru un pacient fictiv

        String path[]=new String[POPESCU.length()];

        if (btSocket!=null)
        {
            try
            {
                path= POPESCU.split("");
               // path=POPESCU.toCharArray();
                for(int i=0;i<path.length;i++){
                btSocket.getOutputStream().write(path[i].toString().getBytes());
                }
                btSocket.getOutputStream().write("X".toString().getBytes());
//
//                for(char cmd:path){
//                    btSocket.getOutputStream().write(cmd.);
//                }
            }
            catch (IOException e)
            {
                msg("A ESUAT");
            }finally {
                path[0]="\0";
            }
        }
    }

    public void doSendCommandFilip(View view) {
        String path[]=new String[FILIPESCU.length()];
        if (btSocket!=null)
        {
            try
            {
                 path=FILIPESCU.split("");
                for(int i=0;i<path.length;i++){
                    btSocket.getOutputStream().write(path[i].toString().getBytes());
                }
                btSocket.getOutputStream().write("X".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("A ESUAT");
            }
        finally {
            path[0]="\0";
        }

        }
    }

    public void doSendCommandIon(View view) {
        String path[]=new String[IONESCU.length()];
        if (btSocket!=null)
        {
            try
            {
                path=IONESCU.split("");
                for(int i=0;i<path.length;i++){
                    btSocket.getOutputStream().write(path[i].toString().getBytes());
                }
                btSocket.getOutputStream().write("X".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("A ESUAT");
            }
            finally {
                path[0]="\0";
            }
        }
    }

    public void doSendCommandLast(View view) {
        if (btSocket!=null)
        {
            try
            {
                String path[]=SALON_5.split("");
                for(int i=0;i<path.length;i++){
                    btSocket.getOutputStream().write(path[i].toString().getBytes());
                }
                btSocket.getOutputStream().write("X".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("A ESUAT");
            }
        }
    }

    public void doChangeControl(View view) {
        if(btSocket!=null){
            {
                try
                {
                    btSocket.getOutputStream().write("Z".toString().getBytes()); //close connection
                }
                catch (IOException e)
                { msg("Error");}
            }

        }
    }

    public void doSendAnotherCommand(View view) {
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
                        try {
                            PreparedStatement statement = con.prepareStatement(query);
                            statement.setString(1, "mesajasdsa");

                            statement.execute();

                            con.close();
                            msg("Succes");
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
