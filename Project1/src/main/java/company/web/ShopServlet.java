package company.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.io.PrintWriter;


@WebServlet(name = "ShopServlet")
public class ShopServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set content-type header before accessing the Writer
        // databind.Object objectMapper = new ObjectMapper();
        response.setContentType("");
        PrintWriter out = response.getWriter();

        // then write the response
        out.println("<html>" +
                "<head><title>Book Description</title></head>" );

        //Get the identifier of the book to display
        String bookId = request.getParameter("bookId");
        if (bookId != null) {
            // and the information about the book and print it

        }
        out.println("</body></html>");
        out.close();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set content type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // then write the response
        out.println("<html>" +
                "<head><title> Receipt </title>");

        out.println("<h3>Thank you for purchasing your books from us " +
                request.getParameter("cardname"));
        out.close();
    }

}
