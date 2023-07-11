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
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet"
	href="<%=Const.ROOT%>admin/lib/layui-v2.5.4/css/layui.css" media="all">
<link rel="stylesheet" href="<%=Const.ROOT%>admin/css/public.css" media="all">

</head>
<body>
	<div class="layuimini-container">
		<div class="layuimini-main">
			<fieldset class="layui-elem-field layuimini-search">
				<legend>搜索信息</legend>
				<div style="margin: 10px 10px 10px 10px">
					<form class="layui-form layui-form-pane" action="">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">名称</label>
								<div class="layui-input-inline">
									<input type="text" name="name" autocomplete="off"
										class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<a class="layui-btn" lay-submit="" lay-filter="data-search-btn">搜索</a>
							</div>
							<div class="layui-inline">
								<button type="button"
									class="layui-btn layui-btn-normal data-add-btn">
									<i class="layui-icon layui-icon-add-1"></i>添加
								</button>
							</div>
						</div>
					</form>
				</div>
			</fieldset>
			<table class="layui-hide" id="currentTableId"
				lay-filter="currentTableFilter"></table>
			<script type="text/html" id="currentTableBar">
			<a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
        </script>
		</div>
	</div>
<div class="layuimini-container" id="popAdd" style="display: none;">
    <div class="layuimini-main">
        <form class="layui-form" action="" lay-filter="aexample">
        	<div class="layui-form-item">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
					<label class="layui-form-label">图片</label>
					<div class="layui-input-block">
						<input type="hidden" id="pic1" name="pic" required>
						<button type="button" class="layui-btn" id="test1">
							<i class="layui-icon">&#xe67c;</i>上传文件
						</button>
					</div>
			</div>
			<div class="layui-form-item">
                <label class="layui-form-label">积分</label>
                <div class="layui-input-block">
                    <input type="number" name="jifen" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
					<label class="layui-form-label">介绍</label>
					<div class="layui-input-block">
						<input id="content" name="content" value="" type="hidden">
					    <div id="editor">
						</div>
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

<div class="layuimini-container" id="popUpdate" style="display: none;">
    <div class="layuimini-main">
        <form class="layui-form" action="" lay-filter="uexample">
        	<input type="hidden" name="id"/> 
        	<div class="layui-form-item">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
					<label class="layui-form-label">图片</label>
					<div class="layui-input-block">
						<input type="hidden" id="pic2" name="pic" required>
						<button type="button" class="layui-btn" id="test2">
							<i class="layui-icon">&#xe67c;</i>上传文件
						</button>
					</div>
			</div>
			<div class="layui-form-item">
                <label class="layui-form-label">时长</label>
                <div class="layui-input-block">
                    <input type="number" name="jifen" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
					<label class="layui-form-label">介绍</label>
					<div class="layui-input-block">
						<input id="content2" name="content" value="" type="hidden">
					    <div id="editor2">
						</div>
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

	<script src="<%=Const.ROOT%>admin/lib/layui-v2.5.4/layui.js" charset="utf-8"></script>
	<script src="<%=Const.ROOT%>wangEditor/wangEditor.min.js" charset="utf-8"></script>
	<script>
	
	var E = window.wangEditor;
	var editor = new E('#editor');
	editor.customConfig.uploadFileName = 'file';
	editor.customConfig.uploadImgServer = '<%=Const.ROOT %>upfile';
	editor.customConfig.onchange = function (html) {
		document.getElementById("content").value=html;
    }
	editor.customConfig.zIndex = 1;
	editor.create();
	

	var editor2 = new E('#editor2');
	editor2.customConfig.uploadFileName = 'file';
	editor2.customConfig.uploadImgServer = '<%=Const.ROOT %>upfile';
	editor2.customConfig.onchange = function (html) {
		document.getElementById("content2").value=html;
    }
	editor2.customConfig.zIndex = 1;
	editor2.create();
	
    layui.use(['form', 'table','laydate','upload'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
        	laydate = layui.laydate;
       		upload = layui.upload;
       		
       	//执行实例
            var uploadInst = upload.render({
              elem: '#test1' //绑定元素
              ,accept: 'file'
              ,url: '<%=Const.ROOT%>upfile/' //上传接口
              ,done: function(res){
            	layer.msg("上传成功", {icon: 6,time: 1000});
                $("#pic1").val(res.filename);
              }
              ,error: function(){
                //请求异常回调
            	  layer.msg("上传失败", {icon: 5});
              }
            });
       	
            var uploadInst2 = upload.render({
                elem: '#test2' //绑定元素
                ,accept: 'file'
                ,url: '<%=Const.ROOT%>upfile/' //上传接口
                ,done: function(res){
              	layer.msg("上传成功", {icon: 6,time: 1000});
                  $("#pic2").val(res.filename);
                }
                ,error: function(){
                  //请求异常回调
              	  layer.msg("上传失败", {icon: 5});
                }
              });
       		
        table.render({
            elem: '#currentTableId',
            url: '<%=Const.ROOT%>wupin/list',
            cols: [[
				{field:'id',hide:true,width: 0},
				{width: 100,style:'line-height:100px',templet:'#no',title: 'NO', sort: true},
                {field: 'name', width: 180, title: '名称'},
                {templet: '#pic', width: 180, title: '图片'},
                {field: 'jifen', width: 180, title: '所需积分'},
                {title: '操作', minWidth: 50, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 10,
            page: true
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            //var result = JSON.stringify(data.field);
            //console.log(data.field);
            //执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where:data.field
            }, 'data');
			
            return false;
        });

        // 监听添加操作
        $(".data-add-btn").on("click", function () {
        	var addIndex=layer.open({
        		title:"新增",
        		area: ['920px', '620px'],
        		type: 1, 
        		content: $("#popAdd")
        	});
        });
        //监听提交事件，其中data.filed就是需要提交的表单数据
        form.on('submit(add)', function (data) {
            $.post("<%=Const.ROOT%>wupin/add",data.field,function(data){
            	if(data.code=="0"){
					layer.msg(data.msg, { icon: 6, time: 800},function(){
						//table.reload('currentTableId');//数据表格重
						location.reload();
						layer.closeAll();//关闭弹出层
					});
				}else{
					layer.msg(data.msg, {icon: 5});
				}	
            });
            return false;//return false是阻止提交
        });
        
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                //layer.alert('编辑行：<br>' + JSON.stringify(data))
                layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "修改",
                        area: ['920px', '620px'],
                        content: $("#popUpdate")//引用的弹出层的页面层的方式加载修改界面表单
               });
              //表单初始赋值
               form.val('uexample', data);
               //动态向表传递赋值当然也是异步请求的要数据的修改数据的获取
               setFormValue(obj,data);  
               editor2.txt.html(data.content);
            }else if (obj.event === 'delete') {
                layer.confirm('真的删除行么', function (index) {
                    $.getJSON('<%=Const.ROOT%>wupin/delete',{'id':obj.data.id},function(msg) {
  						if(msg.code=="0"){
  							layer.msg(msg.msg, { icon: 6, time: 800},function(){
  								layer.close(index);
  								obj.del();
  							});
  						}else{
  							layer.msg(msg.msg, {icon: 5});
  						}	
  					}); 
                });
            }
        });
      //自定义验证规则
        form.verify({
           price: [
                 /^\d*\.*\d*$/
                 , '积分必须是数字'
             ]
        });
        //监听弹出框表单提交，massage是修改界面的表单数据
          function setFormValue(obj,data){
              form.on('submit(update)', function(message) {
            	  //console.log(message);
            	  $.ajax({
                  url:'<%=Const.ROOT%>wupin/update',
					type : 'POST',
					data : message.field,
					dataType:"JSON",
					success : function(msg) {
						console.log(msg);
						layer.closeAll();
						if(msg.code=="0"){
							layer.msg(msg.msg, { icon: 6, time: 800},function(){
								//table.reload('currentTableId');//数据表格重
								layer.closeAll();//关闭弹出层
								location.reload();
							});
						}else{
							layer.msg(msg.msg, {icon: 5});
						}	
					}
				});
				 return false;
			   });
			}
		});
    
	</script>
	<script type="text/html" id="no">
    {{d.LAY_TABLE_INDEX+1}}
	</script>
	<script type="text/html" id="pic">
	{{#if(d.pic!=''){}}
    	<a href="<%=Const.ROOT%>images/{{d.pic}}" target="_blank"><img src="<%=Const.ROOT%>images/{{d.pic}}" style="width:120px;"/></a>
	{{#}}}
	</script>
</body>
</html>