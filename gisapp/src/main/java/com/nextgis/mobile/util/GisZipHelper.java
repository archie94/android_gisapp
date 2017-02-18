package com.nextgis.mobile.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Class to keep track of all gis zip files received
 * Created by arka on 3/2/17.
 */

public class GisZipHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "layers.db";
    private final static String TABLE = "gisZips";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_FILE = "zips";

    public GisZipHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FILE+" TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void addDB(String zipFileName) {
        ContentValues values=new ContentValues();
        values.put(COLUMN_FILE,zipFileName);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE, null, values);
        db.close();
        Log.d("YOLO", "INSERTED " + zipFileName);
    }

    public void deleteDB(String zipFileName) {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + COLUMN_FILE+"=\"" + zipFileName + "\";");
    }

    public boolean searchDB(String zipFileName) {
        String query = "Select * from " + TABLE + " where " + COLUMN_FILE + " =\"" + zipFileName + "\";";
        SQLiteDatabase db = getReadableDatabase();
        Log.d("YOLO", "SEARCHING FOR  " + zipFileName);
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            Log.d("YOLO", "NOT FOUND " + zipFileName);
            return false;
        }
        cursor.close();
        Log.d("YOLO", "FOUND " + zipFileName);
        return true;
    }
}