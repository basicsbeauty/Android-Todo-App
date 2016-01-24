package com.example.briankwon21.simpletodo;

import java.util.Calendar;

public class TodoItem {
    private long id;
    private String text;
    private int year;
    private int month;
    private int day;
    private Calendar calendar = Calendar.getInstance();

    public TodoItem(String text) {
        this.text = text;
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public TodoItem() {
        this("");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStrDueDate() {
        return (month+1) + "/" + day + "/" + year;
    }

}
