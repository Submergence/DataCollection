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
    <title>Welcome Page</title>
    <script>
        function genClient() {

        }
    </script>
</head>
<body>
    <div class="topDiv">
        <p class="topWord">Welcome to the Test Web System</p>
    </div>
    <div class="secDiv">
        <a href="login_new.jsp">Click to begin test.</a>

    </div>
    <div id="guacamole-record">
        <form action="${pageContext.request.contextPath}/buildContainer" method="get">
            <button type="submit">BEGIN! </button>
        </form>
    </div>

</body>
</html>




