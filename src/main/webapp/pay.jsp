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
           <label style="margin:10px 20px">请您尽快付款:</label>
      </div>
	   <div id="ahh"  style="text-align:center;display:none">
	   	<img src="<%=Const.ROOT %>images/z3.jpg" style="max-width:300px;margin:20px 0px;"/>
	   	<button id="yizhifu" class="layui-btn" >已支付</button>
	   </div>
	   <div class="wahaha" style="display:none">
	   <div class="layui-form-item">
              <label class="layui-form-label" style="">积分</label>
              <div class="layui-input-block">
              <input type="text" value="${sessionScope.users.score}" disabled class="layui-input">
              </div>
        </div>
        <div class="layui-form-item">
              <label class="layui-form-label" style="">所需积分</label>
              <div class="layui-input-block">
              <input type="text" value="${sessionScope.orders.price*10}" disabled class="layui-input">
              </div>
         </div>
         <button id="okzhifu" class="layui-btn">确认支付</button>
         </div>
	   <div class="woshisb" style="display:none">
  		  <h3>请选择支付方式：</h3>
		  <button id="zhifubao" class="layui-btn">支付宝支付</button>
		  <button id="weixin" class="layui-btn">微信支付</button>
		  <button id="jifen" class="layui-btn">积分支付</button>

      </div>
      <div id="bbb" class="FloatBarHolder layui-clear" style="display:block">
        <div class="th Settlement">
          <button class="layui-btn layui-btn-continue">返回</button>
          <button class="layui-btn" id="jiesuan">确定</button>
        </div>
        <div class="th total">
          <p>应付：<span>￥</span><span class="pieces-total">${sessionScope.orders.price }</span> 元</p>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 菜单 -->
<%@include file="footer.jsp" %>
<script type="text/javascript">
  layui.config({
    base: '<%=Const.ROOT %>res/static/js/util/' //存放新模块的目录
  }).use(['mm','jquery','element','layer','form'],function(){
    var mm = layui.mm,$ = layui.$,element = layui.element,layer=layui.layer,form = layui.form;;
    $(".layui-btn-continue").click(function(){
    	history.go(-1);
    });
    
    $("input[type='radio']").click(function(){
    	$(".pieces-total").html($(this).attr("data"));
    });
    
    $("#jiesuan").click(function(){
    	$.getJSON("<%=Const.ROOT%>orders/add",{},function(data){
    		if(data.code==0){
    			
<%--     				location.href="<%=Const.ROOT%>orders/index"; --%>
    				$('.woshisb').css("display","block");
    				$('#bbb').css("display","none")
    		}else{
    			layer.msg(data.msg, {icon: 5});
    		}
    	});
    });
    $('#zhifubao').click(function(){
    	layer.msg("正在跳转···", {icon: 6,time: 1000},function(){
    	// 支付账号：cpaybp5258@sandbox.com
  	  location.href="http://localhost:8080/films/pay/alipay?orderId=1414"})
    });
    $('#weixin').click(function(){
    	$('#ahh').css("display","block")
    });
    $('#yizhifu').click(function(){
    	location.href="<%=Const.ROOT%>orders/index";
    });
    $('#jifen').click(function(){
    	$('.wahaha').css("display","block")
    });
    $('#okzhifu').click(function(){
    	if(parseInt('${sessionScope.users.score}')>parseInt('${sessionScope.orders.price*10}')){
    		layer.msg("支付成功",{icon: 6,time: 1000},function(){
    			
    			location.href="<%=Const.ROOT%>orders/index";
    		})
    	}else{
    		layer.msg("积分不足")
    	}
    })
});
 
  
  
</script>
</body>
</html>