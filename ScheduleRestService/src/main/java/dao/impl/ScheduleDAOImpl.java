package dao.impl;

import dao.ScheduleDAO;
import db.Connector;
import model.Lesson;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

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
    public boolean isUserContainedAndAdding(User user) {
        return conn.isUserContainedAndAdding(user);
    }

    @Override
    public String getType(int type_id) {
        return conn.getType(type_id);
    }

    @Override
    public HashMap<String,ArrayList<Lesson>> getSchedules(User user){ return conn.getSchedules(user);}
    @Override
    public ArrayList<Lesson> getClasses(int schedule_id) {
        return conn.getClasses(schedule_id);
    }

    @Override
    public int getGroupID(String email) {
        return conn.getGroupID(email);
    }


}
