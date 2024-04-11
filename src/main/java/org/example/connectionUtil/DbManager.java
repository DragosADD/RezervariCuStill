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


    public void closeConnection(Connection connection, Statement statement, ResultSet res) {
        this.closeConnection(connection, statement);
        this.closeConnection(res);
    }

    public void closeConnection(Connection connection, Statement[] statements, ResultSet[] resultSets) {
        this.closeConnection(connection);
        for (Statement stmt : statements) {
            this.closeConnection(stmt);
        }
        for (ResultSet resSet : resultSets) {
            this.closeConnection(resSet);
        }
    }

    public void closeConnection(Connection connection, Statement statement) {
        this.closeConnection(connection);
        this.closeConnection(statement);

    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(ResultSet res) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void closeConnection(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
