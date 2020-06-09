package company.web;

import company.apps.Inventory;
import company.apps.Item;
import company.connections.MyConnection;
import company.connections.MyPostgresConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(name = "WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    public Connection connection;
    public Inventory inv;
    private ArrayList<Item> current_items;
    private float current_cash;

    public WelcomeServlet() {
        this.connection = MyPostgresConnection.connection;
        this.inv = new Inventory(connection);
        this.current_cash = inv.getCurrent_cash();
        this.current_items = inv.getCurrent_items();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello kind fghfghfSir!");

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
