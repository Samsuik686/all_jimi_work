var currentPageNum;

$(function() {
	//初始化主板类型选择框
	// zbxhComboboxInit();
	queryListPage(1);
});

$(document).ready(function() {

});

/*
function zbxhComboboxInit(){
	$.get(ctx+"/basegroupcon/queryList?enumSn=BASE_ZBXH",function(data){//后台请求

		var options=$("#zbxhCombobox").combobox('options');
		options.textField="gname";
		options.valueField="gid";
		//加载数据
		var all={gid:0,gname:"全选"};
		data.data.push(all);
		$("#zbxhCombobox").combobox("loadData",data.data);
	})
}
 */

function clickFocus() {
	document.getElementById("imei").style.height = "120px";
	document.getElementById("imei").style.overflow = "auto";
}

function clickBlur() {
	document.getElementById("imei").style.height = "26px";
	document.getElementById("imei").style.overflow = "hidden";
}

// 新增弹窗
function warranty_add() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if (row.length < 1) {
		$.messager.alert("操作提示", "请选择设备", "info");
		return;
	}

	windowVisible("warranty_add");
	$('#warranty_add').window('open');

	var imeiStr = '';
	for (var i = 0; i < row.length; i++) {
		imeiStr += row[i].imei + ',';
	}
	imeiStr = imeiStr.replace(/\,/g, '\n');

	$("#imei_add").val(imeiStr);
}

// 重复提醒
function warranty_repeat(row){
	windowVisible("warranty_repeat");
	$('#warranty_repeat').window('open');

	var imeiStr = row.join(",");
	imeiStr = imeiStr.replace(/\,/g, '\n');

	$("#imei_repeat").val(imeiStr);
}

function prolongOther() {
	if ($("#prolongMonth").val() == "other") {
		$("#cs_text").css('display', '');
	} else {
		$("#cs_text").css('display', 'none');
	}
}

function monthNumber() {
	if ($("#cs_number").val() > 200) {
		$("#cs_number").val(200);
		$.messager.alert("操作提示", "自定义月数范围1-200", "info");
	}
	if ($("#cs_number").val() < 1) {
		$("#cs_number").val(1);
		$.messager.alert("操作提示", "自定义月数范围1-200", "info");
	}
}

// 设备延保
function saveWarranty() {
	var row = $('#DataGrid1').datagrid('getSelections');

	var imeiStr = row[0].imei;
	for (var i = 1; i < row.length; i++) {
		imeiStr += ',' + row[i].imei;
	}

	var prolongMonth = $("#prolongMonth").val();
	if (prolongMonth == "other") {
		prolongMonth = $("#cs_number").val();
	}

	$("#saveButton").linkbutton("disable");
	var url = ctx + "/workflowcon/setWarrantyInfo";
	var param = {
		"prolongMonth" : prolongMonth,
		"imeis" : imeiStr
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			warranty_repeat(returnData.data);
			closeWindow();
		}
		if (returnData.code == '0') {
			closeWindow();
			//queryListPage(currentPageNum);// 重新加载RoleList
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#saveButton").linkbutton("enable");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	
	if($("#imei").val()){
		var patt = /^[0-9\n]+$/i;
		if(!patt.test($("#imei").val())){
			$.messager.alert("操作提示","请输入正确的IMEI","info");
			return;
		}
	}
	var imeis = $("#imei").val().trim().replace(/\n/g, ',');

	var url = ctx + "/workflowcon/getDeviceInfo";
	var param = {
		"currentpage" : currentPageNum,
		"pageSize" : pageSize,
		"imeis" : imeis,
		"mcType" : $("#mcType").val().trim(),
		"sfVersion" : $("#sfVersion").val().trim(),
		"cpName" : $("#cpName").val().trim(),
		"bill" : $("#bill").val().trim(),
		"outDateStart" : $("#outDateStart").val().trim(),
		"outDateEnd" : $("#outDateEnd").val().trim(),
		"productionDateStart" : $("#productionDateStart").val().trim(),
		"productionDateEnd" : $("#productionDateEnd").val().trim()
		// "zbxhId":$("#zbxhCombobox").combobox('getValue'),
	}

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		}
		$("#DataGrid1").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 查询
function search_data() {
	queryListPage(1);
}

// 刷新
function refreshInfo() {
	$("#searchImei").val("");
	queryListPage(currentPageNum);
}

function closeWindow() {
	clean();
	$('#warranty_add').window('close');
}

function closeRepeatWindow() {
	clean();
	$('#warranty_repeat').window('close');
}

// 清除新增表单数据
function clean() {
	$("#prolongMonth").val(0);
	$("#imei_add").val("");
}

//批量导入
function batchAddWindow(){

	windowVisible("batchWarranty");
	$('#batchWarranty').window('open');

	// var imeiStr = '';
	// for (var i = 0; i < row.length; i++) {
	// 	imeiStr += row[i].imei + ',';
	// }
	// imeiStr = imeiStr.replace(/\,/g, '\n');

	// $("#imei_add").val(imeiStr);
}
//自定义月数
function batchProlongOther() {
	if ($("#batchProlongMonth").val() == "other") {
		$("#batch_text").css('display', '');
	} else {
		$("#batch_text").css('display', 'none');
	}
}
//批量导入窗口关闭
function batchCloseWindow() {
	$("#batchProlongMonth").val(0);
	$('#batchWarranty').window('close');
}
// 设备延保
function batchSaveWarranty() {
	var prolongMonth = $("#batchProlongMonth").val();
	if (prolongMonth == "other") {
		prolongMonth = $("#batch_number").val();
	}

	$("#batchSaveButton").linkbutton("disable");
	var url = ctx + "/workflowcon/ImportWarranty";
	var file = $('#file')[0].files[0];
	console.log(file);
	var fd = new FormData();
	fd.append("file",file);
	fd.append("prolongMonth",prolongMonth);
	$.ajax({
		url:url,
		type:'POST',
		data:fd,
		processData: false,
		contentType:false,
		success:function(result){
			processSSOOrPrivilege(result);
			console.log(result);
			$.messager.alert("操作提示",result.msg,"info");
			$.messager.alert("操作提示",result.msg,"info");
		},
		error:function (result) {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
	/*
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			warranty_repeat(returnData.data);
			closeWindow();
		}
		if (returnData.code == '0') {
			closeWindow();
			//queryListPage(currentPageNum);// 重新加载RoleList
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#saveButton").linkbutton("enable");
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});

	 */
}