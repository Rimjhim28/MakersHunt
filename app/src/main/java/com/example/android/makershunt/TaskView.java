package com.example.android.makershunt;

public class TaskView {
    public String description, startDate, endDate;
    public int flag;

    public TaskView(){}

    public TaskView(String description, String startDate, String endDate, int flag){
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getFlag() {
        return flag;
    }
}
