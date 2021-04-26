<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/workflow/a.atree/worktimeTree.js?20170831"></script>
<script type="text/javascript" src="${ctx}/res/js/workflow/test/workTest.js?20170831"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/material/changeboard_com.js"> </script>

</head>
<body style="margin:0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/>
	<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
	<div class="easyui-layout" style="overflow: hidden; width:100%;" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow:auto" title="报价列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">
						<span>
							&nbsp;设备IMEI:
							<input type="text" class="form-search" id="imei" style="width: 135px">
						</span>
						<span>
							&nbsp;开始日期:  
							<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
						</span>&nbsp;
						<span>
						处理状态: 
						<select id="testState" style="width: 110px;" editable="false" panelHeight="auto" >
							<option value = 0>全部</option>
							<option value = 1 selected="selected">待处理</option>
							<option value = 2>已完成</option>
						</select>&nbsp;
						</span>
					</div>
					<div style="margin-bottom:5px">
						<span>
							&nbsp;智能锁ID:
							<input type="text" class="form-search" id="lockId" style="width: 135px">
						</span>
						<span>
							&nbsp;结束日期: 
							<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"> 
						</span> &nbsp;
						<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>				
					</div>
					<div style="margin-bottom: 5px;">
					<div style="padding-top: 2px; display: none;" id="showTip"> 
				 	  <span class="l-btn-text icon-tip" style="padding-left: 20px; color: red;">您有<label style="color: green;">&nbsp;<span class="number"></span>&nbsp;</label>条新数据已推送到,请刷新后及时处理数据 !</span>
					</div>
				</div>
				<div>
                    <table cellspacing="0" cellpadding="0">
						<tbody>
						<tr>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="showInfo();">填写测试结果</a></td>
							<td><div class="datagrid-btn-separator"></div></td>	
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="getData();">获取数据</a></td>
							<td><div class="datagrid-btn-separator"></div></td>	
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-return" plain="true" onclick="sendData();">返回数据来源站</a></td>
							<td><div class="datagrid-btn-separator"></div></td>                                                          
                            <td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-wxicon" plain="true" onclick="notPrice()">不报价</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-wlsqicon" plain="true" onclick="changeboard('workTest');">保内对换主板</a></td>
							<td><div class="datagrid-btn-separator"></div></td>
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-plfszfjicon" plain="true" onclick="sendToOther();">发送给其他人</a></td>
							<td><div class="datagrid-btn-separator"></div></td>															
							<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>							
						</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			<table id="DataGrid1"  fit="true" rownumbers="false" pagination="true" pageSize="20" autoRowHeight="true" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="tid" align="center">testId</th>
						<th field="id" align="center" hidden="true" >id</th>
						<th field="acceptanceTime" width="130" align="center">受理日期</th>
						<th field="testPerson"  width="80" align="center">当前测试员</th>
						<th field="dataSource"  width="80" align="center">数据来源站</th>	
						<th field="testStatus"  width="130" align="center">进度</th>	
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="lockId" width="80" align="center">智能锁ID</th>	
						<th field="w_cusName"  width="150" align="center">客户名称</th>
						<th field="w_phone"  width="100" align="center">客户手机号</th>
						<th field="repairnNmber" width="110" align="center">送修批号</th>
						<th field="testResult"  width="150" align="center">测试结果</th>
						<th field="w_model"  width="150" align="center">市场型号|主板型号</th>	
						<th field="insideSoftModel" width="100" align="center">内部机型</th>
						<th field="w_sjfjDesc" width="160" align="center">随机附件</th>
						<th field="isWarranty"  width="80" align="center">保内|保外</th>	
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
						<th field="w_cjgzDesc"  width="150" align="center">初检故障</th>	
						<th field="remark"  width="150" align="center">送修备注</th>	
						<th field="acceptRemark"  width="150" align="center">受理备注</th>
						<th field="repairNumberRemark" width="120" align="center">批次备注</th>
						<th field="accepter"  width="80" align="center">受理人员</th>	
						<th field="w_wxbjDesc"  width="150" align="center">维修报价描述</th>	
						<th field="w_zzgzDesc"  width="150" align="center">最终故障</th>	
						<th field="w_solution"  width="150" align="center">处理措施</th>												
						<th field="w_engineer"  width="80" align="center">维修人员</th>			
						<th field="w_isRW"  width="80" align="center">是否人为</th>
						<th field="dataSourceCode" hidden="true">进度状态码</th>							
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- 填写测试结果  Start-->
		<div id="w_testDesc" class="easyui-window" title="填写测试结果" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"  style="visibility:hidden; width: 1050px; height: 450px; padding: 2px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
					<input type="hidden" id="id"/>
					<table>
						<tr>
							<td align="right">IMEI：</td>
							<td><input type="text" id="imei_info" style="width: " disabled="disabled"/></td>
							<td align="right">智能锁ID：</td>
							<td><input type="text" id="lockId_info" style="width: " disabled="disabled"/></td>
							<td align="right">蓝牙ID：</td>
							<td><input type="text" id="bluetoothId_info" style="width: " disabled="disabled"/></td>
							<td align="right">送修批号：</td>
							<td><input type="text" id="repairnNumber_info" style="width: " disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right">数据来源站：</td>
							<td><input type="text" id="dataSource_info" style="width: " disabled="disabled"/></td>
							<td align="right">受理人员：</td>
							<td><input type="text" id="accepter_info" style="width: " disabled="disabled"/></td>
							<td align="right">维修人员：</td>
							<td><input type="text" id="engneer_info" style="width: " disabled="disabled"/></td>
							<td align="right">主板型号：</td>
							<td><input type="text" id="model_info" style="width: " disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right">客户名称：</td>
							<td><input type="text" id="cusName_info" style="width: " disabled="disabled"/></td>
							<td align="right">手机号：</td>
							<td><input type="text" id="phone_info" style="width: " disabled="disabled"/></td>
							<td align="right">保内/保外：</td>
							<td><input type="text" id="isWarranty_info" style="width: " disabled="disabled"/></td>
							<td align="right">市场型号：</td>
							<td><input type="text" id="modelMarket_info" style="width: " disabled="disabled"/></td>
						</tr>
						<tr>
							<td align="right">受理备注：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="acceptRemark_info" disabled="disabled"></textarea>
							</td>
							<td align="right">送修备注：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="remark_info" disabled="disabled"></textarea>
							</td>							
						</tr>
						<tr>
							<td align="right">批次备注：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="repairNumberRemark_info" disabled="disabled"></textarea>
							</td>
							<td align="right">初检故障：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="cjDesc_info" disabled="disabled"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">维修报价描述：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="repariPriceDesc_info" disabled="disabled"></textarea>
							</td>
							<td align="right">最终故障：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="zgDesc_info" disabled="disabled"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">维修处理措施：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="sulotion_info" disabled="disabled"></textarea>
							</td>
							<td align="right">随机附件：</td>
							<td colspan="3">
								<textarea rows="2" cols="60" id="sjfj_info" disabled="disabled"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">测试结果描述：</td>
							<td colspan="7">
								<textarea rows="5" cols="143" id="testResult_info"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div region="south" border="false" style="text-align: right; " id="showSaveBut">
					<a id="resultSave" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">保存</a>
					<a id="resultClose" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" >关闭</a>
				</div>
			</div>
		</div>
		<!-- END -->
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
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="changeboardAdd('test')">保存</a>
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="changeboard_close();">关闭</a>
				</div>
			</div>
		</div>
		<!-- 保内对换主板  - End-->
	</div>
</body>
</html>