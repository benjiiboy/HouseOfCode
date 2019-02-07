package com.example.benjamin_pc.houseofcode.Models;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class ChatMessage {

    private String name;
    private String date;
    private String text;
    private Bitmap Photo;

    public ChatMessage(){

    }

    public ChatMessage(String name, String date, String text){
        this.name = name;
        this.date = date;
        this.text = text;
    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getDate(){return date;}

    public  void  setDate(String date){this.date = date;}

    public String getText(){return text;}

    public void setText(String text){this.text = text;}



}
