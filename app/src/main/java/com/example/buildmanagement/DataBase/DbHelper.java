package com.example.buildmanagement.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "buildManagementDb";

    public static SQLiteDatabase database;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE buildObjects (_id INTEGER PRIMARY KEY AUTOINCREMENT, startDate TEXT NOT NULL, endDate TEXT NOT NULL, status TEXT NOT NULL, objectName TEXT NOT NULL, objectCategory TEXT NOT NULL, price REAL NOT NULL, phone TEXT NOT NULL, indexRegion INTEGER NOT NULL, region TEXT NOT NULL, city TEXT NOT NULL, street TEXT NOT NULL, home TEXT NOT NULL, flat INTEGER, finishDate TEXT, foremanId INTEGER NOT NULL, clientId INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE clients (_id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT NOT NULL, lastName TEXT NOT NULL, middleName TEXT, phone TEXT NOT NULL, photo TEXT NOT NULL);");
        db.execSQL("CREATE TABLE employees (_id INTEGER PRIMARY KEY AUTOINCREMENT, buildObjectId INTEGER, firstName TEXT NOT NULL, lastName TEXT NOT NULL, middleName TEXT, phone TEXT NOT NULL, workerStatus TEXT NOT NULL, speciality TEXT NOT NULL, post TEXT NOT NULL, photo TEXT NOT NULL);");
        db.execSQL("CREATE TABLE orders (_id INTEGER PRIMARY KEY AUTOINCREMENT, createdDate TEXT NOT NULL, name TEXT NOT NULL, price REAL NOT NULL);");
        db.execSQL("CREATE TABLE objectsPhotos (_id INTEGER PRIMARY KEY AUTOINCREMENT, objectId INTEGER NOT NULL, photoPath TEXT NOT NULL);");
        db.execSQL("CREATE TABLE feedbacks (_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT, score INTEGER NOT NULL, employeeId INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists buildObjects");
        db.execSQL("drop table if exists clients");
        db.execSQL("drop table if exists employees");
        db.execSQL("drop table if exists orders");
        onCreate(db);
    }
}
