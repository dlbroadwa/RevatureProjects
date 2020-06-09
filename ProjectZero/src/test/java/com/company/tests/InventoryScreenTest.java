package com.company.tests;

import com.company.apps.Inventory;
import com.company.apps.Item;
import com.company.screens.InventoryScreen;
import com.company.screens.Screens;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class InventoryScreenTest {
    private Item fakeItem;
    @Mock
    private Inventory Fake;
    private Screens f= new InventoryScreen(Fake);

    ArrayList<Item> list= new ArrayList<>();


    @Test
    public void printCurrentInventory() {
        Item x = new Item();
        x.setItem_name("a");
        x.setQuantity(1);
        x.setItem_id(1);

        list.add(x);
        //assertTrue(printCurrentInventory(list).);
    }

    @Test
    public void displayTest() {
    }
}