package com.company.screens;

import com.company.apps.Application;
import com.company.apps.Inventory;
import com.company.apps.InventoryManagementApp;
import com.company.apps.Item;
import com.company.connections.MyPostgresConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class LoginScreen implements Screens{
      protected Connection connection;
      protected ArrayList<Item> inventory;

      public LoginScreen(Connection connection){
            this.connection= connection;
            ;
      }

      public Screens display(Application app, Inventory i) {
            this.inventory= i.getCurrent_items();
            ResultSet resultSet;
            Statement statement;
            Scanner scan = ((InventoryManagementApp)app).getScan();
            String username;
            String password;

            System.out.println("Input User Name and Password: ");
            try {
                  int log = 1;//switch back to one later
                  while(log==1) {
                        username = scan.next();
                        password = scan.next();
                        statement = connection.createStatement();

                        resultSet = statement.executeQuery("select * from inventoryapp.user");

                        while (resultSet.next()) {
                              if (resultSet.getString(2).equals(username) &&
                                      resultSet.getString(3).equals(password)) {
                                    log = 0;
                                    break;
                              }
                        }
                        if(log==1){
                              System.out.println("Incorrect Email or Password.\nTry Again:");}
                  }
                  if(log == 0){
                        System.out.println("Welcome.");
                        return new InventoryScreen(i);
                        //return null;
                  }

            } catch (SQLException ex){
                  ex.printStackTrace();
            }

            return null;
      }
}

