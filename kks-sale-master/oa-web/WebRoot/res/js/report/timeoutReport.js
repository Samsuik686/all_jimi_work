$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;

/**
 * 刷新超天时间
 */
function flushAll(){
	var url = ctx+"/workflow/flushTimeoutDays"
	asyncCallService(url, 'post', false, 'json', null, function(returnData) {
		if(returnData != null){
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		queryListPage(1);
	})
}
function flushBackTime(){
	let startTime = $("#startTime").val();
	let endTime = $("#endTime").val();
	if(startTime == null || startTime == undefined || startTime=="" ||
		endTime == null || endTime == undefined || endTime==""){
		$.messager.alert("操作提示","请选择受理开始时间和结束时间","info");
		return;
	}
	let param={};
	param.startTime = startTime;
	param.endTime = endTime;
	var url = ctx+"/workflow/flushBackTime"
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if(returnData != null){
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		queryListPage(1);
	})
}
$(function() {
	datagridInit();
	keySearch(queryListPage);
	
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	 });
	 
	 $("#timeoutState").combobox({
		 onSelect:function(){
			 // var ts=$("#timeoutState").combobox('getValue');
			 // if(!ts || ts==0){
				//  $("#repairNumberState").combobox('setValue','0');
			 // }else if(ts && ts==2){
				//  $("#repairNumberState").combobox('setValue','21');
			 // }else if(ts && ts==1){
				//  $("#repairNumberState").combobox('setValue','0');
			 // }
		 } 
	 });
	 
	 $("#repairNumberState").combobox({
		 onSelect:function(){
			 var ts=$("#repairNumberState").combobox('getValue');
			 if(!ts || ts==0 || ts==20){
				 $("#timeoutState").combobox('setValue','0');
			 }else if(ts && ts==21){
				 $("#timeoutState").combobox('setValue','2');
			 }else {
				 $("#timeoutState").combobox('setValue','1');
			 } 
		 } 
	 });
	 
});

// --------------------------------------------- 初始化 》工具栏 ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	$("#DataGrid1").datagrid({
		singleSelect : false,
		pagination:false,
	});
	queryListPage(1);
}
// --------------------------------------------- 初始化 》工具栏 ------------------------------------------ END

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
	getTreeNode();
	var url = ctx + "/reportCon/getAllTimeoutAcceptList";
	var param = {
			"imei" : $.trim($("#imei").val()),
			"cusName" : $.trim($("#searchCusName").val()),	
			"repairnNmber" : $.trim($("#searchRepairnNmber").val()),	
			"startTime" : $("#startTime").val(),
			"endTime" : $("#endTime").val(),
			"timeoutState" : $("#timeoutState").combobox('getValue'),
			"treeDate" : $("#tree-Date").val(),
			"currentpage" : currentPageNum,
			"timeoutReasonState": $("#timeoutReasonState").combobox('getValue'),
	}
	var tmp1  = $("#repairNumberState").combobox('getValue');
	var tmp2 = $("#workStation").combobox('getValue');
	var tmp3 = $("#engineer").combobox('getValue');
	if(tmp1!='全部'){
		param.repairNumberState=tmp1;
	}
	if(tmp2!='全部'){
		param.workStation=tmp2;
	}
	if(tmp3!='全部'){
		param.engineer=tmp3;
	}
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data,function(i, item){
				//工站
				item.workStation = workStationForm(item);
				//状态
				if(item.state=="-1"){
					item.state="<label style='color:blue;font-weight: bold;'>已发货</label>";
				}else if(item.state=="0"){
					item.state="<label style='color:blue;font-weight: bold;'>已受理</label>";

				}else if(item.state=="1"){
					item.state="<label style='color:blue;font-weight: bold;'>已受理，待分拣</label>";

				}else if(item.state=="2"){
					item.state="<label style='color:blue;font-weight: bold;'>已分拣，待维修</label>";

				}else if(item.state=="3"){
					item.state="<label style='color:blue;font-weight: bold;'>待报价</label>";

				}else if(item.state=="4"){
					item.state="<label style='color:blue;font-weight: bold;'>已付款，待维修</label>";
				}else if(item.state=="5"){
					item.state="<label style='color:blue;font-weight: bold;'>已维修，待终检</label>";
				}else if(item.state=="6"){
					item.state="<label style='color:blue;font-weight: bold;'>已终检，待维修</label>";
				}else if(item.state=="7"){
					item.state="<label style='color:blue;font-weight: bold;'>已终检，待装箱</label>";
				}else if(item.state=="8"){
					item.state="<label style='color:blue;font-weight: bold;'>放弃报价，待装箱</label>";
				}else if(item.state=="9"){
					item.state="<label style='color:blue;font-weight: bold;'>已报价，待付款</label>";
				}else if(item.state=="10"){
					item.state="<label style='color:blue;font-weight: bold;'>不报价，待维修</label>";
				}else if(item.state=="11"){
					item.state="<label style='color:blue;font-weight: bold;'>放弃报价，待维修</label>";
				}else if (item.state == "12"){
					item.state = "<label style='color:blue;font-weight: bold;'>已维修，待报价</label>";
				}else if (item.state == "13"){
					item.state = "<label style='color:blue;font-weight: bold;'>待终检</label>";
				}else if (item.state == "14"){
					item.state = "<label style='color:blue;font-weight: bold;'>放弃维修</label>";
				} else if (item.state == "15"){
					item.state = "<label style='color:blue;font-weight: bold;'>测试中</label>";
				} else if (item.state == "16") {
					item.state = "<label style='color:red;font-weight: bold;'>已测试，待维修</label>";
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

function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#timeoutState").combobox('setValue','0');
		// $("#repairNumberState").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#timeoutState").combobox('setValue','2');
		// $("#repairNumberState").combobox('setValue','21');
	}else if(treeState=='1'){
		$("#timeoutState").combobox('setValue','1');
		// $("#repairNumberState").combobox('setValue','0');
	}
}
function initTimeoutReasonCombobox(){
	var data=[];
	$.ajax({
		url:ctx+"/timeoutReason/getTimeoutReasonMap",
		type:'GET',
		async:false,
		success:function(result){
			data = result.rows;
		},
		error:function(result){
			alert("初始化错误");
		}
	});
	$("#timeoutReason").combobox ({
		editable : false,
		valueField : "reason",
		textField : "reason",
		editable:true,
		data:data
	});
}
function timeout_updateInfo(){
	initTimeoutReasonCombobox();
	var entity = $('#DataGrid1').datagrid('getSelections');
	if(entity.length > 0){
		windowVisible("timeout_w");
		if (entity.length == 1){
			$("#id_hidden").val(entity[0].id);
			$("#repairnNmber").val(entity[0].repairnNmber);
			$("#imei").val("");
		}else if (entity.length > 1){
			windowVisible("timeout_w");
			for (var i = 0; i < entity.length; i++) {
				(i == 0) ? ids = entity[i].id : ids = entity[i].id + "," + ids;
			}
			$("#id_hidden").val(ids);
			$("#repairnNmber").val("");
			$("#imei").val("");
		}
		$("#dutyOfficer").val(entity[0].dutyOfficer);
		$("#timeoutRemark").val(entity[0].timeoutRemark);
		$("#timeoutReason").combobox('setValue',entity[0].timeoutReason);
		$('#timeout_w').window('open');
	}else{
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}
}

function timeout_save(){
	var isValid = $('#mform').form('validate');
	if (isValid) {
		var url = ctx + "/timeout/updateInfo";
		var param={
				"ids" : $("#id_hidden").val(),
				"dutyOfficer" :$("#dutyOfficer").val(),
				"timeoutRemark" : $("#timeoutRemark").val(),
				"timeoutReason" : $("#timeoutReason").combobox('getValue')
		}
				
		asyncCallService(url, 'post', false,'json', param, function(returnData)	{
			var ret=returnData.data;
			if(returnData.code=='0'){
				$.messager.alert("操作提示",returnData.msg,"info",function(){
					$('#timeout_w').window('close');
					queryListPage(currentPageNum);
				});
			}else{
			 	$.messager.alert("操作提示",returnData.msg,"info");
			}
	 	}, function(){
	 		$.messager.alert("操作提示","网络错误","info");
	 	});
	}
}

/**
 * 导出Excel报表
 */
function exportInfo() {
	var url =  ctx + "/reportCon/timeoutAcceptExport";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='imei' value='" + $.trim($("#imei").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='cusName' value='" + $.trim($("#searchCusName").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='repairnNmber' value='" + $.trim($("#searchRepairnNmber").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='timeoutState' value='" + $("#timeoutState").combobox('getValue') +"'/>"));
	$form1.append($("<input type='hidden' name='treeDate' value='" + $("#tree-Date").val() +"'/>"));
	var temp=$("#repairNumberState").combobox('getValue');
	if(temp != '全部') {
		$form1.append($("<input type='hidden' name='repairNumberState' value='" + $("#repairNumberState").combobox('getValue') + "'/>"));
	}
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}

/**
 * 根据状态确定工站
 * @param item
 * @param row
 * @returns {string}
 */
function workStationForm(row){
	var value;
	//所在工站
	if (row.state == 0){
		value = "受理";
	}else if (row.state == 1){
		value = "分拣";
	}else if (row.state == 2 || row.state == 5 || row.state == 10 || row.state == 11 || row.state == 12 || row.state == 16){
		value = "维修";
	}else if (row.state == 3 || row.state == 9 || row.state == 14){
		value = "报价";
	}else if (row.state == 13){
		value = "终检";
	}else if (row.state == -1){
		value = "已发货";
	}else if (row.state == 15){
		value = "测试";
	}else if (row.state == 4){
		if(row.w_priceState == -1){
			value = "维修";
		}else if(row.w_repairState == -1){
			value = "报价";
		}
	}else if (row.state == 6){
		if(row.w_FinispassFalg == -1){
			value = "维修";
		}else if(row.w_repairState == -1){
			value = "终检";
		}
	}else if (row.state == 7){
		if(row.w_FinispassFalg == -1){
			value = "装箱";
		}else {
			value = "终检";
		}
	}else if (row.state == 8){
		if(row.w_repairState == -1){
			value = "装箱";
		}else {
			value = "维修";
		}
	} else if (row.state == 17){
		value = "受理";
	} else if (row.state == 18){
		value = "分拣";
	} else if (row.state == 19){
		value = "测试";
	}
	return value;
}
$(function () {
	$("#engineer").combobox({
		valueField:'name',
		textField:'name',
		onShowPanel:function () {
			engineerComboboxInit();
		}});
	$("#repairNumberState").combobox({
		valueField:'state',
		textField:'stateStr',
		onShowPanel:function () {
			stateComboboxInit();
		}});

	// $("#timeoutReason").combobox({
	// 	url:ctx+"/timeoutReason/getTimeoutReasonMap",
	// 	valueField:'reason',
	// 	textField:'reason'
	// })
})
/**
 * 维修人员选择栏初始化，除维修人员外其他所有选项都要
 **/
function engineerComboboxInit(){
	var data=[];
	var param = {
		"imei" : $.trim($("#imei").val()),
		"cusName" : $.trim($("#searchCusName").val()),
		"repairnNmber" : $.trim($("#searchRepairnNmber").val()),
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"timeoutState" : $("#timeoutState").combobox('getValue'),
		"treeDate" : $("#tree-Date").val(),
		"timeoutReasonState":$("#timeoutReasonState").combobox('getValue'),
	}
	var tmp1  = $("#repairNumberState").combobox('getValue');
	var tmp2 = $("#workStation").combobox('getValue');
	if(tmp1!='全部'){
		param.repairNumberState=tmp1;
	}
	if(tmp2!='全部'){
		param.workStation=tmp2;
	}
	$.ajax({
		url:ctx+"/reportCon/timeoutEngineer",
		type:'GET',
		data:param,
		async:false,
		success:function(result){
			data = result.data;
		},
		error:function(result){
			alert("初始化错误");
		}
	});
	let all = {};
	all.name="全部";
	let item = new Array();
	item.push(all);
	if(data != null && data != undefined) {
		$.each(data, function (i, value) {
			let tmp = {};
			tmp.name = value;
			item.push(tmp);
		});
	}
	$("#engineer").combobox('loadData',item);
}
/**
 * 进度选择栏初始化，除进度外其他所有选项都要
 **/
function stateComboboxInit(){
	console.log("进度人员初始");
	console.log($("#repairNumberState").combobox('options'));
	var data=[];
	var param = {
		"imei" : $.trim($("#imei").val()),
		"cusName" : $.trim($("#searchCusName").val()),
		"repairnNmber" : $.trim($("#searchRepairnNmber").val()),
		"startTime" : $("#startTime").val(),
		"endTime" : $("#endTime").val(),
		"timeoutState" : $("#timeoutState").combobox('getValue'),
		"treeDate" : $("#tree-Date").val(),
		"timeoutReasonState":$("#timeoutReasonState").combobox('getValue')
	}
	var tmp1  = $("#engineer").combobox('getValue');
	var tmp2 = $("#workStation").combobox('getValue');
	if(tmp1!='全部'){
		param.engineer=tmp1;
	}
	if(tmp2!='全部'){
		param.workStation=tmp2;
	}
	$.ajax({
		url:ctx+"/reportCon/timeoutState",
		type:'GET',
		data:param,
		async:false,
		success:function(result){
			data = result.data;
		},
		error:function(result){
			alert("初始化错误");
		}
	});

	let all = {};
	all.stateStr="全部";
	all.state="全部";
	let item = new Array();
	item.push(all);
	if(data != null || data!=undefined) {
		$.each(data, function (i, value) {
			let tmp = {};
			tmp.state = value;
			tmp.stateStr = stateForm(value);
			item.push(tmp);
		});
	}
	$("#repairNumberState").combobox('loadData',item);
}

function stateForm(item){
	let value;
	// 状态
	if (item == "-1") {
		value = "已发货";
	} else if (item == "0") {
		value = "已受理";
	} else if (item == "1") {
		value = "已受理，待分拣";
	} else if (item == "2") {
		value = "已分拣，待维修";
	} else if (item == "3") {
		value = "待报价";
	} else if (item == "4") {
		value = "已付款，待维修";
	} else if (item == "5") {
		value = "已维修，待终检";
	} else if (item == "6") {
		value = "已终检，待维修";
	} else if (item == "7") {
		value = "已终检，待装箱";
	} else if (item == "8") {
		value = "放弃报价，待装箱";
	} else if (item == "9") {
		value = "已报价，待付款";
	} else if (item == "10") {
		value = "不报价，待维修";
	} else if (item == "11") {
		value = "放弃报价，待维修";
	} else if (item == "12") {
		value = "已维修，待报价";
	} else if (item == "13") {
		value = "待终检";
	} else if (item == "14") {
		value = "放弃维修";
	} else if (item == "15") {
		value = "测试中";
	} else if (item == "16") {
		value = "已测试，待维修";
	} else if (item == "17") {
		value = "已受理，已测试";
	} else if (item == "18") {
		value = "已测试，待分拣";
	} else if (item == "19") {
		value = "不报价，测试中";
	}
	return value;
}