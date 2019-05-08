package com.example.ip2019androidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    public static boolean isLogged=false;
    Button btnLogin;
    EditText txtUser,txtPass;

    Connection con;
    String us,pass,db,ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin =  (Button)findViewById(R.id.btnLogin);
        txtUser = (EditText)findViewById(R.id.actLogin_user);
        txtPass = (EditText)findViewById(R.id.actLogin_pass);

//        ip="35.225.197.231";
//        pass="8Barosani";
//        us="echipa";
//        db="ICARE";

        //      localhost
        //From the emulator, 127.0.0.1 refers to the emulator itself - not your local machine. You need to use ip 10.0.2.2, which is bridged to your local machine.
        ip="10.0.2.2";  //
        pass="root";
        us="root";
        db="ICARE";

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";

        @Override
        protected String doInBackground(String... strings) {
            String usernam = txtUser.getText().toString();
            String passw = txtPass.getText().toString();
            if(usernam.trim().equals("")||passw.trim().equals(""))
            {
                z = "Please enter Username and Pass";
            }
            else
            {
                con = connectionCreate(us,pass,db,ip);
                if(con==null)
                {
                    z= "Check your internet";
                }
                else
                {
                    String query = "select * from MEDICI where Username= '"+usernam.toString()+"' and Password='"+passw.toString()+"'";
                    try {
                        Statement stmt=con.createStatement();
                        ResultSet rs= stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z= "Login Successful";
                            isLogged = true;
                            con.close();
                        }
                        else {
                            z="Invalid credentials";
                            isLogged = false;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        //isLogged =false;
                    }

                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            if(isLogged)
            {
                Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, LoggedActivity.class);
                i.putExtra("drName",txtUser.getText().toString());
                startActivity(i);
            }
        }
    }

    public Connection connectionCreate(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            ConnectionURL = "jdbc:mysql://"+server+":3306/"+database;
            connection = DriverManager.getConnection(ConnectionURL,user,password);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : " , se.getMessage());

        } catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
