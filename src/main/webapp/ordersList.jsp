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
	    <col width="9%">
	    <col width="9%">
	    <col width="9%">
	    <col width="9%">
	    <col width="9%">
	    <col width="10%">
	    <col width="5%">
	    <col width="5%">
	    <col width="10%">
	    <col>
	  </colgroup>
	  <thead>
	    <tr>
	      <th>序号</th>
	      <th>订单号</th>
	      <th>影剧院-厅</th>
	      <th>电影</th>
	      <th>座位</th>
	      <th>播放时间</th>
	      <th>下单时间</th>
	      <th>人数</th>
	      <th>价格</th>
	      <th>状态</th>
	      <th>操作</th>
	    </tr> 
	  </thead>
	  <tbody>
	  	<c:forEach items="${list }" var="v" varStatus="st">
	    <tr>
	      <td>${st.count }</td>
	      <td>${v.ordersno }</td>
	      <td>${v.cinema.name }<br/>${v.ting.name }</td>
	      <td>${v.goods.name }</td>
	      <td>${v.seats }</td>
	      <td>${v.paipian.opday }<br/>${v.paipian.starttime}~${v.paipian.endtime}</td>
	      <td>${v.optime}</td>
	      <td>${v.nums}</td>
	      <td style="color: #ee0000;font-size:18px;font-weight:bold;">￥${v.price}</td>
	      <td>${v.status}</td>
	      <td>
	      	<span class="message-btn" id="${v.goods.id}">评价</span>
	      	<c:if test="${v.status=='已确认' and v.paipian.comptime}"><span class="tui-btn" id="${v.id}">退票</span></c:if>
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
    base: '<%=Const.ROOT %>res/static/js/util/' //存放新模块的目录
  }).use(['mm','jquery','element','layer'],function(){
    var mm = layui.mm,$ = layui.$,element = layui.element,layer=layui.layer;
    
    $(".dele-btn").click(function(){
    	var id=$(this)[0].id;
    	layer.confirm('你确定要删除吗',{
            yes:function(index,layero){
              //ajax
              $.post("<%=Const.ROOT%>orders/delete",{"id":id},function(data){
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
    
    $(".tui-btn").click(function(){
    	var id=$(this)[0].id;
    	layer.confirm('你确定要退票吗',{
            yes:function(index,layero){
              //ajax
              $.post("<%=Const.ROOT%>orders/changestatus",{"id":id,"status":"申请退票"},function(data){
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
    
    $(".message-btn").click(function(){
    	var id=$(this)[0].id;
        location.href="<%=Const.ROOT%>goods/detail?id="+id;
    });
    
    $("#jiesuan").click(function(){
         location.href="<%=Const.ROOT %>index";
    });
});
</script>
</body>
</html>