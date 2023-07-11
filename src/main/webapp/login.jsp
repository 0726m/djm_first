<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>电影网站和推荐系统</title>
  <link rel="stylesheet" type="text/css" href="<%=Const.ROOT %>res/static/css/main.css">
  <link rel="stylesheet" type="text/css" href="<%=Const.ROOT %>res/layui/css/layui.css">
  <script type="text/javascript" src="<%=Const.ROOT %>res/layui/layui.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
</head>
<body>
  <!-- top -->
  <!--<%@include file="top.jsp" %>-->	

  <div class="content content-nav-base  login-content">
    <!-- 菜单 -->
	 <!--<%@include file="menu.jsp" %>-->
    <div class="login-bg">
      <div class="login-cont w1200">
        <div class="form-box">
          <form class="layui-form" action="">
            <legend>用户登录</legend>
            <div class="layui-form-item">
              <div class="layui-inline iphone">
                <div class="layui-input-inline">
                  <i class="layui-icon layui-icon-username iphone-icon"></i>
                  <input type="tel" name="username" id="username" lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-inline iphone">
                <div class="layui-input-inline">
                  <i class="layui-icon layui-icon-password iphone-icon"></i>
                  <input id="password" type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
              </div>
            </div>
            <div class="layui-form-item login-btn">
              <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="btn-send">登录</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

<%@include file="footer.jsp" %>

  <script type="text/javascript">
   layui.config({
      base: '<%=Const.ROOT%>res/static/js/util' //存放新模块的目录
    }).use(['jquery','form'],function(){
          var $ = layui.$,form = layui.form;

          form.on('submit(btn-send)', function (data) {
              $.post("<%=Const.ROOT%>login",data.field,function(data){
            	  if(data.code=="0"){
    					layer.msg(data.msg, { icon: 6, time: 800},function(){
    						location.href="<%=Const.ROOT%>index";
    					});
    				}else{
    					layer.msg(data.msg, {icon: 5});
    				}	
              });
              return false;//return false是阻止提交
          });
    })
  </script>

</body>
</html>