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
                <label class="layui-form-label">影院名称</label>
                <div class="layui-input-block">
                    <select name="cid" lay-verify="required" id="cid1"></select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">座位数</label>
                <div class="layui-input-block">
                    <input type="number" name="seatnums" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
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
        	<input type="hidden" id="id" name="id"/>
            <div class="layui-form-item">
                <label class="layui-form-label">影院名称</label>
                <div class="layui-input-block">
                    <select name="cid" lay-verify="required" id="cid2"></select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-block">
                    <input type="text" name="name" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">座位数</label>
                <div class="layui-input-block">
                    <input type="number" name="seatnums" lay-verify="required" lay-reqtext="必填项，不能为空" placeholder="请输入" autocomplete="off" class="layui-input">
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
	<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
		

        $.getJSON("<%=Const.ROOT%>cinema/jsonlist",function(data){
        	$("#cid1").html("<option value=''>请选择影院</option>");
        	$("#cid2").html("<option value=''>请选择影院</option>");
			for(i=0;i<data.length;i++){
				$("#cid1").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
				$("#cid2").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
			form.render('select');//必须渲染一下
		});
        
        table.render({
            elem: '#currentTableId',
            url: '<%=Const.ROOT%>ting/list',
            cols: [[
				{field:'id',hide:true,width: 0},
				{width: 70, templet:'#no',title: 'NO', sort: true},
                {templet: '#cinema', minWidth: 100, title: '影院名称'},
                {field: 'name', minWidth: 100, title: '名称'},
                {field: 'seatnums', minWidth: 100, title: '座位数'},
                {title: '操作', minWidth: 100, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 10,
            page: true
        });
      
        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
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
        		title:"增加",
        		area: ['700px', '350px'],
        		type: 1, 
        		content: $("#popAdd")
        	});
        });

        //真正的添加
         //监听提交事件，其中data.filed就是需要提交的表单数据
        form.on('submit(add)', function (data) {
            $.post("<%=Const.ROOT%>ting/add",data.field,function(data){
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
                layer.open({
                        //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                        type: 1,
                        title: "修改",
                        area: ['700px', '350px'],
                        content:  $("#popUpdate")
               });
                //表单初始赋值
                form.val('uexample', data);
                //动态向表传递赋值当然也是异步请求的要数据的修改数据的获取
                setFormValue(obj,data);  
            }else if (obj.event === 'delete') {
                layer.confirm('真的删除么', function (index) {
                    $.getJSON('<%=Const.ROOT%>ting/delete',{'id':obj.data.id},function(msg) {
  						if(msg.code=="0"){
  							layer.msg(msg.msg, { icon: 6, time: 800},function(){
  								layer.close(index);
  								obj.del();
  								location.reload();
  							});
  						}else{
  							layer.msg(msg.msg, {icon: 5});
  						}	
  					}); 
                });
            }
        });
        //监听弹出框表单提交，massage是修改界面的表单数据
        function setFormValue(obj,data){
            form.on('submit(update)', function(message) {
          	  //console.log(message);
          	  $.ajax({
                url:'<%=Const.ROOT%>ting/update',
					type : 'POST',
					data : message.field,
					dataType:"JSON",
					success : function(msg) {
						console.log(msg);
						layer.closeAll();
						if(msg.code=="0"){
							layer.msg(msg.msg, { icon: 6, time: 800},function(){
								table.reload('currentTableId');//数据表格重
								layer.closeAll();//关闭弹出层
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
	<script type="text/html" id="cinema">
    {{d.cinema.name}}
	</script>
</body>
</html>