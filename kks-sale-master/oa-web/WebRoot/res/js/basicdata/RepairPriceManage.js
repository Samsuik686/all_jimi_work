var currentPageNum;

$(function() {
	basegroupTreeInit();
	datagridInit();
	basegroupTreeSelect();
	comboboxInit();
	keySearch(queryListPage);
	keySearch(queryListPagePJL, 1);
	keySearch(queryListPagePGPJ, 3);
});

function queryListPage(pageNumber) {// 初始化数据及查询函数
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	var useState = $("#useState option:selected").val();
	var param = {
		"gId" : $("#gIds").val(),
		"priceNumber" : $.trim($("#searchNumber").val()),
		"model" : $.trim($("#searchBymodel").val()),
		"useState" : useState,
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	};
	var url = ctx + "/repairPrice/repairPriceList";
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if (item.price || item.price == '0') {
						item.price = item.price.toFixed(2);
					} else {
						item.price = '0.00';
					}

					if (item.useState == '0') {
						item.useState = '<label style="color:green;font-weight: bold;">正常</label>';
					} else if (item.useState == '1') {
						item.useState = '<label style="color:red;font-weight: bold;">禁用</label>';
					} else {
						item.useState = '';
					}
				});
			}
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
		onDblClickRow : function(rowIndex, row) {
			repairPrice_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后

	$("#pjl_DataGrid").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow : function(index, value) {
			dbClickChoosePJL(index, value);
		}
	});
	queryListPagePJL(1);
	
	$("#pgpj_DataGrid").datagrid({
		singleSelect : true,// 是否单选
		onDblClickRow : function(index, value) {
			dbClickChoosePGPJ(index, value);
		}
	});
	queryListPagePGPJ(1);
}

function repairPrice_addInfo() {
	// 清空下缓存
	$("#rid_hidden").val(0);
	$("#repairNumber").val("");
	$("#pjlPrice").val(0);
	$("#price").val(0);
	$("#hourFee").val(0);
	$("#model").val("");
	$("#amount").val("");
	$("#priceNumber").val("");
	$("#idCode").val("");
	$("#pjlId").val("");
	$("#pjlDesc").val("");
	$("#gzbjDesc").val("");
	$("#priceGroupId").val("");
	$("#priceGroupDesc").val("");
	$("input:radio[name=useState][value=0]").prop("checked", true);
	updateWindowOpen();
	chageWinSize('updateWindow');
	comboboxInit();
}

function repairPriceSave() {
	var isValid = $('#rform').form('validate');
	if (isValid) {
		var reqAjaxPrams = {
			"rid" : $("#rid_hidden").val(),
			"gId" : $("#type").combobox('getValue'),
			"model" : $.trim($("#model").val()),
			"priceNumber" : $.trim($("#priceNumber").val()),
			"idCode" : $.trim($("#idCode").val()),
			"price" : $.trim($("#price").val()),
			"hourFee" : $.trim($("#hourFee").val()),
			"repairType" : $("#type").combobox('getText'),
			"amount" : $.trim($("#amount").val()),
			"useState" : $("input:radio[name=useState]:checked").val(),
			"pjlDesc" : $.trim($("#pjlId").val()),
			"priceGroupDesc" : $.trim($("#priceGroupId").val())
		}
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/repairPrice/addOrUpdateRepairPrice";
	var id = $("#rid_hidden").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载List
			} else {
				queryListPage(1);// 重新加载List
			}

		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function repairPrice_updateInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		var param = "rid=" + row.rid;
		var url = ctx + "/repairPrice/getRepairPriceInfo";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			$('#rid_hidden').val(returnData.data.rid);
			$("#model").val(returnData.data.model);
			$("#type").combobox('setValue', returnData.data.gId);
			$("#priceNumber").val(returnData.data.priceNumber);
			$("#idCode").val(returnData.data.idCode);
			$("#price").val(returnData.data.price);
			$("#hourFee").val(returnData.data.hourFee);
			$("#type").combobox('setText', returnData.data.repairType);
			$("#pjlId").val(returnData.data.pjlId);
			$("#pjlDesc").val(returnData.data.pjlDesc);
			$("#priceGroupId").val(returnData.data.priceGroupId);
			$("#priceGroupDesc").val(returnData.data.priceGroupDesc);
			$("#amount").val(returnData.data.amount);
			$("#defaultModel").val(returnData.data.model);
			if (returnData.data.useState) {
				if (returnData.data.useState == '0') {
					$("input:radio[name=useState][value=0]").prop("checked", true);
				} else {
					$("input:radio[name=useState][value=1]").prop("checked", true);
				}
			} else {
				$("input:radio[name=useState][value=0]").prop("checked", true);
			}
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
function repairPrice_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row) {
		$.messager.confirm("操作提示", "是否删除此维修报价信息", function(conf) {
			if (conf) {
				var url = ctx + "/repairPrice/deleteRepairPrice";
				var param = "rid=" + row.rid;
				if (row.rid != -1) {

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
	downLoadExcelTmp("BASE-DATA/Excel-WXBJ-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("repairPrice/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/repairPrice/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='gId' value='" + $("#gIds").val() +"'/>"));
	$form1.append($("<input type='hidden' name='priceNumber' value='" + $.trim($("#searchNumber").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='model' value='" + $.trim($("#searchBymodel").val()) +"'/>")); 
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

// 关闭增加或修改窗口
function updateWindowClose() {
	$('#updateWindow').window('close');
}

// 打开增加或修改窗口
function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	windowVisible("updateWindow");
	$("#defaultModel").val("");//当前的主板型号
}
