package model;

import java.sql.Time;
import java.text.SimpleDateFormat;


public class Lesson {
    private int id;
    private int day_id;
    private String schedule_name;
    private String name;
    private String room;
    private Time timeStart;
    private Time timeEnd;
    private String type;

    Lesson(){}

    public Lesson(int id, int day, String name, String schedule_name, String room, Time timeStart, Time timeEnd, String type){
        setId(id);
        setDay(day);
        setName(name);
        setSchedule(schedule_name);
        setRoom(room);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setType(type);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day_id;
    }

    public void setDay(int day) {
        this.day_id= day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule_name;
    }

    public void setSchedule(String schedule_name) {
        this.schedule_name = schedule_name;
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
