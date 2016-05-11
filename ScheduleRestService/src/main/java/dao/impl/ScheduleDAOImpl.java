package dao.impl;

import dao.ScheduleDAO;
import db.Connector;
import model.Lesson;
import model.Schedule;
import model.User;

import java.util.ArrayList;

public class ScheduleDAOImpl implements ScheduleDAO{

    private Connector conn = new Connector("com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/schedule",
            "root", "root");

    public ArrayList<User> getAllUsers() {
        return conn.getAllUsers();
    }

    @Override
    public String getPassword(String email) {
        return conn.getPassword(email);
    }

    @Override
    public boolean isPrefect(String email) {
        return conn.isPrefect(email);
    }

    @Override
    public boolean isUserContainedAndAdding(User user) {
        return conn.isUserContainedAndAdding(user);
    }

    @Override
    public String getType(int type_id) {
        return conn.getType(type_id);
    }

    @Override
    public ArrayList<Schedule> getSchedules(User user){ return conn.getSchedules(user);}
    @Override
    public ArrayList<Lesson> getLessons(int schedule_id) {
        return conn.getLessons(schedule_id);
    }

    @Override
    public int getGroupID(String email) {
        return conn.getGroupID(email);
    }
    @Override
    public  ArrayList<String> getWeek() {
        return conn.getWeek();
    }
    @Override
    public  int addLesson(Lesson lesson) {
        return conn.addLesson(lesson);
    }

    @Override
    public boolean editLesson(Lesson lesson) {
        return conn.editLesson(lesson);
    }

    @Override
    public boolean deleteLesson(int id) {
        return conn.deleteLesson(id);
    }

    @Override
    public int addSchedule(Schedule schedule) {
        return conn.addSchedule(schedule);
    }

    @Override
    public boolean deleteSchedule(Schedule schedule) {
        return conn.deleteSchedule(schedule);
    }

    @Override
    public boolean renameSchedule(String name, String newName) {
        return conn.renameSchedule(name, newName);

    }


}
