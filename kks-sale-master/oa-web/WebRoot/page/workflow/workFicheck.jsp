<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/ficheck/ficheck.js?20170831"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
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
<body style="margin:0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/> 
	<div class="easyui-layout" style="overflow: hidden; width:100%;"  fit="true">
		<div region="west" id="dic-west" style="width: 180px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
	    <!-- 列表 -Start-->
		<div region="center" id="dic-center" style="overflow: auto" title="终检列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">	
					<span>设备IMEI:&nbsp;<input type="text" class="form-search" id="imei" style="width:130px"></span>
					<span>
						&nbsp;开始日期: 
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span>
					<span>&nbsp;送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmber" style="width:135px"></span>
				</div>	
				<div style="margin-bottom: 5px;">
					<span>智能锁ID:&nbsp;<input type="text" class="form-search" id="lockId" style="width:130px"></span>
					<span>
						&nbsp;结束日期: 
						<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span>
					<span>
						&nbsp;处理状态:
						<select id="state" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="2" selected="selected">待处理</option>
							<option value="1">已完成</option>
						</select>
					</span>
					<span>
						<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a>
					</span>
				</div>	
				<div style="margin-bottom: 5px;">
					<div style="padding-top: 2px; display: none;" id="showTip"> 
					   <span class="l-btn-text icon-tip" style="padding-left: 20px; color: red;">您有<label style="color: green;">&nbsp;<span class="number">7</span>&nbsp;</label>条新数据已推送到,请刷新后及时处理数据 !</span>
					</div>
				</div>
				<div>
                    <table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pcckicon" plain="true" onclick="showRepairState();">查看</a></td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-zjicon" plain="true" onclick="doFinCheck();">终检</a> </td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-fszxicon" plain="true" onclick="sendEnchase();">发送装箱</a> </td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendWorkTestView();">发送至测试</a></td>	
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-fszxicon" plain="true" onclick="sendRepairAgain();">不合格发送维修</a> </td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-return" plain="true" onclick="sendBackToRepair();">待终检返回维修</a> </td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="7">
									<table style="width: 100%;">
										<tr>
											<td ><span style="font-size: 14px;">【共<label style="color: green;">&nbsp;<span id="acc_number" style="font-weight: bold; font-size: 14px;">0</span>&nbsp;</label>条记录】</span></td>
											<td ><span style="font-size: 14px; ">【放弃报价共<label style="color: green;">&nbsp;<span id="giveupCount" style="font-weight: bold; font-size: 14px;">0</span>&nbsp;</label>条记录】</span></td>
											<td>
												<div>
													<span><strong>扫描IMEI|智能锁ID</strong> &nbsp;<input type="text" style="width:220px; height:28Px; font-size:16px; font-weight:bold; color:#0066FF;" id="scanimei" onkeypress="if(event.keyCode==13) {scancData(event)}"/></span>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1"  singleSelect="true" fit="true" rownumbers="false" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="lockInfo" hidden="true"></th>
						<th field="acceptanceTime" width="130"  align="center">受理时间</th>
						<th field="lockId" width="80" align="center">智能锁ID</th>	
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="w_sendFinTime" width="130"  align="center">发送终检时间</th>					
						<th field="w_FinispassDesc" width="80"  align="center">进度</th>
						<th field="w_engineer" width="80" align="center">维修员</th>
						<th field="w_cusName" width="150"  align="center">客户名称</th>
						<th field="w_FinChecker" width="80" align="center">终检员</th>
						<th field="w_payTime" width="130" align="center">客户付款日期</th>
						<th field="w_model"  width="160" align="center">主板型号|市场型号</th>
						<th field="w_cjgzDesc"  width="160" align="center">初检故障</th>
						<th field="w_zzgzDesc"    width="160"  align="center">最终故障</th>
						<th field="repairnNmber" width="100"  align="center">送修批号</th>
						<th field="insideSoftModel" width="100" align="center">内部机型</th>
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>		
						<th field="isWarranty" width="80" align="center">保内|保外</th>
						<th field="returnTimes"  width="80"   align="center">返修次数</th>
						<th field="backDate" width="80" align="center">预计返还日期</th>
						<th field="salesTime"  width="130"   align="center">出货日期</th>
						<th field="acceptRemark" width="150" align="center">受理备注</th>
						<th field="remark" width="150" align="center">送修备注</th>
						<th field="repairNumberRemark" width="150" align="center">批次备注</th>
						<th field="w_repairRemark" width="150" align="center">维修备注</th>
						<th field="w_priceRemark" width="150" align="center">批次报价备注</th>
						<th field="testPerson" width="80" align="center">测试人员</th>
						<th field="testResult" width="150"  align="center"><font color="green;">测试结果</font></th>	
					</tr> 
				</thead>
			</table>
		</div>
		<!-- 列表 -End-->
		
		<div style="position:fixed;">
			<div id="showAll" class="easyui-window" title="查看" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
					 style="width: 950px; height: 520px; visibility: hidden;">
					 <div id="batch_toolbar" style="height:30px; padding: 2px 15px;">
					<div>
						<span>
							状态：<select id="ficheck_search_State" style="width: 130px;"></select>
						</span>
						<span>
							<a  class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="showRepairState('search')">查询</a>
						</span>
						<span>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="showRepairState('search');">刷新</a>
						</span>
					</div>
				</div>
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
					   	<div align="center">
						   	<div>
							   	<span style="font-weight:400; font-size: 18px;text-align: center;color:#0066FF;margin-top: 10px;">批次详情 </span> &nbsp;&nbsp;&nbsp;
							   	<span id="infoCount" style="font-size: 14px;"></span>
						   	</div>
					   	</div>
					    <div style="height: 330px;overflow:auto;overflow-x:hidden;">
							<table id="DataGrid2" singleSelect="true" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" toolbar="#batch_toolbar">
								<thead>
									<tr>
										<th field="acceptanceTime" width="125" align="center">受理时间</th>
										<th field="repairnNmber" width="100" align="center">送修批号</th>
										<th field="imei" width="120" align="center">IMEI</th>
										<th field="state" width="110" align="center">状态</th>
										<th field="w_engineer" width="80" align="center">维修员</th>
										<th field="w_model" width="80" align="center">主板型号</th>
										<th field="w_marketModel" width="100" align="center">市场型号</th>
										<th field="insideSoftModel" width="80" align="center">内部机型</th>
										<th field="lockId" width="120" align="center">智能锁ID</th>	
										<th field="bluetoothId" width="120" align="center">蓝牙ID</th>
										<th field="acceptRemark" width="150" align="center">受理备注</th>
										<th field="backDate" width="130" align="center">预计返还日期</th>
										<th field="repairNumberRemark" width="150" align="center">批次备注</th>
										<th field="w_onePriceRemark" width="150" align="center">设备报价备注</th>
										<th field="remark" width="150" align="center">送修备注</th>
										<th field="w_repairRemark" width="150" align="center">维修备注</th>
										<th field="testPerson" width="100" align="center">测试人员</th>
										<th field="testResult" width="200" align="center"><font color="green;">测试结果</font></th>	
									</tr>
								</thead>
							</table>
				 	    </div>
				    </div>
			 	    <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('showAll')">关闭</a>
				   </div>
				</div>
			</div>
			
			<!-- 终检 -START-->
			<div id="wzj" class="easyui-window" title="终检" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="width:950px;height:550px; visibility: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false">
						<fieldset style="border:1px solid #ccc; margin:3px;">
							<legend><b>产品信息</b></legend> 
							<table align="center" width="100%" cellpadding="2">	
							    <tr>
							    	<td align="right">IMEI：</td> 
							     	<td>
							     		<input type="text" id="fm_imei" style="width:135px;" disabled="disabled" >
							     		<input type="hidden" id="id">
							     		<input type="hidden" id="w_fId">
	 						     	</td>
							     	<td align="right">送修单位 ：</td>
							     	<td>
								     	<input type="text" id="fm_w_cusName" style="width:135px;" disabled="disabled">
								     </td>
							     	<td align="right">主板型号：</td>
							     	<td>
							     		<input type="text" id="fm_w_model" style="width:135px;" disabled="disabled">
							     	</td> 
							     	<td align="right">市场型号：</td> 
							     	<td>
							     		<input type="text" id="fm_w_marketModel" style="width:135px;" disabled="disabled">
							     	</td>
							     </tr>
							     <tr>
							     <td align="right">智能锁ID：</td> 
							     	<td><input type="text" id="fm_lockId" style="width:135px;" disabled="disabled"></td>
							      	<td align="right">是否保修：</td>
							     	<td><input type="text" id="fm_w_isWarranty" style="width:135px;" disabled="disabled" ></td>
							     	<td align="right">付款时间：</td>
							     	<td><input type="text" id="fm_w_payTime" style="width:135px;" disabled="disabled" ></td>
							    	<td align="right">终&nbsp;检&nbsp;人：</td> 
							     	<td>
							     		<input type="text" id="fm_w_finChecker"  style="width:135px;" disabled="disabled">
							     	</td>
							     </tr>
							     <tr>
							     	<td align="right">最终故障：</td>
							     	<td colspan="3"> 
								     	<input type="text" id="fm_w_zzgzName" style="width:355px;" disabled="disabled">
								    </td>
									<td align="right">蓝牙ID：</td>
									<td><input type="text" id="fm_bluetoothId" style="width:135px;" disabled="disabled" ></td>
								    <td align="right">维&nbsp;修&nbsp;人：</td>
							     	<td><input type="text" id="fm_w_engineer" style="width:135px;" disabled="disabled" ></td>
							     </tr>
							     <tr>
							     	 <td align="right"><font color="red">测试结果：</font></td>
								     <td colspan="7">
								     	<textarea id="testResult_info" disabled="disabled" style="width:100%;"></textarea>
								     </td>
							     </tr>
							     <tr>
							     	<td align="right">是否合格：</td> 
							     	<td> 
								     	<input type="radio" name="fm_w_ispass" value="1" checked="checked" />是
	                			        <input type="radio" name="fm_w_ispass" value="0" />否
								    </td>
							    </tr>
							    <tr>
								    <td align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
							     	<td colspan="7" > 
								     	<textarea type="text" id="fm_w_finDesc" style="width:100%;"></textarea>
								     </td>
							     </tr>       
							</table> 
						</fieldset>
	
						<fieldset style="border:1px solid #ccc;  margin:10px; width: 95%;">
							<legend><b>终检测试用例匹配</b></legend>
							<table id="table_wzj" singleSelect="false" sortable="true" rownumbers="false" autoRowHeight="false" pagination="false" fitColumns="true" striped="true">
								<thead>
									<tr>
										<th field="model" width="100" align="center">主板型号</th>
										<th field="step" width="80" align="center">测试步骤</th>
										<th field="testMethod" width="300" align="center">方法</th>
										<th field="becareful" width="300" align="center">注意事项</th>
									</tr>
								</thead>
							</table>
						</fieldset>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a id="ficheckUpdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="InfoSave()">保存</a>  
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowFiCheckClose()">关闭</a>
				    </div>
				</div>
		     </div>
		     <!-- 终检 -END-->
	     </div>
	</div>
</body>
</html>