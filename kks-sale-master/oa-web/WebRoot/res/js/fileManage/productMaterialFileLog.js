
$(function(){
	initSearch();
	queryListPage();
	keySearch(queryListPage);
});

function initSearch(){
	$("#searchByFileName").val("");
	$("#searchByUserName").val("");
	$("#searchByIp").val("");
	$("#startTime").val("");
	$("#endTime").val("");
}

function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum == "" || currentPageNum == null){
		currentPageNum = 1;
	} 
	
	var pageSize = getCurrentPageSize('DataGrid1');
	var selParams = {
			"fileName" : $.trim($("#searchByFileName").val()),
			"userName" : $.trim($("#searchByUserName").val()),
			"ip" : $.trim($("#searchByIp").val()),
			"startTime" : $("#startTime").val(),
			"endTime" : $("#endTime").val(),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	};
	var url = ctx+"/productMaterialFileLog/getProductMaterialFileLogPage";
	
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', selParams, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			$("#DataGrid1").datagrid('loadData',ret);  
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
		getPageSize();
		resetCurrentPageShow(currentPageNum);
	}, function(){
		$("#DataGrid1").datagrid('loaded');	
	 	$.messager.alert("操作提示","网络错误","info");
	});
}

// 导出
function exportInfo(){	
	var url =  ctx + "/productMaterialFileLog/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='fileName' value='" + $.trim($("#searchByFileName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='userName' value='" + $.trim($("#searchByUserName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='ip' value='" + $.trim($("#searchByIp").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}
