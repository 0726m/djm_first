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


  <div class="content content-nav-base commodity-content">
     <%@include file="menu.jsp" %>
    <div class="commod-cont-wrap">
      <div class="commod-cont layui-clear" style="width:1210px;margin:0px auto">
         <div class="left-nav">
          <div class="title"><a style="color:#FFF" href="<%=Const.ROOT%>goods/index">所有分类</a></div>
          <div class="list-box">
          	<c:forEach items="${kindList}" var="v">
	            <dl>
	             <dt><a href="<%=Const.ROOT%>goods/index?kid=${v.id}">${v.name}</a></dt>
	            </dl>
            </c:forEach>
          </div>
        </div> 
        <div class="right-cont-wrap">
          <div class="right-cont">
            <div class="cont-list layui-clear" id="list-cont">
            <c:forEach items="${list }" var="v">
              <div class="item"  style="margin:0px 20px 20px 0px;">
                <div class="img">
                  <a href="<%=Const.ROOT%>goods/detail?id=${v.id}"><img src="<%=Const.ROOT%>images/${v.pic}" alt="" style="width:280px;height:280px"></a>
                </div>
                <div class="text">
                  <p class="title">${v.name }</p>
                  <p class="price">
                    <span class="pri">时长:${v.times }分 </span>
                    <span class="nub">[${v.startday }上映]</span>
                  </p>
                </div>
              </div>
             </c:forEach>
            </div>
            <div id="demo0" style="text-align: center;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <%@include file="footer.jsp" %>
<script>

  layui.config({
    base: '<%=Const.ROOT%>res/static/js/util/' //存放新模块的目录
  }).use(['mm','laypage','jquery'],function(){
      var laypage = layui.laypage,$ = layui.$,
     mm = layui.mm;
      laypage.render({
          elem: 'demo0',
          curr: '${pageBean.pageNo}',
          limit: '${pageBean.pageSize}',
          count: '${pageBean.totalCount}',
          jump: function(obj, first){
              //obj包含了当前分页的所有参数，比如：
              console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
              console.log(obj.limit); //得到每页显示的条数
              page=obj.curr;  //改变当前页码
              limit=obj.limit;
              //首次不执行
              if(!first){
              	location.href="<%=Const.ROOT%>goods/index?page="+page;
              }
          }
        });


    $('.sort a').on('click',function(){
      $(this).addClass('active').siblings().removeClass('active');
    })
    $('.list-box dt').on('click',function(){
      if($(this).attr('off')){
        $(this).removeClass('active').siblings('dd').show()
        $(this).attr('off','')
      }else{
        $(this).addClass('active').siblings('dd').hide()
        $(this).attr('off',true)
      }
    })

});
</script>


</body>
</html>