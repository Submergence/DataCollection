<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Login</title>

  <script type="text/javascript">
    //切换验证码
    function refreshCode(){
      img=document.getElementById("vcode"); //获取验证码图片对象
      var time=new Date().getTime();  //时间戳
      img.src="${pageContext.request.contextPath }/checkCode?"+time;
    }
  </script>
</head>
<body>
<div class="record" style="width: 400px;">
  <form action="${pageContext.request.contextPath}/checkLoginServlet" method="post">
    <div class="form-group">
      <label for="user">用户名：</label>
      <input type="text" name="username" class="form-control" id="user" placeholder="请输入用户名"/>
    </div>

    <div class="form-group">
      <label for="password">密码：</label>
      <input type="text" name="password" class="form-control" id="password" placeholder="请输入密码"/>
    </div>

    <div class="form-inline">
      <label for="vcode">验证码：</label>
      <input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="请输入验证码" style="width: 120px;"/>
      <a href="javascript:refreshCode()"><img src="${pageContext.request.contextPath }/checkCode" title="看不清点击刷新" id="vcode"/></a>
    </div>
    <div style="color: red;">${log_msg}</div>
    <hr/>
    <div class="form-group" style="text-align: center;">
      <input class="btn btn btn-primary" type="submit" value="Login">
    </div>
  </form>

  <!-- 出错显示的信息框 -->
  <div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" >
      <span>&times;</span></button>
    <strong>${log_msg}</strong>
  </div>
</div>
</body>
</html>