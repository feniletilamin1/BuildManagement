package com.example.buildmanagement.Repositories;

import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.ObjectPhoto;

import java.util.List;

public interface BuildObjectRepository {
    List<BuildObject> findAll();
    BuildObject findOneById(int id);
    int save(BuildObject buildObject);
    int update(BuildObject buildObject);
    int delete(int id);
    void addEmployeeOnObject(int objectId, int empId);
    void removeEmployeeFromObject(int empId);
    int addObjectPhoto(ObjectPhoto objectPhoto);
    List<ObjectPhoto> getObjectPhotos(int objectId);
    int deletePhoto(int photoId);
    void finishWork(int objectId);
}
