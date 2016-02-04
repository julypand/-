package web;

import model.Lesson;
import model.User;

import java.util.ArrayList;


public interface ScheduleWebService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    String checkEmail(User user);
    ArrayList<Lesson> getSchedule(String mas);
}
