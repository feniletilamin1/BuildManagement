package com.example.buildmanagement.Models;

public class Employee {
    int id;
    Integer buildObjectId;
    String firstName;
    String lastName;
    String middleName;
    String phone;
    String workerStatus;
    String speciality;
    String post;
    String photo;


    public Employee(int id, Integer buildObjectId, String firstName, String lastName, String middleName, String phone, String workerStatus, String speciality, String post, String photo) {
        this.id = id;
        this.buildObjectId = buildObjectId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.workerStatus = workerStatus;
        this.speciality = speciality;
        this.post = post;
        this.photo = photo;
    }

    public Employee(Integer buildObjectId, String firstName, String lastName, String middleName, String phone, String workerStatus, String speciality, String post, String photo) {
        this.buildObjectId = buildObjectId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.workerStatus = workerStatus;
        this.speciality = speciality;
        this.post = post;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBuildObjectId() {
        return buildObjectId;
    }

    public void setBuildObjectId(Integer buildObjectId) {
        this.buildObjectId = buildObjectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(String workerStatus) {
        this.workerStatus = workerStatus;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public String getPost() {
        return post;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
