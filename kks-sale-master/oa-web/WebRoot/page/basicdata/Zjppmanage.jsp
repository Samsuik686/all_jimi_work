<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/zjppmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="终检型号匹配管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>主板型号:&nbsp;<input type="text" class="form-search" id="searchByModel" style="width: 200px" /> </span>&nbsp; &nbsp; &nbsp; &nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="zjpp_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="zjpp_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="zjpp_deleteInfo();">删除</a>
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
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="matchId"></th>
						<th field="model" width="220" align="center">主板型号</th>
						<th field="step" width="220" align="center">测试步骤</th>
						<th field="testMethod" width="220" align="center">方法</th>
						<th field="becareful" width="220" align="center">注意事项</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新终检型号匹配信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 380px; padding: 5px;" toolbar="#toolbar">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="matchId_hidden" style="width: 220px;" maxlength="11">
									</td>
								</tr>
								<tr>
									<td>主板型号：</td>
									<td>
										<input type="text" id="modelP" style="width: 220px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>测试步骤：</td>
									<td>
										<input type="text" id="stepP" style="width: 220px;" maxlength="20" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>方&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;法：</td>
									<td colspan="7">
										<textarea id="testMethodP" style="width: 100%; height: 80px;" maxlength="2000" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
								<tr>
									<td>注意事项：</td>
									<td colspan="7">
										<textarea id="becarefulP" style="width: 100%; height: 80px;" maxlength="2000" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="zjppmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="zjppmanageSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>