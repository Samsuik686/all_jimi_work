var currentPageNum;

var currentUserName = "",
	currentUserId = "",
 	currentUserOrgId = "",
 	currentUserPosition = "";

$(function(){
	initEvent();
	initData();	
	getCurrentUserInfo();
});

function getCurrentUserInfo(){
	 currentUserName = $("#userName").html();
	 currentUserId = $("#userId").html();
	 currentUserOrgId = $("#userOrgId").html();
	 currentUserPosition = $("#position").html();
}

function initData(){
	$("#DataGrid1").datagrid({
//		singleSelect : true
//		onBeforeCheck: function (rowIndex, rowData) {
//  	        $('#DataGrid1').datagrid("unselectAll");//取消选中当前所有行
//  	    }
	}); 
	queryListPage(1);
}

function initEvent(){
	 keySearch(queryListPage);
	 
	 $("#searchinfo").click(function(){
		 queryListPage(1);
	 });
	 	
}

function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum == "" || currentPageNum == null){
		currentPageNum = 1;
	} 
	
	var pageSize = getCurrentPageSize('DataGrid1');
	var name = $.trim($("#searchName").val());
	var selParams = {
			"name" : name,
			"createUser" : $.trim($("#searchCreateUser").val()),
//			"status" : $.trim($("#searchStatus").val()),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	};
	var url = ctx+"/customFlow/customFlowList";
	
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


function formatOper(val,row,index){
	return "<a href='#' onclick=\"openFlowWin(2,'"+row.name+"');\">查看</a>";
}

function convertStatus(val,row,index){
	if('0' == row.status){
		return "生效";
	}else if('1' == row.status){
		return "失效";
	}else{
		return "未识别";
	}
}

function openFlowWin(forward,name){
	var picUrl = ctx+"/customFlow/customFlowPicture?forward=" + forward + "&name=" + name;
	window.open(picUrl);	
}

function formatDate(val,row,index){
	var date = row.createDate;
	return date;
}

function deleteCustomFlow() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if(row.length > 1){
		$.messager.alert("提示信息", "一次只能删除一行数据。", "info");
		return;
	}
	if (row.length > 0) {
		$.messager.confirm("操作提示", "删除操作会删除整个流程相关，包括已经存在的任务。是否删除？", function(conf) {
			if (conf) {
				var name = row[0].name;
				
				var url = ctx + "/customFlow/customFlowDelete";
				var param = "name=" + name;
				asyncCallService(url, 'post', false, 'json', param,
					function(returnData) {
						processSSOOrPrivilege(returnData);
							if (returnData.code == '0') {
									queryListPage(currentPageNum);
							}
							$.messager.alert("操作提示", returnData.msg, "info");
						}, 
						function() {
								$.messager.alert("操作提示", "网络错误", "info");
					});
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}
