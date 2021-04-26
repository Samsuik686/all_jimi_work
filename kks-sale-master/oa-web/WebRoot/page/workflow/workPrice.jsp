<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/tree.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/price.js?20170224"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/dzlmaterial.js?20161215"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/cplmaterial.js?20161110"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/repair/wxbjMaterial.js?20161209"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20170831"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/price_pj.js"></script>

</head>
<body style="margin:0">
	<input type="hidden" id="gIds"/>
	<input type="hidden" id="gIds-WXBJ"/>
	<input type="hidden" id="gIds-DZL"/>
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	<input type="hidden" id="curLoginer" value="${USERSESSION.uName}"/>
	<div class="easyui-layout" style="overflow: hidden; width:100%;" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow:auto" title="报价列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">
					<span>
						&nbsp;设备IMEI:&nbsp;
						<input type="text" class="form-search" style="width:138px ">
                        <textarea class="form-search" style="position: absolute;left:79px;resize: none; width: 138px;height: 26px;" id="price_imei" onfocus="clickFocus()" onblur="clickBlur()"></textarea> 
                        &nbsp;
					</span>
					<span>
						&nbsp;客户名称:&nbsp;<input type="text" class="form-search"  id="price_cusName" style="width:135px ">&nbsp;
					</span>
					<span>
						&nbsp;开始日期:  
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span>
					<span>
						&nbsp;处理状态: 
						<select id="priceState" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="">全部</option>
							<option value="2" selected="selected">待处理</option>
							<option value="1">已完成</option>
						</select>&nbsp;
					</span>
				</div>
				<div style="margin-bottom: 5px;">
					<span>&nbsp;智能锁ID:&nbsp;<input type="text" class="form-search" id="price_lockId" style="width:130px"></span>&nbsp;
					<span>
						&nbsp;送修批号:&nbsp;<input type="text" class="form-search"  id="repairnNmber" style="width:135px ">&nbsp;
					</span>
					<span>
						&nbsp;结束日期: 
						<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"> 
					</span> &nbsp;&nbsp;
					<span>
						&nbsp;进&nbsp;&nbsp;&nbsp;度: 
						<select id="state" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="0" >待报价</option>
							<option value="2">已报价，待付款</option>
							<option value="1">已付款，待维修</option>
							<option value="-1">已发送</option>
						</select>&nbsp;
					</span>
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
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
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="showInfo();">修改</a></td>														
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pcckicon" plain="true" onclick="showByRepairnNmber();">批次查看</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-pcckicon" plain="true" onclick="ListLogCostInit();">批次填写物流费</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-fswxicon" plain="true" onclick="giveupPrice();">放弃报价发维修</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-fswxicon" plain="true" onclick="sendRepair();">已付款发送维修</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a></td>
						</tr>
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>							
							<td><div class="datagrid-btn-separator"></div></td>														
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-bjicon" plain="true" onclick="bathPrice();">批次报价</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="ListAllPriceRepairNub();">可报价批次号</a></td>							
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-return" plain="true" onclick="sendDataToRepair();">未报价返回维修</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-return" plain="true" onclick="sendNoPriceToRepair();">待付款返回维修</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
<!-- 							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-wxicon" plain="true" onclick="prolong();">延保</a></td> -->
						</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1" singleSelect="true" fit="true" rownumbers="false" pagination="true" pageSize="20" autoRowHeight="true" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="w_priceId" align="center"></th>
						<th field="acceptanceTime" width="130" align="center">受理时间</th>
						<th field="price_createTime"  width="130" align="center">维修发送时间</th>						
						<th field="w_cusName" width="150" align="center">客户名称</th>
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="w_phone" width="90" align="center">手机号</th>		
						<th field="repairnNmber" width="110" align="center">送修批号</th>
						<th field="w_priceState" width="120" align="center">进度</th>
						<th field="w_engineer" width="80" align="center">维修员</th>	
						<th field="w_marketModel" width="90" align="center">市场型号</th>	
						<th field="acceptRemark" width="150px" align="center">受理备注</th>
						<th field="w_cusRemark" width="150" align="center">送修单位备注</th>
						<th field="w_repairRemark" width="150px" align="center">维修备注</th>
						<th field="w_sumPrice" width="80" align="center">维修报价(￥)</th>						
						<th field="w_wxbjDesc" width="200" align="center">维修报价描述</th>
						<th field="testPerson" width="80" align="center">测试人员</th>	
						<th field="testResult" width="150"  align="center"><font color="green;">测试结果</font></th>												
						<th field="w_t_remAccount" width="80" align="center">收款方式</th>
						<th field="w_receipt" width="80" align="center">是否收税</th>											
						<th field="isWarranty" width="80" align="center">保内|保外</th>										
						<th field="w_isRW" width="80" align="center">是否人为</th>						
						<th field="w_model" width="80" align="center">主板型号</th>	
						<th field="backDate" width="100" align="center">预计返还日期</th>	
						<th field="remark" width="150px" align="center">送修备注</th>		
						<th field="w_zzgzDesc" width="150px" align="center">最终故障</th>											   						
						<th field="w_expressNO" width="100px" align="center">快递单号</th>	
						<th field="w_isPay" width="80" align="center">是否付款</th>						
						<th field="w_offer" width="80px"  align="center">报价人</th>													
						<th field="insideSoftModel" width="140" align="center">内部机型</th>
						<th field="lockId" width="80" align="center">智能锁ID</th>	
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
						<th field="payDelivery"  width="100px"  align="center">客户寄货方式</th>	
						<th field="w_priceRemark" width="150px" align="center">维修报价说明</th>	
						<th field="repairNumberRemark" width="150px" align="center">批次备注</th>									
					</tr>
				</thead>
			</table>
		</div>  
	    
	    <!-- ==============================批次查看 start ================================ -->
	    <div style="position:fixed;">
		    <div id="batchInfo_w" class="easyui-window" title="批次查看" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
			 	 style="width:1050px;height:530px;visibility: hidden; ">
			 	
				<div id="batch_toolbar" style="height:30px; padding: 2px 15px;">
					<div>
						<span>
							状态：<select id="search_State" style="width: 130px;"></select>
						</span>
						<span>
							<a  class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryBatchList()">查询</a>
						</span>
						<span>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryBatchList();">刷新</a>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-bjicon" plain="true" onclick="bathPrice(1);">批次报价</a>
						</span>
					</div>
				</div>
			 	<div class="easyui-layout" style="overflow: hidden; width:100%;height:500px;" fit="true"> 
			 		<div region="north" id="dic-north" style="overflow:auto;height:25px;">
				 		<div id="show_price" style="height:18px; padding: 2px 15px; display: none;">
					 		<span style="font-weight:800; font-size: 14px;color:blue;margin-top: 5px;">批次费用统计：</span> &nbsp;&nbsp;&nbsp;
					   		<span id="show_repairPrice" style="font-weight:400; font-size: 12px;color:red;margin-top: 10px;">维修费：￥0.00 </span> &nbsp;&nbsp;&nbsp;
					   		<span id="show_pjCost" style="font-weight:400; font-size: 12px;color:#0066FF;margin-top: 10px;">配件费 ：￥0.00</span> &nbsp;&nbsp;&nbsp;
					   		<span id="show_logCost" style="font-weight:400; font-size: 12px;color:#0066FF;margin-top: 10px;">运费：￥0.00 </span> &nbsp;&nbsp;&nbsp;
					   		<span id="show_ratePrice" style="font-weight:400; font-size: 12px;color:#0066FF;margin-top: 10px;">税费：￥0.00 </span> &nbsp;&nbsp;&nbsp;
					   		<span id="show_sumPrice" style="font-weight:400; font-size: 12px;color:green;margin-top: 10px;">总费用：￥0.00 </span> &nbsp;&nbsp;&nbsp;
						</div>
			 		</div>
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
							<table id="batchInfo_DataGrid" singleSelect="true" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" striped="true" toolbar="#batch_toolbar">
								<thead>
									<tr>
										<th checkbox="true" field="w_priceId"></th>
										<th field="w_priceStateFalg" hidden="true"></th><!-- 报价表状态 -->
										<th field="acceptanceTime" width="125" align="center">受理时间</th>
										<th field="imei" width="105" align="center">IMEI</th>
										<th field="repairnNmber" width="100" align="center">送修批号</th>
										<th field="w_phone" width="85" align="center">客户手机号</th>
										<th field="w_cusName" width="110" align="center">客户名称</th>
										<th field="state" width="110" align="center">报价状态</th>										
										<th field="isWarranty" width="60" align="center">保内|保外</th>
										<th field="w_isRW" width="40" align="center">人为</th>	
										<th field="w_repairPrice" width="75" align="center">维修报价(￥)</th>
										<th field="w_sumPrice" width="55" align="center">维修(￥)</th>
										<th field="w_priceRepairMoney" width="55" align="center">报价(￥)</th>
										<th field="w_engineer" width="80" align="center">维修员</th>
										<th field="w_wxbjDesc" width="150" align="center">维修报价描述</th>
										<th field="testPerson" width="80" align="center">测试人员</th>	
										<th field="testResult" width="150"  align="center"><font color="green;">测试结果</font></th>	
										<th field="w_model" width="80" align="center">主板型号</th>
										<th field="w_marketModel" width="100" align="center">市场型号</th>
										<th field="insideSoftModel" width="100" align="center">内部机型</th>
										<th field="acceptRemark" width="150px" align="center">受理备注</th>
										<th field="remark" width="150px" align="center">送修备注</th>	
										<th field="w_repairRemark" width="150px" align="center">维修备注</th>	
										<th field="repairNumberRemark" width="150px" align="center">批次备注</th>		
										<th field="lockId" width="80" align="center">智能锁ID</th>	
										<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
										<th field="w_zzgzDesc" width="150px" align="center">最终故障</th>											   						
										<th field="w_expressNO" width="150px" align="center">快递单号</th>	
										<th field="w_cusRemark" width="150" align="center">送修单位备注</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;margin-top: 5px;">
						<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="batchInfo_w_close()">关闭</a>
			        </div>
				</div>
			</div>
		
			<!-- ==============================批次查看 end================================ -->
			<!-- ==============================批次报价-START============================== -->
			<div id="bathPrice_w" class="easyui-window" title="批量报价信息" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
			 	 style="width:450px;height:535px;visibility: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding:1px;background:#fff;border:1px solid #ccc;">
						<div align="right">
							<input type="hidden" id="type_batch" value="type_batch"/>
							<input type="button" id="showPj" onclick="showPj();" value="批次配件料管理" />
							<input type="button" id="isLine" onclick="showIsPay();" value="人工确认付款" />
							<input id="isAutoPay" type="text" style="display: none;"/>
						</div>
						<form id="priceForm">
							<fieldset style="border:1px solid #ccc;margin: 0px; padding:0px;">
								<legend><b>报价信息</b></legend>
								<table align="center" width="100%" cellpadding="1">	
									<tr>
										<td><input type="text" id="bathPrice_cusName" style="display: none;"/></td>
									</tr>
									<tr>	
										<td align="right">批&nbsp;次&nbsp;号： </td>
										<td><input type="text" id="bathPrice_repairnNmber" style="width:235px;"  disabled="disabled"/></td>
									</tr>
									<tr class="price_isHidden">	
										<td align="right">收款方式： </td>
										<td><input type="text" id="bathPrice_remAccount"  style="width:235px;" disabled="disabled"/></td>
									</tr>
									<tr>	
									    <td align="right">维修费用： </td>
										<td><input type="text" id="bathSumRepairPrice" value="0" style="width:235px;" disabled="disabled"/></td>
									</tr>
									<tr>	
									    <td align="right">物&nbsp;流&nbsp;费： </td>
										<td><input type="text" id="bathPrice_logCost" value="0" style="width:235px;" class="easyui-validatebox" validType="Amount"/></td>
									</tr>
									<tr>    
                                        <td align="right">延&nbsp;保&nbsp;费： </td>
                                        <td><input type="text" id="bathPrice_prolongCost" value="0" style="width:235px;" class="easyui-validatebox" validType="Amount" disabled="disabled"/></td>
                                    </tr>
									<tr>	
									    <td align="right">批次配件费： </td>
										<td><input type="text" id="bathPrice_pjCost" value="0" style="width:235px;" class="easyui-validatebox" validType="Amount" disabled="disabled"/></td>
									</tr>
									<tr>	
									    <td align="right">是否开发票： </td>
										<td align="left">
											<input type="radio" name="bathPrice_receipt" value="0"/>是
											<input type="radio" name="bathPrice_receipt" value="1"  checked="checked"/>否
											<input type="text" id="rate" disabled="disabled" placeholder="税率（整数）" style="width: 110px;"  maxlength="3" validType="Number" class="easyui-validatebox"/>%
										</td>
									</tr>
									<tr>	
									    <td align="right">税费： </td>
										<td><input type="text" id="bathPrice_ratePrice" value="0" style="width:235px;" disabled="disabled"/></td>
									</tr>									
									<tr>	
									    <td align="right">报价费用： </td>
										<td><input type="text" id="bathPrice_totalMoney" value="0" style="width:235px;" disabled="disabled"/></td>
									</tr>
									<tr>	
									    <td align="right">客户收货方式： </td>
										<td align="left">
											<input type="radio" name="customer_Receipt" value="Y" checked="checked"/>顺付
											<input type="radio" name="customer_Receipt" value="N"/>到付											
										</td>
									</tr>
									<tr class="price_isHidden">	
									    <td align="right">是否付款： </td>
										<td align="left">
											<input type="radio" name="bathPrice_isPay" value="0"/>是
											<input type="radio" name="bathPrice_isPay" value="1"  checked="checked"/>否
										</td>
									</tr>
									<tr class="price_isHidden">
										<td align="right">付款日期： </td>
										<td><input type="text" id="bathPrice_payTime" style="width:235px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" editable="false"/></td>
									</tr>
									<tr class="price_isHidden">	
										<td align="right">付款原因： </td>
										<td><input type="text" id="bathPrice_payReason" style="width:235px;"/></td>
									</tr>	
									<tr>
										<td align="right">报价备注：</td>
										<td align="left" ><textarea id="priceRemark" style="width: 235px; height: 50px; resize:none;"></textarea></td>
									</tr>
								</table>
							</fieldset>
						</form>
					</div>
					<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
						<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveBathPrice()">保存</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="bathPrice_w_close()">关闭</a>
			        </div>
				</div>
			</div>
			
            <!-- 延保 -->
			<div id="prolong_w" class="easyui-window" title="延保服务" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
                 style="width:450px;height:350px;visibility: hidden;">
                <div class="easyui-layout" fit="true">
                    <div region="center" border="false" style="padding:1px;background:#fff;border:1px solid #ccc;">
                       <div align="right">
                            <input type="button" id="prolong_set" onclick="prolong_prcie_w_open()" value="设置延保价格" />
                        </div>
                        <form id="prolong">
                            <fieldset style="border:1px solid #ccc;margin: 0px; padding:0px;">
                                <legend><b>延保信息</b></legend>
                                <table align="center" width="100%" cellpadding="1"> 
                                    <tr>    
                                        <td align="right">批次号： </td>
                                        <td><input type="text" id="prolong_repair_number" value="0" style="width:235px;" disabled="disabled"/></td>
                                    </tr>
                                    <tr>    
                                        <td align="right">IMEI： </td>
                                        <td><textarea id="prolong_repair_imei"  style="width:235px;height:60px" disabled="disabled"/></textarea></td>
                                    </tr>
	                                <tr>
	                                    <td align="right">延保时间： </td>
	                                    <td>
                                            <input type="radio" name="prolong_year" value="1" checked="checked"/>一年
                                            <input type="radio" name="prolong_year" value="2" />两年
                                            <input type="radio" name="prolong_year" value="3" />三年
	                                    </td>
	                                </tr>
<!--                                     <tr> -->
<!--                                         <td align="right">是否开发票： </td> -->
<!--                                         <td align="left"> -->
<!--                                             <input type="radio" name="prolong_receipt" value="0"/>是 -->
<!--                                             <input type="radio" name="prolong_receipt" value="1"  checked="checked"/>否 -->
<!--                                             <input type="text" id="prolong_rate" disabled="disabled" placeholder="税率（整数）" style="width: 110px;"  maxlength="3" validType="Number" class="easyui-validatebox"/>% -->
<!--                                         </td> -->
<!--                                     </tr> -->
                                    <tr>
                                        <td align="right">延保单价： </td>
                                        <td><input type="text" id="prolong_cost" value="0" style="width:235px;" disabled="disabled"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">延保数量： </td>
                                        <td><input type="text" id="prolong_devices" value="0" style="width:235px;" disabled="disabled"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">延保总费用： </td>
                                        <td><input type="text" id="prolong_total_price" value="0" style="width:235px;"  disabled="disabled"/></td>
                                    </tr>
                                </table>
                            </fieldset>
                        </form>
                    </div>
                    <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
                        <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveProlong()">保存</a>
                        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="prolong_w_close()">关闭</a>
                    </div>
                </div>
            </div>
            
            <!-- 设置延保价格 -->
            <div id="prolong_prcie_w" class="easyui-window" title="延保服务" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
                 style="width:450px;height:335px;visibility: hidden;">
                <div class="easyui-layout" fit="true">
                    <div region="center" border="false" style="padding:1px;background:#fff;border:1px solid #ccc;">
                        <form id="prolong_price">
                            <fieldset style="border:1px solid #ccc;margin: 0px; padding:0px;">
                                <legend><b>延保价格</b></legend>
                                <table align="center" width="100%" cellpadding="1"> 
                                    <tr>    
                                        <td align="right">延保一年价格： </td>
                                        <td><input type="text" id="oneYearPrice" value="0" style="width:235px;" /></td>
                                    </tr>   
                                    <tr>    
                                        <td align="right">延保两年价格： </td>
                                        <td><input type="text" id="twoYearPrice" value="0" style="width:235px;" /></td>
                                    </tr>   
                                    <tr>    
                                        <td align="right">延保三年价格： </td>
                                        <td><input type="text" id="threeYearPrice" value="0" style="width:235px;" /></td>
                                    </tr>   
                                </table>
                            </fieldset>
                        </form>
                    </div>
                    <div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
                        <a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveProlongCost()">保存</a>
                        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="prolong_prcie_w_close()">关闭</a>
                    </div>
                </div>
            </div>
			
			<!-- 修改 -->
			<div id="priceManage" class="easyui-window" title="报价管理" modal="true" closed="true" style="width:560px;height:550px;visibility: hidden;" 
			 minimizable="false" maximizable="false" collapsible="false" >
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:1px;background:#fff;border:1px solid #ccc;">
					<fieldset style="border:1px solid #ccc;">
						<legend><b>维修信息</b></legend> 
						  <table align="left" cellpadding="1" style="padding-left: 5px;">
							<tr>
								<td align="right" style="width: 75px;">IMEI：</td>
								<td><input type="text" id="repair_imei" disabled="disabled" ></td>
								<td>主板型号 ：</td>      
								<td><input type="text" id="repair_w_model" disabled="disabled" ></td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">智能锁ID：</td>
								<td><input type="text" id="repair_lockId" disabled="disabled" ></td>
								<td>市场型号：</td>
								<td><input type="text" id="price_w_marketModel" disabled="disabled" ></td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">蓝牙ID：</td>
								<td><input type="text" id="repair_bluetoothId" disabled="disabled" ></td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">测试结果：</td>
								<td colspan="3">
									<textarea id="testResult_info" rows="1" disabled="disabled" style="width: 100%; height: 28px;"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">受理备注：</td>
								<td colspan="3">
									<textarea id="acceptRemark" rows="1" disabled="disabled" style="width: 100%; height: 28px;"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">送修备注：</td>
								<td colspan="3">
									<textarea id="sendRemark" rows="1" disabled="disabled" style="width: 100%; height: 28px;"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">批次备注：</td>
								<td colspan="3">
									<textarea id="batchRemark" rows="1" disabled="disabled" style="width: 100%; height: 28px;"></textarea>
								</td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">维修备注：</td>
								<td colspan="3">
									<textarea id="repairRemark" rows="1" disabled="disabled" style="width: 100%; height: 28px;"></textarea>
								</td>
							</tr>		
										
						 </table>
					</fieldset>
					<br>
					<fieldset style="border:1px solid #ccc;"> 
						<legend><b>报价信息</b></legend>
						<input type="hidden" id="rfild">
						<input type="hidden" id="wfid">
						<input type="hidden" id="priceId">
						<table align="left" cellpadding="1" style="padding-left: 5px;">
							<tr >
						    	<td align="right" style="width: 75px;">维修报价：</td>
						    	<td>
						    		<input type="text"  id="repair_w_wxbjDesc" style="width:325px;" readonly="readonly">
							     	<input type="hidden"  id="repair_w_wxbjId">
							     	<label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertWXBJ()">添加</a></label>
						    	</td>
							</tr>
						    <tr>
						    	<td align="right" style="width: 75px;">选择配件料：</td>						    	
								<td>
							     	<input type="text"  id="repair_w_matDesc" style="width:325px;" readonly="readonly"> 
							     	<input type="hidden"  id="repair_w_matId">
							     	<input type="hidden"  id="repair_w_consumption">
							     	<!-- <label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertMat()">添加</a></label> -->
							     </td>
						    </tr>
						    <tr>
						    	<td align="right" style="width: 75px;">选择电子料：</td>
								<td>
							     	<input type="text"  id="repair_w_dzlDesc" style="width:325px;" readonly="readonly"> 
							     	<input type="hidden"  id="repair_w_dzlId">
							     	<!-- <label class="only_show"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="doinsertDzl()">添加</a></label> -->
							     </td>
						    </tr>
							<tr height="25px;">
								<td align="right" style="width: 75px;">放弃报价：</td>
								<td><input type="radio" name="price_w_isRW" value="1" />是
									<input type="radio" name="price_w_isRW" value="0" checked="checked"/>否
								</td>
							</tr>
							<tr>
								<td align="right" style="width: 75px;">报价备注：</td>
								<td colspan="3">
									<textarea rows="3"  id="onePriceRemark" style="width: 100%;"></textarea>
								</td>
							</tr>
					  </table>
					</fieldset>
					<br>
				</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="priceUpdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="priceUpdate()">保存</a>
					<a id="priceClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowPriceClose();">关闭</a>
				</div>
			</div>
		</div>
		
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
			 					<span>&nbsp;编码:&nbsp;<input type="text" class="form-search2" id="wxbjName" style="width:150px"></span>&nbsp; &nbsp;
								<span>&nbsp;关键字:&nbsp;<input type="text" class="form-search2" id="searchKey" style="width:200px" placeholder="报价描述，主板型号"></span>&nbsp; &nbsp; &nbsp; &nbsp;
								<span>&nbsp; &nbsp; 
									<a id="searchWxbjInfo" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageWxbj()">查询</a>
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
									<th field="price" width="100" align="center">报价金额（￥）</th>
									<th field="hourFee" width="100" align="center">工时费（￥）</th>
									<th field="amount" width="220" align="center">报价描述</th>
									<th field="pjlDesc" width="200" align="center">关联配件料</th><!-- 过后删除 -->
									<th field="model" width="100" align="center">主板型号</th>
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
									<th field="proNO" width="100" align="center">物料编码</th>
									<th field="proName" width="120" align="center">物料名称</th>
									<th field="proSpeci" width="220" align="center">规格</th>
									<th field="retailPrice" width="80" align="center">零售价（￥）</th>
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
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('mat_w')">关闭</a>
			    </div>
			</div>
	    </div>
	    <!-- 选择配件料  - End-->
	    
	    <!--  选择电子料 - Start-->
		<div id="dzl_w" class="easyui-window" title="选择电子料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px;visibility: hidden; ">
			<div class="easyui-layout" fit="true">
			    <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="类别">
			       <ul id="typeTree-DZL" class="easyui-tree" animate="true" dnd="false"></ul>
	  			</div>
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<div id="dzToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<span>&nbsp;物料编码:&nbsp;<input type="text" class="form-search4" id="s_proNO_dzl" style="width:180px"></span>&nbsp; &nbsp;
								<span>&nbsp;物料名称:&nbsp;<input type="text" class="form-search4" id="s_proName_dzl" style="width:180px"></span>&nbsp; &nbsp;								
								<span>&nbsp;&nbsp; 
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageDZL(1)">查询</a>
								</span>
							</div>
						</div>
						<table id="dzl_DataGrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" autoRowHeight="true" 
								pagination="true" fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#dzToolbar">
							<thead>
								<tr>
									<th checkbox="true" field="dzlId"></th>
									<th field="proNO" width="100" align="center">物料编码</th>
									<th field="proName" width="120" align="center">物料名称</th>
									<th field="proSpeci"  width="220" align="center">规格</th>
									<th field="placesNO"  width="80" 	align="center">序号</th>
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
	    
	     <!-- 获取需要填写物流费的数据列表 start -->
	  	  <div id="logcost_w" class="easyui-window" title="数据列表" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
			 	 style="width:1010px;height:450px;visibility: hidden; ">
			 	<div class="easyui-layout" style="overflow: hidden; width:100%;height:500px;" fit="true"> 
					<div region="center" id="dic-center" style="overflow:auto;">
					   	<div>
					   		<span>
					   			&nbsp;&nbsp;&nbsp;批次号：<input type="text" id="logcost_repairNumber_search" class="form-search7"/>
					   		</span>
					   		<span>
					   			&nbsp;&nbsp;&nbsp;客户名称：<input type="text" id="logcost_cusName_search" class="form-search7"/>
					   		</span>
					   		<span>
					   			&nbsp;&nbsp;物流支付状态：
					   			<select id="logcost_pay_search"  style="width: 120px;" editable="false" panelHeight="auto">
					   				<option value="" >全部</option>
					   				<option value="1" selected="selected">未支付</option>
					   				<option value="0">已支付</option>
					   				<option value="2">系统默认状态</option>
					   			</select>
					   		</span>
					   		<a id="search_logcost" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
					   		<label style="color: red;" >双击列表填写物流费</label>
					   	</div>
					   	<div>
							<table id="sogcost_table" singleSelect="true"  sortable="true" rownumbers="false" autoRowHeight="true" fitColumns="true" 
									striped="true" pagination="true" pageSize="10">
								<thead>
									<tr>
										<th checkbox="true" field="id" align="center"></th>						
										<th field="repairnNmber" width="250" align="center">批次号</th>
										<th field="cusName" width="200" align="center">客户名称</th>
										<th field="w_phone" width="200" align="center">手机号</th>
										<th field="w_logCost" width="150" align="center">物流费</th>
										<th field="w_batchPjCosts" width="150" align="center">批次配件费</th>
										<th field="w_ratePrice" width="100" align="center">税费</th>
										<th field="w_totalMoney" width="150" align="center">总费用</th>
										<th field="w_customerReceipt" width="160" align="center">客户收货方式</th>
										<th field="w_remAccount" width="200" align="center">付款方式</th>
										<th field="payedLogCost" width="200" align="center">物流费支付状态</th>
										<th field="w_priceRemark" width="200" align="center">备注</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;margin-top: 5px;">
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="logcost_w_close()">关闭</a>
		        </div>
			</div>
		</div>
		 <!-- 获取需要填写物流费的数据列表  end -->	
		 
		 <!-- 填写物流费用 -->
		 <div id="setLogCost" class="easyui-window" title="填写物流费" modal="true" closed="true" style="width:420px;height:450px;visibility: hidden;" 
			 minimizable="false" maximizable="false" collapsible="false" >
			<div class="easyui-layout" fit="true">
				<div region="center" style="padding:2px;background:#fff;border:1px solid #ccc;">
					<div align="right">
						<input type="hidden" id="type_logCost" value="type_logCost"/>
						<input type="button" id="logCost_showPj" onclick="showPj();" value="批次配件料管理" />
					</div>
					<form id="logcost_form">
						<table align="left" cellpadding="2">
						<tr>
							<td align="right">批次号：</td>
							<td><input type="text" id="logCost_repairNumber" style="width:235px;" disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right">客户名称：</td>
							<td><input type="text" id="logCost_cusName" style="width:235px;" disabled="disabled"/></td>
						</tr>
                        <tr>                            
                            <td align="right">物流费：</td>
                            <td><input type="text" id="logCost" style="width:235px;" validType="Amount" class="easyui-validatebox"/></td>
                        </tr>
						<tr>	
						    <td align="right">是否开发票： </td>
							<td align="left">
								<input type="radio" name="bathPrice_receipt_logcost" value="0"/>是
								<input type="radio" name="bathPrice_receipt_logcost" value="1"  checked="checked"/>否
								<input type="text" id="rate_logcost" disabled="disabled" placeholder="税率（整数）" style="width: 35%;"  maxlength="3" validType="Number" class="easyui-validatebox"/>%
							</td>
						</tr>
						<tr>	
						    <td align="right">批次配件费： </td>
							<td><input type="text" id="logCost_pjCost" value="0" style="width:235px;" class="easyui-validatebox" validType="Amount" disabled="disabled"/></td>
						</tr>
						<tr>	
						    <td align="right">税费： </td>
							<td><input type="text" id="logCost_ratePrice" value="0" style="width:235px;" class="easyui-validatebox" validType="Amount" disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right">总费用：</td>
							<td><input type="text" id="logCost_sumMoney" value="0" style="width:235px;" validType="Amount" disabled="disabled"/></td>
						</tr>
						<tr>	
						    <td align="right">客户收货方式： </td>
							<td >
								<input type="radio" name="customer_Receipt_logcost" value="Y" checked="checked"/>顺付
								<input type="radio" name="customer_Receipt_logcost" value="N"/>到付
							</td>
						</tr>
						<tr>
							<td align="right">人工付款： </td>
							<td >
								<input type="radio" name="now_pay" value="1" />是
								<input type="radio" name="now_pay" value="0" checked="checked"/>否
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td><textarea rows="3" style="width:235px;" id="priceMark"></textarea></td>
						</tr>						
					</table>
					</form>					
			 	</div>
			 	<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
					<a id="priceUpdate" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveLogcost()">保存</a>
					<a  class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="setLogCostClose();">关闭</a>
				</div>
			</div>
		 </div>
		 
		 <!-- 查询可报价批次号  start -->
		 <div id="repairNub_w" class="easyui-window" title="可报价批次号" modal="true" closed="true"  maximizable="false" minimizable="false" collapsible="false"
			 	 style="width:700px;height:420px;visibility: hidden; ">
			 	<div class="easyui-layout"  fit="true"> 
					<div region="center" id="dic-center" style="overflow:auto;">
					   	<div>
					   		<span>
					   			&nbsp;&nbsp;&nbsp;客户名称：<input type="text" id="cusName_search" class="form-search8"/>
					   		</span>
					   		<a id="search_repairNumber" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
					   	</div>
					   	<div>
							<table id="repairNub_table" singleSelect="true"  sortable="true" rownumbers="false" autoRowHeight="true" fitColumns="true" 
									striped="true" pagination="false" pageSize="20">
								<thead>
									<tr>					
										<th field="repairnNmber" width="250" align="center">批次号</th>
										<th field="cusName" width="200" align="center">客户名称</th>
									</tr>
								</thead>
							</table>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;margin-top: 5px;">
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="repairNub_w_close()">关闭</a>
		        </div>
			</div>
		</div>
		<!-- 查询可报价批次号  end -->
		
		 <!-- 批次配件料管理 - Start-->
		<div id="batchPj_w" class="easyui-window" title="批次配件料管理" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:850px;height:520px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<div id="pjToolbar" style=" padding: 10px 15px;">
							<div style="margin-bottom:5px">	
								<span>&nbsp;配件编码:&nbsp;<input type="text" class="form-search5"  id="s_proNO_Pj" style="width:150px"></span>
								<span>&nbsp;配件名称:&nbsp;<input type="text" class="form-search5"  id="s_proName_Pj" style="width:150px"></span>							
								<span>&nbsp;
									<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPj();">查询</a>
								</span>
							</div>
							<div>
			                    <table cellspacing="0" cellpadding="0">
									<tbody>
										<tr>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="batchGroupPj_add();">选择组合配件</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="batchPj_add();">增加</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="batchPj_edit();">修改</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="batchPj_del();">删除</a></td>
											<td><div class="datagrid-btn-separator"></div></td>
											<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPj();">刷新</a></td>							
										</tr>
								 	</tbody>
								</table>
							</div>
						</div>
						<table id="batchPj_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" 
							   fitColumns="true"  striped="true" scrollbarSize="0" toolbar="#pjToolbar">
							<thead>
								<tr>
									<th checkbox="true" field="id"></th>
									<!-- <th field="mid" width="110" align="center">物料id</th> -->
									<th field="marketModel" width="110" align="center">市场型号</th>
									<th field="proNO" width="150" align="center">配件编码</th>
									<th field="proName" width="110" align="center">配件名称</th>
									<th field="retailPrice" width="120" align="center">单价（￥）</th>
									<th field="pjNumber" width="120" align="center">数量</th>
									<th field="repairNumber" width="150" align="center">批次号</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeBatchPj_w();">关闭</a>
			    </div>
			</div>
	    </div>
	    <!--批次配件料管理  - End-->
	    
		<!-- 批次报价增加配件料-START--> 
		<div id="batch_choosepj_w" class="easyui-window" title="增加配件料" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:600px;height:450px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" style="overflow:auto">
					<div id="datagrid-groupToolbar">
						<div style="margin-bottom:5px">	
							<span>&nbsp;配件编码:&nbsp;<input type="text" class="form-search6"  id="proNO_Pj" style="width:150px"></span>
							<span>&nbsp;配件名称:&nbsp;<input type="text" class="form-search6"  id="proName_Pj" style="width:150px"></span>								
							<span>&nbsp; 
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPagePj();">查询</a>
							</span>
						</div>
					</div>
					<table id="choosepj_DataGrid" fit="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" fitColumns="true" striped="true" pageSize="30" toolbar="#datagrid-groupToolbar">
							<thead>
							<tr>
								<th checkbox="true" field="id"></th>
								<th field="marketModel" width="110" align="center">市场型号</th>
								<th field="proNO" width="150" align="center">配件编码</th>
								<th field="proName" width="110" align="center">配件名称</th>
								<th field="retailPrice" width="120" align="center">单价（￥）</th>
							</tr>
						</thead>				
					</table>
				</div>
				<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
					<div>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveBatch_choosepj('batchPrice');">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('batch_choosepj_w')">关闭</a>
					</div>
			    </div>
			</div>
	     </div>
	     <!-- 批次报价增加配件料-END-->
	     
	      <!-- 修改配件数量 -->
		 <div id="editPj_w" class="easyui-window" title="修改配件数量" modal="true" closed="true" style="width:350px;height:360px;visibility: hidden;" 
			 minimizable="false" maximizable="false" collapsible="false" >
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<form id="editPj_form">
						<table align="left" cellpadding="2" style="padding-left: 25px;">
							<tr>
								<td><input type="hidden" id="editPj_id"></td>
							</tr>
							<tr>
								<td align="right">批次号：</td>
								<td><input type="text" id="editPj_repairNumber" width="200" disabled="disabled"/></td>
							</tr>
							<tr class="singleSelect_show">
								<td align="right">市场型号：</td>
								<td><input type="text" id="editPj_marketModel" width="200" disabled="disabled"/></td>
							</tr>
							<tr class="singleSelect_show">
								<td align="right">配件编码：</td>
								<td><input type="text" id="editPj_proNO" width="200" disabled="disabled"/></td>
							</tr>
							<tr class="singleSelect_show">
								<td align="right">配件名称：</td>
								<td><input type="text" id="editPj_proName" width="200" disabled="disabled"/></td>
							</tr>	
							<tr class="singleSelect_show">
								<td align="right">单价：</td>
								<td><input type="text" id="editPj_retailPrice" width="200" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段"/></td>
							</tr>
							<tr>
								<td align="right">数量：</td>
								<td><input type="text" id="editPj_pjNumber" width="200" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段"/></td>
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
		 
		 <!-- 批次报价增加销售配件料组合-START--> 
		<div id="batch_chooseGroupPj_w" class="easyui-window" title="选择销售配件料组合" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			  style="width:600px;height:450px;visibility: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="center" style="overflow:auto">
					<div id="datagrid-toolbar">
						<div style="margin-bottom:5px">	
							<span>名称:&nbsp;<input type="text" class="form-search9"  id="groupName_Pj" style="width:150px"></span>&nbsp;
							<span>市场型号:&nbsp;<input type="text" class="form-search9"  id="groupMarketModel_Pj" style="width:150px"></span>&nbsp;								
							<span> 
								<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPageGroupPj('');">查询</a>
							</span>
						</div>
					</div>
					<table id="chooseGroupPj_DataGrid" fit="true" sortable="true" rownumbers="false" 
							autoRowHeight="true" pagination="true" fitColumns="true" striped="true" pageSize="20" toolbar="#datagrid-toolbar">
							<thead>
							<tr>
								<th checkbox="true" field="groupPjId"></th>
								<th field="marketModel" width="110" align="center">市场型号</th>
								<th field="groupName" width="110" align="center">名称</th>
								<th field="groupPrice" width="80" align="center">总价格（￥）</th>
								<th field="remark" width="150" align="center">备注</th>
							</tr>
						</thead>				
					</table>
				</div>
				<div region="south" border="true" style="text-align:right;height:35px;line-height:30px;">
					<div>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveBatch_choosepj('groupPj');">保存</a>
						<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('batch_chooseGroupPj_w')">关闭</a>
					</div>
			    </div>
			</div>
	     </div>
	     <!-- 批次报价增加配件料-END-->
	     
	     <!-- 销售配件料组合详情 - Start-->
		<div id="groupPjDetails_w" class="easyui-window" title="销售配件料组合详情" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" 
			 style="width:500px;height:380px; visibility: hidden; ">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
					<div style="height:280px;">
						<table id="groupPjDetails_DataGrid" fit="true" sortable="true" rownumbers="false" autoRowHeight="true" 
							   striped="true" scrollbarSize="0">
							<thead>
								<tr>
									<!-- <th checkbox="true" field="id"></th> -->
									<!-- <th field="groupId" >groupId</th>
									<th field="pjlId" >pjlId</th> -->
									<th field="proNO" width="150" align="center">编码</th>
									<th field="proName" width="110" align="center">名称</th>
									<th field="retailPrice" width="80" align="center">单价（￥）</th>
									<th field="proNumber" width="80" align="center">数量</th>
									<th field="proSpeci" width="220" align="center">规格</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('groupPjDetails_w')">关闭</a>
			    </div>
			</div>
	    </div>
	    <!--销售配件料组合详情  - End-->
		</div>
		<script type="text/javascript">
			$(function(){
				$(".price_isHidden").hide();
				$("#isLine").show();

				$("#bathPrice_logCost").blur(function(){
					if($("#rate").val() && $("#rate").length > 0 && $('#rate').validatebox("isValid") ){
						$("#bathPrice_totalMoney").val((($("#rate").val()/100+1)*($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1)).toFixed(2));
						$("#bathPrice_ratePrice").val(($("#bathPrice_totalMoney").val()*1-$("#bathPrice_logCost").val()*1-$("#bathSumRepairPrice").val()*1-$("#bathPrice_pjCost").val()*1-$("#bathPrice_prolongCost").val()*1).toFixed(2));
					}else{
						$("#bathPrice_totalMoney").val(($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1).toFixed(2));
						$("#bathPrice_ratePrice").val(0);
					}
				});

				$("#rate").blur(function(){
					if($("#rate").val() && $("#rate").length > 0 && $('#rate').validatebox("isValid") ){
						$("#bathPrice_totalMoney").val((($("#rate").val()/100+1)*($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1)).toFixed(2));
						$("#bathPrice_ratePrice").val(($("#bathPrice_totalMoney").val()*1-$("#bathPrice_logCost").val()*1-$("#bathSumRepairPrice").val()*1-$("#bathPrice_pjCost").val()*1-$("#bathPrice_prolongCost").val()*1).toFixed(2));
					}else{
						$("#bathPrice_totalMoney").val(($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1).toFixed(2));
						$("#bathPrice_ratePrice").val(0);
					}	
				});

				$("#bathPrice_pjCost").change(function(){
					if($("#rate").val() && $("#rate").length > 0 && $('#rate').validatebox("isValid") ){
						$("#bathPrice_totalMoney").val((($("#rate").val()/100+1)*($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1)).toFixed(2));
						$("#bathPrice_ratePrice").val(($("#bathPrice_totalMoney").val()*1-$("#bathPrice_logCost").val()*1-$("#bathSumRepairPrice").val()*1-$("#bathPrice_pjCost").val()*1-$("#bathPrice_prolongCost").val()*1).toFixed(2));
					}else{
						$("#bathPrice_totalMoney").val(($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1).toFixed(2));
						$("#bathPrice_ratePrice").val(0);
					}
				});							

				//填写费率自动计算报价费用
				$("#rate").blur(function(){
					getRatePrice();					
				});			 
			});
		</script>
	    <!-- ============================== 批次报价-END ==============================-->
	</div>
</body>
</html>