package model;

import java.sql.Time;
import java.text.SimpleDateFormat;


public class Lesson {
    private int day_id;
    private String name;
    private String room;
    private String timeStart;
    private String timeEnd;
    private String type;

    Lesson(){}

    public Lesson(int day, String name, String room, Time timeStart, Time timeEnd, String type){
        setDay(day);
        setName(name);
        setRoom(room);
        setTimeStart(convert(timeStart));
        setTimeEnd(convert(timeEnd));
        setType(type);
    }
    public Lesson(int day, String name, String room, String timeStart, String timeEnd, String type){
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type= type;
    }
}
