package com.example.eventslist;

public class NoteValues {

    private String title;
    private String description;
    private String date;
    private String location;

    public NoteValues(String text, String description, String date, String location) {
        this.title = text;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public NoteValues() {
        this.title = "Title";
        this.description = "";
        this.date = "";
        this.location = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
