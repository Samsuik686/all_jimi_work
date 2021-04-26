<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx}/res/js/report/accquoteReport.js"></script>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="配件报价报表" >
			<div id="toolbar" style="padding:10px 15px;">
			  <div style="margin-bottom:5px">			   
				<span>
				市场型号:&nbsp;<input type="text" id="marketModel" name="marketModel"  style="width: 150px"  class="easyui-validatebox form-search" required="true" missingMessage="请填写此字段">&nbsp;
				</span>
				<a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>				
			 </div>
			 <div>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a> 
			 </div>
			</div>
			<table id="Table_Accqute" class="easyui-datagrid"  singleSelect="true" sortable="true" rownumbers="false" pagination="false" fitColumns="true" fit="true" striped="true" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th checkbox="true" field="w_repairId"></th>
						<th field="marketModel" width="110" align="center">市场型号</th>
						<th field="model" width="80" align="center">主板型号</th>
						<th field="proNO" width="80" align="center">编码</th>
						<th field="proName" width="150" align="center">名称</th>
						<th field="proSpeci" width="250" align="center">规格</th>
						<th field="consumption" width="80" align="center">用量</th>
						<th field="retailPrice" width="110" align="center">零售价（￥）</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<!-- EChart 图表      Start -->
		<div id="Echart_Accqute" class="easyui-window" title="配件报价图表" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false"
			 style="width:850px;height:600px;padding:5px;">
			<div class="easyui-layout" fit="true">
				<div region="center" border="false" style="padding:8px;background:#fff;border:1px solid #ccc;">
					<div id="main" style="width: 800px;height:500px;"></div>
				</div>
			</div>
		</div>
		
		<!-- EChart 图表      End   -->
		<script type="text/javascript">
				/**
				 * 初始化列表
				 * 初始化数据及查询函数
				 * @param pageNumber  当前页数
				 */
				function queryListPage(pageNumber){
					currentPageNum=pageNumber;
					if(currentPageNum=="" || currentPageNum==null){
						currentPageNum=1;
					}
					var url=ctx+"/reportCon/getAccqueteList";
					var param= "marketModel="+$.trim($("#marketModel").val());
					var modal = $("#marketModel").val();
					if(modal){
						 $("#Table_Accqute").datagrid('loading');
						asyncCallService(url, 'post', false,'json', param, function(returnData){
							processSSOOrPrivilege(returnData);
							if(returnData.code==undefined){ 
								$("#Table_Accqute").datagrid('loadData',returnData.data);
							}else {
								$.messager.alert("操作提示",returnData.msg,"info");
							}
							 $("#Table_Accqute").datagrid('loaded');
						}, function(){
							 	$.messager.alert("操作提示","网络错误","info");
						});
					}else{
						$('#Table_Accqute').datagrid('loadData',{total:0,rows:[]}); 
						$.messager.alert("操作提示","请输入市场型号查询","warning");
					}
				}
    	</script>
	</div>
</body>
</html>