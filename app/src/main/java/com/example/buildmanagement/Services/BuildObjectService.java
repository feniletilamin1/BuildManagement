package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.ObjectPhoto;

import java.util.List;

public interface BuildObjectService {
    List<BuildObject> findAll();
    BuildObject findOneById(int id);
    int create(BuildObject buildObject);
    void update(BuildObject buildObject);
    void delete(int id);
    void addEmployeeOnObject(int objectId, int empId);
    void removeEmployeeFromObject(int empId);
    long addObjectPhoto(ObjectPhoto objectPhoto);
    List<ObjectPhoto> getObjectPhotos(int objectId);
    void deletePhoto(int photoId);
    void finishWork(int objectId);
}
