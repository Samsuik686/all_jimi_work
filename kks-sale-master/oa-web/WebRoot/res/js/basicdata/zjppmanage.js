var currentPageNum;

$(function() {
	datagridInit();
	keySearch(queryListPage);
});

function queryListPage(pageNumber) {// 初始化数据及查询函数
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var url = ctx + "/zjppmanage/zjppmanageList";
	// var param="model="+$("#searchByModel").val()+"&currentpage="+currentPageNum;
	var selParams = "model=" + $.trim($("#searchByModel").val());
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {

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

$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};

function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow:function(rowIndex,row){
			zjpp_updateInfo(rowIndex,row);
		}
	});

	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

function zjpp_addInfo() {

	$("#matchId_hidden").val(0);
	// 清空下缓存
	$("#modelP").val("");
	$("#stepP").val("");
	$("#testMethodP").val("");
	$("#becarefulP").val("");
	// 加载所有MenuId及对应的FunctionId
	// loadAllMenuFunction();
	updateWindowOpen();

	// 改变弹出框位置add by wg.he 2013/12/10
	chageWinSize('updateWindow');

}

function zjppmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var model = $("#modelP").val().trim();
		var step = $("#stepP").val().trim();
		var testMethod = $("#testMethodP").val().trim();
		var becareful = $("#becarefulP").val().trim();
		var reqAjaxPrams = "matchId=" + $("#matchId_hidden").val() + "&model=" + model + "&step=" + step + "&testMethod=" +
							testMethod + "&becareful=" + becareful + "&id=0";
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/zjppmanage/addOrUpdateZjppmanage";
	var id = $("#matchId_hidden").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {

		processSSOOrPrivilege(returnData);

		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载ZjppmanageList
			} else {
				queryListPage(1);// 重新加载ZjppmanageList
			}
			
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function zjpp_updateInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		var param = "matchId=" + row.matchId;
		var url = ctx + "/zjppmanage/getZjppmanage";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			$("#matchId_hidden").val(returnData.data.matchId);
			$("#modelP").val(returnData.data.model);
			$("#stepP").val(returnData.data.step);
			$("#testMethodP").val(returnData.data.testMethod);
			$("#becarefulP").val(returnData.data.becareful);
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
		updateWindowOpen();
		chageWinSize('updateWindow');
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 删除
 */
function zjpp_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var index = $('#DataGrid1').datagrid('getRowIndex', row);
				$('#DataGrid1').datagrid('deleteRow', index);
				var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");
				var getMatchId;
				for (var z = 0; z < deleted.length; z++) {
					getMatchId = deleted[z].matchId;
					var url = ctx + "/zjppmanage/deleteZjppmanage";
					var param = "matchId=" + getMatchId;
					if (getMatchId != -1) {
						asyncCallService(url, 'post', false, 'json', param, function(returnData) {
							processSSOOrPrivilege(returnData);
							if (returnData.code == '0') {
								queryListPage(currentPageNum);// 重新加载ZjppmanageList
							}
							$.messager.alert("操作提示", returnData.msg, "info");
						}, function() {
							$.messager.alert("操作提示", "网络错误", "info");
						});
					} else {
						$.messager.alert("操作提示", "删除错误!", "info");
						queryListPage(currentPageNum);// 重新加载ZjppmanageList
					}
				}
				$('#DataGrid1').datagrid('acceptChanges');
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}
}

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-ZJPP-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("zjppmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("zjppmanage/ExportDatas?model=" + $.trim($("#searchByModel").val()));
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function updateWindowClose() {
	$('#updateWindow').window('close');
}

function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	windowVisible("updateWindow");
}
