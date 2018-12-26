package com.example.android.makershunt;

public class Task {

    String title, description, link;
    int points;

    public Task(){}
    public Task(String title, String description, String link, int points){
        this.title = title;
        this.description = description;
        this.link = link;
        this.points = points;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLink(){
        return link;
    }

    public int getPoints() {
        return points;
    }
}
