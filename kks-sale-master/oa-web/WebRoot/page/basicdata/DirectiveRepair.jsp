<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/directiverepair.js"></script>
<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
</head>
<body style="margin:0">
	<div class="easyui-layout" style="overflow: hidden; width:100%;" fit="true" >
		<div region="center" id="dic-center" style="overflow:auto" title="管理员查询">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<span> 工站:&nbsp; 
					<select id="workstation" class="easyui-combobox" style="width: 150px;" editable="false" panelHeight="auto">
							<option value="0">受理</option>
							<option value="1">报价</option>
							<option value="2">维修</option>
							<option value="3">终检</option>
							<option value="4">装箱</option>
					</select>&nbsp;
					</span> 
					<span>&nbsp;操作人员:&nbsp;<input type="text" id="operator" style="width: 180px" /></span>
					<span>
						开始日期: 
						<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 180px"> 
					</span> &nbsp;
					<span>
						结束日期: 
						<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 180px"> 
					</span>
					<span>&nbsp; &nbsp; <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a></span>
				</div>
			
			</div>
			<table id="DataGrid1" singleSelect="true" rownumbers="false" autoRowHeight="true" pagination="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>					
						<th field="w_cusName" width="150" align="center">送修单位</th>
						<th field="w_model" width="150" align="center">主板型号</th>
						<th field="imei" width="130" align="center">IMEI</th>
						<th field="remark" width="130" align="center">送修备注</th>							
						<th field="w_cjgzDesc" width="200" align="center">初检故障</th>
						<th field="acceptanceTime" width="150" align="center">受理时间</th>
						<th field="w_zzgzDesc" width="200" align="center">最终故障</th>
						<th field="w_solution" width="120" align="center">处理方法</th>
						<th field="returnTimes" width="120" align="center">返修次数</th>
						<th field="w_engineer" width="120" align="center">操作人员</th>
						<th field="w_sjfjDesc" width="150" align="center">附件</th>
						<th field="w_ispass" width="120" align="center">终检结果</th>																								
						<th field="w_packTime" width="150" align="center">装箱时间</th>
						<th field="w_expressNO" width="140" align="center">快递单号</th>
						<th field="w_expressCompany" width="120" align="center">快递公司</th>
						<th field="state"  width="180px"  align="center">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>
</body>
</html>