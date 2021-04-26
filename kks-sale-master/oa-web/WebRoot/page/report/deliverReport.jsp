<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/deliverReport.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="寄件月报表">
			<div id="toolbar" style="padding:10px 15px;">
			   <div style="margin-bottom:5px">
					<span>选择日期：<input type="text" class="form-search" id="dateTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',readOnly:true})" style="width: 110px"></span>
					<span>主板型号：<input type="text" class="form-search" id="model" style="width:135px"/>&nbsp;</span>
					<span>开始日期：<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;</span>
					<span>结束日期：<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;</span>
				    <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryDeliverList('')">查询</a>
				</div>
				<div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a> 
				</div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid" fit="true"  singleSelect="true" sortable="true" rownumbers="false" pagination="false" fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="model" width="150" align="center">主板型号</th>
						<th field="date1"  width="50"  align="center">1日</th>
						<th field="date2"  width="50" align="center">2日</th>
						<th field="date3"  width="50" align="center">3日</th>
						<th field="date4"  width="50" align="center">4日</th>
						<th field="date5"  width="50" align="center">5日</th>
						<th field="date6"  width="50" align="center">6日</th>
						<th field="date7"  width="50" align="center">7日</th>
						<th field="date8"  width="50" align="center">8日</th>
						<th field="date9"  width="50" align="center">9日</th>
						<th field="date10"  width="50" align="center">10日</th>
						<th field="date11"  width="50" align="center">11日</th>
						<th field="date12"  width="50" align="center">12日</th>
						<th field="date13"  width="50" align="center">13日</th>
						<th field="date14"  width="50" align="center">14日</th>
						<th field="date15"  width="50" align="center">15日</th>
						<th field="date16"  width="50" align="center">16日</th>
						<th field="date17"  width="50" align="center">17日</th>
						<th field="date18"  width="50" align="center">18日</th>
						<th field="date19"  width="50" align="center">19日</th>
						<th field="date20"  width="50" align="center">20日</th>
						<th field="date21"  width="50" align="center">21日</th>
						<th field="date22"  width="50" align="center">22日</th>
						<th field="date23"  width="50" align="center">23日</th>
						<th field="date24"  width="50" align="center">24日</th>
						<th field="date25"  width="50" align="center">25日</th>
						<th field="date26"  width="50" align="center">26日</th>
						<th field="date27"  width="50" align="center">27日</th>
						<th field="date28"  width="50" align="center">28日</th>
						<th field="date29"  width="50" align="center">29日</th>
						<th field="date30"  width="50" align="center">30日</th>
						<th field="date31"  width="50" align="center">31日</th>
						<th field="totalNum" width="130" align="center">寄件月统计</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>