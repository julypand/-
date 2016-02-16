package service;

import model.Lesson;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface ScheduleService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
    String getType(int type_id);
    HashMap<String,ArrayList<Lesson>> getSchedules(User user);
    ArrayList<Lesson> getLessons(int schedule_id);
    int getGroupID(String email);
}
