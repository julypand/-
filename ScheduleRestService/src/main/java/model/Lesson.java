package model;

import java.sql.Time;


public class Lesson {
    String day;
    String name;
    String room;
    Time timeStart;
    Time timeEnd;
    String type;

    Lesson(){}

    public Lesson(String day, String name, String room, Time timeStart, Time timeEnd, String type){
        setDay(day);
        setName(name);
        setRoom(room);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setType(type);
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day= day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type= type;
    }
}
