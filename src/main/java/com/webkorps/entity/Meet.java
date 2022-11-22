package com.webkorps.entity;

import com.webkorps.exception.MeetTimeException;
import com.webkorps.util.TimeUtil;

import java.time.LocalTime;


public class Meet {

    private Long id;

    private LocalTime inTime;


    private LocalTime outTime;



    private String room;


    private int members;

    public void setInTime(LocalTime inTime) {
        if(!TimeUtil.isValidTime(inTime)){
            throw new MeetTimeException("INCORRECT_INPUT");
        }
        this.inTime = inTime;
    }

    public void setOutTime(LocalTime outTime) {
        if(!TimeUtil.isValidTime(outTime)){
            throw new MeetTimeException("INCORRECT_INPUT");
        }
        this.outTime = outTime;
    }

    public void setMembers(int members) {
        if(members <2 || members >20){
            throw new RuntimeException("NO_VACANT_ROOM");
        }
        this.members = members;
    }



    public LocalTime getInTime() {
        return inTime;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getMembers() {
        return members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
