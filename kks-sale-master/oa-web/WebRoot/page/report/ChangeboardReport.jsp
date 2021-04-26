<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/material/changeboard.js"> </script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}">
	<input type="hidden" id="curLoginUserId" value="${USERSESSION.uId}">
	<input type="hidden" id="changeboard_roleId"/><!-- 当前登录人角色 -->
	<input type="hidden" id="changeboard_uPosition"/><!-- 当前登录人职位 -->
	<input type="hidden" id="cb_sendFlag">
	<div class="easyui-layout" style="overflow:hidden; width: 100%;" fit="true" scroll="no">
		<div region="center" id="dic-center" style="overflow: auto" title="主板免费更换管理">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>申请人:&nbsp;<input type="text" class="form-search" id="searchByApplicater" style="width: 110px"></span>&nbsp;
					<span>开始日期:&nbsp;<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span>&nbsp;
					<span>结束日期:&nbsp;<input type="text" class="form-search" id="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span>&nbsp; 
					<span>
						维修|测试: 
						<select id="searchByRepairOrTest" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value = "" selected="selected">全部</option>
							<option value = "维修" >维修</option>
							<option value = "测试">测试</option>
						</select>
					</span>
					<span>
						状态: 
						<select id="searchByState" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value = "" selected="selected">全部</option>
							<option value = "1">待处理</option>
							<option value = "2">不同意</option>
							<option value = "3">已完成</option>
						</select>&nbsp;
					</span>
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryChangeboardListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="changeboard_exportInfo();">导出</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="changeboard_refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="changeboard_DataGrid" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">		   
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="cusName" width="180px" align="center">客户名称</th>
						<th field="imei" width="130px" align="center">IMEI</th>
						<th field="state" width="100px" align="center">状态</th>
						<th field="sendFlag" width="100px" align="center">处理岗位</th>
						<th field="model" width="120px" align="center">主板型号</th>
						<th field="isWarranty" width="120px" align="center">保内 | 保外</th>
						<th field="number" width="80px" align="center">申请数量</th>
						<th field="repairOrTest" width="100px" align="center">维修 | 测试</th>
						<th field="applicater" width="100px" align="center">申请人</th>
						<th field="appTime" width="160px" align="center">申请日期</th>
						<th field="purpose" width="220px" align="center">换板原因</th>
						<th field="charger" width="100px" align="center">主管</th>
						<th field="chargerUpdateTime" width="160px" align="center">主管批复日期</th>
						<th field="manager" width="100px" align="center">经理</th>
						<th field="managerUpdateTime" width="160px" align="center">经理批复日期</th>
						<th field="serviceCharger" width="100px" align="center">客服文员</th>
						<th field="serviceUpdateTime" width="160px" align="center">换板日期</th>
						<th field="remark" width="220px" align="center">备注</th>				
					</tr>
				</thead>
			</table>
			</div>
		</div>
	</body>
</html>