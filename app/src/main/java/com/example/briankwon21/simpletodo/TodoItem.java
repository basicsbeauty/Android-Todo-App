package com.example.briankwon21.simpletodo;

public class TodoItem {
    private long id;
    private String text;

    public TodoItem() {
        this.text = "";
    }

    public TodoItem(String text) {
        this.text = text;
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
}
