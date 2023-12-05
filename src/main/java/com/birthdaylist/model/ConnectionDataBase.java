package com.birthdaylist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {

    private static final String url = "jdbc:postgresql://localhost:5432/birthday_list";
    private static final String user = "adm123";
    private static final String password = "123";
    private static Connection conn;

    public Connection getConexao(){
        try {
            if (conn == null) {
                Class.forName("org.postgresql.Driver");
                System.out.println("Connected database");
                conn = DriverManager.getConnection(url, user, password);
                return  conn;
            }else{
                return conn;
            }
        } catch (SQLException e){
            System.out.println("Error: Connection to database failed.");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e){
            System.out.println("Error: JDBC driver not found.");
            e.printStackTrace();
            return null;
        }

    }

    public void closedConnection(){
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the connection.");
            e.printStackTrace();
        }
    }

}
