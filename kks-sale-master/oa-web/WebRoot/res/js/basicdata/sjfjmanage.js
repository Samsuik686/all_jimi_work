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
	var url = ctx + "/sjfjmanage/sjfjmanageList";
	// var param="gId="+$("#gIds").val()+"&brand="+$("#brand").val()+"&name="+$("#name").val()+"&currentpage="+currentPageNum;
	var selParams = "gId=" + $("#gIds").val() + "&brand=" + $.trim($("#brand").val()) + "&name=" + $.trim($("#name").val());
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

/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			sjfj_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);
}
// TODO 清除表单数据
function clearFromParams() {
	$("#eidi").val(0);
	$("#type").val("");
	$("#brandi").val("");
	$("#namei").val("");
	$("#colori").val("");
	$("#numberi").val("");
	comboboxInit();
}

// 是否同步刷新
function processSaveAjaxes(reqAjaxPrams) {
	var url = ctx + "/sjfjmanage/addOrUpdateSjfjmanage";
	var id = $("#eidi").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {

		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			windowCloseEncl();
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

// 新增随机附件管理信息
function SjfjmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var gId = $("#type").combobox('getValue');
		var category = $("#type").combobox('getText');
		var reqAjaxPrams = "eid=" + $("#eidi").val() + "&gId=" + gId + "&category=" + category + "&brand=" + $("#brandi").val() + "&name=" + $("#namei").val() + "&color=" + $("#colori").val() + "&number=" + $("#numberi").val();
		processSaveAjaxes(reqAjaxPrams);
	}
}

// 删除随机附件信息
function sjfj_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var getCurrentId = row.eid;

				var url = ctx + "/sjfjmanage/deleteSjfjmanage";
				var param = "eid=" + getCurrentId;

				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);

					if (returnData.code == '0') {
						queryListPage(currentPageNum);
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

// 弹出新增框
function sjfj_addInfo() {
	clearFromParams();
	windowOpenEncl();
	chageWinSize('w');
	comboboxInit();
}
// 弹出修改框
function sjfj_updateInfo() {
	// comboboxInit();
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.eid;
		var url = ctx + "/sjfjmanage/selectSjfjmanage";
		var param = "eid=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data.data;
			// TODO 数据展示

			$("#eidi").val(entity.eid);
			$('#type').combobox('setValue', entity.gId);// 一定要先value后text,否则text与value值会相同全为value值
			$('#type').combobox('setText', entity.category);
			$("#brandi").val(entity.brand);
			$("#namei").val(entity.name);
			$("#colori").val(entity.color);
			$("#numberi").val(entity.number);
			windowOpenEncl();
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
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-SJFJ-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("sjfjmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("sjfjmanage/ExportDatas?gId=" + $("#gIds").val() + "&brand=" + $.trim($("#brand").val()) + "&name=" + $.trim($("#name").val()));
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function windowOpenEncl() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w').window('open');
	windowVisible("w");
}
function windowCloseEncl() {
	$('#w').window('close');
}