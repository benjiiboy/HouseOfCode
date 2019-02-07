package com.example.benjamin_pc.houseofcode;

public class ChatRoom {


    private String Name;
    private String Description;

    public ChatRoom(){

    }

    public ChatRoom( String name,String desc){
        this.Name = name;
        this.Description = desc;

    }

    public String getName(){return Name;}

    public void setName(String name){this.Name = name;}

    public String getDescription(){return Description;}

    public void setDescription(String description){this.Description = description;}
}
