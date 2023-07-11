<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="site-nav-bg">
    <div class="site-nav w1200">
      <p class="sn-back-home">
        <i class="layui-icon layui-icon-home"></i>
        <a href="<%=Const.ROOT %>index">首页</a>
      </p>
      <div class="sn-quick-menu">
      	<c:if test="${not (sessionScope.role==1 or sessionScope.role==2) }">
        	<div class="login"><a href="<%=Const.ROOT %>login.jsp">登录</a></div>
        	<div class="login"><a href="<%=Const.ROOT %>reg.jsp">注册</a></div>
        </c:if>
        <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
            <div class="sp-cart" style="width:200px;">欢迎您:${sessionScope.users.grade },${sessionScope.users.name }</div>
        	<div class="login"><a href="<%=Const.ROOT %>orders/index">我的订单</a></div>
        	<div class="login"><a href="<%=Const.ROOT %>updateUsers.jsp">个人资料</a></div>
        	<div class="login"><a href="<%=Const.ROOT %>logout">注销</a></div>
        </c:if>
      </div>
    </div>
  </div>

  <div class="header">
    <div class="headerLayout w1200">
      <div class="headerCon">
        <h1 class="mallLogo" style="color:#CFB2F6">电影网站和推荐系统</h1>
        <div class="mallSearch">
          <form action="<%=Const.ROOT %>goods/index" class="layui-form" novalidate>
            <input type="text" name="name" required  lay-verify="required" autocomplete="off" class="layui-input" placeholder="请输入电影名称">
            <button class="layui-btn" lay-submit lay-filter="formDemo">
                <i class="layui-icon layui-icon-search"></i>
            </button>
            <input type="hidden" name="" value="">
          </form>
        </div>
      </div>
    </div>
  </div>