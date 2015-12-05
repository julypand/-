package service.impl;

import dao.ScheduleDAO;
import dao.impl.ScheduleDAOImpl;
import model.User;
import service.ScheduleService;

import java.util.ArrayList;

/**
 * Created by Julie on 05.12.2015.
 */
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleDAO scheduleDAO = new ScheduleDAOImpl();

    public ArrayList<User> getAllUsers() {
        return scheduleDAO.getAllUsers();
    }
}
