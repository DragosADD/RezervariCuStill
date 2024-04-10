package org.example.connectionUtil;

import java.sql.*;

public class DbManager {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/rezervaricustil";
        String user = "dragosadd";
        String password = "34925612";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Fatal Error: MariaDB JDBC Driver not found.");
            e.printStackTrace();
            System.exit(1);
        }
        return DriverManager.getConnection(url, user, password);
    }


    //nu este recomandata
    public void closeConnection(Connection connection, Statement statement, ResultSet res) {
        this. closeConnection(connection, statement);
        if(res !=null){
            try{
                res.close();
            }catch (SQLException e){
                System.out.println("print exception");
            }

        }
    }

    public  void closeConnection(Connection connection, Statement statement){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("print exception");
            }
        }
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                System.out.println("print exception");
            }
        }
    }
}
