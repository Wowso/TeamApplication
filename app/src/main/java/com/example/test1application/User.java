package com.example.test1application;

public class User{
    public String username;
    public String email;
    public  String acc;
    public  String fsr;
    public  String vec;
    public String mus;
    public int  id;
    public String  tag;

    public User(){

    }

    public  User(String username ,String email)
    {
        this.username = username;
        this.email = email;
    }

    public  User(String acc, String fsr, String vec)
    {
        this.acc = acc;
        this.fsr = fsr;
        this.vec = vec;
    }

    public  User(String tag)
    {
        this.tag = tag;
    }

    public  User(int id,String acc, String fsr, String vec)
    {
        this.id = id;
        this.acc = acc;
        this.fsr = fsr;
        this.vec = vec;
    }

}
