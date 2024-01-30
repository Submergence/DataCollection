package com.DataCollection.Web.Servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int imgWidth = 100;
        int imgHeight = 40;

        //创建图片对象并在内存中存储图像
        BufferedImage image = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_INT_RGB);
        //美化图像
        Graphics graphics = image.getGraphics(); // 获取画笔对象
        graphics.setColor(Color.pink); //设置画笔颜色
        graphics.fillRect(0, 0, imgWidth, imgHeight); // 设置图形，坐标，宽高
        // 设置随机验证码
        String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<5;i++){
            int index = random.nextInt(string.length()); //随机选取字符
            graphics.setFont(new Font("normal", Font.PLAIN, 20)); // 设置画笔大小
            sb.append(string.charAt(index));
            graphics.setColor(Color.blue);
            graphics.drawString(string.charAt(index) + "", imgWidth/5*i, 25);
        }

        String verify_code = sb.toString();
        req.getSession().setAttribute("verify_code", verify_code);

        int x1=0, y1=0, x2=0, y2=0;
        for(int i=0; i<=8; i++){
            x1 = random.nextInt(imgWidth);
            y1 = random.nextInt(imgHeight);
            x2 = random.nextInt(imgWidth);
            y2 = random.nextInt(imgHeight);
            graphics.setColor(Color.gray);
            graphics.drawLine(x1, y1, x2, y2);
        }

        // 图像写入页面
        ImageIO.write(image, "jpg", resp.getOutputStream());

    }
}
