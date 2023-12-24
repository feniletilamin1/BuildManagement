package com.example.buildmanagement.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.Models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    final SQLiteDatabase database = DbHelper.database;

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();

        Cursor cursor = database.query("employees", null, null, null, null, null, null);

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

            do {

                employees.add(new Employee(cursor.getInt(idIndex),
                        cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                        cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                        cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return employees;
    }

    @Override
    public Employee findOneById(int id) {
        Employee employee = null;

        Cursor cursor = database.query("employees",
            null,
            "_id = ?",
            new String[] {String.valueOf(id)},
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

            employee = new Employee(cursor.getInt(idIndex),
                    cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                    cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                    cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                    cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex));
        }

        cursor.close();

        return employee;
    }

    @Override
    public int save(Employee employee) {;

        ContentValues contentValues = fillContentValue(employee);

        int newId = (int)database.insert("employees", null, contentValues);

        return newId;
    }

    @Override
    public int update(Employee employee) {
        int countUpdatedRows;

        ContentValues updatedValues = fillContentValue(employee);

        countUpdatedRows = database.update("employees", updatedValues,
                "_id=" + employee.getId(), null);
        return countUpdatedRows;
    }

    @Override
    public int delete(int id) {
        int countDeletedRows;

        countDeletedRows = database.delete("employees","_id=" + id, null);

        return countDeletedRows;
    }

    @Override
    public List<Employee> getForemanList() {
        List<Employee> employeeList = new ArrayList<>();

        Cursor cursor = database.query("employees",
                null,
                "workerStatus = ? AND post = ?",
                new String[] {"Свободен", "Прораб"},
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

            do {
                employeeList.add(new Employee(cursor.getInt(idIndex),
                        cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                        cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                        cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return employeeList;
    }

    public List<Employee> getObjectEmployees(int objectId) {
        List<Employee> employees = new ArrayList<>();

        Cursor cursor = database.query("employees",
                null,
                "buildObjectId = ? AND post = ?",
                new String[] {String.valueOf(objectId), "Рабочий"},
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

            do {

                employees.add(new Employee(cursor.getInt(idIndex),
                        cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                        cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                        cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return employees;
    }

    public List<Employee> getFreeEmployees() {
        List<Employee> employees = new ArrayList<>();

        Cursor cursor = database.query("employees",
                null,
                "workerStatus = ? AND post = ?",
                new String[] {"Свободен", "Рабочий"},
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

            do {

                employees.add(new Employee(cursor.getInt(idIndex),
                        cursor.getInt(buildObjectIndex), cursor.getString(firstNameIndex),
                        cursor.getString(lastNameIndex), cursor.getString(middleNameIndex),
                        cursor.getString(phoneIndex), cursor.getString(workerStatusIndex),
                        cursor.getString(specialityIndex), cursor.getString(postIndex), cursor.getString(photoIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return employees;
    }

    private ContentValues fillContentValue(Employee employee) {
        ContentValues contentValues = new ContentValues();

        if(employee.getBuildObjectId() != null){
            contentValues.put("buildObjectId", employee.getBuildObjectId());
        }
        contentValues.put("firstName", employee.getFirstName());
        contentValues.put("lastName", employee.getLastName());
        contentValues.put("middleName", employee.getMiddleName());
        contentValues.put("phone", employee.getPhone());
        contentValues.put("workerStatus", employee.getWorkerStatus());
        contentValues.put("speciality", employee.getSpeciality());
        contentValues.put("post", employee.getPost());
        contentValues.put("photo", employee.getPhoto());

        return contentValues;
    }
}
