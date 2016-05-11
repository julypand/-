package model;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Lesson {
    int id;
    int day_id;
    String schedule_name;
    String name;
    String room;
    Time timeStart;
    Time timeEnd;
    String type;

    Lesson() {
    }

    public Lesson(int id, int day, String name,String schedule_name, String room, Time timeStart, Time timeEnd, String type) {
        setId(id);
        setDay(day);
        setSchedule(schedule_name);
        setName(name);
        setRoom(room);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setType(type);
    }

    public Lesson(int id, int day, String name, String schedule_name, String room, String timeStart, String timeEnd, String type) {
        this(id,name,room,timeStart,timeEnd,type);
        setDay(day);
        setSchedule(schedule_name);
    }

    public Lesson(int day, String name,String schedule_name, String room, String timeStart, String timeEnd, String type) {
        setDay(day);
        setSchedule(schedule_name);
        setName(name);
        setRoom(room);
        setTimeStart(convert(timeStart));
        setTimeEnd(convert(timeEnd));
        setType(type);
    }

    public Lesson(int id, String name, String room, String timeStart, String timeEnd, String type){
        setId(id);
        setName(name);
        setRoom(room);
        setTimeStart(convert(timeStart));
        setTimeEnd(convert(timeEnd));
        setType(type);
    }

    public String toString() {
        return new String("Day: " + this.getDay() + "Name: " + this.getName() + ", room: " + this.getRoom() + ", timeStart: " +
                this.getTimeStart() + ", timeEnd: " + this.getTimeEnd() + ", type: " + this.getType());
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
        this.day_id = day;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getSchedule() {
        return schedule_name;
    }

    public void setSchedule(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public Time convert(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return new Time(formatter.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convert(Time time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }
}