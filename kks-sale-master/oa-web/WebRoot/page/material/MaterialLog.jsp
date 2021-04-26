<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/material/MaterialLog.js?20170220"></script>
<script type="text/javascript" src="${ctx}/res/js/material/MaterialLog_choseMat.js"></script>
</script>
</head>
<body style="margin: 0">
	<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow: auto"
			title="出入库管理列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">
					<span>&nbsp;开始日期: <input type="text" class="form-search" id="outTimeStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>&nbsp;
					<span>&nbsp;物料类型:
						 <select id="s_materialType" class="easyui-combobox form-search" name="dept" style="width: 115px;" editable="false" panelHeight="auto">
								<option value="">选择物料类型</option>
								<option value="0">配件料</option>
								<option value="1">电子料</option>
						</select>
					</span>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市场型号: <input type="text" class="form-search" id="s_marketModel" style="width: 115px"></span>&nbsp;
					<span>&nbsp;物料名称: <input type="text" class="form-search" id="s_proName" style="width: 150px"></span>&nbsp;
				</div>
				<div style="margin-bottom:5px">
					<span>&nbsp;结束日期: <input type="text" class="form-search" id="outTimeEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>&nbsp;
					<span>出库|入库: 
						<select id="s_totalType" class="easyui-combobox form-search" name="dept" style="width: 115px;" editable="false" panelHeight="auto">
								<option value="">选择入库/出库</option>
								<option value="1">出库</option>
								<option value="0">入库</option>
						</select>
					</span>&nbsp;
					<span>出入库类型: <input type="text" class="form-search" id="s_type" style="width: 115px"></span>&nbsp;
					<span>&nbsp;物料编码: <input type="text" class="form-search" id="s_proNO" style="width: 150px"></span>&nbsp;
					<span>&nbsp;<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage(1)">查询</a> </span>				
				</div>
				
				<div>
                    <table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="materialLogAdd();">添加</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="materialLogUpdate();">修改</a></td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="materialLogDelete();">删除</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xzmbicon" plain="true" onclick="downLoadExcelTmp('MATERIAL/Excel-MTLG-TEMP.xlsx');">下载模板</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daoruicon" plain="true" onclick="ImportExcelDatas('materialLog/ImportDatas');">导入</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="ExportOrders();">导出</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>							
							</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			
			<table id="DataGrid1"  singleSelect="true" rownumbers="false"
				autoRowHeight="true" pagination="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar" >
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
						<th field="receivingParty" width="120px" align="center">收货方</th>
						<th field="clerk" width="100px" align="center">经手人</th>
						<th field="marketModel" width="100px" align="center">市场型号</th>
						<th field="platform" width="100px" align="center">平台</th>
						<th field="proNO" width="160px" align="center">物料编码</th>
						<th field="proName" width="200px" align="center">物料名称</th>
						<th field="proSpeci" width="300px" align="center">物料规格</th>
						<th field="placesNO" width="80px" align="center">位号</th>
						<th field="consumption" width="50px" align="center">用量</th>
						<th field="masterOrSlave" width="50px" align="center">M/T</th>
						<th field="loss" width="50px" align="center">损耗</th>
						<th field="tradePrice" width="80px" align="center">批发价(￥)</th>
						<th field="costPrice" width="80px" align="center">成本价(￥)</th>
						<th field="hourlyRates" width="80px" align="center">工时费(￥)</th>
						<th field="retailPrice" width="80px" align="center">零售价(￥)</th>
						<th field="manufacturerCode" width="80px" align="center">厂商代码</th>
						<th field="manufacturer" width="80px" align="center">厂商名称</th>
						<th field="remark" width="220" align="center" width="200px">备注</th> 
					</tr>
				</thead>
			</table>
		</div>

		<!-- 添加-START-->
		<div id="w" class="easyui-window" title="添加或修改出库入库信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility:hidden; width: 620px; height: 550px; padding: 5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<form id="mform">
						<table align="center">
							<tr>
								<td align="right">物料类型：</td>
								<td>
									<input type="hidden" id="orderNOi" />
									<select id="materialTypei" class="easyui-combobox" style="width: 180px;" editable="false" panelHeight="auto">
										<option value="0" selected="selected">配件料</option>
										<option value="1">电子料</option>
									</select>
								</td>
								<td align="right">入库/出库：</td>
								<td>
									<select id="totalTypei" class="easyui-combobox" name="dept" style="width: 180px;" editable="false" panelHeight="auto">
										<option value="1" disabled="disabled">出库</option>
										<option value="0">入库</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">出入库类型：</td>
								<td><input type="text" id="typei" style="width: 180px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" /></td>
								<td align="right">&nbsp;&nbsp;出入库日期：</td>
								<td><input type="text" id="outTimei" style="width: 180px" class="easyui-validatebox" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></td>
								<td><input type="hidden" id="idi" value="0" /></td>
							</tr>
							<tr>
								<td align="right">物料编码：</td>
								<td colspan="3"><input type="text" id="proNOi" style="width: 80%" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly">
									<label id="updateHide"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="log_chooseMaterial();">添加</a></label>
								</td>
							</tr>
							<tr>
								<td align="right">物料名称：</td>
								<td colspan="3"><input type="text" id="proNamei" style="width: 80%" disabled="disabled" /></td>
							</tr>
							<tr>
								<td align="right">物料规格：</td>
								<td colspan="3"><input type="text" id="proSpeci" style="width: 100%" disabled="disabled" /></td>
							</tr>
							<tr>
								<td align="right">市场型号：</td>
								<td><input type="text" id="marketModeli" style="width: 180px" disabled="disabled"/></td>
								<td align="right">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
								<td><input type="text" id="consumption" class="easyui-validatebox" validType="Number" style="width: 180px" disabled="disabled" /></td>
							</tr>
							<tr>
								<td align="right">零售价(￥)：</td>
								<td><input type="text" id="retailPrice" style="width: 180px;" validType="Amount" class="easyui-validatebox" disabled="disabled"></td>
								<td align="right">位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
								<td align="right"><input type="text" id="placesNOi" style="width: 180px" disabled="disabled"/></td>
							</tr>
							<tr>
								<td align="right">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</td>
								<td><input type="text" id="numberi" class="easyui-validatebox" validType="Number" style="width: 180px" required="true" missingMessage="请填写此字段" /></td>
								<td align="right">损&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;耗：</td>
								<td><input type="text" id="loss" style="width: 180px" /></td>
							</tr>
							<tr>
								<td align="right">M / T：</td>
								<td>
									<select id="masterOrSlave" class="easyui-combobox" name="dept" style="width: 180px;" editable="false" panelHeight="auto">
										<option value="M" selected="selected">M</option>
										<option value="T">T</option>
									</select>
								</td>
								<td align="right">平&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;台：</td>
								<td><input type="text" id="platformi" style="width: 180px" /></td>
							</tr>
							<tr>	
								<td align="right">仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
								<td><input type="text" id="depotNamei" style="width: 180px" class="easyui-validatebox" required="true" missingMessage="请填写此字段" /></td>
								<td align="right">工时费(￥)：</td>
								<td><input type="text" id="hourlyRates" style="width: 180px;" validType="Amount" class="easyui-validatebox pjlShow"></td>
							</tr>
							<tr class="pjlShow">
								<td align="right">批发价(￥)：</td>
								<td> <input type="text" id="tradePrice" style="width: 180px;" validType="Amount" class="easyui-validatebox pjlShow"></td>
								<td align="right">成本价(￥)：</td>
								<td> <input type="text" id="costPrice" style="width: 180px;" validType="Amount" class="easyui-validatebox pjlShow"></td>
							</tr>
							<tr>
								<td align="right">厂商代码：</td>
								<td><input type="text" id="manufacturerCode" style="width: 180px"/></td>
								<td align="right">厂商名称：</td>
								<td><input type="text" id="manufacturer" style="width: 180px"/></td>
							</tr>
							<tr>
								<td align="right">收&nbsp;货&nbsp;方：</td>
								<td><input type="text" id="receivingPartyi" class="easyui-validatebox" style="width: 180px" required="true" missingMessage="请填写此字段" /></td>
								<td align="right">经&nbsp;手&nbsp;人：</td>
								<td><input type="text" id="clerki" style="width: 180px" disabled="disabled" value="${USERSESSION.uName}"/></td>
							</tr>
							<tr>
								<td align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td colspan="3"><textarea id="remarki" style="width: 100%;" rows="3"></textarea></td>
							</tr>
						</table>
					</form>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="MaterialLog" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="MaterialLogSave()">保存</a>
					<a id="MaterialLogDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick=" windowCloseMaterialLog()">关闭</a>
				</div>
			</div>
		</div>
		
		<!-- 选择物料信息 -START-->
		<div id="log_material" class="easyui-window" title="选择物料信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			  style="width:760px;height:500px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region='center' >
					<div id="log_toolbar" style=" padding: 10px 15px;">
						<div style="margin-bottom:5px">			
							<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search5"  id="log_proNO" style="width:160px" /></span>&nbsp;&nbsp;
							<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search5"   id="log_proName" style="width:160px" /></span>&nbsp;&nbsp;
							<span>&nbsp;&nbsp;
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryLogMatListPage(1);">查询</a>
							</span>       
						</div>
					</div>
									
					<table id="log_MaterialDataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" autoRowHeight="true" 
						   striped="true" scrollbarSize="0" toolbar="#log_toolbar">
						<thead>
							<tr>
								<th checkbox="true" field="id"></th>
								<th field="proNO" width="160px" align="center">物料编码</th>
								<th field="proName" width="200px" align="center">物料名称</th>
								<th field="consumption" width="80px" align="center">用量</th>
								<th field="marketModel" width="80px" align="center">市场型号</th>
								<th field="retailPrice" width="80px" align="center">零售价(￥)</th>
								<th field="placesNO" width="80px" align="center">序号</th>
								<th field="proSpeci" width="330px" align="center">物料规格</th>
							</tr>
						</thead>
					</table>
				</div> 
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
					<a id="log_mat" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('log_material')">关闭</a>  
			    </div> 
			</div>
	     </div>
	    <!-- 选择物料信息 -END-->
		</form>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>