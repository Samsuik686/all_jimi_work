<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/saledata.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<style type="text/css">
/* 扫描IMEI */
strong {
	font-family: "微软雅黑";
	font-size: 16px;
	color: #0066FF;
}
</style>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow:hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow:auto" title="销售数据" fit="true">
			<div id="toolbar" style="padding: 10px 15px;">
				<div>
					<table cellspacing="0" cellpadding="0" width="100%;">
						<tbody>
							<tr>
								<td style="padding-left: 30%;">
									<span><strong>扫描IMEI|智能锁ID</strong> &nbsp;<input type="text" style="width: 220px; height: 28Px; font-size: 16px; font-weight: bold; color: #0066FF;" id="scanimei" onkeypress="if(event.keyCode==13) {scancData(event)}"/></span> 
									<span><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a></span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="lockInfo" hidden="true"></th>
						<th field="imei" width="160" align="center">IMEI</th>
						<th field="xpId" width="220" align="center">芯片ID</th>
						<th field="sfVersion" width="200" align="center">主板软件版本号</th>
						<th field="hdVersion" width="100" align="center">主板硬件版本号</th>
						<th field="lockId" width="150" align="center">智能锁ID</th>
						<th field="bluetoothId" width="150" align="center">蓝牙ID</th>
						<th field="bill" width="150" align="center">订单号</th>
						<th field="mcType" width="110" align="center">市场型号</th>
						<th field="cpName" width="130" align="center">公司名称</th>
						<th field="productionDate" width="160" align="center">生产日期</th>
						<th field="outDate" width="160" align="center">出货日期</th>
						<th field="status" width="130" align="center">设备状态</th>
						<th field="boxNumber" width="160" align="center">箱号</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>