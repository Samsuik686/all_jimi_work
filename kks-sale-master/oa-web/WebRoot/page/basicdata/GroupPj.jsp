<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/groupPj.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx }/res/js/workflow/price_pj.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="销售配件料组合管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>市场型号: <input type="text" class="form-search" id="searchByMarketModel" style="width: 150px" /> </span>&nbsp; 
					<span>名称: <input type="text" class="form-search" id="searchByGroupName" style="width: 150px" /> </span>&nbsp; 
					<span>总价格: <input type="text"  class="easyui-validatebox form-search" validType="Amount" id="searchByGroupPrice" style="width: 135px" /> </span>&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="groupPj_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="groupPj_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="groupPj_deleteInfo();">删除</a>
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
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshInfo();">刷新</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" fitColumns="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="groupPjId"></th>
						<th field="groupName" width="100" align="left">组合配件名称</th>
						<th field="groupPrice" width="100" align="center">总价格</th>
						<th field="marketModel" width="100" align="center">市场型号</th>
						<th field="remark" width="220" align="left">备注</th>
					</tr>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position: fixed;">
			<div id="updateWindow" class="easyui-window" title="更新销售配件料组合信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 350px; height: 300px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr>
									<td>
										<input type="hidden" id="groupPjId" />
										<input type="hidden" id="groupPj_details_ids"><!-- 新增时保存选中配件料id -->
										<input type="hidden" id="details_ids"><!-- 详情id -->
										<input type="hidden" id="importFalg"><!-- 导入标识 -->
									</td>
								</tr>
								<tr>
									<td>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
									<td>
										<input type="text" id="groupName" style="width: 220px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;总&nbsp;价&nbsp;格：</td>
									<td>
										<input type="text" id="groupPrice" style="width: 135px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" readonly="readonly"/>
										<label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doChoosePJL();">选择</a></label>
									</td>
								</tr>
								<tr>
									<td>市场型号：</td>
									<td>
										<input type="text" id="marketModel" style="width: 220px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="3">
										<textarea id="remark" style="width: 100%; height: 75px;" rows="1"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="groupPj_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="groupPjSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 销售配件料组合详情 - Start-->
		<div id="details_w" class="easyui-window" title="销售配件料组合详情" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="style="overflow: auto;padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:430px;">
						<div id="detailsToolbar" style=" padding: 5px 5px;">
							<div>
			                    <table cellspacing="0" cellpadding="0">
									<tbody>
										<tr>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="details_add();">增加</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="details_edit();">修改</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="details_del();">删除</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListDetails();">刷新</a></td>							
										</tr>
								 	</tbody>
								</table>
							</div>
						</div>
						<table id="details_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" 
							   fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#detailsToolbar">
							<thead>
								<tr>
									<th checkbox="true" field="id"></th>
									<th field="proNO" width="150" align="center">编码</th>
									<th field="proName" width="110" align="center">名称</th>
									<th field="retailPrice" width="80" align="center">单价（￥）</th>
									<th field="proNumber" width="80" align="center">数量</th>
									<th field="masterOrSlave" width="50px" align="center">M|T</th>
									<th field="proSpeci" width="220" align="center">规格</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<span style="margin-right:650px;">共<label style="color: green;">&nbsp;<span id="detailsCount">0</span>&nbsp;</label>条数据</span>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeDetails_w();">关闭</a>
			    </div>
			</div>
	    </div>
	    <!--销售配件料组合详情  - End-->
	    
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
											&nbsp;市场型号:&nbsp;<input type="text" class="form-search1 easyui-validatebox"  id="choosepj_marketModel" style="width:100px" required="true" missingMessage="请填写此字段" placeholder="精确查询">
											&nbsp;编码:&nbsp;<input type="text" class="form-search1"  id="choosepj_proNO" style="width:135px">
											&nbsp;名称:&nbsp;<input type="text" class="form-search1"  id="choosepj_proName" style="width:135px">						
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
								<th field="marketModel" width="100" align="center">市场型号</th>
								<th field="model" width="90" align="center">主板型号</th>
								<th field="proNO" width="150" align="center">编码</th>
								<th field="proName" width="120" align="center">名称</th>
								<th field="retailPrice" width="60" align="center">单价（￥）</th>
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
	     
	      <!-- 修改配件数量 -->
		 <div id="editPj_w" class="easyui-window" title="修改配件数量" modal="true" closed="true" style="width:350px;height:360px;visibility: hidden;" 
			 minimizable="false" maximizable="false" collapsible="false" >
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<form id="editPj_form">
						<table align="left" cellpadding="2" style="padding-left: 25px;">
							<tr>
								<td><input type="hidden" id="editPj_id"></td>
								<td><input type="hidden" id="editPj_groupId"></td>
							</tr>
							<tr class="singleSelect_show">
								<td align="right">编码：</td>
								<td><input type="text" id="editPj_proNO" width="200" disabled="disabled"/></td>
							</tr>
							<tr class="singleSelect_show">
								<td align="right">名称：</td>
								<td><input type="text" id="editPj_proName" width="200" disabled="disabled"/></td>
							</tr>	
							<tr class="singleSelect_show">
								<td align="right">单价：</td>
								<td><input type="text" id="editPj_retailPrice" width="200" disabled="disabled"/></td>
							</tr>
							<tr>
								<td align="right">数量：</td>
								<td><input type="text" id="editPj_proNumber" width="200" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"/></td>
							</tr>
						</table>
					</form>					
			 	</div>
			 	<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="editPj_Update" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveEditPj()">保存</a>
					<a  class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="editPj_Close();">关闭</a>
				</div>
			</div>
		 </div>
	</div>
</body>
</html>