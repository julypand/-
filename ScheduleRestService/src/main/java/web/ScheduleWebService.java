package web;

import model.Lesson;
import model.Schedule;
import model.User;

import java.util.ArrayList;


public interface ScheduleWebService {
    ArrayList<User> getAllUsers();
    User getUser(User user);
    boolean checkEmail(User user);
    User getSchedules(User user);
    public int addLesson(Lesson lesson);
    public int addSchedule(Schedule schedule);

}
