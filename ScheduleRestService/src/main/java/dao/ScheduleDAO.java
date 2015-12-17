package dao;

import model.Lesson;
import model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public interface ScheduleDAO {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
    ArrayList<Lesson> getClassesSelectedDay(int day, int group);
    int getGroupID(String email);
}
