<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/priceGroupPj.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx }/res/js/workflow/price_pj.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
<style type="text/css">
.panel-tool .panel-tool-close{
 	display: none;
 }
</style>
</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_WXBJ" />
	<input type="hidden" id="gIds" />
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="报价配件料组合管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>主板型号: <input type="text" class="form-search" id="searchByModel" style="width: 150px" /> </span>&nbsp; 
					<span>名称: <input type="text" class="form-search" id="searchByGroupName" style="width: 150px" /> </span>&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="priceGroupPj_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="priceGroupPj_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="priceGroupPj_deleteInfo();">删除</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="bathch_addInfo();">批量增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="groupPjId"></th>
						<th field="pjlDesc" hidden="true"></th>
						<th field="model" width="100" align="center">主板型号</th>
						<th field="groupName" width="180" align="left">组合名称</th>
						<th field="pjlPrice" width="80" align="center">配件费（￥）</th>
						<th field="remark" width="150" align="left">备注</th>
					</tr>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position: fixed;">
			<div id="updateWindow" class="easyui-window" title="更新报价配件料组合信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			style="visibility: hidden; width: 650px; height: 550px; padding: 2px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 2px; background: #fff; border: 1px solid #ccc; overflow: auto">
						<fieldset style="border:1px solid #ccc;">
							<legend><b>组合信息</b></legend> 
							<form id="mform" action="">
								<table align="center">
									<tr>
										<td>
											<input type="hidden" id="groupPjId" />
											<input type="hidden" id="pjlDesc" />
										</td>
									</tr>
									<tr>
										<td>&nbsp;主板型号：</td>
										<td>
											<input type="text" id="model" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly" />
										</td>
										<td>&nbsp;配&nbsp;件&nbsp;费：</td>
										<td>
											<input type="text" id="pjlPrice" style="width: 135px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" readonly="readonly"/>
										</td>
									</tr>
									<tr>
										<td>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
										<td>
											<input type="text" id="groupName" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
										</td>
									</tr>
									<tr>
										<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
										<td colspan="3">
											<textarea id="remark" style="width: 450px; height: 35px;" rows="1"></textarea>
										</td>
									</tr>
								</table>
							</form>
						</fieldset>
						<fieldset style="border:1px solid #ccc; height: 315px">
							<legend><b>配件料信息</b></legend> 
							<div id="delPjl_toolbar" style="padding: 2px 2px;">
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="addPjl();">增加</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="delPjl();">删除</a>
							</div>
							<table id="details_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true"
							  	  striped="true" scrollbarSize="0" toolbar="#delPjl_toolbar">
								<thead>
									<tr>
										<th checkbox="true" field="id"></th>
										<th field="pjlId" hidden="true"></th>
										<th field="model" width="100" align="center">主板型号</th>
										<th field="marketModel" width="130" align="center">市场型号</th>
										<th field="proNO" width="150" align="center">编码</th>
										<th field="proName" width="110" align="center">名称</th>
										<th field="retailPrice" width="80" align="center">单价（￥）</th>
										<th field="consumption" width="80" align="center">用量</th>
										<th field="masterOrSlave" width="50px" align="center">M|T</th>
										<th field="proSpeci" width="220" align="center">规格</th>
									</tr>
								</thead>
							</table>
						</fieldset>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="priceGroupPj_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="priceGroupPjSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
	    
		<!-- 增加配件料-START--> 
		<div id="choosepj_w" class="easyui-window" title="增加配件料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:650px;height:450px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" style="overflow:auto">
					<div id="choosepjToolbar">
						<div style="margin-bottom:5px">	
							<form id="searchPjlForm">
								<table>
									<tr>
										<td>
											&nbsp;主板型号:&nbsp;<input type="text" class="form-search1 easyui-validatebox"  id="choosepj_model" style="width:100px" required="true" missingMessage="请填写此字段" placeholder="精确查询">
											&nbsp;编码:&nbsp;<input type="text" class="form-search1"  id="choosepj_proNO" style="width:110px">
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;市场型号:&nbsp;<input type="text" class="form-search1"  id="choosepj_marketModel" style="width:100px">
											&nbsp;名称:&nbsp;<input type="text" class="form-search1"  id="choosepj_proName" style="width:110px">						
											<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPagePj();">查询</a>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<table id="choosepj_DataGrid" fit="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" striped="true" pageSize="30" toolbar="#choosepjToolbar">
							<thead>
							<tr>
								<th checkbox="true" field="pjlId"></th>
								<th field="model" width="80" align="center">主板型号</th>
								<th field="marketModel" width="100" align="center">市场型号</th>
								<th field="proNO" width="130" align="center">编码</th>
								<th field="proName" width="120" align="center">名称</th>
								<th field="retailPrice" width="60" align="center">单价（￥）</th>
								<th field="hourFee" width="68" align="center">工时费（￥）</th>
								<th field="consumption" width="50" align="center">用量</th>
								<th field="masterOrSlave" width="50" align="center">M|T</th>
								<th field="proSpeci" width="280" align="center">规格</th>
							</tr>
						</thead>				
					</table>
				</div>
				<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
					<div>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveChoosepj();">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('choosepj_w')">关闭</a>
					</div>
			    </div>
			</div>
	     </div>
	     <!-- 增加配件料-END-->
	     
	     <!-- 批量增加 start -->
	    <div id="batchAdd_w" class="easyui-window" title="批量增加" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:450px;height:300px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" style="overflow:auto">
					<div id="choosepjToolbar">
						<div style="margin-bottom:5px">	
							<form id="searchPjlForm" >
								<table align="center">
									<tr>
										<td>
											&nbsp;主板型号:&nbsp;
											<input type="text" class="form-search2"  id="batch_model" style="width:150px">
										</td>
									</tr>
									<tr>
										<td>
										&nbsp;类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:&nbsp;
											<input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;关&nbsp;&nbsp;键&nbsp;&nbsp;字:&nbsp;
											<input type="text" class="form-search2"  id="batch_key" style="width:280px">						
										</td>
									</tr>
									<tr>
										<td align="center"><a id="" name="ckek" class="easyui-linkbutton" iconCls="icon-addicon" href="javascript:void(0)" onclick="saveAll();">生成</a></td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
					<div>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('batchAdd_w')">关闭</a>
					</div>
			    </div>
			</div>
	     </div>
	    <!-- 批量增加 end -->
	</div>
</body>
</html>