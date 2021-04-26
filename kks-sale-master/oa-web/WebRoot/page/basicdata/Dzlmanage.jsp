<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/dzlmanage.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_DZL" />
	<input type="hidden" id="gIds" />
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="west" id="dic-west" style="width: 200px;" title="电子料类别">
			<div class="easyui-panel" style="background-color: #E0ECFF">
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="treesAddwindow()">增加</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="treesDelete()">删除</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treesUpdateWindow()">修改</a>
			</div>
			<ul id="typeTree" class="easyui-tree">

			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="电子料管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px"> 
					<span>名称: <input type="text" class="form-search" id="searchByProName" style="width: 150px" /> </span>&nbsp;&nbsp;
					<span>编码: <input type="text" class="form-search" id="searchByProNO" style="width: 150px" /> </span>&nbsp;&nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="dzl_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="dzl_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="dzl_deleteInfo();">删除</a>
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
						<th checkbox="true" field="dzlId"></th>
						<th field="dzlType" width="100px" align="left">类别</th>
						<th field="proNO" width="140" align="left">编码</th>
						<th field="proName" width="200" align="left">名称</th>
						<th field="proSpeci" width="280" align="left">规格</th>
						<th field="consumption" width="80" align="center">用量</th>
						<th field="masterOrSlave" width="50px" align="center">M|T</th>
						<th field="placesNO" width="80" align="center">序号</th>
						<th field="enabledFlag" width="80" align="center">禁用</th>
						<th field="remark" width="220" align="center">备注</th>
					</tr>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">	
			<div id="updateWindow" class="easyui-window" title="更新电子料信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility:hidden; width: 420px; height: 350px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td><input type="hidden" id="dzlId"/></td>
								</tr>
								<tr>
									<td>编码：</td>
									<td colspan="3"><input type="text" id="proNO" style="width: 100%;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>	
									<td>名称： </td>        
									<td colspan="3"><input type="text" id="proName" style="width: 100%;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>
									<td>规格：</td>
									<td colspan="3"><input type="text" id="proSpeci" style="width: 100%;" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>
									<td>序号：</td>
									<td><input type="text" id="placesNO" style="width:135px" class="easyui-validatebox" required="true" missingMessage="请填写此字段"/></td>
									<td>用量：</td>
									<td><input type="text" id="consumption" style="width:100px" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"/></td>
								</tr>
								<tr>
									<td>类别：</td>
									<td>
										<input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 135px;" required="true" missingMessage="请选择类别" />
									</td>
									<td>&nbsp;M|T：</td>
									<td>
										<select id="masterOrSlave" class="easyui-combobox" name="dept" style="width: 100px;" editable="false" panelHeight="auto">
											<option value="M" selected="selected">M</option>
											<option value="T">T</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>禁用：</td>
									<td align="left">
										<input type="radio" name="enabledFlag" value="0" />是 &nbsp; 
										<input type="radio" name="enabledFlag" value="1" checked="checked" />否
									</td>
								</tr>
								<tr>
									<td>备注：</td>
									<td colspan="3"><textarea id="remark" style="width: 100%;height: 75px;" rows="1"></textarea></td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
							<a id="dzlmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="dzlmanageSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
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