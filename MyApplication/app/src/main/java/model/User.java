package model;


import java.util.ArrayList;


public class User {

    private String name;
    private String surname;
    private int course;
    private int group;
    private String email;
    private String password;
    private boolean isPrefect;
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public User(){}

    public User(String name, String surname, int course, int group, String email, String password, ArrayList<Schedule> schedules){
        setName(name);
        setSurname(surname);
        setCourse(course);
        setGroup(group);
        setEmail(email);
        setPassword(password);
        setSchedules(schedules);
    }
    public User(String name, String surname, int course, int group, String email, String password, ArrayList<Schedule> schedules, boolean isPrefect) {
        this(name, surname, course, group, email, password, schedules);
        setIsPrefect(isPrefect);
    }
    public User(String email){
        setEmail(email);
    }
    public User(String email, String password){
        this(email);
        setPassword(password);
    }
    public User(String email, String password, boolean isPrefect){
        this(email, password);
        setIsPrefect(isPrefect);
    }
    public User(String email, boolean isPrefect){
        this(email);
        setIsPrefect(isPrefect);
    }
    public User(String email, ArrayList<Schedule> schedules){
        this(email);
        setSchedules(schedules);
    }
    public User(String email, Schedule schedules){
        this(email);
        setSchedules(new ArrayList<Schedule>());
        getSchedules().add(schedules);
    }

    public String toString(){
        return new String("Name: " + this.getName() + ", surname: " + this.getSurname() + ", course: " +
                + this.getCourse() + ", group: " + this.getGroup() + ", email: " + this.getEmail());
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

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public  ArrayList<String> week() {
        return schedules.get(0).getWeek();
    }

    public boolean getIsPrefect() {
        return isPrefect;
    }

    public void setIsPrefect(boolean isPrefect) {
        this.isPrefect = isPrefect;
    }
}