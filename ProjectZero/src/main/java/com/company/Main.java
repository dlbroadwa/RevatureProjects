package com.company;

import com.company.connections.MyConnection;
import com.company.connections.MyPostgresConnection;

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

    public static void main(String[] args) {
        //database connection used to link to the AWS DB
        MyConnection connect = new MyPostgresConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres","1234","inventoryapp");

    }
}
