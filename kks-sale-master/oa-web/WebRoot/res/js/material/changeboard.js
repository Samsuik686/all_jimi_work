/* ==========================================保内对换主板审批 start======================================== */
$.messager.defaults= { ok: "确定",cancel: "取消" };
$(function(){
	getCurrentUserPosition_changeboard();
	datagridInit_cb(); 
	keySearch(queryChangeboardListPage);
});

var currentPageNum_cb;

/**
* 获取当前分页参数
* @param size		数据源
* @param DataGrid	DataGrid ID
*/
var currPageSize = 30;
var windowCurrPageSize = 10;
 function pageSet_cb(total){
   var p = $('#changeboard_DataGrid').datagrid('getPager');
   $(p).pagination(
		{  
		    total:total,  
		    pageList:true,
		    showPageList:true,
		    pageList: [10,20,30,50],
		    showRefresh:false,
		    beforePageText: '第',
		    afterPageText: '页    共 {pages} 页',  
		    displayMsg: '   共 {total} 条记录',  
			onBeforeRefresh:function(){ 
				$(this).pagination('loading'); 
				$(this).pagination('loaded'); 
			},
		    onSelectPage:function(pageNumber, pageSize, total){ 
				$(this).pagination('loading'); 
				queryChangeboardListPage(pageNumber);   
				$(this).pagination('loaded'); 
			},
			onChangePageSize:function(pageSize){ 
		       $(this).pagination('loading');
				currPageSize = pageSize;
		       $(this).pagination('loaded'); 
		}  
    }); 
 }
 
  function getPageSize_cb(){
	  $.ajax({ 
	        type:"GET",     
	        async:false,    
	        cache:false,    
	       	url:ctx+"/form/queryCurrentPageSize.api",    
	        dataType:"json",
	        success:function(returnData){	
	        	if(returnData.code!=null){
	        		$.messager.alert("操作提示","系统繁忙，请稍候在试!","info");
	        		return 0;
	        	}
	        	pageSet_cb(returnData.data);
	        },   
	       error:function(){
			   $.messager.alert("操作提示","网络错误","info");
			   return 0;
		   }
     });  
 }

/**
 * 当当前页处于>1时，点击某条件查询按钮后，分页栏还是未同步当前页码
 * @param currentPageNum	当前页码
 * @param DataGrid		DataGrid ID
 */
function resetCurrentPage_cb(currentPageNum){
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum_cb=1;
	}
	$('#changeboard_DataGrid').datagrid('getPager').pagination({
		 pageNumber:currentPageNum_cb
	});
}

function getCurrentPageSize_cb(DataGrid){
   var pageSize = 30;
   if (DataGrid == 'changeboard_DataGrid') {
	   // 一级页面的table
	   pageSize =  currPageSize;
   } else {
	   pageSize = windowCurrPageSize;	   
   }
   return pageSize;
}

//时分秒十以内前面加0
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

function queryChangeboardListPage(pageNumber)  {//初始化数据及查询函数
	currentPageNum_cb = pageNumber;
	if(currentPageNum_cb == "" || currentPageNum_cb == null){
		currentPageNum_cb=1;
	}
	var pageSize = getCurrentPageSize_cb('changeboard_DataGrid');
	var url=ctx+"/changeboard/changeboardList";
    var param={
    		"applicater" : $.trim($("#searchByApplicater").val()),
    		"repairOrTest" : $("#searchByRepairOrTest option:selected").val(),
    		"state" : $("#searchByState option:selected").val(),
    		"startDate" : $("#startTime").val(),
    		"endDate" : $("#endTime").val(),
    		"currentpage" : currentPageNum_cb,
			"pageSize" : pageSize
    }
    $("#changeboard_DataGrid").datagrid('loading');
    asyncCallService(url, 'post', false,'json', param, function(returnData){
		
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){
			$.each(returnData.data,function(i, item){
				var uPosition = $.trim($("#changeboard_uPosition").val());//登录人职位
				
				if(item.sendFlag == 1){
					 if(item.repairOrTest == "测试"){
						 if(uPosition.indexOf("终端技术主管") > -1){
							 	item.sendFlag = "<label style='color:green;font-weight: bold;'>终端技术主管</label>";
							}else{
								item.sendFlag = "终端技术主管";
							}
					 }else if(item.repairOrTest == "维修"){
						 if(uPosition.indexOf("维修主管") > -1 && uPosition.indexOf("终端技术主管") == -1 ){
							 item.sendFlag = "<label style='color:green;font-weight: bold;'>维修主管</label>";
						}else{
							 item.sendFlag = "维修主管";
						}
					}
				}else if(item.sendFlag == 2){
					if(uPosition.indexOf("客服中心经理") > -1){
						item.sendFlag = "<label style='color:green;font-weight: bold;'>客服中心经理</label>";
					}else{
						item.sendFlag = "客服中心经理";
					}
				}else if(item.sendFlag == 3){
					if(uPosition.indexOf("客服文员") > -1){
						item.sendFlag = "<label style='color:green;font-weight: bold;'>客服文员</label>";
					}else{
						item.sendFlag = "客服文员";
					}
				}else if(item.sendFlag == 4){
					if(uPosition.indexOf("物料管理员") > -1){
						item.sendFlag = "<label style='color:green;font-weight: bold;'>物料管理员</label>";
					}else{
						item.sendFlag = "物料管理员";
					}
				}
				
				if(item.state==0){
					item.state="<label style='color:#1C86EE;font-weight: bold;'>待主管批复</label>";
				}else if(item.state==1){
					//修改待经理批复为待换板（即主管同意）
					item.state="<label style='color:green;font-weight: bold;'>待换板</label>";
				}else if(item.state==2){
					item.state="<label style='color:#FF6347;font-weight: bold;'>不同意</label>";
				}else if(item.state==3){
					item.state="<label style='color:green;font-weight: bold;'>待换板</label>";
				}else if(item.state==4){
					item.state="<label style='color:red;font-weight: bold;'>不同意</label>";
				}else if(item.state==5){
					item.state="<label style='color:red;font-weight: bold;'>待返还</label>";
				}else if(item.state==6){
					item.state="<label style='color:green;font-weight: bold;'>已完成</label>";
				}
				// 保内保外
				if (item.isWarranty == 0) {
					item.isWarranty = "<label style='color:green;font-weight: bold;'>保内</label>";
				} else if (item.isWarranty == 1) {
					item.isWarranty = "<label style='color:red;font-weight: bold;'>保外</label>";
				}
			});
			$("#changeboard_DataGrid").datagrid('loadData',returnData.data);  
			getPageSize_cb("changeboard_DataGrid");
			resetCurrentPage_cb("changeboard_DataGrid", currentPageNum_cb);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		 $("#changeboard_DataGrid").datagrid('loaded');	
	}, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
 } 
  
function changeboard_next(){
	var row = $('#changeboard_DataGrid').datagrid('getSelections');
	if (!row){
		$.messager.alert("操作提示","未选中数据","info");
		return;
	}
	$.messager.confirm("操作提示","确定发送选中的数据",function(conf){						
		if(!conf){
			return;
		}
		//登录人职位
		var uPosition = $.trim($("#changeboard_uPosition").val());
		var params = new Array();
		for(var i = 0; i < row.length; i++){
			var sendflag = row[i].sendFlag;
			if(sendflag.indexOf("</label>") > -1){
				sendflag = sendflag.substring(sendflag.indexOf(">")+1, sendflag.indexOf("</label>"))
			}
			var sendFlag = 0;
			//主管批复的下一站为物料管理员（sendFlag=3，原本下一站为经理批复，现已取消2020-08-21）
			if(((sendflag == "维修主管" && uPosition.indexOf("维修主管") > -1 && uPosition.indexOf("终端技术主管") == -1 ) || (sendflag == "终端技术主管" && uPosition.indexOf("终端技术主管") > -1))){
				if(row[i].state.indexOf('待换板') == -1){
					$.messager.alert("操作提示", "此IMEI设备不符合发送条件：" + row[i].imei, "info");
					return;
				}
				//由于经理批复流程取消、主管批复后直接到物料管理员
				sendFlag = 4;
			}else if(sendflag == "客服中心经理" && uPosition.indexOf("客服中心经理") > -1){
				if(row[i].state.indexOf('待换板') == -1){
					$.messager.alert("操作提示", "此IMEI设备不符合发送条件：" + row[i].imei, "info");
					return;
				}
				sendFlag = 4;
			}else if(sendflag == "客服文员" && uPosition.indexOf("客服文员") > -1){
				$.messager.alert("操作提示", "此IMEI设备不符合发送条件：" + row[i].imei, "info");
				return;
			}else if(sendflag == "物料管理员" && uPosition.indexOf("物料管理员") > -1) {
				$.messager.alert("操作提示", "此IMEI设备不符合发送条件：" + row[i].imei, "info");
				return;
			}

			if(sendFlag == 0){
				$.messager.alert("操作提示", "此IMEI设备不符合发送条件：" + row[i].imei, "info");
				return;
			}
			var data = {
				"id" : row[i].id,
				"sendFlag" : sendFlag
			};
			params.push(data);
		}
		
		var param = {
				"changeboardStr" : JSON.stringify(params)
		}

		var url = ctx + "/changeboard/updateChangeboardBatch";
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			processSSOOrPrivilege(returnData);
			if (returnData.code != '0') {
				$.messager.alert("操作提示", "发送失败", "info");
			}
			$.messager.alert("操作提示", "发送成功", "info");
			queryChangeboardListPage(currentPageNum_cb);
			
		}, function() {
			$.messager.alert("操作提示", "网络错误", "info");
		});

	});
	
}


 function datagridInit_cb() {
	$("#changeboard_DataGrid").datagrid({
     singleSelect : false,//是否单选 
     onDblClickRow : function(rowIndex, row) {
    	 changeboard_appInfo1(rowIndex, row);
		}
	}); 
	queryChangeboardListPage(1);
 }    
 function changeboard_appInfo1(rowIndex, row){
	var param="id="+row.id;
	var url=ctx+"/changeboard/getChangeboard";
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		var sendFlag = returnData.data.sendFlag;
		var repairOrTest = returnData.data.repairOrTest;
			$("#changeboard_id").val(returnData.data.id);	
			$("#changeboard_cusName").val(returnData.data.cusName);
			$("#changeboard_imei").val(returnData.data.imei);
			$("#changeboard_model").val(returnData.data.model);
			var isWarranty = returnData.data.isWarranty;
			if(isWarranty == 0){
				isWarranty = "保内";
			}else if(isWarranty && isWarranty == 1){
				isWarranty = "保外";
			}
			$("#changeboard_repairOrTest").val(returnData.data.repairOrTest);
			$("#changeboard_isWarranty").val(isWarranty);
			$("#changeboard_number").val(returnData.data.number);
			$("#changeboard_applicater").val(returnData.data.applicater);
			$("#changeboard_appTime").val(returnData.data.appTime);
			$("#changeboard_purpose").val(returnData.data.purpose);
			$("#changeboard_charger").val(returnData.data.charger);
			$("#changeboard_chargerUpdateTime").val(returnData.data.chargerUpdateTime);
			$("#changeboard_manager").val(returnData.data.manager);
			$("#changeboard_managerUpdateTime").val(returnData.data.managerUpdateTime);
			$("#changeboard_serviceCharger").val(returnData.data.serviceCharger);
			$("#changeboard_serviceUpdateTime").val(returnData.data.serviceUpdateTime);
			$("#changeboard_testBackTime").val(returnData.data.testBackTime);
			$("#changeboard_remark").val(returnData.data.remark);
			var uPosition = $.trim($("#changeboard_uPosition").val());//登录人职位
			
			//批复的客服文员全部改为物料管理员，待经理批复表示主管同意
			if(uPosition.indexOf("终端技术主管") > -1 && sendFlag == 1 && repairOrTest == "测试" && returnData.data.state < 3) {
				$(".chargeDo").removeAttr("disabled");
			}else if(uPosition.indexOf("维修主管") > -1 && uPosition.indexOf("终端技术主管") == -1 && sendFlag == 1 && repairOrTest == "维修" && returnData.data.state < 3){
				$(".chargeDo").removeAttr("disabled");
			/*}取消客服中心经理
			else if(uPosition.indexOf("客服中心经理") > -1 && sendFlag == 2 && (returnData.data.state == 1 || returnData.data.state == 3 || returnData.data.state == 4)){
				$(".managerDo").removeAttr("disabled");

			 */
			}else if(uPosition.indexOf("物料管理员") > -1 && sendFlag == 4 && (returnData.data.state == 1 || returnData.data.state == 5 || returnData.data.state == 6)){
				$(".serviceChargerDo").removeAttr("disabled");
				if(repairOrTest == "测试"){
					$("input[name='changeboard_state'][value='5']").prop('disabled',false);
				}else{
					$("input[name='changeboard_state'][value='5']").prop('disabled','disabled');
				}
			}
			//1表示待换板和主管同意
			if(returnData.data.state=="0"){
				$("input[name='changeboard_state']").prop('checked',false);
			}else if(returnData.data.state=="1" && uPosition.indexOf("维修主管") > -1){
				$("input[name='changeboard_state'][value='1'][class='managerDo']").prop('checked',true);
			}else if(returnData.data.state=="2"){
				$("input[name='changeboard_state'][value='2']").prop('checked',true);
				/*取消经理
			}else if(returnData.data.state=="3" && uPosition.indexOf("客服中心经理") > -1 ){
				$("input[name='changeboard_state'][value='3'][class='managerDo']").prop('checked',true);
				 */
			}else if(returnData.data.state=="1" && uPosition.indexOf("物料管理员") > -1 ){
				$("input[name='changeboard_state'][value='3'][class='serviceChargerDo']").prop('checked',true);
			}else if(returnData.data.state=="4"){
				$("input[name='changeboard_state'][value='4']").prop('checked',true);
			}else if(returnData.data.state=="5"){
				$("input[name='changeboard_state'][value='5']").prop('checked',true);
			}else if(returnData.data.state=="6"){
				$("input[name='changeboard_state'][value='6']").prop('checked',true);
			}
			$("#remark").val(returnData.data.remark);
			//取消经理
			if($(".chargeDo").attr("disabled") == "disabled" /*&& $(".managerDo").attr("disabled") == "disabled" */&& $(".serviceChargerDo").attr("disabled") == "disabled"){//判断是否能修改
				$(".notShow").hide();
			}else{
				$(".notShow").show();
			}
			changeboard_update_w_open();
			chageWinSize('changeboard_update_w');
	}, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
		
}
 function changeboard_appInfo(){
	var row = $('#changeboard_DataGrid').datagrid('getSelected');
	if (row){
		var param="id="+row.id;
		var url=ctx+"/changeboard/getChangeboard";
		asyncCallService(url, 'post', false,'json', param, function(returnData){
			var sendFlag = returnData.data.sendFlag;
			var repairOrTest = returnData.data.repairOrTest;
				$("#changeboard_id").val(returnData.data.id);	
				$("#changeboard_cusName").val(returnData.data.cusName);
				$("#changeboard_imei").val(returnData.data.imei);
				$("#changeboard_model").val(returnData.data.model);
				var isWarranty = returnData.data.isWarranty;
				if(isWarranty == 0){
					isWarranty = "保内";
				}else if(isWarranty && isWarranty == 1){
					isWarranty = "保外";
				}
				$("#changeboard_repairOrTest").val(returnData.data.repairOrTest);
				$("#changeboard_isWarranty").val(isWarranty);
				$("#changeboard_number").val(returnData.data.number);
				$("#changeboard_applicater").val(returnData.data.applicater);
				$("#changeboard_appTime").val(returnData.data.appTime);
				$("#changeboard_purpose").val(returnData.data.purpose);
				$("#changeboard_charger").val(returnData.data.charger);
				$("#changeboard_chargerUpdateTime").val(returnData.data.chargerUpdateTime);
				$("#changeboard_manager").val(returnData.data.manager);
				$("#changeboard_managerUpdateTime").val(returnData.data.managerUpdateTime);
				$("#changeboard_serviceCharger").val(returnData.data.serviceCharger);
				$("#changeboard_serviceUpdateTime").val(returnData.data.serviceUpdateTime);
				$("#changeboard_testBackTime").val(returnData.data.testBackTime);
				$("#changeboard_remark").val(returnData.data.remark);
				var uPosition = $.trim($("#changeboard_uPosition").val());//登录人职位
				
				
				if(uPosition.indexOf("终端技术主管") > -1 && sendFlag == 1 && repairOrTest == "测试" && returnData.data.state < 3) {
					$(".chargeDo").removeAttr("disabled");
				}else if(uPosition.indexOf("维修主管") > -1 && uPosition.indexOf("终端技术主管") == -1 && sendFlag == 1 && repairOrTest == "维修" && returnData.data.state < 3){
					$(".chargeDo").removeAttr("disabled");
				} /*
					取消经理批复
					else if(uPosition.indexOf("客服中心经理") > -1 && sendFlag == 2 && (returnData.data.state == 1 || returnData.data.state == 3 || returnData.data.state == 4)){
					$(".managerDo").removeAttr("disabled");
					*/
				else if(uPosition.indexOf("物料管理员") > -1 && sendFlag == 4 && (returnData.data.state == 1 || returnData.data.state == 5 || returnData.data.state == 6)){
					$(".serviceChargerDo").removeAttr("disabled");
					if(repairOrTest == "测试"){
						$("input[name='changeboard_state'][value='5']").prop('disabled',false);
					}else{
						$("input[name='changeboard_state'][value='5']").prop('disabled','disabled');
					}
				}
				
				if(returnData.data.state=="0"){
					$("input[name='changeboard_state']").prop('checked',false);
				}else if(returnData.data.state=="1" && uPosition.indexOf("维修主管") > -1){
					$("input[name='changeboard_state'][value='1'][clss='chargeDo']").prop('checked',true);
				}else if(returnData.data.state=="2"){
					$("input[name='changeboard_state'][value='2']").prop('checked',true);
					/*
				}else if(returnData.data.state=="3" && uPosition.indexOf("客服中心经理") > -1 ){
					$("input[name='changeboard_state'][value='3'][class='managerDo']").prop('checked',true);
					 */
				}else if(returnData.data.state=="1" && uPosition.indexOf("物料管理员") > -1 ){
					$("input[name='changeboard_state'][value='3'][class='serviceChargerDo']").prop('checked',true);
				}else if(returnData.data.state=="4"){
					$("input[name='changeboard_state'][value='4']").prop('checked',true);
				}else if(returnData.data.state=="5"){
					$("input[name='changeboard_state'][value='5']").prop('checked',true);
				}else if(returnData.data.state=="6"){
					$("input[name='changeboard_state'][value='6']").prop('checked',true);
				}
				$("#remark").val(returnData.data.remark);
				
				if($(".chargeDo").attr("disabled") == "disabled" /*&& $(".managerDo").attr("disabled") == "disabled" */&& $(".serviceChargerDo").attr("disabled") == "disabled"){//判断是否能修改
					$(".notShow").hide();
				}else{
					$(".notShow").show();
				}
				changeboard_update_w_open();
				chageWinSize('changeboard_update_w');
		}, function(){
			 $.messager.alert("操作提示","网络错误","info");
		});
	}else{
		$.messager.alert("提示信息","请先选择所要批复的行！","info");
	} 
 }
 
 function changeboard_update() {
		var isValid = $('#changeboardUpdate_form').form('validate');
		if(isValid){
			var roleIds = $("#changeboard_roleId").val();
			var uPosition = $.trim($("#changeboard_uPosition").val());//登录人职位
			var repairOrTest = $.trim($("#changeboard_repairOrTest").val());
//			if(roleIds.length > 0){//管理员和超级管理员权限（暂时不用）
			
			
			//取消经理，添加物料管理员if(uPosition.indexOf("客服中心经理") > -1 || uPosition.indexOf("维修主管") > -1 || uPosition.indexOf("终端技术主管") > -1 || uPosition.indexOf("客服文员") > -1){维修主管、终端技术主管、客服文员和客服中心经理才能操作
			if(uPosition.indexOf("维修主管") > -1 || uPosition.indexOf("终端技术主管") > -1 || uPosition.indexOf("物料管理员") > -1){
				var state = $("input[name='changeboard_state']:checked").val();
				var param = {
						"id" : $("#changeboard_id").val(),
						"number" : $("#changeboard_number").val(),
						"state" : state,
						"remark" : $.trim($("#changeboard_remark").val())
				}
				if(state < 3 && (uPosition.indexOf("维修主管") > -1 || uPosition.indexOf("终端技术主管") > -1)){
					param['charger'] = $.trim($("#curLoginUser").val());
					param['chargerUpdateDate'] = initTime();
					//取消经理
					/*
				}else if((state == 3 || state == 4) && uPosition.indexOf("客服中心经理") > -1){
					param['manager'] = $.trim($("#curLoginUser").val());
					param['managerUpdateDate'] = initTime();
					 */
					//更换为物料管理员
				}else if(state == 1 && uPosition.indexOf("物料管理员") > -1){//待换板
					param['serviceCharger'] = $.trim($("#curLoginUser").val());
					param['serviceUpdateDate'] = "kong";//清空时间
					param['testBackDate'] = "kong";
				}else if(state == 5 && uPosition.indexOf("物料管理员") > -1 && repairOrTest == "测试"){//待返还
					param['serviceCharger'] = $.trim($("#curLoginUser").val());
					param['testBackDate'] = "kong";
					param['serviceUpdateDate'] = initTime();
				}else if(state == 6 && uPosition.indexOf("物料管理员") > -1  && repairOrTest == "测试"){
					if($.trim($("#changeboard_serviceUpdateTime").val())){
						param['serviceCharger'] = $.trim($("#curLoginUser").val());
						param['serviceUpdateDate'] = $("#changeboard_serviceUpdateTime").val();
						param['testBackDate'] = initTime();
					}else{
						$.messager.alert("操作提示", "需先确认是否换板", "info");
						return;
					}
				}else if(state == 6 && uPosition.indexOf("物料管理员") > -1 && repairOrTest == "维修"){
					param['serviceCharger'] = $.trim($("#curLoginUser").val());
					param['serviceUpdateDate'] = initTime();
				}
					
				var url = ctx + "/changeboard/updateChangeboard";
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if (returnData.code == '0') {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
							changeboard_update_w_close();
							queryChangeboardListPage(currentPageNum_cb);
						});
					} else {
						$.messager.alert("操作提示", returnData.msg, "info", function() {
						});
					}
				}, function() {
					$.messager.alert("操作提示", "网络错误", "info", function() {
					});
				});
			}else{
				$.messager.alert("操作提示", "无权限操作", "info", function(){
					changeboard_update_w_close();
				});
			}
		}
	}
 

 //获取当前登录人角色
 function getCurrentUserRole_changeboard(){
 	var url=ctx+"/rolePrivilege/getCurrentUserRoleId";
 	asyncCallService(url, 'post', false,'json', '', function(returnData){		
 		if(returnData.code==undefined){
 			var getRoleIdListData = returnData.data;
 			if(getRoleIdListData.indexOf('25') > -1 || getRoleIdListData.indexOf('96') > -1){
 				$("#changeboard_roleId").val(getRoleIdListData);
 			}
 		}else {
 			$.messager.alert("操作提示",returnData.msg,"info");
 		}
 			
 	}, function(){
 		 	$.messager.alert("操作提示","网络错误","info");
 	});
 }
 
//获取当前登录人职位
 function getCurrentUserPosition_changeboard(){
 	var url=ctx+"/user/SelectUidByUname";
	var param="uId="+$.trim($("#curLoginUserId").val());
	asyncCallService(url, 'post', false,'json', param, function(ssoReturnData){
		processSSOOrPrivilege(ssoReturnData);
		if(ssoReturnData.code==undefined){
			$("#changeboard_uPosition").val(ssoReturnData.data[0].uPosition);
		}else {
			$.messager.alert("操作提示",ssoReturnData.msg,"info");
		}
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
 }
 
//打开批复窗口
 function changeboard_update_w_open(){
 	$(".validatebox-tip").remove();
 	$(".validatebox-invalid").removeClass("validatebox-invalid");
 	$('#changeboard_update_w').window('open');
 	windowVisible("changeboard_update_w");
 	getCurrentUserRole_changeboard();
 }
 
 function changeboard_update_w_close(){
	windowCommClose('changeboard_update_w');
	$(".chargeDo").attr('disabled','disabled');
 	$(".managerDo").attr('disabled','disabled');
 	$(".serviceChargerDo").attr('disabled','disabled');
 	$(".notShow").hide();
 }
 
 function changeboard_refreshInfo(){
	 queryChangeboardListPage();
 }
 
 function changeboard_exportInfo(){
	var url =  ctx + "/changeboard/ExportDatas";
	var $form1=$("<form action='"+url+"' target='_blank' id='hidenForm' method='post'></form>");
	$form1.append($("<input type='hidden' name='applicater' value='" + $.trim($("#searchByApplicater").val()) +"'/>"));
	$form1.append($("<input type='hidden' name='repairOrTest' value='" + $("#searchByRepairOrTest option:selected").val() +"'/>"));
	$form1.append($("<input type='hidden' name='state' value='" + $("#searchByState option:selected").val() +"'/>"));
	$form1.append($("<input type='hidden' name='startDate' value='" + $("#startTime").val() +"'/>")); 
	$form1.append($("<input type='hidden' name='endDate' value='" + $("#endTime").val() +"'/>")); 
	$("body").append($form1);
	$form1.submit();
	$form1.remove(); 
 }
/* ==========================================保内对换主板审批 end======================================== */
