package company.apps;


import company.screens.InventoryScreen;
import company.screens.LoginScreen;
import company.screens.Screens;
import company.services.Function;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InventoryManagementApp extends Application {
    private Scanner scan;
    protected Connection connection;

    public Scanner getScan() {
        return scan;
    }

    public void run() throws SQLException {

        Inventory session = new Inventory(connection);
        Screens login = new LoginScreen(connection);
        Screens inventory = new InventoryScreen(session);
        Function update = new Function();


        login.display(this, session);
        inventory.display(this, session);
        System.out.println("Your account balance is "+session.getCurrent_cash());

        update.function(this, connection,session);

    }

    public InventoryManagementApp(Connection connection){
        this.connection= connection;
        this.scan = new Scanner(System.in);
    }
}
