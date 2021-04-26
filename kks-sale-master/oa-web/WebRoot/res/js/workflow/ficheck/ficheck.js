$.messager.defaults = {
	ok : "确定",
	cancel : "取消"
};
var currentPageNum;
var Heartbeat = 5000 // 5秒
$(function() {
	datagridInit();
	 keySearch(queryListPage);
	$('.panel-tool').hide();
	$("#scanimei").focus();//扫描IMEI获得焦点
	// 获取推送消息
	setInterval("runTip('TIP_ZJ')", 5000);
	
	 $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	   });
	
	 //终检页面按回车键保存
	 $("#wzj").keydown(function(e){
		 if (e.keyCode == 13) {
			 $("#ficheckUpdate").click();
	      }
	 })
});

// --------------------------------------------- 初始化 》工具栏 ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit() {
	// 受理列表工具栏
	$("#DataGrid1").datagrid({
		singleSelect : false,
		onDblClickRow : function(rowIndex, row) {
			DblClickShowInfo(rowIndex, row);
		}
	});
	queryListPage(1);

	$("#table_wzj").datagrid({
		singleSelect : true
	});

}

//初始化查看窗口
function initLookWindow(){
	windowVisible("showAll");
	$('#showAll').window('open');
	selAllState('ficheck_search_State');
	$("#DataGrid2").datagrid({
	     singleSelect:true
	}); 
	$("#repairState_DataGrid").datagrid({
		singleSelect : false
	});
}
// --------------------------------------------- 初始化 》工具栏 ------------------------------------------ END

/**
 * 同步筛选扫描数据
 */
function scancData(event) {	
	$('#DataGrid1').datagrid("clearSelections");
	var Scanimei = $.trim($("#scanimei").val());
//	var evt=evt?evt:(window.event?window.event:null); //兼容IE和FF
	var evt = event;
	var nimeiAginSubmit = false;
	if((Scanimei.length >=6 && evt.keyCode==13) && !nimeiAginSubmit){
		var rows = $("#DataGrid1").datagrid("getRows");
		var ids = null;
		if(rows){
			for (var int = 0; int < rows.length; int++) {
				var row = rows[int];
				if(($.trim(row.imei)==Scanimei || $.trim(row.lockId)==Scanimei || $.trim(row.lockInfo)==Scanimei) && row.w_FinispassDesc.indexOf('已完成') == -1){
					$('#DataGrid1').datagrid('selectRow',int);
					ids=row.id;
				}
			}
			if(null !=ids){
				nimeiAginSubmit = true;
				$("#scanimei").blur();
				doFinCheck();
			}else{
				$("#scanimei").blur();
				nimeiAginSubmit = false;
				$('#DataGrid1').datagrid("clearSelections");
				$.messager.alert("操作提示","未匹配到数据","warning",function(){
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}
		}
	}else if(Scanimei.length > 15 && !nimeiAginSubmit){
		nimeiAginSubmit = false;
		$.messager.alert("操作提示","未匹配到数据","warning",function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	}else{
		$("#scanimei").focus();
		nimeiAginSubmit = false;
	}	
}

// --------------------------------------------- 终检列表管理 ------------------------------------------ START
/**
 * 计时刷新同步数据
 */
function syscDBDatas() {
	window.setInterval("queryListPage(1)", Heartbeat);
}

/**
 * 初始化终检列表 初始化数据及查询函数
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
	var state = $("#state").combobox('getValue');
	if(!$("#tree-Date").val() && state == '1' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
		return;
	}
	var url = ctx + "/workflowfinCheckcon/finCheckList";
	var selParams = "imei=" + $.trim($("#imei").val())
				+"&lockId=" + $.trim($("#lockId").val())
				+"&state="+state
	    	    +"&startTime="+$("#startTime").val() 
	    	    +"&endTime="+$("#endTime").val() 
	    	    +"&treeDate="+$("#tree-Date").val()
				+"&repairnNmber=" + $.trim($("#repairnNmber").val());
	 var param = addPageParam('DataGrid1', currentPageNum, selParams);
	$("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if (returnData.code == undefined) {
			if(returnData.data.length > 0){
				$.each(returnData.data, function(i, item) {
					//距受理时间1天，受理时间显示黄色，两天，红色
					if(item.w_FinispassFalg != "-1"){//终检工站未发送数据
						var t =	getDayFromAcceptanceTime(item.acceptanceTime);
						if(t == 1){
							item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
						}else if(t == 2){
							item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
						}
					}
					
					item.w_model = item.w_model + " | " + item.w_marketModel;
					// 是否维修
					if (item.isWarranty == 0) {
						item.isWarranty = "保内";
					} else {
						item.isWarranty = "保外";
					}
					// 状态
					if (item.w_FinispassFalg == 0) {
						item.w_FinispassDesc = "<label style='color:red;font-weight: bold;'>待终检</label>";
					} else if (item.w_FinispassFalg == 1) {
						item.w_FinispassDesc = "<label style='color:green;font-weight: bold;'>已终检,待装箱</label>";
					} else if (item.w_FinispassFalg == 2) {
						item.w_FinispassDesc = "<label style='color:red;font-weight: bold;'>已终检,待维修</label>";
					} else if (item.w_FinispassFalg == 3) {
						item.w_FinispassDesc = "<label style='color:blue;font-weight: bold;'>测试中</label>";
					} else {
						item.w_FinispassDesc = "<label style='color:green;font-weight: bold;'>已完成</label>";
					}
					if(!item.w_zzgzDesc){
						item.w_zzgzDesc = "无异常";
					}
					if(i == 0){
						$("#giveupCount").html(item.giveupPriceCount);		
					}
							
				});
			}else{
				$("#giveupCount").html('0');	
			}		
			
			$("#DataGrid1").datagrid('loadData', returnData.data);
			getPageSize();
			resetCurrentPageShow(currentPageNum);
			$("#scanimei").val("");
			$("#scanimei").focus();
		} else {
			$.messager.alert("操作提示", returnData.msg, "info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});			
		}
		$("#DataGrid1").datagrid('loaded');
		$("#acc_number").html($("#DataGrid1").datagrid('getRows').length);		
		$("#scanimei").val("");
		$("#scanimei").focus();
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info", function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	});

	// 重置推送消息
	resetTip('TIP_ZJ');
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

function windowFiCheckClose()
{	
	$('#wzj').window('close');
	$("#scanimei").val("");
	nimeiAginSubmit = false;
	$('#DataGrid1').datagrid("clearSelections");
	$("#scanimei").focus();
}

/**
 * 清除数据
 */
function clearFromDatas() {
	$("#fm_imei").val("");
	$("#fm_lockId").val("");
	$("#fm_bluetoothId").val("");
	$("#fm_w_cusName").val("");
	$("#fm_w_model").val("");
	$("#fm_w_marketModel").val("");

	$("#fm_w_isWarranty").val("");
	$("#fm_w_finChecker").val("");
	$("#fm_w_zzgzName").val("");

	$("#fm_w_model").val("");
	$("#fm_w_finDesc").val("");
	$("input[name='fm_w_ispass'][value='1']").prop("checked", true);
}

/**
 * 终检
 */
function doFinCheck() {
	windowVisible("wzj");
	clearFromDatas();
	var rows = $("#DataGrid1").datagrid("getSelections");
	var ids = null;
	if (rows.length == 1) {
		var state = rows[0].w_FinispassDesc.substring(rows[0].w_FinispassDesc.indexOf(">")+1,rows[0].w_FinispassDesc.indexOf("</label>")).trim();
		$("#fm_w_engineer").val(rows[0].w_engineer);
		$("#fm_w_payTime").val(rows[0].w_payTime);
		$("#testResult_info").val(rows[0].testResult);
		if(state == '已完成'){
			$.messager.alert("操作提示","IMEI为<font color='red'>"+rows[0].imei+"</font>是已完成的数据禁止操作","info");
			return;
		}
		if(state == '测试中'){
			$.messager.alert("操作提示","IMEI为<font color='red'>"+rows[0].imei+"</font>的数据正在测试禁止操作","info");
			return;
		}
		var url = ctx + "/workflowfinCheckcon/doFinCheck";
		var param = "id=" + rows[0].id + "&fiId=" + rows[0].w_finId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == '0') {
				var entity = returnData.data;
				// TODO 显示单据信息
				$("#id").val(entity.id);
				$("#w_fId").val(entity.w_fId);
				$("#fm_imei").val(entity.imei);
				$("#fm_lockId").val(entity.lockId);
				$("#fm_bluetoothId").val(entity.bluetoothId);
				$("#fm_w_cusName").val(entity.w_cusName);
				$("#fm_w_model").val(entity.w_model);
				$("#fm_w_marketModel").val(entity.w_marketModel);
				(entity.isWarranty == 0) ? $("#fm_w_isWarranty").val("保内") : $("#fm_w_isWarranty").val("保外");
				$("#fm_w_finChecker").val(entity.w_FinChecker);
				$("#fm_w_zzgzName").val(entity.w_zzgzDesc);
				$("#fm_w_finDesc").val(entity.w_FinDesc);
				$("input[name='fm_w_ispass'][value='"+ entity.w_ispass +"']").prop("checked", true);
				

				// TODO 加载终检测试用例匹配数据
				if (entity.w_model != null && entity.w_model != undefined) {
					// TODO 根据主板型号获取检测方式
					var url = ctx + "/zjppmanage/getZjppListByModel";
					var param = "model=" + entity.w_model
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData) {
							$("#table_wzj").datagrid('loadData', returnData.data);
						} else {
							$.messager.alert("操作提示", returnData.msg, "info", function(){
								$("#scanimei").val("");
								$("#scanimei").focus();
							});
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					});
				}
				windowCommOpen("wzj");
				$("#fm_w_finDesc").focus();
			} else {
				$.messager.alert("操作提示", returnData.msg, "info", function(){
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		});
	} else if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择单行数据终检！", "warning", function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	} else {
		$.messager.alert("操作提示", "请选择一行进行终检!", "infow", function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	}
}

function DblClickShowInfo(rowIndex, rows) {
	if (rows) {
		var state = rows.w_FinispassDesc.substring(rows.w_FinispassDesc.indexOf(">")+1,rows.w_FinispassDesc.indexOf("</label>")).trim();		
		$("#fm_w_engineer").val(rows.w_engineer);
		$("#fm_w_payTime").val(rows.w_payTime);
		$("#testResult_info").val(rows.testResult);
		if(state == '已完成'){
			$.messager.alert("操作提示","IMEI为<font color='red'>"+rows.imei+"</font>是已完成的数据禁止操作","info");
			return;
		}
		if(state == '测试中'){
			$.messager.alert("操作提示","IMEI为<font color='red'>"+rows.imei+"</font>的数据正在测试禁止操作","info");
			return;
		}
		windowVisible("wzj");
		clearFromDatas();
		var url = ctx + "/workflowfinCheckcon/doFinCheck";
		var param = "id=" + rows.id + "&fiId=" + rows.w_finId;
		asyncCallService(url, 'post', false, 'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if (returnData.code == '0') {
				var entity = returnData.data;
				// TODO 显示单据信息
				$("#id").val(entity.id);
				$("#w_fId").val(entity.w_fId);
				$("#fm_imei").val(entity.imei);
				$("#fm_lockId").val(entity.lockId);
				$("#fm_bluetoothId").val(entity.bluetoothId);
				$("#fm_w_cusName").val(entity.w_cusName);
				$("#fm_w_model").val(entity.w_model);
				$("#fm_w_marketModel").val(entity.w_marketModel);
				(entity.isWarranty == 0) ? $("#fm_w_isWarranty").val("保内") : $("#fm_w_isWarranty").val("保外");
				$("#fm_w_finChecker").val(entity.w_FinChecker);
				$("#fm_w_zzgzName").val(entity.w_zzgzDesc);
				$("input[name='fm_w_ispass'][value='"+ entity.w_ispass +"']").prop("checked", true);
				
				//维修无故障时显示无异常
				if(!$.trim($("#fm_w_zzgzName").val())){
					$("#fm_w_zzgzName").val("无异常");
				}
				$("#fm_w_finDesc").val(entity.w_FinDesc);

				// TODO 加载终检测试用例匹配数据
				if (entity.w_model != null && entity.w_model != undefined) {
					// TODO 根据主板型号获取检测方式
					var url = ctx + "/zjppmanage/getZjppListByModel";
					var param = "model=" + entity.w_model
					asyncCallService(url, 'post', false, 'json', param, function(returnData) {
						processSSOOrPrivilege(returnData);
						if (returnData) {
							$("#table_wzj").datagrid('loadData', returnData.data);
						} else {
							$.messager.alert("操作提示", returnData.msg, "info", function(){
								$("#scanimei").val("");
								$("#scanimei").focus();
							});
						}
					}, function() {
						$.messager.alert("操作提示", "网络错误", "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					});
				}
				windowCommOpen("wzj");
				$("#fm_w_finDesc").focus();
			} else {
				$.messager.alert("操作提示", returnData.msg, "info", function(){
					$("#scanimei").val("");
					$("#scanimei").focus();
				});
			}
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		});
	} else {
		$.messager.alert("操作提示", "请选择单行数据终检！", "warning", function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	}
}

/**
 * 发送装箱
 */
function sendEnchase() {
	var SendInfo = $("#DataGrid1").datagrid('getSelections');
	var all = true;
	var marked_words="";
	if (SendInfo.length>0){
		marked_words = "确定要发送<font color='red'>选中数据</font>到装箱？";
		all = false;
	}else{
		//一键发送符合条件的数据到装箱
		marked_words = "确定要发送所有<font color='red'>已终检，待装箱</font>的数据到装箱？";
		SendInfo = $("#DataGrid1").datagrid("getRows"); //获取当前页面所有数据
	}
	$.messager.confirm("操作提示", marked_words, function(conf) {
		if (conf) {
			var ids = "";
			var errorMsg = "";
			for (var int = 0; int < SendInfo.length; int++) {
				if (SendInfo[int].w_FinispassFalg == 1) {
					(int == 0) ? ids = SendInfo[int].id : ids = SendInfo[int].id + "," + ids;
				} else if(!all) {
					ids = "";
					errorMsg = "有不符合发送装箱的项，请检查！";
					break;
				}
			}
			if (ids != "") {
				var url = ctx + "/workflowfinCheckcon/sendEnchase";
				var param = {
						"ids" : ids
				}
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						queryListPage(currentPageNum);
						$.messager.alert("操作提示", "发送装箱成功！", "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					} else {
						$.messager.alert("操作提示", returnData.msg, "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				});
			} else {
				if(errorMsg){
					$.messager.alert("操作提示", errorMsg, "info");
				}else{
					$.messager.alert("操作提示", "未找到符合发送装箱的数据", "info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}
				
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
				if(SendInfo[i].w_FinispassFalg == 0 ){ // 已维修，待终检； 的数据
					(i == 0) ? ids = SendInfo[i].id : ids = SendInfo[i].id + "," + ids;
				}
			}
			
			if(ids){
				var testuser = $("#test_user option:selected").val();
				var url=ctx+"/workflowcon/sendWorkTest";
			    var param = {
		    		"ids" : ids,
		    		"dataSource" : "终检站",
		    		"testStatus" : "2",			//待终检，测试中
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

/**
 * 更新数据
 */
function InfoSave() {
	var ispass = $('input:radio:checked').val();
	var finDesc = $('#fm_w_finDesc').val();
	var id = $("#id").val();
	var w_fId = $("#w_fId").val();

	var url = ctx + "/workflowfinCheckcon/updateInfo";
	var param = "id=" + w_fId + "&wfId=" + id + "&ispass=" + ispass + "&finDesc=" + finDesc;
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		if (returnData.code == '0') {
			$.messager.alert("操作提示", "终检成功！", "info", function() {
				queryListPage(currentPageNum);
				windowCommClose("wzj");
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		} else {
			$.messager.alert("操作提示", returnData.msg, "info", function(){
				$("#scanimei").val("");
				$("#scanimei").focus();
			});
		}
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info", function(){
			$("#scanimei").val("");
			$("#scanimei").focus();
		});
	});
}

/**
 * 待终检返回维修
 */
function sendBackToRepair() {
	var sendP = $('#DataGrid1').datagrid('getSelections');
	var count = 0;//不符合条件数据个数
	if (sendP.length > 0) {
		$.messager.confirm("操作提示", "确定返回<font color='red'>选中数据</font>到维修工站？", function(conf){
			if(conf){
				var ids = "";
				var errorMsg = "";
				for (var i = 0; i < sendP.length; i++) {
					if(sendP[i].w_FinispassFalg == 0){//待终检
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
					var url = ctx + "/workflowfinCheckcon/sendBackToRepair";
					var param = {"ids" : ids};										
					asyncCallService(url, 'post', false,'json', param, function(returnData){
						processSSOOrPrivilege(returnData);
						if(returnData.code=='0'){
							$.messager.alert("操作提示","发送维修成功！","info",function(){
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
}

/**
 * 发送维修
 */
function sendRepairAgain() {
	var SendInfo = $("#DataGrid1").datagrid('getSelections');
	var all = true;
	var marked_words="";
	if (SendInfo.length>0){
		marked_words = "确定要发送<font color='red'>选中数据</font>到维修？";
		all = false;
	}else{
		//一键发送符合条件的数据到维修
		marked_words = "确定要发送所有<font color='red'>已终检,待维修</font>的数据到维修？";
		SendInfo = $("#DataGrid1").datagrid("getRows"); //获取当前页面所有数据
	}
	$.messager.confirm("操作提示", marked_words, function(conf) {
		if (conf) {
			var ids = "";
			var errorMsg = "";
			for (var int = 0; int < SendInfo.length; int++) {
				var state = SendInfo[int].w_FinispassDesc.substring(SendInfo[int].w_FinispassDesc.indexOf(">")+1,SendInfo[int].w_FinispassDesc.indexOf("</label>")).trim();
				if(state == '已完成' && !all){
					$.messager.alert("操作提示","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是已完成的数据禁止操作","info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
					return;
				}
				if (SendInfo[int].w_FinispassFalg == 2) {
					(int == 0) ? ids = SendInfo[int].id : ids = SendInfo[int].id + "," + ids;
				} else if(!all) {
					ids = "";
					errorMsg="有不符合发送维修的数据"
					break;
				}
			}
			if (ids != "") {
				var url = ctx + "/workflowfinCheckcon/sendRepair";
				var param = "ids=" + ids;
				asyncCallService(url, 'post', false, 'json', param, function(returnData) {
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", "发送维修成功！", "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
						queryListPage(currentPageNum);
					} else {
						$.messager.alert("操作提示", returnData.msg, "info", function(){
							$("#scanimei").val("");
							$("#scanimei").focus();
						});
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				});
			} else {
				if(errorMsg){
					$.messager.alert("提示信息", errorMsg, "info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}else{
					$.messager.alert("提示信息", "未找到符合发送维修的数据", "info", function(){
						$("#scanimei").val("");
						$("#scanimei").focus();
					});
				}
			}
		}
	});
}

//初始化批次查询数据
function showRepairState(type){
	var updated = $('#DataGrid1').datagrid('getSelected');
	if (updated){
		if (!type) {
			initLookWindow();
		}
		var repairnNmber=updated.repairnNmber;

		var url=ctx+"/workflowfinCheckcon/imeiSateList";
		var state = $("#ficheck_search_State option:selected").val();
		var param="repairnNmber="+repairnNmber+"&state="+state; 
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code==undefined){
				var ret=returnData.data;
				commData(ret);
				if(!state ){
					stateInfo(repairnNmber);
				}			
				$("#DataGrid2").datagrid('loadData',ret); 
				$("#infoCount").html("共<font color='green' style='font-weight: bold;'> [ "+ret.length+" ] </font> 条数据");
				
			}else {
				$.messager.alert("操作提示",returnData.msg,"info");
			}
		}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
		});
	}else{
		$.messager.alert("提示信息","请先选择所要修改的行！","info");
	}	
}

//弹出查看
function stateInfo(repairnNmber){
	var url = ctx + "/price/repairStateList";
	var param="price_repairnNmber="+repairnNmber; 		
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
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
			$("#repairState_DataGrid").datagrid('loadData',returnData.data);	
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}		
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});

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
			} else {
				item.w_isRW = "<label style='color:green;font-weight: bold;'>否</label>";
			}
			//是否付款
			if(item.w_isPay==0){
				item.w_isPay="<label style='color:green;font-weight: bold;'>是</label>";
			}else if(item.w_isPay=='1'){
				item.w_isPay="<label style='color:red;font-weight: bold;'>否</label>";
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
			}
			
			if (item.testResult) {
				item.testResult = "<span title="+item.testResult+">"+item.testResult+"</span>";
			}
		});
	}
}
// --------------------------------------------- 终检列表管理 ------------------------------------------ End
