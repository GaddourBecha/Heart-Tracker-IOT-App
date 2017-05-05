package com.example.gaddour.iotapp;

/**
 * Created by gaddour on 03/05/17.
 */

public class AccessValeur {
    //private variables
    String bat;
    String date;

    public AccessValeur (){

    }
    public AccessValeur (String bat,String date){
        this.bat=bat;
        this.date=date;
    }
    public String getBat(){
        return this.bat;
    }

    // setting id
    public void setBat(String bat){
        this.bat=bat ;
    }

    // getting ID
    public String getDate(){
        return this.date;
    }

    // setting id
    public void setDate(String date){
        this.date=date ;
    }
}
