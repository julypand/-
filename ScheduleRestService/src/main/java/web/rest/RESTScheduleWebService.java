package web.rest;

import model.Lesson;
import model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.ScheduleService;
import service.impl.ScheduleServiceImpl;
import web.ScheduleWebService;

import java.awt.*;
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by Julie on 05.12.2015.
 */
@Path("/users")
public class RESTScheduleWebService implements ScheduleWebService {

    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayList<User> getAllUsers() {
        return scheduleService.getAllUsers();
    }

    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayList<User> some(String msg) {
        System.out.println("POST: " + msg);
        try {
            JSONObject child = new JSONObject(msg);
            String password = child.getString("password");
            String email = child.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scheduleService.getAllUsers();
    }*/

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getPassword(String email) {
        JSONObject child = null;
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
        JSONObject child = null;
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
        System.out.println("HERE");
        ArrayList<Lesson> list = new ArrayList<Lesson>();
        JSONObject child = null;
        int d = 0, g = 0;
        try {
            child = new JSONObject(mas);
            d = child.getInt("day");
            g = child.getInt("group");
            System.out.println("REST: " + d + ", " + g);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list = scheduleService.getClassesSelectedDay(d, g);
        System.out.println(list);
        return list/*new String("{\"email\":\"  HELLO FROM REST  \"}")*/;
    }
}
