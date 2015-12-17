package service.impl;

import dao.ScheduleDAO;
import dao.impl.ScheduleDAOImpl;
import model.Lesson;
import model.User;
import service.ScheduleService;

import java.util.ArrayList;


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

    @Override
    public ArrayList<Lesson> getClassesSelectedDay(int day, int group) {
        return scheduleDAO.getClassesSelectedDay(day, group);
    }

    @Override
    public int getGroupID(String email) {
        return scheduleDAO.getGroupID(email);
    }

    public ArrayList<User> getAllUsers() {
        return scheduleDAO.getAllUsers();
    }
}
