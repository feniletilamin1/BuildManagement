package com.example.buildmanagement.Repositories;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.Helpers.DateFormatter;
import com.example.buildmanagement.Models.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    final SQLiteDatabase database = DbHelper.database;

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        Cursor cursor = database.query("orders", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int dateAddIndex = cursor.getColumnIndex("createdDate");
            int nameIndex = cursor.getColumnIndex("name");
            int priceIndex = cursor.getColumnIndex("price");
            do {

                orders.add(new Order(cursor.getInt(idIndex), DateFormatter.stringToDate(cursor.getString(dateAddIndex)), cursor.getString(nameIndex), BigDecimal.valueOf(cursor.getDouble(priceIndex))));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return orders;
    }

    @Override
    public int save(Order order) {
        ContentValues contentValues = fillContentValue(order);

        int newId = (int)database.insert("orders", null, contentValues);

        return newId;
    }

    @Override
    public int update(Order order) {
        int countUpdatedRows;

        ContentValues updatedValues = fillContentValue(order);

        countUpdatedRows = database.update("orders", updatedValues, "_id=" + order.getId(), null);

        return countUpdatedRows;
    }

    @Override
    public int delete(int id) {
        int countDeletedRows;

        countDeletedRows = database.delete("orders","_id=" + id, null);

        return countDeletedRows;
    }

    @Override
    public Order findOneById(int id) {
        Order order = null;

        Cursor cursor = database.query("orders",
                null,
                "_id = ?",
                new String[] {String.valueOf(id)},
                null, null, null);

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int dateAddIndex = cursor.getColumnIndex("createdDate");
            int nameIndex = cursor.getColumnIndex("name");
            int priceIndex = cursor.getColumnIndex("price");
            Calendar date = DateFormatter.stringToDate(cursor.getString(dateAddIndex));

            order = new Order(cursor.getInt(idIndex), date, cursor.getString(nameIndex), BigDecimal.valueOf(cursor.getDouble(priceIndex)));
        }

        cursor.close();

        return order;
    }

    private ContentValues fillContentValue(Order order) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", order.getName());
        contentValues.put("createdDate", new Date().getTime());
        contentValues.put("price", order.getPrice().doubleValue());

        return contentValues;
    }
}

