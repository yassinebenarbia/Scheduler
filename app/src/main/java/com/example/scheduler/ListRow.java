package com.example.scheduler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ListRow {
    private String  EMPTY = "";
    private Boolean expanded;

    public ListRow(@NotNull Date date, @NotNull int routine,
                   String label, String note) {
        this.date = date;
        this.routine = routine;
        this.label = label;
        this.note = note;
    }

    public ListRow(@NotNull Date date, @NotNull int routine) {
        this.date = date;
        this.routine = routine;
        this.label = EMPTY;
        this.note = EMPTY;
    }
    private Date date;
    private String label;
    private String note;
    private int routine;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getRoutine() {
        return routine;
    }

    public void setRoutine(int routine) {
        this.routine = routine;
    }

    // TODO : improve the newInstance such that it can handle optional parameters
    static public ListRow newInstance(Date date, int cycle, String label, String note){
        ListRow lr = new ListRow(date, cycle, label, note);
        return lr;
    }
}
