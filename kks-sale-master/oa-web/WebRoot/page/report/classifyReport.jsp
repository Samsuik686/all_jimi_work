<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/classifyReport.js?20170318"></script>
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
	<div class="easyui-layout" fit="true">
		<div region="center" title="机型分类报表">
			<div id="toolbar" style="padding:10px 15px;">
			  <div style="margin-bottom:5px">
				<span>
				 开始日期:
				<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;
				</span>
			     <span>
			            结束日期:
			     <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px">&nbsp;
			    </span>
			    <span>
					保内/保外:
					<select id="isWarranty"  style="width: 100px;" editable="false" panelHeight="auto" >
						<option value="">全部</option>
						<option value="0" selected="selected">保内</option>
						<option value="1">保外</option>
					</select>
				</span>&nbsp;
				<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
			</div>
			  <div>
			     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a>
			  </div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true"  fitColumns="true" toolbar="#toolbar">
				<thead>
					<tr>
						<!-- <th checkbox="true" field="w_repairId"></th> -->
						<th field="model" width="603" align="center">主板型号</th>
						<th field="usage" width="553" align="center">数量</th>
						<th field="ratio" width="555" align="center">百分比</th>
					</tr>
				</thead>
			</table>
			<!-- <div region="center" border="false" style="padding:8px;background:#fff;border:1px solid #ccc;"> -->
			<div id="chartdiv" style="margin-top: 30px;"></div>
		</div>
		</div>
</body>
</html>