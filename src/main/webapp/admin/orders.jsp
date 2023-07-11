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
								<label class="layui-form-label">订单编号</label>
								<div class="layui-input-inline">
									<input type="text" name="ordersno" autocomplete="off"
										class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<a class="layui-btn" lay-submit="" lay-filter="data-search-btn">搜索</a>
							</div>
						</div>
					</form>
				</div>
			</fieldset>
			<table class="layui-hide" id="currentTableId"
				lay-filter="currentTableFilter"></table>
			<script type="text/html" id="currentTableBar">
				{{#if(d.status=='未确认'){}}
			    <a class="layui-btn layui-btn-xs layui-btn-normal data-count-edit" lay-event="shenhe">确认</a>
				{{#}}}
				{{#if(d.status=='申请退票'){}}
			    <a class="layui-btn layui-btn-xs layui-btn-warm data-count-edit" lay-event="tui">同意退</a>
				{{#}}}
            	<a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
        </script>
		</div>
	</div>


	<script src="<%=Const.ROOT%>admin/lib/layui-v2.5.4/layui.js" charset="utf-8"></script>
	<script>
	
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
       		
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
        table.render({
            elem: '#currentTableId',
            url: '<%=Const.ROOT%>orders/list',
            cols: [[
				{field:'id',hide:true,width: 0},
				{width: 70,style:'line-height:100px',templet:'#no',title: 'NO', sort: true},
                {field:'ordersno', width: 140, title: '订单号'},
                {templet:'#uname', width: 120, title: '姓名-电话'},
                {templet:'#ciname', width: 110, title: '影剧院-厅'},
                {templet:'#goods', width: 120, title: '电影'},
                {templet:'#starttime', width: 150, title: '放映时间'},
                {field: 'optime', width: 120, title: '下单时间'},
                {field: 'seats', width: 90, title: '座位'},
                {field: 'price', width: 60, title: '价格'},
                {field: 'status', width: 100, title: '状态'},
                {title: '操作', minWidth: 40, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 10,
            page: true,
            done: function(res, curr, count){
            	$(".pic").parent(".layui-table-cell").height("60px");
            }
        });
       
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'shenhe') {
            	layer.confirm('是否确认?', function (index) {
                    $.getJSON('<%=Const.ROOT%>orders/changestatus',{'id':obj.data.id,'status':'已确认'},function(msg) {
  						if(msg.code=="0"){
  							layer.msg(msg.msg, { icon: 6, time: 800},function(){
  								layer.close(index);
  								location.reload();
  							});
  						}else{
  							layer.msg(msg.msg, {icon: 5});
  						}	
  					}); 
            	});
	        }else if (obj.event === 'tui') {
            	layer.confirm('是否同意退票?', function (index) {
                    $.getJSON('<%=Const.ROOT%>orders/changestatus',{'id':obj.data.id,'status':'已退票'},function(msg) {
  						if(msg.code=="0"){
  							layer.msg(msg.msg, { icon: 6, time: 800},function(){
  								layer.close(index);
  								location.reload();
  							});
  						}else{
  							layer.msg(msg.msg, {icon: 5});
  						}	
  					}); 
            	});
	        }else if (obj.event === 'delete') {
                layer.confirm('真的删除行么', function (index) {
                    $.getJSON('<%=Const.ROOT%>orders/delete',{'id':obj.data.id},function(msg) {
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
	});
    
	</script>
	<script type="text/html" id="no">
    {{d.LAY_TABLE_INDEX+1}}
	</script>
	<script type="text/html" id="uname">
    <span class="pic">{{d.users.name+'<br/>'+d.users.phone}}</span>
	</script>
	<script type="text/html" id="ciname">
     <span class="pic">{{d.cinema.name+'<br/>'+d.ting.name}}</span>
	</script>
	<script type="text/html" id="goods">
    {{d.goods.name}}
	</script>
	<script type="text/html" id="starttime">
	<span class="pic">{{d.paipian.opday+'<br/>'+d.paipian.starttime+"~"+d.paipian.endtime}}</span>
	</script>
</body>
</html>