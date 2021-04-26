<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<jsp:include page="/page/common/import.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript" src='${ctx }/res/script/windowReset.js'></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/material/MaterialLogTemp.js?20170220"></script>
</script>
</head>
<body style="margin: 0">
	<input type="hidden" id="curLoginUser" value="${USERSESSION.uName}"/>
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow: auto"
			title="出入库导入管理列表">
			<div id="toolbar" style=" padding: 10px 15px;">
				<div style="margin-bottom:2px">
					<span>&nbsp;开始日期: <input type="text" class="form-search" id="outTimeStart" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>&nbsp;
					<span>&nbsp;结束日期: <input type="text" class="form-search" id="outTimeEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 115px"></span>&nbsp;
					<span>&nbsp;<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage(1)">查询</a> </span>				
				</div>
				
				<div>
                    <table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-delecticon" plain="true" onclick="materialLogTempDelete();">删除</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="ExportOrders();">导出</a></td>	
								<td><div class="datagrid-btn-separator"></div></td>
								<td><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryListPage();">刷新</a></td>							
							</tr>
					 	</tbody>
					</table>
				</div>
			</div>
			
			<table id="DataGrid1"  singleSelect="true" rownumbers="false"
				autoRowHeight="true" pagination="true" striped="true" pageSize="20" scrollbarSize="0" toolbar="#toolbar" >
				<thead>
					<tr>
						<th checkbox="true" field="id" align="center"></th>
						<th field="materialType" align="center" width="135px">物料类型(电子/组件)</th>
						<th field="totalType"  width="80px" align="center">入库/出库</th>
						<th field="type" width="150px" align="center" >出入库类型</th>
						<th field="depotName" width="150px" align="center">仓库</th>
						<th field="outTime" width="150px" align="center">出入库日期</th>
						<th field="number" width="80px" align="center">数量</th>
						<th field="receivingParty" width="120px" align="center">收货方</th>
						<th field="clerk" width="100px" align="center">经手人</th>
						<th field="marketModel" width="100px" align="center">市场型号</th>
						<th field="platform" width="100px" align="center">平台</th>
						<th field="proNO" width="160px" align="center">物料编码</th>
						<th field="proName" width="200px" align="center">物料名称</th>
						<th field="proSpeci" width="300px" align="center">物料规格</th>
						<th field="placesNO" width="80px" align="center">序号</th>
						<th field="consumption" width="50px" align="center">用量</th>
						<th field="masterOrSlave" width="50px" align="center">M/T</th>
						<th field="loss" width="50px" align="center">损耗</th>
						<th field="tradePrice" width="80px" align="center">批发价(￥)</th>
						<th field="costPrice" width="80px" align="center">成本价(￥)</th>
						<th field="hourlyRates" width="80px" align="center">工时费(￥)</th>
						<th field="retailPrice" width="80px" align="center">零售价(￥)</th>
						<th field="manufacturerCode" width="80px" align="center">厂商代码</th>
						<th field="manufacturer" width="80px" align="center">厂商名称</th>
						<th field="remark" width="220" align="center" width="200px">备注</th>
						<th field="importDate"  align="center" width="160px">导入时间</th>
						<th field="importPerson"  align="center" width="160px">导入人</th>
						<th field="rowNO"  align="center" width="160px">行号</th>
						<th field="errorInfo"  align="center" width="160px">错误原因</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>