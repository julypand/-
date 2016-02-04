package dao;

import model.Lesson;
import model.User;
import java.util.ArrayList;


public interface ScheduleDAO {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
    ArrayList<Lesson> getClasses(int group);
    int getGroupID(String email);
}
