package service.impl;

import dao.ScheduleDAO;
import dao.impl.ScheduleDAOImpl;
import model.User;
import service.ScheduleService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Julie on 05.12.2015.
 */
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleDAO scheduleDAO = new ScheduleDAOImpl();

    @Override
    public String getPassword(String email) {
        return scheduleDAO.getPassword(email);
    }

    @Override
    public boolean isUserContainedAndAdding(User user) {
        return scheduleDAO.isUserContainedAndAdding(user);
    }

    public ArrayList<User> getAllUsers() {
        return scheduleDAO.getAllUsers();
    }
}