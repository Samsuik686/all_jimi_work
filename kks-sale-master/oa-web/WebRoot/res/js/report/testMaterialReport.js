$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;

$(function(){    
	datagridInit();	
	keySearch(queryListPage);
}); 

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false
	});
	queryListPage(1);
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END

/**
 * 初始化列表
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber) {
	currentPageNum=pageNumber;
	if(currentPageNum == "" || currentPageNum == null){
		currentPageNum = 1;
	}
	var url=ctx+"/testMaterialReport/testMaterialReportPage";
	var pageSize = getCurrentPageSize('DataGrid1');
	var	param={
		"bill" : $.trim($("#searchByBill").val()),
		"imei" : $.trim($("#searchByImei").val()),
		"materialNumber" : $.trim($("#searchByMaterialNumber").val()),
		"materialName" : $.trim($("#searchByMaterialName").val()),
		"testMatfalg" : $("#testMatfalg").combobox('getValue'),
		"startDate" : $("#startTime").val(),
		"endDate" : $("#endTime").val(),
		"saleStartDate" : $("#saleStartTime").val(),
		"saleEndDate" : $("#saleEndTime").val(),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	}
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data, function(i, item) {
				if(item.testMatfalg){
					item.testMatfalg = "<label style='color:red;font-weight: bold;'>异常</label>";
					if(item.solution){
						item.solution = "<label style='color:red;font-weight: bold;'>" + item.solution + "</label>";
					}
				}else{
					item.testMatfalg = "<label style='color:green;font-weight: bold;'>正常</label>";
				}
			});
			
			$("#DataGrid1").datagrid('loadData',returnData.data);
			$("#DataGrid1").datagrid('loaded');	
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		}else {
			$("#DataGrid1").datagrid('loaded');	
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
			$("#DataGrid1").datagrid('loaded');	
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 导出Excel报表
 */
function exportReportExcel() {
	var url =  ctx + "/testMaterialReport/exportDatasTestMaterialReport";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='bill' value='" + $.trim($("#searchByBill").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='imei' value='" + $.trim($("#searchByImei").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='materialNumber' value='" + $.trim($("#searchByMaterialNumber").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='materialName' value='" + $.trim($("#searchByMaterialName").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='testMatfalg' value='" + $("#testMatfalg").combobox('getValue') +"'/>"));
	$form1.append($("<input type='hidden' name='startDate' value='" + $("#startTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='endDate' value='" +  $("#endTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='saleStartDate' value='" + $("#saleStartTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='saleEndDate' value='" +  $("#saleEndTime").val() +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}
