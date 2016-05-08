package model;

import java.sql.Time;
import java.text.SimpleDateFormat;


public class Lesson {
    private int id;
    private int day_id;
    private String name;
    private String room;
    private Time timeStart;
    private Time timeEnd;
    private String type;

    Lesson(){}

    public Lesson(int id, int day, String name, String room, Time timeStart, Time timeEnd, String type){
        setId(id);
        setDay(day);
        setName(name);
        setRoom(room);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setType(type);
    }

    public String convert(Time time){
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        return format.format(time);
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
