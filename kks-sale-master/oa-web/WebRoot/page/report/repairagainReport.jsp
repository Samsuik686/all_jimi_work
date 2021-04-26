<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/page/comm.jsp" />
<jsp:include page="/page/common/page.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="${ctx }/res/script/windowReset.js"></script>
<script type="text/javascript" src="${ctx}/res/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/res/js/echarts.min.js"></script>
<style type="text/css">
.datagrid-header-row {
	height: 18px;
}

.datagrid-header-row td {
	background: rgb(102, 153, 204) none repeat scroll 0% 0%;
	font-weight: bold;
	color: rgb(255, 255, 255);
}
</style>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="二次返修报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<form id="sForm" action="${ctx}/reportCon/doExportRepairADatas" method="post" class="m-b0">
						<span> 开始时间:&nbsp; <input type="text" class="form-search" readonly="readonly" id="StartDate" name="StartDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 200px" />&nbsp;
						</span> <span> 结束时间:&nbsp; <input type="text" class="form-search" readonly="readonly" id="EndDate" name="EndDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" style="width: 200px" />&nbsp;
						</span> <a id="searchinfo" name="ckek" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="queryListPage('')">查询</a>
					</form>
				</div>
				<div>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportReportExcel();">导出</a> 
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick=" queryListPage(1);">刷新</a>
				</div>
			</div>
			<table id="Table_Datas" class="easyui-datagrid" fit="true" fitColumns="true" singleSelect="true" sortable="true" rownumbers="false" striped="true" pagination="false" scrollbarSize="0" toolbar="#toolbar">
				<thead>
					<tr>
						<th width="110" align="center">
							<label style="font-weight: bold;">受理总数 : </label><label style="font-weight: bold;" id="acceptCount">0</label>	
						</th>
						<th width="110" align="center">
							<label style="font-weight: bold;">维修总数 : </label><label style="font-weight: bold;" id="totalCount">0</label>
						</th>
						<th width="110" align="center">
							<label style="font-weight: bold;">放弃维修 : </label><label style="font-weight: bold;" id="giveUpRepairCount">0</label>
						</th>
						<th width="110" align="center">
                            <label style="font-weight: bold;">二次返修 : </label><label style="font-weight: bold;" id="twiceRepairCount">0</label>
                        </th>
						<th width="110" align="center"></th>
					</tr>
					<tr>
						<th field="engineer" width="120" align="center">维修员</th>
						<th field="returnTimes" width="120" align="center">维修数量</th>
						<th field="returnTimesA" width="120" align="center">二次维修数量</th>
						<th field="returnTimesP" width="120" align="center">二次返修率</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="position: fixed;">
			<div id="lookDetail" class="easyui-window" title="查看" modal="true" closed="true" maximizable="false" minimizable="false" collapsible="false" style="visibility: hidden; width: 960px; height: 480px; padding: 5px;">
				<div class="easyui-layout" fit="true">
				<div id="look_toolbar" style="padding: 10px 15px;">
					<div>
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-daochuicon" plain="true" onclick="exportLookReportExcel();">导出</a> 
						<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick=" queryListPageTwo(1);">刷新</a>
					</div>
				</div>
					<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
						<table id="DataGrid2" singleSelect="true" striped="true" pagination="true" rownumbers="false" autoRowHeight="true" fit="true" pageSize='10'  toolbar="#look_toolbar">
							<thead>
								<tr>
									<th field="cusName" width="160" align="center">送修单位</th>
									<th field="model" width="110" align="center">机型</th>
									<th field="imei" width="130" align="center">IMEI</th>
									<th field="isWarranty" width="80" align="center">保内/保外</th>
									<th field="cjgzDesc" width="160" align="center">初检故障</th>
									<th field="zzgzDesc" width="110" align="center">最终故障</th>
									<th field="solution" width="110" align="center">处理方法</th>
									<th field="lastAccTime" width="130" align="center">上次受理时间</th>
									<th field="lastEngineer" width="110" align="center">上次维修工程师</th>
									<th field="acceptanceTime" width="130" align="center">受理时间</th>
									<th field="engineer" width="110" align="center">维修工程师</th>
									<th field="returnTimes" width="80" align="center">返修次数</th>
									<th field="remark" width="110" align="center">送修备注</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$.messager.defaults = {
				ok : "确定",
				cancel : "取消"
			};
			var currentPageNum;

			$(function() {
				datagridInit();
				keySearch(queryListPage);
			});

			//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
			/**
			 * 初始化 表单工具栏面板
			 */
			function datagridInit() {
				$("#Table_Datas").datagrid({
					onDblClickRow : function(rowIndex, row) {
						engineer = row.engineer;
						detailDatagridInit();
					}
				});
				queryListPage();
			}
			//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

			/**
			 * 导出Excel报表
			 */
			function exportReportExcel() {
				$('#sForm').submit();
			}

			//导出指定时间段维修人员二次返修记录
			function exportLookReportExcel(){
				ExportExcelDatas("reportCon/doExportRepairDetail?engineer=" + engineer +"&StartDate=" + $("#StartDate").val() + "&EndDate=" + $("#EndDate").val());
			}
			
			/**
			 * 初始化列表
			 * 初始化数据及查询函数
			 * @param pageNumber  当前页数
			 */
			function queryListPage(pageNumber) {
				currentPageNum = pageNumber;
				if (currentPageNum == "" || currentPageNum == null) {
					currentPageNum = 1;
				}
				var url = ctx + "/reportCon/getRepairagainList";
				var param = null;
				var StartTime = $("#StartDate").val();
				var EndTime = $("#EndDate").val();
				/* if(StartTime !="" || EndTime !=""){ */
				param = "StartDate=" + StartTime + "&EndDate=" + EndTime;

				/* } */
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					if (returnData.code == '0') {
						$("#acceptCount").html(returnData.data.sumCount);
						$("#giveUpRepairCount").html(returnData.data.giveUpRepair);
						$("#totalCount").html(returnData.data.sumCount - returnData.data.giveUpRepair);
						$("#twiceRepairCount").html(returnData.data.twiceRepair + '(' + 
								(returnData.data.twiceRepair/(returnData.data.sumCount - returnData.data.giveUpRepair)*100).toFixed(2) + "%" + ')');
						$("#Table_Datas").datagrid('loadData', returnData.data.data);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
			var engineer = "";
			function detailDatagridInit(engineer) {
				$("#DataGrid2").datagrid({});
				queryListPageTwo(1);
			}

			function queryListPageTwo(pageNumber) {
				windowCommOpen("lookDetail");
				windowVisible("lookDetail");
				currentPageNumTwo = pageNumber;
				if (currentPageNumTwo == "" || currentPageNumTwo == null) {
					currentPageNumTwo = 1;
				}
				var url = ctx + "/reportCon/getRepairagainDetailList";
				var StartTime = $("#StartDate").val();
				var EndTime = $("#EndDate").val();
				var param = addPageParam('DataGrid2', currentPageNumTwo, "engineer=" + engineer +"&StartDate=" + StartTime + "&EndDate=" + EndTime);
				$("#DataGrid2").datagrid('loading');
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.each(returnData.data, function(i, item) {
							if (item.isWarranty == '0') {
								item.isWarranty = '保内';
							} else {
								item.isWarranty = '保外';
							}
							item.returnTimes = item.returnTimes;
						});
						$("#DataGrid2").datagrid('loadData', returnData.data);
						getPageSizeTwo("DataGrid2");
						resetCurrentPageShowT("DataGrid2", currentPageNumTwo);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
					$("#DataGrid2").datagrid('loaded');
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		</script>
	</div>
</body>
</html>