package model;

import java.util.ArrayList;

public class Schedule {
    private int id;
    private String name;
    private String userEmail;
    private ArrayList<Lesson> lessons  = new ArrayList<>();
    private ArrayList<String> week = new ArrayList<>();

    public Schedule(){}

    public Schedule(String name){
        setName(name);
    }

    public Schedule(int id, String name){
        this(name);
        setId(id);
    }

    public Schedule(String name, String userEmail){
        this(name);
        setUserEmail(userEmail);
    }
    public Schedule(int id, String name, ArrayList<Lesson> lessons){
        this(id, name);
        setLessons(lessons);
    }
    public Schedule(int id, String name, ArrayList<Lesson> lessons,ArrayList<String> week){
        this(id, name,lessons);
        setWeek(week);
    }
    public void addLesson(Lesson lesson){
        lessons.add(lesson);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<String> getWeek() {
        return week;
    }

    public void setWeek(ArrayList<String> week) {
        this.week = week;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String emailUser) {
        this.userEmail = emailUser;
    }
}
