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
	var url = ctx + "/reportCon/top5OfModelsList";
	var param = null;
	var boardModel = $.trim($("#jx").val());
	var startTime = $.trim($("#outTimeStart").val());
	var endTime = $.trim($("#outTimeEnd").val());
	var isWarranty = $.trim($("#isWarranty option:selected").val());
	param = "boardModel=" + boardModel + "&startTime=" + startTime + "&endTime=" + endTime+ "&isWarranty=" + isWarranty;
	$("#Table_Accqute").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data,function(i, item){
				if(i == returnData.data.length - 1){
					item.boardModel="<label style='font-weight: bold;'>"+item.boardModel+"</label>";
					if(item.number!=null)	item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
				}	
			});
			$("#Table_Accqute").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#Table_Accqute").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	/*$("#Table_Accqute").datagrid({
     singleSelect:false,	
		toolbar:[
			{
				text:'导出', 
				iconCls:'icon-daoruicon',
				handler:function(){
					exportReportExcel();
				}
			},'-',{
				 text:'刷新', 
				 iconCls:'icon-reload',
				 handler:function(){
					 queryListPage(currentPageNum);
			     } 
			  }
		]
	}); */
	
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 导出Excel报表
 */
function exportReportExcel(){
	ExportExcelDatas("reportCon/top5OfModelsExportDatas?boardModel=" + $.trim($("#jx").val()) + "&startTime=" 
			+ $.trim($("#outTimeStart").val()) 
			+ "&endTime=" + $.trim($("#outTimeEnd").val()) 
			+ "&isWarranty=" + $.trim($("#isWarranty option:selected").val()));
}

