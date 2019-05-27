package com.example.ip2019androidapp;

public class Pacient {
    int id;
    String Nume;
    String Cale;
    String Medicament;

    public Pacient(String n,String c, String m)
    {
        this.Cale=c;
        this.Medicament=m;
        this.Nume=n;
    }

    public int getId() { return id; }

    public String getNume() { return Nume; }

    public String getCale() {
        return Cale;
    }

    public String getMedicament() {
        return Medicament;
    }
}
