<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/tree.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/repair.js?20171012"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/final.js?20161219"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/dzlmaterial.js?20161110"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/cplmaterial.js?20161110"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/wxbjMaterial.js?20170117"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/wlsqMaterial.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/testMaterial.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20170831"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/material/changeboard_com.js"> </script>

<script type="text/javascript" src="${ctx}/res/js/basicdata/repairprice.pjl/pjl.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/repairprice.pjl/priceGroupPj.js"></script>
<script type="text/javascript" src="${ctx}/res/js/basicdata/basegroup.js"></script>

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
	<input type="hidden" id="gIds"/>
	<input type="hidden" id="gIds-WXBJ"/>
	<input type="hidden" id="gIds-DZL"/>
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/> 
	<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
	<div class="easyui-layout"	style="overflow: hidden; auto; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="维修列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">	
					<span>&nbsp;设备IMEI:&nbsp;<input type="text" class="form-search" id="repairImei" style="width: 130px"></span>
					<span>
						&nbsp;开始日期: 
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span>
					<span>&nbsp;送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmber" style="width: 135px"></span>&nbsp;
					<span>
						处理状态:&nbsp;
						<select id="repairState" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="0" selected="selected">待处理</option>
							<option value="1">已完成</option>
						</select>&nbsp;
					</span>
				</div>	
				<div style="margin-bottom: 5px;">
					<span>&nbsp;智能锁ID:&nbsp;<input type="text" class="form-search" id="repairLockId" style="width:130px"></span>
					<span>
						&nbsp;结束日期: 
						<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span>
					<span>
						&nbsp;送修单位:&nbsp;<input type="text" class="form-search" id="search_cusName" style="width: 135px">
					</span>
					<span id="repairUserSpan">&nbsp;维修人员:&nbsp;
						<select id="repairUser" style="width: 100px;"></select>&nbsp; 
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
								<td ><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-wxicon" plain="true" onclick="notPrice();">不报价</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-wxicon" plain="true" onclick="InfoUpdate();">维修</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-wlsqicon" plain="true" onclick="w1Apply();">物料申请</a></td>
								<td><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-fsbjicon" plain="true" onclick="sendPrice();">发送报价</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-fszjicon" plain="true" onclick="sendFicheck();">发送终检</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-fszxicon" plain="true" onclick="sendPack();">发送装箱</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-plfszfjicon" plain="true" onclick="sendWorkTestView();">发送至测试</a></td>							
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-return" plain="true" onclick="sendDataToSort();">返回分拣</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton isEnabled" iconCls="icon-wlsqicon" plain="true" onclick="changeboard('workRepair');">保内对换主板</a></td>
								<td ><div class="datagrid-btn-separator"></div><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>														
							</tr>
							<tr>
								<td colspan="9">
									<table style="width: 100%;">
										<tr>
											<td><span style="font-size: 14px;">【共<label style="color: green;">&nbsp;<span id="acc_number" style="font-weight: bold; font-size: 14px;">0</span>&nbsp;</label>条记录】</span></td>
											<td align="right">
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
			<table id="DataGrid1" singleSelect="true" fit="true" rownumbers="false" autoRowHeight="true" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="w_repairId"></th>
						<th field="id" hidden="true"></th>
						<th field="lockInfo" hidden="true"></th>
						<th field="acceptanceTime"  width="130"  align="center">受理时间</th>	
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="w_repairState" width="110" align="center">进度</th>
						<th field="w_cusName" width="110" align="center">送修单位</th>
						<th field="w_model" width="130" align="center">主板型号|市场型号</th>
						<th field="isWarranty" width="80" align="center">保内|保外</th>	
						<th field="w_sjfjDesc" width="160" align="center">随机附件</th>
						<th field="sfVersion" width="180" align="center">软件版本号</th>
						<th field="w_engineer" width="80" align="center">维修员</th>					
						<th field="lastEngineer"  width="80"   align="center">上次维修人员</th>
						<th field="lastAccTime"  width="130"   align="center">上次受理时间</th>
						<th field="testMatStatus" width="80" align="center"><font color="#0000FF">试流料</font></th>
						<th field="lockId" width="80" align="center">智能锁ID</th>	
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
						<th field="insideSoftModel" width="100" align="center">内部机型</th>
						<th field="backDate" width="80" align="center">预计返还日期</th>
						<th field="w_isRW" width="80" align="center">是否人为</th>
						<th field="returnTimes"  width="80"   align="center">返修次数</th>
						<th field="w_repairAgainCount" width="80" align="center">内部返修次数</th>
						<th field="price_createTime" width="130" align="center">发送报价日期</th>
						<th field="w_payTime" width="130" align="center">客户付款日期</th>		
						<th field="repairnNmber" width="110" align="center">送修批号</th>
						<th field="remark" width="160" align="center">送修备注</th>
						<th field="w_cjgzDesc" width="160" align="center">初检故障</th>
						<th field="w_zzgzDesc" width="200" align="center">最终故障</th>
						<th field="w_repairPrice" width="100" align="center">维修报价(￥)</th>
						<th field="w_hourFee" width="100" align="center">工时费(￥)</th>
						<th field="w_wxbjDesc" width="200" align="center">报价描述</th>
						<th field="w_matDesc" width="200" align="center">配件料</th>
						<th field="w_dzlDesc" width="200" align="center">电子料</th>
						<th field="w_sllDesc" width="200" align="center">试流料</th>
						<th field="w_sumPrice" width="100" align="center">维修总报价(￥)</th>
						<th field="w_solution" width="255" align="center">处理方法</th>
						<th field="testPerson" width="80" align="center">测试人员</th>
						<th field="testResult" width="200" align="center"><font color="green;">测试结果</font></th>
						<th field="acceptRemark" width="225" align="center">受理备注</th>
						<th field="w_onePriceRemark" width="225" align="center">报价备注</th>
						<th field="w_priceRemark" width="225" align="center">批次报价备注</th>
						<th field="repairNumberRemark" width="225" align="center">批次备注</th>						
					</tr>
				</thead>
			</table>
			<!-- ----------- 分页功能  --------------- -->
			<input type="hidden" name="total" value=""/>
			<input type="hidden" name="currentpage" value="1"/>
			<!-- -------------------------------- -->
		</div>
		
		<div id="repairManage" class="easyui-window" title="维修管理" modal="true" closed="true" style="width:1060px;height:550px;visibility: hidden;" 
			 minimizable="false" maximizable="false" collapsible="false" >
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:5px;background:#fff;border:1px solid #ccc;">
						<fieldset style="border:1px solid #ccc;">
							<legend><b>受理信息</b></legend> 
							  <table align="left" cellpadding="2" style="padding-left: 3px;">
								<tr>
									<td><input id="repair_id"  type="text"  style="display: none;"/></td>
									<td><input id="repair_repairId"  type="text"  style="display: none;"/></td>
									<td><input type="hidden" id="repair_w_sumPrice"></td>
									<td><input type="hidden" id="rfild"></td>
									<td><input type="hidden" id="repair_modelType"></td><!-- 型号类别 -->
									<td><input type="hidden" id="repair_w_isRW"/></td>
								</tr>
								<tr>
									<td align="right" style="width: 85px;">IMEI：</td>
									<td><input type="text" id="repair_imei" disabled="disabled" ></td>
									<td align="right">送修单位：</td>
									<td><input type="text" id="repair_w_cusName" disabled="disabled" ></td>
									<td align="right">送修批号：</td>
									<td><input type="text" id="repair_repairnNmber" disabled="disabled" ></td>
									<td align="right">返修次数：</td>
									<td><input type="text" id="repair_returnTimes" disabled="disabled" style="width: 135px;"></td>
								</tr>
								<tr>
									<td align="right" style="width: 85px;">智能锁ID：</td>
									<td><input type="text" id="repair_lockId" disabled="disabled" ></td>
									<td align="right" style="width: 65px;">蓝牙ID：</td>
									<td><input type="text" id="repair_bluetoothId" disabled="disabled"></td>
									
									<td align="right">出货日期：</td>
									<td><input type="text" id="repair_salesTime" disabled="disabled"  class="easyui-datetimebox"></td>
							     	<td align="right">订单号 ：</td>
							     	<td><input type="text" id="repair_bill" readonly="readonly"  style="width: 135px;"/></td>
								</tr>
								<tr>
									<td align="right">主板型号：</td>      
									<td><input type="text" id="repair_w_model" disabled="disabled" ></td>
									<td align="right">市场型号：</td>
									<td><input type="text" id="repair_w_marketModel" disabled="disabled" ></td>
									<td align="right">初检故障：</td>
									<td colspan="3"><input type="text" id="repair_w_cjgzDesc" style="width:100%;" disabled="disabled" ></td>
								</tr>
								<tr>
									<td align="right">受&nbsp;理&nbsp;人：</td>
									<td><input type="text" id="repair_accepter" disabled="disabled"></td>
									<td align="right">受理时间：</td>
									<td><input type="text" id="repair_acceptanceTime" disabled="disabled" class="easyui-datetimebox"></td>
									<td align="right">送修备注：</td>
									<td colspan="3"><input id="repair_remark" style="width: 100%;"  disabled="disabled"></td>
								</tr>
								<tr>
									<td align="right">随机附件：</td>
									<td colspan="3"><input type="text" id="repair_w_sjfjDesc" style="width:100%;" disabled="disabled" ></td>
									<td align="right">报价备注：</td>
									<td colspan="3"><input id="repair_onePriceRemark" style="width: 100%;"  disabled="disabled"></td>
								</tr>
								<tr>
							     	<td align="right">使用试流料 ：</td>
							     	<td><input type="text" id="repair_testMatStatus" disabled="disabled"></td>
							     	<td align="right">订单数量：</td>
							     	<td><input type="text" id="repair_outCount" disabled="disabled"></td>
									<td align="right"><font color="red">测试结果：</font></td>
									<td colspan="3"><input style="width: 100%;" id="repair_testResult" disabled="disabled"></td>
								</tr>
								<tr>
									<td align="right" style="width: 85px;">上次维修人员：</td>
									<td><input type="text" id="repair_lastEngineer" disabled="disabled" /></td>
									<td align="right">内部机型：</td>
							     	<td><input type="text" id="repair_insideSoftModel" disabled="disabled"></td>
							     	<td align="right" style="width: 80px;">软件版本号：</td>
							     	<td colspan="3"><input type="text" id="repair_sfVersion" disabled="disabled" style="width: 100%;"></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" style="width: 85px;">上次受理时间：</td>
									<td><input type="text" id="repair_lastAccTime"/></td>
									<td align="right">保内保外：</td>
									<td><input type="text" id="repair_w_isWarranty" disabled="disabled" ></td>
								</tr>
						 </table>
					</fieldset>
					<br>
					<fieldset style="border:1px solid #ccc;"> 
						<legend><b>维修信息</b></legend>
						<input type="hidden" id="update_repairState"/>							
					 	<table align="left" cellpadding="2" style="padding-left: 3px; table-layout: fixed;" id="weixiu-table">
							<tr>
								<td align="right" style="width: 85px;">最终故障：</td>
								<td width="400">
							     	<input type="text"  id="repair_w_zzgzDesc" style="width:325px;" readonly="readonly">
							     	<input type="hidden"  id="repair_w_zzgzId">
							     	<input type="hidden"  id="repair_stateFlag">
							     	<label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertZZGZ()">添加</a></label>
							     </td>
								<td align="right">维修工程师：</td>
								<td><input type="text" id="repair_w_engineer" disabled="disabled" /></td>
						    </tr>
					    	<tr>
						    	<td align="right">不良试流料：</td>
								<td>
							     	<input type="text"  id="repair_w_sllDesc" style="width:325px;" readonly="readonly"> 
							     	<input type="hidden"  id="repair_w_sllMatNO">
							     	<label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertSLL()">添加</a></label>
							     </td>
						    </tr>
						    <tr>
						    	 <td align="right" rowspan="2"  width="85">处理措施：</td>
							     <td rowspan="2"  width="360">
							     	<textarea rows="2" id="repair_w_solution" cols="48" style="resize:none;" maxlength="255" onblur="getSolutionTwo()" ></textarea>
							     </td>
							     <td align="right" rowspan="2"  width="70">处理措施2：</td>
							     <td rowspan="2"  width="360">
							     	<textarea rows="2"  disabled="disabled" id="repair_w_solution_two" cols="58" style="resize:none;height: 100%;" maxlength="255" ></textarea>
							     </td>							    							     
						    </tr>
					    </table>
					    <br>
						<table align="left" cellpadding="2" style="padding-left: 5px; table-layout: fixed;" id="baojia-table">
								<tr>
							    	<td align="right"  style="width: 85px;">维修报价：</td>
							    	<td width="400">
							    		<input type="text"  id="repair_w_wxbjDesc" style="width:325px;" readonly="readonly">
								     	<input type="hidden"  id="repair_w_wxbjId">
								     	<label class="only_show ficheckHide"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertWXBJ()">添加</a></label>
							    	</td>
							    	<td align="right" rowspan="2"  width="70">报价说明：</td>
								    <td rowspan="2"  width="360">
								     	<textarea id="repair_w_priceRemark" rows="2" cols="58" style="resize:none;" maxlength="255" ></textarea>
								    </td>
							    </tr>
							    <tr>
							    	<td align="right">选择配件料：</td>
									<td>
								     	<input type="text"  id="repair_w_matDesc" style="width:325px;" readonly="readonly"> 
								     	<input type="hidden"  id="repair_w_matId">
								     	<input type="hidden"  id="repair_w_consumption">
								     	<label class="only_show repair_beforeHide"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertMat()">添加</a></label>
								     </td>
							    </tr>
							    <tr>
							    	<td align="right">选择电子料：</td>
									<td>
								     	<input type="text"  id="repair_w_dzlDesc" style="width:325px;" readonly="readonly"> 
								     	<input type="hidden"  id="repair_w_dzlId">
								     	<label class="only_show repair_beforeHide"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertDzl()">添加</a></label>
								     </td>
									<td align="right"><font style="color:red;font-weight: bold;">是否人为：</font></td>
									<td><input type="radio" name="repair_w_isRW" value="0" /><font style="color:red;font-weight: bold;">是</font>
										<input type="radio" name="repair_w_isRW" value="1" checked="checked"/><font style="color:blue;font-weight: bold;">否</font>
									</td>
							    </tr>
								<tr>
									<td align="right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7"><textarea rows="2" id="repair_w_repairRemark"  style="height: 50px;width:100%; resize:none;"></textarea></td>
								</tr>
							
					  </table>
					</fieldset>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px; margin: 5px 0px;">
					<a id="baojiaUpdate" class="easyui-linkbutton isEnabled" iconCls="icon-ok" href="javascript:void(0)" onclick="repairUpdate()">保存</a>
					<a id="weixiuUpdate" class="easyui-linkbutton isEnabled" iconCls="icon-ok" href="javascript:void(0)" onclick="doSaveRepair()">保存</a>
					<a id="sureUpdate"  class="easyui-linkbutton isEnabled" iconCls="icon-ok" href="javascript:void(0)" onclick="doSureUpdate()">保存</a>
					<a id="repairClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowRepairClose();">关闭</a>
				</div>
			</div>
		</div>

		<!-- 选择最终故障  -Start-->
		
		<div id="zzgz_w" class="easyui-window" title="选择最终故障" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				 <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
			       <ul id="typeTree-ZZGZ" class="easyui-tree" animate="true" dnd="false"></ul>
	  			 </div>
				 <div region="center" id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择">
					<div style="height:280px;">
						<div id="zToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<!-- <input type="hidden" class="form-search" id="modelType"> -->	
			 					<span>&nbsp;故障编码:&nbsp;<input type="text" class="form-search1" id="methodNO" style="width:120px"></span>&nbsp;&nbsp;&nbsp;&nbsp;
			 					<span>&nbsp;关键字:&nbsp;<input type="text" class="form-search1" id="proceMethod" style="width:120px" placeholder="最终故障"></span>&nbsp;&nbsp;&nbsp;&nbsp;
								<span>&nbsp;&nbsp;
									<a id="searchZzgzInfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageZZGZ(1)">查询</a>
								</span>
							</div>
						</div>
					
						<table id="zzgz_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" fitColumns="true" striped="true" scrollbarSize="0" toolbar="#zToolbar">
							<thead>
								<tr>
									<th field="id" hidden="true"></th>
									<th field="faultType" width="220" align="center">故障类别</th>
									<th field="proceMethod" width="220" align="center">最终故障</th>
									<th field="methodNO" width="220" align="center">故障编码</th>
									<!-- <th field="modelType" width="220" align="center">型号类别</th> -->
									<th field="isSyncSolution" width="220" align="center">是否同步</th>
									<th field="remark" width="220" align="center">方法描述</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选最终故障</div>
						<div class="selected-type-box" id ="selected-type-box-ZZGZ">
						</div>						
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				    <a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="ZZGZSave()">保存</a>
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('zzgz_w')">关闭</a>
			    </div>
	    </div>
	   </div>
	    <!-- 选择最终故障  - End-->
	    
	    <!-- 选择维修报价  -Start-->		
		<div id="wxbj_w" class="easyui-window" title="选择维修报价" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				 <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
			       <ul id="typeTree-WXBJ" class="easyui-tree" animate="true" dnd="false"></ul>
	  			 </div>
				 <div region="center" id="dic-center" style="overflow:auto" title="[详细信息]  注：双击选择">
					<div style="height:280px;">
						<div id="wToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">		
			 					<span>&nbsp;编码:&nbsp;<input type="text" class="form-search2" id="wxbjName" style="width:120px"></span>&nbsp;&nbsp;
								<span>&nbsp;关键字:&nbsp;<input type="text" class="form-search2" id="searchKey" style="width:170px" placeholder="报价描述，主板型号"></span>&nbsp;&nbsp;&nbsp;&nbsp;
								<span>&nbsp;&nbsp;
									<a id="searchWxbjInfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageWxbj()">查询</a>
								    <a id="addRepairPrice" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="repairPrice_addInfo();">增加</a>
								</span>
							</div>
						</div>
					
						<table id="wxbj_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" striped="true" scrollbarSize="0" toolbar="#wToolbar">
							<thead>
								<tr>
									<th field="rid" hidden="true"></th>
									<th field="gId" hidden="true">类别</th>
									<th field="repairType" width="100" align="center">报价类别</th>
									<th field="priceNumber" width="80" align="center">编码</th>
									<th field="model" width="80" align="center">主板型号</th>
									<th field="amount" width="220" align="center">报价描述</th>
									<th field="pjlDesc" width="200" align="center">关联配件料</th>
									<th field="price" width="80" align="center">报价金额（￥）</th>
									<th field="hourFee" width="80" align="center">工时费（￥）</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选维修报价</div>
						<div class="selected-type-box" id ="selected-type-box-Wxbj">
						</div>						
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				    <a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="WxbjSave()">保存</a>
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('wxbj_w')">关闭</a>
			    </div>
	  	  </div>
	   </div>
	   <!-- 选择维修报价 END -->
	    
	    <!-- 选择配件料 - Start-->
		<div id="mat_w" class="easyui-window" title="选择配件料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div><font color="red">内部机型：</font> <font color="blue"><span id="repair_pjl_insideSoftModel"></span></font></div>
					<div style="height:280px;">
						<div id="cpToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">
								<span>&nbsp;编码:&nbsp;<input type="text" class="form-search3"  id="s_proNO_cpl" style="width:150px"></span>&nbsp;
								<span>&nbsp;名称:&nbsp;<input type="text" class="form-search3"  id="s_proName_cpl" style="width:150px"></span>&nbsp;
								<span>备选型号
									<select id="otherModel" class="easyui-combobox" style="width: 150px;" editable="false" panelHeight="auto" >
									</select>
								</span>
								<span>&nbsp;
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageCPL(1)">查询</a>
								</span>
							</div>
						</div>
						<table id="mat_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" 
								pagination="true" fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#cpToolbar">
							<thead>
								<tr>
									<th field="pjlId" hidden="true">id</th>
									<th field="model" width="100" align="center">主板型号</th>
									<th field="marketModel" width="100" align="center">市场型号</th>
									<th field="proNO" width="150" align="center">物料编码</th>
									<th field="proName" width="160" align="center">物料名称</th>
									<th field="proSpeci" width="260" align="center">规格</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选配件料</div>
						<div class="selected-type-box" id ="selected-type-box-CPL">
						</div>						
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="CPLSave()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="pjl_w_close();">关闭</a>
			    </div>
			</div>
	    </div>
	    <!-- 选择配件料  - End -->
	    
	    <!--  选择电子料 - Start-->
		<div id="dzl_w" class="easyui-window" title="选择电子料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:950px;height:520px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
			 	<div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
			       <ul id="typeTree-DZL" class="easyui-tree" animate="true" dnd="false"></ul>
	  			</div>
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<div id="dzToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search4" id="s_proNO_dzl" style="width:150px"></span>&nbsp;&nbsp;
								<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search4" id="s_proName_dzl" style="width:150px"></span>&nbsp;&nbsp;
								<span>&nbsp;&nbsp;
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageDZL(1)">查询</a>
								</span>
							</div>
						</div>
						<table id="dzl_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" 
								pagination="true" striped="true" scrollbarSize="0" toolbar="#dzToolbar">
							<thead>
								<tr>
									<th field="dzlId" hidden="true"></th>
									<th field="dzlType" width="80" align="center">类别</th>
									<th field="proNO" width="150" align="center">物料编码</th>
									<th field="proName" width="130" align="center">物料名称</th>
									<th field="proSpeci" width="180" align="center">规格</th>
									<th field="remark" width="220" align="center">备注</th>
									<th field="consumption" width="50" align="center">用量</th>
									<th field="placesNO" width="60" align="center">序号</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选电子料</div>
						<div class="selected-type-box" id ="selected-type-box-DZL">
						</div>						
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="DZLSave()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('dzl_w')">关闭</a>
			    </div>
			</div>
	    </div>
	    <!-- 选择电子料  - End-->
	    
	    <!--  选择试流料 - Start-->
		<div id="sll_w" class="easyui-window" title="选择试流料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<div id="sllToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search6" id="s_proNO_sll" style="width:180px"></span>&nbsp;&nbsp;
								<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search6" id="s_proName_sll" style="width:180px"></span>&nbsp;&nbsp;
								<span>&nbsp;&nbsp;
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageSLL(1)">查询</a>
								</span>
							</div>
						</div>
						<table id="sll_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" 
								pagination="true" striped="true" scrollbarSize="0" toolbar="#sllToolbar">
							<thead>
								<tr>
									<th field="bill" width="150" align="center">订单号</th>
									<th field="materialNumber" width="150" align="center">物料编码</th>
									<th field="materialName" width="160" align="center">物料名称</th>
									<th field="materialSpecification" width="260" align="center">规格</th>
									<th field="placeNumber" width="80" align="center">位号</th>
								</tr>
							</thead>
						</table>
					</div>
					<div class="selected-type" style="height: 130px;">
						<div class="panel-header"><a href="javascript:;" class="clear-select-btn">清除</a>已选试流料</div>
						<div class="selected-type-box" id ="selected-type-box-SLL">
						</div>						
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a class="easyui-linkbutton" iconCls="icon-ok"     href="javascript:void(0)" onclick="SLLSave()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('sll_w')">关闭</a>
			    </div>
			</div>
	    </div>
	    <!-- 选择试流料  - End-->
	    
		<!-- 物料申请  - Start-->
		<div id="w1Apply" class="easyui-window" title="物料申请" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width: 640px; height: 450px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="background: #fff; border: 1px solid #ccc;">
					<form id="w1Applyform" action="">
						<table align="center" cellspacing="2">
							<tr>
								<td><input id="appId"  type="text"  style="display: none;"/></td>
							</tr>
							<tr>
								<td>物料类型：</td>
								<td>
									<select id="matType" class="easyui-combobox" style="width: 150px;" editable="false" panelHeight="auto" >
										<option value="0">配件料</option>
										<option value="1">电子料</option>
									</select>&nbsp;
								</td>
								<td>&nbsp;平&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;台：</td>
								<td><input type="text" id="platform" style="width: 150px;"/></td>
							</tr>
							<tr>
								<td>物料编码：</td>
								<td colspan="3"><input type="text" id="proNO" style="width: 80%" class="easyui-validatebox" required="true" missingMessage="请填写此字段" readonly="readonly">
									<label><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="chooseMaterial();">添加</a></label>
								</td>
							</tr>
							<tr>
								<td>物料名称：</td>
								<td colspan="3"><input type="text" id="proName" style="width: 100%"></td>
							</tr>
							<tr>
								<td>物料规格：</td>
								<td colspan="3"><input type="text" id="proSpeci" style="width: 100%"></td>
							</tr>
							<tr>
								<td>申请数量：</td>
								<td><input type="text" id="number" style="width: 150px;" class="easyui-validatebox" validType="Number" required="true"  missingMessage="请填写此字段"></td>
								<td>&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
								<td><input type="text" id="unit" style="width: 150px;"></td>
							</tr>
							<tr>
								<td>&nbsp;申&nbsp;请&nbsp;人：</td>
								<td><input type="text" id="applicater" style="width: 150px;" disabled="disabled" value="${USERSESSION.uName}"/></td>
								<td>申请日期：</td>
								<td><input type="text" id="appTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width: 150px"></td>
							</tr>
							<tr>
								<td>申请用途：</td>
								<td colspan="7"><textarea id="purpose" style="width: 100%; height: 80px; resize:none;" ></textarea></td>
							</tr>
							<tr>
								<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td colspan="7"><textarea id="wl_remark" style="width: 100%; height: 80px; resize:none;" ></textarea></td>
							</tr>
						</table>
					</form>
				</div>
				<div region="south" border="false" 	style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="wlAdd()">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="wlApply_close();">关闭</a>
				</div>
			</div>
		</div>
		<!-- 物料申请  - End-->
		
		<!-- 选择物料信息 -START-->
		<div id="w_material" class="easyui-window" title="选择物料信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			  style="width:760px;height:500px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region='center' >
					<div id="wlsqToolbar" style=" padding: 10px 15px;">
						<div style="margin-bottom:5px">			
							<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search5"  id="s_proNO" style="width:180px" /></span>&nbsp;&nbsp;
							<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search5"   id="s_proName" style="width:180px" /></span>&nbsp;&nbsp;
							<span>&nbsp;&nbsp;
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageMaterial(1);">查询</a>
							</span>       
						</div>
					</div>
									
					<table id="WLSQ_MaterialDataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="true" fitColumns="true" autoRowHeight="true" 
						   striped="true" scrollbarSize="0" toolbar="#wlsqToolbar">
						<thead>
							<tr>
								<th checkbox="true" field="id"></th>
								<th field="proNO" width="180px" align="center">物料编码</th>
								<th field="proName" width="200px" align="center">物料名称</th>
								<th field="proSpeci" width="330px" align="center">物料规格</th>
							</tr>
						</thead>
					</table>
				</div> 
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px; margin-top:5px;"> 
					<a id="wlsqmat" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_material')">关闭</a>  
			    </div> 
			</div>
	     </div>
	    <!-- 选择物料信息 -END-->
	    
	    <!-- 保内对换主板  - Start-->
		<div id="changeboard_w" class="easyui-window" title="保内对换主板" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width: 500px; height: 380px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="background: #fff; border: 1px solid #ccc;">
					<form id="changeboard_form" action="">
						<table align="center" cellspacing="2">
							<tr>
								<td><input id="changeboard_id" type="hidden"/></td>
								<td><input id="changeboard_wfId" type="hidden"/></td>
								<td><input id="changeboard_repairOrTest" type="hidden"/></td>
							</tr>
							<tr>
								<td>客户名称：</td>
								<td><input type="text" id="changeboard_cusName" style="width: 150px;" disabled="disabled"> </td>
								<td>&nbsp;&nbsp;&nbsp;I&nbsp;M&nbsp;E&nbsp;I：</td>
								<td><input type="text" id="changeboard_imei" style="width: 150px;" disabled="disabled"></td>
							</tr>
							<tr>
								<td>主板型号：</td>
								<td><input type="text" id="changeboard_model" style="width: 150px;" disabled="disabled"></td>
								<td>保内|保外：</td>
								<td><input type="text" id="changeboard_isWarranty" style="width: 150px;" disabled="disabled"></td>
							</tr>
							<tr>
								<td>申请数量：</td>
								<td><input type="text" id="changeboard_number" style="width: 150px;" disabled="disabled"></td>
							</tr>
							<tr>
								<td>&nbsp;申&nbsp;请&nbsp;人：</td>
								<td><input type="text" id="changeboard_applicater" style="width: 150px;" disabled="disabled" value="${USERSESSION.uName}"/></td>
								<td>申请日期：</td>
								<td><input type="text" id="changeboard_appTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width: 150px"></td>
							</tr>
							<tr>
								<td>换板原因：</td>
								<td colspan="7"><textarea id="changeboard_purpose" style="width: 100%; height: 50px; resize:none;"  class="easyui-validatebox" required="true" missingMessage="请填写此字段"></textarea></td>
							</tr>
							<tr>
								<td>&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td colspan="7"><textarea id="changeboard_remark" style="width: 100%; height: 50px; resize:none;" ></textarea></td>
							</tr>
						</table>
					</form>
				</div>
				<div region="south" border="false" 	style="text-align: right; height: 30px; line-height: 30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="changeboardAdd('repair')">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="changeboard_close();">关闭</a>
				</div>
			</div>
		</div>
		<!-- 保内对换主板  - End-->
		
		
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
<!--                                 <tr> -->
<!--                                     <td align="right">报价金额：</td> -->
<!--                                     <td> -->
<!--                                         <input type="text" id="price" style="width: 220px;" maxlength="255" class="easyui-validatebox" validType="Amount"  required="true" missingMessage="请填写金额"/> -->
<!--                                     </td> -->
<!--                                 </tr> -->
                                <tr>
                                    <td align="right">工&nbsp;时&nbsp;费：</td>
                                    <td>
                                        <input type="text" id="hourFee" style="width: 220px;" maxlength="255" class="easyui-validatebox" validType="Amount"  required="true" missingMessage="请填写金额"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">主板型号：</td>
                                    <td>
                                        <input type="text" id="model" style="width: 220px;" maxlength="255" class="easyui-validatebox" disabled="disabled"/>
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
		
	</div>
	<div>
		<!-- 区分最终故障，配件料，电子料的弹框 -->
		<input type="hidden" id="finallyFault"/>
		<input type="hidden" id="dzlModal"/>
		<input type="hidden" id="sslModal"/>
		<input type="hidden" id="cplModal"/>
		<input type="hidden" id="wxbjModal"/>
	</div>
	<input type="hidden" id="LX_Type" value="BASE_WXBJ" />
    <input type="hidden" id="gIds" />
</body>
</html>