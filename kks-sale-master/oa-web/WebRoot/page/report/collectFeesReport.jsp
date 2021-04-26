<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!-- <script type="text/javascript">
 $(document).ready(function(){
    var d = new Date();
    function addzero(v) {
	 if (v < 10) 
		 return '0' + v;
	 return v.toString();
	 } 
    var s = d.getFullYear().toString() + '-'+addzero(d.getMonth() + 1) + '-' + addzero(d.getDate());
    document.getElementById('searchDate').value=s;
  });
</script> -->
<script type="text/javascript" src="${ctx}/res/js/report/collectFeesReport.js?20170118"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" scroll="no" fit="true">
		<div region="center" title="售后收费月报表">
			<div id="toolbar" style="padding:10px 15px;">
			    <div style="margin-bottom:5px">
					<span>选择日期(年-月):&nbsp;<input type="text" class="form-search" id="searchDate" onfocus="WdatePicker({dateFmt:'yyyy-MM',readOnly:true})" style="width: 110px">&nbsp;</span> 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				
				<div>
				     <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" fit="true" singleSelect="true" sortable="true" rownumbers="false"  fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="payTimes" width="220" align="center">日期</th>
						<th field="repairPay" width="220" align="center">维修费(￥)</th>
						<th field="materialPay" width="220" align="center">配件费(￥)</th>
						<th field="rechargePay" width="220" align="center">平台费(￥)</th>
						<th field="phoneRecharge" width="220" align="center">手机卡费(￥)</th>
						<th field="wlwkRecharge" width="220" align="center">物联网卡费(￥)</th>
						<th field="logCost" width="220" align="center">运费(￥)</th>
						<th field="ratePrice" width="220" align="center">税费(￥)</th>
						<th field="totalPay" width="220" align="center">日汇总(￥)</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>