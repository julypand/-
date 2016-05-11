package web.rest;

import model.Lesson;
import model.Schedule;
import model.User;
import service.ScheduleService;
import service.impl.ScheduleServiceImpl;
import web.ScheduleWebService;
import java.util.ArrayList;
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

    @Path("/isPrefect")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean isPrefect(User user) {
        String email = user.getEmail();
        boolean isPrefect = scheduleService.isPrefect(email);
        return isPrefect;
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
        ArrayList<Schedule> schedules =  scheduleService.getSchedules(user);
        ArrayList<String> week = scheduleService.getWeek();
        user.setSchedules(schedules);
        user.weekSet(week);
        return user;
    }

    @Path("/classes/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public int addLesson(Lesson lesson){
        int lessonId = scheduleService.addLesson(lesson);
        return lessonId;
    }

    @Path("/classes/edit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean editLesson(Lesson lesson){
        boolean isSuccessful = scheduleService.editLesson(lesson);
        return isSuccessful;
    }

    @Path("/classes/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean deleteLesson(int id){
        boolean isSuccessful = scheduleService.deleteLesson(id);
        return isSuccessful;
    }

    @Path("/schedules/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public int addSchedule(Schedule schedule){
        int scheduleId = scheduleService.addSchedule(schedule);
        return scheduleId;
    }

    @Path("/schedules/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean deleteSchedule(Schedule schedule){
        boolean isSuccessful = scheduleService.deleteSchedule(schedule);
        return isSuccessful;
    }

    @Path("/schedules/edit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public boolean renameSchedule(model.Pair pairNames){
        boolean isSuccessful = scheduleService.renameSchedule(pairNames);
        return isSuccessful;
    }
}