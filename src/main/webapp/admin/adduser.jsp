<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>电影网站和推荐系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=Const.ROOT %>admin/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=Const.ROOT %>admin/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <form class="layui-form" action="" lay-filter="example"> 
        	<div class="layui-form-item">
			      <label class="layui-form-label">用户名</label>
			      <div class="layui-input-block">
			        <input type="text" name="username" lay-verify="required|username" autocomplete="off" placeholder="请填写用户名"  class="layui-input">
			      </div>
            </div>
             <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="password" id="password1" name="password" lay-verify="required|password" autocomplete="off" placeholder="请填写6到12位密码" class="layui-input">
                </div>
             </div>
             <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                    <input type="password" name="repass" lay-verify="required|repass" autocomplete="off" placeholder="请输入确认密码" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="姓名是必填项，不能为空" placeholder="请输入姓名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" lay-verify="required|phone" lay-reqtext="电话是必填项，不能为空" autocomplete="off" placeholder="请输入电话" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="add">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form> 
</div>
</div>

<script src="<%=Const.ROOT %>admin/lib/layui-v2.5.4/layui.js?v=1.0.4" charset="utf-8"></script>

<script>
	//加载需要的模块
    layui.use(['form', 'layedit', 'laydate'], function () {
    	var $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;
        
        
        //自定义验证规则,注意required|uUsername,其中required是自带的,uUsername是自定义的
        form.verify({
        	username: function (value) {
                if (value.length < 2) {
                    return '用户名长度必须大于等于2';
                }
            }
            , password: [
                /^[\S]{6,12}$/
                , '密码必须6到12位'
            ]
            ,repass: function(value) {
               	//获取密码
               	var pass = $("#password1").val();
             	if(pass!=value) {
             		return '两次输入的密码不一致';
             	}
             }
           ,phone: [
                 /^[\d]{11}$/
                 , '电话必须11位'
             ]
        });
        
        //监听提交事件，其中data.filed就是需要提交的表单数据
        form.on('submit(add)', function (data) {
        	var url="<%=Const.ROOT%>users/add";
            $.post(url,data.field,function(data){
            	//parent.location.reload();//用layer弹出的iframe则这样刷新父页面
            	if(data.code==1){//5错误，6正常
            		layer.msg(data.msg,{"icon":5,timeout:300});
            	}else{
            		layer.msg(data.msg,{"icon":6,timeout:300},function(){
            			//parent.location.href="<%=Const.ROOT%>admin/login.jsp";
            			parent.location.reload();
            		});
            	}	
            });
            return false;//return false是阻止提交
        });
    });
</script>

</body>
</html>