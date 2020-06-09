package company;

import company.connections.MyConnection;
import company.connections.MyPostgresConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    //The static block is used to preempt the data base connection
    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        //database connection used to link to the AWS DB

        MyConnection connect = new MyPostgresConnection();
    }
}
