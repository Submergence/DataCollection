<%--
  Created by IntelliJ IDEA.
  User: gengyifan
  Date: 2023/6/28
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Option Page</title>
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
            text-align: center;
        }
        button {
            padding: 10px 20px;
            font-size: 18px;
            margin-top: 20px;
            cursor: pointer;
        }
        button:disabled {
            background-color: grey;
            color: white;
        }
        #logoutButton {
            position: fixed;
            right: 20px;
            top: 20px;
        }
        #logoutContainer {
            position: fixed;
            right: 20px;
            top: 20px;
            display: flex;
            align-items: center;
        }
        #userIdDisplay {
            margin-right: 150px;
            size: 10px;
        }
    </style>
    <script type="text/javascript">
        window.onload = function (){
            var userIdDisplay = document.getElementById('userIdDisplay');
            var username = '${sessionScope.user_name}';
            userIdDisplay.textContent = 'Hi, ' + username + " !";
            // Check if the user is logged in
            var user = '${sessionScope.user_id}';
            if (!user) {
                sessionStorage.setItem('log_msg', "you have not login, please login before test.")
                window.location.href = '${pageContext.request.contextPath}/pages/login.jsp';
            }

            <%--document.getElementById('beginButton').onclick = function() {--%>
            <%--    // Disable the button on click--%>
            <%--    this.disabled = true;--%>

            <%--    // Send a GET request to the servlet--%>
            <%--    var xhr = new XMLHttpRequest();--%>
            <%--    xhr.open('GET', '${pageContext.request.contextPath}/buildContainer', true);--%>
            <%--    xhr.onreadystatechange = function() {--%>
            <%--        if (xhr.readyState === 4 && xhr.status === 200) {--%>
            <%--            // Wait for 5 seconds before opening the new page--%>
            <%--            setTimeout(function() {--%>
            <%--                window.open(xhr.responseText, '_blank');--%>
            <%--            }, 5000);--%>

            <%--        }else{--%>
            <%--            // Display an alert with the error message--%>
            <%--            alert("Error occurred: " + xhr.responseText);--%>
            <%--            // Enable the button so the user can try again--%>
            <%--            document.getElementById('beginButton').disabled = false;--%>
            <%--        }--%>
            <%--    };--%>
            <%--    xhr.send();--%>
            <%--};--%>
            document.getElementById('beginButton').onclick = function() {
                var beginButton = document.getElementById('beginButton');
                var logMsgElement = document.getElementById('log_msg');

                // Disable the button and change text to show it's loading
                beginButton.disabled = true;
                beginButton.textContent = 'Loading...';
                logMsgElement.textContent = 'Starting the test environment, please wait...';

                // Send a GET request to the servlet
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '${pageContext.request.contextPath}/buildContainer', true);
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4) {
                        beginButton.disabled = false;
                        beginButton.textContent = 'Begin';
                        if (xhr.status === 200) {
                            // 从servlet接收到的信息
                            var serverResponse = xhr.responseText;
                            logMsgElement.textContent = serverResponse; // 显示servlet返回的消息
                            setTimeout(function() {
                                window.open(xhr.responseText, '_blank');
                            }, 3000);
                        } else {
                            // Display an alert with the error message
                            var response = JSON.parse(xhr.responseText);
                            var errorMsg = response.err_log || "An unknown error occurred"; // Use a default error message if 'err_log' is not set

                            // Display an alert with the 'err_log' message
                            alert("Error occurred: " + errorMsg);
                            logMsgElement.textContent = 'Error: ' + errorMsg;
                        }
                    }
                };
                xhr.send();
            };

            document.getElementById('logoutButton').onclick = function() {
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '${pageContext.request.contextPath}/logout', true); // Assuming '/logout' is the URL to logout on your server
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        // If the request is successful, clear any session data
                        sessionStorage.removeItem('user');
                        // And redirect to the login page
                        window.location.href = '${pageContext.request.contextPath}/pages/login.jsp';
                    }
                };
                xhr.send();
            };

            document.getElementById('finishButton').onclick = function() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', '${pageContext.request.contextPath}/finish', true);
                document.getElementById('log_msg').textContent = 'removing record resource......';
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4) {
                        var logMsgElement = document.getElementById('log_msg');
                        if (xhr.status === 200) {
                            logMsgElement.textContent = 'testing finished, reloading......';
                            setTimeout(function() {
                                window.location.href = '${pageContext.request.contextPath}/pages/finish.jsp';
                            }, 1000);
                        } else {
                            logMsgElement.textContent = 'error: ' + '${sessionScope.log_msg}';
                        }
                    }
                };
                xhr.send();
            };
        }
    </script>
</head>
<body>
<div class="record">
    <h1>Welcome to the Test Web System</h1>
    <p>Please click the 'BEGIN' button below to start your testing session in 5 seconds. </p>
<%--    <form action="${pageContext.request.contextPath}/buildContainer" method="get">--%>
<%--        <button type="submit">BEGIN!</button>--%>
    <form>
        <button type="button" id="beginButton">Begin</button>
        <button type="button" id="finishButton">Finish</button>
    </form>
    <div id="logoutContainer">
        <p id="userIdDisplay"></p>
        <button type="button" id="logoutButton">Logout</button>
    </div>
    <p id="log_msg" >${sessionScope.log_msg}</p>
</div>
</body>
</html>




