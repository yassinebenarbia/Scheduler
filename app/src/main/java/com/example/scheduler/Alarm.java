package com.example.scheduler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class that englobe the attributes of an alarm
 */
public class Alarm {
    private UUID uuid;
    private String label;
    private String note;
    private Boolean state;
    private Time time;
    private Date startingPoint;
    private Integer cycleLength;
    private ArrayList<Integer> checkPoints;


}
