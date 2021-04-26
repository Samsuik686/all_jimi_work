<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/material/material.js?20170220"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript"
	src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout"
		style="overflow: hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow:auto" title="物料信息管理列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">				
					<span>&nbsp;物料类型: 
						<select id="s_materialType" class="easyui-combobox form-search" name="dept" style="width: 135px;" editable="false" panelHeight="auto">
							<option value="">选择物料类型</option>
							<option value="0">配件料</option>
							<option value="1">电子料</option>
						</select>
					</span>
					<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search" id="s_proNO" style="width: 135px"></span>&nbsp;
					<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search" id="s_proName" style="width: 150px"></span>&nbsp;
					<span>&nbsp;<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage(1)">查询</a> </span>				
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="showInfo(1);">出入库信息</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="updateInfo();">修改</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="ExportOrders();">导出</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-scwlxqdicon" plain="true" onclick="queryListPageTwo();">生成物料需求单</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>
							</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="materialType" width="100px" align="center">物料类型</th>
						<th field="proNO" width="180px" align="center">物料编码</th>
						<th field="proName" width="200px" align="center">物料名称</th>
						<th field="proSpeci" width="330px" align="center">规格</th>
						<th field="masterOrSlave" width="80px" align="center">M | T</th>
						<th field="lossType" width="60px" align="center">损耗</th>
						<th field="consumption" width="60px" align="center">用量</th>
						<th field="totalNum" width="100px" align="center">数量</th>
						<th field="retailPrice" width="80px" align="center">零售价（￥）</th>
					</tr>
				</thead>
			</table>
		</div>

		<!-- 物料信息管理 -START-->
		<div id="w_updateMaterial" class="easyui-window" title="修改物料信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			 style="visibility:hidden; width: 600px; height: 460px; padding: 5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false"
					style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<fieldset style="border: 1px solid #ccc;">
						<legend>
							<b>物料信息</b>
						</legend>
						<table>
							<tr><td><input type="hidden" id="id"></td></tr>
							<tr>
								<td align="right">物料类型：</td>
								<td>
									<select id="materialType" class="easyui-combobox" style="width: 200px;" disabled="disabled">
										<option value="0">配件料</option>
										<option value="1">电子料</option>
									</select>
								</td>
								<td>创建时间：</td>
								<td><input type="text" id="createTime" style="width: 180px;" class="easyui-datetimebox" disabled="disabled"></td>
							</tr>
							<tr>
								<td align="right">物料编码：</td>
								<td><input type="text" id="proNO" style="width: 180px;" disabled="disabled"> </td>
								<td>&nbsp;&nbsp;物料名称：</td>
								<td><input type="text" id="proName" style="width: 180px;" disabled="disabled"></td>
							</tr>
							<tr>
								<td align="right">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>
								<td colspan="3"><input type="text" id="proSpeci" style="width: 100%" disabled="disabled"></td>
							</tr>
							<tr>
								<td align="right">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
								<td><input type="text" id="consumption" style="width: 180px;" disabled="disabled"></td>
								<td>&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
								<td><input type="text" id="totalNum" class="easyui-validatebox" validType="Number" style="width: 180px" required="true" missingMessage="请填写此字段" ></td>
							</tr>
							<tr>
								<td align="right">&nbsp;损&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗 ：</td>
								<td><input type="text" id="lossType" style="width: 180px;"></td>
								<td align="right">&nbsp;&nbsp;&nbsp;M | T ：</td>
								<td>
									<!-- <select id="masterOrSlave" class="easyui-combobox" name="dept" style="width: 180px;" editable="false" panelHeight="auto">
										<option value="M" selected="selected">M</option>
										<option value="T">T</option>
									</select> -->
									<input type="text" id="masterOrSlave" style="width: 180px;" disabled="disabled">
								</td>
							</tr>
						</table>
					</fieldset>
					<br>
					<br>
					<form id="mform" action="">
						<fieldset style="border: 1px solid #ccc;">
							<legend>
								<b>价格管理</b>
							</legend>
							<table>
								<tr>	
									<td align="right">预警数值：</td>
									<td><input type="text" id="analyzeNum" style="width: 180px;" validType="Number" class="easyui-validatebox" required="true" missingMessage="请填写此字段"></td>
									<td>零售价（￥）:</td>
									<td><input type="text" id="retailPrice" style="width: 180px;" validType="Amount" class="easyui-validatebox"></td>
								</tr>
								<tr>
									<td align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="3"><textarea id="remark" style="resize：none; width: 100%;" rows="5" maxlength="250"></textarea></td>									
								</tr>
							</table>
						</fieldset>
					</form>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="InfoSave()">保存</a>
					 <a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_updateMaterial')">关闭</a>
				</div>
			</div>
		</div>
		<!-- 物料信息管理 -END-->

		<!-- 生成物料需求单  -START-->
		<div id="w__MaterialOrder" class="easyui-window" title="物料需求单" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			 style="visibility:hidden; width: 960px; height: 480px; padding: 5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<div id="OrderDataGrid_toolbar" style="padding: 2px 15px;">
						<table>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="ExportOrders('order');">导出</a>
							</td>
						</table>
					</div>
					<table id="OrderDataGrid" singleSelect="true" striped="true" pagination="true" rownumbers="false" autoRowHeight="true" fit="true" fitColumns="true" pageSize="10" toolbar="#OrderDataGrid_toolbar">
						<thead>
							<tr>
								<th field="materialType" width="120" align="center">物料类型</th>
								<th field="proNO" width="150" align="center">物料编码</th>
								<th field="proName" width="150" align="center">物料名称</th>
								<th field="proSpeci" width="250" align="center">物料规格</th>
								<th field="conNum" width="100" align="center">需求数量</th>
							</tr>
						</thead>
					</table>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w__MaterialOrder')">关闭</a>
				</div>
			</div>
		</div>
		<!-- 生成物料需求单 -END-->
		
		<!-- 查看物料出入库信息  -START-->
		<div id="w_logInfo" class="easyui-window" title="物料出入库信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility:hidden; width: 960px; height: 480px; padding: 5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<div id="showLog_toolbar" style="padding: 2px 15px;">
						<table>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="showInfo(1);">刷新</a>
							</td>
						</table>
					</div>
					<table id="LogInfoDataGrid" singleSelect="true" striped="true" pagination="true" rownumbers="false" autoRowHeight="true" fit="true" pageSize='10' toolbar="#showLog_toolbar">
						<thead>
							<tr>
								<th checkbox="true" field="id" align="center"></th>
								<th field="orderNO"  align="center" width="160px">单据编号</th>
								<th field="materialType" align="center" width="135px">物料类型(电子/组件)</th>
								<th field="totalType"  width="80px" align="center">入库/出库</th>
								<th field="type" width="150px" align="center" >出入库类型</th>
								<th field="depotName" width="150px" align="center">仓库</th>
								<th field="outTime" width="150px" align="center">出入库日期</th>
								<th field="number" width="80px" align="center">数量</th>
								<th field="marketModel" width="100px" align="center">市场型号</th>
								<th field="platform" width="100px" align="center">平台</th>
								<th field="proNO" width="160px" align="center">物料编码</th>
								<th field="proName" width="200px" align="center">物料名称</th>
								<th field="proSpeci" width="300px" align="center">物料规格</th>
								<th field="placesNO" width="80px" align="center">序号</th>
								<th field="consumption" width="50px" align="center">用量</th>
								<th field="masterOrSlave" width="50px" align="center">M/T</th>
								<th field="loss" width="50px" align="center">损耗</th>
								<th field="tradePrice" width="80px" align="center">批发价(￥)</th>
								<th field="costPrice" width="80px" align="center">成本价(￥)</th>
								<th field="hourlyRates" width="80px" align="center">工时费(￥)</th>
								<th field="retailPrice" width="80px" align="center">零售价(￥)</th>
								<th field="receivingParty" width="120px" align="center">收货方</th>
								<th field="clerk" width="100px" align="center">经手人</th>
								<th field="manufacturerCode" width="80px" align="center">厂商代码</th>
								<th field="manufacturer" width="80px" align="center">厂商名称</th>
								<th field="remark" width="220" align="center" width="200px">备注</th> 
							</tr>
						</thead>
					</table>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_logInfo')">关闭</a>
				</div>
			</div>
		</div>
		<!-- 查看物料出入库信息 -END-->
			
	</div>
</body>
</html>