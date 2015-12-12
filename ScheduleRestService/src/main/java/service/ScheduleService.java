package service;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Julie on 05.12.2015.
 */
public interface ScheduleService {
    ArrayList<User> getAllUsers();
    String getPassword(String email);
    boolean isUserContainedAndAdding(User user);
}
