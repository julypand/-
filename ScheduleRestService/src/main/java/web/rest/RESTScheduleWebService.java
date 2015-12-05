package web.rest;

import model.User;
import service.ScheduleService;
import service.impl.ScheduleServiceImpl;
import web.ScheduleWebService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by Julie on 05.12.2015.
 */
@Path("/user")
public class RESTScheduleWebService implements ScheduleWebService {

    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public ArrayList<User> getAllUsers() {
        return scheduleService.getAllUsers();
    }
}
