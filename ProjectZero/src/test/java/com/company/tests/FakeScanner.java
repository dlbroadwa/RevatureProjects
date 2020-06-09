package com.company.tests;

import java.util.Scanner;

public class FakeScanner {

    public String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
