package service;

import model.Lesson;
import model.Schedule;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface ScheduleService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
    String getType(int type_id);
    ArrayList<Schedule> getSchedules(User user);
    ArrayList<Lesson> getLessons(int schedule_id);
    int getGroupID(String email);
    ArrayList<String> getWeek();
    int addLesson(Lesson lesson);
}
