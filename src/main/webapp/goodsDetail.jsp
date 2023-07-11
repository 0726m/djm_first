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
        <a href="javascript:;">电影详情</a>
      </div>
      <div class="product-intro layui-clear">
        <div class="preview-wrap">
          <a href="javascript:;"><img src="<%=Const.ROOT %>images/${goods.pic}" style="width:400px;height:400px"></a>
        </div>
        <div class="itemInfo-wrap">
          <div class="itemInfo">
            <div class="title">
              <h4>${goods.name }</h4>
            </div>
            <div class="summary" style="height:auto;padding-bottom:12px;overflow: hidden;">
            	<p>类型:${goods.kind.name }</p>
            	<c:choose>
            		<c:when test="${empty cinemaList}">
            			该片暂无排片计划！
            		</c:when>
            		<c:otherwise>
            			<c:forEach items="${cinemaList}" var="v">
			             	<div class="cinema">
			             		<span class="cname">${v.name}</span>
			             		<ul class="tingname">
				             	<c:forEach items="${v.tings}" var="t">
						             <li data-id="${t.id}" data-name="${v.name}" data-ting="${t.name}" data-type="${v.type}" data-address="${v.address}" class="ting">${t.name}</li>
					            </c:forEach>
				            	</ul>
				            </div>
			            </c:forEach>
			            <p class="reference">类型:<span id="type"></span> 地址:<span id="address"></span></p>
            		</c:otherwise>
            	</c:choose>
	          <input type="hidden" id="pid"/>
              <div class="cinema" id="paibanlist">
	           </div>
              <p class="activity"><span>价格:</span><strong class="price" id="price"></strong> 元  
              <span style="margin-left:20px">优惠价格:</span><strong class="price" id="yhprice"></strong> 元 
               </p>
              <p id="result" style="border:1px solid #F00;font-size:15px;font-weight:700"><span id="cinema_name"></span><span id="ting_name"></span><span id="opday_name"></span><span id="times_name"></span></p>
              <div class="choose-btns">
	             <c:if test="${not(sessionScope.role==1 or sessionScope.role==2)}">
	            	<h4>请先登录!</h4>
	             </c:if>
	             <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
	              <button class="layui-btn  layui-btn-danger car-btn"><i class="layui-icon layui-icon-rmb"></i>购买</button>
	             </c:if>
	            </div>
            </div>
          </div>
        </div>
      </div>
     <div class="layui-clear">
        <div class="detail">
          <div class="layui-tab layui-tab-brief" style="width:1022px;">
            <ul class="layui-tab-title">
              <li class="layui-this"><h4>详情</h4></li>
              <li><h4>评论</h4></li>
              <c:if test="${goods.bofang==1 }"><li><h4>在线点播</h4></li></c:if>
            </ul>
            <div class="layui-tab-content">
              <div class="layui-tab-item layui-show">
                <div class="item" style="padding: 20px">
                  ${goods.content }
                </div>
              </div>
              <div class="layui-tab-item">
                <div class="item" style="padding: 20px">
                <c:if test="${empty messageList}">暂无评论!<hr/></c:if>
                <!-- 评论 -->
                <c:forEach items="${messageList}" var="message" varStatus="st">
                  <div class="layui-row">
                    <div class="layui-col-md12">
                      <h5 style="line-height: 38px;color: #a5a5a5;">【${message.users.name }】 发布于${message.optime } 【评分：${message.score }分】</h5>
                    </div>
                    <div class="layui-col-md12">
                      <p>${st.count }# ${message.content } </p>
                    </div>
                  </div>
                  	<hr/>
                 	<c:forEach items="${message.replys }" var="r" varStatus="rst">
                  	<div class="layui-row">
	                    <div class="layui-col-md11 layui-col-md-offset1">
	                      <h5 style="line-height: 38px;color: #a5a5a5;">${r.users.name }回复于${r.optime }</h5>
	                    </div>
	                  	<div class="layui-col-md11 layui-col-md-offset1">
	                      <p>${r.content }</p>
	                    </div>
                    </div>
                 	</c:forEach>
                  <hr/>
                  <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
	            	<form class="layui-form" method="post" id="reply" >
	                    <div class="layui-form-item layui-form-text">
	                      <div class="layui-input-inline">
	                        <input id="reply${message.id}" placeholder="您的回复" style="width:400px" class="layui-input"/>
	                      </div>
	                      <div class="layui-input-inline" style="text-align: right;">
	                        <button type="button" data="${message.id }" class="layui-btn layui-btn-primary reply">回复</button>
	                      </div>
	                    </div>
	              </form>
	              </c:if>
                  </c:forEach>
                  <!-- 评论end -->
                  <!-- 写评论 -->
                  <div  id="message" >
                  <c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
                  <div style="text-align:center;font-size:25px;color:#999;margin-bottom:5px;line-height:50px;background-color:#eee;border-radius:3px;">发表评论</div>
                  <div class="layui-row">
                    <div class="layui-col-md12">
                      <form class="layui-form" action="" method="post">
                      	<input type="hidden" name="uid" value="${sessionScope.users.id}"/>
                      	<input type="hidden" name="gid" value="${goods.id}"/>
                      	<input type="hidden" name="score" id="score" value="4"/>
                        <div class="layui-form-item layui-form-text">
                          <label class="layui-field-title">评分：</label><div id="test1"></div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                          <textarea class="layui-textarea" name="content" style="resize:none" placeholder="写点什么啊" lay-verify="required"></textarea>
                        </div>
                        <div class="layui-form-item">
                          <div class="layui-input-block">
                            <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="add">发表评论</button>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                  </c:if>
                  </div>
                </div>
              </div>
              <div class="layui-tab-item">
              	<div class="item" style="padding: 20px">
              		<c:if test="${sessionScope.role==1 or sessionScope.role==2 }">
              			<video src="<%=Const.ROOT %>images/${goods.mp4}" controls style="width:100%"></video>
              		</c:if>
              		<c:if test="${not(sessionScope.role==1 or sessionScope.role==2) }">
              			请先登录!
              		</c:if>
              	</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <div class="layuimini-container" id="popAdd" style="display: none;">
    <div class="layuimini-main">
        <form class="layui-form" action="" lay-filter="aexample">
        	<input type="hidden" name="pid" id="a_pid"/>
            <div id="seats"></div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="orderadd">立即提交</button>
                </div>
            </div>
        </form> 
</div>
</div>
  <%@include file="footer.jsp" %>	
<script type="text/javascript">
  layui.config({
    base: '<%=Const.ROOT%>res/static/js/util/' //你存放新模块的目录，注意，不是layui的模块目录
  }).use(['form','mm','jquery','element','rate'],function(){
      var mm = layui.mm,form=layui.form,$ = layui.$,element=layui.element,rate=layui.rate;
      //显示文
       //显示文字
      rate.render({
          elem: '#test1'
          ,value: 4 //初始值
          ,text: true //开启文本
          ,setText: function(value){
              $("#score").val(value);
          }
      });
      
      
      $(function(){
      	  $.post("<%=Const.ROOT%>orders/exists",{'gid':'${goods.id}'},function(data){
          	  if(data.code=="1"){
          		  $("#message").show();
          		  $("#reply").show(); 
          	  }		
            });
      }); 
      
      $(".ting").click(function(){
    	  var tid=$(this).attr("data-id");
    	  var type=$(this).attr("data-type");
    	  var address=$(this).attr("data-address");
    	  var name=$(this).attr("data-name");
    	  var ting=$(this).attr("data-ting");
    	  $("#type").html(type);
    	  $("#address").html(address);
    	  $("#cinema_name").html(name);
    	  $("#ting_name").html(ting);
    	  
    	  //清空
    	  $("#price").html("");
    	  $("#yhprice").html("");
    	  $("#opday_name").html("");
    	  $("#times_name").html("");
    	  $("#pid").val("");
    	  
    	  
    	  //排班
    	  $.getJSON("<%=Const.ROOT%>paipian/jsonlist",{"tid":tid},function(result){
    		  var content="";
    		  for(i=0;i<result.length;i++){
    			  content+="<div class='cinema'>";
    			  var p=result[i];
    			  content+="<span class=\"days\">"+p.name+"</span><ul class=\"times\">";
    			  for(j=0;j<p.times.length;j++){
    				  content+="<li class='pp' data-opday='"+p.name+"' data-times='"+p.times[j].starttime+"~"+p.times[j].endtime+"' data-id='"+p.times[j].id+"' data-price='"+p.times[j].price+"' data-yhprice='"+p.times[j].yhprice+"'>"+p.times[j].starttime+"~"+p.times[j].endtime+"</li>";
    			  }
    			  content+="</ul></div>";
    		  }
    		  $("#paibanlist").html(content);
    	  });
      });
      //动态绑定的事件
      $('#paibanlist').on('click','.pp', function () {
    	  var id=$(this).attr("data-id");
    	  var price=$(this).attr("data-price");
    	  var yhprice=$(this).attr("data-yhprice"); 
    	  var opday=$(this).attr("data-opday");
    	  var times=$(this).attr("data-times");
    	 // console.log(id);
    	  $("#pid").val(id);
    	  $("#price").html("<i>￥</i>"+price);
    	  $("#yhprice").html("<i>￥</i>"+yhprice);
    	  $("#opday_name").html(opday);
    	  $("#times_name").html(times);
      });
      
      //购买
      $(".car-btn").click(function(){
    	  var pid=$("#pid").val();
    	  if(pid){
    		  $("#a_pid").val(pid);
    		  //加载座位
    		  $.getJSON("<%=Const.ROOT%>seat/jsonlist",{"pid":pid},function(data){
    			  var seats=$("#seats");
    			  seats.html("");
    			  seats.append("<p>屏幕</p>");
    			  for(i=0;i<data.length;i++){
    				  if(data[i].status=="1"){
    				  	seats.append("<div class='red' id='"+data[i].id+"'>"+data[i].name+"</div>");
    				  }else{
    					seats.append("<div id='"+data[i].id+"'>"+data[i].name+"</div>");
    				  }
    			  }
    			  var addIndex=layer.open({
      	      		title:"选座",
      	      		area: ['920px', 'auto'],
      	      		type: 1, 
      	      		content: $("#popAdd")
      	      	  });
    		  });
    		 
    	  }else{
    		  layer.msg("先选择对应场次", {icon: 5});
    	  }
      });
      var sids=[];
      $("#seats").on("click","div",function(){
    	  if($(this).hasClass("red")){
    		  layer.msg("此座位已经有人选!", {icon: 5});
    	  }else{
    		  var sid=$(this).attr("id");
    		  if($(this).hasClass("green")){
        		  $(this).removeClass("green");
        		  for(i=0;i<sids.length;i++){
        			  if(sids[i]==sid){
        				  sids.splice(i,1);//删除
        			  }
        		  }
        	  }else{
        		  $(this).addClass("green");
        		  sids.push(sid);
        	  }
    	  }
      });
      //开始购票
      form.on('submit(orderadd)', function (data) {
    	  if(sids.length==0){
    		  layer.msg("请先选择座位！", {icon: 5});
    		  return false;
    	  }
    	  var url="";
    	  for(i=0;i<sids.length;i++){
    		  url+="&sids="+sids[i];
    	  }
    	  var pid=$("#a_pid").val();
    	  location.href="<%=Const.ROOT%>orders/pay?pid="+pid+url;
    	  return false;
      });
      
      form.on('submit(add)', function (data) {
    	  //console.log(data);
          $.post("<%=Const.ROOT%>message/add",data.field,function(data){
        	  if(data.code=="0"){
					layer.msg(data.msg, { icon: 6, time: 800},function(){
						location.reload();
					});
				}else{
					layer.msg(data.msg, {icon: 5});
				}	
          });
          return false;//return false是阻止提交
      });
      
    
      
      $(".reply").click(function(){
    	  var id=$(this).attr("data");
    	  var content=$("#reply"+id).val();
    	  if(content==""){
    		  layer.msg("内容不能为空！", {icon: 5});
    		  return false;
    	  }
    	  $.post("<%=Const.ROOT%>reply/add",{'pid':id,'content':content,'type':1},function(data){
        	  if(data.code=="0"){
        		  layer.msg(data.msg, { icon: 1, time: 800},function(){
        			  location.reload();
				  });
				}else{
					layer.msg(data.msg, {icon: 5});
				}	
          });
      });
      
  });
</script>

</body>
