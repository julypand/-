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
            rs = st.executeQuery("SELECT * FROM users");
            while(rs.next())
                list.add(new User(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
