<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/badrateReport.js?20170324"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>

</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="机器不良率报表" >
			<div id="toolbar" style="padding:10px 15px;">
			    <div style="margin-bottom:5px">			   
			        <span>主板型号:&nbsp;<input type="text" class="form-search" id="jx" name="model" style="width:135px"></span>&nbsp;
					<span>
						保内/保外:
						<select id="isWarranty" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">保内</option>
							<option value="1">保外</option>
						</select>
					</span>&nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>			
				</div>
				<div id="export_div" style="display:none;">
				  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a> 
				</div>
				 
			</div>
			<table id="DataGrid1" class="easyui-datagrid" fit="true" singleSelect="true" sortable="true" rownumbers="false"  fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="model" width="80" align="center">主板型号</th>					
						<th field="repairs" width="80" align="center">返修机数量</th>
						<th field="goods" width="80" align="center">出货总数</th>
						<th field="percent" width="80" align="center">总返修比率</th>
					</tr>
				</thead>
			</table>
			
		</div>
		
	</div>
</body>
</html>