package model;

/**
 * Created by Julie on 05.12.2015.
 */
public class User {

    private String name;
    private String surname;
    private int course;
    private int group;
    private String email;
    private String password;
    private Schedule schedule;

    public User(){}

    public User(String name, String surname, int course, int group, String email, String password/*, Schedule schedule*/){
        setName(name);
        setSurname(surname);
        setCourse(course);
        setGroup(group);
        setEmail(email);
        setPassword(password);
        /*setSchedule(schedule);*/
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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
