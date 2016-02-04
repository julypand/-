package dao.impl;

import dao.ScheduleDAO;
import db.Connector;
import model.Lesson;
import model.Schedule;
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
    public ArrayList<Lesson> getClasses(int group) {
        return conn.getClasses(group);
    }

    @Override
    public int getGroupID(String email) {
        return conn.getGroupID(email);
    }


}
