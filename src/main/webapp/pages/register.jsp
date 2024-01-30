<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Register Page</title>
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
    .register_btn {
      width: 100%;
      padding: 10px;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    .register_btn:hover {
      background-color: #0056b3;
    }
    .error {
      color: red;
    }
  </style>
  <script>
    function validateForm() {
      var pwd1 = document.getElementById("pwd").value;
      var pwd2 = document.getElementById("pwdA").value;
      var username = document.getElementById("user").value;

      if (username.trim() === "") {
        document.getElementById("log_msg_re").textContent = "Username cannot be empty.";
        return false;
      }
      if (pwd1.trim() === "") {
        document.getElementById("log_msg_re").textContent = "Password cannot be empty.";
        return false;
      }
      if (pwd2.trim() === "") {
        document.getElementById("log_msg_re").textContent = "Password confirmation cannot be empty.";
        return false;
      }
      if (pwd1 !== pwd2) {
        document.getElementById("log_msg_re").textContent = "Passwords should be the same!";
        return false;
      }

      return true;
    }
  </script>
</head>
<body>
<div class="record">
  <h1>Register Page of the Test Web System</h1>
  <div class="failDiv">
    <div id="log_msg_re" style="color: red;">${log_msg_re}</div>
  </div>
  <form action="/data/registerServlet" method="post" onsubmit="return validateForm();">
    <div class="inputDiv">
      <label for="user">Username:</label>
      <input type="text" name="username" class="formInput" id="user" placeholder="Input your user name." required/>
    </div>
    <div class="inputDiv">
      <label for="pwd">Password:</label>
      <input type="password" name="password" class="formInput" id="pwd" placeholder="Input your password." required/>
    </div>
    <div class="inputDiv">
      <label for="pwdA">Password again:</label>
      <input type="password" name="passwordAgain" class="formInput" id="pwdA" placeholder="Input your password again." required/>
    </div>
    <div class="inputDiv">
      <label for="email">Email:</label>
      <input type="text" name="email" class="formInput" id="email" placeholder="Email." required/>
    </div>
    <div class="inputDiv">
      <input type="submit" value="SUBMIT" class="register_btn"/>
    </div>
  </form>
</div>
</body>
</html>