<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/sjfjmanage.js?20170220"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>

</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_SJFJ" />
	<input type="hidden" id="gIds" />
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" fit="true">
		<div region="west" id="dic-west" style="width: 200px; overflow: auto;" title="随机附件类别">
			<div class="easyui-panel" style="background-color: #E0ECFF">
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="treesAddwindow()">增加</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="treesDelete()">删除</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treesUpdateWindow()">修改</a>
			</div>
			<ul id="typeTree" class="easyui-tree" animate="true" dnd="false">
			</ul>

		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="随机附件管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>&nbsp;品牌: <input type="text" class="form-search" id="brand" style="width: 200px" /></span>&nbsp; &nbsp; &nbsp; &nbsp; 
					<span>&nbsp;名称: <input type="text" class="form-search"  id="name" style="width: 200px" /></span>&nbsp; &nbsp; &nbsp; &nbsp; 
					<span>&nbsp; &nbsp; <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a> </span>
					</tr>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="sjfj_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="sjfj_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="sjfj_deleteInfo();">删除</a>
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
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="eid"></th>
						<th field="category" width="220" align="center">随机类别</th>
						<th field="brand" width="220" align="center">品牌</th>
						<th field="name" width="220" align="center">名称</th>
						<th field="color" width="220" align="center">颜色</th>
						<th field="number" width="220" align="center">数量</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<!-- 添加-START-->
			<from id="addSjfjmanage">
			<div id="w" class="easyui-window" title="更新随机附件信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 450px; height: 280px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 5px; background: #fff; border: 1px solid #ccc;">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="eidi" value="0" />
									</td>
								</tr>
								<tr>
									<td>随机类别：</td>
									<td>
										<input type="text" id="type" style="width: 150px" selected="selected" class="easyui-combobox" data-options="valueField:'id',textField:'text'" editable="false" panelHeight="auto" required="true" missingMessage="请选择类别" />
									</td>
								</tr>
								<tr>
									<td>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</td>
									<td>
										<input type="text" id="brandi" style="width: 220px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
									<td>
										<input type="text" id="namei" style="width: 220px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</td>
									<td>
										<input type="text" id="colori" style="width: 220px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
									<td>
										<input type="text" id="numberi" style="width: 220px" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="SjfjmanageSave()">保存</a> <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCloseEncl()">关闭</a>
					</div>
				</div>
			</div>
			</from>
			<!-- 添加-END-->
	
	
			<!-- 添加-START-->
	
			<div id="wg" class="easyui-window" title="分类增加窗口" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 350px; height: 250px; padding: 5px;">
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
						<a id="treeUpdate" class="easyui-linkbutton" iconCls="icon-redo" href="javascript:void(0)" onclick="treesAddOrUpdate()">保存</a> <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="tree_windowClose()">关闭</a>
					</div>
				</div>
			</div>
			<!-- 添加-END-->
		</div>
	</div>
</body>
</html>