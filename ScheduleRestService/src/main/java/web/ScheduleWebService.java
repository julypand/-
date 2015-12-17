package web;

import model.Lesson;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Julie on 05.12.2015.
 */
public interface ScheduleWebService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    String checkEmail(User user);
    ArrayList<Lesson> getDaySchedule(String mas);
}
