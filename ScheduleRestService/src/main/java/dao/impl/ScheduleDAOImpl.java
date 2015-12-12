package dao.impl;

import dao.ScheduleDAO;
import db.Connector;
import model.Schedule;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Julie on 05.12.2015.
 */
public class ScheduleDAOImpl implements ScheduleDAO{

    private Connector conn = new Connector("com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/people",
            "root", "password");

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


}
