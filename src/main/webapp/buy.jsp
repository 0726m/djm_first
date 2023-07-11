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
  <style>
.paypass{
 width: 320px;
 height: 53px;
 margin: 40px 10px;
}

.paypass .box{
 width: 310px;
 height: 50px;
 border:1px solid #ccc;
 margin-top: 10px;
 line-height: 50px;
}

.paypass .box input.paw{
 width: 50px;
 height: 48px;
 line-height: 40px;
 margin-left: -9px;
 border:none;
 border-right: 1px dashed #ccc;
 text-align: center;
}
.paypass .box input.paw:nth-child(1){
 margin-left: 0;
}
.paypass .box .pawDiv:nth-child(6) input.paw{
 border: none;
}
.paypass .box input.paw:focus{outline:0;}
.paypass .box .pawDiv{
 display: inline-block;
 line-height: 40px;
 width: 51px;
 height: 48px;
 margin-top: 0px;
 float: left;
}
.paypass .point{
 font-size: 18px;
 color: #ccc;
 margin: 5px 0;
}
.paypass .errorPoint{
 color: red;
 display: none;
}
  </style>
</head>
<body>
  <!-- top -->
  <%@include file="top.jsp" %>	
  <div class="content content-nav-base shopcart-content">
      <!-- 菜单 -->
	  <%@include file="menu.jsp" %>
    <div class="banner-bg w1200" style="background: url(<%=Const.ROOT%>res/static/img/pay_banner.jpg) no-repeat;">
      <h3>电影网站和推荐系统</h3>
      <p>在线支付</p>
    </div>
    <div class="cart w1200">
    	<div class="cart-table-th">
           <label style="margin:10px 20px">
           		<input type="radio" name="zhekou" value="90" data="100" checked="checked"/> 9折会员<span>【100元/年 】</span>
           		<input type="radio" name="zhekou" value="80" data="200"/> 8折会员<span>【200元/年 】</span>
           		<input type="radio" name="zhekou" value="70" data="300"/> 7折会员<span>【300元/年 】</span>
           		<input type="radio" name="zhekou" value="60" data="400"/> 6折会员<span>【400元/年 】</span>
           		<input type="radio" name="zhekou" value="50" data="500"/> 5折会员<span>【500元/年 】</span>
           </label>
      </div>
      <div class="cart-table-th">
           <label style="margin:10px 20px">请您扫码付款:</label>
      </div>
	   <div style="text-align:center">
	   	<img src="<%=Const.ROOT %>images/z.jpg" style="max-width:500px;margin:20px 0px"/>
	   </div>
      <div class="FloatBarHolder layui-clear">
        <div class="th Settlement">
          <button class="layui-btn layui-btn-continue">返回</button>
          <button class="layui-btn" id="jiesuan">确定</button>
        </div>
        <div class="th total">
          <p>应付：<span>￥</span><span class="pieces-total">100</span> 元</p>
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
    $(".layui-btn-continue").click(function(){
    	history.go(-1);
    });
    
    $("input[type='radio']").click(function(){
    	$(".pieces-total").html($(this).attr("data"));
    });
    
    $("#jiesuan").click(function(){
    	var zhekou=$("input[type='radio']:checked").val();
    	$.getJSON("<%=Const.ROOT%>users/chongzhi",{"id":"${sessionScope.users.id}","zhekou":zhekou},function(data){
    		if(data.code==0){
    			layer.msg("支付成功", {icon: 6,time: 1000},function(){
    				location.href="<%=Const.ROOT%>index";
    			});
    		}else{
    			layer.msg(data.msg, {icon: 5});
    		}
    	});
    });
});
  
  
</script>
</body>
</html>