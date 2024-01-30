<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="../css/login_new.css"/>

    <script type="text/javascript">
        //切换验证码
        function refreshCode(){
            img = document.getElementById("captchaCode"); //获取验证码图片对象
            var time = new Date().getTime();  //时间戳
            img.src = "/data/checkCode?" + time;
        }

        function reg_onClick(){
            window.location.href="/data/pages/register.jsp";
        }
    </script>
</head>
<body>
<div class="topDiv">
    <p class="topWord">Login Page of the Test Web System</p>
</div>
<div class="secDiv">
    <div class="failDiv">
        <div style="color: red;">${log_msg}</div>
    </div>
    <form action="/data/verify" method="post">
        <div class="inputDiv">
            <label for="user">Username:      </label>
            <input type="text" name="username" class="formInput" id="user" placeholder="Input your user name."/>
        </div>
        <div class="inputDiv">
            <label for="pwd">Password :     </label>
            <input type="password" name="password" class="formInput" id="pwd" placeholder="Input your password."/>
        </div>
        <div class="inputDiv">
            <label for="captcha">Captcha  :     </label>
            <input type="text" name="captcha" class="formInput" id="captcha" placeholder="Captcha." style="..."/>
            <a href="javascript:refreshCode()"><img src="/data/captchaServlet" title="flash" id="captchaCode"/></a>
        </div>
        <div class="inputDiv">
            <button type="button" onclick="javascript:reg_onClick()" class="register_btn">REGISTER</button>
<%--            <form action="/data/pages/re、gister.jsp" method="GET">--%>
<%--                <input type="submit" value="REGISTER" />--%>
<%--            </form>--%>
            <input type="submit" value="LOGIN" class="login_btn"/>
        </div>
        <%-- Java 代码 --%>
        <%-- 检查验证码和输入字段 --%>
        <%-- 如果验证码为空或长度不等于4，或者用户名或密码为空，设置错误消息 --%>
        <%-- 注意：这里使用的是 JSP 标签库的 EL 表达式，推荐使用 EL 表达式替代 Java 代码 --%>
        <%-- 如果需要重定向，可以使用 response.sendRedirect 方法 --%>
        <%-- 请根据实际需求调整逻辑和错误处理 --%>

        <%
            if (request.getMethod().equalsIgnoreCase("POST")) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String captcha = request.getParameter("captcha");
                String verify_code = (String) request.getSession().getAttribute("verify_code");

                if (captcha == null || captcha.length() != 4 || username == null || password == null) {
                    request.getSession().setAttribute("log_msg", "Input has something wrong.");
                }
            }
        %>
    </form>
</div>
</body>
</html>

