package db;

import model.Schedule;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Julie on 05.12.2015.
 */
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

    public ArrayList<User> getAllUsers(){
        ArrayList<User> list = new ArrayList<User>();
        Statement st = null;
        ResultSet rs = null;
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
        Statement st = null;
        ResultSet rs = null;
        ArrayList<String> list = new ArrayList<String>();
        try{
            st = con.createStatement();
            rs = st.executeQuery("select email from user");
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            if(list.contains(email)){
                st = con.createStatement();
                rs = st.executeQuery("select password from user where email=\'" + email + "\'");

                while(rs.next()){
                    return rs.getString(1);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserContainedAndAdding(User user){
        Statement st = null;
        ResultSet rs = null;
        try{
            st = con.createStatement();
            rs = st.executeQuery("select email from user");
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
                st.executeUpdate("insert into user (name, surname, email, password, group_id) " +
                        "values ('" + user.getName() + "\', \'" + user.getSurname() + "\', \'"
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
}