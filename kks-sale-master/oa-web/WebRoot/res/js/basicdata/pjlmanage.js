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
	var url = ctx + "/pjlmanage/pjlmanageList";
	var param = {
		"marketModel" : $.trim($("#searchByMarketModel").val()),
		"model" : $.trim($("#searchByModel").val()),
		"proName" : $.trim($("#searchByProName").val()),
		"proNO" : $.trim($("#searchByProNO").val()),
		"otherModel" : $.trim($("#searchByOtherModel").val()),
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

/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			pjl_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}
// 清除表单数据
function pjl_addInfo() {
	$("#pjlId").val(0);
	$("#marketModel").val("");
	$("#model").val("");
	$("#proNO").val("");
	$("#proName").val("");
	$("#proSpeci").val("");
	$("#retailPrice").val("");
	$("#hourFee").val("");
	$("#otherModel").val("");
	$("#consumption").val("");
	$("#remark").val("");
	$("#masterOrSlave").combobox("setValue", "M");
	updateWindowOpen();
}

function pjlmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
			"pjlId" : $("#pjlId").val(),
			"marketModel" : $.trim($("#marketModel").val()),
			"model" : $.trim($("#model").val()),
			"proNO" : $.trim($("#proNO").val()),
			"proName" : $.trim($("#proName").val()),
			"proSpeci" : $.trim($("#proSpeci").val()),
			"retailPrice" : $.trim($("#retailPrice").val()),
			"hourFee" : $.trim($("#hourFee").val()),
			"otherModel" : $.trim($("#otherModel").val()),
			"consumption" : $.trim($("#consumption").val()),
			"masterOrSlave" : $("#masterOrSlave").combobox('getValue'),
			"remark" : $("#remark").val()
		};
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/pjlmanage/addOrUpdatePjlInfo";
	var id = $("#pjlId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载pjlmanageList
			} else {
				queryListPage(1);// 重新加载pjlmanageList
			}
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function pjl_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.pjlId;
		var url = ctx + "/pjlmanage/getPjlInfo";
		var param = "pjlId=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data;
			$("#pjlId").val(entity.pjlId);
			$("#marketModel").val(entity.marketModel);
			$("#model").val(entity.model);
			$("#proNO").val(entity.proNO);
			$("#proName").val(entity.proName);
			$("#proSpeci").val(entity.proSpeci);
			$("#retailPrice").val(entity.retailPrice);
			$("#hourFee").val(entity.hourFee);
			$("#otherModel").val(entity.otherModel);
			$("#consumption").val(entity.consumption);
			$("#masterOrSlave").combobox('setValue', entity.masterOrSlave);
			$("#remark").val(entity.remark);
			updateWindowOpen();
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
		queryListPage(1);
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 删除
 */
function pjl_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var getPjlId = row.pjlId;
				if (getPjlId != -1) {
					var param = "pjlId=" + getPjlId;
					var url = ctx + "/pjlmanage/deletePjlInfo";
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);// 重新加载pjlmanageList
						}
						$.messager.alert("操作提示", returnData.msg, "info");
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				}
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
	downLoadExcelTmp("BASE-DATA/Excel-PJL-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("pjlmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/pjlmanage/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='marketModel' value='" + $.trim($("#searchByMarketModel").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='model' value='" + $.trim($("#searchByModel").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='proName' value='" + $.trim($("#searchByProName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='proNO' value='" + $.trim($("#searchByProNO").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='otherModel' value='" + $.trim($("#searchByOtherModel").val()) +"'/>")); 
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