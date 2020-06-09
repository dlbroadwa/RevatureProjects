package com.company.services;

import com.company.apps.Application;
import com.company.apps.Inventory;
import com.company.apps.InventoryManagementApp;
import com.company.apps.Item;
import com.company.connections.MyPostgresConnection;
import com.company.screens.InventoryScreen;
import javafx.stage.Screen;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Function{
    protected Connection connection;
    protected Inventory inventory;
    protected ArrayList<Item> current_items;
    protected Application app;
    protected BigDecimal current_cash;

    public Inventory function(Application app, Connection connection, Inventory x) throws SQLException {
        /******* The Function class is used to manipulate local Array objects ************************************/
        /******** ***************************************/
        this.inventory = x;
        this.current_items = inventory.getCurrent_items();
        this.current_cash= inventory.getCurrent_cash();
        this.connection=connection;
        this.app=app;

        /******** CONTROLS THE INVENTORY PURCHASE PORTION***************************************/
        printSelection();
        replenish();
        return inventory;
        /******** END ***************************************/
    }

    public void replenish(){
        int selection=999;
        int quantity=0;
        Scanner scan = ((InventoryManagementApp)app).getScan();
        /******** while loop controls the users input ***************************************/
        while (selection != 0) {

            System.out.println("Enter the ID# of the item you would like to purchase: \n" +
                    "Enter \"0\" to exit | \"411\" to see your inventory");
            selection = scan.nextInt();

            while(selection < 0 || (selection > (current_items.size()) && selection != 411)) {
                System.out.println("Invalid Selection\n"+
                        "Re-enter the ID# of the item you would like to purchase: "
                        +"Enter \"0\" to exit | \"411\" to see your inventory");
                selection = scan.nextInt();
            }
/******** if the user inputs the correct input 0 -4 or 411 they will cont. ***************************************/
            switch (selection){
                case 0: System.out.println("GoodBye.");
                    break;
                case 411: //print the current inventory
                    InventoryScreen a = new InventoryScreen(inventory).printCurrentInventory(current_items);
                    selection=999;
                break;

                default: System.out.println("How many units of "+
                        current_items.get(selection-1).getItem_name() +
                        " would like to purchase?: ");
                    quantity= scan.nextInt();//make dmmy proof
                    math(selection-1,quantity);
                    break; }
/*************Utilizing Default lets me take the input from *********************
             * ***********   a variable range of inputs   *************************************************/
        }
    }
    /******** prints the inventory screen to make purchases ***************************************/
    public void printSelection() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from inventoryapp.inventory");

        System.out.print("Online Market:  \n"+
                "--------------------------\n"+
                "ID|  Name  | Cost per Unit\n");
        while (resultSet.next()) {
            System.out.print(
                    resultSet.getInt(1)+" | "
                            + resultSet.getString(2)+" | "
                            + resultSet.getFloat(3)+"\n");
        }
    }
/***********************used with the switch loop and default aspect to update the objects **/
    /********************** and controls the users input ***************************************/

    public void math(int item,int quantity){
        BigDecimal cost = current_items.get(item).getItem_price().multiply(BigDecimal.valueOf(quantity));
        int oldq= current_items.get(item).getQuantity();

        if (1 == cost.compareTo(current_cash)){
            System.out.println("ALERT! Insufficient funds");
        } else{
            System.out.println("You have purchased "+quantity+" unit(s) of "+
                current_items.get(item).getItem_name()+" for $"+ cost);
            BigDecimal cash = current_cash.subtract(cost);
            inventory.setCurrent_cash(cash);
            current_items.get(item).setQuantity(oldq + quantity);
            inventory.setCurrent_items(current_items);
        }

    }
}
