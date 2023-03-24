package com.example.scheduler;

import java.util.Date;
import java.util.UUID;

public class Cycle {
    //ToDo : need to create a constructor
    private UUID  uuid ;
    private String name;
    private Long mountingDateInMillis;
    private  Integer cycleLength;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getMountingDateInMillis() {
        return mountingDateInMillis;
    }
    public void setMountingDateInMillis(Long mountingDateInMillis) {
        this.mountingDateInMillis = mountingDateInMillis;
    }

    public Integer getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(Integer cycleLength) {
        this.cycleLength = cycleLength;
    }

}
