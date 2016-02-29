package model;


import java.util.ArrayList;
import java.util.HashMap;


public class User {

    private String name;
    private String surname;
    private int course;
    private String group;
    private String email;
    private String password;
    private HashMap<String,ArrayList<Lesson>> schedules;
    private  ArrayList<String> week;

    public User(){}

    public User(String name, String surname, int course,String group, String email, String password, HashMap<String,ArrayList<Lesson>> schedules){
        setName(name);
        setSurname(surname);
        setCourse(course);
        setGroup(group);
        setEmail(email);
        setPassword(password);
        setSchedules(schedules);
    }
    public User(String name, String surname, int course,String group, String email, String password){
        setName(name);
        setSurname(surname);
        setCourse(course);
        setGroup(group);
        setEmail(email);
        setPassword(password);
        setSchedules(new HashMap<String, ArrayList<Lesson>>());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, ArrayList<Lesson>> getSchedules() {
        return schedules;
    }

    public void setSchedules(HashMap<String, ArrayList<Lesson>> schedules) {
        this.schedules = schedules;
    }

    public  ArrayList<String> getWeek() {
        return week;
    }

    public void setWeek( ArrayList<String> week) {
        this.week = week;
    }
}
