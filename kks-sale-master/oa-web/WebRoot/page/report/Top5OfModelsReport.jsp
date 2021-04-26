<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/top5OfModelsReport.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="各机型前5项故障报表">
			<div  id="toolbar" style="padding:10px 15px;">
			  <div style="margin-bottom:5px">
				    <span>&nbsp;主板型号: <input type="text" class="form-search" id="jx" name="boardModel" style="width:160px"/>&nbsp;</span>
					<span>&nbsp;开始日期: <input type="text" class="form-search" id="outTimeStart" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width:160px"/>&nbsp;</span>
					<span>&nbsp;结束日期: <input type="text" class="form-search" id="outTimeEnd"  name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width:160px"/>&nbsp;</span>
				    <span>
						保内/保外:
						<select id="isWarranty"  style="width: 180px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">保内</option>
							<option value="1">保外</option>
						</select>
					</span>&nbsp;
				    <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a> 
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage(currentPageNum);">刷新</a> 
				</div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true" fitColumns="true" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="boardModel" width="427" align="center">主板型号</th>
						<th field="faultType" width="427" align="center">故障分类</th>
						<th field="number" width="427" align="center">故障次数</th>
						<th field="percent" width="429" align="center">百分比</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>