package com.company.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;


public class MyPostgresConnectionTest {

    @InjectMocks private FakeConnection connection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test //mock the Data base connection
    public void testTestConnection() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        int value = connection.executeQuery("");
        assertEquals(value, 1);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
