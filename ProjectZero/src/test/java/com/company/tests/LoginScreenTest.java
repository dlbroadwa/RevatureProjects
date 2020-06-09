package com.company.tests;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class LoginScreenTest {

    @Test public void testUserInput(){
        FakeScanner testInput= new FakeScanner();

        String input = "abc";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals("abc", testInput.getInput());
    }
    @Test
    public void display() {
    }
}