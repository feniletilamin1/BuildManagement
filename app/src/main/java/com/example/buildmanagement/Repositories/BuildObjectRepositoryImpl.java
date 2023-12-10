package com.example.buildmanagement.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.Helpers.DateFormatter;
import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.Models.ObjectPhoto;
import com.example.buildmanagement.Services.EmployeeService;
import com.example.buildmanagement.Services.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuildObjectRepositoryImpl implements BuildObjectRepository{

    final SQLiteDatabase database = DbHelper.database;
    final EmployeeService employeeService = new EmployeeServiceImpl();

    @Override
    public List<BuildObject> findAll() {
        List<BuildObject> buildObjects = new ArrayList();

        Cursor cursor = database.query("buildObjects", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int startDateIndex = cursor.getColumnIndex("startDate");
            int endDateIndex = cursor.getColumnIndex("endDate");
            int statusIndex = cursor.getColumnIndex("status");
            int objectNameIndex = cursor.getColumnIndex("objectName");
            int objectCategoryIndex = cursor.getColumnIndex("objectCategory");
            int priceIndex = cursor.getColumnIndex("price");
            int phoneIndex = cursor.getColumnIndex("phone");
            int indexIndex = cursor.getColumnIndex("indexRegion");
            int regionIndex = cursor.getColumnIndex("region");
            int cityIndex = cursor.getColumnIndex("city");
            int streetIndex = cursor.getColumnIndex("street");
            int homeIndex = cursor.getColumnIndex("home");
            int flatIndex = cursor.getColumnIndex("flat");
            int finishDateIndex = cursor.getColumnIndex("finishDate");
            int clientIdIndex = cursor.getColumnIndex("clientId");
            int foremanIdIndex = cursor.getColumnIndex("foremanId");

            do {

                buildObjects.add(new BuildObject(cursor.getInt(idIndex),
                        DateFormatter.stringToDate(cursor.getString(startDateIndex)),
                        DateFormatter.stringToDate(cursor.getString(endDateIndex)),
                        cursor.getString(statusIndex), cursor.getString(objectNameIndex),
                        cursor.getString(objectCategoryIndex), BigDecimal.valueOf(cursor.getDouble(priceIndex)),
                        cursor.getString(phoneIndex), cursor.getInt(indexIndex),
                        cursor.getString(regionIndex), cursor.getString(cityIndex),
                        cursor.getString(streetIndex), cursor.getString(homeIndex),
                        cursor.getInt(flatIndex), DateFormatter.stringToDate(cursor.getString(finishDateIndex)),
                        cursor.getInt(clientIdIndex), cursor.getInt(foremanIdIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return buildObjects;
    }

    @Override
    public BuildObject findOneById(int id) {
        BuildObject buildObject = null;

        Cursor cursor = database.query("buildObjects",
                null,
                "_id = ?",
                new String[] {String.valueOf(id)},
                null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int startDateIndex = cursor.getColumnIndex("startDate");
            int endDateIndex = cursor.getColumnIndex("endDate");
            int statusIndex = cursor.getColumnIndex("status");
            int objectNameIndex = cursor.getColumnIndex("objectName");
            int objectCategoryIndex = cursor.getColumnIndex("objectCategory");
            int priceIndex = cursor.getColumnIndex("price");
            int phoneIndex = cursor.getColumnIndex("phone");
            int indexIndex = cursor.getColumnIndex("indexRegion");
            int regionIndex = cursor.getColumnIndex("region");
            int cityIndex = cursor.getColumnIndex("city");
            int streetIndex = cursor.getColumnIndex("street");
            int homeIndex = cursor.getColumnIndex("home");
            int flatIndex = cursor.getColumnIndex("flat");
            int finishDateIndex = cursor.getColumnIndex("finishDate");
            int clientIdIndex = cursor.getColumnIndex("clientId");
            int foremanIdIndex = cursor.getColumnIndex("foremanId");

            buildObject = new BuildObject(cursor.getInt(idIndex),
                    DateFormatter.stringToDate(cursor.getString(startDateIndex)),
                    DateFormatter.stringToDate(cursor.getString(endDateIndex)),
                    cursor.getString(statusIndex), cursor.getString(objectNameIndex),
                    cursor.getString(objectCategoryIndex), BigDecimal.valueOf(cursor.getDouble(priceIndex)),
                    cursor.getString(phoneIndex), cursor.getInt(indexIndex),
                    cursor.getString(regionIndex), cursor.getString(cityIndex),
                    cursor.getString(streetIndex), cursor.getString(homeIndex),
                    cursor.getInt(flatIndex), DateFormatter.stringToDate(cursor.getString(finishDateIndex)),
                    cursor.getInt(clientIdIndex), cursor.getInt(foremanIdIndex));
        }

        cursor.close();

        return buildObject;
    }

    @Override
    public int save(BuildObject buildObject) {

        ContentValues contentValues = fillContentValue(buildObject);

        int newId = (int)database.insert("buildObjects", null, contentValues);

        return newId;
    }

    @Override
    public int update(BuildObject buildObject) {
        int countUpdatedRows;

        ContentValues updatedValues = fillContentValue(buildObject);

        countUpdatedRows = database.update("buildObjects", updatedValues,
                "_id=" + buildObject.getId(), null);

        return countUpdatedRows;
    }

    @Override
    public int delete(int id) {
        int countDeletedRows;

        List<Employee> employees = employeeService.getObjectEmployees(id);

        for (Employee employee: employees) {
             removeEmployeeFromObject(employee.getId());
        }

        Employee foreman = getForeman(id);

        if(foreman != null) {
            removeEmployeeFromObject(foreman.getId());
        }

        countDeletedRows = database.delete("buildObjects","_id=" + id, null);

        return countDeletedRows;
    }

    public void addEmployeeOnObject(int objectId, int empId) {
        Employee employee = employeeService.findOneById(empId);
        employee.setBuildObjectId(objectId);
        employee.setWorkerStatus("Занят");

        employeeService.update(employee);
    }

    public void removeEmployeeFromObject(int empId) {
        Employee employee = employeeService.findOneById(empId);

        employee.setBuildObjectId(0);
        employee.setWorkerStatus("Свободен");

        employeeService.update(employee);
    }

    @Override
    public int addObjectPhoto(ObjectPhoto objectPhoto) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("objectId", objectPhoto.getObjectId());
        contentValues.put("photoPath", objectPhoto.getPhotoPath());

        int newId = (int)database.insert("objectsPhotos", null, contentValues);

        return newId;
    }

    @Override
    public List<ObjectPhoto> getObjectPhotos(int objectId) {
        List<ObjectPhoto> objectPhotos = new ArrayList<>();

        Cursor cursor = database.query("objectsPhotos",
                null,
                "objectId = ?",
                new String[] {String.valueOf(objectId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int photoPathIndex = cursor.getColumnIndex("photoPath");

            do {
                objectPhotos.add(new ObjectPhoto(cursor.getInt(idIndex), objectId, cursor.getString(photoPathIndex)));
            } while (cursor.moveToNext());
        }

        return objectPhotos;
    }

    @Override
    public int deletePhoto(int photoId) {
        int countDeletedRows;

        countDeletedRows = database.delete("objectsPhotos","_id=" + photoId, null);

        return countDeletedRows;

    }

    @Override
    public void finishWork(int objectId) {
        BuildObject buildObject = findOneById(objectId);
        buildObject.setStatus("Завершён");
        buildObject.setFinishDate(Calendar.getInstance());
        update(buildObject);

        List<Employee> employees = employeeService.getObjectEmployees(objectId);

        for (Employee employee: employees) {
            removeEmployeeFromObject(employee.getId());
        }

        Employee foreman = getForeman(objectId);

        removeEmployeeFromObject(foreman.getId());
    }

    private Employee getForeman(int objectId) {
        Employee employee = null;

        Cursor cursor = database.query("employees",
                null,
                "buildObjectId = ?",
                new String[] {String.valueOf(objectId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int buildObjectIndex = cursor.getColumnIndex("buildObjectId");
            int firstNameIndex = cursor.getColumnIndex("firstName");
            int lastNameIndex = cursor.getColumnIndex("lastName");
            int middleNameIndex = cursor.getColumnIndex("middleName");
            int phoneIndex = cursor.getColumnIndex("phone");
            int workerStatusIndex = cursor.getColumnIndex("workerStatus");
            int specialityIndex = cursor.getColumnIndex("speciality");
            int postIndex = cursor.getColumnIndex("post");
            int photoIndex = cursor.getColumnIndex("photo");

            employee = (new Employee(cursor.getInt(idIndex),
                    cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                    cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                    cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                    cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex)));
        }

        cursor.close();

        return employee;

    }

    private ContentValues fillContentValue(BuildObject buildObject) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("startDate", buildObject.getStartDate().getTime().toString());
        contentValues.put("endDate", buildObject.getEndDate().getTime().toString());
        contentValues.put("status", buildObject.getStatus());
        contentValues.put("objectName", buildObject.getObjectName());
        contentValues.put("objectCategory", buildObject.getObjectCategory());
        contentValues.put("price", String.valueOf(buildObject.getPrice()));
        contentValues.put("phone", buildObject.getPhone());
        contentValues.put("indexRegion", buildObject.getIndexRegion());
        contentValues.put("region", buildObject.getIndexRegion());
        contentValues.put("city", buildObject.getCity());
        contentValues.put("street", buildObject.getStreet());
        contentValues.put("home", buildObject.getHome());
        contentValues.put("flat", buildObject.getFlat());
        if(buildObject.getFinishDate() != null)
            contentValues.put("finishDate", buildObject.getFinishDate().getTime().toString());
        contentValues.put("clientId", buildObject.getClientId());
        contentValues.put("foremanId", buildObject.getForemanId());

        return contentValues;
    }
}
