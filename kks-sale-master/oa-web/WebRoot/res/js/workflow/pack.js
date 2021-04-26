$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
var nimeiAginSubmit = false;
$(function(){    
	datagridInit();
	//获取推送消息
	setInterval("runTip('TIP_ZX')", 5000);  
	 keySearch(queryListPage);
	 keySearch(queryImeilist, 3);
	 WorkTreeInit1();//树数据（月）
	 
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	$('#packTreeDate').val("");
    	$("#packTreeState").val("");
    	$("#treeType").val("");
    	$("#packMonth").val("");
    	queryListPage(1);  
	   });
}); 

/**
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber){
	$("#showTip").hide();
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	var treeType = $("#treeType").val();
	if(!treeType){
		getTreeNode();
		$("#packTreeDate").val("");
		$("#packMonth").val("");
		$('#typeTreeTime1').find('.tree-node-selected').removeClass('tree-node-selected');//取消树节点选中事件
	}
	var state = $("#state").combobox('getValue');
	if(!$("#tree-Date").val() && !$("#packTreeState").val() && state == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
		return;
	}
	var url=ctx+"/pack/packListPage";
	var selParams = "startTime="+$("#startTime").val() +
					"&endTime="+$("#endTime").val() +
					"&treeDate="+$("#tree-Date").val()+
					"&packMonth="+$("#packMonth").val()+
					"&packTreeDate="+$("#packTreeDate").val()+
					"&w_packState="+state+ 
					"&w_cusName="+$.trim($("#searchCusname").val())+
					"&repairnNmber=" + $.trim($("#repairnNmber").val());
	var param = addPageParam('DataGrid1', currentPageNum, selParams);
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			
			$.each(returnData.data,function(i, item){
				//状态
				if(item.w_packState=="0"){
					item.w_packState="<label style='color:red;font-weight: bold;'>待装箱</label>";
				}else{
					item.w_packState="<label style='color:green;font-weight: bold;'>已发货</label>";
				}
				
				//客户寄货方式
				if(item.payDelivery =='N'){
					item.payDelivery="<label style='color:#0066FF;font-weight: bold;'>顺付</label>";
				}else{
					item.payDelivery="<label style='font-weight: bold;'>到付</label>";
				}
				//客户收货方式
				if(item.w_customerReceipt =='N'){
					item.w_customerReceipt="<label style='font-weight: bold;'>到付</label>";					
				}else{
					item.w_customerReceipt="<label style='color:#0066FF;font-weight: bold;'>顺付</label>"; //我们这边付快递费
				}
				
				if(item.payedLogCost=='0'){
					item.payedLogCost="<label style='color:green;font-weight: bold;'>已支付</label>";					
				}else if(item.payedLogCost=='1'){
					item.payedLogCost="<label style='color:red;font-weight: bold;'>未支付</label>";
				}else{
					item.payedLogCost="";
				}
			});
			
			
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
	}, function(){
	 	$.messager.alert("操作提示","网络错误","info");
	 	$("#DataGrid1").datagrid('loaded');	
	});
	//重置推送消息
	resetTip('TIP_ZX');
	$("#scanimei").val("");
	$("#scanimei").focus();
} 

function getTreeNode(){
	var treeDate = $('#tree-Date').val();
	if(treeDate && treeDate!='1' && treeDate!='2'){
		//点击tree查询清空处理状态和开始结束日期
		$("#state").combobox('setValue','0');
		$("#startTime").val("");
		$("#endTime").val("");
	}else{
		$('#tree-Date').val("");
	}
	var treeState = $("#tree-State").val();
	if(treeState=='2'){
		//查询已完成的数据
		$("#state").combobox('setValue','1');
	}else if(treeState=='1'){
		$("#state").combobox('setValue','2');
	}
}

/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	$("#DataGrid1").datagrid({
     singleSelect:true,			
     onDblClickRow:function(rowIndex,row){
    	 queryImeiSate();
		}
	}); 
	queryListPage(1);
	
	$("#matchImei_DataGrid").datagrid({
		singleSelect:true
	});
	queryImeilist();
}


//初始化查看窗口
function initLookWindow(){
	windowVisible("lookManage");
	$('#lookManage').window('open');
	 selAllState('pack_search_State');
	 $("#searchImei").val("");
	$("#DataGrid2").datagrid({
     singleSelect:true,
     onDblClickRow:function(rowIndex,row){
    	 openUpdateImei(rowIndex,row);
		}
	}); 
	$("#repairState_DataGrid").datagrid({
		singleSelect : false
	});
}

function updateImei(){		
	var isValid = $('#upImeiform').form('validate');
	if(!isValid){
		return;
	}else if($("#newImei").val().length != 15){
		$.messager.alert("操作提示","IMEI必须为15位数字","info");
		return;
	}
	var url=ctx+"/pack/updateImei";
	var param="imei="+$("#oldImei").val()+
			"&newImei="+$("#newImei").val();  		
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if (returnData.code == '0') {
			$.messager.alert("操作提示",returnData.msg,"info", function(){
				windowCommClose('updateImei_w')
				queryImeiSate('search');
			});
			
		} 
		});
	
	
}

function openUpdateImei(rowIndex,entity){
	if(entity){
		windowVisible("updateImei_w");
		windowCommOpen("updateImei_w");
		$("#newImei").val("");
		$("#oldImei").val(entity.imei);
	}else{
		$.messager.alert("操作提示","未匹配到数据","info");
	}
}

//弹出查看
function queryImeiSate(type){
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length==1){
		if (!type) {
			initLookWindow();
		}
		var repairnNmber=updated[0].repairnNmber;
		var searchImei = $("#searchImei").val();
		var url=ctx+"/pack/imeiSateList";
		var state = $("#pack_search_State option:selected").val();
		var param="repairnNmber="+repairnNmber+"&imei="+searchImei+"&state="+state;  		
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){
				$.each(returnData.data,function(i, item){
					//距受理时间1天，受理时间显示黄色，两天，红色
					if(item.state != "-1"){//装箱工站未发货数据
						var t =	getDayFromAcceptanceTime(item.acceptanceTime);
						if(t == 1){
							item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
						}else if(t == 2){
							item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
						}
					}
					
					//状态
					if(item.state=="-1"){
						item.state="<label style='color:green;font-weight: bold;'>已发货</label>";
					}else if(item.state=="0"){
						item.state="<label style='color:red;font-weight: bold;'>已受理</label>";
					}else if(item.state=="1"){
						item.state="<label style='color:red;font-weight: bold;'>已受理，待分拣</label>";
					}else if(item.state=="2"){
						item.state="<label style='color:red;font-weight: bold;'>已分拣，待维修</label>";
					}else if(item.state=="3"){
						item.state="<label style='color:red;font-weight: bold;'>待报价</label>";
					}else if(item.state=="4"){
						item.state="<label style='color:red;font-weight: bold;'>已付款，待维修</label>";
					}else if(item.state=="5"){
						item.state="<label style='color:red;font-weight: bold;'>已维修，待终检</label>";
					}else if(item.state=="6"){
						item.state="<label style='color:red;font-weight: bold;'>已终检，待维修</label>";
					}else if(item.state=="7"){
						item.state="<label style='color:red;font-weight: bold;'>已终检，待装箱</label>";
					}else if(item.state=="8"){
						item.state="<label style='color:red;font-weight: bold;'>放弃报价，待装箱</label>";
					}else if(item.state=="9"){
						item.state="<label style='color:red;font-weight: bold;'>已报价，待付款</label>";
					}else if(item.state=="10"){
						item.state="<label style='color:red;font-weight: bold;'>不报价，待维修</label>";
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
					}
					
					if (item.machinaInPack == 'Y') {
						item.machinaInPack = "<label style='color:blue;font-weight: bold;'>是</label>";
					} else {
						item.machinaInPack = "<label style='color:red;font-weight: bold;'>否</label>";
					}
					
					// 保内保外
					if (item.isWarranty == 0) {
						item.isWarranty = "<label style='color:green;font-weight: bold;'>保内</label>";
					} else if (item.isWarranty == 1) {
						item.isWarranty = "<label style='color:red;font-weight: bold;'>保外</label>";
					}

					//是否人为
					if (item.w_isRW == 0) {
						item.w_isRW = "<label style='color:red;font-weight: bold;'>是</label>";
					} else {
						item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
					}
				});
				if(!state && !searchImei){
					stateInfo(repairnNmber);
				}				
				$("#DataGrid2").datagrid('loadData',returnData.data);//加载查看窗口数据
				$("#infoCount").html("共<font color='green' style='font-weight: bold;'> [ "+returnData.data.length+" ] </font> 条数据");
				var packCount = 0;
				if (returnData.data[0]) {
					packCount = returnData.data[0].machinaInPackCount;
				}
				$("#packCount").html("已扫描<font color='green' style='font-weight: bold;'> [ "+packCount+" ] </font> 条数据");
				
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}		
		}, function(){
			 	$.messager.alert("操作提示","网络错误","info");
		});
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}else if(updated.length>1){
		$.messager.alert("提示信息","只能选择一行修改！","info");
	}
} 

//点击装箱
function clickPage(type){
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length==1){
		if(!type){
			if(updated[0].payedLogCost.indexOf('未支付') >-1){
				$.messager.alert("提示信息","客户物流费未支付暂时不能装箱！","info");
			}else{
				var repairnNmber=updated[0].repairnNmber;
				var url=ctx+"/pack/checkPage";
				var param="repairnNmber="+repairnNmber;  		
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						InfoUpdate();
					}else{
						$.messager.alert("提示信息",returnData.msg,"info");
					}
				});
			}
		}else{
			packRepairNumberInfo();
		}
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}else if(updated.length>1){
		$.messager.alert("提示信息","只能选择一行修改！","info");
	}
}


//弹出装箱框
function InfoUpdate(){
	windowVisible("packManage");
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length==1){
		var getCurrentId=updated[0].id;
		var url=ctx+"/pack/getInfo";
		var param="id="+getCurrentId+"&packId="+updated[0].w_packId;
		asyncCallService(url, 'post', false,'json', param, function(returnData) {
			var entity = returnData.data;
			$("#pack_id").val(entity.id);
			$("#pack_w_packId").val(entity.w_packId);
			$("#pack_imei").val(entity.imei);
			$("#pack_w_cusName").val(entity.w_cusName);
			$("#pack_repairnNmber").val(entity.repairnNmber);
			$("#pack_w_phone").val(entity.w_phone);
			$("#pack_w_cusRemark").val(entity.w_cusRemark);
			$("#pack_w_address").val(entity.w_address);
			$("#pack_w_expressCompany").val(entity.w_expressCompany);
			$("#pack_w_expressNO").val(entity.w_expressNO);
			$("#pack_w_packingNO").val(entity.w_packingNO);
			$("#pack_w_packer").val(entity.w_packer);
			$("#pack_w_shipper").val(entity.w_shipper);
			$("#packRemark").val(entity.w_packRemark);
			$("#w_price_Remark").val(updated[0].w_price_Remark);
			//TODO 时间
			if (entity.w_packTime == undefined) {
				entity.w_packTime=getNowFormatDate();  //装箱 日期获取到当天日期
			}
			$("#pack_w_packTime").val(entity.w_packTime);
			$("#pack_w_shipper").val("深圳市康凯斯信息技术有限公司");
			$('#packManage').window('open');
	 	}, function(){
	 		$.messager.alert("操作提示","网络错误","info");
	 	});
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}else if(updated.length>1){
		$.messager.alert("提示信息","只能选择一行修改！","info");
	}
}

//弹出修改装箱备注
function packRepairNumberInfo(){
	windowVisible("packRepairNumberManage");
	var updated = $('#DataGrid1').datagrid('getSelections');
	if (updated.length==1){
		var getCurrentId=updated[0].id;
		var url=ctx+"/pack/getInfo";
		var param="id="+getCurrentId+"&packId="+updated[0].w_packId;
		asyncCallService(url, 'post', false,'json', param, function(returnData) {
			var entity = returnData.data;
			$("#packRepairNumber_id").val(entity.id);
			$("#packRepairNumber_w_packId").val(entity.w_packId);
			$("#packRepairNumber_imei").val(entity.imei);
			$("#packRepairNumber_w_cusName").val(entity.w_cusName);
			$("#packRepairNumber_repairnNmber").val(entity.repairnNmber);
			$("#packRepairNumber_w_phone").val(entity.w_phone);
			$("#packRepairNumber_w_cusRemark").val(entity.w_cusRemark);
			$("#packRepairNumber_w_address").val(entity.w_address);
			$("#packRepairNumberRemark").val(entity.w_packRemark);
			$("#packRepairNumber_price_Remark").val(updated[0].w_price_Remark);
			$('#packRepairNumberManage').window('open');
	 	}, function(){
	 		$.messager.alert("操作提示","网络错误","info");
	 	});
	}else if(updated.length==0){
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}else if(updated.length>1){
		$.messager.alert("提示信息","只能选择一行修改！","info");
	}
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
} 

//装箱
function packUpdate(){
	var url=ctx+"/pack/updateInfo";
	var param={
			"expressCompany" : $("#pack_w_expressCompany").val(),
			"expressNO" : $("#pack_w_expressNO").val(),
			"packingNO" : $("#pack_w_packingNO").val(),
			"shipper" : $("#pack_w_shipper").val(),
			"packRemark" : $("#packRemark").val(),
			"packDate" : $("#pack_w_packTime").val(),
			"repairnNmber" : $("#pack_repairnNmber").val()
	}
	asyncCallService(url, 'post', false,'json', param, function(returnData)
	{
		processSSOOrPrivilege(returnData);
		if(returnData.code=='0'){
			queryListPage(currentPageNum);
			windowCommClose("packManage");
		}
		$.messager.alert("操作提示",returnData.msg,"info");
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}

//增加装箱备注
function updatePackRemark(){
	var url=ctx+"/pack/updatePackRemark";
	var param={
			"packRemark" : $("#packRepairNumberRemark").val(),
			"repairnNmber" : $("#packRepairNumber_repairnNmber").val()
	}
	asyncCallService(url, 'post', false,'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if(returnData.code=='0'){
			queryListPage(currentPageNum);
			windowCommClose("packRepairNumberManage");
		}
		$.messager.alert("操作提示",returnData.msg,"info");
	}, function(){
		$.messager.alert("操作提示","网络错误","info");
	});
}


// 扫描设备确认设备实物已到装箱工站
function scancData (event) {
	var Scanimei = $.trim($("#scanimei").val());
	var evt = event;
	if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
		nimeiAginSubmit = true;
		var url = ctx+"/workflowcon/getDataByImeiInPack";
		var param = null;
		if(Scanimei.length == 15){
			param = {
					"imei" : Scanimei
			};
		}else if(Scanimei.length <15){
			param = {
					"lockId" : Scanimei 
			};
		}else{
			param = {
					"lockInfo" : Scanimei 
			};
		}
		if(param){
			asyncCallService(url, 'post', false,'json', param, function(returnData){
				nimeiAginSubmit = false;
				processSSOOrPrivilege(returnData);
				if(returnData.code== undefined){	
					var ret = returnData.data;
					var showTip = "客户：<label style='color:green;font-weight: bold;'>"+ret.w_cusName + 
										"</label><br>批次号为：<label style='color:green;font-weight: bold;'>" + ret.repairnNmber + 
										"</label><br>IMEI为：<label style='color:green;font-weight: bold;'>" + ret.imei;
					if(ret.lockId){
						showTip = showTip + "</label><br><label style='margin-left : 42px;'>智能锁ID为：</label><label style='color:green;font-weight: bold;'>" + ret.lockId;
					}
					$.messager.alert("操作提示", showTip + "</label><br><label style='margin-left : 42px;'>的设备已发送到装箱</label>","info", function(){
						$("#scanimei").val("");				
						$("#scanimei").focus();
					});
				}else {
					$.messager.alert("操作提示",returnData.msg,"info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});;
				}			
			}, function(){
				$.messager.alert("操作提示","网络错误","info", function(){
					$("#scanimei").val("");
					$("#scanimei").focus();
				});		
			});
		}else{
			$.messager.alert("操作提示","扫描错误","info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});	
		}
	}
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
				} else if (item.state == '13') {
					item.state = "待终检";
				} else if (item.state == "14") {
					item.state = "放弃维修";			
				} else if (item.state == "15") {
					item.state = "测试中";			
				} else if (item.state == "16") {
					item.state = "已测试,待维修";			
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

function matchImei(){
	windowVisible("w_matchImei");
	$('#w_matchImei').window('open');
	$("#matchImei").val("");
}

function queryImeilist(){
	var matchImei = $.trim($("#matchImei").val());
	if(matchImei){
		if(matchImei.length == 6){
			var url=ctx+"/pack/matchImei";
			var param = {
					"imei" : matchImei
			}
			asyncCallService(url, 'post', false,'json', param, function(returnData)	{
				processSSOOrPrivilege(returnData);
				if(returnData.code== undefined){
					$.each(returnData.data,function(i, item){
						//状态
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
						} else if (item.state == '13') {
							item.state = "待终检";
						} else if (item.state == "14") {
							item.state = "放弃维修";			
						} else if (item.state == "15") {
							item.state = "测试中";			
						} else if (item.state == "16") {
							item.state = "已测试,待维修";			
						}
					});
					$("#matchImei_DataGrid").datagrid('loadData', returnData.data);
				}
			}, function(){
				$.messager.alert("操作提示","网络错误","info");
			});
		}else{
			$.messager.alert("操作提示","匹配的IMEI不是6位","info");
		}
	}else{
		$("#matchImei_DataGrid").datagrid('loadData',{"total":0,"rows":[]});
	}
}

//日期树（月）
function WorkTreeInit1(){
	$.ajax({
		type : "POST",
		async : false,
		cache : false,
		url : ctx + '/pack/getTimeList',
		data : null,
		dataType : "json",
		success : function(returnData) {
			WorkTreeLoad1(returnData);
		},
		error : function() {
			$.messager.alert("操作提示", "网络错误", "info");
		}
	});
}

function WorkTreeLoad1(returnData) {
	var parentTree = '[{"id":"","text":"装箱单(月)",children:[';	
	var groupList = "";
	var reg = /,$/gi;
	$.each(returnData.data, function(i, j) {
		if (j.children) {
			groupList += '{"id":"' + j.id + '","text":"' + j.text + '", "state" : "closed", "children":[';
			$.each(j.children, function(a, b) {
				if (b.children) {
					groupList += '{"id":"' + b.id + '","text":"' + b.text + '", "state" : "closed", "children":[';
					$.each(b.children, function(x, y) {
						groupList += '{"id":"' + y.id + '","text":"' + y.text + '"},';
						
					});
					groupList = groupList.replace(reg, "");
					groupList += ']},';
				} else {
					groupList += '{"id":"' + b.id + '","text":"' + b.text + '", "state" : "closed"},';
				}
			});
			groupList = groupList.replace(reg, "");
			groupList += ']},';
		} else {
			groupList += '{"id":"' + j.id + '","text":"' + j.text + '", "state" : "closed"},';
		}
	});
	groupList = groupList.replace(reg, "");
  	parentTree += groupList+']}]';
  	
	$('#typeTreeTime1').tree(
		 {
			data : eval(parentTree),
			onClick : function(node){
				$("#treeType").val(node.id);
				$('#tree-Date').val("");
		    	$("#tree-State").val("");
		    	$('#typeTreeTime').find('.tree-node-selected').removeClass('tree-node-selected');
				if(node.id){
					if(node.id == '1' || node.id == '2'){
						$("#packTreeState").val(node.id);
						$("#packTreeDate").val("");
					}else{
						$("#packTreeDate").val(node.id);
						if(node.id.length == 7){
							var parentNode = $('#packTreeDate').tree('getParent',node.target);
							$("#packTreeState").val(parentNode.id);
							$("#packMonth").val(node.id);//月
							$("#startTime").val("");
							$("#endTime").val("");
						}else if(node.id.length == 10){
							var parentNode = $('#packTreeDate').tree('getParent',node.target);
							var rootParentNode = $('#packTreeDate').tree('getParent',parentNode.target);
							$("#packTreeState").val(rootParentNode.id);
							$("#packMonth").val("");
						}
					}
					if($("#packTreeState").val()){
						if($("#packTreeState").val() == 1){
							$("#state").combobox('setValue','2');//已完成
						}else if($("#packTreeState").val() == 2){
							$("#state").combobox('setValue','1');//未完成
						}
						$("#startTime").val("");
						$("#endTime").val("");
						queryListPage(1);
					}
				}
			}
		 }
     );
}

// 导出
function exportInfo() {
	var treeType = $("#treeType").val();
	if(!treeType){
		getTreeNode();
		$("#packTreeDate").val("");
		$("#packMonth").val("");
		$('#typeTreeTime1').find('.tree-node-selected').removeClass('tree-node-selected');//取消树节点选中事件
	}
	var state = $("#state").combobox('getValue');
	if(!$("#tree-Date").val() && !$("#packTreeState").val() && state == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","导出已完成数据请选择开始，结束日期","info");
		return;
	}
	var url =  ctx + "/pack/ExportPackDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='startTime' value='" + $("#startTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='endTime' value='" + $("#endTime").val() +"'/>"));
	$form1.append($("<input type='hidden' name='treeDate' value='" + $("#tree-Date").val() +"'/>"));
	$form1.append($("<input type='hidden' name='packMonth' value='" + $.trim($("#packMonth").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='packTreeDate' value='" + $.trim($("#packTreeDate").val()) +"'/>")); 
	$form1.append($("<input type='hidden' name='w_packState' value='" + state +"'/>"));
	$form1.append($("<input type='hidden' name='w_cusName' value='" + $.trim($("#searchCusname").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='repairnNmber' value='" + $.trim($("#repairnNmber").val()) +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove();
}