package com.example.benjamin_pc.houseofcode;

public class ChatRoom {


    private String name;
    private String description;

    public ChatRoom(){

    }

    public ChatRoom( String name,String desc){
        this.name = name;
        this.description = desc;

    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}
}
