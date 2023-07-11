<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>修改</title>
<link href="<%=Const.ROOT %>css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=Const.ROOT %>js/jquery.min.js"></script>
<script src="<%=Const.ROOT %>js/ajaxfileupload.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="<%=Const.ROOT %>css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="<%=Const.ROOT %>css/memenu.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<%@include file="top.jsp" %>
	
<!--content-->
<div class=" container">
<div class=" register">
		<h1>个人信息修改</h1>
		  	  <form action="<%=Const.ROOT%>users/update" method="post">
		  	  	<input type="hidden" name="id" value="${sessionScope.users.id }"/>
				 <div class="col-md-6 register-top-grid">
					 <div>
						<span>用户名</span>
						<input type="text"  id="username" value="${sessionScope.users.username }" name="username" readonly> 
					 </div>
					 <div>
						<span>密码</span>
						<input type="password" name="password" value="${sessionScope.users.password }" required>
					 </div>
					 <div>
						 <span>确认密码</span>
						 <input type="password" name="repassword" value="${sessionScope.users.password }" required>
					 </div>
					</div>
				     <div class="col-md-6 register-bottom-grid">
					      <div>
							<span>姓名</span>
							<input type="text" name="name" value="${sessionScope.users.name }" required> 
						 </div>	
						 <div>
							<span>地址</span>
							<input type="text" name="address" value="${sessionScope.users.address }" required> 
						 </div>				 
							<div>
								 <span>电话</span>
								 <input type="text" name="tel" value="${sessionScope.users.tel }" pattern="\d{11}" required>
							 </div>
						<input type="submit" value="提交">		
					 </div>
					 <div class="clearfix"> </div>
				</form>
			</div>
</div>
<!--//content-->

<%@include file="footer.jsp" %>
</body>
<script>
<c:if test="${not empty msg}">
alert('${msg}');
</c:if>
</script>
</html>
			