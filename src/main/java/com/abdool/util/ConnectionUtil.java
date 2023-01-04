package com.abdool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    // this class will include all the code needed to connect to our database.
    private static ConnectionUtil connectionUtil;

    private ConnectionUtil() {

    }

    public static ConnectionUtil getConnectionUtil() {
        //create one if it hasn't been created
        if (connectionUtil == null) {
            return new ConnectionUtil();
        }

        return connectionUtil;
    }


    //method to actually connect to the DB - using the DriverManager
    public Connection getConnection() {
        //initial connection Object as null
        Connection conn = null;

        // sensitive info - should NOT be here
        //In our run configurations - we can create environment variables
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

/*   //a test to make sure it configures correctly
    // this is for demo purposes and is not needed
    public static void main(String[] args) {
        try(Connection connection = ConnectionUtil.getConnectionUtil().getConnection()) {
            if(connection != null) {
                System.out.println("Connection Successful.");
            } else {
                System.out.println("Connection Unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

}
