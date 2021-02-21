package com.company.tests;

import com.company.services.Function;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FunctionTest {

    @Test
    //accepts app /connection/ inventory > returns inventory throws SQLException
    public void function() {
    }

    @Test
    //does it add new items to the inventory?
    public void replenish() {
    }

    @Test
    //throws SQLException
    public void printSelection() {
    }
    //accepts two ints
    //public void givenNull_MathThrows()

    @Test//(expected = AssertionError.class)
    public void whenMathCalledVerified() throws Exception{
        Function function = mock(Function.class);
        doNothing().when(function).math(isA(Integer.class), isA(Integer.class)); //doNothing is defaul
        function.math(1, 1);
        verify(function, times(1)).math(1, 1);
    }


}