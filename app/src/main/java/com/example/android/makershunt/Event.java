package com.example.android.makershunt;

public class Event {
    String week, startDate, endDate;

    Event(String week, String startDate, String endDate){
        this.week = week;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    String getWeek(){
        return week;
    }

    String getStartDate(){
        return startDate;
    }

    String getEndDate(){
        return endDate;
    }
}
