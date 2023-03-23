package com.example.scheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.UUID;


/**
 * This class is responsible for managing the DB
 * as <i>Read,&nbsp;Write,&nbsp;Update</i> and <i>Delete</i> from and into the DB
 */
public class AlarmDBManager {
    private AlarmDBHelper dbHelper;
    private SQLiteDatabase dataBase;

    AlarmDBManager(Context context){
        dbHelper = new AlarmDBHelper(context);
    }

    public void open() throws SQLException{
         dataBase = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }

    public Boolean addAlarm(Alarm alarm, UUID cycleUuid){
       ContentValues values = new ContentValues();
        //TODO : is the uuid value stored in the database a String type ??
       values.put("uuid",alarm.getUuid().toString());
       values.put("timeInMills", alarm.getTimeInMills());
       values.put("note", alarm.getNote());
       values.put("label", alarm.getLabel());
       values.put("Alarm_uuid",cycleUuid.toString());
       values.put("cycle");
       dataBase.insert("alarm",null,values);
       return true;
    }
    public Boolean addCycle(Cycle cycle){

    }

}
