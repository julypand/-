package service.impl;

import dao.ScheduleDAO;
import dao.impl.ScheduleDAOImpl;
import model.Lesson;
import model.User;
import service.ScheduleService;

import java.util.ArrayList;
import java.util.HashMap;


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
    public ArrayList<Lesson> getClasses(int schedule_id) {
        return scheduleDAO.getClasses(schedule_id);
    }
    @Override
    public HashMap<String,ArrayList<Lesson>> getSchedules(User user){
        return scheduleDAO.getSchedules(user);
    }

    @Override
    public int getGroupID(String email) {
        return scheduleDAO.getGroupID(email);
    }
    public String getType(int type_id) {
        return scheduleDAO.getType(type_id);
    }

    public ArrayList<User> getAllUsers() {
        return scheduleDAO.getAllUsers();
    }
}
