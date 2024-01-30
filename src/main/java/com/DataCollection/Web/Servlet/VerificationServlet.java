package com.DataCollection.Web.Servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DataCollection.Service.Impl.UserServiceImpl;
import com.DataCollection.Service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import com.DataCollection.Dao.Impl.UserDaoImpl;
import com.DataCollection.Pojo.User;

public class VerificationServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String captcha = req.getParameter("captcha");


        UserService userService = new UserServiceImpl();
        User resUser = userService.login(new User(null, username, password, null));
        //获取session，并从中获取验证码
        HttpSession httpSession = req.getSession();
        String verifyCode = httpSession.getAttribute("verify_code").toString();
        httpSession.removeAttribute("verify_code"); //必须立刻删除，防止复用

        if(captcha.equalsIgnoreCase(verifyCode)){ //验证码正确
            if(resUser == null){ // 密码错误
                httpSession.setAttribute("log_msg", "username or password is wrong.");
//            req.getRequestDispatcher("/data/login.jsp").forward(req,resp);
                resp.sendRedirect("/data/pages/login.jsp");
            }else{ // 密码正确
                System.out.println("now user is:" + resUser.toString());
                httpSession.setAttribute("user", resUser);
                httpSession.setAttribute("user_id",resUser.getId());
                httpSession.setAttribute("user_name",resUser.getUsername());
                httpSession.setAttribute("log_msg", null);
                resp.sendRedirect("/data/pages/success.jsp");
            }
        }else{ // 验证码错误
            httpSession.setAttribute("log_msg", "captcha is wrong.");
//            req.getRequestDispatcher("/data/login.jsp").forward(req,resp);
            resp.sendRedirect("/data/pages/login.jsp");
        }

    }
}
