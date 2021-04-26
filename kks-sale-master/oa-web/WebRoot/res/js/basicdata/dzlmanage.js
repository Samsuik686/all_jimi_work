$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
$(function() {
	basegroupTreeInit();
	datagridInit();
	basegroupTreeSelect();
	comboboxInit();
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
	var url = ctx + "/dzlmanage/dzlmanageList";
	var param = {
		"gId" : $("#gIds").val(),
		"proName" : $.trim($("#searchByProName").val()),
		"proNO" : $.trim($("#searchByProNO").val()),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	};
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				}
			});
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
			dzl_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}
// 清除表单数据
function dzl_addInfo() {
	$("#dzlId").val(0);
	$("#proNO").val("");
	$("#proName").val("");
	$("#proSpeci").val("");
	$("#placesNO").val("");
	$("#consumption").val("");
	$("#remark").val("");
	$("#masterOrSlave").combobox("setValue", "M");
	$("input[name='enabledFlag'][value='1']").prop('checked', true);// 默认不禁用
	updateWindowOpen();
	comboboxInit();
}

function dzlmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
			"dzlId" : $("#dzlId").val(),
			"gId" : $("#type").combobox('getValue'),
			"dzlType" : $("#type").combobox('getText'),
			"proNO" : $.trim($("#proNO").val()),
			"proName" : $.trim($("#proName").val()),
			"proSpeci" : $.trim($("#proSpeci").val()),
			"placesNO" : $.trim($("#placesNO").val()),
			"consumption" : $.trim($("#consumption").val()),
			"masterOrSlave" : $("#masterOrSlave").combobox('getValue'),
			"enabledFlag" : $("input[name='enabledFlag']:checked").val(),
			"remark" : $("#remark").val()
		};
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/dzlmanage/addOrUpdateDzlInfo";
	var id = $("#dzlId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载dzlmanageList
			} else {
				queryListPage(1);// 重新加载dzlmanageList
			}
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function dzl_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.dzlId;
		var url = ctx + "/dzlmanage/getDzlInfo";
		var param = "dzlId=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data;
			$("#dzlId").val(entity.dzlId);
			$("#proNO").val(entity.proNO);
			$("#proName").val(entity.proName);
			$("#proSpeci").val(entity.proSpeci);
			$("#placesNO").val(entity.placesNO);
			$("#consumption").val(entity.consumption);
			$("#masterOrSlave").combobox('setValue', entity.masterOrSlave);
			$("#type").combobox('setValue', returnData.data.gId);
			$("#type").combobox('setText', returnData.data.dzlType);
			$("#remark").val(entity.remark);
			if (entity.enabledFlag == "1") {
				$("input[name='enabledFlag'][value='1']").prop('checked', true);
			} else {
				$("input[name='enabledFlag'][value='0']").prop('checked', true);
			}
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
function dzl_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var getDzlId = row.dzlId;
				if (getDzlId != -1) {
					var param = "dzlId=" + getDzlId;
					var url = ctx + "/dzlmanage/deleteDzlInfo";
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							queryListPage(currentPageNum);// 重新加载dzlmanageList
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
	downLoadExcelTmp("BASE-DATA/Excel-DZL-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("dzlmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/dzlmanage/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='gId' value='" + $.trim($("#gIds").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='proName' value='" + $.trim($("#searchByProName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='proNO' value='" + $.trim($("#searchByProNO").val()) +"'/>")); 
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