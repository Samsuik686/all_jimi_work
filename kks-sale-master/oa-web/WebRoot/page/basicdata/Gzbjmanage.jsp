<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/gzbjmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="故障报价管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>故障类别:&nbsp;<input type="text" class="form-search" id="searchByFaultType" style="width: 200px" /> </span>&nbsp; &nbsp; &nbsp; &nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="gzbj_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="gzbj_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="gzbj_deleteInfo();">删除</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xzmbicon" plain="true" onclick="downloadTemplet();">下载模板</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="importInfo();">导入</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="faultType" width="150" align="left">故障类别</th>
						<th field="faultDesc" width="220" align="left">故障现象</th>
						<th field="costs" width="80" align="center">价格(￥)</th>
						<th field="remark" width="220" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新故障报价信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 380px; padding: 5px;" toolbar="#toolbar">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="id" style="width: 280px;" maxlength="11">
									</td>
								</tr>
								<tr>
									<td>故障类别：</td>
									<td>
										<input type="text" id="faultType" style="width: 280px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>故障现象：</td>
									<td colspan="7">
										<textarea id="faultDesc" style="width: 100%; height: 80px;" maxlength="255"></textarea>
									</td>
								</tr>
								<tr>
									<td>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>
									<td>
										<input type="text" id="costs" style="width: 280px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写金额"/>
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7">
										<textarea id="remark" style="width: 100%; height: 80px;" maxlength="255"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="gzbjmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="gzbjmanageSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>