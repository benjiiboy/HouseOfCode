package com.example.benjamin_pc.houseofcode;

import android.graphics.Bitmap;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

public class ChatMessage {

    private String Name;
    private String Created;
    private String Text;
    private Bitmap Photo;

    public ChatMessage(){

    }

    public ChatMessage(String name, String date, String text){
        this.Name = name;
        this.Created = date;
        this.Text = text;
    }

    public String getName(){return Name;}

    public void setName(String name){this.Name = name;}

    public String getDate(){return Created;}

    public  void  setDate(String date){this.Created = date;}

    public String getText(){return Text;}

    public void setText(String text){this.Text = text;}



}
