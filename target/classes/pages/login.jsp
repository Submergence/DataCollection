<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }
        .record {
            width: 400px;
            margin: 0 auto;
            padding-top: 100px;
        }
        .inputDiv {
            margin-bottom: 15px;
        }
        .formInput {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
        }
        .captchaDiv {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .captchaInput {
            width: calc(100% - 130px);
        }
        .login_btn {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .login_btn:hover {
            background-color: #0056b3;
        }
        .error {
            color: red;
            display: none;
        }
    </style>
    <script>
        function refreshCode(){
            img = document.getElementById("captchaCode"); //获取验证码图片对象
            var time = new Date().getTime();  //时间戳
            img.src = "${pageContext.request.contextPath}/checkCode?" + time;
        }
        function reg_onClick(){
            window.location.href="${pageContext.request.contextPath}/pages/register.jsp";
        }

        function validateForm() {
            const username = document.getElementById('user').value;
            const password = document.getElementById('pwd').value;
            const captcha = document.getElementById('captcha').value;

            if (username === '') {
                document.getElementById('usernameError').style.display = 'block';
                return false;
            } else {
                document.getElementById('usernameError').style.display = 'none';
            }

            if (password === '') {
                document.getElementById('passwordError').style.display = 'block';
                return false;
            } else {
                document.getElementById('passwordError').style.display = 'none';
            }

            if (captcha === '' || captcha.length !== 4) {
                document.getElementById('captchaError').style.display = 'block';
                return false;
            } else {
                document.getElementById('captchaError').style.display = 'none';
            }

            return true;
        }
    </script>
</head>
<body>
<div class="record">
    <h1>Login Page of the Test Web System</h1>
    <div class="failDiv">
        <div style="color: red;">${log_msg}</div>
    </div>
    <form id="loginForm" action="${pageContext.request.contextPath}/verify" method="post" onsubmit="return validateForm()">
        <div class="inputDiv">
            <label for="user">Username:</label>
            <input type="text" name="username" class="formInput" id="user" placeholder="Enter your username" required/>
            <div id="usernameError" class="error">Username is required</div>
        </div>
        <div class="inputDiv">
            <label for="pwd">Password:</label>
            <input type="password" name="password" class="formInput" id="pwd" placeholder="Enter your password" required/>
            <div id="passwordError" class="error">Password is required</div>
        </div>
<%--        <div class="inputDiv captchaDiv">--%>
<%--            <label for="captcha">Captcha:</label>--%>
<%--            <input type="text" name="captcha" class="formInput captchaInput" id="captcha" placeholder="Enter captcha" required/>--%>
<%--            <img src="${pageContext.request.contextPath}/captchaServlet" title="Refresh" id="captchaCode" onclick="refreshCode()"/>--%>
<%--            <div id="captchaError" class="error">Captcha is required</div>--%>
<%--        </div>--%>
        <div class="inputDiv" style="display: flex; justify-content: space-between; align-items: center;">
            <div style="flex-grow: 1;">
                <label for="captcha">Captcha:</label>
                <input type="text" name="captcha" class="formInput" id="captcha" placeholder="Enter captcha" required/>
                <div id="captchaError" class="error">Captcha is required</div>
            </div>
            <img src="${pageContext.request.contextPath}/captchaServlet" title="Refresh" id="captchaCode" onclick="refreshCode()"/>
        </div>
        <div class="inputDiv">
            <input type="submit" value="LOGIN" class="login_btn"/>
        </div>
        <div class="inputDiv">
            <button type="button" onclick="reg_onClick()" class="login_btn">REGISTER</button>
        </div>
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