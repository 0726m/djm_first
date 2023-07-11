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
  .cinema{
  	overflow:hidden;
  	clear:both;
  	margin-bottom:5px;
  }
  .cinema span{
  	border:1px solid #ddd;
  	line-height:35px;
  	overflow:hidden;
  	display:inline-block;
  	float:left;
  	text-align:center;
  	margin:0px 5px;
  	font-weight:700;
  	background-color:#2F4056;
  	color:#FFF;
  	width:150px;
  }
  .cinema ul{
  	float:left;
  }
  .cinema ul li{
  	border-bottom:1px solid #ddd;
  	padding:8px 20px;
  	float:left;
  	margin-right:10px;
  	background-color:#01AAED;
  	color:#FFF;
  }
  .cinema .days{
  	background-color:#FF5722;
  }
  .cinema .times{
  	background-color:#FFB800;
  }
  #seats{
  	padding:20px;
  	margin-button:20px;
  	overflow: hidden;
  }
  #seats p{
  	text-align: center;
  	margin-bottm:50px;
  	padding:20px 0px;
  	border:1px solid #ddd;
  }
  #seats div{
  	padding:8px 25px;
	border:1px solid #ddd;
	margin:10px;
	float:left;
	cursor:pointer;
  }
  #seats .green{
  	background-color:#5FB878;
  	color:#FFF;
  }
  #seats .red{
  	background-color:#FF5722;
  	color:#FFF;
  }
  
  </style>
</head>
<body>
  <!-- top -->
  <%@include file="top.jsp" %>	

  <div class="content content-nav-base datails-content">
     <!-- 菜单 -->
	<%@include file="menu.jsp" %>
    <div class="data-cont-wrap w1200">
      <div class="crumb">
        <a href="<%=Const.ROOT %>index">首页</a>
        <span>></span>
        <a href="javascript:;">物品详情</a>
      </div>
      <div class="product-intro layui-clear">
        <div class="preview-wrap">
          <a href="javascript:;"><img src="<%=Const.ROOT %>images/${wupin.pic}" style="width:400px;height:400px"></a>
        </div>
        <div class="itemInfo-wrap">
          <div class="itemInfo">
            <div class="title">
              <h4>${wupin.name }</h4>
            </div>
              <p class="activity"><span>所需积分:</span><strong class="jifen" id="jifen">${wupin.jifen }</strong> 分
               </p>
              <p id="result" style="border:1px solid #F00;font-size:15px;font-weight:700"><span id="cinema_name"></span><span id="ting_name"></span><span id="opday_name"></span><span id="times_name"></span></p>
              <div class="choose-btns">
	            <c:if test="${not(sessionScope.role==1 or sessionScope.role==2) }">
              			请先登录!
              	</c:if>
	             <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
	              <button id="btn" class="layui-btn  layui-btn-danger car-btn"><i class="layui-icon layui-icon-rmb"></i>兑换</button>
	             </c:if>
	            </div>
            </div>
          </div>
        </div>
     <div class="layui-clear">
        <div class="detail">
          <div class="layui-tab layui-tab-brief" style="width:1022px;">
            <ul class="layui-tab-title">
              <li class="layui-this"><h4>详情</h4></li>
            </ul>
            <div class="layui-tab-content">
              <div class="layui-tab-item layui-show">
                <div class="item" style="padding: 20px">
                  ${wupin.content }
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  
  <%@include file="footer.jsp" %>	
<script type="text/javascript">
  layui.config({
    base: '<%=Const.ROOT%>res/static/js/util/' //存放新模块的目录
  }).use(['form','mm','jquery','element','rate'],function(){
      var mm = layui.mm,form=layui.form,$ = layui.$,element=layui.element,rate=layui.rate;
    
      //开始购票
      $("#btn").click(function () {
    	  if(parseInt('${wupin.jifen}')>parseInt('${sessionScope.users.score}')){
    		  layer.msg("您的积分不足!");
    		  return false;
    	  }
    	  location.href="<%=Const.ROOT%>wupin/dui?id=${wupin.id}";
    	  return false;
      });
      
     
      
  });
</script>

</body>
