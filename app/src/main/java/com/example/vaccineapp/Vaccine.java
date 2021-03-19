package com.example.vaccineapp;

public class Vaccine {
    private String place;
    private String data;

    // deafultiv
    public Vaccine()
    {
        place = "";
        data = "";
    }
    public Vaccine(String place,String data)
    {
        this.place = place;
        this.data = data;
    }

    // geters
    public String getPlace()
    {
        return place;
    }

    // geters
    public String getData()
    {
        return data;
    }

    // setters
    public void setPlace(String place)
    {
        this.place = place;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}
