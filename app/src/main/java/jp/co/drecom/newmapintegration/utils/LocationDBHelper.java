package jp.co.drecom.newmapintegration.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Statement;

/**
 * Created by huang_liangjin on 2015/03/19.
 * all operations about the DB
 * Maybe
 */
public class LocationDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NewMapIntegration.db";
    //***********************MyLocationTable*****************************//
    public static final String MY_LOCATION_TABLE_NAME = "myLocationTable";
    public static final String MY_LOCATION_ID = "id";
    public static final String MY_LOCATION_TIME = "current_localtime";
    public static final String MY_LOCATION_LATITUDE = "current_latitude";
    public static final String MY_LOCATION_LONGITUDE = "current_longitude";
    public static final String CREATE_MY_LOCATION_TABLE =
            "create table " + MY_LOCATION_TABLE_NAME
                    + "(" + MY_LOCATION_ID + " integer primary key autoincrement, " +
                    MY_LOCATION_LATITUDE + " real, " +
                    MY_LOCATION_LONGITUDE + " real, " +
                    MY_LOCATION_TIME + " integer " +
                    ")";
    public static final String DELETE_MY_LOCATION_TABLE = "drop table if exists "
            + MY_LOCATION_TABLE_NAME;
    //***********************MyLocationTable*****************************//

    private static String SELECT_LOCATION_DATA = "select "
            + MY_LOCATION_LATITUDE + ", "
            + MY_LOCATION_LONGITUDE + ", "
            + MY_LOCATION_TIME + " from "
            + MY_LOCATION_TABLE_NAME + " where "
            + MY_LOCATION_TIME + " between " + " ? and ?";

    public SQLiteDatabase mLocationDB;

    public LocationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MY_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_MY_LOCATION_TABLE);
        onCreate(db);
    }

    public long saveDataToDB(double latitude, double longitude,long time) {
        ContentValues values = new ContentValues();

        values.put(MY_LOCATION_LATITUDE, latitude);
        values.put(MY_LOCATION_LONGITUDE, longitude);
        values.put(MY_LOCATION_TIME, time);
        return mLocationDB.insert(MY_LOCATION_TABLE_NAME,
                null, values);
    }

    public Cursor getLocationLog(long starTime, long endTime) {
        //TODO
        NewLog.logD(String.valueOf(starTime));
        NewLog.logD(String.valueOf(endTime));
        // where location_time is between
        return mLocationDB.rawQuery(SELECT_LOCATION_DATA,
                new String[] {String.valueOf(starTime), String.valueOf(endTime)});

//        return mLocationDB.rawQuery("select current_latitude, current_longitude, current_localtime from myLocationTable where id between 1 and 5", null);

    }


}