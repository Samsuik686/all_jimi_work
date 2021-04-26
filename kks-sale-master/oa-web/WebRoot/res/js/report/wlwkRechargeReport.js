var currentPageNum;
var isBatchSearch = false;// 是否点击批量查询
var searchNOstTemp; // 临时保存批量查询输入的号码

$(function() {
	datagridInit();
	keySearch(queryListPage);
});

function queryComm(type, param) {
	if (type == "batch") {
		isBatchSearch = true;
		$("#phoneNumber").val("");
		$("#phoneNumber").attr("disabled", true);
	} else if (type == "search") {
		$("#searchNO").val("");
		isBatchSearch = false;
		$("#phoneNumber").removeAttr("disabled");
	}
	var searchNOs = $.trim($("#searchNO").val());
	if (searchNOs) {
		searchNOstTemp = searchNOs.replace(/\ +/g, ",").replace(/[\r\n]/g, ",");
		param['searchNOs'] = searchNOstTemp;
	}
	if (!isBatchSearch) {
		searchNOstTemp = "";
	}
}

/**
 * 初始化数据及查询函数
 * 
 * @param pagepayOther
 */
function queryListPage(pagepayOther, type) {

	currentPageNum = pagepayOther;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	if (!$("#searchForm").form('validate')) {
		return;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/wlwkRechargeReport/rechargeList";

	var param = {
		"startDate" : $("#startTime").val(),
		"endDate" : $("#endTime").val(),
		"phone" : $.trim($("#phoneNumber").val()),
		"cusName" : $.trim($("#search_cusName").val()),
		"orderStatus" : $("#search_status").combobox('getValue'),
		"searFormulas" : $("#search_formula").combobox('getValues').join(","),
		"remittanceMethod" : $("#remittanceMethodSearch").combobox('getValue'),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	}

	queryComm(type, param);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(
			url,
			'post',
			false,
			'json',
			param,
			function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == undefined) {
					var ret = returnData.data;
					closeBatchSearch();// 关闭批量查询窗口
					if (ret && ret.length > 0) {
						$
								.each(
										ret,
										function(i, item) {
											if (item.payOther
													|| item.payOther == '0') {
												item.payOther = item.payOther
														.toFixed(2);
											} else {
												item.payOther = '0.00';
											}

											if (item.faceValue
													|| item.faceValue == '0') {
												item.faceValue = item.faceValue
														.toFixed(2);
											} else {
												item.faceValue = '0.00';
											}

											if (item.payable
													|| item.payable == '0') {
												item.payable = item.payable
														.toFixed(2);// 两位小数
											} else {
												item.payable = '0.00';
											}

											if (item.ratePrice
													|| item.ratePrice == '0') {
												item.ratePrice = item.ratePrice
														.toFixed(2);
											} else {
												item.ratePrice = '0.00';
											}
											// 付款方式
											if (item.remittanceMethod == 'personPay') {
												item.remittanceMethod = "<label style='font-weight: bold;'>人工付款</label>";
											} else if (item.remittanceMethod == 'aliPay') {
												item.remittanceMethod = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
											} else if (item.remittanceMethod == 'weChatPay') {
												item.remittanceMethod = "<label style='color:green;font-weight: bold;'>微信付款</label>";
											}
											// 确认状态
											if (item.orderStatus == '已充值') {
												item.orderStatus = "<strong style='color:green'>已充值</strong>";
											} else if (item.orderStatus == '待充值') {
												item.orderStatus = "<strong style='color:red'>待充值</strong>";
											}
											if (i == ret.length - 1) {
												item.faceValue = "";
												item.payOther = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>"
														+ item.payOther
														+ "</label>";
												item.payable = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>"
														+ item.payable
														+ "</label>";
												item.ratePrice = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>"
														+ item.ratePrice
														+ "</label>";
												item.monthNumber = "<label style='font-weight: bold;'>"
														+ item.monthNumber
														+ "</label>";
											}
										});
					}
					$("#DataGrid1").datagrid('loadData', returnData.data);
					querySum(type);
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

function querySum(type) {
	var url = ctx + "/wlwkRechargeReport/querySum";
	var param = {
		"startDate" : $("#startTime").val(),
		"endDate" : $("#endTime").val(),
		"phone" : $.trim($("#phoneNumber").val()),
		"cusName" : $.trim($("#search_cusName").val()),
		"orderStatus" : $("#search_status").combobox('getValue'),
		"searFormulas" : $("#search_formula").combobox('getValues').join(","),
		"remittanceMethod" : $("#remittanceMethodSearch").combobox('getValue')
	}
	queryComm(type, param);
	$("#DataGrid2").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if (item.costs || item.costs == '0') {
						item.costs = item.costs.toFixed(2);
					} else {
						item.costs = '0.00';
					}
				});
			}
			$("#DataGrid2").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#DataGrid2").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};

/**
 * 初始化表格
 */
function datagridInit() {
	$("#DataGrid1")
			.datagrid(
					{
						singleSelect : false,// 是否单选
						onDblClickRow : function(rowIndex, row) {
							if (rowIndex != $("#DataGrid1").datagrid('getData').rows.length - 1) {
								wlwk_recharge_updateInfo(rowIndex, row);
							}
						}
					});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

/**
 * 清空缓存
 */
function wlwk_recharge_addInfo() {
	windowVisible("updateWindow");
	$("#wlwkId").val(0);
	$("#cusName").val("");
	$("#rechargeDate").val(initTime());
	$("#faceValue").val("");
	$("#phone").val("");
	$("#payOther").val("");
	$("#faceValue").val("");
	$("#monthNumber").val("");
	$("#payable").val("");
	$("#orderStatus").combobox('setValue', "已充值");
	// $("#account").val("");
	$("#remittanceMethod").combobox('setValue', "");
	$("#formula").val("");
	$("#ratePrice").val("");
	$("#remark").val("");
	updateWindowOpen();
	chageWinSize('updateWindow');
}

/**
 * 插入数据
 */
function wlwk_rechargeSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var row = $('#DataGrid1').datagrid('getSelections');
		var wlwkIds = [];
		for (var i = 0; i < row.length; i++) {
			wlwkIds.push(row[i].wlwkId);
		}

		var cusName = $("#cusName").val();
		var rechargeDate = $("#rechargeDate").val();
		if (!rechargeDate) {
			$("#rechargeDate").val(initTime());
		}
		rechargeDate = $("#rechargeDate").val();
		var faceValue = $("#faceValue").val();
		var phone = $("#phone").val();
		var payOther = $("#payOther").val();
		var ratePrice = $("#ratePrice").val();
		var formula = $("#formula").val();
		var monthNumber = $("#monthNumber").val();
		var payable = $("#payable").val();
		var orderStatus = $("#orderStatus").combobox('getValue');
		// var account = $("#account").val();
		var remittanceMethod = $("#remittanceMethod").combobox('getValue');
		var reqAjaxPrams = {
			"wlwkIdsStr" : wlwkIds.toString(),
			"cusName" : cusName,
			"rechargeTime" : rechargeDate,
			"formula" : formula,
			"faceValue" : faceValue,
			"phone" : phone,
			"payOther" : payOther,
			"monthNumber" : monthNumber,
			"payable" : payable,
			"orderStatus" : orderStatus,
			// "account" : account,
			"remittanceMethod" : remittanceMethod,
			"ratePrice" : ratePrice,
			"remark" : $.trim($("#remark").val())
		}
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/wlwkRechargeReport/addOrUpdateRecharge";
	var id = $("#wlwkId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(
			returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载rechargeList
			} else {
				queryListPage(1);// 重新加载rechargeList
			}

		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function checkBatch(row, attribute) {
	var temp = row[0][attribute];
	for (var i = 1; i < row.length; i++) {
		if (row[i][attribute] != temp) {
			if (attribute == "remittanceMethod" || attribute == "orderStatus") {
				$("#" + attribute).combobox({
					disabled : true
				});
				$("#" + attribute).combobox("setValue", "");
				return;
			}

			$("#" + attribute).attr("disabled", true);
			$("#" + attribute).val("");
			return;
		}
	}

	if (attribute == "remittanceMethod" || attribute == "orderStatus") {
		$("#" + attribute).combobox({
			disabled : false
		});
		var reg = new RegExp('[\u4e00-\u9fa5]+$','g');
		temp = temp.match(/[\u4e00-\u9fa5]/g).join("");
		$("#" + attribute).combobox("setValue", temp);
		return;
	}
	$("#" + attribute).attr("disabled", false);
	$("#" + attribute).val(temp);
}

function wlwk_recharge_updateInfo() {

	var row = $('#DataGrid1').datagrid('getSelections');

	if (row.length < 1) {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		return;
	}
	windowVisible("updateWindow");

	for (var i = 0; i < row.length; i++) {
		if ($('#DataGrid1').datagrid('getRowIndex', row[i]) == $("#DataGrid1")
				.datagrid('getData').rows.length - 1) {
			$.messager.alert("提示信息", "总计行不能进行编辑操作！", "info");
			return;
		}
	}

	updateWindowOpen();
	chageWinSize('updateWindow');

	if (row.length > 0) {
		checkBatch(row, "cusName");
		checkBatch(row, "rechargeDate");
		checkBatch(row, "faceValue");
		checkBatch(row, "phone");
		checkBatch(row, "payOther");
		checkBatch(row, "monthNumber");
		checkBatch(row, "orderStatus");
		checkBatch(row, "remittanceMethod");
		checkBatch(row, "formula");
		checkBatch(row, "ratePrice");
		checkBatch(row, "payable");
		checkBatch(row, "remark");
	}
}

function wlwk_recharge_updateInfo1() {
	if ($('#DataGrid1').datagrid('getRowIndex',
			$("#DataGrid1").datagrid('getSelected')) != $("#DataGrid1")
			.datagrid('getData').rows.length - 1) {
		windowVisible("updateWindow");
		var row = $('#DataGrid1').datagrid('getSelected');
		if (row) {
			var param = "wlwkId=" + row.wlwkId;
			var url = ctx + "/wlwkRechargeReport/getRecharge";
			asyncCallService(url, 'post', false, 'json', param, function(
					returnData) {
				processSSOOrPrivilege(returnData);
				$("#wlwkId").val(returnData.data.wlwkId);
				$("#cusName").val(returnData.data.cusName);
				$("#rechargeDate").val(returnData.data.rechargeDate);
				$("#faceValue").val(returnData.data.faceValue);
				$("#phone").val(returnData.data.phone);
				$("#payOther").val(returnData.data.payOther);
				$("#faceValue").val(returnData.data.faceValue);
				$("#monthNumber").val(returnData.data.monthNumber);
				$("#payable").val(returnData.data.payable);
				$("#orderStatus").combobox('setValue',
						returnData.data.orderStatus);
				$("#remittanceMethod").combobox('setValue',
						returnData.data.remittanceMethod);
				$("#formula").val(returnData.data.formula);
				$("#ratePrice").val(returnData.data.ratePrice);
				$("#remark").val(returnData.data.remark);
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
			updateWindowOpen();
			chageWinSize('updateWindow');
		} else {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		}
	} else {
		$.messager.alert("提示信息", "此行不能进行修改操作！", "info");
	}
}

// 时分秒十以内前面加0
function addZero(v) {
	if (v < 10) {
		return '0' + v;
	} else {
		return v;
	}
}

// 获得当前时间
function initTime() {
	var date = new Date();
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-"
			+ addZero(date.getDate()) + " " + addZero(date.getHours()) + ":"
			+ addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

/**
 * 删除
 */
function wlwk_recharge_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelections');
	var wlwkIds = [];
	var tempRow = [];
	for (var i = 0; i < row.length; i++) {
		if ($('#DataGrid1').datagrid('getRowIndex', row[i]) == $("#DataGrid1")
				.datagrid('getData').rows.length - 1) {
			$.messager.alert("提示信息", "总计行不能进行删除操作！", "info");
			return;
		}
		wlwkIds.push(row[i].wlwkId);
		tempRow.push(row[i]);
	}

	if (row.length > 0) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				for (var i = 0; i < tempRow.length; i++) {
					var index = $('#DataGrid1').datagrid('getRowIndex',
							tempRow[i]);
					$('#DataGrid1').datagrid('deleteRow', index);
				}

				var url = ctx + "/wlwkRechargeReport/deleteRecharge";
				var param = "wlwkIdsStr=" + wlwkIds;

				asyncCallService(url, 'post', false, 'json', param, function(
						returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);// 重新加载rechargeList
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

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("REPORTS/Excel-WLWK_RECHARGE-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("wlwkRechargeReport/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url = ctx + "/wlwkRechargeReport/ExportDatas";
	var $form1 = $("<form action='" + url
			+ "' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='startDate' value='"
			+ $("#startTime").val() + "'/>"));
	$form1.append($("<input type='hidden' name='endDate' value='"
			+ $("#endTime").val() + "'/>"));
	$form1.append($("<input type='hidden' name='cusName' value='"
			+ $.trim($("#search_cusName").val()) + "'/>"));
	$form1.append($("<input type='hidden' name='orderStatus' value='"
			+ $("#search_status").combobox('getValue') + "'/>"));
	$form1.append($("<input type='hidden' name='searFormulas' value='"
			+ $("#search_formula").combobox('getValues').join(",") + "'/>"));
	$form1.append($("<input type='hidden' name='remittanceMethod' value='"
			+ $("#remittanceMethodSearch").combobox('getValue') + "'/>"));
	if (isBatchSearch) {
		$form1.append($("<input type='hidden' name='searchNOs' value='"
				+ searchNOstTemp + "'/>"));
	} else {
		$form1.append($("<input type='hidden' name='phone' value='"
				+ $.trim($("#phoneNumber").val()) + "'/>"));
	}
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
}

function batchSearch() {
	windowVisible("batchSearchWindow");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#batchSearchWindow').window('open');
	$("#phoneNumber").val("")
	$("#phoneNumber").attr("disabled", true);
}

function closeBatchSearch() {
	$('#batchSearchWindow').window('close');
}
