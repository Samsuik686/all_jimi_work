<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/queryRepairState.js"></script>
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
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="维修进度查询" fit="true">
			<div id="toolbar" style="padding: 10px 15px;">
				<div>
					<table cellspacing="0" cellpadding="0" width="100%;">
						<tbody>
							<tr>
								<td style="padding-left: 30%;">
									<span><strong>扫描IMEI|智能锁ID</strong> &nbsp;<input type="text" style="width:220px; height:28Px; font-size:16px; font-weight:bold; color:#0066FF;" id="scanimei" onkeypress="if(event.keyCode==13) {scancData(event)}"/></span>
									<span><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a></span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="acceptanceTime" width="120" align="center">受理时间</th>
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="lockId" width="100" align="center">智能锁ID</th>
						<th field="marketModel" width="100" align="center">市场型号</th>
						<th field="cusName" width="150" align="center">送修单位</th>
						<th field="phone" width="100" align="center">手机号</th>
						<th field="state" width="100" align="center">进度</th>
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
						<th field="wxbjDesc" width="260" align="center">维修报价描述</th>
						<th field="zzgzDesc" width="260" align="center">最终故障</th>
						<th field="sjfjDesc" width="260" align="center">随机附件</th>
						<th field="solution" width="260" align="center">处理措施</th>
						<th field="address" width="300" align="center">联系地址</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>