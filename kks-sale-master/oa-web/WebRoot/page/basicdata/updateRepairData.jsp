<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/basicdata/updateRepairData.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
/* 扫描IMEI */
strong {
	font-family: "微软雅黑";
	font-size: 16px;
	color: #0066FF;
}
</style>
</head>
<body style="margin: 0">
	<div class="easyui-layout" style="overflow: hidden; width: 100%;" fit="true">
		<div region="center" id="dic-center" style="overflow: auto" title="维修（批次未付款）数据修改" fit="true">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom:5px">	
					<span>
						送修批号: <input type="text" class="form-search" id="repairnNmber" style="width: 130px" value="1500626240600"/> 
					</span> &nbsp;
					<span>
						IMEI: <input type="text" class="form-search" id="imei" style="width: 130px" value="868120160046592"/> 
					</span> &nbsp;
					<span><a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryData()">查询</a>
					</span>
				</div>
				<div>
					<table cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
								<td>
									<span>
										修改状态:
										<select id="state" class="easyui-combobox" style="width: 130px;" editable="false" panelHeight="auto">
											<option value="2">已分拣，待维修</option>
											<option value="3">待报价</option>
										</select>&nbsp;
									</span>		
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-xgicon" plain="true" onclick="InfoUpdate();">修改</a></td>
								<td>
									<div class="datagrid-btn-separator"></div>
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="queryData();">刷新</a>
								</td>
							</tr>
					 	</tbody>
					</table>
	             </div>
			</div>
			<table id="DataGrid1" class="easyui-datagrid" fit="true" singleSelect="true" sortable="true" rownumbers="false" pagination="false" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="id"></th>
						<th field="acceptanceTime" width="120" align="center">受理时间</th>
						<th field="repairnNmber" width="110" align="center">送修批号</th>
						<th field="imei" width="120" align="center">IMEI</th>
						<th field="lockId" width="100" align="center">智能锁ID</th>
						<th field="w_cusName" width="150" align="center">送修单位</th>
						<th field="w_phone" width="90" align="center">手机号</th>
						<th field="w_station"  width="80"  align="center">所在工站</th>
						<th field="state" width="100" align="center">进度</th>
						<th field="w_repairMoney" width="90" align="center">维修金额（￥）</th>
						<th field="w_isPay" width="80" align="center"> 是否付款 </th> 
						<th field="w_isPrice" width="100" align="center"> 放弃报价  </th> 
						<th field="w_giveUpRepairStatus" width="120" align="center"> 客户放弃维修状态  </th>
						<th field="w_offer" width="80" align="center"> 报价人 </th>
						<th field="sendPackTime" width="130" align="center">发送装箱时间</th>	
						<th field="wxbjDesc" width="260" align="center">维修报价描述</th>
						<th field="zzgzDesc" width="260" align="center">最终故障</th>
						<th field="solution" width="260" align="center">处理措施</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>