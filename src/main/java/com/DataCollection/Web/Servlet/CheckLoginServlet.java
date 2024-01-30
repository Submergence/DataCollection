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

import org.apache.commons.beanutils.BeanUtils;

import com.DataCollection.Dao.Impl.UserDaoImpl;
import com.DataCollection.Pojo.User;

@WebServlet("/checkLoginServlet")
public class CheckLoginServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");
        //2.获取用户的请求
//        User user=new User();
//        Map<String, String[]> pMap=req.getParameterMap();
//        //3.使用BeanUtil封装对象
//        try {
//            BeanUtils.populate(user, pMap);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        //4.现获取前端填写的验证码,比较验证码
        System.out.println(req.getParameter("username"));
        System.out.println(req.getParameter("password"));

        String exc=req.getParameter("verifycode");//获取前端用户填写的验证码
        HttpSession htp=req.getSession();  //获取session
        String excode=(String) htp.getAttribute("session_code");  //获取后端checkcode随机验证码
        //为防止验证码重复使用，session中的session_code一旦获得，就必须删除
        htp.removeAttribute("session_code");
        if(excode!=null && excode.equalsIgnoreCase(exc)) {
            //忽略字母大小写，比较验证码
            //如果验证码正确，再比较用户的用户名和密码
            //验证码正确
            //5.创建userDao对象
            UserDaoImpl userDaoImpl=new UserDaoImpl();  //调用与数据库的函数

            User lu=userDaoImpl.queryUserByUsernameAndPassword(req.getParameter("username"), req.getParameter("password"));
            if(lu!=null) {
                //如果登录成功
                //保存数据，用户信息
                htp.setAttribute("user", lu);  //在session中保存用户的信息
                htp.setAttribute("username", lu.getUsername());//在session中存储用户名
                //重定向到success.jsp页面
                resp.sendRedirect("/success.jsp");
            }
            else {//用户名或密码不正确
                req.setAttribute("log_msg", "用户名或密码错误");  //存储错误信息，用request域存储
                //请求转发，重新回到登录页面
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }else {//验证码不正确
            req.setAttribute("log_msg", "验证码错误");  //存储错误信息，用request域存储
            req.getRequestDispatcher("/login.jsp").forward(req, resp);  //请求转发，重新回到登录页面
        }



    }


}
