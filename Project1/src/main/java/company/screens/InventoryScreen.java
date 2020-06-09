package company.screens;

import company.apps.Application;
import company.apps.Inventory;
import company.apps.Item;

import java.sql.Connection;

import java.util.ArrayList;

/**********************Calls screen to print your current inventory *********** **/

public class InventoryScreen implements Screens {

    protected Connection connection;
    protected Inventory inventory;
    protected ArrayList<Item> current_items;

    public InventoryScreen(Inventory i){
        this.inventory= i;
    }
    /********************** and controls the users input ***************************************/
    public Screens display(Application app, Inventory i) {
        this.current_items = inventory.getCurrent_items();
        printCurrentInventory(current_items);

        return null;
    }

    public InventoryScreen printCurrentInventory(ArrayList<Item> current_items){
        int x = 0;

        System.out.print("Your Current Inventory: \n"+
                        "--------------------------\n"+
                        "ID|  Name  | Quantity\n");

        while (x<current_items.size()) { //returns false if no more rows
            System.out.print(
                      current_items.get(x).getItem_id()+" | "
                    + current_items.get(x).getItem_name()+" | "
                    + current_items.get(x).getQuantity()+"\n"
                    );
            x++;
        } /**********************  sends it back to the display method***************************************/
        return null;
    }


}
