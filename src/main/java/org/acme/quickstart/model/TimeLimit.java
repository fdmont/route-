package org.acme.quickstart.model;


import java.time.LocalDateTime;


public class TimeLimit {

    private final LocalDateTime lastUpdate;
    private final Short profileId;
    private final Short interval;
    private final Auditory auditory;

    public TimeLimit(LocalDateTime lastUpdate, Short profileId, Short interval, Auditory auditory) {
        this.lastUpdate = lastUpdate;
        this.profileId = profileId;
        this.interval = interval;
        this.auditory = auditory;
    }


    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public Short getProfileId() {
        return profileId;
    }

    public Short getInterval() {
        return interval;
    }

    public Auditory getAuditory() {
        return auditory;
    }
}
