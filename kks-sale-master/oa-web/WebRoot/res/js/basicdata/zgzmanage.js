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
	comboboxInit1();
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
	var url = ctx + "/zgzmanagecon/manageList";
	// var param="gId="+$("#gIds").val()+"&currentpage="+currentPageNum;
	var selParams = "gId=" + $("#gIds").val();
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		$("#DataGrid1").datagrid('loading');
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				}
				if (item.isSyncSolution == 1) {
					item.isSyncSolution = '是';
				} else if (item.isSyncSolution == 0) {
					item.isSyncSolution = '否';
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
		singleSelect : true,
		onDblClickRow:function(rowIndex,row){
			zzgz_updateInfo(rowIndex,row);
		}
	});
	queryListPage(1);
}

// 清除表单数据
function clearFromParams() {
	$("#id").val(0);
	$("#proceMethod").val("");
	$("#methodNO").val("");
	$("input[name='enabledFlag'][value='1']").prop('checked', true);// 默认不禁用
	$("#remark").val("");
	comboboxInit();
	comboboxInit1();
}

// 是否同步刷新
function processSaveAjax(reqAjaxPrams) {
	var url = ctx + "/zgzmanagecon/addOrUpdateInfo";
	var id = $("#id").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {

		processSSOOrPrivilege(returnData);

		if (returnData.code == '0') {
			zgzwindowClose();
			if(id > 0){
				queryListPage(currentPageNum);
			}else{
				queryListPage(1);
			}
			
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 新增最终故障信息
function InfoSave() {

	var isValid = $('#mform').form('validate');
	if (isValid) {
		var reqAjaxPrams = "id=" + $("#id").val() + "&gId=" + $("#type").combobox('getValue') 
			+ "&faultType=" + $("#type").combobox('getText') + "&proceMethod=" + $("#proceMethod").val() 
			+ "&methodNO=" + $("#methodNO").val() + "&modelType=" + $("#modelType").combobox('getText') 
			+ "&enabledFlag=" + $("input[name='enabledFlag']:checked").val() + "&remark=" + $("#remark").val()
			+ "&isSyncSolution=" + $("input[name='isSyncSolution']:checked").val();
		processSaveAjax(reqAjaxPrams);
	}
}

// 删除最终故障信息
function zzgz_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				var	getCurrentId = row.id;
				var url = ctx + "/zgzmanagecon/deleteInfo";
				var param = "id=" + getCurrentId;
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
function zzgz_addInfo() {
	clearFromParams();
	zgzwindowOpen();
	chageWinSize('zzgz_window');
}
// 弹出修改框
function zzgz_updateInfo() {

	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated) {
		var getCurrentId = updated.id;

		var url = ctx + "/zgzmanagecon/getInfo";
		var param = "id=" + getCurrentId;

		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			var entity = returnData.data;
			// TODO 数据展示
			$("#id").val(entity.id);
			$("#type").combobox('setValue', entity.gid);
			$("#proceMethod").val(entity.proceMethod);
			$("#methodNO").val(entity.methodNO);
			$("#modelType").combobox('setValue', entity.modelType);
			$("#remark").val(entity.remark);
			if (entity.enabledFlag == "1") {
				$("input[name='enabledFlag'][value='1']").prop('checked', true);
			} else {
				$("input[name='enabledFlag'][value='0']").prop('checked', true);
			}
			if (entity.isSyncSolution == "0") {
				$("input[name='isSyncSolution'][value='0']").prop('checked', true);
			} else {
				$("input[name='isSyncSolution'][value='1']").prop('checked', true);
			}
			zgzwindowOpen();
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

// 类型下拉框的公用方法
function comboboxInit1() {
	var url = ctx + '/basegroupcon/queryList';
	var param = "enumSn=" + $("#LX_Type1").val();
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		comboboxLoadType1(returnData);
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function comboboxLoadType1(returnData) {
	var diclist = '[';
	$.each(returnData.data, function(i, j) {
		diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
	});
	var reg = /,$/gi;
	diclist = diclist.replace(reg, "");
	diclist += ']';
	$('#modelType').combobox({
		data : eval('(' + diclist + ')'),
		valueField : 'id',
		textField : 'text',
		onLoadSuccess : function(data) {
			if (data) {
				$('#modelType').combobox('setValue', data[0].id);
			}
		}
	});
}

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("BASE-DATA/Excel-ZZGZ-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("zgzmanagecon/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("zgzmanagecon/ExportDatas?gId=" + $("#gIds").val());
}

/**
 * 刷新
 */
function refreshInfo() {
	queryListPage(currentPageNum);
}

function zgzwindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#zzgz_window').window('open');
	windowVisible("zzgz_window");
}
function zgzwindowClose() {
	$('#zzgz_window').window('close');
}

function windowClose1() {
	$('#wg').window('close');
}