<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/deliverYearReport.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="寄件年报表" >
			<div id="toolbar" style="padding:10px 15px;">
			  <div style="margin-bottom:5px">
					<span>选择日期：<input type="text" class="form-search" id="dateTime" onfocus="WdatePicker({dateFmt:'yyyy',readOnly:true})" style="width: 110px"></span>
					<span>主板型号：<input type="text" class="form-search" id="model" style="width:135px"/>&nbsp;</span>
					<span>开始日期：<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;</span>
					<span>结束日期：<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;</span>
				 	<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryDeliverYearList('')">查询</a>
				</div>
				<div>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a>
				</div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid" fit="true"  singleSelect="true" sortable="true" rownumbers="false" pagination="false" fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="model" width="150" align="center">主板型号</th>
						<th field="month1"  width="50"  align="center">1月</th>
						<th field="month2"  width="50" align="center">2月</th>
						<th field="month3"  width="50" align="center">3月</th>
						<th field="month4"  width="50" align="center">4月</th>
						<th field="month5"  width="50" align="center">5月</th>
						<th field="month6"  width="50" align="center">6月</th>
						<th field="month7"  width="50" align="center">7月</th>
						<th field="month8"  width="50" align="center">8月</th>
						<th field="month9"  width="50" align="center">9月</th>
						<th field="month10"  width="50" align="center">10月</th>
						<th field="month11"  width="50" align="center">11月</th>
						<th field="month12"  width="50" align="center">12月</th>
						<th field="totalNum" width="130" align="center">寄件年统计</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>