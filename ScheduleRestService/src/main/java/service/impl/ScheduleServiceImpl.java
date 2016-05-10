package service.impl;

import dao.ScheduleDAO;
import dao.impl.ScheduleDAOImpl;
import model.Lesson;
import model.Pair;
import model.Schedule;
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
    public ArrayList<Lesson> getLessons(int schedule_id) {
        return scheduleDAO.getLessons(schedule_id);
    }
    @Override
    public ArrayList<Schedule> getSchedules(User user){
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
    public  ArrayList<String> getWeek(){return  scheduleDAO.getWeek();}
    public  int  addLesson(Lesson lesson){return  scheduleDAO.addLesson(lesson);}

    @Override
    public boolean editLesson(Lesson lesson) {
        return scheduleDAO.editLesson(lesson);
    }

    @Override
    public int addSchedule(Schedule schedule) {
        return scheduleDAO.addSchedule(schedule);
    }
    @Override
    public boolean deleteSchedule(Schedule schedule){
        return scheduleDAO.deleteSchedule(schedule);
    }

    @Override
    public boolean renameSchedule(Pair pair) {
        return scheduleDAO.renameSchedule(pair.getFirst(), pair.getSecond());
    }

}
