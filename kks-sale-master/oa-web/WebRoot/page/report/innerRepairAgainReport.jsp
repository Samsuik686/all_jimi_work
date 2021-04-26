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
.datagrid-header-row{
	height: 18px;
}
.datagrid-header-row td{
	background: rgb(102, 153, 204) none repeat scroll 0% 0%; 
	font-weight: bold; color: rgb(255, 255, 255);
}
</style>
</head>
<body style="margin: 0">
	<div class="easyui-layout" fit="true">
		<div region="center" title="内部二次返修报表">
			<div id="toolbar" style="padding: 10px 15px;">
				<div style="margin-bottom: 5px">
					<form id="sForm" action="${ctx}/innerRepairAgainReport/doExportRepairADatas" method="post" class="m-b0">
						<span> 开始时间:&nbsp; <input type="text" class="form-search" readonly="readonly" id="StartDate" name="StartDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width: 200px" />&nbsp;
						</span> <span> 结束时间:&nbsp; <input type="text" class="form-search" readonly="readonly" id="EndDate" name="EndDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width: 200px" />&nbsp;
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
						<th></th>
						<th width="110" align="center">
							<label style="font-weight: bold;">维修总数 : </label><label style="font-weight: bold;" id="totalCount">0</label>
						</th>
						<th width="110" align="center"></th>
						<th width="110" align="center"></th>
					</tr>
					<tr>
						<th field="engineer" width="120" align="center">维修员</th>
						<th field="returnTimes" width="120" align="center">维修数量</th>
						<th field="returnTimesA" width="120" align="center">二次维修数量</th>
						<th field="returnTimesP" width="120" align="center">返修率</th>
					</tr>
				</thead>
			</table>
		</div>

		<script type="text/javascript">
			$.messager.defaults = {
				ok : "确定",
				cancel : "取消"
			};
			var currentPageNum;

			$(function() {
				datagridInit();
			});

			//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
			/**
			 * 初始化 表单工具栏面板
			 */
			function datagridInit() {
				queryListPage();
			}
			//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

			/**
			 * 导出Excel报表
			 */
			function exportReportExcel() {
				$('#sForm').submit();
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
				var url = ctx + "/innerRepairAgainReport/getRepairagainList";
				var param = null;
				var StartTime = $("#StartDate").val();
				var EndTime = $("#EndDate").val();
				param = "StartDate=" + StartTime + "&EndDate=" + EndTime;

				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					if (returnData.code == '0') {
						$("#totalCount").html(returnData.data.total);
						$("#Table_Datas").datagrid('loadData', returnData.data.data);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		</script>
	</div>
</body>
</html>