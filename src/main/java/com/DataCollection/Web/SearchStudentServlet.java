package com.DataCollection.Web;

import com.DataCollection.Pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchStudentServlet extends HttpServlet {
    /**
     * 1. Get request parameters;
     * 2. Send SQL query to search the student's information;
     * 3. Save the result into request area;
     * 4. Send the request to showStudent.jsp page.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = new ArrayList<User>();
        for(int i=0;i<10;i++){
            int t = i + 1;
            userList.add(new User(t,"name"+t,"password","123"+t+"@123.com"));
        }
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("-/login.jsp").forward(req,resp);

    }
}
