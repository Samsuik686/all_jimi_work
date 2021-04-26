<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/warrantyReport.js?20170106"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="售后收费明细报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>客户名称: <input type="text" class="form-search" id="cusName_search" style="width: 150px"></span>&nbsp;
					<span>IMEI号: <input type="text" class="form-search"	placeholder="多个IMEI号以逗号分隔" id="imei_search" style="width: 150px"></span>&nbsp;
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span> &nbsp;
					<span>结束日期: <input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span> &nbsp;
					<span>汇款方式:
						<select id="payMethod_search" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="personPay">人工付款</option>
							<option value="aliPay">支付宝付款</option>
							<option value="weChatPay">微信付款</option>
						</select>&nbsp;
					</span>
					<span style="width: 200px"></span>&nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="false" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="warrId"></th>
						<th field="repairNumber" width="120" align="center">送修批次</th>
						<th field="cusName" width="150" align="center">客户名称</th>
						<th field="modelType" width="80" align="center">主板型号</th>
						<th field="imei" width="130" align="center">IMEI</th>
						<th field="priceDate" width="150" align="center">报价日期</th>
						<th field="priceReason" width="80" align="center">报价原因</th>
						<th field="zzgzDesc" width="220" align="center">报价故障描述</th>
						<th field="solution" width="220" align="center">处理措施</th>
						<th field="number" width="80" align="center">数量</th>
						<th field="unitPrice" width="80" align="center">单价(￥)</th>
						<th field="totalMoney" width="80" align="center">合计金额(￥)</th>
						<th field="remAccount" width="80" align="center">汇款方式</th>
						<th field="payReason" width="120" align="center">收款原因</th>
						<th field="payDate" width="150" align="center">付款日期</th>
						<th field="isPay" width="80" align="center">确认结果</th>
						<th field="priceRemark" width="220" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>