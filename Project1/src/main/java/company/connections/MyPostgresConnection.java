package company.connections;

import company.apps.Application;
import company.apps.InventoryManagementApp;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyPostgresConnection extends MyConnection {
    public static Connection connection = null;

    public MyPostgresConnection() {
        this.url = "jdbc:postgresql://dlbroadwa.cpbqys5iu3x8.us-east-2.rds.amazonaws.com:5432/postgres";
        this.username = "postgres";
        this.password = "Espadapooh4";
        this.schema = "inventoryapp";

        this.connection = getConnection();
        testConnection();
    }
/**********************CONNECTS TO THE DATABASE AND CALLS THE APPLICATION **/
    /************************************* ***************************************/
    public void testConnection(){

        try {
            // tests if database is connected
            //this.connection = getConnection();
            if(connection != null) {

                //call the application
                Application app = new InventoryManagementApp(connection);
                app.run();

            } else {
                System.out.println("Connection failed");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // if connection worked close it
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    // this will only happen if the connection has already been closed
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public Connection getConnection() {
        Connection connect = null;
        try{
             connect = DriverManager.getConnection("jdbc:postgresql://dlbroadwa.cpbqys5iu3x8.us-east-2.rds.amazonaws.com:5432/postgres",
                "postgres","Espadapooh4");}
        catch (SQLException throwables){
            // this will only happen if the connection has already been closed
            throwables.printStackTrace();
        }
        return connect;
    }
}
