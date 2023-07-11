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
  <div class="content content-nav-base shopcart-content">
      <!-- 菜单 -->
	  <%@include file="menu.jsp" %>
    <div class="cart w1200">

      <table class="layui-table">
	  <colgroup>
	    <col width="5%">
	    <col width="20%">
	    <col width="20%">
	    <col width="20%">
	    <col width="15%">
	    <col>
	  </colgroup>
	  <thead>
	    <tr>
	      <th>序号</th>
	      <th>用户</th>
	      <th>物品</th>
	      <th>积分</th>
	      <th>兑换时间</th>
	      <th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${list }" var="v" varStatus="st">
	    <tr>
	      <td>${st.count }</td>
	      <td>${v.wupin.name }</td>
	      <td>${v.users.name }</td>
	      <td>${v.wupin.jifen}</td>
	      <td>${v.optime}</td>
	      <td>
	      	<span class="dele-btn" id="${v.id}">删除</span>
	      </td>
	    </tr>
	    </c:forEach>
	  </tbody>
	</table>

      <div class="FloatBarHolder layui-clear">
      	<div class="th th-chk">
            <label>&nbsp;&nbsp;<span class="Selected-pieces" style="color:#ff5500;font-size:20px;font-weight:700"></span></label>
        </div>
        <div class="th Settlement">
           <button class="layui-btn" id="jiesuan">返回</button>
        </div>
      </div>
    </div>
  </div>
  <!-- 菜单 -->
<%@include file="footer.jsp" %>
<script type="text/javascript">
  layui.config({
    base: '<%=Const.ROOT %>res/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
  }).use(['mm','jquery','element','layer'],function(){
    var mm = layui.mm,$ = layui.$,element = layui.element,layer=layui.layer;
    
    $(".dele-btn").click(function(){
    	var id=$(this)[0].id;
    	layer.confirm('你确定要删除吗',{
            yes:function(index,layero){
              //ajax
              $.post("<%=Const.ROOT%>records/delete",{"id":id},function(data){
            	  if(data.code=="0"){
    					layer.msg(data.msg, { icon: 6, time: 800},function(){
    						location.reload();
    					});
    				}else{
    					layer.msg(data.msg, {icon: 5});
    				}	
              });
            }
          });
    });
    
   
    $("#jiesuan").click(function(){
         location.href="<%=Const.ROOT %>index";
    });
});
</script>
</body>
</html>