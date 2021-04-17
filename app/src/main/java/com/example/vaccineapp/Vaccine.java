package com.example.vaccineapp;

/**
 * The Students activity.
 *
 *  @author Ori Ofek <oriofek106@gmail.com> 17/04/2021
 *  @version 1.0
 *  @since 17/04/2021
 *  sort description:
 *  this is the activty the implement the exercise that my teacher gave and in this class there is a vaccine
 */
public class Vaccine {
    private String place;
    private String data;
    private boolean occured;

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
        this.occured = true;

        if (data.equals("NOT TAKEN"))
        {
            occured = false;
            this.place = "NOT TAKEN";
        }
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

    // geters
    public boolean getOccur()
    {
        return occured;
    }

    // setters
    public void setOccur(Boolean b)
    {
        this.occured = b;
    }

    // setters
    public void setPlace(String place)
    {
        this.place = place;
    }

    public void setData(String data)
    {

        this.data = data;
        occured = true;
        if (data.equals("NOT TAKEN"))
        {
            occured = false;
            this.place = "NOT TAKEN";
        }
    }
}
