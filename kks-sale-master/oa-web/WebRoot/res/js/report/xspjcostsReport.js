var currentPageNum;

$(function() {
	datagridInit();
	keySearch(queryListPage);
	keySearch(queryListPagePj, 1);
	keySearch(queryListPageGroupPj, 2);//销售配件料组合
	
	 $("input:radio[name=receipt]").change(function(){
		 if($("input:radio[name=receipt]:checked").val()==0){
			 $("#rate").attr("disabled",false);
			 $("#rate").val(17);					
		 }else{
			 $("#rate").attr("disabled",true);
			 $("#rate").val(0);
		 }			
		 getSumPrice();
	});
});

//获得总费用
function getSumPrice(){
	if($("#rate").val() && $("#rate").length > 0 && $('#rate').validatebox("isValid") ){
		$("#payPrice").val(($("#unitPrice").val() * $("#number").val()).toFixed(2));
		$("#sumPrice").val((($("#rate").val()/100+1)*($("#payPrice").val()*1 + $("#logCost").val()*1)).toFixed(2));
		$("#ratePrice").val(($("#sumPrice").val()*1-$("#payPrice").val()*1 - $("#logCost").val()*1).toFixed(2));
	}else{
		$("#payPrice").val(($("#unitPrice").val() * $("#number").val()).toFixed(2));
		$("#sumPrice").val(($("#logCost").val()*1+$("#payPrice").val()*1).toFixed(2));
		$("#ratePrice").val("0.00");
	}
}

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
	var url = ctx + "/xspjcosts/xspjcostsList";
	var param ={
		"cusName" : $.trim($("#searchCusName").val()),	
		"startDate" : $("#startTime").val(),
		"endDate" : $("#endTime").val(),
		"payType" : $("#payMethod_search").combobox('getValue'),
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
					if(item.unitPrice||item.unitPrice=='0'){
						item.unitPrice=item.unitPrice.toFixed(2);
					}else{
						item.unitPrice='0.00';
					}
					
					if(item.payPrice||item.payPrice=='0'){
						item.payPrice=item.payPrice.toFixed(2);//两位小数
					}else{
						item.payPrice='0.00';
					}
					
					if(item.logCost||item.logCost=='0'){
						item.logCost=item.logCost.toFixed(2);//两位小数
					}else{
						item.logCost='0.00';
					}
					
					if(item.ratePrice||item.ratePrice=='0'){
						item.ratePrice=item.ratePrice.toFixed(2);//两位小数
					}else{
						item.ratePrice='0.00';
					}
					
					if(item.sumPrice||item.sumPrice=='0'){
						item.sumPrice=item.sumPrice.toFixed(2);//两位小数
					}else{
						item.sumPrice='0.00';
					}
					
					//税率为0，不显示
					if(item.rate=='0'){
						item.rate="";
					}
					//付款方式
					if(item.payMethod == 'personPay'){
						item.payMethod = "<label style='font-weight: bold;'>人工付款</label>";
					}else if(item.payMethod == 'aliPay'){
						item.payMethod = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
					}else if(item.payMethod == 'weChatPay'){
						item.payMethod = "<label style='color:green;font-weight: bold;'>微信付款</label>";
					}
					//确认状态
					if (item.receipt == 0) {
						item.receipt = "<strong style='color:red'>是</strong>";
					} else if (item.receipt == 1){
						item.receipt = "<strong style='color:green'>否</strong>";
					} 
					//确认状态
					if (item.payState == 0 || item.payState == '未确认') {
						item.payState = "<strong style='color:red'>未确认</strong>";
					} else if (item.payState == 1 || item.payState == '已确认'){
						item.payState = "<strong style='color:green'>已确认</strong>";
					} 
					if(i == ret.length-1){
						item.unitPrice="";
						item.number="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.number+"</label>";
						item.payPrice="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.payPrice+"</label>";
						item.logCost="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.logCost+"</label>";
						item.ratePrice="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.ratePrice+"</label>";
						item.sumPrice="<label style='color:rgb(51, 153, 255);font-weight: bold;'>"+item.sumPrice+"</label>";
						item.cusName="<label style='font-weight: bold;'>"+item.cusName+"</label>";
					}
				});
			}
			if(ret.length ==1){
				$("#DataGrid1").datagrid('loadData',{total:0,rows:[]});
			}else{
				$("#DataGrid1").datagrid('loadData', returnData.data);
				getPageSize();
				resetCurrentPageShow(currentPageNum);
			}
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
		singleSelect : true,// 是否单选
		onDblClickRow : function(rowIndex, row) {
			 if(rowIndex != $("#DataGrid1").datagrid('getData').rows.length-1){
				 xspj_updateInfo(rowIndex, row);
			 }
		}
	});
	queryListPage(1);// 初始化查询页面数据，必须放在datagrid()初始化调用之后
	
	//选择配件料
	$("#choosepj_DataGrid").datagrid({
	     singleSelect:true,
		 onDblClickRow: function(index,value){
	 	 }
	});	
	
	//销售配件料组合选择
	$("#chooseGroupPj_DataGrid").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
		    showGroupPjDetails(index,value);//销售配件料组合详情
		}
	});
	
	//销售配件料组合详情
	$("#groupPjDetails_DataGrid").datagrid({
	});
}

/**
 * 清空缓存
 */
function xspj_addInfo() {
	windowVisible("updateWindow");
	$("#saleId").val(0);
	$("#cusName").val("");
	$("#saleDate").val(initTime());
	$("#marketModel").val("");
	$("#proName").val("");
	$("#proNO").val("");
	$("masterOrSlave").val("");
	$("#groupPjId").val("");
	$("#groupName").val("");
	
	$("#number").val("");
	$("#unitPrice").val("");
	$("#payReason").val("销售出库");
	$("#expressType").val("");
	$("#expressNO").val("");
	$("#payMethod").combobox('setValue','personPay');
	$("#payDate").val(initTime());
	$("input[name='payState'][value='1']").prop("checked", true);
	$("input[name='receipt'][value='1']").prop("checked", true);
	$("#rate").attr("disabled",true);
	$("#rate").val(0);
	$("#remark").val("");
	$("#payPrice").val("0.00");
	$("#logCost").val("0.00");	
	$("#ratePrice").val("0.00")
	$("#sumPrice").val("0.00");
	updateWindowOpen();
	chageWinSize('updateWindow');
}

/**
 * 插入数据
 */
function xspjcostsSave() {
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var receipt = $("input:radio[name = receipt]:checked").val();
		var rate = $("#rate").val();
		if(receipt == 0 && !rate){
			$.messager.alert("操作提示","开发票请填写税率","info");
			return;
		}else if(receipt == 0 && rate <= 0){
			$.messager.alert("操作提示","税率必须大于零","info");
			return;
		}else if(receipt == 1){
			$("#rate").val(0);
		}
		var cusName = $("#cusName").val();
		var saleDate = $("#saleDate").val();
		if (!saleDate) {
			$("#saleDate").val(initTime());
		}
		saleDate = $("#saleDate").val();
		var marketModel = $("#marketModel").val();
		var proName = $("#proName").val();
		var number = $("#number").val();
		var unitPrice = $("#unitPrice").val();
		var payReason = $("#payReason").val();
		var expressType = $("#expressType").val();
		var expressNO = $("#expressNO").val();
		var payMethod = $("#payMethod").combobox('getValue');
		var payDate = $("#payDate").val();
		if (!payDate) {
			$("#payDate").val(initTime());
		}
		payDate = $("#payDate").val();
		var payState = $("input[name='payState']:checked").val(); 
		var payPrice = $("#payPrice").val();
		var logCost = $("#logCost").val();	
		var ratePrice = $("#ratePrice").val()
		var sumPrice = $("#sumPrice").val();
		var remark = $("#remark").val();
		var reqAjaxPrams = {
			"saleId" : $("#saleId").val(),
			"cusName" : cusName,
			"saleTime" : saleDate,
			"marketModel" : marketModel,
			"proName" : proName,
			"number" : number,
			"unitPrice" : unitPrice,
			"payReason" : payReason,
			"payMethod" : payMethod,
			"payTime" : payDate,
			"payState" : payState,
			"remark" : remark,
			"proNO" : $.trim($("#proNO").val()),
			"masterOrSlave" : $.trim($("#masterOrSlave").val()),
			"groupPjId" : $("#groupPjId").val(),
			"groupName" : $.trim($("#groupName").val()),
			"expressType" : expressType,
			"expressNO" : expressNO,
			"payPrice" : payPrice,
			"receipt" : receipt,
			"rate" : $("#rate").val(),
			"logCost" : logCost,
			"ratePrice" : ratePrice,
			"sumPrice" : sumPrice,
			"id" : 0
		};
		processSaveAjax(reqAjaxPrams, 0);
	}
}

function processSaveAjax(reqAjaxPrams, type) {
	var url = ctx + "/xspjcosts/addOrUpdateXspjcosts";
	var id = $("#saleId").val();
	asyncCallService(url, 'post', false, 'json', reqAjaxPrams, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			updateWindowClose();
			if (id > 0){
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

/**
 * 修改数据
 */
function xspj_updateInfo() {
	if($('#DataGrid1').datagrid('getRowIndex', $("#DataGrid1").datagrid('getSelected')) != $("#DataGrid1").datagrid('getData').rows.length-1){
		windowVisible("updateWindow");
		var row = $('#DataGrid1').datagrid('getSelected');
		if (row) {
			var param = "saleId=" + row.saleId;
			var url = ctx + "/xspjcosts/getXspjcosts";
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				processSSOOrPrivilege(returnData);
				$("#saleId").val(returnData.data.saleId);
				$("#cusName").val(returnData.data.cusName);
				$("#saleDate").val(returnData.data.saleDate);
				$("#marketModel").val(returnData.data.marketModel);
				$("#proName").val(returnData.data.proName);
				$("#number").val(returnData.data.number);
				$("#unitPrice").val(returnData.data.unitPrice);
				$("#proNO").val(returnData.data.proNO);
				$("#masterOrSlave").val(returnData.data.masterOrSlave);
				$("#groupPjId").val(returnData.data.groupPjId);
				$("#groupName").val(returnData.data.groupName);
				if($.trim($("#groupName").val()) && $("#groupPjId").val()){
					$(".groupHide").attr('disabled','disabled');
					$("#proName").validatebox({required:false});
					$("#groupName").validatebox({required:true});
					$(".groupShow").show();
					$(".notGroupShow").hide();
				}else{
					$(".groupHide").removeAttr('disabled');
					$("#groupName").validatebox({required:false});
					$("#proName").validatebox({required:true});
					$(".notgroupShow").show();
					$(".groupShow").hide();
				}
				$("#payReason").val(returnData.data.payReason);
				$("#expressType").val(returnData.data.expressType);
				$("#expressNO").val(returnData.data.expressNO);
				$("#logCost").val(returnData.data.logCost); 
				$("#payMethod").combobox('setValue',returnData.data.payMethod);
				$("#payDate").val(returnData.data.payDate);
				var payState = returnData.data.payState;
				if(payState == '0'){
					$("input:radio[name=payState][value=0").prop("checked",true);
				}else{
					$("input:radio[name=payState][value=1]").prop("checked",true);
				}
				var receipt = returnData.data.receipt;							
				if(receipt == '0'){
					$("input:radio[name=receipt][value='"+receipt+"']").prop("checked",true);
					$("#rate").val(returnData.data.rate);
					$("#rate").prop('disabled',false);
				}else{
					$("#rate").val("");
					$("input:radio[name=receipt][value=1]").prop("checked",true);
				}		
				$("#payPrice").val(returnData.data.payPrice);
				$("#logCost").val(returnData.data.logCost);	
				$("#ratePrice").val(returnData.data.ratePrice)
				$("#sumPrice").val(returnData.data.sumPrice);
				if (returnData.data.payState == 0 || returnData.data.payState == '未确认') {
					$("input[name='payState'][value='0']").prop("checked", true);
				} else {
					$("input[name='payState'][value='1']").prop("checked", true);
				} 
				$("#remark").val(returnData.data.remark);
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
			updateWindowOpen();
			chageWinSize('updateWindow');
		} else {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info");
		}
	}else{
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
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

/**
 * 删除
 */
function xspj_deleteInfo() {
	if($('#DataGrid1').datagrid('getRowIndex', $("#DataGrid1").datagrid('getSelected')) != $("#DataGrid1").datagrid('getData').rows.length-1){
		var row = $('#DataGrid1').datagrid('getSelected');
		if (row) {
			$.messager.confirm("操作提示", "是否删除此信息", function(conf) {
				if (conf) {
					var index = $('#DataGrid1').datagrid('getRowIndex', row);
					$('#DataGrid1').datagrid('deleteRow', index);
					var deleted = $('#DataGrid1').datagrid('getChanges', "deleted");
					var getsaleId;
					for (var z = 0; z < deleted.length; z++) {
						getsaleId = deleted[z].saleId;
						var url = ctx + "/xspjcosts/deleteXspjcosts";
						var param = "saleId=" + getsaleId;
						if (getsaleId != -1) {
							asyncCallService(url, 'post', false, 'json', param, function(returnData) {
								processSSOOrPrivilege(returnData);
								if (returnData.code == '0') {
									queryListPage(currentPageNum);
								}
								$.messager.alert("操作提示", returnData.msg, "info");
							}, function() {
								$.messager.alert("操作提示", "网络错误", "info");
							});
						} else {
							$.messager.alert("操作提示", "删除错误!", "info");
							queryListPage(currentPageNum);
						}
					}
					$('#DataGrid1').datagrid('acceptChanges');
				}
			});
		} else {
			$.messager.alert("提示信息", "请先选择所要删除的行！", "info");
		}
	}else{
		$.messager.alert("提示信息", "此行不能进行删除操作！", "info");
	}
}

/**
 * 下载模板
 */
function downloadTemplet() {
	downLoadExcelTmp("REPORTS/Excel-XSPJCOSTS-TEMP.xlsx");
}

/**
 * 导入
 */
function importInfo() {
	ImportExcelDatas("xspjcosts/ImportDatas");
}

/**
 * 导出
 */
function exportInfo() {
	var url =  ctx + "/xspjcosts/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='cusName' value='" + $.trim($("#searchCusName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='startDate' value='" + $("#startTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='endDate' value='" + $("#endTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='payType' value='" + $("#payMethod_search").combobox('getValue') +"'/>")); 
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

function updateWindowOpen() {
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#updateWindow').window('open');
	$(".groupHide").removeAttr('disabled');
}

function updateWindowClose() {
	$(".notgroupShow").show();
	$(".groupShow").show();
	$('#updateWindow').window('close');
}

//function isGroup(){
//	var proName = $.trim($("#proName").val());
//	if(proName){
//		var proNameFlag = proName.substring(proName.indexOf("【")+1, proName.indexOf("】"));
//		if(proNameFlag == "组合"){
//			$(".groupHide").attr('disabled','disabled');
//		}else{
//			$(".groupHide").removeAttr('disabled');
//		}
//	}else{
//		$(".groupHide").removeAttr('disabled');
//	}
//}

//打开选择配件料窗口
function chooseMaterial(){
	$(".groupHide").removeAttr('disabled');
	$("#groupName").validatebox({required:false});
	$("#proName").validatebox({required:true});
	$(".notgroupShow").show();
	$(".groupShow").hide();
	
	windowVisible("choosepj_w");
	$('#choosepj_w').window('open');
	$("#pj_marketModel").val("");
	$("#pj_proNO").val("");
	$("#pj_proName").val("");
	queryListPagePj();
}

//关闭选择配件料窗口
function materialClose(){
 	$('#choosepj_w').window('close');
 	$('#chooseGroupPj_w').window('close');
 }

//查询所有配件料
function queryListPagePj(pageNumber){
	currentPageNum_Pj=pageNumber;
	if(currentPageNum_Pj=="" || currentPageNum_Pj==null){
		currentPageNum_Pj=1;
	}
	var pageSize = getCurrentPageSizeByChoosepj('choosepj_DataGrid');
	var url=ctx+"/pjlmanage/pjlmanageList";
    var param={
		"proNO" : $.trim($("#pj_proNO").val()),
		"marketModel" : $.trim($("#pj_marketModel").val()),
		"proName" : $.trim($("#pj_proName").val()),
		"currentpage" :currentPageNum_Pj,
		"pageSize" : pageSize
    }
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#choosepj_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizePj();
			resetCurrentPagePj(currentPageNum_Pj);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 选择物料
 */
function saveChoose(chooseType){
	var ret = "";
	 if(chooseType == 'pj'){
		 ret = $('#choosepj_DataGrid').datagrid('getSelected');
	 }else if(chooseType == 'groupPj'){
		 ret = $('#chooseGroupPj_DataGrid').datagrid('getSelected');
	 }
	 if(ret){
		 if(chooseType == 'pj'){
			 $("#proNO").val(ret.proNO);
			 $("#proName").val(ret.proName);
			 $("#marketModel").val(ret.marketModel);
			 $("#masterOrSlave").val(ret.masterOrSlave);
			 $("#unitPrice").val(ret.retailPrice.toFixed(2));
		 }else if(chooseType == 'groupPj'){
			 $("#groupPjId").val(ret.groupPjId);
			 $("#groupName").val(ret.groupName);
			 $("#marketModel").val(ret.marketModel);
			 $("#unitPrice").val(ret.groupPrice.toFixed(2));
		 }
		 $("#number").val(1);
		 $("#payPrice").val(($("#unitPrice").val() * $("#number").val()).toFixed(2));
		 materialClose();
//		 isGroup();
	 }else{
		 $.messager.alert("提示信息", "请先选择一行操作！", "warning");
	 }
}

//组合配给料
function chooseGroupPj(){
	$(".groupHide").attr('disabled','disabled');
	$("#proName").validatebox({required:false});
	$("#groupName").validatebox({required:true});
	$(".groupShow").show();
	$(".notGroupShow").hide();

	windowVisible("chooseGroupPj_w");
	$('#chooseGroupPj_w').window('open');
	$("#groupMarketModel_Pj").val("");
	$("#groupName_Pj").val("");
	queryListPageGroupPj(1);
}

//查询所有销售配件料组合
function queryListPageGroupPj(pageNumber){
	currentPageNum_groupPj=pageNumber;
	if(currentPageNum_groupPj=="" || currentPageNum_groupPj==null){
		currentPageNum_groupPj=1;
	}
	var pageSize = getCurrentPageSize('chooseGroupPj_DataGrid');
	var url = ctx + "/groupPj/groupPjList";
	var param = {
		"marketModel" : $.trim($("#groupMarketModel_Pj").val()),
		"groupName" : $.trim($("#groupName_Pj").val()),
		"currentpage" : currentPageNum_groupPj,
		"pageSize" : pageSize
	};
	$("#chooseGroupPj_DataGrid").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$("#chooseGroupPj_DataGrid").datagrid('loadData',returnData.data);  
			getPageSizePj();
			resetCurrentPagePj(currentPageNum_groupPj);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#chooseGroupPj_DataGrid").datagrid('loaded');
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

//组合配给料详情
function showGroupPjDetails(){
	windowVisible("groupPjDetails_w");
	$('#groupPjDetails_w').window('open');
	queryGroupPjDetailsList();
}

//查看销售配件料组合详情
function queryGroupPjDetailsList(){
	var row = $('#chooseGroupPj_DataGrid').datagrid('getSelected');
	if (row) {
		var param = "groupId=" + row.groupPjId;
		var url = ctx + "/groupPjDetails/groupPjDetailsList";
		$("#groupPjDetails_DataGrid").datagrid('loading');
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == undefined) {
				$("#groupPjDetails_DataGrid").datagrid('loadData', returnData.data);
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
			$("#groupPjDetails_DataGrid").datagrid('loaded');
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}

