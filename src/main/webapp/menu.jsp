<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="main-nav">
  <div class="inner-cont0">
    <div class="inner-cont1 w1200">
      <div class="inner-cont2">
        <a href="<%=Const.ROOT %>index" class="active">首页</a>
        <a href="<%=Const.ROOT %>goods/index">电影列表</a>
        <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
       	 <a href="<%=Const.ROOT %>goods/tuijian">我的推荐</a>
       	 <a href="<%=Const.ROOT %>wupin/index">积分兑换</a>
       	 <a href="<%=Const.ROOT %>records/index">我的兑换记录</a>
        </c:if>
      </div>
    </div>
  </div>
</div>