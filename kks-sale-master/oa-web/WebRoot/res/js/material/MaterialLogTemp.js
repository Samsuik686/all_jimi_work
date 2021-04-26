$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	datagridInit();	
	keySearch(queryListPage);
});
/**
 * 初始化数据及查询函数
 * 
 * @param pageNumber
 *            当前页数
 */
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	var param =
		{
			"outTimeStarts" : $("#outTimeStart").val(), 
			"outTimeEnds" : $("#outTimeEnd").val(), 
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
		};
	var url = ctx + "/materialLogTemp/getMaterialLogTempList";
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', true, 'json', param, function(returnData) {
		var ret = returnData.data;
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		 $("#DataGrid1").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false
	});
	queryListPage(1);
}

// 删除出入库导入管理信息
function materialLogTempDelete() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示","是否删除此信息", function(conf) {
				if (conf) {
					var getCurrentId = new Array();
					var row = $('#DataGrid1').datagrid('getSelections');
					for (var z = 0; z < row.length; z++) {
						getCurrentId[z] = row[z].id;
					}
					var url = ctx + "/materialLogTemp/deleteMaterialLogTemp";
					var param = "ids=" + getCurrentId;
					asyncCallService(url,'post',false,'json',param,function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);
						}
						$.messager.alert("操作提示",returnData.msg,"info");
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
					$('#DataGrid1').datagrid('acceptChanges');
				}
			});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

/**
 * 导出数据
 */
function ExportOrders() {
	var url =  ctx + "/materialLogTemp/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='outTimeStarts' value='" + $("#outTimeStart").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='outTimeEnds' value='" + $("#outTimeEnd").val() +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}


