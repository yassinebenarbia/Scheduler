package com.example.scheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * Saves the <b>Alarm </b>object with it's respect foreign key <b>cycleUuid</b>
     * @param alarm the alarm object that meant to be saved
     * @param cycleUuid the foreign key that links the alarm with it's respective cycle
     * @return <i>True</i> if the operation succeed and <i>False</i> otherwise
     */
    public Boolean addAlarm(Alarm alarm, UUID cycleUuid){
       ContentValues values = new ContentValues();
        //TODO : is the uuid value stored in the database a String type ??
       values.put("uuid",alarm.getUuid().toString());
       values.put("timeInMills", alarm.getTimeInMills());
       values.put("note", alarm.getNote());
       values.put("label", alarm.getLabel());
       values.put("Alarm_uuid",cycleUuid.toString());

       return dataBase.insert("alarm",null,values) != -1;
    }

    /**
     * saves the <b>Cycle</b> object on the DB
     * @param cycle  the <b>Cycle</b> object that meant to be saved
     * @return  <i>True</i> if the operation is done with success and <i>False</i> otherwise
     */
    public Boolean addCycle(Cycle cycle){
        ContentValues values = new ContentValues();
        values.put("uuid",cycle.getUuid().toString());
        values.put("name",cycle.getName());
        values.put("mountingDate",cycle.getMountingDateInMillis());
        values.put("cycleLength",cycle.getCycleLength());


        return dataBase.insert("cycle",null,values)!=-1;
    }

    /**
     *  gets all the <b>Cycles</b> from the DB
     * @return <i>ArrayList < Cycle ></i>
     */
    public ArrayList<Cycle> getAllCycles(){
        ArrayList<Cycle> cycles = new ArrayList<>();
        String getRequest = "SELECT * FROM"+AlarmDBHelper.getCycleTableName();
        Cursor listOfCycles = dataBase.rawQuery(getRequest,null);
        // TODO : convert from "listOfCycles" Cursor type to ArrayList<Cycle> type

        return cycles;
    }

    /**
     *  gets all the <b>Alarms</b> from the DB
     * @return <i>ArrayList < Alarm ></i>
     */
    public ArrayList<Alarm> getAllAlarms(){
        ArrayList<Alarm> alarms = new ArrayList<>();
        String getRequest = "SELECT * FROM"+AlarmDBHelper.getAlarmTableName();
        Cursor listOfAlarms = dataBase.rawQuery(getRequest,null);
        // TODO : convert from "listOfCycles" Cursor type to ArrayList<Alarm> type
        if(listOfAlarms.moveToFirst()){
            while(listOfAlarms.moveToNext()){
                int uuidPos = listOfAlarms.getColumnIndex("uuid"); // UUID
                int labelPos = listOfAlarms.getColumnIndex("label"); // TEXT
                int notePos = listOfAlarms.getColumnIndex("note"); // TEXT
                int timeInMillisPos = listOfAlarms.getColumnIndex("timeInMillis"); // LONG
                int currentStateIndexerPos = listOfAlarms.getColumnIndex("CurrentStateIndexer"); // NUMBER
                int enabledPos = listOfAlarms.getColumnIndex("enabled"); // BIT
                int cycleForeignKeyPos = listOfAlarms.getColumnIndex("Cycle_uuid"); // UUID

                String uuid = listOfAlarms.getString(uuidPos > -1 ? uuidPos : 0);
                String label = listOfAlarms.getString(labelPos > -1 ? uuidPos : 0);
                String note = listOfAlarms.getString(notePos > -1 ? notePos : 0);
                Long timeInMillis = listOfAlarms.getLong(timeInMillisPos > -1 ? timeInMillisPos : 0 );
                Integer currentState = listOfAlarms.getInt(currentStateIndexerPos > -1 ? currentStateIndexerPos : 0);
                Integer enabled = listOfAlarms.getInt(enabledPos > -1 ? enabledPos : 0);
                String cycleForeignKey = listOfAlarms.getString(cycleForeignKeyPos > -1 ? cycleForeignKeyPos : 0);

                // ToDo : need to manage how to fit those values in the Alarm object
//                Alarm(uuid,);

                // some stuff goes here
            }
        }

        return alarms;
    }

    /**
     * gets all the <b>Alarms </b> from the DB that are entangled with
     * a Cycle that have the <b>cycleUuid</b> as an identifier
     * @param cycleUuid a <i>UUID</i> String that refers to the <b>Cycle</b> identifier
     * @return <i>ArrayList < Alarm ></i>
     */
    public ArrayList<Alarm> getAllAlarms(String cycleUuid){
        ArrayList<Alarm> alarms = new ArrayList<>();
        String getRequest = "SELECT * FROM"+AlarmDBHelper.getAlarmTableName()+"WHWRE Cycle_uuid =?";
        Cursor listOfAlarms = dataBase.rawQuery(getRequest, new String[]{cycleUuid});
        // TODO : convert from "listOfCycles" Cursor type to ArrayList<Alarm> type

        return alarms;
    }

}
