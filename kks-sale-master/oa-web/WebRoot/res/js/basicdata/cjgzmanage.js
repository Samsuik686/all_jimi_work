$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
var typeTree = "typeTree";
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
function queryListPage(pageNumber) {// 初始化数据及查询函数
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var url = ctx + "/cjgzmanage/CjgzmanageList";
	var selParams = "gId=" + $("#gIds").val() + "&initheckFault=" + $.trim($("#initheckFault").val()) + "&number=" + $.trim($("#number").val());
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, item) {
				if (item.available == 0) {
					item.available = '<label style="color:red;font-weight: bold;">是</label>';
				} else if (item.available == 1) {
					item.available = '<label style="color:green;font-weight: bold;">否</label>';
				}
			});
		}
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
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			cjgz_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

function cjgz_addInfo() {
	// 清空下缓存
	clearFromParam();
	windowOpenCheckFault();
	chageWinSize('w');
	comboboxInit();
}

// TODO 清除表单数据
function clearFromParam() {
	$("#iidP").val(0);
	$("#type").val("");
	$("#initheckFaultP").val("");
	$("#descriptionP").val("");
	$("#numberP").val("");
	$("input[name='availableP'][value='1']").prop('checked', true);// 默认不禁用
}

// 新增初检故障管理信息
function checkFaultSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var gId = $("#type").combobox('getValue');
		var faultClass = $("#type").combobox('getText');
		var reqAjaxPrams = "iid=" + $("#iidP").val() + "&gId=" + gId + "&faultClass=" + faultClass + "&initheckFault=" + $("#initheckFaultP").val() +
							"&description=" + $("#descriptionP").val() + "&number=" + $("#numberP").val() + "&available=" + $("input:radio:checked").val();
		processSaveAjaxs(reqAjaxPrams);
	}
}

// 是否同步刷新
function processSaveAjaxs(reqAjaxPrams) {
	var url = ctx + "/cjgzmanage/addOrUpdateCjgzmanage";
	var id = $("#iidP").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			windowCloseCheckFault();
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

// 删除初检故障管理信息
function cjgz_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此故障信息", function(conf) {
			if (conf) {
				var getCurrentCjgzmanageId = row.iid;
				var url = ctx + "/cjgzmanage/deleteCjgzmanage";
				var param = "iid=" + getCurrentCjgzmanageId;
	
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);// 重新加载RoleList
					}
					$.messager.alert("操作提示", returnData.msg, "info");
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
	}

}

// 弹出修改框
// 修改故障信息
function cjgz_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.iid;
		var url = ctx + "/cjgzmanage/selectCjgzmanage";
		var param = "iid=" + getCurrentId;

		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var Redata = returnData.data;
			if (Redata.code == '0') {
				$("#iidP").val(Redata.data.iid);
				$('#type').combobox('setValue', Redata.data.gid);// 一定要先value后text,否则text与value值会相同全为value值
				$('#type').combobox('setText', Redata.data.faultClass);
				$("#initheckFaultP").val(Redata.data.initheckFault);
				$("#descriptionP").val(Redata.data.description);
				$("#numberP").val(Redata.data.number);
				if (Redata.data.available == 0) {
					$("input[name='availableP'][value='0']").prop("checked", true);
				} else {
					$("input[name='availableP'][value='1']").prop("checked", true);
				}
				windowOpenCheckFault();
				queryListPage(currentPageNum);
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 下载模板
 */
function downloadTemplet(){
	downLoadExcelTmp("BASE-DATA/Excel-CJGZ-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo(){
	ImportExcelDatas("cjgzmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo(){
	ExportExcelDatas("cjgzmanage/ExportDatas?gId=" + $("#gIds").val() + "&initheckFault=" + $.trim($("#initheckFault").val()) + "&number=" + $.trim($("#number").val()));
}

/**
 * 刷新
 */
function refreshInfo(){
	queryListPage(currentPageNum);
}

function windowOpenCheckFault() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w').window('open');
	windowVisible("w");
}
function windowCloseCheckFault() {
	$('#w').window('close');
}
