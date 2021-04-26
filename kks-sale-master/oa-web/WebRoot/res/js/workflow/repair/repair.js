$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;

$(function() {
	$("#repairUser").change(function(){
		if($.trim($("#repairUser option:selected").val())!=$.trim($("#curLoginUser").val())){
			$(".isEnabled").linkbutton('disable');
		}else{
			$(".isEnabled").linkbutton('enable');
		}
		queryListPage(1);
	});
	getCurrentUserRole();	
	datagridInit();
	keySearch(queryListPage);
	setInterval("runTip('TIP_WX')", 5000); 
	$('.panel-tool').hide();
	window.onresize = function () {
    	//调整浏览器窗口大小时，弹出框的分页会恢复成默认的数值，
    	setTimeout(function(){
    		if($("#finallyFault").val()){
    			getPageSizeZZGZ();
    		}else if($("#dzlModal").val()){
    			getPageSizeDZL();
    		}else if( $("#cplModal").val()){
    			getPageSizeCPL();
    		}else if($("#wxbjModal").val()){
    			getPageSizeWXBJ();
    		}else if($("#sllModal").val()){
    			getPageSizeSLL();
    		}
    	},500);
    	
    	}
	$("#scanimei").focus();//扫描IMEI获得焦点   
	 //获取推送消息
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	   });
	 
	 $("input[name=repair_w_isRW]").change(function(){
		var isRW = $("input[name=repair_w_isRW]:checked").val();
		$("#repair_w_isRW").val(isRW);
		getSolutionTwo();
	 });
}); 
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	var lastIndex;
	$("#DataGrid1").datagrid({
		singleSelect : false,
		onDblClickRow : function(rowIndex, row) {
			InfoUpdate(rowIndex, row);
		}
	});
	queryListPage(1);

	// 选择初检故障
	$("#zzgz_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseZZGZ(index, value);
		}
	});

	// 选择维修报价
	$("#wxbj_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseWxbj(index, value);
		}
	});

	// 选择维修组件
	$("#mat_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseMat(index, value);
		}
	});

	// 选择维修组件
	$("#dzl_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseDZL(index, value);
		}
	});
	
	// 选择试流料
	$("#sll_DataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseSLL(index, value);
		}
	});
	
	// 物料申请根据编码选择物料
	$("#WLSQ_MaterialDataGrid").datagrid({
		singleSelect : true,
		onDblClickRow : function(index, value) {
			dbClickChooseMaterial(index, value);
		}
	});
}
// --------------------------------------------- 初始化 》工具栏 ------------------------------------------ END

/**
 * 同步筛选扫描数据
 */
function scancData(event) {
	$('#DataGrid1').datagrid("clearSelections");
	var Scanimei = $.trim($("#scanimei").val());
//	var evt = evt ? evt : (window.event ? window.event : null); // 兼容IE和FF
	var evt = event;
	var nimeiAginSubmit = false;
	if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
		var rows = $("#DataGrid1").datagrid("getRows");
		var ids = null;
		if (rows) {
			for (var int = 0; int < rows.length; int++) {
				var row = rows[int];
				if (($.trim(row.imei)==Scanimei || $.trim(row.lockId)==Scanimei || $.trim(row.lockInfo)==Scanimei) && row.w_repairState.indexOf('已完成') == -1) {
					$('#DataGrid1').datagrid('selectRow', int);
					ids = row.id;
				}
			}
			if (null != ids) {
				nimeiAginSubmit = true;
				$("#scanimei").blur();
				InfoUpdate();
			} else {
				$("#scanimei").blur();
				nimeiAginSubmit = false;
				$('#DataGrid1').datagrid("clearSelections");
				$.messager.alert("操作提示", "未匹配到数据", "warning", function() {
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}
		}
	} else if (Scanimei.length > 15 && !nimeiAginSubmit) {
		nimeiAginSubmit = false;
		$.messager.alert("操作提示", "未匹配到数据", "warning", function() {
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	} else {
		$("#scanimei").val("");
		$("#scanimei").focus();
		nimeiAginSubmit = false;
	}
}

// --------------------------------------------- 列表管理 ------------------------------------------ START
/**
 * 初始化数据及查询函数
 * 
 * @param pageNumber
 *            当前页数
 */
function queryListPage(pageNumber) {
	$("#showTip").hide();
	currentPageNum = pageNumber;
	if (currentPageNum == "" || currentPageNum == null) {
		currentPageNum = 1;
	}
	getTreeNode();
	var repairState = $("#repairState").combobox('getValue');
	if(!$("#tree-Date").val() && repairState == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
		return;
	}
	var url=ctx+"/repair/repairList";
	var engineer = $("#repairUser option:selected").val();
	if(engineer == '全部'){
		engineer = '';
		//$(".isEnabled").linkbutton('disable');
	}else if(!engineer){
		engineer = $.trim($("#curLoginUser").val());
		$(".isEnabled").linkbutton('enable');
	}
    var selParams="repair_Imei="+$.trim($("#repairImei").val())
    	+"&repair_lockId=" + $.trim($("#repairLockId").val())
    	+"&repairState="+repairState
    	+"&startTime="+$("#startTime").val() 
    	+"&endTime="+$("#endTime").val() 
    	+"&treeDate="+$("#tree-Date").val()
    	+"&repair_repairnNmber="+$.trim($("#repairnNmber").val())
    	+"&repair_cusName="+$.trim($("#search_cusName").val())
    	+"&searchEngineer="+engineer;
    var param = addPageParam('DataGrid1', currentPageNum, selParams);
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){

		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data, function(i, item) {
				//上次受理日期
				if(item.lastAccTime){
					var t = (new Date(item.acceptanceTime)*1 - new Date(item.lastAccTime).getTime()*1)/1000/3600/24;//上次受理时间和受理时间的时间差（天）
					if(t <= 92){
						item.lastAccTime = "<label style='background-color:#EE3B3B;'>" + item.lastAccTime + "</label>";
					}
				}
				
				//距受理时间1天，受理时间显示黄色，两天，红色
				if(item.w_repairState != "-1"){//维修工站未发送数据
					var t =	getDayFromAcceptanceTime(item.acceptanceTime);
					if(t == 1){
						item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
					}else if(t == 2){
						item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
					}
				}
				// 试流料
				if (item.testMatStatus == "是") {
					item.testMatStatus = "<label style='color:#0000FF;font-weight: bold;'>是</label>";
				}else if (item.testMatStatus == "否"){
					item.testMatStatus = "<label style='color:#808080;;font-weight: bold;'>否</label>";
				}
				
				// 保内保外
				if (item.isWarranty == 0) {
					item.isWarranty = "<label style='color:green;font-weight: bold;'>保内</label>";
				} else if (item.isWarranty == 1) {
					item.isWarranty = "<label style='color:red;font-weight: bold;'>保外</label>";
				}
				//状态
				if(item.w_repairState=="0"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>已分拣,待维修</label>";
				}else if(item.w_repairState=="1"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>已维修,待报价</label>";
				}else if(item.w_repairState=="2"){
					item.w_repairState="<label style='color:green;font-weight: bold;'>已维修,待终检</label>";
				}else if(item.w_repairState=="3"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>已终检,待维修</label>";
				}else if(item.w_repairState=="4"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>已付款,待维修</label>";
				}else if(item.w_repairState=="5"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>不报价,待维修</label>";
				}else if(item.w_repairState=="6"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>放弃报价,待维修</label>";
				}else if(item.w_repairState=="7"){
					item.w_repairState="<label style='color:red;font-weight: bold;'>放弃报价,待装箱</label>";
				}else if(item.w_repairState=="8"){
					item.w_repairState="<label style='color:blue;font-weight: bold;'>测试中</label>";
				}else if(item.w_repairState=="9"){
					item.w_repairState="<label style='color:blue;font-weight: bold;'>已测试,待维修</label>";
				}else if(item.w_repairState=="10"){
					item.w_repairState="<label style='color:blue;font-weight: bold;'>不报价,测试中</label>";
				}else{
					item.w_repairState="<label style='color:green;font-weight: bold;'>已完成</label>";
				}

				if (item.w_isRW == 0) {
					item.w_isRW = "<label style='color:red;font-weight: bold;'>是</label>";
				} else {
					item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
				}
				(item.w_repairPrice == undefined) ? item.w_repairPrice = "0" : item.w_repairPrice = item.w_repairPrice;
				(item.w_hourFee == undefined) ? item.w_hourFee = "0" : item.w_hourFee = item.w_hourFee;
				(item.w_sumPrice == undefined) ? item.w_sumPrice = "0" : item.w_sumPrice = item.w_sumPrice;
			});
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
			$("#scanimei").val("");
			$("#scanimei").focus();
		} else {
			$.messager.alert("操作提示", returnData.msg, "info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
		$("#DataGrid1").datagrid('loaded');
		$("#acc_number").html($("#DataGrid1").datagrid('getRows').length);
		$("#scanimei").val("");
		$("#scanimei").focus();
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info", function() {
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	});
	// 重置推送消息
	resetTip('TIP_WX');
}

function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#repairState").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#repairState").combobox('setValue','1');
	}else if(treeState=='1'){
		$("#repairState").combobox('setValue','0');
	}
}

function ShowDiffentInfo(type){
	if(type && type != 'priced'){
		//已分拣待维修，已维修，待报价
		$("#baojia-table").show();
		$("#weixiu-table").show();
		$("#sureUpdate").show();
		$("#weixiuUpdate").hide();
		$("#baojiaUpdate").hide();
		$(".ficheckHide").show();
		$(".repair_beforeHide").hide();
		$('#repair_w_priceRemark').attr('disabled',false);
	}else if(type == 'priced'){
		//不报价
		$("#baojia-table").hide();
		$("#weixiu-table").show();
		$("#baojiaUpdate").hide();
		$("#weixiuUpdate").show();
		$("#sureUpdate").hide();
		$(".repair_beforeHide").show();
	}else{
		//待终检，已终检，已付款
		$('#repair_w_priceRemark').attr('disabled',true);
		$(".ficheckHide").hide();
		$("#weixiu-table").show();
		$("#baojiaUpdate").hide();
		$("#weixiuUpdate").show();
		$("#sureUpdate").hide();
		$(".repair_beforeHide").show();
	}
}

// 弹出维修框
function InfoUpdate(rowIndex, entity) {
	windowVisible("repairManage");
	//维修报价默认选中第一个
	defaultSelectFirst_WXBJ();
	
	clearFromParams();
	if (entity) {
		var re_state = entity.w_repairState;
		var subString_ret = re_state.substring(re_state.indexOf(">") + 1, re_state.indexOf("</label>")).trim();
		if ("已维修,待报价" == subString_ret || "已分拣,待维修" == subString_ret || "已测试,待维修" == subString_ret) {
			//维修报价，最终故障都显示，输入维修报价后到报价工站，输入最终故障则到终检站
			showOrUpdateInfo(false);
			ShowDiffentInfo(true);
			windowCommOpen("repairManage");
			loadEntity(entity);
			$(".repair_beforeHide").hide(); //填写最终故障之前不能选择物料
		}else if(subString_ret =="已维修,待终检" || subString_ret =="已终检,待维修" || "已付款,待维修" == subString_ret 
				|| "不报价,待维修" == subString_ret){
			//可选择组件，不可操作报价
			showOrUpdateInfo(false);	
			ShowDiffentInfo();
			windowCommOpen("repairManage");
			loadEntity(entity);
			$(".repair_beforeHide").show();
		} else if("放弃报价,待维修" == subString_ret || "放弃报价,待装箱" == subString_ret ){
			//可选择组件，不可操作报价
			showOrUpdateInfo(false);	
			ShowDiffentInfo();
			windowCommOpen("repairManage");
			loadEntity(entity);
			$(".repair_beforeHide").hide();
		} else{
			showOrUpdateInfo(true);	
			ShowDiffentInfo();
			windowCommOpen("repairManage");
			loadEntity(entity);	
			$(".repair_beforeHide").hide();
			
			$("#scanimei").val("");
			nimeiAginSubmit = false;
			$('#DataGrid1').datagrid("clearSelections");
			$("#scanimei").focus();
//			$.messager.alert("提示信息", "此状态设备不允许再维修！", "info");
		}
		$("#repair_stateFlag").val(subString_ret);
	} else {
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 1) {
			var re_state = entity[0].w_repairState;
			var subString_ret = re_state.substring(re_state.indexOf(">") + 1, re_state.indexOf("</label>")).trim();
			if ("已维修,待报价" == subString_ret || "已分拣,待维修" == subString_ret || "已测试,待维修" == subString_ret) {
				// 维修报价，最终故障都显示，输入维修报价后到报价工站，输入最终故障则到终检站
				showOrUpdateInfo(false);
				ShowDiffentInfo(true);
				windowCommOpen("repairManage");
				loadEntity(entity[0]);
				$(".repair_beforeHide").hide(); //填写最终故障之前不能选择物料
			} else if(subString_ret =="已维修,待终检" || subString_ret =="已终检,待维修" || "已付款,待维修" == subString_ret 
					|| "不报价,待维修" == subString_ret){
				//可选择组件，不可操作报价
				showOrUpdateInfo(false);
				ShowDiffentInfo();
				windowCommOpen("repairManage");
				loadEntity(entity[0]);
				$(".repair_beforeHide").show();
			} else if("放弃报价,待维修" == subString_ret || "放弃报价,待装箱" == subString_ret){
				//可选择组件，不可操作报价
				showOrUpdateInfo(false);
				ShowDiffentInfo();
				windowCommOpen("repairManage");
				loadEntity(entity[0]);
				$(".repair_beforeHide").hide();
			} else {
				showOrUpdateInfo(true);	
				ShowDiffentInfo();
				windowCommOpen("repairManage");
				loadEntity(entity[0]);
				$(".repair_beforeHide").hide();
				
				$("#scanimei").val("");
				nimeiAginSubmit = false;
				$('#DataGrid1').datagrid("clearSelections");
				$("#scanimei").focus();
			}
			$("#repair_stateFlag").val(subString_ret);
		} else if (entity.length == 0) {
			$.messager.alert("提示信息", "请先选择所要修改的行！", "info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		} else {
			$.messager.alert("提示信息", "请先选择一行进行维修！", "info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
	}
	queryListPageSLLcheck(1);
}

function getSelParamsSLLcheck(){
	var param = "proNO="+ $.trim($("#s_proNO_sll").val())
		+"&proName=" + $.trim($("#s_proName_sll").val())
		+"&bill=" + $.trim($("#repair_bill").val())
		+ "&currentpage="+currentPageNum_SLL;
	return param;
}

function queryListPageSLLcheck(pageNumber){
	currentPageNum_SLL=pageNumber;
	if(currentPageNum_SLL=="" || currentPageNum_SLL==null){
		currentPageNum_SLL=1;
	}
	var url=ctx+"/testMaterial/testMaterialList";
	var param=getSelParamsSLLcheck();
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			if(returnData.data.size>0){
				queryListPageSLL(1);
			}
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}

function showOrUpdateInfo(type){
	if(type){
		//已付款数据，只能查看，
		$('.only_show').hide();
		$('input[name=repair_w_isRW]').attr('disabled',true);
		$('#baojiaUpdate').hide();
		$('#weixiuUpdate').hide();
		$('#sureUpdate').hide();
		$('#repair_w_priceRemark').attr('disabled',true);
		$('#repair_w_repairRemark').attr('disabled',true);
		$('#repair_w_solution').attr('disabled',true);		
		$(".ficheckHide").show();
	}else{
		$('.only_show').show();
		$('input[name=repair_w_isRW]').attr('disabled',false);
		$('#repair_w_priceRemark').attr('disabled',false);
		$('#repair_w_repairRemark').attr('disabled',false);
		$('#repair_w_solution').attr('disabled',false);		
		$(".ficheckHide").show();
	}
}

function windowRepairClose() {
	$('#repairManage').window('close');
	$("#scanimei").val("");
	nimeiAginSubmit = false;
	$('#DataGrid1').datagrid("clearSelections");
	$("#scanimei").focus();
}

function clearFromParams() {
	// 清除数据
	$("#repair_modelType").val("");
	$("#repair_w_zzgzDesc").val("");
	$("#repair_w_zzgzId").val("");
	$("#repair_w_matDesc").val("");
	$("#repair_w_matId").val("");
	$("#repair_w_consumption").val("");
	$("#repair_w_dzlDesc").val("");
	$("#repair_w_dzlId").val("");
	$("#repair_w_sllDesc").val("");
	$("#repair_w_sllMatNO").val("");
	$("#repair_w_solution_two").val("");
	$("input[name='repair_w_isRW'][value='1']").prop("checked", true);
	$("#repair_w_isRW").val("");
	mat_emusIds = "";
}

function loadEntity(entity) {
	// TODO 数据展示
	$("#repair_id").val(entity.id);
	$("#rfild").val(entity.rfild);
	$("#repair_repairId").val(entity.repairId);
	$("#repair_imei").val(entity.imei);
	$("#repair_lockId").val(entity.lockId);
	$("#repair_bluetoothId").val(entity.bluetoothId);
	$("#repair_salesTime").datebox("setValue", entity.salesTime);
	var model = entity.w_model.split('|');
	$("#repair_w_model").val(model[0]);
	$("#repair_w_marketModel").val(entity.w_marketModel);
	$("#repair_modelType").val(entity.w_modelType);
	$("#repair_w_isRW").val(entity.w_isRW);
	$("#repair_accepter").val(entity.accepter);
	var isWarranty = entity.isWarranty.substring(entity.isWarranty.indexOf(">") + 1, entity.isWarranty.lastIndexOf("<"));
	$("#repair_w_isWarranty").val(isWarranty);
	$("input[name='warrantyi']").attr("disabled", true);
	$("#repair_w_sjfjDesc").val(entity.w_sjfjDesc);
	$("#repair_w_cusName").val(entity.w_cusName);
	$("#repair_repairnNmber").val(entity.repairnNmber);
	$("#repair_accepter").val(entity.accepter);
	if(entity.acceptanceTime.indexOf(">") != -1){
		$("#repair_acceptanceTime").datebox("setValue", entity.acceptanceTime.substring(entity.acceptanceTime.indexOf(">")+1,entity.acceptanceTime.indexOf("</label>")).trim());
	}else{
		$("#repair_acceptanceTime").datebox("setValue", entity.acceptanceTime);
	}
	$("#repair_returnTimes").val(entity.returnTimes);
	$("#repair_w_cjgzDesc").val(entity.w_cjgzDesc);
	$("#repair_remark").val(entity.remark);
	$("#repair_onePriceRemark").val(entity.w_onePriceRemark);
	$("#repair_testResult").val(entity.testResult);

	/*----------最终故障---------*/
	$("#repair_w_zzgzDesc").val(entity.w_zzgzDesc);
	$("#repair_w_zzgzId").val(entity.w_zzgzId);

	/*----------维修报价---------*/
	$("#repair_w_wxbjDesc").val(entity.w_wxbjDesc);
	$("#repair_w_wxbjId").val(entity.w_wxbjId);
	/*----------是否人为---------*/
	var isRW = entity.w_isRW.substring(entity.w_isRW.indexOf(">") + 1, entity.w_isRW.lastIndexOf("<"));
	if (isRW == '是') {
		$("input[name='repair_w_isRW'][value='0']").prop("checked", true);
	} else {
		$("input[name='repair_w_isRW'][value='1']").prop("checked", true);
	}

	/*----------维修组件---------*/
	$("#repair_w_matDesc").val(entity.w_matDesc);
	$("#repair_w_matId").val(entity.w_matId);

	$("#repair_w_dzlDesc").val(entity.w_dzlDesc);
	$("#repair_w_dzlId").val(entity.w_dzlId);
	
	$("#repair_w_sllDesc").val(entity.w_sllDesc);
	$("#repair_w_sllMatNO").val(entity.w_sllMatNO);
	
	$("#repair_w_engineer").val(entity.w_engineer);
	$("#repair_lastEngineer").val(entity.lastEngineer);
	
	if(entity.lastAccTime && entity.lastAccTime.indexOf(">") != -1){
		$("#repair_lastAccTime").val(entity.lastAccTime.substring(entity.lastAccTime.indexOf(">")+1,entity.lastAccTime.indexOf("</label>")).trim());
		$("#repair_lastAccTime").attr("style","color:#EE3B3B;");
	}else{
		$("#repair_lastAccTime").val(entity.lastAccTime);
		$("#repair_lastAccTime").removeAttr("style");
	}
	
	$("#repair_bill").val(entity.bill);
	$("#repair_sfVersion").val(entity.sfVersion);
	$("#repair_outCount").val(entity.outCount);
	
	
	if(entity.testMatStatus && entity.testMatStatus.indexOf(">") != -1){
		$("#repair_testMatStatus").val(entity.testMatStatus.substring(entity.testMatStatus.indexOf(">")+1,entity.testMatStatus.indexOf("</label>")).trim());
	}else{
		$("#repair_testMatStatus").val(entity.testMatStatus);
	}
//	$("#repair_testMatStatus").val(entity.testMatStatus);
	$("#repair_insideSoftModel").val(entity.insideSoftModel);
	$("#repair_pjl_insideSoftModel").html(entity.insideSoftModel);
	
	$("#repair_w_repairRemark").val(entity.w_repairRemark);
	$('#repair_w_solution').val(entity.w_solution);
	$("#repair_w_solution_two").val(entity.w_solutionTwo);
	$('#repair_w_priceRemark').val(entity.w_priceRemark);	
	
	var state = entity.w_repairState.substring(entity.w_repairState.indexOf(">") + 1, entity.w_repairState.indexOf("</label>")).trim();
	if(state == '已维修,待终检'){
		$("#update_repairState").val(2);
	}else if(state == '已终检,待维修'){
		$("#update_repairState").val(3);
	}else if(state == '已付款,待维修'){
		$("#update_repairState").val(4);
	}else if(state == '不报价,待维修'){
		$("#update_repairState").val(5);
	}else if(state == '放弃报价,待维修'){
		$("#update_repairState").val(6);
	}else if(state == '放弃报价,待装箱'){
		$("#update_repairState").val(7);
	}else if(state == '测试中'){
		$("#update_repairState").val(8);
	}else if(state == '已测试,待维修'){
		$("#update_repairState").val(9);
	}else if(state == '已分拣,待维修'){
		$("#update_repairState").val(0);
	}else if(state == '已维修,待报价'){
		$("#update_repairState").val(1);
	}else if(state == '已完成'){
		$("#update_repairState").val(-1);
	}else if(state == '不报价，测试中'){
		$("#update_repairState").val(19);
	}else{
		$("#update_repairState").val("");
	}
}

//逻辑获得处理措施2的显示内容
function getSolutionTwo(){
	//	1、当是保内机器（无异常除外），处理措施1为维修填写的处理措施，处理措施2：换板；    
	//	2、当是车载类保内机器，且是无异常，处理处理措施1和处理措施2 一样；	                                       
	//	3、保外机器，非车载类机器，人为损坏时，处理措施1和处理措施2 一样
	var solution = $('#repair_w_solution').val();
	var repairZzgzDesc = $('#repair_w_zzgzDesc').val();
	var zzgzIds = $('#repair_w_zzgzId').val().trim();
	var warranty = $.trim($("#repair_w_isWarranty").val()); //保内外
	var w_isRW = $("#repair_w_isRW").val();
	if(w_isRW.indexOf("</label>") > -1){
		w_isRW = w_isRW.substring(w_isRW.indexOf(">") + 1, w_isRW.indexOf("</label>")).trim();  //是否人为
	}
	if(w_isRW == '0' || w_isRW == '是'){
		$('#repair_w_solution_two').val(solution);
	}else{
		if(repairZzgzDesc){
			if(warranty == '保内'){
				/*if(repairZzgzDesc.indexOf("无异常") == -1){
					$('#repair_w_solution_two').val("换板");
				}else{			
					$('#repair_w_solution_two').val(solution);			
				}*/
				//根据最终故障ids查询同步标识
				asyncCallService(ctx + '/zgzmanagecon/getZgzmanageByIds', 'post', false,'json', {ids : zzgzIds}, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == undefined) {
						var isSync = true;
						$.each(returnData.data, function(i, item) {
							if (item.isSyncSolution == 1 || item.isSyncSolution == '是') {
								isSync = true;
								return false;//其中一个允许同步就允许措施1和2相同，终止循环
							} else {
								isSync = false;
							}
						});
						if (isSync) {
							$('#repair_w_solution_two').val(solution);
						} else {
							$('#repair_w_solution_two').val("换板");
						}
					} else {
						$.messager.alert("操作提示", returnData.msg, "info");
					}
				}, function(){
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}else{
				$('#repair_w_solution_two').val(solution);
			}
		}
		
	}	
}

//确定报价还是直接发送终检
function doSureUpdate(){
	var repairPrice = $('#repair_w_wxbjDesc').val();
	var repairZzgzDesc = $('#repair_w_zzgzDesc').val();
	if(repairZzgzDesc){
		//填写最终故障，状态变为：已维修，待终检
		doSaveRepair();
	}else if(repairPrice){
		//添加维修报价，到已维修，待报价
		repairUpdate();
	}else {
		repairUpdate();
	}
}

//保存报价
function repairUpdate(){
	if(parseInt($("input[name=repair_w_isRW]:checked").val())==1 &&
				$.trim($("#repair_w_isWarranty").val())=="保内"){//保内非人为不允许填写维修报价
		if($("#repair_w_wxbjDesc").val()){
			$.messager.alert("操作提示","<font color='red'>保内非人为</font>设备不能选择<font color='red'>维修报价</font>！","info");
			return;
		}else{//保内非人为要填写最终故障
			doSaveRepair();
			return;
		}
	}else{
		if(!$("#repair_w_wxbjDesc").val()){
			$.messager.alert("操作提示","请选择维修报价","info");
			return;
		}
	}
	var url=ctx+"/repair/updateInfo";
	var param="wfId="+$("#repair_id").val()+
	"&rfild="+$("#rfild").val()+
	"&isRW="+$("input[name='repair_w_isRW']:checked").val()+
	"&repairRemark="+$("#repair_w_repairRemark").val()+
	"&priceRemark="+$("#repair_w_priceRemark").val()+
	"&wxbjDesc="+$("#repair_w_wxbjId").val()+
	"&dzlDesc="+$("#repair_w_dzlId").val()+
	"&repairState="+$("#update_repairState").val()+ //修改之前比较数据库状态，一致，允许修改
	"&matDesc="+$("#repair_w_matId").val()+
	"&sllDesc="+$("#repair_w_sllMatNO").val();
	
	asyncCallService(url, 'post', false,'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if(returnData.code=='0'){
			$.messager.alert("操作提示",returnData.msg,"info",function(){
				windowCommClose("repairManage");
				queryListPage(currentPageNum);
			});
		}else {
			$.messager.alert("操作提示",returnData.msg,"info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info", function() {
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	});
}

//保存维修
function doSaveRepair(){
	if(!$("#repair_w_zzgzDesc").val()){
		$.messager.alert("操作提示","最终故障必选","info");
	}else if(!$('#repair_w_solution').val()){
		$.messager.alert("操作提示","请填写处理措施","info");
	}else{
		if($.trim($("#repair_testMatStatus").val()) == '是' && !$("#repair_w_sllMatNO").val()){
			$.messager.confirm("操作提示","该设备已使用<font color='red'>试流料</font>，是否放弃选择？",function(con){
				if(con){
					updateRepairInfo();
				}
			});
		}else{
			updateRepairInfo();
		}
	}
}

function updateRepairInfo() {
	var url=ctx+"/repair/updateInfo";
	var param="wfId="+$("#repair_id").val()+
	"&rfild="+$("#rfild").val()+
	"&isRW="+$("input[name='repair_w_isRW']:checked").val()+
	"&repairRemark="+$("#repair_w_repairRemark").val()+
	"&wxbjDesc="+$("#repair_w_wxbjId").val()+
	"&zzgzDesc="+$("#repair_w_zzgzId").val()+
	"&dzlDesc="+$("#repair_w_dzlId").val()+
	"&matDesc="+$("#repair_w_matId").val()+
	"&sllDesc="+$("#repair_w_sllMatNO").val()+
	"&repairState="+$("#update_repairState").val()+ //修改之前比较数据库状态，一致，允许修改
	"&solution="+$('#repair_w_solution').val()+
	"&solutionTwo="+$("#repair_w_solution_two").val();
	asyncCallService(url, 'post', false,'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			$.messager.alert("操作提示", returnData.msg, "info", function() {
				windowCommClose("repairManage");
				queryListPage(currentPageNum);
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info", function() {
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	});
}

// 不报价
function notPrice() {
	var sendP = $('#DataGrid1').datagrid('getSelections');
	if (sendP.length > 0) {
		$.messager.confirm("操作提示", "确定不报价吗？", function(conf) {
			if (conf) {
				var ids = "";
				var errorMsg = "";
				for (var i = 0; i < sendP.length; i++) {
					if (sendP[i].w_repairStateFalg == 0) { // 已分拣，待维修
						(i == 0) ? ids = sendP[i].id : ids = sendP[i].id + "," + ids;
					}
				}
				if (ids != "" && ids!=null && ids!=undefined) {
					var url = ctx + "/repair/notPrice";
					var param = "ids=" + ids;
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == '0') {
							$.messager.alert("操作提示", "不报价成功！", "info", function() {
								$("#scanimei").val("");
								$("#scanimei").focus();
							});
							queryListPage(currentPageNum);
						} else {
							$.messager.alert("操作提示", returnData.msg, "info", function() {
								$("#scanimei").val("");
								$("#scanimei").focus();
							});
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info", function() {
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					});
				} else {
					$.messager.alert("操作提示", "未找到<font color='red'>已分拣,待维修</font>的数据", "warning", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}
			}
		});
	} else {
		$.messager.alert("提示信息", "请先选择不报价的行！", "warning", function() {
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	}
}

// 发送报价
function sendPrice() {
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var marked_words="";
	var all = false;
	if (sendP.length > 0) {
		marked_words = "确定要发送<font color='red'>选中数据</font>到报价？";
	} else {
		//一键发送所有待报价数据到报价工站
		marked_words = "确定要发送所有<font color='red'>已维修,待报价</font>的数据到报价？";
		sendP = $('#DataGrid1').datagrid('getRows');
		all = true;
	}
	$.messager.confirm("操作提示", marked_words, function(conf) {
		if (conf) {
			var ids = "";
			var notPriceIds = "";//点击报价后，已分拣、待维修状态要变成不报价、待维修
			var notPriceRepairNumber = "";//不报价的批次号
			var errorMsg = "";
			for (var i = 0; i < sendP.length; i++) {
				if(!all){
					if (sendP[i].w_repairStateFalg == 1) { // 已维修，待报价
						(i == 0) ? ids = sendP[i].id : ids = sendP[i].id + "," + ids;
						(i == 0) ? notPriceRepairNumber = sendP[i].repairnNmber : notPriceRepairNumber = sendP[i].repairnNmber + "," + notPriceRepairNumber;
					}else if (sendP[i].w_repairStateFalg == 0) {
						errorMsg = "发送报价列表中存在未填写维修信息！";
						ids = "";
						break;
					}else if(sendP[i].w_repairStateFalg != 0 || sendP[i].w_repairStateFalg != 1){
						errorMsg = "发送报价列表中存在不支持报价服务！";
						ids = "";
						break;
					}else{
						(i == 0) ? notPriceIds = sendP[i].id : notPriceIds = sendP[i].id + "," + notPriceIds;
					}
				}else{
					if (sendP[i].w_repairStateFalg == 1) { // 已维修，待报价
						(i == 0) ? ids = sendP[i].id : ids = sendP[i].id + "," + ids;
						(i == 0) ? notPriceRepairNumber = sendP[i].repairnNmber : notPriceRepairNumber = sendP[i].repairnNmber + "," + notPriceRepairNumber;
					}else if (sendP[i].w_repairStateFalg == 0) {
						(i == 0) ? notPriceIds = sendP[i].id : notPriceIds = sendP[i].id + "," + notPriceIds;
					}
				}
			}
			if (ids != "" && ids!=null && ids!=undefined) {
				var url = ctx + "/repair/sendPrice";
				var param = "ids=" + ids;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						if (notPriceIds != "" && notPriceIds!=null && notPriceIds!=undefined && notPriceRepairNumber != "" && notPriceRepairNumber!=null && notPriceRepairNumber!=undefined) {
							var url = ctx + "/repair/sendPriceUpdateState";
							var param = "notPriceIds=" + notPriceIds+"&notPriceRepairNumber=" + notPriceRepairNumber;
							asyncCallService(url, 'post', false, 'json', param, function(ret) {
								processSSOOrPrivilege(ret);
								if (ret.code == '0') {
									$.messager.alert("操作提示", "发送报价成功！", "info", function() {
										$("#scanimei").val("");
										$("#scanimei").focus();
									});
									queryListPage(currentPageNum);
								} else {
									$.messager.alert("操作提示", ret.msg, "info", function() {
										$("#scanimei").val("");
										$("#scanimei").focus();
									});
									return;
								}
							}, function() {
								$.messager.alert("操作提示", "网络错误", "info", function() {
									$("#scanimei").val("");
									$("#scanimei").focus();
								});
								return;
							});
						}
						queryListPage(currentPageNum);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				});
			} else {
				if(errorMsg){
					$.messager.alert("操作提示", errorMsg, "warning", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}else{
					$.messager.alert("操作提示", "未找到符合发送报价的数据", "warning", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}
			}
		}
	});
}

var submitFlag = true;
// 发送终检
function sendFicheck() {
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var marked_words="";
	var all = false;
	if (sendP.length > 0) {
		marked_words = "确定要发送<font color='red'>选中数据</font>到终检？";
	} else {
		//一键发送所有已维修，待终检数据到终检工站
		marked_words = "确定要发送所有<font color='red'>已维修，待终检</font>的数据到终检？";
		sendP = $('#DataGrid1').datagrid('getRows');
		all = true;
	}
	$.messager.confirm("操作提示", marked_words, function(conf) {
		if (conf) {
			var ids = "";
			var errorMsg = "";
			for (var i = 0; i < sendP.length; i++) {
				if (sendP[i].w_repairStateFalg == 2) { // 已维修，待终检
					(i == 0) ? ids = sendP[i].id : ids = sendP[i].id + "," + ids;
				} else if (sendP[i].w_repairStateFalg == 0 && !all) {
					errorMsg = "发送终检列表中存在未填写维修信息！";
					ids = "";
					break;
				} else if(!all) {
					errorMsg = "发送终检列表中存在不支持终检服务！";
					ids = "";
					break;
				}
			}
			
			if (ids != "" && ids!=null && ids!=undefined) {
				if(!submitFlag){
					return;
				}
				submitFlag = false;
				var url = ctx + "/repair/sendFicheck";
				var param = "ids=" + ids;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "发送终检成功！", "info", function() {
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
						queryListPage(currentPageNum);						
					} else {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					}
					submitFlag = true;
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
						submitFlag = true;
					});
				});
			} else {
				if(errorMsg){
					$.messager.alert("操作提示", errorMsg, "warning", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}else{
					$.messager.alert("操作提示", "未找到匹配数据", "warning", function() {
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}
			}
		}
	});
}

//放弃报价的数据发送装箱
function sendPack(){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var marked_words="";
	var count = 0;//不符合条件数据个数
	var batchSend = false; //批量发送
	if (sendP.length > 0) {
		marked_words = "确定发送<font color='red'>选中数据</font>到装箱？";
	} else {
		//一键发送所有放弃报价,待装箱数据到装箱工站
		marked_words = "确定发送所有<font color='red'>放弃报价,待装箱</font>的数据到装箱？";
		sendP = $('#DataGrid1').datagrid('getRows');
		batchSend = true;
	}
	
	$.messager.confirm("操作提示", marked_words, function(conf){
		if(conf){
			var ids = "";
			for (var i = 0; i < sendP.length; i++) {
				if(sendP[i].w_repairStateFalg == 7){//（放弃报价的数据）放弃报价,待装箱），直接发送装箱
					(i ==0)?ids = sendP[i].id:ids = sendP[i].id+","+ids;
				}else{
					count++;
				}
			}
			if(count > 0 && !batchSend){
				errorMsg = "发送装箱列表中存在不支持装箱服务！";
				ids = "";
			}
			if(ids!="" && ids!=null && ids!=undefined){
				var url=ctx+"/repair/sendPack";
				var param="ids="+ids;										
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送装箱成功！","info",function(){
							queryListPage(currentPageNum);
						});
					}else{
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}, function(){
					$.messager.alert("操作提示","网络错误","info");
				});
			}else{
				$.messager.alert("操作提示", "有不匹配数据在", "warning");
			}
		}
	});
	
}

// 已分拣待维修的数据返回到分拣工站
function sendDataToSort(){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var marked_words="";
	var count = 0;//不符合条件数据个数
	var batchSend = false; //批量发送
	if (sendP.length > 0) {
		marked_words = "确定发送<font color='red'>选中数据</font>到分拣工站？";
	} else {
		//一键发送所有已分拣，待维修数据到分拣工站
		sendP = $('#DataGrid1').datagrid('getRows');
		if(sendP.length>0){
			//一键发送符合条件的数据到分拣
			marked_words = "确定要发送所有<font color='red'>待维修或已维修，待报价</font>的数据到分拣工站？";
			batchSend = true;
		}else{
			$.messager.alert("操作提示","未找到匹配数据","info");
			return;
		}
	}
	
	 $.messager.confirm("操作提示",marked_words,function(conf){
			if(conf){
				var ids = "";
				var repairStates = "";
				for (var i = 0; i < sendP.length; i++) {
					if(sendP[i].w_repairStateFalg == 0 || sendP[i].w_repairStateFalg == 1 || 
							sendP[i].w_repairStateFalg == 5 || sendP[i].w_repairStateFalg == 6){ // 已分拣，待维修；已维修，待报价 ；不报价，待维修 ；放弃报价,待维修 的数据
						(i == 0) ? ids = sendP[i].id : ids = sendP[i].id + "," + ids;
						(i == 0) ? repairStates = sendP[i].w_repairStateFalg : repairStates = sendP[i].w_repairStateFalg + "," + repairStates;
					}else{
						count++;
					}
				}
				if(count > 0 && !batchSend){
					errorMsg = "返回分拣列表中存在不支持分拣服务！";
					ids = "";
				}
				if(ids!="" && ids!=null && ids!=undefined){
					var url=ctx+"/repair/sendDataToSort";
					var param="ids="+ids+
							  "&repairState="+repairStates;										
					asyncCallService(url, 'post', false,'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						if(returnData.code=='0'){
							$.messager.alert("操作提示","匹配数据返回分拣成功！","info",function(){
								queryListPage(currentPageNum);
							});
						}else{
							$.messager.alert("操作提示",returnData.msg,"info");
						}
					}, function(){
						$.messager.alert("操作提示","网络错误","info");
					});
				}else{
					$.messager.alert("操作提示", "未找到匹配数据", "warning");
				}
			}
	 });
	
}

//发送到测试工站
function sendWorkTest(){
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	if (SendInfo.length>0) {
		var marked_words = "确定要发送<font color='red'>选中数据</font>到测试？";
		$.messager.confirm("操作提示",marked_words,function(conf){						
		if(conf){
			var ids = "";
			for (var i = 0; i < SendInfo.length; i++) {
				if(SendInfo[i].w_repairStateFalg == 0 ){ // 已分拣，待维修； 的数据
					(i == 0) ? ids = SendInfo[i].id : ids = SendInfo[i].id + "," + ids;
				}
			}
			
			if(ids){
				var testuser = $("#test_user option:selected").val();
				var url=ctx+"/workflowcon/sendWorkTest";
			    var param = {
		    		"ids" : ids,
		    		"dataSource" : "维修站",
		    		"testStatus" : "4",			//待维修，测试中
		    		"testPerson" : testuser
			    }; 										
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送测试成功！","info",function(){
							queryListPage(currentPageNum);
							windowCommClose('testUser_w');
						});
					}else{
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}, function(){
					$.messager.alert("操作提示","网络错误","info");
				});
			}else{
				$.messager.alert("操作提示", "未找到匹配数据", "warning");
			}
		}
		});
	}else{
		$.messager.alert("操作提示","未找到匹配数据","info");
	}
}

//维修     177
var repair_oId = 177;
//终端技术     180
var terminal_oId = 180;
//几米    147   软件一部
var jimi_oId = 147;
/**
 * 获取当前登录人角色
 */
function getCurrentUserRole(){
	var url=ctx+"/rolePrivilege/getCurrentUserRoleId";
	asyncCallService(url, 'post', false,'json', '', function(returnData){		
		if(returnData.code==undefined){
			// 177 线上售后部 组织架构ID,本地测试可用92
			var getRoleIdListData=returnData.data;
			if(getRoleIdListData.indexOf('25') > -1 || getRoleIdListData.indexOf('96') > -1){
				initUserInfo("repairUser", '全部', "全部", ctx+"/user/UserListByOrgId", "oId=" + repair_oId, "维修");
//				initUserInfo("repairUser", '全部', "全部", ctx+"/user/UserListByOrgId", "oId=" + jimi_oId, "");
				$('#repairUserSpan').show();
				$(".isEnabled").linkbutton('enable');//管理员一打开，按钮禁用
			}else{
				$('#repairUserSpan').hide();
				$(".isEnabled").linkbutton('enable');//其他人员打开，按钮启用
			}
			
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
			
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
}

/* ==========================================物料申请 start======================================== */
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

// 清除物料申请表单数据
function clearw1Apply() {
	// 清空下缓存
	$("#appId").val(0);
	$("#proSpeci").val("");
	$("#unit").val("PCS");
	$("#platform").val("");
	$("#number").val("");
	$("#proName").val("");
	$("#proNO").val("");
	$("#appTime").val("");
	$("#purpose").val("");
	$("#wl_remark").val("");
	$("#matType").combobox('setValue','0')
	
}

// 弹出物料申请
function w1Apply() {
	windowVisible("w1Apply");
	clearw1Apply();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#w1Apply').window('open');
	chageWinSize('w1Apply');
}



function wlAdd() {
	var url = ctx + "/wlsqmanage/insertWlsqmanage";
	// 未选择时间时，默认当前时间
	var appTime = $("#appTime").val();
	if (!appTime) {
		$("#appTime").val(initTime());
	}
	appTime = $("#appTime").val();

	var isValid = $('#w1Applyform').form('validate');
	if(isValid){
		var param=
			{
				"proSpeci" : $("#proSpeci").val(),
				"unit"  :  $("#unit").val(),
				"matType" : $("#matType").combobox('getValue'),
				"platform" : $("#platform").val(),
				"number" : $("#number").val(),
				"proName" : $("#proName").val(),
				"proNO" : $("#proNO").val(),
				"applicater" : $("#applicater").val(),
				"appDate" : appTime,
				"purpose" : $("#purpose").val(),
				"state" : "1",
				"remark" : $("#wl_remark").val()
			};
		
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			
			processSSOOrPrivilege(returnData);

			if (returnData.code == '0') {
				$.messager.alert("操作提示", returnData.msg, "info", function() {
					windowCommClose('w1Apply');
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			} else {
				$.messager.alert("操作提示", returnData.msg, "info", function() {
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}

		}, function() {
			$.messager.alert("操作提示", "网络错误", "info", function() {
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		});
	}
}

function wlApply_close(){
	windowCommClose('w1Apply');
	$("#scanimei").val("");
	$("#scanimei").focus();
}
/* ==========================================物料申请 end======================================== */

// --------------------------------------------- 列表管理 ------------------------------------------ END

$(function() {
	basegroupTreeInit();
	datagridInit6();
	basegroupTreeSelect();
	comboboxInit();
	keySearch(queryListPage);
	keySearch(queryListPagePJL, 1);
	keySearch(queryListPagePGPJ, 3);
});

function datagridInit6() {
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

$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};

function repairPrice_addInfo() {
	// 清空下缓存
	$("#rid_hidden").val(0);
	$("#repairNumber").val("");
	$("#pjlPrice").val(0);
	$("#hourFee").val(0);
	$("#model").val($("#repair_w_model").val());
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
			$.messager.alert("操作提示", returnData.msg, "info");
			updateWindowClose();
		}
		
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
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
