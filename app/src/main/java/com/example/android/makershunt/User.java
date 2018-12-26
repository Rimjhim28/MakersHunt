package com.example.android.makershunt;

public class User {

    String mail; Task task; int points;

    public User(Task task, String mail, int points){
        this.task = task;
        this.mail = mail;
    }

    public Task getTask(){
        return task;
    }

    public String getMail(){
        return mail;
    }
}
