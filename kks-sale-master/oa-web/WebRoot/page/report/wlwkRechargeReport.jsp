<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/wlwkRechargeReport.js?20170220"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" scroll="no" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="物联网卡充值报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 2px">
					<span>开始日期：<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span>&nbsp;
					<span>客户名称：<input type="text" class="easyui-validatebox form-search"  id="search_cusName" style="width: 135px;"></span>&nbsp;
					<span>套餐：
						<select id="search_formula" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" multiple="true">
							<option value="" selected="selected">全部</option>
							<option value="5M">5M</option>
							<option value="10M">10M</option>
							<option value="30M">30M</option>
						</select>
					</span>&nbsp;
					<span>汇款方式：
						<select id="remittanceMethodSearch" class="easyui-combobox form-search" style="width: 100px;" editable="true" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="personPay">人工付款</option>
							<option value="aliPay">支付宝付款</option>
							<option value="weChatPay">微信付款</option>
						</select>&nbsp;
					</span>
				</div>
				<div style="margin-bottom: 5px">	
					<span>结束日期：<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"></span>&nbsp;
					<span>&nbsp;手&nbsp;机&nbsp;号：<input type="text" class="form-search" id="phoneNumber" style="width: 135px"></span>&nbsp;
					<span>状态：
						<select id="search_status" class="easyui-combobox form-search" style="width: 100px;" editable="false" panelHeight="auto" >
							<option value="" selected="selected">全部</option>
							<option value="已充值">已充值</option>
							<option value="待充值">待充值</option>
						</select>
					</span>&nbsp;
					<span style="width: 200px"></span>&nbsp; 
					<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('','search')">查询</a>
					<a id="bathSearchInfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="batchSearch()">批量查询</a>
				</div>
				<div>
					<!-- datagrid-btn-separator 分割线 -->
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-addicon" plain="true" onclick="wlwk_recharge_addInfo();">增加</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="wlwk_recharge_updateInfo();">修改</a>
							</td>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="wlwk_recharge_deleteInfo();">删除</a>
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
						<th checkbox="true" field="wlwkId"></th>
						<th field="rechargeDate" width="150" align="center">充值日期</th>
						<th field="cusName" width="150" align="center">客户名称</th>
						<th field="phone" width="110" align="center">手机号</th>
						<th field="formula" width="60" align="center">套餐</th>
						<th field="faceValue" width="90" align="center">充值面值(￥)</th>						
						<th field="monthNumber" width="60" align="center">月数</th>
						<th field="payable" width="100" align="center">收客户费用(￥)</th>
						<th field="payOther" width="100" align="center">需付给卡商(￥)</th>
						<th field="ratePrice" width="80" align="center">税费(￥)</th>
						<th field="orderStatus" width="60" align="center">状态</th>						
						<!-- <th field="account" width="100" align="center">收款账号</th> -->
						<th field="remittanceMethod" width="80" align="center">汇款方式</th>
						<th field="remark" width="150" align="center">备注</th>
					</tr>
				</thead>
			</table>
			<div style="height: 200px;">
				<table id="DataGrid2" class="easyui-datagrid" border="false">
					<thead>
						<tr>
							<th colspan="2" align="center">费用账单</th>
						</tr>
						<tr>
							<th field="projects" width="100" align="center">项目</th>
							<th field="costs" width="120" align="center">费用(￥)</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<div style="position:fixed;">
			<div id="updateWindow" class="easyui-window" title="更新物联网卡充值信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden;width: 500px; height: 460px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="wlwkId" style="width: 250px;" />
									</td>
								</tr>
								<tr id = "id" style="display: none">
									<td>&nbsp;&nbsp;&nbsp;充值日期：</td>
									<td>
										<input type="text" id="rechargeDate" style="width: 250px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" />
									</td>
								</tr>
								
								<tr>
									<td>&nbsp;&nbsp;&nbsp;客户名称：</td>
									<td>
										<input type="text" id="cusName" style="width: 250px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;手&nbsp;机&nbsp;号：</td>
									<td>
										<input type="text" id="phone" style="width: 250px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;套&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;餐：</td>
									<td>
										<input type="text" id="formula" style="width: 150px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;充值面值：</td>
									<td>
										<input type="text" id="faceValue" style="width: 150px;" class="easyui-validatebox" validType="Amount"  required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</td>
									<td>
										<input type="text" id="monthNumber" style="width: 150px;" class="easyui-validatebox" validType="Number" required="true" missingMessage="请填写此字段" />个月
									</td>
								</tr>
								<tr>
									<td>收客户费用：</td>
									<td>
										<input type="text" id="payable" style="width: 150px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>需付给卡商：</td>
									<td>
										<input id="payOther" style="width: 150px;" class="easyui-validatebox" validType="Amount" required="true" missingMessage="请填写此字段" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</td>
									<td>
										<input id="ratePrice" style="width: 150px;" class="easyui-validatebox" validType="Amount"/>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
									<td>
										<select id="orderStatus" class="easyui-combobox form-search" style="width: 150px;" editable="false" panelHeight="auto">
											<option value="已充值" selected="selected">已充值</option>
											<option value="待充值">待充值</option>
										</select>
									</td>
								</tr>
						<!-- 		<tr>
									<td>&nbsp;&nbsp;&nbsp;收款账号：</td>
									<td>
										<input id="account" style="width: 250px;" class="easyui-validatebox" required="true" missingMessage="请填写此字段" />
									</td>
								</tr> -->
								<tr>
									<td>&nbsp;&nbsp;&nbsp;汇款方式：</td>
									<td>
										<select id="remittanceMethod" class="easyui-combobox form-search" style="width: 150px;" editable="true" panelHeight="auto" >
											<option value="" selected="selected">全部</option>
											<option value="personPay">人工付款</option>
											<option value="aliPay">支付宝付款</option>
											<option value="weChatPay">微信付款</option>
										</select>
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td>
										<textarea id="remark" style="width: 250px;"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="recharge_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="wlwk_rechargeSave()">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="updateWindowClose()">关闭</a>
					</div>
				</div>
			</div>
			
			<div id="batchSearchWindow" class="easyui-window" title="批量查询" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 450px; height: 450px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<textarea id="searchNO" style="width:400px; height: 350px;"></textarea>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="recharge_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="queryListPage('','batch')">查询</a>
						 <a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeBatchSearch();">取消</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>