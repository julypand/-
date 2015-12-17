package web.rest;

import model.Lesson;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
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
    public String getPassword(String email) {
        JSONObject child;
        String emailtwo = null;

        try {
            child = new JSONObject(email);
            emailtwo = child.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int group_id = scheduleService.getGroupID(emailtwo);
        String str = ("{\"password\":\"" + scheduleService.getPassword(emailtwo) + "\",\"group_id\":\"" + group_id + "\"}");
        System.out.println(str);
        return str;
    }

    @Path("/signup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String checkEmail(User user) {
        JSONObject child;
        String email = null;
        try {
            child = new JSONObject(user);
            email = child.getString("email");

            if (scheduleService.isUserContainedAndAdding(user)) {
                return new String("{\"email\":\"" + null + "\"}");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String("{\"email\":\"" + email + "\"}");
    }

    @Path("/classes")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayList<Lesson> getDaySchedule(String mas){
        ArrayList<Lesson> lessons;
        JSONObject child;
        int day = 0, group = 0;
        try {
            child = new JSONObject(mas);
            day = child.getInt("day");
            group = child.getInt("group");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lessons = scheduleService.getClassesSelectedDay(day, group);
        System.out.println(lessons);
        return lessons;
    }
}
