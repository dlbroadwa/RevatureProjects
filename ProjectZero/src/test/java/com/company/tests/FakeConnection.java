package com.company.tests;

import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class FakeConnection {

    private Connection connection;

    public void getFakeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:6666/jcg", "root", "password");
    }

    public int executeQuery(String query) throws SQLException {
        return connection.createStatement().executeUpdate(query);
    }
}