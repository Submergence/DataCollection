package com.DataCollection.Web.Servlet;

import com.DataCollection.Pojo.User;
import com.DataCollection.Service.Impl.UserServiceImpl;
import com.DataCollection.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    /**
     * Steps：1. Get parameters of the request；
     *        2. Check the code(correct);
     *        3. Check the Username and Password(correct);
     *        4. Use UserService save the parameters to database;
     *        5. Jump to register_success.html;
     *        6. Jump back to register page(Incorrect).
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        HttpSession httpSession = request.getSession();
        UserService userService = new UserServiceImpl();
        Boolean isSuccess = userService.existsUsername(username);
        System.out.println(isSuccess);
        if(!isSuccess){
            userService.registerUser(new User(null, username, password, email));
            httpSession.setAttribute("log_msg", "Register successes! Now you can login the system.");
            response.sendRedirect("/data/pages/login.jsp");
            System.out.println("s");
        }else{
            httpSession.setAttribute("log_msg_re", "Username is not available, please try others.");
            response.sendRedirect("/data/pages/register.jsp");
            System.out.println("f");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
