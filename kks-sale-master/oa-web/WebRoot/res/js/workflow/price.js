$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
var price = false;
var currentPageNumTwo;
var refreshTime =20000 //20秒刷新一次
var currentPageNum_Pj;
var currentPageNum_groupPj;
var openType = "";

function clickFocus() {
	document.getElementById("price_imei").style.height = "120px";
	document.getElementById("price_imei").style.overflow = "auto";
}

function clickBlur() {
	document.getElementById("price_imei").style.height = "26px";
	document.getElementById("price_imei").style.overflow = "hidden";
}

$(function(){    
	datagridInit(); 
	queryProlongCost();
//   	setInterval("autoPrice()", 60 * 1000);
   
  //获取推送消息
	setInterval("runTip('TIP_BJ')", 5000);  
	keySearch(queryListPage);
	keySearch(queryListPageWxbj,2);
	keySearch(queryListPageCPL,3);
	keySearch(queryListPageDZL,4);
	keySearch(queryListPj,5);
	keySearch(queryListPagePj,6);
	keySearch(queryListPageTwo,7);
	keySearch(repairnNumberList,8);
	keySearch(queryListPageGroupPj,9);//销售配件料组合
	selAllState('search_State');
	
    $('input:radio[name="prolong_year"]').click(function() {
    	var price;
    	var year = $("input:radio[name=prolong_year]:checked").val();
    	if (year == "1"){
    		price = oneYearPrice;
    	} else if (year == "2"){
    		price = twoYearPrice;
    	} else if (year == "3"){
    		price = threeYearPrice;
    	}
    	$("#prolong_cost").val(price);
    	var num = $("#prolong_devices").val();
    	$("#prolong_total_price").val(num * price);
    });
	
	 $('.panel-tool').hide();
	 
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	   });
	 
	 $("input:radio[name=bathPrice_receipt]").live('change',function(){
			var receiptVal = $("input:radio[name=bathPrice_receipt]:checked").val();
			if(receiptVal == 0){
				//开发票
				$("#rate").attr("disabled",false);
				$("#rate").val("17");				
			}else{
				$("#rate").val("");
				$("#rate").attr("disabled",true);
			}
			getRatePrice();
		});
	 
	 $("input:radio[name=bathPrice_receipt_logcost]").change(function(){
		 if($("input:radio[name=bathPrice_receipt_logcost]:checked").val()==0){
			 $("#rate_logcost").attr("disabled",false);
			 $("#rate_logcost").val(17);					
		 }else{
			 $("#rate_logcost").attr("disabled",true);
			 $("#rate_logcost").val("");
		 }			
		  getLogcostRatePrice();
	});	
	 
	 $("#logCost").blur(function(){
		 if(!$("#logCost").val()){
			 $("#logCost").val(0);
		 }
		 getLogcostRatePrice();
	 });
	 
	 $("#rate_logcost").blur(function(){
		 if(!$("#rate_logcost").val()){
			 $("#rate_logcost").val(0);
		 }
		 getLogcostRatePrice();
	 });

}); 

// 自动报价
function autoPrice(){
	var url=ctx+"/totalpay/autoTotalpay";
	asyncCallService(url, 'post', false,'json', null, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code == undefined){
			$.messager.alert("自动报价提示", "以下批次号已自动报价:" + returnData.data.toString(),"info");
			console.log(returnData.data);
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber)  {//初始化数据及查询函数
	$("#showTip").hide();
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	} 
	getTreeNode();
	var priceState = $("#priceState").combobox('getValue');
	
	if(!$("#tree-Date").val() && priceState == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		if(!$("#price_cusName").val()){
			$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
			return;
		}
	}
	if($("#price_imei").val()){
		var patt = /^[0-9\n]+$/i;
		if(!patt.test($("#price_imei").val())){
			$.messager.alert("操作提示","请输入正确的IMEI","info");
			return;
		}
	}
	var imeis = $("#price_imei").val().trim().replace(/\n/g, ',');
	
	var url=ctx+"/price/priceList";
    var selParams="price_repairnNmber="+$.trim($("#repairnNmber").val())
    		  +"&price_imei="+$.trim(imeis)
    		  +"&price_lockId="+$.trim($("#price_lockId").val())
    		  +"&price_cusName="+$.trim($("#price_cusName").val())
    		  +"&priceState="+priceState
    		  +"&state="+$("#state").combobox('getValue')
	    	  +"&startTime="+$("#startTime").val() 
	    	  +"&endTime="+$("#endTime").val() 
	    	  +"&treeDate="+$("#tree-Date").val();
	  var param = addPageParam('DataGrid1', currentPageNum, selParams);
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			commData(ret);
			$("#DataGrid1").datagrid('loadData',ret);  
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
		getPageSize();
		resetCurrentPageShow(currentPageNum);
	}, function(){
			$("#DataGrid1").datagrid('loaded');	
		 	$.messager.alert("操作提示","网络错误","info");
	});
	//重置推送消息
    resetTip('TIP_BJ');
 } 

function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#priceState").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#priceState").combobox('setValue','1');
	}else if(treeState=='1'){
		$("#priceState").combobox('setValue','2');
	}
}

//公共的显示数据
function commData(ret){
	if(ret&&ret.length>0){  
		$.each(ret,function(i,item){
			//距受理时间1天，受理时间显示黄色，两天，红色
			if(item.state != "-1"){//未发货数据
				var t =	getDayFromAcceptanceTime(item.acceptanceTime);
				if(t == 1){
					item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
				}else if(t == 2){
					item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
				}			
			}
			//保内/保外
			if(item.isWarranty==0){
				item.isWarranty="<label style='color:green;font-weight: bold;'>保内</label>";
			}else if(item.isWarranty==1){
				item.isWarranty="<label style='color:red;font-weight: bold;'>保外</label>";
			}
			//是否人为
			if (item.w_isRW == 0) {
				item.w_isRW = "<label style='color:red;font-weight: bold;'>是</label>";
			} else if(item.w_isRW == 1) {
				item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
			}
			//是否付款
			if(item.w_isPay==0){
				item.w_isPay="<label style='color:green;font-weight: bold;'>是</label>";
			}else if(item.w_isPay=='1'){
				item.w_isPay="<label style='color:red;font-weight: bold;'>否</label>";
			}
			//报价表：报价状态
			if(item.w_priceState=='0'){
				item.w_priceState="<label style='color:red;font-weight: bold;'>待报价</label>";
			}else if(item.w_priceState=='1'){
				item.w_priceState="<label style='color:green;font-weight: bold;'>已付款，待维修</label>";
			}else if(item.w_priceState=='2'){
				item.w_priceState="<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
			}else if(item.w_priceState=='4'){
				item.w_priceState="<label style='color:red;font-weight: bold;'>放弃维修</label>";
			}else {
				item.w_priceState="<label style='color:green;font-weight: bold;'>已完成</label>";
			}
			//客户寄货方式
			if(item.payDelivery =='Y'){
				item.payDelivery="<label style='color:#0066FF;font-weight: bold;'>到付</label>";
			}else{
				item.payDelivery="<label style='font-weight: bold;'>顺付</label>";
			}
			//是否收税
			if(item.w_receipt == '0'){
				item.w_receipt = "<label style='color:red;font-weight: bold;'>是</label>";
			}else{
				item.w_receipt = "<label style='color:green;font-weight: bold;'>否</label>";
			}
			
			//付款方式
			if(item.w_t_remAccount == 'personPay'){
				item.w_t_remAccount = "<label style='font-weight: bold;'>人工付款</label>";
			}else if(item.w_t_remAccount == 'aliPay'){
				item.w_t_remAccount = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
			}else if(item.w_t_remAccount == 'weChatPay'){
				item.w_t_remAccount = "<label style='color:green;font-weight: bold;'>微信付款</label>";
			}else{
				item.w_t_remAccount = "";
			}
			
			//流程表：状态
			if (item.state == '-1') {
				item.state = "<label style='color:green;font-weight: bold;'>已发货</font>";
			} else if (item.state == '0') {
				item.state = "<label style='color:green;font-weight: bold;'>已受理</font> ";
			} else if (item.state == '1') {
				item.state = "<label style='color:green;font-weight: bold;'>已受理，待分拣</font>";
			} else if (item.state == '2') {
				item.state = "<label style='color:green;font-weight: bold;'>已分拣，待维修</font>";
			} else if (item.state == '3') {
				item.state = "<label style='color:red;font-weight: bold;'>待报价</font>";
			} else if (item.state == '4') {
				item.state = "<label style='color:green;font-weight: bold;'>已付款，待维修</font>";
			} else if (item.state == '5') {
				item.state = "<label style='color:green;font-weight: bold;'>已维修，待终检</font>";
			} else if (item.state == '6') {
				item.state = "<label style='color:green;font-weight: bold;'>已终检，待维修</font>";
			} else if (item.state == '7') {
				item.state = "<label style='color:green;font-weight: bold;'>已终检，待装箱</font>";
			} else if (item.state == '8') {
				item.state = "<label style='color:green;font-weight: bold;'>放弃报价，待装箱</font>";
			} else if (item.state == '9') {
				item.state = "<label style='color:green;font-weight: bold;'>已报价，待付款</font>";
			} else if (item.state == '10') {
				item.state = "<label style='color:green;font-weight: bold;'>不报价，待维修</font>";
			}else if(item.state=="11"){
				item.state="<label style='color:red;font-weight: bold;'>放弃报价，待维修</label>";
			}else if (item.state == "12") {
				item.state = "<label style='color:red;font-weight: bold;'>已维修，待报价</label>";				
			}else if (item.state == "13") {
				item.state = "<label style='color:red;font-weight: bold;'>待终检</label>";				
			}else if (item.state == "14") {
				item.state = "<label style='color:red;font-weight: bold;'>放弃维修</label>";	
			}else if (item.state == "15") {
				item.state = "<label style='color:red;font-weight: bold;'>测试中</label>";	
			}else if (item.state == "16") {
				item.state = "<label style='color:red;font-weight: bold;'>已测试,待维修</label>";	
			}else if (item.state == "17") {
				item.state = "<label style='color:red;font-weight: bold;'>已受理,已测试</label>";	
			}else if (item.state == "18") {
				item.state = "<label style='color:red;font-weight: bold;'>已测试,待分拣</label>";	
			}else if (item.state == "19") {
				item.state = "<label style='color:red;font-weight: bold;'>不报价,测试中</label>";	
			}
			
			
			if (item.testResult) {
				item.testResult = "<span title="+item.testResult+">"+item.testResult+"</span>";
			}
		});
	}
}

/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	$("#DataGrid1").datagrid({
     singleSelect:false,
		onDblClickRow:function(rowIndex,row){
			showInfo(rowIndex,row);
		}
	}); 
	queryListPage(1);//初始化查询页面数据，必须放在datagrid()初始化调用之后
	
	//选择维修报价
	$("#wxbj_DataGrid").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	 dbClickChooseWxbj(index,value);
	 	 }
	});
	
	//选择配件料
	$("#mat_DataGrid").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	dbClickChooseMat(index,value);
	 	 }
	});
	
	//选择配件料
	$("#dzl_DataGrid").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	dbClickChooseDZL(index,value);
	 	 }
	});
	
	//批次配件料管理
	$("#batchPj_DataGrid").datagrid({
	     singleSelect:false,
	     onDblClickRow: function(index,value){
	    	 batchPj_edit(index,value);
	 	 }
	});
	
	//批次配件料选择
	$("#choosepj_DataGrid").datagrid({
	     singleSelect:false
	});
	
	//批次销售配件料组合选择
	$("#chooseGroupPj_DataGrid").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
		    showGroupPjDetails(index,value);//销售配件料组合详情
		}
	});
	
	//批次销售配件料组合详情
	$("#groupPjDetails_DataGrid").datagrid({
	});
}

/*=========================================查看 start==============================================*/
// 初始化查看表单工具栏面板
function batchInfoInit(){
	//详情
	$("#batchInfo_DataGrid").datagrid({
     singleSelect:false,
	});
	//维修状态统计
	$("#repairState_DataGrid").datagrid({
		singleSelect : false
	});
	queryBatchList();
}

//弹出报价框
function showInfo(rowIndex,entity){
	//维修报价默认选中第一个
	defaultSelectFirst_WXBJ();
	
	if(entity){
		var state = entity.w_priceState.substring(entity.w_priceState.indexOf(">")+1,entity.w_priceState.indexOf("</label>")).trim();
		if(state == '已报价，待付款' || state=='待报价'){
			windowCommOpen("priceManage");
			windowVisible("priceManage");
			showOrUpdateInfo(false);
			clearFromParams();
			loadEntity(entity);	
			$("input[name='price_w_isRW'][value='0']").prop("checked", "checked");
		}else{
			//可以查看，不可以修改
			windowCommOpen("priceManage");
			windowVisible("priceManage");
			showOrUpdateInfo(true);
			clearFromParams();
			loadEntity(entity);	
			if(entity.w_isPrice){
				$("input[name='price_w_isRW'][value='"+entity.w_isPrice+"']").prop("checked", "checked");
			}else{
				$("input[name='price_w_isRW'][value='0']").prop("checked", "checked");
			}
			
		}
		setRemark(entity);
	}else{
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 1){
			var state = entity[0].w_priceState.substring(entity[0].w_priceState.indexOf(">")+1,entity[0].w_priceState.indexOf("</label>")).trim();
			if(state == '已报价，待付款' || state=='待报价'){
				windowCommOpen("priceManage");
				windowVisible("priceManage");
				clearFromParams();
				loadEntity(entity[0]);	
				$("input[name='price_w_isRW'][value='0']").prop("checked", "checked");
				showOrUpdateInfo(false);
			}else{
				windowCommOpen("priceManage");
				windowVisible("priceManage");
				clearFromParams();
				loadEntity(entity[0]);	
				if(entity.w_isPrice){
					$("input[name='price_w_isRW'][value='"+entity.w_isPrice+"']").prop("checked", "checked");
				}else{
					$("input[name='price_w_isRW'][value='0']").prop("checked", "checked");
				}
				showOrUpdateInfo(true);
			}
			setRemark(entity[0]);
		}else if(entity.length == 0){
			$.messager.alert("提示信息","请先选择所要操作的行！","info");
		}else{
			$.messager.alert("提示信息","请先选择一行进行维修！","info");
		}
	}
}

function setRemark(entity){
	$("#acceptRemark").val(entity.acceptRemark);
	$("#sendRemark").val(entity.remark);
	$("#batchRemark").val(entity.repairNumberRemark);
	$("#repairRemark").val(entity.w_repairRemark);
	if(entity.testResult){
		var testResult = entity.testResult.substring(entity.testResult.indexOf(">")+1,entity.testResult.indexOf("</span>"));
		$("#testResult_info").val(testResult);
	}
}

function showOrUpdateInfo(type){
	if(type){
		//已付款数据，只能查看，
		$('.only_show').hide();
		$('input[name=price_w_isRW]').attr('disabled',true);
		$('#priceUpdate').hide();
	}else{
		$('.only_show').show();
		$('input[name=price_w_isRW]').attr('disabled',false);
		$('#priceUpdate').show();
	}
}

//关闭修改报价窗口
function windowPriceClose(){	
	$('#priceManage').window('close');
}

function clearFromParams(){
	//清除数据
	$("#repair_w_zzgzDesc").val("");
	$("#repair_w_zzgzId").val("");
	$("#repair_w_matDesc").val("");
	$("#repair_w_matId").val("");
	$("#repair_w_consumption").val("");
	$("#repair_w_dzlDesc").val("");
	$("#repair_w_dzlId").val("");
	$("#onePriceRemark").val("");
	mat_emusIds  = "";
}

function loadEntity(entity){
	$("#repair_imei").val(entity.imei);
	$("#repair_lockId").val(entity.lockId);
	$("#repair_bluetoothId").val(entity.bluetoothId);
	var model= entity.w_model.split('|');
	$("#repair_w_model").val(model[0]);
	$("#price_w_marketModel").val(entity.w_marketModel);
	$("#rfild").val(entity.rfild);
	$("#wfid").val(entity.id);
	$("#priceId").val(entity.w_priceId);
	
/*	----------维修组件---------*/
	$("#repair_w_matDesc").val(entity.w_matDesc);
	$("#repair_w_matId").val(entity.w_matId);
	
	$("#repair_w_dzlDesc").val(entity.w_dzlDesc);
	$("#repair_w_dzlId").val(entity.w_dzlId);
	
	/*----------维修报价---------*/
	$("#repair_w_wxbjDesc").val(entity.w_wxbjDesc);
	$("#repair_w_wxbjId").val(entity.w_wxbjId);
	
	$("#onePriceRemark").val(entity.w_onePriceRemark);
}

var repairnNmber;
//初始化批次查询数据
function queryBatchList(){
	var url=ctx+"/price/priceListByrepairNumber";
	var state = $("#search_State option:selected").val();
	var param="repairnNmber="+repairnNmber+"&state="+state;
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			commData(ret);
			if(!state ){
				stateInfo(repairnNmber);
			}	
			
			$.each(ret, function(i, item) {
				if (item.w_sumPrice != null && item.w_priceRepairMoney != null && item.w_sumPrice != item.w_priceRepairMoney) {
					item.w_sumPrice = "<label style='color:red;font-weight: bold;'>" + item.w_sumPrice + "</label>";
					item.w_priceRepairMoney = "<label style='color:red;font-weight: bold;'>" + item.w_priceRepairMoney + "</label>";
				} 
			});
			$("#batchInfo_DataGrid").datagrid('loadData',ret); 
			$("#infoCount").html("共<font color='green' style='font-weight: bold;'> [ "+(ret.length-1)+" ] </font> 条数据");
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
	 	$.messager.alert("操作提示","网络错误","info");
	});
}

//批次查看查看（显示当前选中批次的数据）
function showByRepairnNmber(){
	
	var selecteds=$('#DataGrid1').datagrid('getSelections');
	if(selecteds.length > 1){
		$.messager.alert("提示信息","只能选择一行！","info");
	}else if(selecteds.length < 1){
		$.messager.alert("提示信息","请先选择所要操作的行！","info");
	}else if(selecteds.length == 1){
		repairnNmber=selecteds[0].repairnNmber;
		$("#search_State").prop("selectedIndex",0);
		batchInfo_w_open();
		if(selecteds[0].w_priceStateFalg == -1 || selecteds[0].w_priceStateFalg == 1 || selecteds[0].w_priceStateFalg == 2){//已完成或已付款或待付款
			showTotalPayInfo(repairnNmber);
		}else{
			$("#show_price").hide();
		}
	}
}

//显示批次费用统计信息
function showTotalPayInfo(repairnNmber){
	$("#show_price").show();
	var url = ctx + "/totalpay/getByRepairNumber";
	var param="repairnNmber="+repairnNmber;
	asyncCallService(url, 'post', false, 'json', param, function(ret) {
		processSSOOrPrivilege(ret);
			if (ret.code == undefined) {
				var ret = ret.data;
				var show_repairPrice = ret.repairMoney;
				var show_hourFee = ret.hourFee;
				var show_pjCost = ret.batchPjCosts;
				var show_logCost = ret.logCost;
				var show_ratePrice = ret.ratePrice;
				var show_sumPrice = ret.totalMoney;
				
				show_repairPrice = (show_repairPrice == undefined) ? 0 : show_repairPrice;
				$("#show_repairPrice").html("维修费：￥" + show_repairPrice.toFixed(2));
				
				show_hourFee = (show_hourFee == undefined) ? 0 : show_hourFee;
				$("#show_hourFee").html("工时费：￥" + show_hourFee.toFixed(2));
				
				show_pjCost = (show_pjCost == undefined) ? 0 : show_pjCost;
				$("#show_pjCost").html("配件费：￥" + show_pjCost.toFixed(2));
				
				show_logCost = (show_logCost == undefined) ? 0: show_logCost;
				$("#show_logCost").html("运费:￥" + show_logCost.toFixed(2));
				
				show_ratePrice = (show_ratePrice == undefined)? 0: show_ratePrice;
				$("#show_ratePrice").html("税费：￥" + show_ratePrice.toFixed(2));
				
				show_sumPrice = (show_sumPrice == undefined) ? 0: show_sumPrice;
				$("#show_sumPrice").html("总费用：￥" + show_sumPrice.toFixed(2));
			} else {
				$.messager.alert("操作提示", ret.msg, "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
	});
}

//查看维修状态
function stateInfo(repairnNmber){
	var url = ctx + "/price/repairStateList";
	var param="price_repairnNmber="+repairnNmber;
	asyncCallService(url, 'post', false, 'json', param, function(ret) {
		processSSOOrPrivilege(ret);
		if (ret.code == undefined) {
			var ret = ret.data;
			$.each(ret, function(i, item) {
				// 报价状态
				if (item.state == '-1') {
					item.state = "已发货";
				} else if (item.state == '0') {
					item.state = "已受理 ";
				} else if (item.state == '1') {
					item.state = "已受理，待分拣";
				} else if (item.state == '2') {
					item.state = "已分拣，待维修";
				} else if (item.state == '3') {
					item.state = "待报价";
				} else if (item.state == '4') {
					item.state = "已付款，待维修";
				} else if (item.state == '5') {
					item.state = "已维修，待终检";
				} else if (item.state == '6') {
					item.state = "已终检，待维修";
				} else if (item.state == '7') {
					item.state = "已终检，待装箱";
				} else if (item.state == '8') {
					item.state = "放弃报价，待装箱";
				} else if (item.state == '9') {
					item.state = "已报价，待付款";
				} else if (item.state == '10') {
					item.state = "不报价，待维修";
				} else if (item.state == '11') {
					item.state = "放弃报价，待维修";
				} else if (item.state == '12') {
					item.state = "已维修，待报价";
				} else if (item.state == "13") {
					item.state = "待终检";
				} else if (item.state == "14") {
					item.state = "放弃维修";
				} else if (item.state == "15") {
					item.state = "测试中";
				}  else if (item.state == "16") {
					item.state = "已测试,待维修";
				}  else if (item.state == "17") {
					item.state = "已受理,已测试";
				}  else if (item.state == "18") {
					item.state = "已测试,待分拣";
				}  else if (item.state == "19") {
					item.state = "不报价,测试中";
				}
			});
			$("#repairState_DataGrid").datagrid('loadData', ret);
		} else {
			$.messager.alert("操作提示", ret.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}


//关闭维修状态窗口
function repairState_w_close() {
	$('#repairState_w').window('close');
}

//发送维修
function sendRepair(){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var marked_words="";
	var count = 0;//不符合条件数据个数
	var batchSend = false; //批量发送
	if (sendP.length > 0) {
		marked_words = "确定要发送<font color='red'>选中数据</font>到维修？";
	} else {
		//一键发送所有已付款数据到维修工站
		marked_words = "确定要发送所有<font color='red'>已付款，待维修</font>的数据到维修？";
		sendP = $('#DataGrid1').datagrid('getRows');
		batchSend = true;
	}
	
	$.messager.confirm("操作提示", marked_words, function(conf){
		if(conf){
			var ids = "";
			var errorMsg = "";
			for (var i = 0; i < sendP.length; i++) {
				if(sendP[i].w_priceStateFalg == 1){//已付款，待维修
					(i ==0)?ids = sendP[i].id:ids = sendP[i].id+","+ids;
				}else{
					count++;
				}
			}
			if(count > 0 && !batchSend){
				errorMsg = "选中数据中有不符合条件的数据！";
				ids = "";
			}
			if(ids!="" && ids!=null && ids!=undefined){
				var url=ctx+"/price/sendRepair";
				var param="ids="+ids;
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送维修成功！","info",function(){
							queryBatchList();
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

//放弃报价发送维修
//giveupPrice(true)：修改报价页面点击放弃报价单选按钮
//giveupPrice():报价主页面点击放弃报价发维修按钮
function giveupPrice(type){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var count = 0;//不符合条件数据个数
	if(!type){
		if (sendP.length > 0) {
			$.messager.confirm("操作提示", "确定放弃报价<font color='red'>选中数据</font>？", function(conf){
				if(conf){
					var ids = "";
					var errorMsg = "";
					for (var i = 0; i < sendP.length; i++) {
						if(sendP[i].w_priceStateFalg == 0 || sendP[i].w_priceStateFalg == 2 || sendP[i].w_priceStateFalg == 4 ){//待报价或未付款或放弃维修
							(i ==0)?ids = sendP[i].id:ids = sendP[i].id+","+ids;
						}else{
							count++;
						}
					}
					if(count > 0){
						errorMsg = "选中数据中有不符合条件的数据！";
						ids = "";
					}
					if(ids!="" && ids!=null && ids!=undefined){
						var url=ctx+"/price/giveupPriceSendRepair";
						var param="ids="+ids + 
							"&isPrice=1" ;											
						asyncCallService(url, 'post', false,'json', param, function(returnData){
							processSSOOrPrivilege(returnData);
							if(returnData.code=='0'){
								$.messager.alert("操作提示","发送维修成功！","info",function(){
									queryBatchList();
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
		} else {
			$.messager.alert("提示信息","请先选择所要操作的行！","info");
		}
	}else if(type){
		$.messager.confirm("操作提示", "确定放弃报价<font color='red'>选中数据</font>？", function(conf){
			if(conf){
				var url=ctx+"/price/giveupPriceSendRepair";
				var isPrice = $("input[name='price_w_isRW']:checked ").val();		
				var param="ids="+$("#wfid").val()+
						   "&onepriceMark="+$("#onePriceRemark").val()+
						   "&isPrice="+isPrice;
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送维修成功！","info",function(){
							windowCommClose("priceManage");
							queryBatchList();
							queryListPage(currentPageNum);
						});
					}else{
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}, function(){
					$.messager.alert("操作提示","网络错误","info");
				});
			}
		});
	}
}

//打开批次查看窗口
function batchInfo_w_open() {
	windowVisible("batchInfo_w");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#batchInfo_w').window('open');
	batchInfoInit();
}
//关闭批次查看窗口
function batchInfo_w_close() {
	$("#price_imei").val("");//清空查询框price_imei
	$("#price_lockId").val("");//清空查询框price_lockId
	$('#batchInfo_w').window('close');
}
/*=========================================批次查看 end==============================================*/

/*--=======================================批次报价 start============================================*/
//获得维修报价费用
function bathPrice(type){
	openType = $("#type_batch").val();
	if(type && type == "1"){//可报价批次里的批次报价按钮
		var selecteds = $('#batchInfo_DataGrid').datagrid('getSelections');
	}else{
		var selecteds = $('#DataGrid1').datagrid('getSelections');
	}
	if(selecteds.length > 1){
		$.messager.alert("提示信息","只能选择一行！","info");
	}else if(selecteds.length < 1){
		$.messager.alert("提示信息","请先选择所要操作的行！","info");
	}else if(selecteds.length == 1){
		if(selecteds[0].w_priceStateFalg==0 || selecteds[0].w_priceStateFalg == 2){
			$.messager.confirm("操作提示","确定报价吗？",function(conf){
				if(conf){				
					$("#isLine").show();
					price = false;
					var wfIds = "";
					var repairnNmber = selecteds[0].repairnNmber;
					var cusName=selecteds[0].w_cusName;
					$("#bathPrice_repairnNmber").val(repairnNmber);
					$("#bathPrice_cusName").val(cusName);
					var pay_delivery = selecteds[0].payDelivery;
					var delivery = pay_delivery.substring(pay_delivery.indexOf('>')+1,pay_delivery.length-8);
					if(delivery == '是'){
						$('#bathPrice_logCost').prop('disabled',true);
					}else{
						$('#bathPrice_logCost').prop('disabled',false);
					}		
					$('#priceRemark').val(selecteds[0].w_price_Remark);
					if(selecteds[0].w_customerReceipt){
						$("input:radio[name=customer_Receipt][value='"+selecteds[0].w_customerReceipt+"']").prop("checked",true);						
					}else{
						$("input:radio[name=customer_Receipt][value=Y]").prop("checked",true);
					}
					var url=ctx+"/price/bathSumRepairPrice";
					var param="price_repairnNmber="+repairnNmber;
					asyncCallService(url, 'post', false,'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						if(returnData.code==undefined){
							$("#bathSumRepairPrice").val(returnData.data.sumPrice);
							$("#bathPrice_logCost").val(returnData.data.logCost);
							$("#bathPrice_pjCost").val(returnData.data.batchPjCosts);
							$("#bathPrice_prolongCost").val(returnData.data.prolongCost);
							$("#bathPrice_ratePrice").val(returnData.data.ratePrice);
							var receipt = returnData.data.receipt;							
							if(receipt == '0'){
								$("input:radio[name=bathPrice_receipt][value='"+receipt+"']").prop("checked",true);
								$("#rate").val(returnData.data.rate);
								$("#rate").prop('disabled',false);
							}else{
								$("#rate").val("");
								$("input:radio[name=bathPrice_receipt][value=1]").prop("checked",true);
							}							
							bathPrice_w_open();
						}else {
							$.messager.alert("操作提示",returnData.msg,"info");
						}
					}, function(){
						$.messager.alert("操作提示","网络错误","info");
					});					
				}
			});
		}else{
			$.messager.alert("操作提示","选中数据不符合报价条件","info");
		}
	}
}

function showIsPay(){
	if(!price){
		$.messager.confirm("操作提示","是否人工确认客户付款情况？",function(conf){
			if(conf){
				price = true;
				$("#isAutoPay").val(1);//人工确认
				$(".price_isHidden").show();
				$("#isLine").hide();
			}
		});
	}
}

//时分秒十以内前面加0
function addZero(v) {
	if (v < 10) {
		return '0' + v;
	} else {
		return v;
	}
}

//获得当前时间
function initTime() {
	var date = new Date();
	return date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
}

//清缓存
function bathPriceInit(){
	$("#isAutoPay").val(0);//人工确认
	$("#bathPrice_remAccount").val("人工付款");
	var sumPrice = $("#bathSumRepairPrice").val()*1 + $("#bathPrice_logCost").val()*1 + $("#bathPrice_pjCost").val()*1 + $("#bathPrice_prolongCost").val()*1;	
	var ratePrice = $("#rate").val()/100 * sumPrice;
	$("#bathPrice_ratePrice").val(ratePrice.toFixed(2));
	$("#bathPrice_totalMoney").val((sumPrice + ratePrice).toFixed(2));	
	$("#bathPrice_payReason").val("");
	$("input:radio[name=bathPrice_isPay][value=1]").prop("checked",true);
	$("#bathPrice_payTime").val(initTime());
}

//保存及修改批次报价记录
function saveBathPrice(){
	if(!$('#priceForm').form('validate')){
		return false;
	}
	var receiptVal = $("input:radio[name=bathPrice_receipt]:checked").val();
	var rateVal = $("#rate").val();
	if(receiptVal == 0 && !rateVal){
		$.messager.alert("操作提示","开发票请填写税率","info");
		return;
	}else if(receiptVal == 0 && rateVal <= 0){
		$.messager.alert("操作提示","税率必须大于零","info");
		return;
	}
	$.messager.confirm("操作提示","确定保存吗？",function(conf){
		if(conf){
			var repairnNmber=$("#bathPrice_repairnNmber").val();
			var url=ctx+"/totalpay/updateOrSaveInfo";
			var payDate = $("#bathPrice_payTime").val()
			if (!payDate) {
				$("#bathPrice_payTime").val(initTime());
			}
			payDate = $("#bathPrice_payTime").val();
			var param={
					"price_repairnNmber" : repairnNmber,
					"repairNumber" : repairnNmber,
					"remAccount" : $("#bathPrice_remAccount").val(),
					"payReason" : $("#bathPrice_payReason").val(),
					"logCost" : $("#bathPrice_logCost").val(),
					"prolongCost" : $("#bathPrice_prolongCost").val(),
					"repairMoney" : $("#bathSumRepairPrice").val(),
					"totalMoney" : $("#bathPrice_totalMoney").val(),
					"payDate" : payDate,
					"isPay" : $("input:radio[name=bathPrice_isPay]:checked").val(),
					"customerReceipt" : $("input:radio[name=customer_Receipt]:checked").val(),
					"receipt" : receiptVal,
					"rate" : rateVal,
					"priceRemark" : $("#priceRemark").val(),
					"isAutoPay" : $("#isAutoPay").val(),
					"batchPjCosts" : $("#bathPrice_pjCost").val(),
					"ratePrice" : $("#bathPrice_ratePrice").val()
			}
			console.log(param);
			asyncCallService(url, 'post', false,'json', param, function(returnData)	{
				var ret=returnData.data;
				if(returnData.code=='0'){
					$.messager.alert("操作提示",returnData.msg,"info",function(){
						bathPrice_w_close();
						queryBatchList();
						queryListPage(currentPageNum);
					});
				}else{
				 	$.messager.alert("操作提示",returnData.msg,"info");
				}
		 	}, function(){
		 		$.messager.alert("操作提示","网络错误","info");
		 	});
			queryBatchList();
		}
	});
}

//修改报价信息，添加组件
function priceUpdate(){
	var isPrice = $("input[name='price_w_isRW']:checked ").val(); // 报价页面修改报价放弃报价单选按钮
	if(isPrice == '1'){
		$.messager.confirm('系统提示','确认放弃报价？', function(conf){
			if(conf){
				giveupPrice(true);//修改页面单选按钮
				}else{
					return;
				}
			});
	}else{
		sureUpdate(isPrice);
	}
}

function sureUpdate(isPrice){
	var url=ctx+"/repair/updateInfo";
	var param="dzlDesc="+$("#repair_w_dzlId").val()+
	"&matDesc="+$("#repair_w_matId").val()+
	"&wxbjDesc="+$("#repair_w_wxbjId").val()+
	"&repair_Imei="+$("#repair_imei").val()+
	"&rfild="+$("#rfild").val()+
	"&isPrice="+isPrice+
	"&priceId="+$("#priceId").val()+
	"&repair_onePriceRemark="+$("#onePriceRemark").val();
	asyncCallService(url, 'post', false,'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if(returnData.code=='0'){
			$.messager.alert("操作提示",returnData.msg,"info",function(){
				windowCommClose("priceManage");
				queryBatchList();
				queryListPage(currentPageNum);
			});
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
			queryBatchList();
			queryListPage(currentPageNum);
		}
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 导出
 */
function exportInfo() {
	var param="price_repairnNmber="+$.trim($("#repairnNmber").val())
				  +"&price_imei="+$.trim($("#price_imei").val())
				  +"&price_lockId="+$.trim($("#price_lockId").val())
				  +"&price_cusName="+$.trim($("#price_cusName").val())
				  +"&priceState="+$("#priceState").combobox('getValue')
				  +"&startTime="+$("#startTime").val() 
				  +"&endTime="+$("#endTime").val() 
				  +"&treeDate="+$("#tree-Date").val();
	window.open(ctx + "/price/ExportDatas?" + param);
}

//物流费数据列表
function ListLogCostInit(){
	openType = $("#type_logCost").val();
	$("#sogcost_table").datagrid({
     singleSelect:false,
     onDblClickRow:function(rowIndex,row){
			showLogCostInfo(rowIndex,row);
		}
	});
	
	openLostW();	
	queryListPageTwo();
	
	$('#search_logcost').click(function(){
		queryListPageTwo();
	});
}

function openLostW(){
	windowVisible("logcost_w");
	$('#logcost_w').window('open');	
}

function clearLogcostParam(){
	$('#logCost_cusName').val("");
	$('#logCost_repairNumber').val("");
	$('#logCost').val("");
	$('#priceMark').val("");
	$("#logCost_pjCost").val("");
	$("#rate_logcost").val("");
	$("#rate_logcost").attr("disabled",true);
	$("input:radio[name=bathPrice_receipt_logcost][value=1]").prop("checked",true);
	$("input:radio[name=customer_Receipt_logcost][value=Y]").prop("checked",true);
	$("input:radio[name=now_pay][value=0]").prop("checked",true);	
}

function showLogCostInfo(rowIndex,row){	
	clearLogcostParam();
	if(row){
		if(row.payedLogCost.indexOf('未支付') >-1){
			openLogcost();
			$('#logCost_cusName').val(row.cusName);
			$('#logCost_repairNumber').val(row.repairnNmber);	
			$('#logCost').val(row.w_logCost);
			getPricePjCosts();
			$('#logCost_ratePrice').val(row.w_ratePrice);
			$("#logCost_sumMoney").val(row.w_totalMoney);
			$("#priceMark").val(row.w_priceRemark);
			if(row.w_remAccount == 'personPay'){
				$("input:radio[name=now_pay][value=1]").prop("checked",true);
			}else{
				$("input:radio[name=now_pay][value=0]").prop("checked",true);
			}
		
			var receipt = row.w_receipt;							
			if(receipt == '0'){
				$("input:radio[name=bathPrice_receipt_logcost][value='"+receipt+"']").prop("checked",true);
				$("#rate_logcost").val(row.w_rate);
				$("#rate_logcost").prop('disabled',false);
			}else{
				$("#rate_logcost").val("");
				$("input:radio[name=bathPrice_receipt_logcost][value=1]").prop("checked",true);
			}		
		}else{
			$.messager.alert("操作提示", "物流费已支付不允许再修改", "warning");
		}
		
	}
}

//保存物流费用
function saveLogcost(){
	var isValid = $('#logcost_form').form('validate');
	if (isValid) {		
		var url=ctx+"/totalpay/saveLogcost";
		var priceMark = $("#priceMark").val();
		var repairnNmber=$("#logCost_repairNumber").val();
		var logCost = $("#logCost").val();	
		var customerReceipt=$("input:radio[name=customer_Receipt_logcost]:checked").val();
		var isAutoPay = $("input:radio[name=now_pay]:checked").val();
		var receipt = $("input:radio[name=bathPrice_receipt_logcost]:checked").val();
		var rate = $("#rate_logcost").val();
		var totalMoney = $("#logCost_sumMoney").val();
		
		var param={
				"repairNumber":repairnNmber,
				"priceRemark":priceMark,
				"customerReceipt":customerReceipt,
				"isAutoPay":isAutoPay,
				"logCost":logCost,
				"totalMoney":totalMoney,
				"receipt":receipt,
				"rate":rate,
				"batchPjCosts":$("#logCost_pjCost").val(),
				"ratePrice":$("#logCost_ratePrice").val()
		}				
			
		asyncCallService(url, 'post', false,'json', param, function(returnData)	{
			var ret=returnData.data;
			if(returnData.code=='0'){
				$.messager.alert("操作提示",returnData.msg,"info",function(){
					$('#setLogCost').window('close');
					queryListPageTwo();
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
 * 获取需要填写物流费的数据
 */
function queryListPageTwo(pageNumber){	
	currentPageNumTwo=pageNumber;
	if(currentPageNumTwo=="" || currentPageNumTwo==null){
		currentPageNumTwo=1;
	} 
	var url=ctx+"/price/queryListLogCost";
	var logcostPay = $("#logcost_pay_search option:selected").val();
	var w_cusName = $("#logcost_cusName_search").val().trim();
	var selParams="repairnNmber="+$("#logcost_repairNumber_search").val().trim()+
				 "&payedLogCost="+logcostPay+
				 "&w_cusName="+w_cusName;	
	var param = addPageParam('sogcost_table', currentPageNumTwo, selParams)
    $("#sogcost_table").datagrid('loading');
	asyncCallService(url, 'post', false,'json',param, function(returnData){
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$.each(returnData.data,function(index,item){
				if(item.payedLogCost=='0'){
					item.payedLogCost="<label style='color:green;font-weight: bold;'>已支付</label>";					
				}else if(item.payedLogCost=='1'){
					item.payedLogCost="<label style='color:red;font-weight: bold;'>未支付</label>";
				} else {
					item.payedLogCost="";
				}
				
				if(item.w_customerReceipt){
					if(item.w_customerReceipt == 'Y'){
						item.w_customerReceipt = "<label style='color:#0066FF;font-weight: bold;'>顺付</label>";	
					}else if(item.w_customerReceipt == 'N'){
						item.w_customerReceipt = "<label style='font-weight: bold;'>到付</label>";
					}
				}
				
				if(item.w_remAccount){
					if(item.w_remAccount == 'personPay'){
						item.w_remAccount = "<label style='font-weight: bold;'>人工付款</label>";
					}else if(item.w_remAccount == 'aliPay'){
						item.w_remAccount = "<label style='color:rgb(51, 153, 255);font-weight: bold;'>支付宝付款</label>";
					}else if(item.w_remAccount == 'weChatPay'){
						item.w_remAccount = "<label style='color:green;font-weight: bold;'>微信付款</label>";
					}else{
						item.w_remAccount = "";
					}
				}
			});
			$("#sogcost_table").datagrid('loadData', returnData.data);
			getPageSizeTwo("sogcost_table");
			resetCurrentPageShowT("sogcost_table",currentPageNumTwo);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#sogcost_table").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

function logcost_w_close(){
	$('#logcost_w').window('close');
	$("#logcost_repairNumber_search").val("");
	$("#logcost_cusName_search").val("");
	$("#logcost_pay_search").prop("selectedIndex",1).change();
	queryListPage(currentPageNum);
}

function setLogCostClose(){
	$('#setLogCost').window('close');
}

function openLogcost(){
	windowVisible("setLogCost");
	$('#setLogCost').window('open');
}

// 查询可以报价的批次号
function ListAllPriceRepairNub(){
	
	$("#repairNub_table").datagrid({
	     singleSelect:false,
	});
	
	$("#search_repairNumber").click(function(){
		repairnNumberList();
	});
	
	windowVisible("repairNub_w");
	$('#repairNub_w').window('open');
	repairnNumberList();
}

//可报价批次列表
function repairnNumberList(){
	getTreeNode();
	var cusName = $("#cusName_search").val().trim();
	var url=ctx+"/price/priceList";
    var param="allPrice=allPrice" + "&price_cusName=" + cusName;
	  $("#repairNub_table").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			var ret=returnData.data;
			commData(ret);
			$(ret).each(function(index,item){
				if(item.repairnNmber){
					if(item.w_isAllPay == 1){ //已报过价的颜色变绿
						item.repairnNmber = '<a href="javascript:;" class="easyui-linkbutton" onclick="selDataByRepairNumber(this, true)"><label style="color:green;">'+item.repairnNmber+'</label></a>';
					}else{
						item.repairnNmber = '<a href="javascript:;" class="easyui-linkbutton" onclick="selDataByRepairNumber(this, false)">'+item.repairnNmber+'</a>';
					}
				}
			});
			$("#repairNub_table").datagrid('loadData',ret);  
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#repairNub_table").datagrid('loaded');	
		getPageSize();
		resetCurrentPageShow(currentPageNum);
	}, function(){
		$("#repairNub_table").datagrid('loaded');	
	 	$.messager.alert("操作提示","网络错误","info");
	});
}

//点击可报价批次号
function selDataByRepairNumber(val, type){
	repairnNmber = val.innerText;
	if(repairnNmber){
		batchInfo_w_open();
		if(type){
			showTotalPayInfo(repairnNmber);
		}else{
			$("#show_price").hide();
		}
	}else{
		$.messager.alert("提示信息","未能匹配到批次号","info");
	}
}

function repairNub_w_close(){
	$('#repairNub_w').window('close');
	$('#cusName_search').val("");	
	queryListPage(currentPageNum);
}

//未报价数据返回维修工站
function sendDataToRepair(){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var count = 0;//符合条件数据个数
	
	if (sendP.length > 0) {
		$.messager.confirm("操作提示", "确定要发送<font color='red'>选中数据</font>到维修？", function(conf){
			if(conf){
				var ids = "";
				for (var i = 0; i < sendP.length; i++) {
					if(sendP[i].w_priceStateFalg == 0){//待报价
						(i ==0)?ids = sendP[i].id:ids = sendP[i].id+","+ids;
					}else{
						count++;
					}
				}
				if(count > 0){
					errorMsg = "选中数据中有不符合条件的数据！";
					ids = "";
				}

				if(ids){
					var url=ctx+"/price/sendDataToRepair";
					var param="ids="+ids;
					asyncCallService(url, 'post', false,'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						if(returnData.code=='0'){
							$.messager.alert("操作提示","匹配数据返回维修成功！","info",function(){
								queryBatchList();
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
	} else {
		$.messager.alert("提示信息","请先选择所要操作的行！","info");	
	}
}

//待付款，返回维修
function sendNoPriceToRepair(){
	var sendP = $('#DataGrid1').datagrid('getSelections');
	if(sendP.length > 0){
		if(sendP.length == 1){
			if(sendP[0].w_priceStateFalg == 2){
				$.messager.confirm("操作提示", "确定要操作选中的<font color='red'>待付款</font>数据到维修工站？", function(conf){
					if(conf){
						var id = sendP[0].id;
						var repairNumber = sendP[0].repairnNmber;
						var url=ctx+"/price/sendNoPriceToRepair";
					var param={
							"id" : id,
							"repairNumber" :repairNumber
					};
					asyncCallService(url, 'post', false, 'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						$.messager.alert("操作提示", returnData.msg ,"info",function(){
							queryBatchList();
							queryListPage(currentPageNum);
						});
					}, function(){
						$.messager.alert("操作提示","网络错误","info");
					});
					}
				});
			}else{
				$.messager.alert("操作提示", "数据不符合要求", "warning");
			}
		}else{
			$.messager.alert("提示信息","只能选择一行操作！","info");
		}
	}else{
		$.messager.alert("提示信息","请先选择所要操作的行！","info");	
	}
}

//根据税率计算维修费用
function getRatePrice(){
	if($("input:radio[name=bathPrice_receipt]:checked").val()==0 && $('#rate').validatebox("isValid")){
		$("#bathPrice_totalMoney").val((($("#rate").val()/100+1)*($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1)).toFixed(2));
		$("#bathPrice_ratePrice").val(($("#bathPrice_totalMoney").val()*1-$("#bathPrice_logCost").val()*1-$("#bathSumRepairPrice").val()*1-$("#bathPrice_pjCost").val()*1-$("#bathPrice_prolongCost").val()*1).toFixed(2));
	}else{
		$("#bathPrice_totalMoney").val(($("#bathPrice_logCost").val()*1+$("#bathSumRepairPrice").val()*1+$("#bathPrice_pjCost").val()*1+$("#bathPrice_prolongCost").val()*1).toFixed(2));
		$("#bathPrice_ratePrice").val(0);
	}
}

function getLogcostRatePrice(){
	var sumPrice = $("#logCost").val()*1 + $("#logCost_pjCost").val()*1;
	if($("input:radio[name=bathPrice_receipt_logcost]:checked").val()==0){		
		var totalMoney = ($("#rate_logcost").val()/100+1) * sumPrice;
		$("#logCost_sumMoney").val(totalMoney.toFixed(2));
		$("#logCost_ratePrice").val((totalMoney-$("#logCost").val()*1- $("#logCost_pjCost").val()*1).toFixed(2));
	}else{		
		$("#logCost_sumMoney").val(sumPrice.toFixed(2));
		$("#logCost_ratePrice").val(0);
	}
}

// TODO 延保
var oneYearPrice;
var twoYearPrice;
var threeYearPrice;

function prolong() {
	var entity = $('#DataGrid1').datagrid('getSelections');
	if(entity.length < 1){
		$.messager.alert("提示信息","请先选择所要操作的行！","info");
		return;
	} 
	
	var repairnNmber = entity[0].repairnNmber;
	for(var i=0; i<entity.length; i++){
		var state = entity[i].w_priceState.substring(entity[i].w_priceState.indexOf(">")+1,entity[i].w_priceState.indexOf("</label>")).trim();
		if(state != '待报价'){
			$.messager.alert("提示信息","请选择待报价的设备进行延保！","info");
			return;
		}
		if(entity[i].repairnNmber != repairnNmber){
			$.messager.alert("提示信息","请选择同一批次的设备进行延保！","info");
			return;
		}
	}
	$.messager.confirm("操作提示", "确定延保<font color='red'>选中设备</font>吗？", function(conf){
		if(conf){
			prolong_w_open();
			var imeis = "";
			for(var i=0;i<entity.length;i++){
				imeis = imeis + entity[i].imei +"\r";
			}
			imeis = imeis.substring(0, imeis.length-1);
			$("#prolong_repair_imei").val(imeis);
			$("#prolong_repair_number").val(repairnNmber);
			$("#prolong_devices").val(entity.length);
			
			var price;
			var year = $("input:radio[name=prolong_year]:checked").val();
			if (year == "1"){
				price = oneYearPrice;
			} else if (year == "2"){
				price = twoYearPrice;
			} else if (year == "3"){
				price = threeYearPrice;
			}
			$("#prolong_cost").val(price);
			$("#prolong_total_price").val(entity.length * price);
		}
	});
}

function saveProlong(){
	$("#icon-ok").linkbutton('disable');
	var entity = $('#DataGrid1').datagrid('getSelections');
	console.log(entity, "imei");
	var imeiStr = "";
	for (var i=0; i<entity.length;i++){
		var imei = entity[i].imei;
		imeiStr = imeiStr + "," +imei;
	}
	imeiStr = imeiStr.substring(1, imeiStr.length);
	
	var url = ctx + "/price/saveProlongInfo";
	var param = {
			"imeiStr" : imeiStr,
			"repairNumber" : entity[0].repairnNmber,
			"prolongYear" :$("input:radio[name=prolong_year]:checked").val(),
			"prolongCost" :$("#prolong_cost").val()
	}
	console.log(param, "param");
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == 0) {
			prolong_w_close();
			$.messager.alert("操作提示", "设备延保成功", "info");

		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
	$("#icon-ok").linkbutton('enable');
}

//延保窗口
function prolong_w_open() {
	windowVisible("prolong_w");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#prolong_w').window('open');
}
function prolong_w_close() {
	$('#prolong_w').window('close');
}

//设置延保价格窗口
function prolong_prcie_w_open() {
	windowVisible("prolong_prcie_w");
	$('#prolong_prcie_w').window('open');
	queryProlongCost();
}
function prolong_prcie_w_close() {
	$('#prolong_prcie_w').window('close');
}
// 查询延保价格
function queryProlongCost(){
	var url = ctx + "/price/queryProlongCost";
	asyncCallService(url, 'post', false, 'json', null, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			var ret = returnData.data;
			$("#oneYearPrice").val(ret.oneYearPrice);
			$("#twoYearPrice").val(ret.twoYearPrice);
			$("#threeYearPrice").val(ret.threeYearPrice);
			oneYearPrice = ret.oneYearPrice;
			twoYearPrice = ret.twoYearPrice;
			threeYearPrice = ret.threeYearPrice;
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 保存延保价格
function saveProlongCost(){
	$.messager.confirm("操作提示", "确认要保存吗？", function(conf) {
		if (conf) {
			var url = ctx + "/price/updateProlongCost";
			var param={
					"oneYearPrice" : $("#oneYearPrice").val(),
					"twoYearPrice" : $("#twoYearPrice").val(),
					"threeYearPrice" :$("#threeYearPrice").val()
			};
			asyncCallService(url, 'post', false, 'json', param, function(returnData) {
				processSSOOrPrivilege(returnData);
				if (returnData.code == 0) {
					prolong_prcie_w_close();
					
					oneYearPrice = $("#oneYearPrice").val();
					twoYearPrice = $("#twoYearPrice").val();
					threeYearPrice = $("#threeYearPrice").val();
					
					$('input:radio[name="prolong_year"]:checked').click();
					
					$.messager.alert("操作提示", "保存成功", "info");
				} else {
					$.messager.alert("操作提示", returnData.msg, "info");
				}
			}, function() {
				$.messager.alert("操作提示", "网络错误", "info");
			});
		}
	});
}

//打开批次报价（同一批次）窗口
function bathPrice_w_open() {
	windowVisible("bathPrice_w");
	$(".price_isHidden").hide();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#bathPrice_w').window('open');
	bathPriceInit();
}
//关闭批次报价窗口
function bathPrice_w_close() {
	$("#isLine").show();
	$("#rate").attr("disabled","disabled");//费率默认不允许填写
	$("#priceRemark").val("");
	$('#bathPrice_w').window('close');
}

//打开批次配件料管理窗口
function showPj(){
	windowVisible("batchPj_w");
	$('#batchPj_w').window('open');
	$("#s_proNO_Pj").val("");
	$("#s_proName_Pj").val("");
	queryListPj();
}

function queryListPj(){
	var url = ctx + "/pricePj/getList";
	var param = "proNO="+$.trim($("#s_proNO_Pj").val())+ "&proName="+$.trim($("#s_proName_Pj").val());
	if(openType && openType == "type_batch"){
		param += "&repairNumber=" + $.trim($("#bathPrice_repairnNmber").val());
	}else if(openType && openType == "type_logCost"){
		param += "&repairNumber=" + $.trim($("#logCost_repairNumber").val());
	}
	$("#batchPj_DataGrid").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			$("#batchPj_DataGrid").datagrid('loadData', returnData.data);
		} else {
			$.messager.alert("操作提示", returnData.msg, "info");
		}
		$("#batchPj_DataGrid").datagrid('loaded');
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

//新增配件料（多选）
function batchPj_add(){
	windowVisible("batch_choosepj_w");
	$('#batch_choosepj_w').window('open');
	$("#proNO_Pj").val("");
	$("#proName_Pj").val("");
	queryListPagePj();
}

//查询选中要修改的配件料
function batchPj_edit(){
	var rows = $("#batchPj_DataGrid").datagrid('getSelections');
	if(rows.length ==1){
		var url = ctx + "/pricePj/getInfo";
		var param = "id=" + rows[0].id;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == 0) {
				var ret = returnData.data;
				$("#editPj_id").val(ret.id);
				$("#editPj_repairNumber").val(ret.repairNumber);
				$("#editPj_marketModel").val(ret.marketModel);
				$("#editPj_proNO").val(ret.proNO);
				$("#editPj_proName").val(ret.proName);
				$("#editPj_retailPrice").val(ret.retailPrice);
				$("#editPj_pjNumber").val(ret.pjNumber);
				openEditPj_w();
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
			$("#batchPj_DataGrid").datagrid('loaded');
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}else if(rows.length>1){
		
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}
				
//修改配件料（数量）
function openEditPj_w(){
	windowVisible("editPj_w");
	$(".singleSelect_show").show();
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#editPj_w').window('open');
}

//保存修改
function saveEditPj(){
	var isValid = $('#editPj_form').form('validate');
	if (isValid) {
		var url = ctx + "/pricePj/updateInfo";
		var param = "id=" + $("#editPj_id").val() + "&pjNumber=" + $.trim($("#editPj_pjNumber").val()) + "&retailPrice=" + $.trim($("#editPj_retailPrice").val());
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == 0) {
				$.messager.alert("操作提示", returnData.msg, "info",function(){
					editPj_Close();
					queryListPj();
				});
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}

//删除（可多选）
function batchPj_del(){
	var rows = $("#batchPj_DataGrid").datagrid('getSelections');
	if(rows.length > 0){
		$.messager.confirm("操作提示", "确定删除已选中的数据吗？", function(conf) {
			if (conf) {
				var ids = "";
				for (var i = 0; i < rows.length; i++) {
					(i == 0) ? ids = rows[i].id : ids = rows[i].id + "," + ids;
				}
				
				//先判断是否有重复数据，再插入
				var url = ctx + "/pricePj/deleteInfo";
				var param = "ids=" + ids;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == 0) { 
						$.messager.alert("操作提示",returnData.msg, "info",function(){
							queryListPj();
						});
					} 
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info");
				});
			}
		});
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

//关闭批次配件料管理 窗口
function closeBatchPj_w(){
	getPricePjCosts();
	windowCommClose('batchPj_w');
}

//查询所有配件料
function queryListPagePj(pageNumber){
	currentPageNum_Pj=pageNumber;
	if(currentPageNum_Pj=="" || currentPageNum_Pj==null){
		currentPageNum_Pj=1;
	}
	var url=ctx+"/pjlmanage/pjlmanageList";
    var param="proNO=" + $.trim($("#proNO_Pj").val()) +"&proName="+ $.trim($("#proName_Pj").val())
	 +"&currentpage="+currentPageNum_Pj; 
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

var groupPjDetailsIds;
function saveBatch_chooseGroupPj(){
	var row = $('#chooseGroupPj_DataGrid').datagrid('getSelected');
	if (row) {
		var param = "groupId=" + row.groupPjId;
		var url = ctx + "/groupPjDetails/groupPjDetailsList";
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == undefined) {
				var ret = returnData.data;
				var pjlId =[];
				$.each(ret,function(i,item){
					if(item && item.pjlId){
						pjlId.push(item.pjlId);
					}
				});
				var ids = "";
				if(pjlId.length > 0){
					for (var i = 0; i < pjlId.length; i++) {
						(i == 0) ? ids = rows[i].pjlId : ids = rows[i].pjlId + "," + ids;
					}
				}
				groupPjDetailsIds = ids;
			} else {
				$.messager.alert("操作提示", returnData.msg, "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}

//保存批次增加的配件料
function saveBatch_choosepj(chooseType){
	var rows = "";
	if(chooseType == 'batchPrice'){
		rows = $("#choosepj_DataGrid").datagrid('getSelections');
	}else if(chooseType == 'groupPj'){
		rows = $("#chooseGroupPj_DataGrid").datagrid('getSelections');
	}
	if(rows.length > 0){
		$.messager.confirm("操作提示", "确定保存已选择的数据吗？", function(conf) {
			if (conf) {
				var ids = "";
				if(chooseType == 'batchPrice'){
					for (var i = 0; i < rows.length; i++) {
						(i == 0) ? ids = rows[i].pjlId : ids = rows[i].pjlId + "," + ids;
					}
					savePricepj(ids);
				}else if(chooseType == 'groupPj'){
					var param = "groupId=" + rows[0].groupPjId;
					var url = ctx + "/groupPjDetails/groupPjDetailsList";
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData.code == undefined) {
							var ret = returnData.data;
							var pjlId =[];
							$.each(ret,function(i,item){
								if(item && item.pjlId){
									pjlId.push(item.pjlId);
								}
							});
							if(pjlId.length > 0){
								for (var i = 0; i < pjlId.length; i++) {
									(i == 0) ? ids = pjlId[i] : ids = pjlId[i] + "," + ids;
								}
							}
							savePricepj(ids);
						} else {
							$.messager.alert("操作提示", returnData.msg, "info");
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info");
					});
				}
			}
		});
	}else{
		$.messager.alert("操作提示", "未选中数据", "info");
	}
}

//判断重复并保存选中的配件料
function savePricepj(ids){
	if(ids){
		//先判断是否有重复数据，再插入
		var url = ctx + "/pricePj/checkRepeat";
		var param = "ids=" + ids;
		var repairNumber = "";
		if(openType && openType == "type_batch"){
			repairNumber= $.trim($("#bathPrice_repairnNmber").val());
		}else if(openType && openType == "type_logCost"){
			repairNumber= + $.trim($("#logCost_repairNumber").val());
		}
		param += "&repairNumber=" + repairNumber;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			//data(重复数据的数量)
			if (returnData.code == undefined && returnData.data ==0) {
				var url1=ctx+"/pricePj/addInfo";
				var cusName = "";//客户名称
				var param1="ids=" + ids + "&repairNumber=" + repairNumber + "&pjNumber=" + 1 + "&offer=" + $.trim($("#curLoginer").val());
				if(openType && openType == "type_batch"){
					param1 += "&cusName=" + $.trim($("#bathPrice_cusName").val());
				}else if(openType && openType == "type_logCost"){
					param1 += "&cusName=" + $.trim($("#logCost_cusName").val());
				}
				asyncCallService(url1, 'post', false,'json', param1, function(ret){
					processSSOOrPrivilege(ret);
				}, function(){
					$.messager.alert("操作提示","网络错误","info");
				});
				$.messager.alert("操作提示", "选择成功！", "info",function(){
					$('#batch_choosepj_w').window('close');
					queryListPj();
				});
			} else {
				$.messager.alert("操作提示", "配件料有已选择的，请确认", "info");
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});
	}
}
//批次配件费
function getPricePjCosts(){
	var url = ctx + "/pricePj/getPricePjCosts";
	var param = ""
	if(openType && openType == "type_batch"){
		param = "&repairNumber=" + $.trim($("#bathPrice_repairnNmber").val());
	}else if(openType && openType == "type_logCost"){
		param = "&repairNumber=" + $.trim($("#logCost_repairNumber").val());
	}
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined){
			if(openType && openType == "type_batch"){
				$("#bathPrice_pjCost").val(returnData.data);
				getRatePrice();
			}else if(openType && openType == "type_logCost"){
				$("#logCost_pjCost").val(returnData.data);
				getLogcostRatePrice();
			}
		}
	});
}

function editPj_Close(){
	windowCommClose('editPj_w');
}

//组合配给料
function batchGroupPj_add(){
	windowVisible("batch_chooseGroupPj_w");
	$('#batch_chooseGroupPj_w').window('open');
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
/*--=======================================批次报价end ================================================*/