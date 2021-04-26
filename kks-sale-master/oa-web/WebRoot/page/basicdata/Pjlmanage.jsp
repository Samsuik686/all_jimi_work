<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/pjlmanage.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="配件料管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>市场型号: <input type="text" class="form-search" id="searchByMarketModel" style="width: 135px" /> </span>&nbsp;&nbsp; 
					<span>名称: <input type="text" class="form-search" id="searchByProName" style="width: 150px" /> </span>&nbsp;&nbsp; 
					<span>备选型号: <input type="text" class="form-search" id="searchByOtherModel" style="width: 135px" /> </span>&nbsp;&nbsp; 
				</div>
				<div style="margin-bottom: 5px">
					<span>主板型号: <input type="text" class="form-search" id="searchByModel" style="width: 135px" /> </span>&nbsp;&nbsp; 
					<span>编码: <input type="text" class="form-search" id="searchByProNO" style="width: 150px" /> </span>&nbsp;&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="pjl_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="pjl_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="pjl_deleteInfo();">删除</a>
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
						<th checkbox="true" field="pjlId"></th>
						<th field="marketModel" width="100" align="left">市场型号</th>
						<th field="model" width="100" align="left">主板型号</th>
						<th field="proNO" width="145" align="left">编码</th>
						<th field="proName" width="180" align="left">名称</th>
						<th field="proSpeci" width="280" align="left">规格</th>
						<th field="consumption" width="80" align="center">用量</th>
						<th field="masterOrSlave" width="50px" align="center">M|T</th>
						<th field="retailPrice" width="90" align="center">零售价（￥）</th>
						<th field="hourFee" width="90" align="center">工时费（￥）</th>
						<th field="otherModel" width="180" align="center">备选型号</th>
						<th field="remark" width="220" align="center">备注</th>
					</tr>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position: fixed;">
			<div id="updateWindow" class="easyui-window" title="更新配件料信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 500px; height: 380px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="pjlId" />
									</td>
								</tr>
								<tr>
									<td>市场型号:</td>
									<td>
										<input type="text" id="marketModel" style="width: 135px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;主板型号:</td>
									<td>
										<input type="text" id="model" style="width: 135px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;编&nbsp;&nbsp;&nbsp;&nbsp;码:</td>
									<td>
										<input type="text" id="proNO" style="width: 135px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</td>
									<td>
										<input type="text" id="proName" style="width: 135px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</td>
									<td colspan="3">
										<input type="text" id="proSpeci" style="width: 100%;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;零&nbsp;售&nbsp;价:</td>
									<td>
										<input type="text" id="retailPrice" style="width: 135px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量:</td>
									<td>
										<input type="text" id="consumption" style="width: 135px" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;工&nbsp;时&nbsp;费:</td>
									<td>
										<input type="text" id="hourFee" style="width: 135px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;M&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;T:</td>
									<td>
										<select id="masterOrSlave" class="easyui-combobox" name="dept" style="width: 135px;" editable="false" panelHeight="auto">
											<option value="M" selected="selected">M</option>
											<option value="T">T</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>备选型号:</td>
									<td>
										<input type="text" id="otherModel" style="width: 135;" />
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
									<td colspan="3">
										<textarea id="remark" style="width: 100%; height: 75px;" rows="1"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="pjlmanage_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="pjlmanageSave()">保存</a> <a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>