package com.example.scheduler;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


/**
 * This class is responsible for <i>creating </i> the DB, such that it instantiate once
 */
public class AlarmDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Alarm.db";
    private static final String ALARM_TABLE_NAME = "Alarm";
    private static final String ALARM_ID = "uuid";
    private static final String ALARM_TIME_IN_MILLS = "timeInMills";
    private static final String ALARM_LABEL = "label";
    private  static final String ALARM_NOTE = "note";
    private static final String ALARM_ENABLED = "enabled";
    private static final String CYCLE_TABLE_NAME = "Cycle";
    private static final String CYCLE_ID = "uuid";
    private static final String CYCLE_NAME = "name";
    private static final String CYCLE_MOUNTING_DATE = "mountingDate";
    private static final  String CYCLE_LENGTH = "cycleLength";



    public AlarmDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public AlarmDBHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    // create a DB schema
    @Override
    public void onCreate(SQLiteDatabase db) {
        // can add "AUTOINCREMENT" to the table id
        String CREATE_ALARM_TABLE = "CREATE TABLE " + ALARM_TABLE_NAME +"("
                                      +ALARM_ID +" UUID PRIMARY KEY, "
                                      +ALARM_TIME_IN_MILLS + " LONG NOT NULL, "
                                      +ALARM_LABEL + "TEXT, "
                                      +ALARM_NOTE + "TEXT, "
                                      +ALARM_ENABLED + "BIT, "
                                      +"FOREIGN KEY ("+ALARM_TABLE_NAME+"_"+ALARM_ID+") "
                                      +"REFERENCES "+ALARM_TABLE_NAME+"("+ALARM_ID+")"
                                      +")";
        db.execSQL(CREATE_ALARM_TABLE);

        String CREATE_CYCLE_TABLE = "CREATE TABLE " + CYCLE_TABLE_NAME +"("
                                    +CYCLE_ID + "UUID PRIMARY KEY, "
                                    +CYCLE_NAME + "TEXT, "
                                    +CYCLE_MOUNTING_DATE + "DATE, "
                                    +CYCLE_LENGTH + "NUMBER"
                                    +")";
        db.execSQL(CREATE_CYCLE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
