package model;

import java.sql.Time;

/**
 * Created by Julie on 17.12.2015.
 */
public class Lesson {
    String name;
    String room;
    Time timeStart;
    Time timeEnd;

    Lesson(){}

    public Lesson(String name, String room, Time timeStart, Time timeEnd){
        setName(name);
        setRoom(room);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
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
}
