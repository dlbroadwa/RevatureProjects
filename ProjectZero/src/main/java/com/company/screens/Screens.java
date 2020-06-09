package com.company.screens;

import com.company.apps.Application;
import com.company.apps.Inventory;

import java.sql.SQLException;

public interface Screens {
    Screens display(Application app, Inventory i) throws SQLException;
}
