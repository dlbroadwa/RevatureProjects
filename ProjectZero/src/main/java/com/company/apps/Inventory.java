package com.company.apps;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.math.BigDecimal;
public class Inventory {
    private ArrayList<Item> current_items;
    private BigDecimal current_cash;

    protected Connection connection;
    protected Statement statement;

    public Inventory(Connection connection){
        this.connection = connection;
        populateInventory();

    }

    public void populateInventory(){
        Item x;
        String name;
        current_items = new ArrayList<>();
        ArrayList<BigDecimal> prices= new ArrayList<>();
        int quantity;
        int item_id;
        BigDecimal balance =BigDecimal.valueOf(0);

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from inventoryapp.pockets");

            while (rs.next()) {
                balance=rs.getBigDecimal(2);
            }
            setCurrent_cash(balance);

            rs = statement.executeQuery("select * from inventoryapp.inventory");
            while (rs.next()) {
                prices.add(rs.getBigDecimal(3));
            }

            ResultSet resultSet = statement.executeQuery("select * from " +
                    "inventoryapp.current_inventory");
            int id = 0;
            while (resultSet.next()) {
                x= new Item();

                //Read from Database table then assign as variables
                item_id=resultSet.getInt(1);
                name= resultSet.getString(2);
                quantity = resultSet.getInt(3);

                //assign local variables to object variables
                x.setItem_id(item_id);
                x.setItem_name(name);
                x.setQuantity(quantity);
                x.setItem_price(prices.get(id));
                current_items.add(id,x);
                id++;
            }
            setCurrent_items(current_items);

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<Item> getCurrent_items() {
        return current_items;
    }

    public void setCurrent_items(ArrayList<Item> current_items) {
        this.current_items = current_items;
    }
    public BigDecimal getCurrent_cash() {
        return current_cash;
    }

    public void setCurrent_cash(BigDecimal current_cash) {
        this.current_cash = current_cash;
    }
}
