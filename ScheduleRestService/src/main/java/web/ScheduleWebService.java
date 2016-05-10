package web;

import model.Lesson;
import model.Pair;
import model.Schedule;
import model.User;

import java.util.ArrayList;


public interface ScheduleWebService {
    ArrayList<User> getAllUsers();
    User getUser(User user);
    boolean checkEmail(User user);
    User getSchedules(User user);
    int addLesson(Lesson lesson);
    boolean editLesson(Lesson lesson);
    boolean deleteLesson(int id);
    int addSchedule(Schedule schedule);
    boolean deleteSchedule(Schedule schedule);
    boolean renameSchedule(Pair pairNames);


}
