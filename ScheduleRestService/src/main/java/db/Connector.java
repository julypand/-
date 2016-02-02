package db;

import model.Lesson;
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
            String query = "SELECT * FROM student_group WHERE group_name = \'" +
                    user.getGroup() + "\' AND course=\'" + user.getCourse() + "\'";
            rs = st.executeQuery(query);
            if (rs.next()) {
                int group_id = rs.getInt(1);

                st = con.createStatement();
                st.executeUpdate("INSERT INTO user (name, surname, email, password, group_id) " +
                        "VALUES ('" + user.getName() + "\', \'" + user.getSurname() + "\', \'"
                        + user.getEmail() + "\', \'" + user.getPassword() + "\', \'" + group_id + "')");
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

    // Schedule
    public ArrayList<Lesson> getClassesSelectedDay(String day, int group){
        ArrayList<Lesson> list = new ArrayList<>();
        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            String query = "SELECT * FROM class WHERE day =\'" + day + "\' AND schedule_id=\'" + group + "\'";
            rs = st.executeQuery(query);
            while(rs.next()){
                list.add(new Lesson(rs.getString(2), rs.getString(3), rs.getTime(4), rs.getTime(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}