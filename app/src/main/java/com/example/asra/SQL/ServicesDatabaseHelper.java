package com.example.asra.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.asra.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServicesDatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ServicesManager.db";

    // User table name
    private static final String TABLE_SERVICE = "services";

    // User Table Columns names
    private static final String COLUMN_SERVICE_ID = "service_id";
    private static final String COLUMN_SERVICE_NAME = "service_name";
    private static final String COLUMN_SERVICE_DESCRIPTION = "service_description";
    private static final String COLUMN_SERVICE_PRICE = "service_price";

    // create table sql query
    private final String CREATE_SERVICE_TABLE = "CREATE TABLE " + TABLE_SERVICE + "("
            + COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SERVICE_NAME + " TEXT,"
            + COLUMN_SERVICE_DESCRIPTION + " TEXT," + COLUMN_SERVICE_PRICE + " INTEGER" + ")";

    // drop table sql query
    private final String DROP_SERVICE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SERVICE;

    public ServicesDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SERVICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Drop User Table if exist
        db.execSQL(DROP_SERVICE_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addService(Services service) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_NAME, service.getName());
        values.put(COLUMN_SERVICE_DESCRIPTION, service.getDescription());
        values.put(COLUMN_SERVICE_PRICE, service.getPrice());

        // Inserting Row
        db.insert(TABLE_SERVICE, null, values);
        db.close();
    }

    public List<Services> getAllServices() {
        String[] columns = {
                COLUMN_SERVICE_ID,
                COLUMN_SERVICE_NAME,
                COLUMN_SERVICE_DESCRIPTION,
                COLUMN_SERVICE_PRICE
        };
        // sorting orders
        String sortOrder =
                COLUMN_SERVICE_NAME + " ASC";
        List<Services> listServices = new ArrayList<Services>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            // query the user table
            cursor = db.query(TABLE_SERVICE, //Table to query
                    columns,    //columns to return
                    null,        //columns for the WHERE clause
                    null,        //The values for the WHERE clause
                    null,       //group the rows
                    null,       //filter by row groups
                    sortOrder); //The sort order
            if (cursor.moveToFirst()) {
                do {
                    Services service = new Services();
                    service.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID))));
                    service.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_NAME)));
                    service.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_DESCRIPTION)));
                    service.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_PRICE)));
                    // Adding user record to list
                    listServices.add(service);
                } while (cursor.moveToNext());
            }
        }

        return listServices;

        // Traversing through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Services service = new Services();
//                service.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID))));
//                service.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_NAME)));
//                service.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_DESCRIPTION)));
//                service.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_PRICE)));
//                // Adding user record to list
//                userList.add(service);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        // return user list
//        return userList;
    }
}
