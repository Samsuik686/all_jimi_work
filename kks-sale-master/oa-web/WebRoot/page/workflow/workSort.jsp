<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%-- <script type="text/javascript" src="${ctx}/res/js/workflow/sort/sort-userManage.js"></script> --%>
<script type="text/javascript" src="${ctx}/res/js/workflow/sort/sort.js?20171012"></script>
<script type="text/javascript" src='${ctx}/res/script/windowReset.js'></script>
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

/* 弹出框右上角的关闭图标隐藏 */
.panel-tool-close{
	display: none;
}
</style>

</head>
<body style="margin:0">
	<input type="hidden" id="tree-Date"/> 
	<input type="hidden" id="tree-State"/> 
	<div class="easyui-layout"	style="overflow: hidden; auto; width: 100%; height: 100%" fit="true">
		<div region="west" id="dic-west" style="width: 150px;" title="选择日期"> 
			<ul id="typeTreeTime" class="easyui-tree">
			</ul>
		</div>
		<div region="center" id="dic-center" style="overflow: auto" title="分拣列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">	
						<span>&nbsp;设备IMEI:&nbsp;<input type="text" class="form-search" id="imei" style="width:135px"></span>&nbsp;
						<span>&nbsp;送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmber" style="width:135px"></span>&nbsp;
						<span>&nbsp;主板型号:&nbsp;<input type="text" class="form-search" id="w_model" style="width:135px"></span>&nbsp;
						<span>&nbsp;处理状态:
							<select id="sortState" class="easyui-combobox form-search" style="width: 135px;" editable="false" panelHeight="auto" >
								<option value="">全部</option>
								<option value="0" selected="selected">待处理</option>
								<option value="1">已完成</option>
							</select>&nbsp;
						</span>
					</div>	
					<div style="margin-bottom: 2px;">
						<span>&nbsp;智能锁ID:&nbsp;<input type="text" class="form-search" id="lockId" style="width:135px"></span>&nbsp;
						<span>&nbsp;送修单位:&nbsp;<input type="text" class="form-search" id="w_cusName" style="width:135px"></span>&nbsp;
						<span>&nbsp;市场型号:&nbsp;<input type="text" class="form-search" id="w_modelType"  style="width:135px"></span>&nbsp;
						<span>&nbsp;初检故障:&nbsp;<input type="text" class="form-search" id="w_cjgzDesc" style="width:135px"></span>&nbsp;
					</div>
					<div style="margin-bottom: 5px;">
						<span>&nbsp;开始日期:&nbsp;<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>&nbsp;				
						<span>&nbsp;结束日期:&nbsp;<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 135px"></span>&nbsp;
						<span>&nbsp;
							<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="javascript:void(0)">查询</a>
						</span>
					</div>
					<div style="margin-bottom: 5px;">
					<div style="padding-top: 2px; display: none;" id="showTip"> 
					   <span class="l-btn-text icon-tip" style="padding-left: 20px; color: red;">您有<label style="color: green;">&nbsp;<span class="number">7</span>&nbsp;</label>条新数据已推送到,请刷新后及时处理数据 !</span>
					</div>				
				</div>
				
				<div>
					<table cellspacing="0" cellpadding="0" width="100%;">
						<tbody>
							<tr>
								<td  style="width:140px;"><span style="font-size: 14px;">【共<label style="color: green;">&nbsp;<span id="acc_number" style="font-weight: bold; font-size: 16px;">0</span>&nbsp;</label>条记录】</span></td>
								<td><a href="javascript:;" id="returnAccount" class="easyui-linkbutton" iconCls="icon-return" plain="true" >返回受理工站</a></td>
								<td>
								<span ><strong>扫描IMEI|智能锁ID</strong> &nbsp;<input type="text" style="width:220px; height:28Px; font-size:16px; font-weight:bold; color:#0066FF;" id="scanimei" onkeypress="if(event.keyCode==13) {scancData(event)}"/></span>
								<span><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage(-1);">刷新</a></span>
								</td>						
							</tr>
					 	</tbody>
					</table>
                </div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid"  singleSelect="true" fit="true" rownumbers="false" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="lockInfo" hidden="true"></th>
						<th field="acceptanceTime" width="130" align="center">受理时间</th>
						<th field="imei" width="120"   align="center">IMEI</th>
						<th field="lockId" width="80" align="center">智能锁ID</th>
						<th field="state"    width="100"  align="center">进度</th>
						<th field="testMatStatus" width="80" align="center"><font color="#0000FF">试流料</font></th>
						<th field="lastEngineer"  width="80" align="center">上次维修人员</th>
						<th field="lastAccTime"  width="130" align="center">上次受理时间</th>
						<th field="w_model"  width="130" align="center">主板型号 |市场型号</th>
						<th field="insideSoftModel" width="100" align="center">内部机型</th>
						<th field="isWarranty" width="80" align="center">保内|保外</th>
						<th field="w_cjgzDesc"  width="150" align="center">初检故障</th>
						<th field="bluetoothId" width="100" align="center">蓝牙ID</th>
						<th field="w_cusName" width="150"  align="center">客户名称</th>
						<th field="backDate" width="80" align="center">预计返还日期</th>					
						<th field="remark" width="120"  align="center">送修备注</th>
						<th field="returnTimes"  width="80"   align="center">返修次数</th>
						<th field="repairnNmber" width="110"  align="center">送修批号</th>
						<th field="salesTime"  width="130"   align="center">出货日期</th>
						<th field="w_sjfjDesc" width="150"  align="center">随机附件</th>
						<th field="repairNumberRemark" width="150"  align="center">批次备注</th>
						<th field="acceptRemark"  width="150"  align="center">受理备注</th>
						<th field="remark" width="150"  align="center">送修备注</th>		
						<th field="testResult" width="150"  align="center">测试结果</th>						
					</tr> 
				</thead>
			</table>
		</div>
		
		<!-- 选择维修人员 -START-->
		<div id="w_repair" class="easyui-window" title="选择初检故障" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			 style="width:750px;height:500px;">
			<div class="easyui-layout" fit="true">
				  <div region="west" id="dic-west" style="width: 200px;overflow:auto;" title="人员列表" >
		       			<ul id="roleManageShowOrganizationId" class="easyui-tree" animate="false" dnd="false"></ul>
		  		  </div> 
				  <div region="center" id="dic-center" style="overflow:auto" title="用户信息">
						<div style="width: auto;">
						   <div>
							<span>用户名:&nbsp;<input type="text" id="uId" style="width:200px"></span>
							<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryUserRoleListPage()">查询</a>
						  </div>
						  <div>
						    
						  </div>
						</div>
						<input type="hidden" id="devId"/> 
						<table id="Table_User" singleSelect="false" sortable="true" rownumbers="false" autoRowHeight="true" pagination="false" fitColumns="true" scrollbarSize="0">
							<thead>
								<tr>
									<th field="uId" checkbox="true" ></th>
									<th field="uName" width="220" align="center" >用户名</th>
									<th field="uManagerName" width="245" align="center" >主管</th>
									<th field="oName" width="220" align="center" >部门</th>
									<th field="uPosition" width="220" align="center" >职务</th>
								</tr>
							</thead>
						</table>
				  </div>
				<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
					<a id="treeAdd" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="InfoSave()">保存</a> 
					<a id="treeDelete" class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="windowCommClose('w_repair')">关闭</a>
				</div>
			</div>
		 </div>
	     <!-- 选择维修人员 -END-->
	</div>
	<script type="text/javascript">
		// 默认情况下,数据/元素不能在其他元素中被拖放。
		document.addEventListener("dragover", function(event) { 
	    	event.preventDefault(); 
		}); 
		
	</script>
</body>
</html>