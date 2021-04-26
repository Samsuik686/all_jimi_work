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
	var url = ctx + "/reportCon/badrateList";
	var param = null;
	var model = $.trim($("#jx").val());
	var isWarranty = $.trim($("#isWarranty option:selected").val());
	param = "model=" + model + "&isWarranty=" + isWarranty;
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if(i == ret.length-1){
						item.model="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.model+"</label>";
						item.repairs="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.repairs+"</label>";
						item.goods="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.goods+"</label>";
						item.percent="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.percent+"</label>";
					}
				});
			}
			$("#DataGrid1").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#DataGrid1").datagrid('loaded');
		
		$("#export_div").css('display', 'block');//数据加载完成才显示导出按钮
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


/**
 * 初始化 表单工具栏面板
 */
function datagridInit()  
{
	/*$("#DataGrid1").datagrid({
     singleSelect:false,	
		toolbar:[
			{
				text:'导出', 
				iconCls:'icon-daoruicon',
				handler:function(){
					exportReportExcel();
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
	ExportExcelDatas("reportCon/badrateExportDatas?model=" + $.trim($("#jx").val())+ "&isWarranty="+$.trim($("#isWarranty option:selected").val()));	
}

