package company.screens;

import company.apps.Application;
import company.apps.Inventory;

import java.sql.SQLException;

public interface Screens {
    Screens display(Application app, Inventory i) throws SQLException;
}
