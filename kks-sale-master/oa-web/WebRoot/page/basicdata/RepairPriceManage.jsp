<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/RepairPriceManage.js?20170118"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/repairprice.pjl/pjl.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/repairprice.pjl/priceGroupPj.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<style type="text/css">
/* 导入的窗口右上角的关闭图表全局不显示，选择配件料的关闭图表显示 */
#selected-type-box-PJL .panel-tool-close{
	display:inline;
} 
</style>
</head>
<body style="margin: 0">
	<input type="hidden" id="LX_Type" value="BASE_WXBJ" />
	<input type="hidden" id="gIds" />
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" scroll="no" fit="true">
		<div region="west" id="dic-west" style="width: 200px;" title="维修报价类别">
			<div class="easyui-panel" style="background-color: #E0ECFF">
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-addicon" onclick="treesAddwindow()">增加</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-delecticon" onclick="treesDelete()">删除</a>
				<a class="easyui-linkbutton" plain="true" href="javascript:void(0)" icon="icon-xgicon" onclick="treesUpdateWindow()">修改</a>
			</div>
			<ul id="typeTree" class="easyui-tree">

			</ul>
		</div>
		
		<div region="center" id="dic-center" style="overflow: auto" title="维修组合报价管理列表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span>编码: <input type="text" class="form-search" id="searchNumber" style="width: 150px" /> </span>&nbsp;&nbsp; 
					<span>主板型号: <input type="text" class="form-search" id="searchBymodel" style="width: 150px" placeholder="精确查询"/> </span>&nbsp;&nbsp; 
					<span>
						启用状态:
						<select id="useState"  style="width: 120px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0" >正常</option>
							<option value="1">禁用</option>
						</select>
					</span>&nbsp;&nbsp;
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="repairPrice_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="repairPrice_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="repairPrice_deleteInfo();">删除</a>
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
						<th checkbox="true" field="rid"></th>
						<th field="repairType" width="150" align="left">报价类别</th>
						<th field="amount" width="280" align="left">报价描述</th>
						<th field="price" width="110" align="center">报价金额(￥)</th>
						<th field="hourFee" width="90" align="center">工时费(￥)</th>
						<th field="priceNumber" width="80" align="center">编码</th>
						<th field="idCode" width="80" align="center">识别码</th>
						<th field="model" width="100" align="center">主板型号</th>
						<th field="priceGroupDesc" width="260" align="center">报价配件料组合</th>
						<th field="pjlDesc" width="260" align="center">关联配件料</th>
						<th field="useState" width="80" align="center">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新维修报价信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  
				style="visibility:hidden; width: 520px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="rform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="rid_hidden" style="width: 150px;" maxlength="11">
									</td>
								</tr>
								<tr>
									<td align="right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
									<td>
										<input id="type" class="easyui-combobox" editable="false" panelHeight="auto" data-options="valueField:'id',textField:'text'" style="width: 150px;" required="true" missingMessage="请选择类别" />
									</td>
								</tr>	
								<tr>
									<td align="right">编  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
									<td>
										<input type="text" id="priceNumber" style="width: 220px;" maxlength="255" class="easyui-validatebox"  required="true" missingMessage="请填写编码"/>
									</td>
								</tr>
								<tr>
									<td align="right">识&nbsp;别&nbsp;码：</td>
									<td>
										<input type="text" id="idCode" style="width: 220px;" maxlength="255" class="easyui-validatebox"  required="true" missingMessage="请填写编码"/>
									</td>
								</tr>
								<tr>
									<td align="right">报价金额：</td>
									<td>
										<input type="text" id="price" style="width: 220px;" maxlength="255" class="easyui-validatebox" validType="Amount"  required="true" missingMessage="请填写金额"/>
									</td>
								</tr>
								<tr>
									<td align="right">工&nbsp;时&nbsp;费：</td>
									<td>
										<input type="text" id="hourFee" style="width: 220px;" maxlength="255" class="easyui-validatebox" validType="Amount"  required="true" missingMessage="请填写金额"/>
									</td>
								</tr>
								<tr>
									<td align="right">主板型号：</td>
									<td>
										<input type="text" id="model" style="width: 220px;" maxlength="255" class="easyui-validatebox"/>
									</td>
								</tr>
								<tr>
									<td align="right">配件料组合：</td>
									<td colspan="7">
								     	<input type="hidden" id="priceGroupId">
										<input type="text" id="priceGroupDesc" style="width: 300px;" readonly="readonly"/>
										<label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doinsertPGPJ();">选择</a></label>
									</td>
								</tr>
								<tr>
									<td align="right">关联配件料：</td>
									<td colspan="7">
								     	<input type="hidden" id="pjlId">
										<input type="text" id="pjlDesc" style="width: 300px;" readonly="readonly"/>
										<label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doinsertPJL();">选择</a></label>
									</td>
								</tr>
								<tr>
									<td align="right">启用状态：</td>
									<td align="left">
										<input type="radio" name="useState" value="0" checked="checked"/>正常
										<input type="radio" name="useState" value="1"  />禁用										
									</td>
								</tr>																							
								<tr>
									<td align="right">报价描述：</td>
									<td colspan="7">
										<textarea id="amount" style="width: 100%; height: 80px;" maxlength="290" class="easyui-validatebox" required="true" missingMessage="请填写报价描述"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="repairPrice_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="repairPriceSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
			
			<!-- 添加维修报价类别-START-->
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
			
			<!-- 关联配件料- Start-->
			<div id="pjl_w" class="easyui-window" title="关联配件料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				 style="width:850px;height:520px; visibility: hidden; ">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;" title="[详细信息]  注：双击选择">
						<div style="height:280px;">
							<div id="pjlToolbar" style=" padding: 10px 15px;">
								<div style="margin-bottom:5px">	
									<div style="margin-bottom: 5px">
										<input type="hidden" id="defaultModel">
										<span>编码: <input type="text" class="form-search1" id="searchByProNO" style="width: 135px" /> </span>&nbsp;&nbsp; 
										<span>市场型号: <input type="text" class="form-search1" id="searchByMarketModel" style="width: 135px" /> </span>&nbsp;&nbsp; 
										<span>名称: <input type="text" class="form-search1" id="searchByProName" style="width: 150px" /> </span>&nbsp;&nbsp; 
										<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPagePJL();">查询</a>
									</div>
									<div>
					                    <table cellspacing="0" cellpadding="0">
											<tbody>
												<tr>
													<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPagePJL;">刷新</a></td>							
												</tr>
										 	</tbody>
										</table>
									</div>
								</div>
							</div>
							<table id="pjl_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" pagination="true" scrollbarSize="0" toolbar="#pjlToolbar">
								<thead>
									<tr>
										<th field="id" hidden="true"></th>
										<th field="marketModel" width="80" align="center">市场型号</th>
										<th field="model" width="80" align="center">主板型号</th>
										<th field="proNO" width="120" align="center">编码</th>
										<th field="proName" width="150" align="center">名称</th>
										<th field="retailPrice" width="80" align="center">零售价(￥)</th>
										<th field="consumption" width="50" align="center">用量</th>
										<th field="masterOrSlave" width="50" align="center">M|T</th>
										<th field="proSpeci" width="200" align="center">规格</th>
										<th field="pjlId" width="50" align="center">关联id</th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="selected-type" style="height: 130px;">
							<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选配件料</div>
							<div class="selected-type-box" id ="selected-type-box-PJL">
						</div>						
					</div>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="pjlSave()">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closepjl_w();">关闭</a>
				    </div>
				</div>
		    </div>
		    <!--关联配件料  - End-->
		    
		    <!-- 报价配件料组合 start -->
		     <div id="pgpj_w" class="easyui-window" title="选择报价配件料组合" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:700px;height:520px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;" title="[详细信息]  注：双击选择">
					<div style="height:280px;">
						<div id="datagrid-PriceGroupPjToolbar">
							<div style="margin-bottom:5px">	
								<span>名称:&nbsp;<input type="text" class="form-search3"  id="searchByGroupName" style="width:150px"></span>&nbsp;
								<span> 
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPagePGPJ('');">查询</a>
								</span>
							</div>
						</div>
						<table id="pgpj_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" pagination="true" fitColumns="true" striped="true" pageSize="20" toolbar="#datagrid-PriceGroupPjToolbar">
							<thead>
								<tr>
									<th checkbox="true" field="groupPjId"></th>
									<th field="model" width="110" align="center">主板型号</th>
									<th field="groupName" width="110" align="center">名称</th>
									<th field="pjPrice" width="80" align="center">配件费（￥）</th>
									<th field="groupPrice" width="80" align="center">总价格（￥）</th>
									<th field="remark" width="150" align="center">备注</th>
								</tr>
							</thead>				
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选报价配件料组合</div>
						<div class="selected-type-box" id ="selected-type-box-PGPJ"> </div>
					</div>
				</div>
				<div region="south" border="true" style="text-align:right;height:30px;line-height:30px;">
					<div>
						<a id="savePriceGroupPj" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveChoose_pgpj();">保存</a>
						<a id="closePriceGroupPj" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('pgpj_w')">关闭</a>
					</div>
			    </div>
			</div>
	      </div>
	      <!-- 报价配件料组合 end -->
		</div>
	</div>
</body>

</html>