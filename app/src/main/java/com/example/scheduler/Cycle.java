package com.example.scheduler;

import java.util.Date;
import java.util.UUID;

public class Cycle {
    private UUID  uuid ;
    private String name;
    private Date mountingDate;

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

    public Date getMountingDate() {
        return mountingDate;
    }

    public void setMountingDate(Date mountingDate) {
        this.mountingDate = mountingDate;
    }

    public Integer getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(Integer cycleLength) {
        this.cycleLength = cycleLength;
    }

    private Integer cycleLength;

}
