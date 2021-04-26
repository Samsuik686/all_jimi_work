$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
   datagridInit();  
   keySearch(queryListPage);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START

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
	var url = ctx + "/ycfkmanage/ycfkCountList";
	var param = null;
	var boardModel = $.trim($("#jx").val());
	var startTime = $.trim($("#outTimeStart").val());
	var endTime = $.trim($("#outTimeEnd").val());
	param = "startTime=" + startTime + "&endTime=" + endTime;
	$("#Table_Ycfk").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#Table_Ycfk").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#Table_Ycfk").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#Table_Ycfk").datagrid({
     singleSelect:false	
	}); 
	
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	ExportExcelDatas("ycfkmanage/ycfkExportDatas?startTime=" + $.trim($("#outTimeStart").val()) + "&endTime=" + $.trim($("#outTimeEnd").val()));
}

