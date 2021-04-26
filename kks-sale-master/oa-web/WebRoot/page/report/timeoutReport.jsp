<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/timeoutReport.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/res/js/report/dateTree/dateTree.js"></script>
</head>
<body style="margin: 0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/> 
	<div class="easyui-layout" style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="超3天机器未寄出报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 2px">
				    <span>
                        &nbsp;设备IMEI:&nbsp;
                       <input type="text" class="form-search" id="imei" style="width:135px ">
                        &nbsp;
                    </span>
					<span>&nbsp;开始日期:&nbsp;<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>
					<span>&nbsp;送修单位: <input type="text" class="form-search" id="searchCusName" style="width: 150px"> </span>&nbsp;
					<span>
						状态:
						<select id="timeoutState" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value="0">全部</option>
							<option value="1" selected="selected">未完成</option>
							<option value="2">已完成</option>
						</select>&nbsp;
					</span>
				</div>
				<div style="margin-bottom: 2px">
					<span>&nbsp;结束日期:&nbsp;<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>
					<span>&nbsp;送修批次: <input type="text" class="form-search" id="searchRepairnNmber" style="width: 150px"> </span>&nbsp;
					<span>
						进度:
						<select id="repairNumberState" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
							<option value="全部">全部</option>
						</select>&nbsp;

					</span>
					<span>
						工站:
						<select id="workStation"  class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
							<option>全部</option>
							<option value="1" >受理</option>
							<option value="2" >分拣</option>
							<option value="3" >维修</option>
							<option value="4" >报价</option>
							<option value="5" >终检</option>
							<option value="6" >测试</option>
                        	<option value="7" >装箱</option>
						</select>&nbsp;
					</span>
					<span>
						维修人员:
						<select id="engineer"  class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto">
							<option>全部</option>
						</select>&nbsp;
						<img>
					</span>
					<span>
						超天原因:
						<select id="timeoutReasonState"  class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto">
							<option value="2">全部</option>
							<option value="0">未填</option>
							<option value="1">已填</option>
						</select>&nbsp;
						<img>
					</span>
					<span><a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)">查询</a> </span>
				</div>
				<div>
				<table cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="timeout_updateInfo();">修改</a>
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
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="flushAll();">刷新超期时间</a>
						</td>
						<td>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="flushBackTime();">刷新应返还时间</a>
						</td>
					</tr>
				</table>
				</div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="true" fit="true" striped="true"  scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="cusName" width="120" align="center">送修单位</th>
						<th field="repairnNmber" width="120" align="center">批次号</th>
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="model" width="80" align="center">主板型号</th>
						<th field="acceptanceTime" width="130" align="center">送修日期</th>
						<th field="timeoutBackTime" width="130" align="center">应返回日期</th>
						<th field="sendPackTime" width="130" align="center">发送装箱时间</th>
						<th field="engineer" width="80" align="center">维修人</th>
						<th field="timeoutDays" width="60" align="center">超/天</th>
						<th field="state" width="120" align="center">状态描述</th>
						<th field="workStation" width="120" align="center">工站</th>
						<th field="timeoutReason" width="150" align="center">超期原因</th>
						<th field="dutyOfficer" width="80" align="center">责任人</th>
						<th field="sendFicheckTime" width="130" align="center">发送终检时间</th>
						<th field="payTime" width="130" align="center">客户付款日期</th>
						<th field="finChecker" width="80" align="center">终检人</th>
						<th field="timeoutRemark" width="150" align="center">备注</th>
					</tr>
				</thead>
			</table>
		</div>
				<div style="position:fixed;">	
			<div id="timeout_w" class="easyui-window" title="修改信息" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 500px; height: 300px; padding: 5px;">
				<div class="easyui-layout" fit="true">
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; overflow: hidden">
						<form id="mform" action="">
							<table align="center">
								<tr style="display: none">
									<td>
										<input type="hidden" id="id_hidden" style="width: 250px;" />
									</td>
								</tr>
								<tr class="singleShow">
									<td>送修批次：</td>
									<td>
										<input type="text" id="repairnNmber" style="width: 250px;" disabled="disabled" />
									</td>
								</tr>
								<tr class="singleShow">
									<td>&nbsp;&nbsp;&nbsp;I&nbsp;M&nbsp;E&nbsp;I：</td>
									<td>
										<input type="text" id="imei" style="width: 250px;" disabled="disabled" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;责&nbsp;任&nbsp;人：</td>
									<td>
										<input type="text" id="dutyOfficer" style="width: 250px;" />
									</td>
								</tr>
								<tr>
									<td>超期原因：</td>
									<td colspan="7">
<%--										<textarea id="timeoutReason" style="width: 100%; height: 50px;" maxlength="255"></textarea>--%>
										<select id="timeoutReason" style="width: 200px;" class="easyui-combobox" editable="true"></select>
									</td>
								</tr>
								<tr>
									<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
									<td colspan="7">
										<textarea id="timeoutRemark" style="width: 100%; height: 50px;" maxlength="255"></textarea>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
						<a id="timeout_save" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="timeout_save();">保存</a> 
						<a id="windowClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('timeout_w')">关闭</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>