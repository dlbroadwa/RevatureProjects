package company.web;

import company.connections.MyConnection;
import company.connections.MyPostgresConnection;
import company.screens.InventoryScreen;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebFault;
import java.io.IOException;
import java.sql.*;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public Connection connection;

    public LoginServlet() {
        MyConnection connect = new MyPostgresConnection();
        this.connection = MyPostgresConnection.connection;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Hello kind Sir!");
        //get
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(email.equals("dlbroadwa@gmail.com")&&password.equals("password12")){
            response.sendRedirect("welcome.html");
        } else {response.sendRedirect("index.html");}


      /*  ResultSet resultSet;
        Statement statement;

        try {
            int log = 0;//switch back to one later
            statement = connection.createStatement();
                resultSet = statement.executeQuery("select * from inventoryapp.users");

                while (resultSet.next()) {
                    if (resultSet.getString(2).equals(email) &&
                            resultSet.getString(3).equals(password)) {
                        log = 0;
                        break;
                    }
                }
                if(log==0) {
                    response.sendRedirect("welcome.html");
                } else {response.sendRedirect("index.html");}

        } catch (SQLException ex) {
            ex.printStackTrace();
        }*/
    }


}
