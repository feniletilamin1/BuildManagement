package com.example.buildmanagement.Models;

public class ObjectPhoto {
    int id;
    int objectId;
    String photoPath;

    public ObjectPhoto(int id, int objectId, String photoPath) {
        this.id = id;
        this.objectId = objectId;
        this.photoPath = photoPath;
    }

    public ObjectPhoto(int objectId, String photoPath) {
        this.objectId = objectId;
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
