<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/testMaterialReportRercent.js?20170318"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script src="${ctx}/res/js/amcharts_3.21.0/amcharts/amcharts.js"></script>
<script src="${ctx}/res/js/amcharts_3.21.0/amcharts/pie.js"></script>
<script src="${ctx}/res/js/amcharts_3.21.0/amcharts/themes/light.js"></script>
<script src="${ctx}/res/js/amcharts_3.21.0/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="${ctx}/res/js/amcharts_3.21.0/amcharts/plugins/export/export.css" type="text/css" media="all" />

<style>
#chartdiv {
	width		: 100%;
	height		: 600px;
	font-size	: 11px;
}						
</style>

</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="center" title="试流料不良率汇总报表" style="overflow: auto" >
			<div id="toolbar" style="padding:10px 15px;">
				  <div style="margin-bottom:2px">
					<span> 物料编码：<input type="text" id="searchByMaterialNumber" class="form-search" style="width: 150px" placeholder="精确匹配"/> </span>
					<span> 物料名称：<input type="text" id="searchByMaterialName" class="form-search" style="width: 150px"/> </span>
					<span> 订单号：<input type="text" id="searchByBill" class="form-search" style="width: 135px" placeholder="精确匹配"/> </span>
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				 </div>
				 <div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a>
				 </div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="bill" width="130" align="center">订单号</th>
						<th field="project" width="100" align="center">项目</th>
						<th field="materialNumber" width="150" align="center">物料编码</th>
						<th field="materialName" width="150" align="center">物料名称</th>
						<th field="createUser" width="80" align="center">创建人</th>
						<th field="createTime" width="150" align="center">创建时间</th>
						<th field="materialSpecification" width="150" align="center">物料规格</th>
						<th field="materialReplace" width="150" align="center">所要替换的物料编码</th>
						<th field="placeNumber" width="80" align="center">位号</th>
						<th field="supplier" width="100" align="center">供应商</th>
						<th field="sMTTestTime" width="130" align="center">SMT试流时间</th>
						<th field="sMTWorkOrder" width="120" align="center">SMT工单</th>
						<th field="sMTTestResult" width="80" align="center">SMT试流结果</th>
						<th field="assemblyTestTime" width="80" align="center">组装试流时间</th>
						<th field="assemblyTestTesult" width="80" align="center">组装试流结果</th>
						<th field="testResult" width="80" align="center">试流判定</th>
					<!-- 	<th field="updateUser" width="80" align="center">修改人</th>
						<th field="updateTime" width="130" align="center">修改时间</th> -->
						<th field="outTime" width="80" align="center">出货日期</th>
						<th field="matCount" width="80" align="center">问题数量</th>
						<th field="amount" width="80" align="center">订单数量</th>
						<th field="percent" width="80" align="center">百分比</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>