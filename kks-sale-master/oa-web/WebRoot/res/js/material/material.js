$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
var currentPageNumTwo;
$(function() {
	datagridInit();
	keySearch(queryListPage);
});

/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,
		onDblClickRow : function(rowIndex, row) {
			updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);

	$("#OrderDataGrid").datagrid({
		singleSelect : false
	});

	$("#LogInfoDataGrid").datagrid({
		singleSelect : false
	});
}

/**
 * 初始化列表 初始化数据及查询函数
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
	var url = ctx + "/materiacon/queryListPage";
	var param = {
		"proNO" : $.trim($("#s_proNO").val()),
		"proName" : $.trim($("#s_proName").val()),
		"materialType" : $("#s_materialType").combobox('getValue'),
		"currentpage" : currentPageNum,
		"pageSize" : pageSize
	} 
	 $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data,function(i, item) {
				if (undefined != item.analyzeNum && item.totalNum < item.analyzeNum) {
					item.totalNum = "<label style='color:red;font-weight: bold;'>" + item.totalNum + "</label>";
				}
				if (item.materialType == '0') {
					item.materialType = '配件料';
				}
				if (item.materialType == '1') {
					item.materialType = '电子料';
				}
			});

			$("#DataGrid1").datagrid('loadData', returnData.data);
		    getPageSize();
		    getPageSizeTwo("OrderDataGrid");
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
 * 加载显示数据
 */
function loadEntity(entity) {
	// TODO 基础数据 数据显示
	$("#id").val(entity.id);
	var material_type = 0;
	if(entity.materialType == "配件料"){
		material_type = 0;
	}else if(entity.materialType == "电子料"){
		material_type = 1;
	}
	$("#materialType").combobox('setValue',material_type);
	$("#proNO").val(entity.proNO);
	$("#proName").val(entity.proName);
	$("#proSpeci").val(entity.proSpeci);
	$("#lossType").val(entity.lossType);
	$("#consumption").val(entity.consumption);
//	if(entity.masterOrSlave){
//		$("#masterOrSlave").combobox("setValue", entity.masterOrSlave);
//	}else{
//		$("#masterOrSlave").combobox("setValue", "M");
//	}
	$("#masterOrSlave").val(entity.masterOrSlave);
	if (isNaN(entity.totalNum)) {
		var totalNum = entity.totalNum.substring(entity.totalNum.indexOf(">") + 1, entity.totalNum.lastIndexOf("<"));
		$("#totalNum").val(totalNum);
	} else {
		$("#totalNum").val(entity.totalNum);
	}
	// 业务管理数据
	if(entity.materialType == "电子料"){
		$(".tipShow").css("visibility","hidden");
		$("#retailPrice").val(entity.retailPrice);
//		$('#retailPrice').validatebox({required:false});
	}else{
		$(".tipShow").css("visibility","visible");
		$("#retailPrice").val(entity.retailPrice);
//		$('#retailPrice').validatebox({required:true});
	}
	$("#createTime").datebox("setValue", entity.createTime);
	$("#analyzeNum").val(entity.analyzeNum);
	$("#remark").val(entity.remark);
}

/**
 * 清除数据
 */
function clearFrom() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$("#proNO").val("");
	$("#proName").val("");
	$("#proSpeci").val("");
	$("#lossType").val("");
	$("#consumption").val(0);
	$("#totalNum").val(0);
//	$("#masterOrSlave").combobox("setValue","M");
	$("#masterOrSlave").val("");
	$("#retailPrice").val(0);
	$("#createTime").datebox("setValue", "");
	$("#analyzeNum").val("");
	$("#remark").val("");
}

/**
 * 处理修改事件
 */
function updateInfo(rowIndex, entity) {
	clearFrom();
	if (entity) {
		windowCommOpen("w_updateMaterial");
		windowVisible("w_updateMaterial");
		loadEntity(entity);
	} else {
		var entity = $('#DataGrid1').datagrid('getSelected');
		if (entity) {
			windowCommOpen("w_updateMaterial");
			windowVisible("w_updateMaterial");
			loadEntity(entity);
		} else {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		}
	}
}
/**
 * 更新信息
 */
function InfoSave() {
	var reqAjaxPrams = {
			"id" : $("#id").val(),
			"materialType" : $("#materialType").combobox('getValue'),
			"proNO" : $.trim($("#proNO").val()),//配件料同步修改价格使用
//			"masterOrSlave" : $("#masterOrSlave").combobox('getValue'),
			"lossType" : $.trim($("#lossType").val()),
			"retailPrice" : $.trim($("#retailPrice").val()),
			"analyzeNum" : $.trim($("#analyzeNum").val()),
			"totalNum" : $.trim($("#totalNum").val()),
			"remark" : $.trim($("#remark").val())
		}
	processSaveAjax(reqAjaxPrams);
}

/**
 * 是否同步刷新
 * 
 * @param reqAjaxPrams
 */
function processSaveAjax(reqAjaxPrams) {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var url = ctx + "/materiacon/updateInfo";
		asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == '0') {
				queryListPage(currentPageNum);
				windowCommClose('w_updateMaterial');
			}
			$.messager.alert("操作提示", returnData.msg, "info");
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	} 
}

//***********************为实现一个页面中，第二个翻页功能 ********************************//
/**
 * 生成物料需求单
 */
function queryListPageTwo(pageNumber) {
	windowCommOpen("w__MaterialOrder");
	windowVisible("w__MaterialOrder");
	currentPageNumTwo = pageNumber;
	if (currentPageNumTwo == "" || currentPageNumTwo == null) {
		currentPageNumTwo = 1;
	}
	var pageSize = getCurrentPageSize('OrderDataGrid');
	var url = ctx + "/materiacon/getOrderList";
	var param = {
			"materialType" : $("#s_materialType").combobox('getValue'),
			"currentpage" : currentPageNumTwo,
			"pageSize" : pageSize
	} 
	$("#OrderDataGrid").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(
			returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				if (item.materialType == '0') {
					item.materialType = '配件料';
				}
				if (item.materialType == '1') {
					item.materialType = '电子料';
				}
			});
			$("#OrderDataGrid").datagrid('loadData', returnData.data);
			getPageSizeTwo("OrderDataGrid");
			resetCurrentPageShowT("OrderDataGrid",currentPageNumTwo);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#OrderDataGrid").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


function showInfo(pageNumber){
	var row = $('#DataGrid1').datagrid('getSelected');
	if (row && row.proNO) {
		var pageSize = getCurrentPageSize('LogInfoDataGrid');
		var param =
			{
				"proNO" : row.proNO,
				"currentpage" : currentPageNum,
				"pageSize" : pageSize
			};
		var url = ctx + "/materialLog/getMaterialLogListPage";
			$("#LogInfoDataGrid").datagrid('loading');
			asyncCallService(url, 'post', true, 'json', param, function(returnData) {
				var ret = returnData.data;
				if (ret.length > 0) {
					$.each(ret, function(i, j) {
						if (j.totalType == '0') {
							j.totalType = '入库';
						}
						if (j.totalType == '1') {
							j.totalType = '出库';
						}
						if (j.materialType == '0') {
							j.materialType = '配件料';
						}
						if (j.materialType == '1') {
							j.materialType = '电子料';
						}
					});
				}
				processSSOOrPrivilege(returnData);
				if (returnData.code == undefined) {
					$("#LogInfoDataGrid").datagrid('loadData', returnData.data);
					getPageSize();
					resetCurrentPageShow(currentPageNum);
				} else {
					$.messager.alert("操作提示", returnData.msg, "info");
				}
				 $("#LogInfoDataGrid").datagrid('loaded');
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
			windowCommOpen("w_logInfo");
			windowVisible("w_logInfo");
	} else {
		$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
	}
}

/**
 * 导出数据
 */
function ExportOrders(type) {
	if ('order' == type) {
		var url =  ctx + "/materiacon/ExportOrderDatas";
		var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	} else {
		var url =  ctx + "/materiacon/ExportMaterialDatas";
		var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
		$form1.append($("<input type='hidden' name='proNO' value='" + $.trim($("#s_proNO").val()) +"'/>"));
		$form1.append($("<input type='hidden' name='proName' value='" + $.trim($("#s_proName").val()) +"'/>")); 
	}
	$form1.append($("<input type='hidden' name='materialType' value='" + $("#s_materialType").combobox('getValue') +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}
// --------------------------------------------- 列表管理
// ------------------------------------------ End
