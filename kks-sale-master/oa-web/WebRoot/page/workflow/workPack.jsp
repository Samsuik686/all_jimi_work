<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/pack.js?20171317"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/print/LodopFuncs.js"></script>
<script type="text/javascript" src="${ctx}/res/js/print/print.js"></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20170831"></script>

<style type="text/css">
/* 扫描IMEI */
strong{
	 font-family:"微软雅黑";
	 font-size: 16px;
	 color:#0066FF;
}
</style>

</head>
<body style="margin: 0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	
	<input type="hidden" id="packTreeDate"/>
	<input type="hidden" id="packTreeState"/>
	<input type="hidden" id="treeType"/><!-- 区分左侧树 -->
	<input type="hidden" id="packMonth"/><!-- 区分选中月或天 -->
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 180px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
			<!-- <hr width="160px;"/>
			<span><font color="red">**************************</font></spa>
			<hr width="160px;"/> -->
			<ul id="typeTreeTime1" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="装箱列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">	
					<!-- <input type="hidden" id="packState" value="0"/> -->			
					<span>&nbsp;开始日期: 
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px">
					</span>
					<span>&nbsp;送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmber" style="width: 135px"/></span>&nbsp;
					<span>
						&nbsp;处理状态:&nbsp;
						<select id="state" class="easyui-combobox form-search" style="width: 110px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="2" selected="selected">待装箱</option>
							<option value="1">已完成</option>
						</select>&nbsp;
					</span>
				</div>	
				<div style="margin-bottom: 5px;">
					<span>&nbsp;结束日期: 
						<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
					</span>
					<span>&nbsp;送修单位:&nbsp;<input type="text" class="form-search" id="searchCusname" style="width: 135px"/></span>					
					<span>&nbsp;
					 	<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
					</span>

				</div>
				<div style="margin-bottom: 5px;">	
					<div style="padding-top: 2px; display: none;" id="showTip"> 
					   <span class="l-btn-text icon-tip" style="padding-left: 20px; color: red;">您有<label style="color: green;">&nbsp;<span class="number">7</span>&nbsp;</label>条新数据已推送到,请刷新后及时处理数据 !</span>
					</div>				
				</div>
				<div>
					<table cellspacing="0" cellpadding="0" style="margin-top: 10px;">
						<tbody>
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pcckicon" plain="true" onclick="queryImeiSate();">查看</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-zxicon" plain="true" onclick="clickPage();">装箱</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-dykddicon" plain="true" onclick="InfoPrint();">打印</a></td>	
							<td><div class="datagrid-btn-separator"></div></td>	
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="matchImei();">匹配IMEI</a></td>	
							<td><div class="datagrid-btn-separator"></div></td>	
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="clickPage('remark');">装箱备注</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>	
							<td>
								<div>
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<strong>扫描IMEI|智能锁ID</strong> &nbsp;
										<input type="text" style="width:220px; height:28px; font-size:16px; font-weight:bold; color:#0066FF;" id="scanimei" onkeypress="if(event.keyCode==13) {scancData(event)}"/>
									</span>
								</div>
							</td>													
						</tr>
					 	</tbody>
					</table>
					
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fit="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="w_packId"></th>
						<th field="repairnNmber" width="100" align="center">送修批号</th>
						<th field="w_packState" width="80" align="center">进度</th>
						<th field="w_cusName" width="120" align="center">送修单位</th>
						<th field="w_phone" width="90" align="center">手机号</th>
						<th field="payedLogCost" width="70" align="center">付款状态</th>
						<th field="payDelivery"  width="80px"  align="center">客户寄货方式</th>
						<th field="w_customerReceipt"  width="80px"  align="center">客户收货方式</th>
						<th field="w_address" width="200" align="center">地址</th>
						<th field="w_expressCompany" width="80" align="center">快递公司</th>
						<th field="w_expressNO" width="100" align="center">快递单号</th>
						<th field="w_packingNO" width="160" align="center">装箱单号</th>
						<th field="w_packer" width="60" align="center">装箱人</th>
						<th field="w_shipper" width="200" align="center">发货方</th>
						<th field="w_packTime" width="130" align="center">装箱时间</th>
						<th field="w_sendPackTime" width="130" align="center">发送装箱时间</th>
						<th field="w_packRemark" width="150" align="center">装箱备注</th>
						<th field="w_price_Remark" width="150" align="center">报价备注</th>
						<th field="repairNumberRemark" width="150" align="center">批次备注</th>
						<th field="w_cusRemark" width="150" align="center">送修单位备注</th>
						<th field="w_telephone" width="90" align="center">座机号</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position:fixed;">
			<div id="lookManage" class="easyui-window" title="查看" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
				 style="width: 950px; height: 520px; visibility: hidden;">
				<div class="easyui-layout" fit="true">
				   <div region="center" id="dic-center" style="overflow:auto;">
					   	<div style="font-weight:400; font-size: 18px;text-align: center;color:#0066FF;">维修状态统计</div>
					   	<div>
						   	<table id="repairState_DataGrid" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" fitColumns="true" striped="true">
								<thead>
									<tr>
										<th field="state" width="250" align="center">状态</th>
										<th field="id" width="200" align="center">各维修状态数量</th>
									</tr>
								</thead>
							</table>
					   	</div>
					   <div id="batch_toolbar" style="height:30px; padding: 2px 15px;">
					   		<table width="100%;">
					   			<tr>
					   				<td width="65%;">
					   					<span>
								   			IMEI：<input type="text" id="searchImei"/>								   			
											状态：<select id="pack_search_State" style="width: 130px;"></select>											
								   			<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryImeiSate('search')" >查询</a> &nbsp;&nbsp; <font style="color: red;">双击数据修改IMEI</font>
							   			</span>
					   				</td>
					   				<td>
					   					<span id="infoCount" style="font-size: 14px;"></span>
					   				</td>
					   				<td>
					   					<span id="packCount" style="font-size: 14px;"></span>
					   				</td>
					   			</tr>
					   		</table>
					  	</div>
						  <div style="height: 330px;overflow:auto;overflow-x:hidden;">
							<table id="DataGrid2" singleSelect="true" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" toolbar="#batch_toolbar">
								<thead>
									<tr>
										<th field="lockInfo" hidden="true"></th>
										<th field="acceptanceTime" width="120" align="center">受理时间</th>
										<th field="w_marketModel" width="100" align="center">市场型号</th>
										<th field="imei" width="110" align="center">IMEI</th>
										<th field="lockId" width="80" align="center">智能锁ID</th>
										<th field="state" width="100" align="center">状态</th>
										<th field="sendPackTime" width="120" align="center">发送装箱时间</th>		
										<th field="machinaInPack" width="60" align="center"><font color="green;">已至装箱</font></th>									
										<th field="w_engineer" width="60" align="center">维修员</th>
										<th field="acceptRemark" width="120" align="center">受理备注</th>
										<th field="w_sjfjDesc" width="120" align="center">随机附件</th>
										<th field="isWarranty" width="60" align="center">保内|保外</th>										
										<th field="w_isRW" width="60" align="center">人为</th>	
										<th field="insideSoftModel" width="100" align="center">内部机型</th>
										<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
										<th field="backDate" width="100" align="center">预计返还日期</th>
										<th field="repairNumberRemark" width="150" align="center">批次备注</th>
										<th field="w_onePriceRemark" width="150" align="center">设备报价备注</th>
										<th field="remark" width="150" align="center">送修备注</th>
										<th field="w_repairRemark" width="150" align="center">维修备注</th>
										<th field="w_FinDesc" width="150" align="center">终检备注</th>
										<th field="testResult" width="200" align="center"><font color="green;">测试结果</font></th>	
									</tr>
								</thead>
							</table>
				 	    </div>
				   </div>
				</div>
			</div>
			
			<div id="packManage" class="easyui-window" title="装箱管理" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				 style="width: 750px; height: 460px; visibility: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<fieldset style="border:1px solid #ccc;">
							<legend><b>产品信息</b></legend> 
							<table align="center" width="100%" cellpadding="2">	
								<tr>
									<td>
										<input id="pack_id"  type="text"  style="display: none;"/>
										<input id="pack_w_packId"  type="text"  style="display: none;"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送修批号：</td>
									<td><input type="text" id="pack_repairnNmber" style="width: 200px;" maxlength="50" disabled="disabled"></td>
									<td>送修单位：</td>
									<td><input type="text" id="pack_w_cusName" style="width: 300px;" maxlength="255" disabled="disabled"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手&nbsp;机&nbsp;号：</td>
									<td><input type="text" id="pack_w_phone" style="width: 200px;" maxlength="50" disabled="disabled"></td>
									<td>客户地址：</td>
									<td><input type="text" id="pack_w_address" style="width: 300px;"  maxlength="255" disabled="disabled"/></td>
								</tr>
								<tr>
									<td>送修单位备注：</td>
									<td colspan="3"><textarea id="pack_w_cusRemark" style="width: 100%; height: 30px; resize:none;"  disabled="disabled"></textarea></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报价备注：</td>
									<td colspan="3"><textarea id="w_price_Remark" style="width: 100%; height: 30px; resize:none;"  disabled="disabled"></textarea></td>
								</tr>
							</table>
						</fieldset>	
						<br/><br/>
						<fieldset style="border:1px solid #ccc;">
							<legend><b>装箱信息</b></legend>
							<table align="center" width="100%" cellpadding="2">	
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;快递单号：</td>
									<td><input type="text" id="pack_w_expressNO" style="width: 200px;" maxlength="50" /></td>
									<td>快递公司：</td>
									<td>
										<select id="pack_w_expressCompany" style="width: 300px;" maxlength="255">  
									      <option value ="顺丰速运">顺丰速运</option>  
									      <option value ="百世汇通">百世汇通</option>
									      <option value ="优速">优速</option>
									      <option value ="自取">自取</option>
									      <option value ="跨越速运">跨越速运</option>
											<option value="申通快递">申通快递</option>
											<option value="德邦快递">德邦快递</option>
									    </select>  	
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;装箱单号：</td>
									<td><input type="text" id="pack_w_packingNO" style="width: 200px;" maxlength="50"></td> 
									<td>&nbsp;发&nbsp;货&nbsp;方：</td>
									<td><input type="text" id="pack_w_shipper" value="深圳市康凯斯信息技术有限公司" disabled="disabled"  style="width: 300px;" maxlength="255"/></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;装&nbsp;箱&nbsp;人：</td>
									<td><input type="text" id="pack_w_packer" style="width: 200px;" maxlength="50" disabled="disabled"></td>
									<td>装箱时间：</td>
									<td><input type="text" id="pack_w_packTime" style="width:150px;"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;装箱备注：</td>
									<td colspan="3"><textarea id="packRemark" style="width: 100%; height: 30px; resize:none;"></textarea> </td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="packUpdate()">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('packManage')">关闭</a>
					</div>
				</div>
			</div>
			
			<!-- 修改IMEI Start -->
			<div id="updateImei_w" class="easyui-window" title="修改IMEI" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width: 540px; height: 300px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="background: #fff; border: 1px solid #ccc;">
					<form id="upImeiform" action="">
						<table align="center" cellspacing="2">
							<tr>
								<td>旧IMEI：</td>
								<td><input type="text" id="oldImei" style="width: 150px;" disabled="disabled"/></td>								
							</tr>
							<tr>
								<td>新IMEI：</td>
								<td><input type="text" id="newImei" style="width: 150px;" maxlength="15"  class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"/></td>
							</tr>
							
						</table>
					</form>
				</div>
				<div region="south" border="false" 	style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="updateImei()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('updateImei_w')">关闭</a>
				</div>
			</div>
		</div>
		<!-- 修改IMEI End -->
		
		<!--  匹配IMEI START -->
		<div id="w_matchImei" class="easyui-window" title="匹配最后六位IMEI" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
			 	 style="width:700px;height:420px;visibility: hidden; ">
			 	<div class="easyui-layout"  fit="true"> 
					<div region="center" id="dic-center" style="overflow:auto;">
					   <div>
		   					<span>
					   			IMEI：<input type="text" id="matchImei" class="form-search3" placeholder="六位数字"/>								   			
					   			<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryImeilist();" >查询</a>
				   			</span>
					  	</div>
					  	<div>
							<table id="matchImei_DataGrid" singleSelect="true"  sortable="true" rownumbers="false" autoRowHeight="true" striped="true" fitColumns="true">
								<thead>
									<tr>
										<th field="acceptanceTime" width="130" align="center">受理时间</th>
										<th field="repairnNmber" width="110" align="center">送修批号</th>
										<th field="imei" width="120" align="center">IMEI</th>
										<th field="state" width="100" align="center">状态</th>
										<th field="sendPackTime" width="130" align="center">发送装箱时间</th>		
									</tr>
								</thead>
							</table>
					  	</div>
				   </div>
				</div>
			</div>
			<!--  匹配IMEI END -->
			
			<!-- 批次备注 start -->
			<div id="packRepairNumberManage" class="easyui-window" title="装箱备注" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
				 style="width: 550px; height: 400px; visibility: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<fieldset style="border:1px solid #ccc;">
							<legend><b>产品信息</b></legend> 
							<table align="center" width="100%" cellpadding="2">	
								<tr>
									<td>
										<input id="packRepairNumber_id"  type="text"  style="display: none;"/>
										<input id="packRepairNumber_w_packId"  type="text"  style="display: none;"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送修批号：</td>
									<td><input type="text" id="packRepairNumber_repairnNmber" style="width: 130px;" maxlength="50" disabled="disabled"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手&nbsp;机&nbsp;号：</td>
									<td><input type="text" id="packRepairNumber_w_phone" style="width: 130px;" maxlength="50" disabled="disabled"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;送修单位：</td>
									<td><input type="text" id="packRepairNumber_w_cusName" style="width: 300px;" maxlength="255" disabled="disabled"></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户地址：</td>
									<td colspan="3"><textarea id="packRepairNumber_w_address" style="width: 380px; height: 30px; resize:none;"  disabled="disabled"></textarea></td>
								</tr>
								<tr>
									<td>送修单位备注：</td>
									<td colspan="3"><textarea id="packRepairNumber_w_cusRemark" style="width: 380px; height: 30px; resize:none;"  disabled="disabled"></textarea></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报价备注：</td>
									<td colspan="3"><textarea id="packRepairNumber_price_Remark" style="width: 380px; height: 30px; resize:none;"  disabled="disabled"></textarea></td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;装箱备注：</td>
									<td colspan="3"><textarea id="packRepairNumberRemark" style="width: 380px; height: 30px; resize:none;"></textarea> </td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="updatePackRemark()">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('packRepairNumberManage')">关闭</a>
					</div>
				</div>
			</div>
			<!-- 批次备注 end -->
		</div>
	</div>
</body>
</html>