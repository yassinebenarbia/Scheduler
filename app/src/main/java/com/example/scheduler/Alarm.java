package com.example.scheduler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class that encompass the attributes of an alarm
 */
public class Alarm {

    /**
     *
     * @param uuid Unique identifier (<b>UUID</b>) of the Alarm object.
     * @param label Optional Label of type String.
     * @param note Optional Note of type String.
     * @param state A Boolean value represent whether the alarm is ON or OFF, <b>True</b> for one and <b>False</b> for off.
     * @param time A Long value that represent the when the alarm should starts during the day.
     * @param mountingPoints a String sequence that represent on which day during the cycle the alarm should start, this should
     *                       be a sequence of numbers separated by comas.
     */
    Alarm(String uuid, String label, String note, Boolean state,Long time, String mountingPoints){
        this.uuid = UUID.fromString(uuid);
        this.label = label;
        this.note = note;
        this.state = state;
        this.mountingPoints = mountingPoints;
        this.time = new Time(time);
    }

    private UUID uuid;
    private String label;
    private String note;
    private Boolean state;
    private Time time;
    private Date startingPoint;
    private Integer cycleLength;
    private String mountingPoints;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    /**
     * gets the Time object that the alarm starts from
     * @return Time
     */
    public Time getTime() {
        return time;
    }
    public long getTimeInMills(){
        return time.getTime();
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public Date getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Date startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Integer getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(Integer cycleLength) {
        this.cycleLength = cycleLength;
    }

    public ArrayList<Integer> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(ArrayList<Integer> checkPoints) {
        this.checkPoints = checkPoints;
    }

    private ArrayList<Integer> checkPoints;

}
