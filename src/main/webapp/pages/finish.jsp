<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Test Complete</title>
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
  </style>
</head>
<body>
<div class="record">
  <h1>Test Complete</h1>
  <p>Your test has been completed successfully.</p>
  <p>${sessionScope.fin_msg}</p>
<%--  <a href="${pageContext.request.contextPath}/pages/welcome.jsp">Return to Welcome Page</a>--%>
</div>
</body>
</html>