<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/repairDetailReport.js?20161229"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>

</head>
<body style="margin: 0">
	<div class="easyui-layout"	style="overflow: hidden; width: 100%; height: 100%" fit="true">
		<div region="center" id="dic-center" style="overflow:auto" 	title="维修总明细报表   <span style='color:red'> 注：查询请先选择开始结束日期<span>" >
			<div id="toolbar" style="padding:10px 15px;">
			 <div style="margin-bottom:2px">
			    <span>开始日期:&nbsp;<input type="text" class="form-search" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/>&nbsp;</span>
				<span>送修批号:&nbsp;<input type="text" class="form-search" id="repairnNmberSearch"  style="width:110px"/></span>&nbsp;
				<span>放弃报价:
					<select id="isPrice"  style="width: 100px;" editable="false" panelHeight="auto" >
						<option value="" selected="selected">全部</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</span>&nbsp;
				<span>&nbsp;&nbsp;&nbsp;保内 | 保外:
					<select id="isWarranty"  style="width: 135px;" editable="false" panelHeight="auto" >
						<option value="">全部</option>
						<option value="0" selected="selected">保内</option>
						<option value="1">保外</option>
					</select>
				</span>&nbsp;
				<span>&nbsp;查询条件:
					<select id="repairDetailFlag"  style="width: 110px;" editable="false" panelHeight="auto" >
						<option value="defalut" selected="selected">默认</option>
						<option value="">二次返修</option>
					</select>
				</span>
				 <span>&nbsp;查询时间:
					<select id="repairTimeType"  style="width: 110px;" editable="false" panelHeight="auto" >
						<option value="0" selected="selected">受理</option>
						<option value="1">取机</option>
						<option value="2">发送装箱时间</option>
					</select>
				</span>
				 &nbsp;
			</div>
			<div>
			    <span>结束日期:&nbsp;<input type="text" class="form-search" id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 110px"/>&nbsp;</span>				
				<span>送修单位:&nbsp;<input type="text" class="form-search" id="cusNameSearch" style="width:110px"/></span>&nbsp;
				<span>免费维修:
					<select id="freeRepair"  style="width: 100px;" editable="false" panelHeight="auto" >
						<option value="" selected="selected">全部</option>
						<option value="0" >是</option>
						<option value="1">否</option>
					</select>
				</span>&nbsp;
				<span>二次返修次数:
					<select id="compare"  style="width: 90px;" editable="false" panelHeight="auto" >
						<option value="" selected="selected">等于</option>
						<option value="0" >大于</option>
						<option value="1">小于</option>
						<option value="2">大于等于</option>
						<option value="3">小于等于</option>
					</select>&nbsp;
					<input type="text" class="form-search" id="returnTimesSearch"  style="width:40px"/>
				</span>&nbsp;
				<span>主板型号:
					<input type="text" class="form-search" id="modelSearch"  style="width:110px"/>
				</span>
<%--				<span>终检人:--%>
<%--					<select id="finChecker"  style="width: 90px;" editable="false" panelHeight="auto" >--%>
<%--						<option value="">全部</option>--%>
<%--					</select>--%>
<%--				</span>--%>
				<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
			 </div>
			 <div>
			      <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportInfo();">导出</a> 
			 </div>
			 
			</div>
			<table id="DataGrid1" class="easyui-datagrid" singleSelect="true" sortable="true" rownumbers="false" pagination="true" pageSize="20" fit="true" fitColumns="false" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th field="repairnNmber" width="150" align="center">送修批号</th>
						<th field="bill" width="130" align="center">订单号</th>
						<th field="cusName" width="150" align="center">送修单位</th>
						<th field="model" width="110" align="center">主板型号</th>
						<th field="createTypeName" width="110" align="center">创建类型</th>
						<th field="imei" width="130" align="center">IMEI</th>
						<th field="returnTimes" width="80" align="center">返修次数</th>
						<th field="isWarranty" width="80" align="center">保内/保外</th>
						<th field="remark" width="150" align="center">送修备注</th>
						<th field="cjgzDesc" width="180" align="center">初检故障现象</th>
						<th field="acceptanceTime" width="150" align="center">受理时间</th>
						<th field="zzgzType" width="180" align="center">故障类别</th>
						<th field="zzgzDesc" width="180" align="center">最终故障</th>
						<th field="dealMethod" width="150" align="center">处理措施</th>
<%--						<th field="solutionTwo" width="150" align="center">处理措施2</th>--%>
						<th field="repairRemark" width="150" align="center">维修备注</th>
						<th field="sendPackTime" width="150" align="center">发送装箱时间</th>
						<th field="packTime" width="150" align="center">取机时间</th>
						<th field="engineer" width="80" align="center">维修工程师</th>
						<th field="finChecker" width="80" align="center">终检人</th>
						<th field="testResult" width="80" align="center">测试结果</th>
						<th field="testPerson" width="80" align="center">测试员</th>
						<th field="isPrice" width="80" align="center">放弃报价</th>
						<th field="state" width="110" align="center">进度</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>