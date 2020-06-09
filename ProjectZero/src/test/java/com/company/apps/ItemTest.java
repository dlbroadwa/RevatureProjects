package com.company.apps;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ItemTest {
    private Item Fake = new Item();
    @Test
    public void getItem_idtest() {
        int v=1;
        Fake.setItem_id(v);
        assertEquals(1,Fake.getItem_id());
    }

    @Test
    public void setItem_idtest() {
        int v=1;
        Fake.setItem_id(v);
        assertEquals(1,Fake.getItem_id());
    }

    @Test
    public void getItem_nametest() {
        String s= "big";
        Fake.setItem_name(s);
        assertEquals("big",Fake.getItem_name());
    }

    @Test
    public void setItem_nametest() {
        String s= "big";
        Fake.setItem_name(s);
        assertEquals("big",Fake.getItem_name());
    }

    @Test
    public void getItem_pricetest() {
       /* BigDecimal f =1;
        Fake.setItem_price(f);
        assertEquals(1.0f,Fake.getItem_price(),0.05);*/
    }

    @Test
    public void setItem_pricetest() {
    /*    float f =1;
        Fake.setItem_price(f);
        assertEquals(1.0f,Fake.getItem_price(),0.05);*/
    }

    @Test
    public void getQuantitytest() {
        int v=100;
        Fake.setQuantity(v);
        assertEquals(100,Fake.getQuantity());
    }

    @Test
    public void setQuantitytest() {
        int v=100;
        Fake.setQuantity(v);
        assertEquals(100,Fake.getQuantity());
    }
}