package web.rest;

import model.Lesson;
import model.User;
import service.ScheduleService;
import service.impl.ScheduleServiceImpl;
import web.ScheduleWebService;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class RESTScheduleWebService implements ScheduleWebService {

    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayList<User> getAllUsers() {
        return scheduleService.getAllUsers();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public User getUser(User user) { // TODO passing user is clumsy. Think of just passing email only. Through query params?
        String email = user.getEmail();
        String password = scheduleService.getPassword(email);
        user.setPassword(password);
        return user;
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean checkEmail(User user) {
        if(!scheduleService.isUserContainedAndAdding(user)){
            return true;
        }
        return false;

    }

    @Path("/classes")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public User getSchedules(User user){
        HashMap<String,ArrayList<Lesson>> schedules =  scheduleService.getSchedules(user);
        ArrayList<String> week = scheduleService.getWeek();
        user.setSchedules(schedules);
        user.setWeek(week);
        return user;
    }
    @Path("/classes/addLesson")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public int addLesson(Lesson lesson){
        int lessonId = scheduleService.addLesson(lesson);
        return lessonId;
    }
}
