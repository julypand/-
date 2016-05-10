package db;

import model.Lesson;
import model.Schedule;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class Connector {
    private Connection con = null;

    public Connector(String driver, String url, String login, String password){
        Properties prop = new Properties();
        prop.put("user", login);
        prop.put("password", password);
        prop.put("autoReconnect", "true");
        prop.put("characterEncoding", "UTF-8");
        prop.put("useUnicode", "true");
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, prop);
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find this db driver classes.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Cannot connect to this db.");
            e.printStackTrace();
        }
    }
    // Login
    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM user");
            while(rs.next())
                list.add(new User(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7),new ArrayList<Schedule>()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getIdUser(String email){
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT id FROM user WHERE email = \'" + email + "\'");
            while(rs.next()) {
                return  rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getPassword(String email){
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT password FROM user WHERE email = \'" + email + "\'");
            while(rs.next()) {
                return  rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getGroupID(String email){
        Statement st;
        ResultSet rs;
        int result = 0;
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT group_id FROM user WHERE email = \'" + email + "\'");
            if(rs.next()) {
                result = rs.getInt(1);
                System.out.println(rs.getInt(1));
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return result;
    }

    //SignUp
    public boolean isUserContainedAndAdding(User user){
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT email FROM user");
            while(rs.next()) {
                if (rs.getString(1).equals(user.getEmail()))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addUser(user);
        return false;
    }



    private void addUser (User user){
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            String query = "SELECT * FROM student_group WHERE name_group = \'" +
                    user.getGroup() + "\' AND course=\'" + user.getCourse() + "\'";
            rs = st.executeQuery(query);
            if (rs.next()) {
                int group_id = rs.getInt(1);
                st = con.createStatement();
                st.executeUpdate("INSERT INTO user (name, surname, email, password, group_id) " +
                        "VALUES ('" + user.getName() + "\', \'" + user.getSurname() + "\', \'"
                        + user.getEmail() + "\', \'" + user.getPassword() + "\', \'" + group_id + "')");
                int user_id = getId(user.getEmail());
                st = con.createStatement();
                st.executeUpdate("INSERT INTO user_has_schedule (user_id,schedule_id) " +
                        "VALUES ('" + user_id + "\', \'"  + group_id + "')");

            } else {
                //TODO handle the case, when such group and course doesn't exist
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getId(String email){
        int id = 1;
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM user WHERE  email='" + email + "\'");
            if(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }


    // Schedule
    public ArrayList<Schedule> getSchedules(User user){
        String email = user.getEmail();
        ArrayList<Schedule> schedules = new ArrayList<>();
        String name;
        Statement st = null;
        ResultSet rs = null;
        int user_id = getId(email);
        try {
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM user_has_schedule WHERE  user_id='" + user_id + "'");

            while(rs.next()){
                int schedule_id = rs.getInt(2);
                boolean isEditable = IsEditableSchedule(schedule_id);
                name = getNameSchedule(schedule_id);

                ArrayList<Lesson> lessons = getLessons(schedule_id);
                schedules.add(new Schedule(schedule_id,name, isEditable, lessons));
                }
            }

        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return schedules;
    }
    public  ArrayList<String> getWeek(){
        ArrayList<String> week = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM day";
            rs = st.executeQuery(query);
            while(rs.next()){
                week.add(rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return week;
    }
    public ArrayList<Lesson> getLessons(int schedule_id){
        ArrayList<Lesson> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM lesson WHERE  schedule_id=\'" + schedule_id + "\'";
            rs = st.executeQuery(query);
            while(rs.next()){
                String name = getNameClass(rs.getInt(2));
               list.add(new Lesson(rs.getInt(1),rs.getInt(8), name, getNameSchedule(schedule_id) ,rs.getString(3), rs.getTime(4), rs.getTime(5), getType(rs.getInt(7))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int addLesson(Lesson lesson){
        String name = lesson.getName();
        String type = lesson.getType();
        String schedule = lesson.getSchedule();
        int name_id = -1;
        int type_id = -1;
        int schedule_id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            name_id = getIdClass(name);
            if(name_id == -1){
                name_id = addClass(name);
            }
            type_id = getIdType(type);
            if(type_id == -1){
                type_id = addType(type);
            }
            schedule_id = getIdSchedule(schedule);

            st.executeUpdate("INSERT INTO lesson (name_id, room, time_start, time_end, schedule_id, type_id, day_id) " +
                    "VALUES ('" + name_id + "\', \'"+lesson.getRoom()+ "\', \'" + lesson.getTimeStart() + "\', \'"
                    + lesson.getTimeEnd() + "\', \'" + schedule_id + "\', \'" + type_id + "\', \'"+ lesson.getDay() + "')");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getLessonId(lesson, name_id, type_id, schedule_id);
    }
    public int addSchedule(Schedule schedule){
        String name = schedule.getName();
        int user_id = -1;
        int schedule_id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();

            st.executeUpdate("INSERT INTO schedule (name) VALUES ('" +name + "')");
            user_id = getIdUser(schedule.getUserEmail());
            schedule_id = getIdSchedule(name);
            st.executeUpdate("INSERT INTO user_has_schedule (user_id,schedule_id) " +
                    "VALUES ('" + user_id + "\', \'"  + schedule_id + "')");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule_id;
    }
    public boolean deleteSchedule(Schedule schedule){
        String name = schedule.getName();
        String email = schedule.getUserEmail();
        int user_id = -1;
        int schedule_id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            schedule_id = getIdSchedule(name);
            user_id = getId(email);
            deleteLessonsOfSchedule(schedule_id);
            st.executeUpdate("DELETE FROM user_has_schedule WHERE user_id = " + user_id + " AND schedule_id = "  + schedule_id);
            st = con.createStatement();
            st.executeUpdate("DELETE FROM schedule WHERE id = " +schedule_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void deleteLessonsOfSchedule(int schedule_id){
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            st.executeUpdate("DELETE FROM lesson WHERE schedule_id = "  + schedule_id + "");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getType(int id){
        String type = "lecture";
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM type WHERE  id=\'" + id + "\'";
            rs = st.executeQuery(query);
            while(rs.next()){
                type = rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }
    private int getLessonId(Lesson lesson, int name_id, int type_id, int schedule_id){
        int id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = " SELECT * FROM lesson WHERE name_id = '" + name_id + "\' AND time_start=\'" + lesson.getTimeStart() + "\' AND time_end=\'" + lesson.getTimeEnd()
                    + "\' AND schedule_id=\'" + schedule_id  + "\' AND type_id=\'" + type_id+"\' AND day_id=\'"+lesson.getDay() +"\'";
            rs = st.executeQuery(query);
            if(rs.next()){
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    private String getNameClass(int name_id){
        String name = "";
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM class WHERE  id=\'" + name_id + "\'";
            rs = st.executeQuery(query);
            if(rs.next()){
                name = rs.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;

    }
    private int getIdClass(String name){
        int id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM class WHERE  name = \'" + name + "\'";
            rs = st.executeQuery(query);
            if(rs.next()){
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    private int addClass(String name){
        int id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "INSERT INTO class (name) VALUES ('" + name +  "')";
            st.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getIdClass(name);
    }
    private int getIdType(String name){
        int id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM type WHERE  name=\'" + name + "\'";
            rs = st.executeQuery(query);
            if(rs.next()){
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    private int addType(String name){
        int id = -1;
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "INSERT INTO type (name) " +
                    "VALUES ('" + name +  "')";
            st.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getIdType(name);
    }
    private String getNameDay(int id){
        String name = "";
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM day WHERE  id=\'" + id + "\'";
            rs = st.executeQuery(query);
            if(rs.next()){
                name = rs.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
    private String getNameSchedule(int schedule_id){
        String name = "";
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM schedule WHERE  id='" + schedule_id + "\'");
            if(rs.next()) {
                name = rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;

    }
    private int getIdSchedule(String name){
        int id = -1;
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM schedule WHERE  name='" + name + "\'");
            if(rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }
    private boolean IsEditableSchedule(int id){
        boolean isEditable = false;
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM schedule WHERE  id = '" + id + "\'");
            if(rs.next()) {
                isEditable = rs.getBoolean(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEditable;

    }
}