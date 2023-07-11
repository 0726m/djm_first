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
  <%@include file="top.jsp" %>	

  <div class="content content-nav-base about-content">
    <%@include file="menu.jsp" %>
    <div class="banner-box">
      <div class="banner" style="height:400px;">
        <span style="color: #FFF;font-size: 40px;position: relative;right: -1200px;top:20px;">通知详情</span>
      </div>
    </div>
    <div class="brief-content layui-clear w1200" style="padding: 40px 0px;">
      <div class="text" style="padding-top: 10px;">
        <h1 style="text-align:center">${notice.title }</h1>
        <p style="text-align:center;margin:10px 0px">${notice.optime }</p>
        <hr/>
        <p>${notice.content }</p>
      </div>
    </div>
	
  </div>
<%@include file="footer.jsp" %>
<script type="text/javascript">

layui.config({
    base: '<%=Const.ROOT %>res/static/js/util/' //存放新模块的目录
  }).use(['mm'],function(){
      var
     mm = layui.mm;
  
    

});
</script>
</body>
</html>