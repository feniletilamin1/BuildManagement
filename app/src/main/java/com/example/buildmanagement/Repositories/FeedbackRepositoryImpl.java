package com.example.buildmanagement.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.Models.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackRepositoryImpl implements FeedbackRepository{

    final SQLiteDatabase database = DbHelper.database;

    @Override
    public List<Feedback> findAllById(int id) {
        List<Feedback> feedbacks = new ArrayList<>();

        Cursor cursor = database.query("feedbacks",
                null,
                "employeeId = ?",
                new String[] {String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int textIndex = cursor.getColumnIndex("text");
            int scoreIndex = cursor.getColumnIndex("score");
            int employeeIdIndex = cursor.getColumnIndex("employeeId");
            do {
                feedbacks.add(new Feedback(cursor.getInt(idIndex), cursor.getString(textIndex), cursor.getInt(scoreIndex), cursor.getInt(employeeIdIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return feedbacks;
    }

    @Override
    public Feedback findOneById(int id) {
        Feedback feedback = null;

        Cursor cursor = database.query("feedbacks",
                null,
                "_id = ?",
                new String[] {String.valueOf(id)},
                null, null, null);

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int textIndex = cursor.getColumnIndex("text");
            int scoreIndex = cursor.getColumnIndex("score");
            int employeeIdIndex = cursor.getColumnIndex("employeeId");
            feedback = new Feedback(cursor.getInt(idIndex), cursor.getString(textIndex), cursor.getInt(scoreIndex), cursor.getInt(employeeIdIndex));
        }

        cursor.close();

        return feedback;
    }

    @Override
    public int save(Feedback feedback) {
        ContentValues contentValues = fillContentValue(feedback);

        int newId = (int)database.insert("feedbacks", null, contentValues);

        return newId;
    }

    @Override
    public int delete(int id) {
        int countDeletedRows;

        countDeletedRows = database.delete("feedbacks","_id=" + id, null);

        return countDeletedRows;
    }

    private ContentValues fillContentValue(Feedback feedback) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("text", feedback.getText());
        contentValues.put("score", feedback.getScore());
        contentValues.put("employeeId", feedback.getEmployeeId());

        return contentValues;
    }
}
