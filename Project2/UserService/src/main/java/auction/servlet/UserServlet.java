package auction.servlet;

import auction.models.User;
import auction.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    /*
    * service -- runs for each and every request made, after the init method
                 has run at least once.
     */
        System.out.println("Servicing MyServlet");
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        /*
         * destroy -- gets called when the server needs it to be.
         *           most likely at server shutdown.
         * */
        System.out.println("Destroy MyServlet");
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        /*
         * init -- beginning of the servlet lifecycle
         *         it runs once, if the servlet has never been initialize
         *         when the first request to a matching url pattern is made.
         *         You can preload servlet with <load-on-startup> in the web.xml.
         * */
        System.out.println("Init MyServlet");
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try
        {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for(int i=0; i<cookies.length; i++)
                {
                    User loggedInUser = userService.retrieveByName(cookies[i].getValue());
                    if(loggedInUser.getRole() == 2)
                    {
                        response.setCharacterEncoding("UTF-8");
                        PrintWriter out = response.getWriter();
                        List<User> users = userService.retrieveAll();
                        ObjectMapper om = new ObjectMapper();
                        String json = om.writeValueAsString(users);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.print(json);
                        out.flush();
                    }
                }
            }
        }catch(Exception e)
        {
            response.setStatus(206);
            PrintWriter out = response.getWriter();
            out.write("Couldn't retrieve users");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        int userRole = Integer.parseInt(request.getParameter("userRole"));
        String userCC = request.getParameter("userCC");
        response.setCharacterEncoding("UTF-8");
        try
        {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for(int i=0; i<cookies.length; i++)
                {
                    User newUser = userService.retrieveByName(cookies[i].getValue());
                    if(newUser.getRole() == 2) {
                        User updateUser;
                        updateUser = userService.retrieveByName(userName);
                        updateUser.setRole(userRole);
                        PrintWriter out = response.getWriter();
                        ObjectMapper om = new ObjectMapper();
                        String json = om.writeValueAsString(updateUser);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.write("Updated user role for user: " + updateUser );
                        out.print(json);
                        out.flush();
                    }
                    else if (newUser.getRole() == 1){
                        User updateUser;
                        updateUser = userService.retrieveByName(userName);
                        updateUser.setCreditCardNumber(userCC);
                        PrintWriter out = response.getWriter();
                        ObjectMapper om = new ObjectMapper();
                        String json = om.writeValueAsString(updateUser);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        out.write("Updated credit card for user: " + updateUser );
                        out.print(json);
                        out.flush();
                    }
                }
            }
        }catch(Exception e)
        {
            response.setStatus(206);
            PrintWriter out = response.getWriter();
            out.write("Something Went Wrong");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        response.setCharacterEncoding("UTF-8");
        try
        {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for(int i=0; i<cookies.length; i++)
                {
                    User newUser = userService.retrieveByName(cookies[i].getValue());
                    if(newUser.getRole() == 2)
                    {
                        User updateUser;
                        updateUser = userService.retrieveByName(userName);
                        boolean worked = userService.deleteUser(updateUser);
                        PrintWriter out = response.getWriter();
                        ObjectMapper om = new ObjectMapper();
                        if(worked)
                            out.println("User: " + userName + " has been deleted.");
                        else
                            out.println("User: " + userName + " is unable to be deleted.");
                    }
                }
            }
        }catch(Exception e)
        {
            response.setStatus(206);
            PrintWriter out = response.getWriter();
            out.write("An Error Occurred");
        }
    }
}
