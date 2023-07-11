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
  <!--<%@include file="top.jsp" %>	-->

  <div class="content content-nav-base  login-content">
    <!-- 菜单 -->
	 <!--<%@include file="menu.jsp" %>-->
    <div class="login-bg">
      <div class="login-cont w1200">
        <div class="form-box" style="width: 380px;height:430px;padding: 10px">
          <form class="layui-form" action="">
            <legend style="padding: 12px 0">用户注册</legend>
            <hr/>
            <div class="layui-form-item">
              <label class="layui-form-label" style="">用户名</label>
              <div class="layui-input-block">
                <input type="text" name="username"  lay-verify="required|username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label">密码</label>
              <div class="layui-input-block">
                <input type="password" name="password" id="password" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label">确认密码</label>
              <div class="layui-input-block">
                <input type="password" name="repassword" lay-verify="required|repass" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label" style="">姓名</label>
              <div class="layui-input-block">
                <input type="text" name="name"  lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label" style="">电话</label>
              <div class="layui-input-block">
                <input type="text" name="phone"  lay-verify="required|phone" placeholder="请输入电话" autocomplete="off" class="layui-input">
              </div>
            </div>

            <div class="layui-form-item">
              <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="btn-send">注册</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
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
      base: '<%=Const.ROOT%>res/static/js/util' //你存放新模块的目录，注意，不是layui的模块目录
    }).use(['jquery','form'],function(){
          var $ = layui.$,form = layui.form;

          form.verify({
          	username: function (value) {
                  if (value.length < 2) {
                      return '用户名长度必须大于等于2';
                  }
              }
              , password: [
                  /^[\S]{6,12}$/
                  , '密码必须6到12位'
              ]
              ,repass: function(value) {
                 	//获取密码
                 	var pass = $("#password").val();
               	if(pass!=value) {
               		return '两次输入的密码不一致';
               	}
               }
             ,phone: [
                   /^[\d]{11}$/
                   , '电话必须11位'
               ]
          });
          
          form.on('submit(btn-send)', function (data) {
              $.post("<%=Const.ROOT%>reg",data.field,function(data){
            	  if(data.code=="0"){
    					layer.msg(data.msg, { icon: 6, time: 800},function(){
    						location.href="<%=Const.ROOT%>login.jsp";
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