package db;

import model.Lesson;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
                list.add(new User(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getPassword(String email){
        Statement st;
        ResultSet rs;
        ArrayList<String> list = new ArrayList<>();
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT email FROM user");
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            if(list.contains(email)){
                st = con.createStatement();
                rs = st.executeQuery("SELECT password FROM user WHERE email=\'" + email + "\'");
                while(rs.next()){
                    return rs.getString(1);
                }
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
            rs = st.executeQuery("SELECT group_id FROM user WHERE email=\'" + email + "\'");
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

    public HashMap<String,ArrayList<Lesson>> getSchedules(User user){
        String email = user.getEmail();
        HashMap<String,ArrayList<Lesson>> schedules = new HashMap<>();
        String name;
        Statement st = null;
        ResultSet rs = null;
        int user_id = getId(email);
        try {
                st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM user_has_schedule WHERE  user_id='" + user_id + "'");

            while(rs.next()){
                int schedule_id = rs.getInt(2);
                name = getNameSchedule(schedule_id);
                ArrayList<Lesson> lessons = getClasses(schedule_id);
                schedules.put(name,lessons);
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

    public ArrayList<Lesson> getClasses(int schedule_id){
        ArrayList<Lesson> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM class WHERE  schedule_id=\'" + schedule_id + "\'";
            rs = st.executeQuery(query);
            while(rs.next()){
               list.add(new Lesson(rs.getString(7),rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5), getType(rs.getInt(8))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}