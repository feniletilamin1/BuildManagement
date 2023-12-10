package com.example.buildmanagement.Models;

public class Feedback {
    int id;
    String text;
    int score;
    int employeeId;

    public Feedback(int id, String text, int score, int employeeId) {
        this.id = id;
        this.text = text;
        this.score = score;
        this.employeeId = employeeId;
    }

    public Feedback(String text, int score, int employeeId) {
        this.text = text;
        this.score = score;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
