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
		<div region="center" id="dic-center" style="overflow: auto" title="主板免费更换管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>申请人: <input type="text" class="form-search" id="searchByApplicater" style="width: 135px"></span>&nbsp;
					<span>
						&nbsp;维修|测试: 
						<select id="searchByRepairOrTest" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value = "" selected="selected">全部</option>
							<option value = "维修" >维修</option>
							<option value = "测试">测试</option>
						</select>&nbsp;
					</span>
					<span>
						&nbsp;状态: 
						<select id="searchByState" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value = "">全部</option>
							<option value = "1" selected="selected">待处理</option>
							<option value = "2">不同意</option>
							<option value = "3">已完成</option>
						</select>&nbsp;
					</span>
					<span>开始日期: <input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> </span>&nbsp;
					<span>结束日期: <input type="text" class="form-search" id="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryChangeboardListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pficon" plain="true" onclick="changeboard_appInfo();">批复</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pficon" plain="true" onclick="changeboard_next();">发送到下一工站</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<!-- <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="changeboard_exportInfo();">导出</a></td>
							<td><div class="datagrid-btn-separator"></div></td> -->
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
						<th field="state" width="120px" align="center">状态</th>
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
		<div style="position:fixed;">
				<div id="changeboard_update_w" class="easyui-window" title="保内对换主板审批" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
					  style="visibility:hidden; width: 520px; height: 500px; padding: 5px;">
					<div class="easyui-layout" fit="true">
						<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
							<form id="changeboardUpdate_form" action="">
								<table align="center" cellspacing="2">
									<tr>
										<td><input id="changeboard_id"  type="text" style="display: none;"/></td>
									</tr>
									<tr>
										<td>客户名称：</td>
										<td><input type="text" id="changeboard_cusName" style="width: 150px;" disabled="disabled"> </td>
										<td>&nbsp;&nbsp;&nbsp;I&nbsp;M&nbsp;E&nbsp;I：</td>
										<td><input type="text" id="changeboard_imei" style="width: 150px;" disabled="disabled"></td>
									</tr>
									<tr>
										<td>主板型号：</td>
										<td><input type="text" id="changeboard_model" style="width: 150px;" disabled="disabled"></td>
										<td>保内|保外：</td>
										<td><input type="text" id="changeboard_isWarranty" style="width: 150px;" disabled="disabled"></td>
									</tr>
									<tr>
										<td>申请数量：</td>
										<td><input type="text" id="changeboard_number" style="width: 150px;" disabled="disabled"></td>
										<td>数据来源：</td>
										<td><input type="text" id="changeboard_repairOrTest" style="width: 150px;" disabled="disabled"></td>
									</tr>
									<tr>
										<td>&nbsp;申&nbsp;请&nbsp;人：</td>
										<td><input type="text" id="changeboard_applicater" style="width: 150px;" disabled="disabled"/></td>
										<td>申请日期：</td>
										<td><input type="text" id="changeboard_appTime" style="width: 150px;" disabled="disabled"></td>
									</tr>
									<tr>
										<td>换板原因：</td>
										<td colspan="7"><textarea id="changeboard_purpose" style="width: 100%; height: 50px;" disabled="disabled"></textarea></td>
									</tr>
									<tr>
										<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
										<td colspan="7"><textarea id="changeboard_remark" style="width: 100%; height: 50px; resize:none;" ></textarea></td>
									</tr>
									<tr>
										<td><font style="color: blue;">主管批复：</font></td>
										<td>
											<input type="radio" name="changeboard_state" value="1" class="chargeDo" disabled="disabled"/>同意
											<input type="radio" name="changeboard_state" value="2" class="chargeDo" disabled="disabled"/>不同意
										</td>
										<!--取消经理批复-->
										<!--
										<td><font style="color: red;">经理批复：</font></td>
										<td>
											<input type="radio" name="changeboard_state" value="3" class="managerDo" disabled="disabled"/>同意
											<input type="radio" name="changeboard_state" value="4" class="managerDo" disabled="disabled"/>不同意
										</td>
										-->
									</tr>
									<tr>
										<td>维修主管：</td>
										<td><input type="text" id="changeboard_charger" style="width: 150px;" disabled="disabled" value="${USERSESSION.uName}"/></td>
										<td>审批日期：</td>
										<td><input type="text" id="changeboard_chargerUpdateTime" style="width: 150px;" disabled="disabled"></td>	
									</tr>
									<!--取消售后经理-->
									<!--
									<tr>
										<td>售后经理：</td>
										<td><input type="text" id="changeboard_manager" style="width: 150px;" disabled="disabled" value="${USERSESSION.uName}"/></td>
										<td>审批日期：</td>
										<td><input type="text" id="changeboard_managerUpdateTime" style="width: 150px;" disabled="disabled"></td>	
									</tr>
									-->
									<tr>
										<td><font style="color: blue;">确认换板：</font></td>
										<td>
											<input type="radio" name="changeboard_state" value="1" class="serviceChargerDo" disabled="disabled"/>待换板
											<input type="radio" name="changeboard_state" value="5" class="serviceChargerDo" disabled="disabled"/>待返还
											<input type="radio" name="changeboard_state" value="6" class="serviceChargerDo" disabled="disabled"/>已完成
										 </td>
									</tr>
									<tr>
										<td>物料管理员：</td>
										<td><input type="text" id="changeboard_serviceCharger" style="width: 150px;" disabled="disabled" value="${USERSESSION.uName}"/></td>
										<td>换板日期：</td>
										<td><input type="text" id="changeboard_serviceUpdateTime" style="width: 150px;" disabled="disabled"></td>	
									</tr>
									<tr>
										<td>返还日期：</td>
										<td><input type="text" id="changeboard_testBackTime" style="width: 150px;" disabled="disabled"></td>
									</tr> 
								</table>
							</form>
						</div>
						<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
							<a class="easyui-linkbutton notShow" iconCls="icon-ok" href="javascript:void(0)" onclick="changeboard_update()">保存</a>
							<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="changeboard_update_w_close()">关闭</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>