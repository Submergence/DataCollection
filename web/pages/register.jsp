<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Page</title>
    <link rel="stylesheet" type="text/css" href="../css/register.css"/>
    <script>
        function validateForm() {
            var pwd1 = document.getElementById("pwd").value;
            var pwd2 = document.getElementById("pwdA").value;
            var username = document.getElementById("user").value;

            if (username.trim() === "") {
                document.getElementById("log_msg_re").textContent = "Username cannot be empty.";
                return false; // 阻止表单提交
            }
            if (pwd1.trim() === "") {
                document.getElementById("log_msg_re").textContent = "Password cannot be empty.";
                return false; // 阻止表单提交
            }
            if (pwd2.trim() === "") {
                document.getElementById("log_msg_re").textContent = "Password confirmation cannot be empty.";
                return false; // 阻止表单提交
            }
            if (pwd1 !== pwd2) {
                document.getElementById("log_msg_re").textContent = "Passwords should be the same!";
                return false; // 阻止表单提交
            }

            return true; // 允许表单提交
        }
    </script>
</head>
<body>
<div class="topDiv">
    <p class="topWord">Register Page of the Test Web System</p>
</div>
<div class="secDiv">
    <div class="failDiv">
        <div id="log_msg_re" style="color: red;">${log_msg_re}</div>
    </div>
    <form action="/data/registerServlet" method="post" onsubmit="return validateForm();">
        <div class="inputDiv">
            <label for="user">Username:      </label>
            <input type="text" name="username" class="formInput" id="user" placeholder="Input your user name."/>
        </div>
        <div class="inputDiv">
            <label for="pwd">Password :     </label>
            <input type="password" name="password" class="formInput" id="pwd" placeholder="Input your password."/>
        </div>
        <div class="inputDiv">
            <label for="pwd">Password again :     </label>
            <input type="password" name="passwordAgain" class="formInput" id="pwdA" placeholder="Input your password."/>
        </div>
        <div class="inputDiv">
            <label for="email">Email  :     </label>
            <input type="text" name="email" class="formInput" id="email" placeholder="Email." style="..."/>
        </div>
        <div class="inputDiv">
            <input type="submit" value="SUBMIT" class="register_btn"/>
        </div>
    </form>
</div>
</body>
</html>
