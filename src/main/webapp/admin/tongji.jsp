<%@page import="cn.util.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
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
				<legend>销售统计</legend>
				<div style="margin: 10px 10px 10px 10px">
					<form class="layui-form layui-form-pane" action="">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">开始时间</label>
								<div class="layui-input-inline">
									<input type="text" name="time1" id="time1" autocomplete="off"
										class="layui-input">
								</div>
								
								<label class="layui-form-label">结束时间</label>
								<div class="layui-input-inline">
									<input type="text" name="time2" id="time2" autocomplete="off"
										class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<a class="layui-btn" lay-submit="" lay-filter="data-search-btn">统计</a>
							</div>
						</div>
					</form>
				</div>
			</fieldset>
			<div id="show">
			 <div id="main1" style="width: 1000px;height:450px;"></div>
			  <hr/>
			</div>
		</div>
	</div>

	<script src="<%=Const.ROOT%>admin/lib/layui-v2.5.4/layui.js" charset="utf-8"></script>
	<script src="<%=Const.ROOT%>admin/js/echarts.min.js" charset="utf-8"></script>
	<script>
    layui.use(['form', 'table','laydate'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
        	laydate = layui.laydate;
        	
        	//日期选择框的id
            laydate.render({
                elem: '#time1'
            });
            laydate.render({
                elem: '#time2'
            });
	
         	// 基于准备好的dom，初始化echarts实例
            var myChart1 = echarts.init(document.getElementById('main1'));
            
        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
        	$.getJSON("<%=Const.ROOT%>orders/tongji",data.field,function(data){
        		// 指定图表的配置项和数据
                var option1 = {
                    title: {
                        text: '各电影院销售额统计'
                    },
                    tooltip: {},
                    legend: {
                        data:['金额']
                    },
                    xAxis: {
                        data: []
                    },
                    yAxis: {},
                    series: [{
                        name: '金额',
                        type: 'bar',
                        data: []
                    }]
                };
        		for(i=0;i<data.result.length;i++){
        			option1.xAxis.data.push(data.result[i].name);
                	option1.series[0].data.push(data.result[i].price);
        		}
        		myChart1.setOption(option1);
    			$("#show").show();
    		});
            return false;
        });
        
        
  });
	</script>
</body>
</html>