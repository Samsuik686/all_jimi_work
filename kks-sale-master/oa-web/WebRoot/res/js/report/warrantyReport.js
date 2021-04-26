$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
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
	var url = ctx + "/warrantyReport/warrantyReportPage";
	var selParams = "startDate=" + $("#startTime").val() + "&endDate=" + $("#endTime").val() + "&payType="+$("#payMethod_search").combobox('getValue') + "&cusName=" + $.trim($("#cusName_search").val()) +"&imei=" + $("#imei_search").val();
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			if (ret && ret.length > 0) {
				$.each(ret, function(i, item) {
					if (item.priceReason == 01) {
						item.priceReason = '保修';
					} else if (item.priceReason == 11) {
						item.priceReason = '过保修期';
					} else if (item.priceReason == 00 || item.priceReason == 10) {
						item.priceReason = '非保';
					}
					if (item.isPay == 0) {
						item.isPay = '已确认';
					} else if (item.isPay == 1) {
						item.isPay = '未付款';
					}
	
					if(item.unitPrice||item.unitPrice=='0'){
						item.unitPrice=item.unitPrice.toFixed(2);
					}else{
						item.unitPrice='0.00';
					}
					
					if(item.totalMoney||item.totalMoney=='0'){
						item.totalMoney=item.totalMoney.toFixed(2);//两位小数
					}else{
						item.totalMoney='0.00';
					}
					
					//付款方式
					if(item.remAccount == 'personPay'){
						item.remAccount = "<label style='font-weight: bold;'>人工付款</label>";
					}else if(item.remAccount == 'aliPay'){
						item.remAccount = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
					}else if(item.remAccount == 'weChatPay'){
						item.remAccount = "<label style='color:green;font-weight: bold;'>微信付款</label>";
					}
					if(i == ret.length-1){
						item.cusName="<label style='font-weight: bold;'>"+item.cusName+"</label>";
						item.unitPrice="";
						item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
						item.totalMoney="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.totalMoney+"</label>";
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
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : true,// 是否单选
	});
	queryListPage(1);
}

/**
 * 导出Execl表
 */
function exportInfo() {
	ExportExcelDatas("warrantyReport/ExportDatas?startDate=" + $("#startTime").val() + "&endDate=" + $("#endTime").val() + "&payType="+$("#payMethod_search").combobox('getValue')+ "&cusName=" + $.trim($("#cusName_search").val()));
}

/**
 * 打印
 */
function printReportExcel() {
	$.messager.alert("操作提示", "功能暂未完善", "info");
}

/**
 * 生成Echart图表
 */
function creatReportEchart() {
	windowCommOpen("Echart_Accqute");
}
