<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/zbxhmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_ZBXH" />
	<input type="hidden" id="gIds" />
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="west" id="dic-west" style="width: 200px;" title="主板型号类别">
			<div class="easyui-panel" style="background-color: #E0ECFF">
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="treesAddwindow()">增加</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="treesDelete()">删除</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treesUpdateWindow()">修改</a>
			</div>
			<ul id="typeTree" class="easyui-tree">

			</ul>
			<div class="easyui-panel" style="background-color: #E0ECFF">
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="zbxhTypeAddWindow('BASE_CJLX')">增加</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="zbxhTreesDelete('BASE_CJLX')">删除</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treesUpdateWindow('BASE_CJLX')">修改</a>
			</div>
			<ul id="createTypeTree" class="easyui-tree">

			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="主板型号管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>市场型号: <input type="text" class="form-search" id="searchBymarketModel" style="width: 135px" /> </span>&nbsp;
					<span>主板型号: <input type="text" class="form-search" id="searchBymodel" style="width: 135px" /> </span>&nbsp;
					<span>
						禁用
						<select id="searchByEnabledFlag" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>&nbsp;
					</span>
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="zbxh_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="zbxh_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="zbxh_deleteInfo();">删除</a>
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
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="batchModify();">批量修改</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="mId"></th>
						<th field="marketModel" width="220" align="center">市场型号</th>
						<th field="model" width="220" align="center">主板型号</th>
						<th field="modelType" width="220" align="center">型号类别</th>
						<th field="createTypeName" width="220" align="center">创建类型</th>
						<th field="enabledFlag" width="220" align="center">禁用</th>
						<th field="remark" width="220" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新主板型号信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 300px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="mId_hidden" style="width: 150px;" maxlength="11">
									</td>
								</tr>
								<tr>
									<td>市场型号：</td>
									<td>
										<input type="text" id="marketModelP" style="width: 150px;" maxlength="100" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>主板型号：</td>
									<td>
										<input type="text" id="modelP" style="width: 150px;" maxlength="255" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>型号类别：</td>
									<td>
										<input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
									</td>
								</tr>
								<tr>
									<td>创建类型：</td>
									<td>
										<input id="addCreateType" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
									</td>
									<td>是否禁用：</td>
									<td align="left">
										<input type="radio" name="enabledFlag" value="0" checked="checked"/>否 &nbsp; 
										<input type="radio" name="enabledFlag" value="1"/>是
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7">
										<textarea id="remarkP" style="width: 100%; height: 80px;" maxlength="255"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="zbxhmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="zbxhmanageSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
			<!-- 批量修改-->
			<div id="batchMoidfyWindow" class="easyui-window" title="更新主板型号信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 300px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="batchForm" action="">
							<table align="center">
								<tr>
									<td>型号类别：</td>
									<td>
										<input id="batchType" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
									</td>
								</tr>
								<tr>
									<td>创建类型：</td>
									<td>
										<input id="batchCreateType" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="batchModify" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="doBatchModify()">保存</a>
						<a id="batchWindowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindows('batchMoidfyWindow')">关闭</a>
					</div>
				</div>
			</div>
	
			<!-- 添加-START-->
			<div id="wg" class="easyui-window" title="分类增加窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 350px; height: 220px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<form id="group_form" action="">
							<table>
								<tr>
									<td>组名：</td>
									<td>
										<input type="text" id="gName" style="width: 200px" class="easyui-validatebox" required="true" missingMessage="请填写此字段">
									</td>
									<td>
										<input type="hidden" id="enumSn" style="width: 200px">
									</td>
									<td>
										<input type="hidden" id="gId" style="width: 200px" value="0">
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeUpdate" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:void(0)" onclick="treesAddOrUpdate()">保存</a> 
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="tree_windowClose()">关闭</a>
					</div>
				</div>
			</div>
			<!-- 添加-END-->
		</div>
	</div>
</body>

</html>