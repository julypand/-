package service;

import model.Lesson;
import model.Pair;
import model.Schedule;
import model.User;

import java.util.ArrayList;

public interface ScheduleService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isPrefect(String email);
    boolean isUserContainedAndAdding(User user);
    String getType(int type_id);
    ArrayList<Schedule> getSchedules(User user);
    ArrayList<Lesson> getLessons(int schedule_id);
    int getGroupID(String email);
    ArrayList<String> getWeek();
    int addLesson(Lesson lesson);
    boolean editLesson(Lesson lesson);
    boolean deleteLesson(int id);
    int addSchedule(Schedule schedule);
    boolean deleteSchedule(Schedule schedule);
    boolean renameSchedule(Pair pairNames);
}
