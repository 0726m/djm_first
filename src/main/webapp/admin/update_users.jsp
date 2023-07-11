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
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>基本资料</legend>
        </fieldset>

        <form class="layui-form" action="" lay-filter="example">  
        <input type="hidden" name="id"/>
           <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="required" lay-reqtext="必填项，不能为空" readonly placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="必填项，不能为空" autocomplete="off" placeholder="请输入" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">电话</label>
                <div class="layui-input-block">
                    <input type="text" name="phone" lay-verify="required|phone" lay-reqtext="必填项，不能为空" autocomplete="off" placeholder="请输入" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit="" lay-filter="update">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
            
        </form> 
</div>
</div>

<script src="<%=Const.ROOT %>admin/lib/layui-v2.5.4/layui.js?v=1.0.4" charset="utf-8"></script>

<script>
    layui.use(['form', 'layedit', 'laydate'], function () {
    	var $ = layui.jquery;
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //自定义验证规则
        form.verify({
        	phone: [
                 /^[\d]{11}$/
                 , '电话必须11位'
             ]
        });

        //监听提交
        form.on('submit(update)', function (data) {
            $.post("<%=Const.ROOT%>users/update",data.field,function(data){
            	layer.msg(data.msg, { icon: 6, time: 800},function(){
            			location.reload();
				});
            });
            return false;
        });
        
        $.getJSON("<%=Const.ROOT%>users/json",{"id":'${sessionScope.users.id}'},function(data){
        	form.val('example',data);
        });
        //表单初始赋值
    });
</script>

</body>
</html>