package model;


import java.util.ArrayList;
import java.util.HashMap;


public class User {

    private String name;
    private String surname;
    private int course;
    private int group;
    private String email;
    private String password;
    private HashMap<String,ArrayList<Lesson>> schedules = new HashMap<>();
    private  ArrayList<String> week;

    public User(){}

    public User(String name, String surname, int course, int group, String email, String password, HashMap<String,ArrayList<Lesson>> schedules){
        setName(name);
        setSurname(surname);
        setCourse(course);
        setGroup(group);
        setEmail(email);
        setPassword(password);
        setSchedules(schedules);
    }
    public User(String email){
        setEmail(email);
    }

    public String toString(){
        return new String("Name: " + this.getName() + ", surname: " + this.getSurname() + ", course: " +
                + this.getCourse() + ", group: " + this.getGroup() + ", email: " + this.getEmail());
    }

    public void addLesson(String name_schedule, Lesson lesson){
        if(getSchedules().containsKey(name_schedule))
            getSchedules().get(name_schedule).add(lesson);
    }

    public void addSchedule(String name,ArrayList<Lesson> lessons){
        getSchedules().put(name,lessons);
    }
    public void addSchedule(String name){
        getSchedules().put(name,new ArrayList<Lesson>());
    }
    public void deleteSchedule(String name){
        getSchedules().remove(name);
    }

    public ArrayList<Lesson> getSchedule(String name){
        return getSchedules().get(name);
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
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

    public HashMap<String,ArrayList<Lesson>> getSchedules() {
        return schedules;
    }

    public void setSchedules(HashMap<String,ArrayList<Lesson>> schedules) {
        this.schedules = schedules;
    }

    public  ArrayList<String> getWeek() {
        return week;
    }

    public void setWeek( ArrayList<String> week) {
        this.week = week;
    }
}