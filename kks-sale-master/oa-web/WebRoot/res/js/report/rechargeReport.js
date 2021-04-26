var currentPageNum;

$(function() {
	datagridInit();
	keySearch(queryListPage);
});

/**
 * 初始化数据及查询函数
 * 
 * @param pageNumber
 */
function queryListPage(pageNumber) {
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	var pageSize = getCurrentPageSize('DataGrid1');
	var url = ctx + "/recharge/rechargeList";
	var param = {
			"startDate" : $("#startTime").val(), 
			"endDate" : $("#endTime").val(),
			"phone" : $.trim($("#phoneNumber").val()),
			"unitName" : $.trim($("#unitNameSearch").val()),
			"term" : $("#termSearch").combobox('getValue'),
			"remittanceMethod" : $("#remittanceMethodSearch").combobox('getValue'),
			"currentpage" : currentPageNum,
			"pageSize" : pageSize
	}
	
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if (item.term == '0') {
						item.term = '一年';
					} else {
						if(item.term!=null)
						item.term = '终身';
					}
					
					if(item.unitPrice||item.unitPrice=='0'){
						item.unitPrice=item.unitPrice.toFixed(2);
					}else{
						item.unitPrice='0.00';
					}
					if(item.ratePrice||item.ratePrice=='0'){
						item.ratePrice=item.ratePrice.toFixed(2);
					}else{
						item.ratePrice='0.00';
					}
					if(item.totalCollection||item.totalCollection=='0'){
						item.totalCollection=item.totalCollection.toFixed(2);//两位小数
					}else{
						item.totalCollection='0.00';
					}
					
					//付款方式
					if(item.remittanceMethod == 'personPay'){
						item.remittanceMethod = "<label style='font-weight: bold;'>人工付款</label>";
					}else if(item.remittanceMethod == 'aliPay'){
						item.remittanceMethod = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
					}else if(item.remittanceMethod == 'weChatPay'){
						item.remittanceMethod = "<label style='color:green;font-weight: bold;'>微信付款</label>";
					}
					
					//确认状态
					if (item.orderStatus == '已充值') {
						item.orderStatus = "<strong style='color:green'>已充值</strong>";
					} else if (item.orderStatus == '待充值'){
						item.orderStatus = "<strong style='color:red'>待充值</strong>";
					}
					
					if(i == ret.length-1){
						item.unitPrice="";
						item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
						item.totalCollection="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.totalCollection+"</label>";
						item.unitName="<label style='font-weight: bold;'>"+item.unitName+"</label>";
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

/**
 * 初始化表格
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			if(rowIndex != $("#DataGrid1").datagrid('getData').rows.length-1){
				 recharge_updateInfo(rowIndex, row);
			 }
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
}

/**
 * 清空缓存
 */
function recharge_addInfo() {
	windowVisible("addWindow");	
	$("#rechIdAdd").val(0);
	$("#unitNameAdd").val("");
	$("#rechargeDateAdd").val(initTime());
	$("#imeiAdd").val("");
	$("#yearNumber").val(0);
	$("#yearUnitPrice").val(0.00);
	$("#yearTotalCollection").val(0.00);
	$("#lifeNumber").val(0);
	$("#lifeUnitPrice").val(0.00);
	$("#lifeTotalCollection").val(0.00);
	$("#totalCollectionAdd").val(0.00);
	$("#taxRate").combobox('setValue', 0);
	$("#remittanceMethodAdd").combobox('select', 0);
	$("#orderStatusAdd").combobox('setValue',"已充值");
	$("#ratePriceAdd").val(0.00);
	$("#remarkAdd").val("");
	addWindowOpen();
	chageWinSize('addWindow');
}

/**
 * 修改数据
 */
function rechargeSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var unitName = $("#unitName").val();
		var rechargeDate = $("#rechargeDate").val();
		if (!rechargeDate) {
			$("#rechargeDate").val(initTime());
		}
		rechargeDate = $("#rechargeDate").val();
		var imei = $("#imei").val();
		var term = $("#term").combobox('getValue');
		var number = $("#number").val();
		var unitPrice = $("#unitPrice").val();
		var ratePrice = $("#ratePrice").val();
		var totalCollection = $("#totalCollection").val();
		var remittanceMethod = $("#remittanceMethod").combobox('getValue');
		var orderStatus = $("#orderStatus").combobox('getValue');
		var remark = $("#remark").val();
		var reqAjaxPrams = {
				"rechId" : $("#rechId").val(),
				"unitName" : unitName,
				"rechargeTime" : rechargeDate,
				"imei" : imei, 
				"term" : term,
				"number" : number,
				"unitPrice" : unitPrice,
				"totalCollection" : totalCollection, 
				"remittanceMethod" : remittanceMethod,
				"orderStatus" : orderStatus,
				"ratePrice" : ratePrice,
				"remark" : remark,
		}
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/recharge/addOrUpdateRecharge";
	var id = $("#rechId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0){
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

/**
 * 新增
 */
function rechargeAdd() {
	var isValid = $('#aform').form('validate');
	if (isValid) {
		if ($("#yearNumber").val() == 0 && $("#lifeNumber").val() == 0) {
			$.messager.alert("操作提示", "一年和终身数量不能同时为0", "info");
			return;
		}
		
		var unitName = $("#unitNameAdd").val();
		var rechargeDate = $("#rechargeDateAdd").val();
		if (!rechargeDate) {
			$("#rechargeDateAdd").val(initTime());
		}
		rechargeDate = $("#rechargeDateAdd").val();
		
		var imei = $("#imeiAdd").val();
		var yearNumber = $("#yearNumber").val();
		var yearUnitPrice = $("#yearUnitPrice").val();
		var yearTotalCollection = $("#yearTotalCollection").val();
		var yearRatePrice = ($("#taxRate").combobox('getValue') * $("#yearTotalCollection").val() / 100).toFixed(2);
		
		var lifeNumber = $("#lifeNumber").val();
		var lifeUnitPrice = $("#lifeUnitPrice").val();
		var lifeTotalCollection = $("#lifeTotalCollection").val();
		var lifeRatePrice = ($("#taxRate").combobox('getValue') * $("#lifeTotalCollection").val() / 100).toFixed(2);

		var remittanceMethod = $("#remittanceMethodAdd").combobox('getValue');
		var orderStatus = $("#orderStatusAdd").combobox('getValue');
		var remark = $("#remarkAdd").val();
		var reqAjaxPrams = {
				"rechId" : $("#rechIdAdd").val(),
				"unitName" : unitName,
				"rechargeTime" : rechargeDate,
				"imei" : imei, 
				"yearNumber" : yearNumber,
				"yearUnitPrice" : yearUnitPrice,
				"yearTotalCollection" : yearTotalCollection,
				"yearRatePrice" : yearRatePrice,
				
				"lifeNumber" : lifeNumber,
				"lifeUnitPrice" : lifeUnitPrice,
				"lifeTotalCollection" : lifeTotalCollection,
				"lifeRatePrice" : lifeRatePrice,
				
				"remittanceMethod" : remittanceMethod,
				"orderStatus" : orderStatus,
				"remark" : remark,
		}
		processAddAjax(reqAjaxPrams, 0);
	}
}

function processAddAjax(reqAjaxPrams, type) {
	var url = ctx + "/recharge/addRecharge";
	var id = $("#rechIdAdd").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			addWindowClose();
			if (id > 0){
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

function recharge_updateInfo(){
	if($('#DataGrid1').datagrid('getRowIndex', $("#DataGrid1").datagrid('getSelected')) != $("#DataGrid1").datagrid('getData').rows.length-1){
		windowVisible("updateWindow");	
		var row = $('#DataGrid1').datagrid('getSelected');
		if (row){
			var param="rechId="+row.rechId;
			var url=ctx+"/recharge/getRecharge";
			asyncCallService(url, 'post', false,'json', param, function(returnData){
				processSSOOrPrivilege(returnData);
				$("#rechId").val(returnData.data.rechId);
				$("#unitName").val(returnData.data.unitName);
				$("#rechargeDate").val(returnData.data.rechargeDate);
				$("#imei").val(returnData.data.imei);
				$("#term").combobox('setValue',returnData.data.term);
				$("#number").val(returnData.data.number);
				$("#unitPrice").val(returnData.data.unitPrice);
				$("#ratePrice").val(returnData.data.ratePrice);
				$("#totalCollection").val(returnData.data.totalCollection);
				$("#remittanceMethod").combobox('setValue',returnData.data.remittanceMethod);
				$("#orderStatus").combobox('setValue',returnData.data.orderStatus);
				$("#remark").val(returnData.data.remark);
			}, function(){
				$.messager.alert("操作提示","网络错误","info");
			});
			updateWindowOpen();
			chageWinSize('updateWindow');
		}else{
			$.messager.alert("提示信息","请先选择所要修改的行！","info");
		}
	}else{
		$.messager.alert("提示信息", "此行不能进行修改操作！", "info");
	}
}

// 自动计算合计金额
function getTotalPrice() {
	$("#unitPrice").blur(function() {
		$("#totalCollection").val($("#number").val() * $("#unitPrice").val());
	});
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
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

/**
 * 删除
 */
function recharge_deleteInfo() {
	var row = $('#DataGrid1').datagrid('getSelections');
	var rechIds = [];
	var tempRow = [];
	for (var i = 0; i < row.length; i++) {
		if ($('#DataGrid1').datagrid('getRowIndex', row[i]) == $("#DataGrid1").datagrid('getData').rows.length - 1) {
			$.messager.alert("提示信息", "总计行不能进行删除操作！", "info");
			return;
		}
		rechIds.push(row[i].rechId);
		tempRow.push(row[i]);
	}
	
	if (row.length > 0) {
		$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
			if (conf) {
				for (var i = 0; i < tempRow.length; i++) {
					var index = $('#DataGrid1').datagrid('getRowIndex', tempRow[i]);
					$('#DataGrid1').datagrid('deleteRow', index);
				}
				
				var url = ctx + "/recharge/deleteRecharge";
				var param ="rechIdsStr=" + rechIds;
				
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
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
	downLoadExcelTmp("REPORTS/Excel-RECHARGE-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("recharge/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/recharge/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='startDate' value='" + $("#startTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='endDate' value='" + $("#endTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='unitName' value='" + $.trim($("#unitNameSearch").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='term' value='" + $("#remittanceMethodSearch").combobox('getValue') +"'/>")); 
	$form1.append($("<input type='hidden' name='remittanceMethod' value='" + $("#remittanceMethodSearch").combobox('getValue') +"'/>")); 
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

function addWindowClose() {
	$('#addWindow').window('close');
}
function addWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#addWindow').window('open');
}
