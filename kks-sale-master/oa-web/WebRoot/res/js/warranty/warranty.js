var currentPageNum;

$(function() {
	queryListPage(1);
});

function clickFocus() {
	document.getElementById("imei").style.height = "120px";
	document.getElementById("imei").style.overflow = "auto";
}

function clickBlur() {
	document.getElementById("imei").style.height = "26px";
	document.getElementById("imei").style.overflow = "hidden";
}

function monthNumberStart() {
	if ($("#prolongMonthStart").val() > 200) {
		$("#prolongMonthStart").val(200);
		$.messager.alert("操作提示", "自定义月数范围0-200", "info");
	}
	if ($("#prolongMonthStart").val() < 0) {
		$("#prolongMonthStart").val(0);
		$.messager.alert("操作提示", "自定义月数范围0-200", "info");
	}
}

function monthNumberEnd() {
	if ($("#prolongMonthEnd").val() > 200) {
		$("#prolongMonthEnd").val(200);
		$.messager.alert("操作提示", "自定义月数范围0-200", "info");
	}
	if ($("#prolongMonthEnd").val() < 0) {
		$("#prolongMonthEnd").val(0);
		$.messager.alert("操作提示", "自定义月数范围0-200", "info");
	}
}

$(document).ready(function() {
	$('#prolongMonth').combobox({
		onSelect : function(param) {
			if (param.value == "other") {
				$("#cs_text").css('display', 'inline-block');
			} else {
				$("#cs_text").css('display', 'none');
			}
		}
	});
});

// 新增弹窗
function warranty_add() {
	windowVisible("warranty_add");
	$('#warranty_add').window('open');
}

// 删除规则
function warranty_del() {
	var row = $('#DataGrid1').datagrid('getSelections');
	if (row.length > 0) {

		$.messager.confirm("操作提示", "是否删除所选保修规则？", function(flag) {
			if (flag) {
				// 删除
				var url = ctx + "/workflowcon/deleteWarrantyInfo";
				var imeis = row[0].imei;
				for (var i = 1; i < row.length; i++) {
					imeis += "," + row[i].imei;
				}
				var param = {
					"imeis" : imeis,
				}
				asyncCallService(url, 'post', false, 'json', param, function(
						returnData) {
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
		$.messager.alert("提示信息", "请先选择所要删除的保修规则！", "info");
	}
}

// 初始化数据及查询函数
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}

	var pageSize = getCurrentPageSize('DataGrid1');
	var prolongMonth = $("#prolongMonth").combobox('getValue');
	var prolongMonthStart = $("#prolongMonthStart").val();
	var prolongMonthEnd = $("#prolongMonthEnd").val();

	if (prolongMonth == 'other') {
		prolongMonth = -1;
	} else {
		prolongMonthStart = '';
		prolongMonthEnd = '';
	}
	var imeis = $("#imei").val().trim().replace(/\n/g, ',');

	var url = ctx + "/workflowcon/getWarrantyInfo";
	var param = {
		"currentpage" : currentPageNum,
		"pageSize" : pageSize,
		"imeis" : imeis,
		"mcType" : $("#mcType").val().trim(),
		"sfVersion" : $("#sfVersion").val().trim(),
		"bill" : $("#bill").val().trim(),
		"cpName" : $("#cpName").val().trim(),
		"prolongMonth" : prolongMonth,
		"prolongMonthStart" : prolongMonthStart,
		"prolongMonthEnd" : prolongMonthEnd,
		"createTimeStart" : $("#createTimeStart").val(),
		"createTimeEnd" : $("#createTimeEnd").val()
	}

	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var ret = returnData.data;
		if (ret.length > 0) {
			$.each(ret, function(i, j) {
				if (j.prolongMonth == 0) {
					j.prolongMonth = "不保修";
				} else {
					j.prolongMonth += "月";
				}

			})
		}
		;
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}

		$("#DataGrid1").datagrid('loaded');
		WorkTreeInit();
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 导出
function exportData() {
	var prolongMonth = $("#prolongMonth").combobox('getValue');
	var prolongMonthStart = $("#prolongMonthStart").val();
	var prolongMonthEnd = $("#prolongMonthEnd").val();

	if (prolongMonth == 'other') {
		prolongMonth = -1;
	} else {
		prolongMonthStart = '';
		prolongMonthEnd = '';
	}
	
	if($("#imei").val()){
		var patt = /^[0-9\n]+$/i;
		if(!patt.test($("#imei").val())){
			$.messager.alert("操作提示","请输入正确的IMEI","info");
			return;
		}
	}
	
	var imeis = $("#imei").val().trim().replace(/\n/g, ',');

//	var param = 
//		"imeis=" + imeis+
//		"&mcType=" + $("#mcType").val().trim()+
//		"&sfVersion=" + $("#sfVersion").val().trim()+
//		"&bill=" + $("#bill").val().trim()+
//		"&cpName=" + $("#cpName").val().trim()+
//		"&prolongMonth=" + prolongMonth+
//		"&prolongMonthStart=" + prolongMonthStart+
//		"&prolongMonthEnd=" + prolongMonthEnd+
//		"&createTimeStart=" + $("#createTimeStart").val()+
//		"&createTimeEnd=" + $("#createTimeEnd").val();
//	
//	window.open(ctx + "/workflowcon/exportWarrantyData?" + param);
	
	var url =  ctx + "/workflowcon/exportWarrantyData";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='imeis' value='" + imeis +"'/>")); 
	$form1.append($("<input type='hidden' name='mcType' value='" + $("#mcType").val().trim() +"'/>")); 
	$form1.append($("<input type='hidden' name='sfVersion' value='" + $("#sfVersion").val().trim() +"'/>")); 
	$form1.append($("<input type='hidden' name='bill' value='" + $("#bill").val().trim() +"'/>")); 
	$form1.append($("<input type='hidden' name='cpName' value='" + $("#cpName").val().trim() +"'/>")); 
	$form1.append($("<input type='hidden' name='prolongMonth' value='" + prolongMonth +"'/>")); 
	$form1.append($("<input type='hidden' name='prolongMonthStart' value='" + prolongMonthStart +"'/>")); 
	$form1.append($("<input type='hidden' name='prolongMonthEnd' value='" + prolongMonthEnd +"'/>")); 
	$form1.append($("<input type='hidden' name='createTimeStart' value='" + $("#createTimeStart").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='createTimeEnd' value='" + $("#createTimeEnd").val() +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
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
