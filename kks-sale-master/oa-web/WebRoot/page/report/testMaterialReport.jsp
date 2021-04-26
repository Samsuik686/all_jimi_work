<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/testMaterialReport.js?20170318"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>

</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="试流料返修明细报表">
			<div id="toolbar" style="padding:10px 15px;">
				  <div style="margin-bottom:2px">
					<span>受理开始日期:&nbsp; <input type="text" id="startTime" class="form-search" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>
					<span>销售开始日期:&nbsp; <input type="text" id="saleStartTime" class="form-search" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>
					<span>&nbsp;物料编码：<input type="text" id="searchByMaterialNumber" class="form-search" style="width: 150px" placeholder="精确匹配"/> </span>
					<span>&nbsp;&nbsp;订单号：<input type="text" id="searchByBill" class="form-search" style="width: 135px" placeholder="精确匹配"/> </span>
					<span>&nbsp;IMEI：<input type="text" id="searchByImei" class="form-search" style="width: 135px" placeholder="精确匹配"/> </span>
				 </div>
				 <div style="margin-bottom:5px">	
				    <span>受理结束日期:&nbsp; <input type="text" id="endTime" class="form-search" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>
				    <span>销售结束日期:&nbsp; <input type="text" id="saleEndTime" class="form-search" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>
					<span> &nbsp;物料名称：<input type="text" id="searchByMaterialName" class="form-search" style="width: 150px" placeholder="精确匹配"/> </span>
					<span> &nbsp;&nbsp;试流料 :
						<select id="testMatfalg" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">异常</option>
							<option value="1">正常</option>
						</select>
					</span>&nbsp; 
						<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				 </div>
				 <div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a>
				 </div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" striped="true" pageSize="20" fitColumns="true" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="bill" width="150" align="center">订单号</th>
						<th field="project" width="130" align="center">项目</th>
						<th field="imei" width="150" align="center">IMEI</th>
						<th field="salesTime" width="130" align="center">出货日期</th>
						<th field="materialNumber" width="150" align="center">物料编码</th>
						<th field="materialName" width="150" align="center">物料名称</th>
						<th field="testMatfalg" width="100" align="center">试流料</th>
						<th field="engineer" width="100" align="center">维修员</th>
						<th field="zzgzDesc" width="200" align="center">最终故障</th>
						<th field="solution" width="200" align="center">处理措施</th>
						<th field="acceptanceTime" width="130" align="center">受理时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>