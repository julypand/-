package service;

import model.Lesson;
import model.User;

import java.util.ArrayList;

public interface ScheduleService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
    ArrayList<Lesson> getClassesSelectedDay(int day, int group);
    int getGroupID(String email);
}
