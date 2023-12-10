package com.example.buildmanagement.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.buildmanagement.DataBase.DbHelper;
import com.example.buildmanagement.Models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    final SQLiteDatabase database = DbHelper.database;
    /**
     * Возвращает список всех клиентов.
     *
     * @return список всех клиентов
     */
    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        Cursor cursor = database.query("clients", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int firstNameIndex = cursor.getColumnIndex("firstName");
            int lastNameIndex = cursor.getColumnIndex("lastName");
            int middleNameIndex = cursor.getColumnIndex("middleName");
            int phoneIndex = cursor.getColumnIndex("phone");
            int photoIndex = cursor.getColumnIndex("photo");
            do {

                clients.add(new Client(cursor.getInt(idIndex),
                        cursor.getString(firstNameIndex), cursor.getString(lastNameIndex),
                        cursor.getString(middleNameIndex), cursor.getString(phoneIndex), cursor.getString(photoIndex)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return clients;
    }

    /**
     * Возвращает клиента по указанному идентификатору.
     *
     * @param id идентификатор клиента
     * @return клиент
     */
    @Override
    public Client findOneById(int id) {
        Client client = null;

        Cursor cursor = database.query("clients",
                null,
                "_id = ?",
                new String[] {String.valueOf(id)},
                null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int firstNameIndex = cursor.getColumnIndex("firstName");
            int lastNameIndex = cursor.getColumnIndex("lastName");
            int middleNameIndex = cursor.getColumnIndex("middleName");
            int phoneIndex = cursor.getColumnIndex("phone");
            int photoIndex = cursor.getColumnIndex("photo");

            client = new Client(cursor.getInt(idIndex),
                    cursor.getString(firstNameIndex), cursor.getString(lastNameIndex),
                    cursor.getString(middleNameIndex), cursor.getString(phoneIndex), cursor.getString(photoIndex));
        }

        cursor.close();

        return client;
    }

    /**
     * Сохраняет клиента в базе данных.
     *
     * @param client клиент для сохранения
     * @return количество затронутых строк
     */
    @Override
    public int save(Client client) {
        ContentValues contentValues = fillContentValue(client);

        int newId = (int)database.insert("clients", null, contentValues);

        return newId;
    }

    /**
     * Обновляет данные клиента в базе данных.
     *
     * @param client клиент для обновления
     * @return количество обновленных строк
     */
    @Override
    public int update(Client client) {
        int countUpdatedRows;

        ContentValues updatedValues = fillContentValue(client);

        countUpdatedRows = database.update("clients", updatedValues,
                "_id=" + client.getId(), null);

        return countUpdatedRows;
    }

    /**
     * Удаляет клиента из базы данных по указанному идентификатору.
     *
     * @param id идентификатор клиента для удаления
     * @return количество удаленных строк
     */
    @Override
    public int delete(int id) {
        int countDeletedRows;
        countDeletedRows = database.delete("clients","_id=" + id, null);

        return countDeletedRows;
    }

    private ContentValues fillContentValue(Client client) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("firstName", client.getFirstName());
        contentValues.put("lastName", client.getLastName());
        contentValues.put("middleName", client.getMiddleName());
        contentValues.put("phone", client.getPhone());
        contentValues.put("photo", client.getPhoto());

        return contentValues;
    }

}
