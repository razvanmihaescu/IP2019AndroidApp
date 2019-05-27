package com.example.ip2019androidapp;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoteActivity extends AppCompatActivity {
    BluetoothSocket btSocket = DeviceList.btSocket;
    Connection con;
    public static List<Pacient> LISTA_PACIENTI=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LISTA_PACIENTI.clear();
        setContentView(R.layout.comenzi_scrollview);
        new testComenzi().execute();
    }

    public void Generare_Comenzi(final Pacient p)
    {

        LinearLayout Layout_comenzi = (LinearLayout)findViewById(R.id.llayout_comenzi);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Button b= new Button(this);
        b.setText(p.getNume());
        b.setId(p.getId());
        b.setLayoutParams(lparams);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSencCommandPacient(p.getCale());
                Toast.makeText(getApplicationContext(), p.getCale(), Toast.LENGTH_SHORT).show();
            }
        });
        Layout_comenzi.addView(b);
    }

    public void doVaruAutomat(View view) {
        new testInsert().execute();
    }

    public void Report()
    {

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    public void doSencCommandPacient(String cale)
    {
        String path[]=new String[cale.length()];

        if (btSocket!=null)
        {
            try
            {   path=cale.split("");
                for(int i=0;i<path.length;i++){
                    btSocket.getOutputStream().write(path[i].toString().getBytes());
                }
                btSocket.getOutputStream().write("X".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("A ESUAT");
            }finally {
                path[0]="\0";
            }
        }
    }

    public class testComenzi extends AsyncTask<String, String, String> {
        String z = "";

        @Override
        protected String doInBackground(String... strings) {
            con = connectionCreate(MainActivity.DB_USERNAME, MainActivity.DB_PASSWORD, MainActivity.DB_NAME, MainActivity.DB_IP);
            if (con == null) {
                z = "Check your internet";
            } else{
                String query = "select * from COMENZI";
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {

                        String nume_pacient=rs.getString(4);
                        String cale=rs.getString(2);
                        String medicament=rs.getString(3);

                        LISTA_PACIENTI.add(new Pacient(nume_pacient,cale,medicament));
                    }
                    if(LISTA_PACIENTI.isEmpty()) {
                        z = "Nu exista nicio comanda !";
                    }
                    z="Comenzile au fost afisate cu succes!";
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(RemoteActivity.this, s, Toast.LENGTH_SHORT).show();
            for (Pacient p :LISTA_PACIENTI)
            {
                Generare_Comenzi(p);
            }
        }
    }

    public class testInsert extends AsyncTask<String, String, String> {
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
                            z="Succes";
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
