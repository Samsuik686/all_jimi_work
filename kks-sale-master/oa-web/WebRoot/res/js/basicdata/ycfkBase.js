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
	var url = ctx + "/ycfkBase/ycfkBaseList";
	var selParams = "gId=" + $.trim($("#gIds").val()) + "&problems=" + $.trim($("#problemsSearch").val()) + "&proDetails=" + $.trim($("#proDetailsSearch").val());
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
			ycfkBase_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);
}
// TODO 清除表单数据
function clearFromParams() {
	$("#yid").val(0);
	$("#problems").val("");
	$("#proDetails").val("");
	$("#methods").val("");
	comboboxInit();
}

// 是否同步刷新
function processSaveAjaxes(reqAjaxPrams) {
	var url = ctx + "/ycfkBase/addOrUpdateYcfkBase";
	var id = $("#yid").val();
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

// 新增400电话记录定义管理信息
function ycfkBase_Save() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams={
				yid : $("#yid").val(),
				gId : $("#type").combobox('getValue'),
				problems : $("#type").combobox('getText'),
				proDetails : $.trim($("#proDetails").val()),
				methods : $.trim($("#methods").val())		
		}
		processSaveAjaxes(reqAjaxPrams);
	}
}

// 删除400电话记录定义信息
function ycfkBase_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var getCurrentId = row.yid;

				var url = ctx + "/ycfkBase/deleteYcfkBase";
				var param = "yid=" + getCurrentId;

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
function ycfkBase_addInfo() {
	clearFromParams();
	windowOpenEncl();
	chageWinSize('w');
	comboboxInit();
}
// 弹出修改框
function ycfkBase_updateInfo() {
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.yid;
		var url = ctx + "/ycfkBase/selectYcfkBase";
		var param = "yid=" + getCurrentId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data.data;
			$("#yid").val(entity.yid);
			$('#type').combobox('setValue', entity.gId);// 一定要先value后text,否则text与value值会相同全为value值
			$('#type').combobox('setText', entity.problems);
			$("#proDetails").val(entity.proDetails);
			$("#methods").val(entity.methods);
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
	downLoadExcelTmp("BASE-DATA/Excel-YCFKBASE-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("ycfkBase/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("ycfkBase/ExportDatas?gId=" + $("#gIds").val() + "&problems=" + $.trim($("#problemsSearch").val()) + "&proDetails=" + $.trim($("#proDetailsSearch").val()));
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