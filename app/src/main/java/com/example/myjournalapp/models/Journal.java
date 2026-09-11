package com.example.myjournalapp.models;

public class Journal {

    private String name;
    private String id;
    private String date;
    private String description;
    private String title;
    private String email;

    public Journal(String name, String id, String date, String description, String title, String email) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.description = description;
        this.title = title;
        this.email = email;
    }

    public Journal(){}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}