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
	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/gzbjmanage/gzbjmanageList";
	var param = {
		"faultType" : $.trim($("#searchByFaultType").val()),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	};
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
			gzbj_updateInfo(rowIndex,row);
		}
	});

	queryListPage(1);
}

function gzbj_addInfo() {
	$("#id").val(0);
	$("#faultType").val("");
	$("#faultDesc").val("");
	$("#costs").val("");
	$("#remark").val("");
	updateWindowOpen();
	chageWinSize('updateWindow');
}

function gzbjmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
				"id" : $("#id").val(),
				"faultType" : $.trim($("#faultType").val()),
				"faultDesc" : $.trim($("#faultDesc").val()),
				"costs" : $("#costs").val(),
				"remark" : $.trim($("#remark").val())
		}
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/gzbjmanage/addOrUpdateGzbjmanage";
	var id = $("#id").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);
			} else {
				queryListPage(1);
			}
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function gzbj_updateInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		var param = "id=" + row.id;
		var url = ctx + "/gzbjmanage/getGzbjmanage";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			$("#id").val(returnData.data.id);
			$("#faultType").val(returnData.data.faultType);
			$("#faultDesc").val(returnData.data.faultDesc);
			$("#costs").val(returnData.data.costs);
			$("#remark").val(returnData.data.remark);
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
function gzbj_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var url = ctx + "/gzbjmanage/deleteGzbjmanage";
				var param = "id=" + row.id;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);// 重新加载gzbjmanageList
					}
					$.messager.alert("操作提示", returnData.msg, "info");
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
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-GZBJ-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("gzbjmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/gzbjmanage/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='faultType' value='" + $.trim($("#searchByFaultType").val()) +"'/>"));
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
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
