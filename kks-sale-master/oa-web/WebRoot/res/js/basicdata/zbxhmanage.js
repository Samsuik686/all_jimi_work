var currentPageNum;
//保存上次查询条件
var lastMarketModel;
var lastModel;
var lastGid;
var lastCreateType;
var lastEnabledFlag;
$(function() {
	// basegroupTreeInit()
	zbxhTreeInit('BASE_ZBXH');
	zbxhTreeInit("BASE_CJLX");
	datagridInit();
	// basegroupTreeSelect();
	zbxhTypeTreeSelect('BASE_CJLX');
	zbxhTypeTreeSelect('BASE_ZBXH');
	comboboxInit();
	zbxhComboboxInit("BASE_CJLX");
	keySearch(queryListPage);
});

function queryListPage(pageNumber) {// 初始化数据及查询函数
	var createNode = $("#createTypeTree").tree("getSelected");
	var id="";
	if(createNode != null && createNode != undefined){
		id = createNode.id;
	}
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	// var param="gId="+$("#gIds").val()+"&model="+$("#searchBymodel").val()+"&marketModel="+$("#searchBymarketModel").val()+"&currentpage="+currentPageNum;
	var selParams = "gId=" + $("#gIds").val() + "&model=" + $.trim($("#searchBymodel").val()) + "&marketModel=" + $.trim($("#searchBymarketModel").val())
					+ "&createType=" + id + "&enabledFlag=" + $("#searchByEnabledFlag").combobox('getValue');
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	var url = ctx + "/zbxhmanage/zbxhmanageList";
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			lastGid = $("#gIds").val();
			lastMarketModel =$.trim($("#searchBymarketModel").val());
			lastModel = $.trim($("#searchBymodel").val());
			lastCreateType = id;
			lastEnabledFlag = $("#searchByEnabledFlag").combobox('getValue');
			$.each(returnData.data, function(i, item) {
				if (item.enabledFlag == 0) {
					item.enabledFlag = '<label style="color:green;font-weight: bold;">否</label>';
				} else if (item.enabledFlag == 1) {
					item.enabledFlag = '<label style="color:red;font-weight: bold;">是</label>';
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

$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};

function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			zbxh_updateInfo(rowIndex, row);
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

function zbxh_addInfo() {

	$("#mId_hidden").val(0);
	// 清空下缓存
	$("#modelP").val("");
	$("#marketModelP").val("");
	$("#remarkP").val("");
	$("input[name='createType'][value='0']").prop('checked', true);// 默认
	$("input[name='enabledFlag'][value='0']").prop('checked', true);// 默认不禁用
	// 加载所有MenuId及对应的FunctionId
	// loadAllMenuFunction();
	updateWindowOpen();

	// 改变弹出框位置add by wg.he 2013/12/10
	chageWinSize('updateWindow');
	comboboxInit();

}

function zbxhmanageSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var model = $("#modelP").val().toUpperCase().trim();
		var marketModel = $("#marketModelP").val().toUpperCase().trim();
		var gId = $("#type").combobox('getValue');
		var modelType = $("#type").combobox('getText');
		var createType = $("#addCreateType").combobox('getValue');
		console.log("创建类型"+createType);
		var remark = $("#remarkP").val().trim();
		var reqAjaxPrams = "mId=" + $("#mId_hidden").val() + "&model=" + model + "&gId=" + gId + "&marketModel=" + marketModel +
			"&modelType=" + modelType + "&createType=" +  createType +
			"&enabledFlag=" + $("input[name='enabledFlag']:checked").val() + "&remark="+ remark + "&id=0" ;
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/zbxhmanage/addOrUpdateZbxhmanage";
	var id = $("#mId_hidden").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0) {
				queryListPage(currentPageNum);// 重新加载List
			}else{
				queryListPage(1);// 重新加载List
			}
			
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function zbxh_updateInfo() {
	var rows = $('#DataGrid1').datagrid('getSelections');
	if(rows.length >1){
		$.messager.alert("提示信息", "请先选择一行数据删除！", "info");
		return;
	}
	var row = rows[0];
	if (row) {
		var param = "mId=" + row.mid;
		var url = ctx + "/zbxhmanage/getZbxhmanage";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			$("#mId_hidden").val(returnData.data.mid);
			$("#modelP").val(returnData.data.model.toUpperCase());
			$("#marketModelP").val(returnData.data.marketModel.toUpperCase());
			$("#type").combobox('setValue', returnData.data.gId);
			$("#type").combobox('setText', returnData.data.modelType);
			$("#addCreateType").combobox('setText', returnData.data.createTypeName);
			$("#addCreateType").combobox('setValue', returnData.data.createType);
			$("#remarkP").val(returnData.data.remark);
			if (returnData.data.enabledFlag == "1") {
				$("input[name='enabledFlag'][value='1']").prop('checked', true);
			} else {
				$("input[name='enabledFlag'][value='0']").prop('checked', true);
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
function zbxh_deleteInfo() {
	var rows = $('#DataGrid1').datagrid('getSelections');
	if(rows.length >1){
		$.messager.alert("提示信息", "请先选择一行数据删除！", "info");
		return;
	}
	var row = rows[0];
	if (row) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
					var getMId = row.mid;
					var url = ctx + "/zbxhmanage/deleteZbxhmanage";
					var param = "mId=" + getMId;
					if (getMId != -1) {

						asyncCallService(url, 'post', false, 'json', param, function(returnData) {

							processSSOOrPrivilege(returnData);

							if (returnData.code == '0') {
								queryListPage(currentPageNum);// 重新加载ZbxhmanageList
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
	downLoadExcelTmp("BASE-DATA/Excel-ZBXH-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("zbxhmanage/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	ExportExcelDatas("zbxhmanage/ExportDatas?gId=" + $("#gIds").val() + "&model=" + $.trim($("#searchBymodel").val()) + "&marketModel=" + $.trim($("#searchBymarketModel").val())
			+ "&createType=" + $("#searchByCreateType").combobox('getValue') + "&enabledFlag=" + $("#searchByEnabledFlag").combobox('getValue'));
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
}
//批量修改的下拉框
function zbxhBatchModifyComboboxInit(id,type){
	var data = zbxhQueryType(type);
	zbxhComboboxLoadType(id,data);
}
function closeWindows(id){
	console.log(id);
	$('#'+id).window('close');
}

// 打开增加或修改窗口
function batchWindowShow() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#batchMoidfyWindow').window('open');
	windowVisible("batchMoidfyWindow");
}
var isAll=false;
//批量修改创建类型和型号类别
function batchModify(){
	//初始化型号类别和创建类型
	zbxhBatchModifyComboboxInit("batchType","BASE_ZBXH");
	zbxhBatchModifyComboboxInit("batchCreateType","BASE_CJLX");
	var rows = $('#DataGrid1').datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm("操作提示", "是否修改<span style='color: red'>选中的</span>数据(仅当前页)？", function(conf) {
			if(conf){
				isAll=false;
				batchWindowShow();
			}

		});
	}else{
		$.messager.confirm("操作提示", "是否修改<span style='color: red'>全部查询</span>数据？", function(conf) {
			if (conf) {
				isAll=true;
				batchWindowShow();
			}
		});
	}

}

//批量修改主板类型和创建类型
function doBatchModify(){
	var param={};
	//所有查询出来的都修改，则需要把上次查询条件传到后端
	param.newGid=$("#batchType").combobox('getValue');
	param.newCreateType=$("#batchCreateType").combobox('getValue');
	if(isAll){
		param.gId = lastGid;
		param.model = lastModel;
		param.marketModel = lastMarketModel;
		param.createType = lastCreateType;
		param.enabledFlag = lastEnabledFlag;
		param.mids=new Array();
	}else{
		var rows = $('#DataGrid1').datagrid('getSelections');
		var mids=new Array();
		for(var i=0;i<rows.length;i++){
			mids.push(rows[i].mid);
		}
		param.mids=mids;
	}
	console.log(param);
	var url = ctx+"/zbxhmanage/batchModify";
	//修改主板类型和创建类型
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			closeWindows("batchMoidfyWindow");
			queryListPage(currentPageNum);// 重新加载ZbxhmanageList
		}
		$.messager.alert("操作提示", returnData.msg, "info");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}