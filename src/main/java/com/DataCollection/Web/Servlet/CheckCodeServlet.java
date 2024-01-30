package com.DataCollection.Web.Servlet;

import java.io.IOException;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckCodeServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int imgwidth=100;
        int imgheight=40;
        //1.创建图片对象，在内存中图片（验证码图片对象）
        BufferedImage image=new BufferedImage(imgwidth,imgheight,BufferedImage.TYPE_INT_RGB);  //也可以指定读取image=imageIO.read(new file())
        //2.美化图片
        Graphics g=image.getGraphics(); //获得画笔对象

        //设置画笔颜色
        g.setColor(Color.pink);
        //在创建的图片对象大小中填充矩形，颜色为上面设置的颜色，第一,二个参数是起始点的x,y,第三，四个参数是有多宽，有多高
        g.fillRect(0, 0, imgwidth, imgheight);

        //重新设置画笔颜色
        g.setColor(Color.yellow);//画框边缘颜色
        //在image上画边框，第一,二个参数是起始点的x,y,第三，四个参数是有多宽，有多高，注意：边框占一个像素，所以需要宽和高-1才能覆盖全部
        g.drawRect(0, 0, imgwidth-1, imgheight-1);

        //随机设置验证码的值
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        //随机在image中写字符串，第三，四个参数是画的位置
        for(int i=1;i<5;i++) {
            int index=random.nextInt(str.length());  //随机选取字母字符
            g.setFont(new Font("宋体", Font.PLAIN, 20));  //设置画笔大小
            sb.append(str.charAt(index));//将随机验证码置于stringbuilder中
            g.setColor(Color.blue);  //画笔颜色
            g.drawString(str.charAt(index)+"",imgwidth/5*i ,25);
        }

        //将验证码存储与session对象中,用于loginservlet中的验证码验证
        String session_code=sb.toString();
        req.getSession().setAttribute("session_code", session_code);

        //随机画干扰线,第一,二个参数是起始点的x,y,第三，四个参数是最后一个点的x,y
        int x1=0,y1=0,x2=0,y2=0;
        for(int i=0;i<=8;i++) {  //画8次线条
            x1=random.nextInt(imgwidth);
            y1=random.nextInt(imgheight);
            x2=random.nextInt(imgwidth);
            y2=random.nextInt(imgheight);
            g.setColor(Color.gray);
            g.drawLine(x1, y1, x2, y2);
        }

        //3.图片显示在页面上
        ImageIO.write(image, "jpg", resp.getOutputStream());  //将图片写入指定文件(第三个参数是指定的位置Fileoutpotstream(new File(""))
    }


}