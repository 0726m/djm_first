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

  <div class="content content-nav-base buytoday-content">
    <div id="list-cont" style="position: relative">
      
	  <!-- 菜单 -->
	  <%@include file="menu.jsp" %>
      <!--公告-->
      <div class="category-con" style="position: absolute;right:0px;top:37px;width:300px;z-index:9">
        <div class="category-inner-con">
          <div class="category-type" style="width:300px;">
            <h3>站内通知</h3>
          </div>
          <div class="category-tab-content">
            <div class="nav-con" style="width:300px;">
              <ul class="normal-nav layui-clear" style="height:381px;">
              	<c:forEach items="${noticeList }" var="v" varStatus="st">
                <li class="nav-item">
                  <div class="title"><a href="<%=Const.ROOT%>notice/detail?id=${v.id}">${v.title }</a></div>
                  <p style="padding-left:27px">${v.optime }</p>
                  <i class="layui-icon layui-icon-right"></i>
                </li>
                </c:forEach>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <!--公告end-->
      <div class="banner-box">
         <!-- 轮播图 -->
         <div class="layui-carousel" id="test1">
		  <ul carousel-item>
	        <li><img src="<%=Const.ROOT %>res/static/img/banner_01.jpg" alt="First slide"></li>
	        <li><img src="<%=Const.ROOT %>res/static/img/banner_02.jpg" alt="First slide"></li>
	        <li><img src="<%=Const.ROOT %>res/static/img/banner_03.jpg" alt="First slide"></li>
		  </ul>
		</div>
      </div>
      
      <!-- 最新 -->
       <div class="news-list-box" >
        <div class="news-list w1200"> 
          <div class="tab-title">
            <a href="javascript:;" class="active tuang" data-content="news">最新电影</a>
          </div>
        </div>
       </div>
      
     <div class="product-list-box">
        <div class="product-list w1200">  
          <div class="list-cont">
            <div class="item-box layui-clear">
            
            <c:forEach items="${goodsList }" var="v">
              <div class="item">
                <a href="<%=Const.ROOT%>goods/detail?id=${v.id}"><img src="<%=Const.ROOT%>images/${v.pic}" alt="" style="width:290px;height:240px"></a>
                <div class="text-box">
                  <p class="title">${v.name }</p>
                  <p class="plic">
                    <span class="Ori-pic">时长:${v.times }分 [${v.startday }上映]</span>
                    <a href="<%=Const.ROOT%>goods/detail?id=${v.id}"><span class="discount"><i class="layui-icon layui-icon-right"></i></span></a>
                  </p>
                </div>
              </div>
              </c:forEach>
          
            </div>
             <!-- <div id="demo0" style="text-align: center;"></div>  -->
          </div>
        </div>  
      </div> 
      
    </div>
  </div>

<%@include file="footer.jsp" %>	
<script>
layui.config({
    base: '<%=Const.ROOT %>res/static/js/util/' //存放新模块的目录
  }).use(['mm','laypage','jquery','carousel'],function(){
      var laypage = layui.laypage, $ = layui.$,
      mm = layui.mm,carousel=layui.carousel;
      //退出_self
      if (top.location != self.location) top.location = self.location;
      //轮播尺寸
	  var cwidth=document.body.offsetWidth-300+"px";
    //建造实例
      carousel.render({
        elem: '#test1'
        ,width: cwidth //设置容器宽度
        ,height:'381px'
        ,arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
      });
    
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
              	location.href="<%=Const.ROOT%>index?page="+page;
              }
          }
        });
      //切换tab
      $('body').on('click','*[data-content]',function(){
          $(this).addClass('active').siblings().removeClass('active')
          var dataConte = $(this).attr('data-content');
          $('*[cont]').each(function(i,item){
            var itemCont = $(item).attr('cont');
            if(dataConte === itemCont){
              $(item).removeClass('layui-hide');
            }else{
              $(item).addClass('layui-hide');
            }
          })
        });
});
</script>


</body>
</html>