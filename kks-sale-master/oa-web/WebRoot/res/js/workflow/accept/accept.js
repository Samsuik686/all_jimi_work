$.messager.defaults= { ok: "确定",cancel: "取消" };
var currentPageNum;
var up_cusNormal;
var acc_number = 0;//批量受理表格行数

$(function(){    
    datagridInit();
    keySearch(queryListPage);
    $('.panel-tool').hide();
    window.onresize = function () {
    	//调整浏览器窗口大小时，弹出框的分页会恢复成默认的数值，
    	setTimeout(function(){
    		if($("#customerModal").val()){
    			getPageSizeSXDW();
    		}else if($("#faultModal").val()){
    			getPageSizeCJGZ();
    		}else if($("#fileModal").val()){
    			getPageSizeSJFJ();
    		}
    	},500);
	}
    
    //批量操作是否货到付款选项
    $('.pay').live('change',function(){
    	if ($("#scan_tbody tr").length > 0) {
    		for (var j = 1; j <= rowindex; j++) {
    			var d = $("input[name='payDelivery-"+j+"']").prop('disabled');//同一批次的再次受理
    			if(!d){
    				$("input[name='payDelivery-"+j+"'][value='" + $(this).val() + "']").prop("checked", "checked");
    			}
    		}
    	}
    	
	});
       
    //根据客户状态提供客户选择方式
    $('.cus_status').live('change',function(){
    	if ($("#scan_tbody tr").length > 0) {
    		var cusVal = $(this).val();
    		for (var i = 1; i <= rowindex; i++) {
    			$("input[name='customerStatus-"+i+"'][value='" + cusVal + "']").prop("checked", "checked");   
    			if(cusVal == 'normal'){
        			//正常
    				$("#cusName-"+i).attr("onclick","chooseCustomer("+i+")");
    			}
    		}
    		if(cusVal == 'normal'){
    			//正常
    			$("input[name='customerName']").val("");
    			$("input[name='repairnNumber']").val("");
    		}else{
    			//无名件
    			$("input[name='customerName']").removeAttr("onclick");    	
    			$("input[name='customerName']").removeAttr("readonly");
    			$("input[name='customerName']").attr("ondblclick","copyCustomer(this)");
    			$("input[name='customerName']").val("");
    			$("input[name='repairnNumber']").val("");
    			
    		}
    	}
    });
    
    $('#searchinfo').click(function(){
    	$('#tree-Date').val("");
    	$("#tree-State").val("");
    	queryListPage(1);  
	   });
    $("input[name='scan_lockId']").focus(function(){
    	$("input[name='scan_imei']").blur();
    });
    
    $("input[name='scan_imei']").focus(function(){
    	$("input[name='scan_lockId']").blur();
    });
}); 

//双击查询出客户名称和送修批号并复制到文本框
function copyCustomer(cus){	
	var cusVal = $(cus).val();
	var v = new Date();
	var s = v.getTime();
	$("input[name='customerName']").val(cusVal);
	$("input[name='repairnNumber']").val(s);
}

//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ START
/**
 * 初始化 表单工具栏面板
 */
function datagridInit(){
	//受理列表工具栏
	$("#DataGrid1").datagrid({
		singleSelect:false,	
		onDblClickRow:function(rowIndex,row){
			InfoUpdate(rowIndex,row);
		}
	});
	queryListPage(1);  
	
	$("#DataGrid-Customer").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	 dbClickChooseCustomer(index,value);
	 	 }
	}); 
	
	//选择附件
	$("#table_sjfj").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	dbClickChooseSJFJ(index,value);
	 	}
	}); 
	
	//选择初检故障
	$("#table_gz").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	dbClickChooseCJGZ(index,value);
	 	 }
	}); 
	
	//选择主板型号
	$("#DataGrid-Zbxh").datagrid({
	     singleSelect:true,
	     onDblClickRow: function(index,value){
	    	 dbClickChooseZbxh(index,value);
	 	 }
	}); 
}
//--------------------------------------------- 初始化 》工具栏  ------------------------------------------ END
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
//--------------------------------------------- 受理列表管理  ------------------------------------------ START
/**
 * 初始化受理列表
 * 初始化数据及查询函数
 * @param pageNumber  当前页数
 */
function queryListPage(pageNumber){
	currentPageNum=pageNumber;
	if(currentPageNum=="" || currentPageNum==null){
		currentPageNum=1;
	}
	getTreeNode();
	var state = $("#state").combobox('getValue');
	if(!$("#tree-Date").val() && state == '2' && (!$("#startTime").val() || !$("#endTime").val())){
		$.messager.alert("操作提示","查询已完成数据请选择开始，结束日期","info");
		return;
	}
	var url=ctx+"/workflowcon/acceptList";
    var selParams="imei="+$.trim($("#imei").val())
    			+"&lockId=" + $.trim($("#lockId").val())
    			+"&state="+state
    			+"&startTime="+$("#startTime").val() 
    			+"&endTime="+$("#endTime").val() 
    			+"&treeDate="+$("#tree-Date").val()
    			+"&isWarranty="+$("#isWarranty").combobox('getValue') 
    			+"&w_cusName="+$.trim($("#w_cusName").val())
    			+"&repairnNmber="+$.trim($("#repairnNmber").val())
    			+"&customerStatus="+$("#customerStatus").combobox('getValue');     
    var param = addPageParam('DataGrid1', currentPageNum, selParams);
    $("#DataGrid1").datagrid('loading');
	asyncCallService(url, 'post', false,'json', param, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code==undefined){ 
			$.each(returnData.data,function(i, item){
				//距受理时间1天，受理时间显示黄色，两天，红色
				if(item.state == "0" || item.state == "15" || item.state == "17"|| item.state == "19"){//已受理 或 测试中
					var t =	getDayFromAcceptanceTime(item.acceptanceTime);
					if(t == 1){
						item.acceptanceTime = "<label style='background-color:#FFEC8B;'>" + item.acceptanceTime + "</label>";
					}else if(t == 2){
						item.acceptanceTime = "<label style='background-color:#FF6347;'>" + item.acceptanceTime + "</label>";
					}					
				}
				//保内/保外
				if(item.isWarranty==0){
					item.isWarranty="保内";
				}else{
					item.isWarranty="保外";
				}
				//状态
				if(item.state == "0"){
					item.state="<label style='color:green;font-weight: bold;'>已受理</label>";
				} else if(item.state == "17"){
					item.state="<label style='color:blue;font-weight: bold;'>已受理,已测试</label>";
				} else if(item.state == "19"){
					item.state="<label style='color:blue;font-weight: bold;'>不报价，测试中</label>";
				} else if (item.state == "15") {
					item.state="<label style='color:blue;font-weight: bold;'>测试中</label>";
				} else {
					item.state="<label style='color:green;font-weight: bold;'>已完成</label>";
				}
				if(item.customerStatus=="un_normal"){
					item.customerStatus="<label style='color:red;font-weight: bold;'>无名件</label>";
				}else{
					item.customerStatus="<label style='font-weight: bold;'>正常</label>";
				}
				if(!item.w_cusName){
					item.w_cusName = item.cusName;
				}
				
				if (item.testResult) {
					item.testResult = "<span title="+item.testResult+">"+item.testResult+"</span>";
				}
			});
			$("#DataGrid1").datagrid('loadData',returnData.data);  
			getPageSize();
			resetCurrentPageShow(currentPageNum);
		}else {
			$.messager.alert("操作提示",returnData.msg,"info");
		}
		$("#DataGrid1").datagrid('loaded');	
	}, function(){
		 	$.messager.alert("操作提示","网络错误","info");
	});
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
		$("#state").combobox('setValue','2');
	}else if(treeState=='1'){
		$("#state").combobox('setValue','0');
	}
}

/**
 * 清除受理表单数据
 */
function clearFromParams()
{
	//TODO 数据展示
	//--产品信息
	$("#fm_id").val("");
	$("#fm_rfild").val("");
	$("#fm_imei").val("");
	$("#fm_lockId").val("");
	$("#fm_bluetoothId").val("");
	$("#fm_salesTime").datebox("setValue","");
	$("#fm_w_model").val("");
	$("#fm_w_marketModel").val("");
	$("#fm_isWarranty").val("");
	/////// 随机附件
	$("#fm_w_sjfjname").val("");
	$("#fm_w_sjfjId").val("");
	
	//--客户信息
	$("#fm_w_cusName").val("");
	$("#fm_w_linkman").val(""); 
	$("#fm_w_phone").val("");
	$("#fm_w_telephone").val("");
	$("#fm_w_email").val("");
	$("#fm_w_fax").val("");
	$("#fm_w_address").val("");
	
	//--受理信息
	$("#fm_repairnNmber").val("");
	$("#fm_accepter").val("");
	$("#fm_acceptanceTime").datebox("setValue","");
	$("#fm_returnTimes").val("");
	$("#fm_state").val("");
	///// 初检故障
	$("#fm_w_initheckFault").val("");
	$("#fm_w_cjgzId").val("");
	$("#fm_remark").val("");
	$("#fm_acceptRemark").val("");
	$("#fm_testResult").val("");
	
	$("#fm_bill").val("");
	$("#fm_sfVersion").val("");
	$("#fm_insideSoftModel").val("");
	$("#fm_outCount").val("");
	$("#fm_testMatStatus").val("");
}

/**
 * 是否同步刷新
 * @param reqAjaxPrams
 */
function processSaveAjax(reqAjaxPrams)
{
	console.log(reqAjaxPrams);
	var url=ctx+"/workflowcon/addOrUpdateInfo";
	asyncCallService(url, 'post', false,'json', reqAjaxPrams, function(returnData){
		processSSOOrPrivilege(returnData);
		if(returnData.code=='0'){
		    $.messager.alert("操作提示",returnData.msg,"info",function(){
		    	queryListPage(currentPageNum);

		    	windowCommClose('wupdate');
		   });
		}else {
			 $.messager.alert("操作提示",returnData.msg,"info");
		}
	}, function(){
		 $.messager.alert("操作提示","网络错误","info");
	});
}

/**
 * 新增信息 
 */ 
function InfoSave(){

	var reqAjaxPrams={
			"Id"           : $.trim($("#fm_id").val()),
			"rfild"        : $.trim($("#fm_rfild").val()),
			"imei"         : $.trim($.trim($("#fm_imei").val())),
			"w_sjfj"       : $.trim($("#fm_w_sjfjId").val()),
			"w_sjfjDesc"   : $.trim($("#fm_w_sjfjname").val()),
			"w_cjgzId"     : $.trim($("#fm_w_cjgzId").val()),
			"w_cjgzDesc"   : $.trim($("#fm_w_initheckFault").val()),
			"repairnNmber" : $.trim($("#fm_repairnNmber").val()),
			"remark"       : $.trim($("#fm_remark").val()),
			"acceptRemark" : $.trim($("#fm_acceptRemark").val()),
			"xhId"         : $.trim($("#fm_xhId").val()), //修改主板型号
			"lockId"		:$.trim($("#fm_lockId").val())
	};
	if(up_cusNormal == 'un_normal'){
		reqAjaxPrams.w_cusName = $.trim($("#fm_w_cusName").val());
		reqAjaxPrams.w_linkman = $.trim($("#fm_w_linkman").val());
		reqAjaxPrams.w_phone = $.trim($("#fm_w_phone").val());
		reqAjaxPrams.w_telephone = $.trim($("#fm_w_telephone").val());
		reqAjaxPrams.w_email = $.trim($("#fm_w_email").val());
		reqAjaxPrams.w_fax = $.trim($("#fm_w_fax").val());
		reqAjaxPrams.w_address = $.trim($("#fm_w_address").val());
		reqAjaxPrams.customerStatus = $.trim($("input[name='customerStatus']:checked ").val());
		if($("input[name='customerStatus']:checked ").val() == 'normal'){//无名件变成正常件
			reqAjaxPrams.accTime = "accTime";
		}
	}
	var isValid = $('#cusForm').form('validate');
	if(isValid){
		if(($.trim($("#fm_imei").val())).length != 15){
			$.messager.alert("提示信息","IMEI长度必须是15位！","warning");
			return;
		}
		var isWarranty = $("#fm_isWarranty").val();
		if(isWarranty){
			if(isWarranty == "保内"){
				isWarranty = 0;
			}else if(isWarranty == "保外"){
				isWarranty = 1;
			}
			reqAjaxPrams.isWarranty = isWarranty;
		}
		processSaveAjax(reqAjaxPrams);
	}
	
}

/**
 * 弹出修改框
 */
function InfoUpdate(rowIndex,entity)
{
	windowVisible("wupdate");
	clearFromParams();
	$("#hideSindex").val("");
	if(entity){
		var state = entity.state.substring(entity.state.indexOf(">")+1,entity.state.indexOf("</label>")).trim();		
		if(state.indexOf("已完成")>=0){
			$(".acc_hide").hide();
		}else{
			$(".acc_hide").show();
		}
		windowCommOpen("wupdate");
		loadEntity(entity);
		
	}else{
		var entity = $('#DataGrid1').datagrid('getSelections');
		if (entity.length == 1){
			var state = entity[0].state.substring(entity[0].state.indexOf(">")+1,entity[0].state.indexOf("</label>")).trim()
			if(state.indexOf("已完成")>=0){
				$(".acc_hide").hide();
			}else{
				$(".acc_hide").show();
			}
			windowCommOpen("wupdate");
			loadEntity(entity[0]);
			
		}else if(entity.length == 0){
			$.messager.alert("提示信息","请先选择所要修改的行！","info");
		}else{
			$.messager.alert("提示信息","请先选择一行进行修改！","info");
		}
	}
	
}

function loadEntity(entity){
	//TODO 数据展示
	//--产品信息
	$("#fm_id").val(entity.id);
	$("#fm_rfild").val(entity.rfild);
	$("#fm_imei").val(entity.imei);
	$("#fm_lockId").val(entity.lockId);
	$("#fm_bluetoothId").val(entity.bluetoothId);
	$("#fm_salesTime").datebox("setValue",entity.salesTime);
	var model= entity.w_model.split('|');
	$("#fm_w_model").val(model[0]);
	$("#fm_w_marketModel").val(entity.w_marketModel);
	$("#fm_xhId").val(entity.xhId);
	
	$("#fm_isWarranty").val(entity.isWarranty);
	/////////	|随机附件
	$("#fm_w_sjfjname").val(entity.w_sjfjDesc);
	$("#fm_w_sjfjId").val(entity.w_sjfj);
	
	//--客户信息
	$("#fm_w_cusName").val(entity.w_cusName);
	$("#fm_w_linkman").val(entity.w_linkman); 
	$("#fm_w_phone").val(entity.w_phone);
	$("#fm_w_telephone").val(entity.w_telephone);
	$("#fm_w_email").val(entity.w_email);
	$("#fm_w_fax").val(entity.w_fax);
	$("#fm_w_address").val(entity.w_address);
	$("#fm_w_sxdwId").val(entity.sxdwId);
	
	//--受理信息
	$("#fm_repairnNmber").val(entity.repairnNmber);
	$("#fm_accepter").val(entity.accepter);
	if(entity.acceptanceTime.indexOf(">") != -1){
		$("#fm_acceptanceTime").datebox("setValue", entity.acceptanceTime.substring(entity.acceptanceTime.indexOf(">")+1,entity.acceptanceTime.indexOf("</label>")).trim());
	}else{
		$("#fm_acceptanceTime").datebox("setValue", entity.acceptanceTime);
	}
	$("#fm_returnTimes").val(entity.returnTimes+"次");
	$("#fm_state").val(entity.state.substring(entity.state.indexOf(">")+1,entity.state.indexOf("</label>")).trim());
	////////  |初检故障
	$("#fm_w_initheckFault").val(entity.w_cjgzDesc);
	$("#fm_w_cjgzId").val(entity.w_cjgzId);
	$("#fm_remark").val(entity.remark);
	$("#fm_acceptRemark").val(entity.acceptRemark);
	
	$("#fm_bill").val(entity.bill);
	$("#fm_sfVersion").val(entity.sfVersion);
	$("#fm_insideSoftModel").val(entity.insideSoftModel);
	$("#fm_outCount").val(entity.outCount);
	$("#fm_testMatStatus").val(entity.testMatStatus);
	if(entity.testResult){
		var testResult = entity.testResult.substring(entity.testResult.indexOf(">")+1,entity.testResult.indexOf("</span>"))
		$("#fm_testResult").val(testResult);
	}
	var cusStatus = entity.customerStatus;
	var normal = cusStatus.substring(cusStatus.indexOf('>')+1,cusStatus.length-8);
	if(normal == '无名件'){
		$('#fm_w_cusName').removeAttr('disabled');
		$('#fm_w_linkman').removeAttr("disabled");
		$('#fm_w_phone').removeAttr("disabled");
		$('#fm_w_telephone').removeAttr("disabled");
		$('#fm_w_email').removeAttr("disabled");
		$('#fm_w_fax').removeAttr("disabled");
		$('#fm_w_address').removeAttr("disabled");	
		$('#fm_w_cusName').attr("onclick","chooseCustomer('')");			
		$("input[name='customerStatus'][value='un_normal']").prop("checked", "checked");		
		$("input[name='customerStatus']").removeAttr("disabled");
		up_cusNormal = 'un_normal';
	}else{
		$("input[name='customerStatus'][value='normal']").prop("checked", "checked");
		$('#fm_w_cusName').attr('disabled','disabled');
		$('#fm_w_linkman').attr('disabled','disabled');
		$('#fm_w_phone').attr('disabled','disabled');
		$('#fm_w_telephone').attr('disabled','disabled');
		$('#fm_w_email').attr('disabled','disabled');
		$('#fm_w_fax').attr('disabled','disabled');
		$('#fm_w_address').attr('disabled','disabled');
		$("input[name='customerStatus']").attr('disabled','disabled');
		up_cusNormal = 'normal';
	}
	
}

/**
 * 删除
 */
function InfoDelete()
{
	var deleteInfo = $('#DataGrid1').datagrid('getSelections');
	if (deleteInfo.length>0)
	{
	  $.messager.confirm("操作提示","是否删除此信息",function(conf){
		if(conf)
		{
    		var ids = "";
			for (var int = 0; int < deleteInfo.length; int++) {
				var state = deleteInfo[int].state.substring(deleteInfo[int].state.indexOf(">")+1,deleteInfo[int].state.indexOf("</label>")).trim()		
				if(state == '已完成'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+deleteInfo[int].imei+"</font>是已完成的数据禁止操作","info");
					return;
				}
				if(state == '测试中'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+deleteInfo[int].imei+"</font>是测试中的数据禁止操作","info");
					return;
				}
				if(state == '不报价，测试中'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+deleteInfo[int].imei+"</font>是测试中的数据禁止操作","info");
					return;
				}
				(int ==0)?ids = deleteInfo[int].id:ids = deleteInfo[int].id+","+ids;
			}
			var url=ctx+"/workflowcon/deleteInfo";
		    var param="ids="+ids; 
			asyncCallService(url, 'post', false,'json', param, function(returnData)
			{
    			processSSOOrPrivilege(returnData);
    			if(returnData.code=='0'){
    				queryListPage(currentPageNum);
    				$.messager.alert("操作提示","操作成功","info");
    			}else{
				 	$.messager.alert("操作提示",returnData.msg,"info");
    			}
			 	}, function(){
			 		$.messager.alert("操作提示","网络错误","info");
			});
	        $('#DataGrid1').datagrid('acceptChanges');  
		   }
	  });
	}else{
		$.messager.alert("提示信息","请先选择所要删除的行！","info");
	}
}

/**
 * 发送分拣
 * 
 * @author Li.Shangzhi
 * @Date 2016-09-06 11:02:00
 */
function sendSort()
{
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	var all = true;
	var marked_words="";
	if (SendInfo.length>0){
		marked_words = "确定要发送<font color='red'>选中数据</font>到分拣？";
		all = false;
	}else{
		//一键发送符合条件的数据到分拣
		marked_words = "确定要发送所有<font color='red'>已受理，正常</font>的数据到分拣？";
		SendInfo = $("#DataGrid1").datagrid("getRows"); //获取当前页面所有数据
	}
	  $.messager.confirm("操作提示",marked_words,function(conf){
		if(conf)
		{
			var ids = "";
			for (var int = 0; int < SendInfo.length; int++) {	
				var cusStatus = SendInfo[int].customerStatus;
				var normal = cusStatus.substring(cusStatus.indexOf('>')+1,cusStatus.length-8);
				if(normal == '无名件' && !all){
					$.messager.alert("操作提示","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是<font color='red'>无名件</font>不允许发送到分拣！","info");
					return;
				}
				var state = SendInfo[int].state.substring(SendInfo[int].state.indexOf(">")+1,SendInfo[int].state.indexOf("</label>")).trim()		
				if(state == '已完成'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是已完成的数据禁止操作","info");
					return;
				} else if (state == '测试中') {
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>的数据正在测试禁止发送到分拣","info");
					return;
				} else if (state == '不报价，测试中') {
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>的数据正在测试禁止发送到分拣","info");
					return;
				}
				if(normal != '无名件'){
					//一键发送时，无名件ID不append进去
					(int ==0)?ids = SendInfo[int].id:ids = SendInfo[int].id+","+ids;
				}
				
			}
			if(ids){
				var url=ctx+"/workflowcon/sendSort";
			    var param="ids="+ids; 
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送分拣成功！","info");
						queryListPage(currentPageNum);            	
					}else {
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}, function(){
					 	$.messager.alert("操作提示","网络错误","info");
				});
			}else{
				$.messager.alert("操作提示","未找到匹配数据","info");
			}
			
		 }
	  });
	
}

// 发送到测试工站
function sendWorkTest(){	
	var SendInfo = $('#DataGrid1').datagrid('getSelections');
	var marked_words = "确定要发送<font color='red'>选中数据</font>到测试？";
	$.messager.confirm("操作提示",marked_words,function(conf){
		if(conf)
		{
			var ids = "";
			for (var int = 0; int < SendInfo.length; int++) {	
				var cusStatus = SendInfo[int].customerStatus;
				var normal = cusStatus.substring(cusStatus.indexOf('>')+1,cusStatus.length-8);
				if(normal == '无名件'){
					$.messager.alert("操作提示","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是<font color='red'>无名件</font>不允许发送到测试！","info");
					return;
				}
				var state = SendInfo[int].state.substring(SendInfo[int].state.indexOf(">")+1,SendInfo[int].state.indexOf("</label>")).trim()		
				if(state == '已完成'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>是已完成的数据禁止操作","info");
					return;
				} else if(state == '测试中'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>的数据正在测试禁止发送","info");
					return;
				} else if(state == '不报价，测试中'){
					$.messager.alert("提示信息","IMEI为<font color='red'>"+SendInfo[int].imei+"</font>的数据正在测试禁止发送","info");
					return;
				}
				if(normal != '无名件'){
					//一键发送时，无名件ID不append进去
					(int ==0)?ids = SendInfo[int].id:ids = SendInfo[int].id+","+ids;
				}
				
			}
			if(ids){
				var testuser = $("#test_user option:selected").val();
				var url=ctx+"/workflowcon/sendWorkTest";
			    var param = {
		    		"ids" : ids,
		    		"dataSource" : "受理站",
		    		"testStatus" : "0",			//已受理，测试中
		    		"testPerson" : testuser
			    }; 
				asyncCallService(url, 'post', false,'json', param, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0'){
						$.messager.alert("操作提示","发送测试成功！","info");
						windowCommClose('testUser_w');
						queryListPage(currentPageNum);            	
					}else {
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}, function(){
					 	$.messager.alert("操作提示","网络错误","info");
				});
			}else{
				$.messager.alert("操作提示","未找到匹配数据","info");
			}
			
		 }
	  });		
	
}

//--------------------------------------------- 受理列表管理  ------------------------------------------  END

//--------------------------------------------- SCAN 扫描受理   V1.1 ---------------------------------------- START
/**
 * 版本记录
 * @author Li.Shangzhi
 * @date 2016-08-01 09:29:15
 * @version 1.1
 */
var  CommScanBatchMap={};


/**
 * 打开扫描窗口
 * 初始化数据
 */
function openScanWindow()
{
	windowVisible("wscan");
	clearScanWindowDatas();
	windowCommOpen("wscan");
	chageWinSize('wscan');
	delRows = [];
	allRows = [];
	acc_number=0;
	$("#acc_number").html(0);

}

/**
* 清除Scan扫描窗口的数据
*/
function clearScanWindowDatas()
{
  $("#scan_tbody").html("");
  CommScanBatchMap={};
  rowindex=0;
}

/**
 * 新增扫描行
 */
var rowindex = 0;  //初始化行数

/**
 * 新增扫描行
 */
function addScanRow(){
	acc_number++;
	$("#acc_number").html(acc_number);
	rowindex++; 	
	allRows.push(rowindex);
	var tr_html='<tr>'
					  +'<td align="center" style="width:30px;"><input type="checkbox" name="ScanCheckBox" id="'+rowindex+'"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="imei-'+rowindex+'" oninput="synchDatas('+rowindex+')"  name="scan_imei"/></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:160px;" id="cusName-'+rowindex+'" name="customerName" onclick="chooseCustomer('+rowindex+','+allRows+')"/><input type="hidden" id="sxdwId-'+rowindex+'" name="sxdwId"/><img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copyCusName('+rowindex+')"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="marketModel-'+rowindex+'" disabled="disabled"/></td>'
					  +'<td align="center" style="width:165px;"><input type="text" style="width:140px;" id="model-'+rowindex+'" onclick="chooseZbxh('+rowindex+')"/><input type="hidden" id="xhId-'+rowindex+'" name="xhId"/><img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copyModel('+rowindex+')"/></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:180px;" id="sfVersion-'+rowindex+'" disabled="disabled"/></td>'
					  +'<td align="center" style="width:90px;"><input type="text" style="width:85px;" id="isWarranty-'+rowindex+'" disabled="disabled" placeholder="保内或保外"/></td>'
					  +'<td align="center" style="width:165px;"><input type="text" style="width:160px;" id="salesTime-'+rowindex+'" disabled="disabled" placeholder="2016-01-01 00:00:00"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" style="width:130px;" id="lastEngineer-'+rowindex+'" disabled="disabled" style="text-align: center;" /></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" style="width:130px;" id="lastAccTime-'+rowindex+'" disabled="disabled" style="text-align: center;" /></td>'
					  +'<td align="center" style="width:90px;"><input type="text" style="width:85px;" id="returnTimes-'+rowindex+'" disabled="disabled" style="text-align: center;" /></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:160px;" id="initheckFault-'+rowindex+'" name="initheckFault" onclick="chooseCjgz('+rowindex+')" readonly="readonly"/><input type="hidden" id="cjgzId-'+rowindex+'" name="cjgzId" /><img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copyCjgz('+rowindex+')"/></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:160px;" id="remark-'+rowindex+'" name="sxRemark"/> <img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copySxRemark('+rowindex+')"/></td>' 
					  +'<td align="center" style="width:185px;"><input type="text" style="width:180px;" id="lockInfo-'+rowindex+'" onkeypress="if(event.keyCode==13) {matchLongStr('+rowindex+')}" name="lockInfo"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:115px;" id="lockId-'+rowindex+'" name="scan_lockId"/><img src="'+ctx+'/res/js/themes/icons/search.png" onclick="synchDatas('+rowindex+')"/></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:160px;" id="sjfjname-'+rowindex+'" name="sjfjName" onclick="chooseSjfj('+rowindex+')" readonly="readonly"/><input type="hidden" id="sjfjId-'+rowindex+'" name="sjfjId" /><img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copySjfj('+rowindex+')"/></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:180px;" id="acceptRemark-'+rowindex+'" /></td>' 
					  +'<td align="center" style="width:185px;"><input type="text" style="width:160px;" id="repairNumberRemark-'+rowindex+'" name="repairNumberRemark"/> <img src="'+ctx+'/res/js/themes/icons/mlzjicon.png" onclick="copyRepairNumberRemark('+rowindex+')"/></td>' 
					  +'<td align="center" style="width:135px;"><span><input type="radio" style="width:15px;height:15px;" class="pay" name="payDelivery-'+rowindex+'" value="N" checked="checked"/>顺付&nbsp;</span><span><input type="radio" style="width:15px;height:15px;" class="pay" name="payDelivery-'+rowindex+'" value="Y"/>到付&nbsp;</span></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="bluetoothId-'+rowindex+'"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="repairnNmber-'+rowindex+'" name="repairnNumber" disabled="disabled"/></td>'
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="insideSoftModel-'+rowindex+'"/></td>'
					  +'<td align="center" style="width:135px;"><span ><input type="radio" style="width:15px;height:15px;" class="cus_status" name="customerStatus-'+rowindex+'" value="normal" checked="checked"/>正常&nbsp;</span><span><input type="radio" style="width:15px;height:15px;" class="cus_status" name="customerStatus-'+rowindex+'" value="un_normal"/>无名件&nbsp;</span></td>'
					  +'<td align="center" style="width:185px;"><input type="text" style="width:180px;" id="expressCompany-'+rowindex+'" placeholder="寄无名件快递公司" /></td>' 
					  +'<td align="center" style="width:185px;"><input type="text" style="width:180px;" id="expressNO-'+rowindex+'" placeholder="寄无名件快递单号" /></td>' 
					  +'<td align="center" style="width:135px;"><input type="text" style="width:130px;" id="bill-'+rowindex+'" disabled="disabled"/></td>'
					  +'<td align="center" style="width:90px;"><input type="text" style="width:85px;" id="outCount-'+rowindex+'" disabled="disabled"/></td>'
					  +'<td align="center" style="width:90px;"><input type="text" style="width:85px;" id="testMatStatus-'+rowindex+'" disabled="disabled"/></td>'
		              +'<td align="center" style="width:135px;"><input type="text" style="width:135px;" id="backTime-'+rowindex+'"/></td>'
					  +'<td align="center" style="display:none"><input type="hidden" id="searchByMarketmodel-'+rowindex+'" /></td>' 
					  +'<td align="center" style="display:none"><input type="hidden" id="ams_model-'+rowindex+'" /></td>' 
					  +'<td align="center" style="display:none"><input type="hidden" id="pricedCount-'+rowindex+'" /></td>' 
					  +'<td align="center" style="display:none"><input type="hidden" id="oldRepairNumber-'+rowindex+'" name="oldRepairNumber"/></td>' 
			  +'</tr>'	  
	$("#scan_tbody").append(tr_html);
	if(allRows.length > 1 ){
		var min = Math.min.apply(null, allRows);
		var v = $("input[name='payDelivery-"+min+"']:checked ").val();
		var c = $("input[name='customerStatus-"+min+"']:checked ").val();
		$("input[name='payDelivery-"+rowindex+"'][value='"+v+"']").prop("checked", "checked");
		$("input[name='customerStatus-"+rowindex+"'][value='"+c+"']").prop("checked", "checked");
		if(c == 'normal'){
			//正常
			$("#cusName-"+rowindex).attr("onclick","chooseCustomer("+rowindex+")");
		}else{
			//无名件
			$("#cusName-"+rowindex).removeAttr("onclick");    	
			$("#cusName-"+rowindex).removeAttr("readonly");
		}
	}
}
/**
 * 获取临时数据
 * @returns
 */
function getTempData(){
	if (acc_number > 0){
		$.messager.alert("操作提示","获取保存数据前请删除现有记录","info");
		return;
	}
	
	var url = ctx + "/workflowcon/getLinshiData";							
	$.ajax({
		url : url,
		type : 'get',
		async : false,
		dataType : 'json',
		data : null,
		cache : false,
		success : function(returnData) {
			var array = returnData.data;
			if(array.length < 1){
				$.messager.alert("操作提示","没有临时保存的数据","info");
				return;
			}
			
			for (var i = 0; i < array.length; i++) {
				addScanRow();
				var index = rowindex;
				$("#lockId-" + index).val(array[i].lockId);
				$("#lockInfo-" + index).val(array[i].lockInfo);//智能锁ID二维码信息
				$("#bluetoothId-" + index).val(array[i].bluetoothId);
				$("#imei-" + index).val(array[i].imei);
				$("#xhId-" + index).val(array[i].xhId);
				$("#cjgzId-" + index).val(array[i].w_cjgzId);
				$("#sjfjId-" + index).val(array[i].w_sjfjId);
				$("#repairnNmber-" + index).val(array[i].repairnNmber);
				
				if (array[i].isWarranty == '0'){
					array[i].isWarranty = '保内';
				}
				if (array[i].isWarranty == '1'){
					array[i].isWarranty = '保外';
				}
				$("#isWarranty-" + index).val(array[i].isWarranty);
				
				$("#returnTimes-" + index).val(array[i].returnTimes);
				$("#lastEngineer-" + index).val(array[i].lastEngineer);
				$("#lastAccTime-" + index).val(array[i].lastAccTime);
				
				$("#salesTime-" + index).val(array[i].salesTime);
				
				if(array[i].payDelivery == 'N') {
					$("input:radio[name='payDelivery-"+index+"'][value='N']").attr('checked','true');
				}
				if(array[i].payDelivery == 'Y') {
					$("input:radio[name='payDelivery-"+index+"'][value='Y']").attr('checked','true');
				}
				
				if(array[i].customerStatus == 'normal') {
					$("input:radio[name='customerStatus-"+index+"'][value='normal']").attr('checked','true');
				} 
				if(array[i].customerStatus == 'un_normal') {
					$("input:radio[name='customerStatus-"+index+"'][value='un_normal']").attr('checked','true');
				}
				
				$("#sxdwId-" + index).val(array[i].sxdwId);
				$("#remark-" + index).val(array[i].remark);	
				$("#acceptRemark-" + index).val(array[i].acceptRemark);	
				$("#repairNumberRemark-" + index).val(array[i].repairNumberRemark);
				$("#cusName-" + index).val(array[i].cusName);
				$("#expressCompany-" + index).val(array[i].expressCompany);
				$("#expressNO-" + index).val(array[i].expressNo);
				
				$("#bill-" + index).val(array[i].bill);
				$("#sfVersion-" + index).val(array[i].sfVersion);
				$("#insideSoftModel-" + index).val(array[i].insideSoftModel);
				
				$("#outCount-" + index).val(array[i].outCount);
				$("#testMatStatus-" + index).val(array[i].testMatStatus);
				$("#backTime-" + index).val(array[i].backTime);
				
				$("#model-" + index).val(array[i].model);
				$("#marketModel-" + index).val(array[i].marketModel);
				$("#initheckFault-" + index).val(array[i].initheckFault);
				$("#sjfjname-" + index).val(array[i].sjfjname);
			}
		},
		error : function() {
			$.messager.alert("操作提示","网络错误","info");
		}
	});
}

// 删除
function deleteTempData(flag){
	var url = ctx + "/workflowcon/deleteLinshiData";							
	$.ajax({
		url : url,
		type : 'get',
		async : false,
		dataType : 'json',
		data : null,
		cache : false,
		success : function(returnData) {
			if(returnData.code == 0){
				if(flag != 0){
					$.messager.alert("操作提示","临时保存数据已删除","info");
				}
				return;
			}
			$.messager.alert("操作提示","服务器错误","info");
		},
		error : function() {
			$.messager.alert("操作提示","网络错误","info");
		}
	})
}

function tempSave(){
	if (acc_number < 1){
		return;
	}
	var array=new Array(acc_number);
	for (var i = 1; i < acc_number + 1; i++) {
		var lockId = $.trim($("#lockId-" + i).val());
		var lockInfo = $.trim($("#lockInfo-" + i).val());//智能锁ID二维码信息
		var bluetoothId = $.trim($("#bluetoothId-" + i).val());
		var imei = $.trim($("#imei-" + i).val());
		var xhId = $("#xhId-" + i).val();
		var cjgzId = $("#cjgzId-" + i).val();
		var sjfjId = $("#sjfjId-" + i).val();
		var repairnNmber = $("#repairnNmber-" + i).val();
		var isWarranty = $("#isWarranty-" + i).val();
		isWarranty = isWarranty == '保内' ? '0' : '1';
		var returnTimes = $("#returnTimes-" + i).val();
		var lastEngineer = $("#lastEngineer-" + i).val();
		var lastAccTime = $("#lastAccTime-" + i).val();
		
		var salesTime = $("#salesTime-" + i).val();
		//验证输入日期，格式不对，默认2014-04-23
		if(salesTime){
			if(!(/^((19|20)\d{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\s([0-2][0-9])\:([0-5][0-9])\:([0-5][0-9])$/.test(salesTime))){
				$("#salesTime-" + i).val("2014-04-23 00:00:00");
			}
		}
		salesTime = $("#salesTime-" + i).val();	
		var payDelivery =  $("input[name='payDelivery-"+i+"']:checked ").val();
		var customerStatus = $("input[name='customerStatus-"+i+"']:checked ").val();
		if(customerStatus == 'un_normal'){
			$("#sxdwId-"+i).val("");//修改为无名件时送修单位id置空
		}
		var sxdwId = $("#sxdwId-" + i).val();
		var remark=$("#remark-" + i).val();	
		var acceptRemark=$("#acceptRemark-" + i).val();	
		var repairNumberRemark=$("#repairNumberRemark-" + i).val();
		var cusName = $("#cusName-" + i).val();
		var expressCompany = $("#expressCompany-" + i).val();
		var expressNo = $("#expressNO-" + i).val();
		
		var bill = $("#bill-" + i).val();
		var sfVersion = $("#sfVersion-" + i).val();
		var insideSoftModel = $("#insideSoftModel-" + i).val();
		
		var outCount = $("#outCount-" + i).val();
		var testMatStatus = $("#testMatStatus-" + i).val();
		var backTime = $("#backTime-" + i).val();
		
		var model = $("#model-" + i).val();
		var marketModel = $("#marketModel-" + i).val();
		var initheckFault = $("#initheckFault-" + i).val();
		var sjfjname = $("#sjfjname-" + i).val();
		
		var reqAjaxPrams = {
						"imei" : imei, 
						"lockId" : lockId,
						"lockInfo" : lockInfo,
						"bluetoothId" : bluetoothId,
						"sxdwId" : sxdwId, 
						"xhId" : xhId, 
						"w_sjfj" : sjfjId, 
						"w_cjgzId" : cjgzId, 
						"repairnNmber" : repairnNmber, 
						"isWarranty" : isWarranty, 
						"returnTimes" : returnTimes, 
						"lastEngineer" : lastEngineer,
						"strLastAccTime" : lastAccTime, 
						"strSalesTime" : salesTime, 
						"payDelivery" : payDelivery, 
						"remark" : remark, 
						"acceptRemark" : acceptRemark,
						"repairNumberRemark" : repairNumberRemark, 
						"customerStatus" : customerStatus, 
						"cusName" : cusName,
						"unNormalExpressCompany" : expressCompany, 
						"unNormalExpressNo" : expressNo, 
						"sfVersion" : sfVersion,
						"insideSoftModel" : insideSoftModel,
						"bill" : bill,
						"outCount" : outCount,
						"testMatStatus" : testMatStatus,
						"backTime" : backTime,

						"model" : model,// xhId
						"marketModel" : marketModel,
						"initheckFault" : initheckFault,// cjgz
						"sjfjname" : sjfjname// sjfj
				}
		array[i-1] = reqAjaxPrams;
	}
	deleteTempData(0);
	var url = ctx + "/workflowcon/copyInfo";
	for(var i = 0;i < array.length; i++ ){
		$.ajax({
			url : url,
			type : 'post',
			async : false,
			dataType : 'json',
			data : {"array":JSON.stringify(array[i])},
			cache : false,
			success : function(returnData) {
				if(i == array.length-1){
					if (returnData.code == '0') {
						$.messager.alert("操作提示",returnData.msg,"info");
					} else {
						$.messager.alert("操作提示",returnData.msg,"info");
					}
				}
			},
			error : function() {
				$.messager.alert("操作提示","网络错误","info");
			}
		});
	}
}

//全选
function selectAll(obj){
	$("input[name='ScanCheckBox']").prop('checked', $(obj).prop('checked'));
}

/*=================记录批量受理 START============================*/
var arr = new Array();//保存受理成功的imei数据
var acc_arr = new Array();//保存受理成功的对象数据
var delRows=new Array();//记录删除行的行号
var allRows = new Array(); //添加过的所有行包括中间删除的
var imeiNullCount = 0;// imei异常
/*=================记录批量受理 END============================*/

/**
 * 删除扫描行
 */
function deleteScanRow(){
	$.messager.confirm("操作提示","确定要删除行？",function(conf){
		if(conf){
			$("input:checkbox[name=ScanCheckBox]:checked").each(function(index,item){
				delRows.push(item["id"]*1);
				$.each(allRows,function(index,items){
		             if(items == item["id"]){
		            	 allRows.splice(index,1);
		        	    }
				});
				$(this).parent().parent().remove();
				acc_number--;
				$("#acc_number").html(acc_number);
				if(imeiNullCount > 0){
					imeiNullCount--;
				}
			});
		}
  });
}

/**
 * 批量受理
 */
function batchAccept() {
	var rowindexFlag = rowindex;
	if ($("#scan_tbody tr").length > 0) {

		$.messager.confirm('系统提示', '您确定要批量受理?', function(conf) {
			if (conf) {
				arr= [];//数组清空
				acc_arr=[];
				imeiNullCount = 0;// imei异常数量设为0
				var repairCount = 0;// 同一数据重复受理次数
				var errorTip = "";// 受理失败提示
				var errorNull = "";// imei为空
				for (var i = 1; i <= rowindex; i++) {
					if($.inArray(i, delRows)==-1){//当行号不在已删除数组里，才能操作	
						var lockId = $.trim($("#lockId-" + i).val());
						var lockInfo = $.trim($("#lockInfo-" + i).val());//智能锁ID二维码信息
						var bluetoothId = $.trim($("#bluetoothId-" + i).val());
						var imei = $.trim($("#imei-" + i).val());
						var xhId = $("#xhId-" + i).val();
						var cjgzId = $("#cjgzId-" + i).val();
						var sjfjId = $("#sjfjId-" + i).val();
						var repairnNmber = $("#repairnNmber-" + i).val();
						var isWarranty = $("#isWarranty-" + i).val();
						isWarranty = isWarranty == '保内' ? '0' : '1';
						var returnTimes = $("#returnTimes-" + i).val();
						var lastEngineer = $("#lastEngineer-" + i).val();
						var lastAccTime = $("#lastAccTime-" + i).val();
						
						var salesTime = $("#salesTime-" + i).val();
						//验证输入日期，格式不对，默认2014-04-23
						if(salesTime){
							if(!(/^((19|20)\d{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\s([0-2][0-9])\:([0-5][0-9])\:([0-5][0-9])$/.test(salesTime))){
								$("#salesTime-" + i).val("2014-04-23 00:00:00");
							}
						}
						salesTime = $("#salesTime-" + i).val();	
						var payDelivery =  $("input[name='payDelivery-"+i+"']:checked ").val();
						var customerStatus = $("input[name='customerStatus-"+i+"']:checked ").val();
						if(customerStatus == 'un_normal'){
							$("#sxdwId-"+i).val("");//修改为无名件时送修单位id置空
						}
						var sxdwId = $("#sxdwId-" + i).val();
						var remark=$("#remark-" + i).val();	
						var acceptRemark=$("#acceptRemark-" + i).val();	
						var repairNumberRemark=$("#repairNumberRemark-" + i).val();
						var cusName = $("#cusName-" + i).val();
						var expressCompany = $("#expressCompany-" + i).val();
						var expressNo = $("#expressNO-" + i).val();
						
						var bill = $("#bill-" + i).val();
						var sfVersion = $("#sfVersion-" + i).val();
						var insideSoftModel = $("#insideSoftModel-" + i).val();
						
						var outCount = $("#outCount-" + i).val();
						var testMatStatus = $("#testMatStatus-" + i).val();
						var backTime = $("#backTime-" + i).val();

						if (imei != null && imei != undefined && imei != "") {
							if(imei.length == 15) {
								var reqAjaxPrams = {
										"imei" : imei, 
										"lockId" : lockId,
										"lockInfo" : lockInfo,
										"bluetoothId" : bluetoothId,
										"sxdwId" : sxdwId, 
										"xhId" : xhId, 
										"w_sjfj" : sjfjId, 
										"w_cjgzId" : cjgzId, 
										"repairnNmber" : repairnNmber, 
										"isWarranty" : isWarranty, 
										"returnTimes" : returnTimes, 
										"lastEngineer" : lastEngineer,
										"strLastAccTime" : lastAccTime, 
										"strSalesTime" : salesTime, 
										"payDelivery" : payDelivery, 
										"remark" : remark, 
										"acceptRemark" : acceptRemark,
										"repairNumberRemark" : repairNumberRemark, 
										"customerStatus" : customerStatus, 
										"cusName" : cusName,
										"unNormalExpressCompany" : expressCompany, 
										"unNormalExpressNo" : expressNo, 
										"sfVersion" : sfVersion,
										"insideSoftModel" : insideSoftModel,
										"bill" : bill,
										"outCount" : outCount,
										"testMatStatus" : testMatStatus,
										"backTime": backTime
								}
								var url = ctx + "/workflowcon/addOrUpdateInfo";								
								
								$.ajax({
									url : url,
									type : 'post',
									async : false,
									dataType : 'json',
									data : reqAjaxPrams,
									cache : false,
									success : function(returnData) {
										if (returnData.code == '0') {
											arr.push(imei);//受理成功的imei保存到数组
											
											//批次号和送修单位id对象相同的保存一次，供发短信使用
											if(acc_arr.length > 0){
												var flag = true;
												for (var int = 0; int < acc_arr.length; int++) {
													if(acc_arr[int].repairnNmber == repairnNmber){
														flag = false;
														return;
													}
												}
												if(flag){
													acc_arr.push({"repairnNmber":repairnNmber,"sxdwId":sxdwId});
												}
											}else{
												acc_arr.push({"repairnNmber":repairnNmber,"sxdwId":sxdwId});
											}
										} else {
											if(repairCount<=1){
												if(errorTip&&errorTip!=returnData.msg){
													errorTip=errorTip+"<br/>"+returnData.msg;
												}else{
													errorTip=returnData.msg;
												}
											}
											repairCount=repairCount*1+1;
										}
									},
									error : function() {
										$.messager.alert("操作提示","网络错误","info");
									}
								});
						    }
							else
						    {
								imeiNullCount++;
								errorNull="IMEI格式异常(必须是15位)";
							}
						}else{
							imeiNullCount++;
							errorNull="批量受理时IMEI不能为空";
						}
					}
				}
				//最终的行数和受理成功的数组长度一致，则说明是全部受理成功，删除受理成功的DOM节点
				if((rowindex-delRows.length)==arr.length){
					
					$.messager.alert("操作提示","批量受理成功","info",function(){
						for(var k=0;k<acc_arr.length;k++){
							var url=ctx+"/workflowcon/pushMsg";
							var param="sxdwId=" + acc_arr[k].sxdwId + "&repairnNmber="+ acc_arr[k].repairnNmber; 
							asyncCallService(url, 'post', false,'json', param, function(returnData) {
								processSSOOrPrivilege(returnData);
							});
						}
						for(var j=0;j<rowindex;j++){
							$("#" + j).parent().parent().remove();
						}
						queryListPage(currentPageNum);
						windowCommClose("wscan");
					});
				}else{
					var url=ctx+"/workflowcon/deleteByImei";
				    var param="imeis="+arr; 
					asyncCallService(url, 'post', false,'json', param, function(returnData) {
		    			processSSOOrPrivilege(returnData);
					});
					
					// 提示
					if(repairCount > 0) {
						$.messager.alert("操作提示", "受理异常，存在原因：<br/>1:" + errorTip, "warning");
					}else if(imeiNullCount > 0) {
						$.messager.alert("操作提示", "受理异常，存在原因：<br/>1:" + errorNull, "warning");
					}else{
						$.messager.alert("操作提示", "批量受理失败","info");
					}
				}
			}
		});
	} else {
		$.messager.alert("操作提示", "暂无受理数据", "info");
	}
}

/**
 * Scan扫描同步数据等待...
 */
function Scanonloading(){  
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo($("#wscan"));   
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在同步数据，请稍候。。。").appendTo($("#wscan")).css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}
/**
 * 去除Scan扫描同步数据等待提示
 */
function Scanremoveload(){  
   $(".datagrid-mask").remove();  
   $(".datagrid-mask-msg").remove();  
}  

function matchLongStr(rowindex){
	var url=ctx+"/lockIdUrl/matchLongStr";
	var lockInfo = $.trim($("#lockInfo-"+rowindex).val());
	var param={
		"urlPrefix" : lockInfo
	}
	asyncCallService(url, 'post', false,'json', param, function(returnData) {
		processSSOOrPrivilege(returnData);
		if(returnData.code== undefined){
			var ret = returnData.data;
			$("#lockInfo-"+rowindex).val(ret.lockInfo);
			$("#lockId-"+rowindex).val(ret.lockId);
		}
	});
}


/**
 * 扫描IMEI同步数据
 */
function synchDatas(rowindex){
	var imei =$.trim($("#imei-"+rowindex).val());
	$("#imei-"+rowindex).keyup(function(){
		if(imei.length <= 15){
			$("#imei-"+rowindex).val(imei);
		}else{
			var ret=isMessagerAlertShow();
			ret = true;
			$("#imei-"+rowindex).css("maxlength","15");
		}
	});
	
	var lockInfo = $.trim($("#lockInfo-"+rowindex).val());
	var lockId = $.trim($("#lockId-"+rowindex).val());
	var imeiCount = 0;		//记录扫描重复次数（包含和本身比较，大于1，重复）
	var lockIdCount = 0;
	if(isMessagerAlertShow()){
		$("#imei-"+rowindex).val("");
		$("#lockId-"+rowindex).val("");
		$("#lockInfo-"+rowindex).val("");
	}else{
		if(imei.length == 15 || (lockId.length > 0 && imei.length ==0)){
			//TODO Check is Repeat or Not
			if($("input[name='scan_imei']").length>=2){
				$("input[name='scan_imei']").each(function(index,item){
					if(imei == $.trim($(this).val()) && imei.length == 15){
						imeiCount++;
					}
				});
			}
			if($("input[name='scan_lockId']").length>=2){
				$("input[name='scan_lockId']").each(function(index,item){
					if(lockId && lockId == $.trim($(this).val())){
						lockIdCount++;
					}
				});
			}
			if(imeiCount>1 || lockIdCount > 1){
				$.messager.alert("操作提示","该设备已存在扫描列表中！","warning",function(){
					$("#scan_tbody input[id$=-"+rowindex+"]").val("");//清空该行所有数据
					$("#lockId-"+rowindex).removeAttr("disabled");
					$("#lockInfo-"+rowindex).removeAttr("disabled");
					$("#bluetoothId-"+rowindex).removeAttr("disabled");
				});
				return;
			}
			{
				//TODO API synch DATES
				/**
				 * @author Li.Shangzhi
				 * 注：
				 * [数据同步,同步过程较为缓慢]
				 * 
				 * 1.扫描过程弹出加载框禁止受理员其他操作
				 * 2.当客户名称、手机号码为空、地址其中一项为空时，同步受理Scan列表为空，[弹出送修单位，快捷创建]需受理员先填写送修单位信息后，才能进行后续操作
				 * 3.当客户名称、手机号码、地址都不为空时，检查本地资源送修单位是否已同步，未同步先同步创建送修单位，进行后续操作
				 * 4.AMS 未同步到型号,售后库先创建同步到售后型号管理默认组中,进行后续操作
				 * 5.SMS 售后数据库未同步到的数据,AMS同步到的数据返修次数默认为第一次0,SMS数据库同步到的记录则按正常流程计算
				 * 
				 */
				Scanonloading();
				
				var reqAjaxPrams= {
						"imei" : imei,
						"lockId" : lockId,
						"lockInfo" : lockInfo
				}
				var url=ctx+"/workflowcon/synchDatas";
				asyncCallService(url, 'post', false,'json', reqAjaxPrams, function(returnData){
					processSSOOrPrivilege(returnData);
					if(returnData.code=='0' || returnData.code=='-30'){
						var wk = returnData.data;
						if(returnData.code=='-30'){
							$.messager.alert("操作提示",returnData.msg, "info");
							Scanremoveload();
							$("#model-"+rowindex).attr("onclick","chooseZbxh("+rowindex+")");
							$("#searchByMarketmodel-"+rowindex).val(wk.w_marketModel);//wk:主板型号对象
							$("#ams_model-"+rowindex).val(wk.w_ams_model);
						}
						if(wk.returnTimes){
							$("#returnTimes-"+rowindex).val(wk.returnTimes);
						}else{
							$("#returnTimes-"+rowindex).val(0);
						}
						$("#lastEngineer-"+rowindex).val(wk.lastEngineer);
						$("#lastAccTime-"+rowindex).val(wk.lastAccTime);
						//TODO 同步处理数据
						//客户名验证
						if(null !=wk.w_cusName && wk.w_cusName.length > 0){
							$("input[name='customerStatus-"+rowindex+"']").attr("disabled","disabled");
							$("#cusName-"+rowindex).val(wk.w_cusName);
							if(CommScanBatchMap[wk.sxdwId] == null || CommScanBatchMap[wk.sxdwId]==undefined){
			 			    	CommScanBatchMap[wk.sxdwId] = wk.repairnNmber;
			 			    }
							$("#sxdwId-"+rowindex).val(wk.sxdwId);
							$("#repairnNmber-"+rowindex).val(CommScanBatchMap[wk.sxdwId]);
						}else{
							$("input[name='customerStatus-"+rowindex+"']").removeAttr("disabled");
							var cusSta = $("input[name='customerStatus-"+rowindex+"']:checked ").val();						
							if(cusSta == 'normal'){
								$("#cusName-"+rowindex).attr("onclick","chooseCustomer("+rowindex+")");
								$("#cusName-"+rowindex).attr("readonly","readonly");
							}						
						}
						//主板型号验证
						if(null !=wk.w_model && wk.w_model.length > 0){//只匹配到一条数据，默认选中
							$("#model-"+rowindex).val(wk.w_model);
							$("#searchByMarketmodel-"+rowindex).val(wk.w_marketModel);
							$("#ams_model-"+rowindex).val(wk.w_ams_model);
							$("#model-"+rowindex).attr("onclick","chooseZbxh("+rowindex+")");
							if(wk.isWarranty == '0'){
								wk.isWarranty ='保内';
							}else if(wk.isWarranty == '1'){
								wk.isWarranty = '保外';
							}else{
								wk.isWarranty = '未知';
							}
							$("#isWarranty-"+rowindex).val(wk.isWarranty);
						}else{
							$("#searchByMarketmodel-"+rowindex).val(wk.w_marketModel);
							$("#ams_model-"+rowindex).val(wk.w_ams_model);
							$("#model-"+rowindex).val("");
							$("#model-"+rowindex).attr("onclick","chooseZbxh("+rowindex+")");
						}
						$("#imei-"+rowindex).val(wk.imei);
						$("#lockId-"+rowindex).val(wk.lockId);
						$("#bluetoothId-"+rowindex).val(wk.bluetoothId);
						$("#lockInfo-"+rowindex).val(wk.lockInfo);
						
						$("#lockId-"+rowindex).attr("disabled","disabled");
						$("#lockInfo-"+rowindex).attr("disabled","disabled");
						$("#bluetoothId-"+rowindex).attr("disabled","disabled");
						
						$("#xhId-"+rowindex).val(wk.xhId);
						$("#marketModel-"+rowindex).val(wk.w_marketModel);
						$("#lastEngineer-"+rowindex).val(wk.lastEngineer);
						$("#lastAccTime-"+rowindex).val(wk.lastAccTime);
						$("#salesTime-"+rowindex).val(wk.salesTime);
						
						$("#bill-" + rowindex).val(wk.bill);
						$("#sfVersion-" + rowindex).val(wk.sfVersion);
						$("#insideSoftModel-" + rowindex).val(wk.insideSoftModel);
						$("#outCount-" + rowindex).val(wk.outCount);
						$("#testMatStatus-" + rowindex).val(wk.testMatStatus);
						$("#backTime-" + rowindex).val(wk.backTime);
						
						$("#isWarranty-"+rowindex).attr("disabled","disabled");
						$("#salesTime-"+rowindex).attr("disabled","disabled");
						Scanremoveload();
						var index=rowindex+1;
						$("#imei-"+index).focus();
						
						$("#pricedCount-" + rowindex).val(wk.pricedCount); // 批次号是否已报价
						$("#oldRepairNumber-" + rowindex).val(wk.oldRepairNumber); // 修改前的批次号
						
						//该批次已经报价，是否更改批次号
	 					if($("#pricedCount-"+rowindex).val() == 1){
	 						var repArr = [];
	 						$("input[name='oldRepairNumber']").each(function(i,j) {
	 							if($("#sxdwId-"+(i+1)).val() && $("#oldRepairNumber-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+rowindex).val() 
	 									&& $("#oldRepairNumber-" + rowindex).val() != $("#repairnNmber-"+(i+1)).val()){
	 								repArr.push($("#repairnNmber-"+(i+1)).val());
	 							}
	 						});
	 						if(repArr.length > 0){
	 							$("#repairnNmber-" + rowindex).val(repArr[0]);
	 						}else{
	 							$.messager.confirm('系统提示', '批次号【'+($("#repairnNmber-" + rowindex).val())+'】的设备已报价，是否重新生成批次号?', function(conf) {
	 								if (conf) {
	 									var currentRepairNumber = new Date().getTime();
	 									$("input[name='repairnNumber']").each(function(i, j){
	 										if($("#sxdwId-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+rowindex).val()){
	 											$("#repairnNmber-"+(i+1)).val(currentRepairNumber);
	 										}
	 									});
	 								}
	 							});
	 						}
						}
						
					}else if(returnData.code=='-100'){
						var work1  = returnData.data;
						$("#backTime-"+rowindex).val(work1.backTime);
						//AMS 数据无法同步
						if(returnData.msg){
							$("#imei-"+rowindex).val("");
							if(!isMessagerAlertShow()){
								$.messager.alert("操作提示",returnData.msg,"error",function(){
									$("#scan_tbody input[id$=-"+rowindex+"]").val("");//清空该行所有数据
									$("#lockId-"+rowindex).removeAttr("disabled");
									$("#lockInfo-"+rowindex).removeAttr("disabled");
									$("#bluetoothId-"+rowindex).removeAttr("disabled");
								});
							}
							Scanremoveload();
						}else{
							var con = 0;
							$.messager.confirm('系统提示','数据同步失败,确定手工录入数据到系统？', function(conf){
								if(conf){
									$("#model-"+rowindex).attr("onclick","chooseZbxh("+rowindex+")");
									
									var index=rowindex+1;
									$("#imei-"+index).focus();
									con+=1;
									$("#searchByMarketmodel-"+rowindex).val("");//手工录入时上次查询到的市场型号置空
									$("#ams_model-"+rowindex).val("");//手工录入时上次查询到的市场型号置空
									$("#model-"+rowindex).val("");
									$("#isWarranty-"+rowindex).val('保外');
									$("#salesTime-"+rowindex).val('2014-04-23 00:00:00');
									if(returnData.data){
										if(returnData.data.returnTimes){
											$("#returnTimes-"+rowindex).val(returnData.data.returnTimes);
										}else{
											$("#returnTimes-"+rowindex).val(0);
										}
										$("#lastEngineer-"+rowindex).val(returnData.data.lastEngineer);
										$("#lastAccTime-"+rowindex).val(returnData.data.lastAccTime);
										//客户名验证
										if(null !=returnData.data.w_cusName && returnData.data.w_cusName.length > 0){
											$("input[name='customerStatus-"+rowindex+"']").attr("disabled","disabled");
											$("#cusName-"+rowindex).val(returnData.data.w_cusName);
											if(CommScanBatchMap[returnData.data.sxdwId] == null || CommScanBatchMap[returnData.data.sxdwId]==undefined){
							 			    	CommScanBatchMap[returnData.data.sxdwId] = returnData.data.repairnNmber;
							 			    }
											$("#sxdwId-"+rowindex).val(returnData.data.sxdwId);
											$("#repairnNmber-"+rowindex).val(CommScanBatchMap[returnData.data.sxdwId]);
										}else{
											$("input[name='customerStatus-"+rowindex+"']").removeAttr("disabled");
											var cusSta = $("input[name='customerStatus-"+rowindex+"']:checked ").val();						
											if(cusSta == 'normal'){
												$("#cusName-"+rowindex).attr("onclick","chooseCustomer("+rowindex+")");
												$("#cusName-"+rowindex).attr("readonly","readonly");
											}						
										}
										
										$("#pricedCount-" + rowindex).val(returnData.data.pricedCount); // 批次号是否已报价
										$("#oldRepairNumber-" + rowindex).val(returnData.data.oldRepairNumber); // 修改前的批次号
										
									}else{
										$("#returnTimes-"+rowindex).val(0);
									}
									$("#isWarranty-"+rowindex).removeAttr("disabled");
									$("#salesTime-"+rowindex).removeAttr("disabled");
									$("#lockId-"+rowindex).removeAttr("disabled");
									$("#lockInfo-"+rowindex).removeAttr("disabled");
									$("#bluetoothId-"+rowindex).removeAttr("disabled");
									
									//该批次已经报价，是否更改批次号
				 					if($("#pricedCount-"+rowindex).val() == 1){
				 						var repArr = [];
				 						$("input[name='oldRepairNumber']").each(function(i,j) {
				 							if($("#sxdwId-"+(i+1)).val() && $("#oldRepairNumber-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+rowindex).val() 
				 									&& $("#oldRepairNumber-" + rowindex).val() != $("#repairnNmber-"+(i+1)).val()){
				 								repArr.push($("#repairnNmber-"+(i+1)).val());
				 							}
				 						});
				 						if(repArr.length > 0){
				 							$("#repairnNmber-" + rowindex).val(repArr[0]);
				 						}else{
				 							$.messager.confirm('系统提示', '批次号【'+($("#repairnNmber-" + rowindex).val())+'】的设备已报价，是否重新生成批次号?', function(conf) {
				 								if (conf) {
				 									var currentRepairNumber = new Date().getTime();
				 									$("input[name='repairnNumber']").each(function(i, j){
				 										if($("#sxdwId-"+(i+1)).val() && $("#sxdwId-"+(i+1)).val() == $("#sxdwId-"+rowindex).val()){
				 											$("#repairnNmber-"+(i+1)).val(currentRepairNumber);
				 										}
				 									});
				 								}
				 							});
				 						}
									}
				 					
								}else if(con==0){
									$("#scan_tbody input[id$=-"+rowindex+"]").val("");//清空该行所有数据
								}
							});
							Scanremoveload();
						}
					}else{
						$("#imei-"+rowindex).val("");
						if(!isMessagerAlertShow()){
							$.messager.alert("操作提示",returnData.msg,"error",function(){
								$("#scan_tbody input[id$=-"+rowindex+"]").val("");//清空该行所有数据
								$("#lockId-"+rowindex).removeAttr("disabled");
								$("#lockInfo-"+rowindex).removeAttr("disabled");
								$("#bluetoothId-"+rowindex).removeAttr("disabled");
							});
						}
						Scanremoveload();
					}
				}, function(){
					$.messager.alert("操作提示","网络错误","info");
					Scanremoveload();
				});
			}
		}else if(imei.length < 15 && imei.length > 0){
			$("#imei-"+rowindex).val(imei);
		}else{
			$.messager.alert("操作提示","IMEI超过15位或者智能锁ID不正确，请确认！","info",function(){
				$("#scan_tbody input[id$=-"+rowindex+"]").val("");//清空该行所有数据
			});
		}
	}
	
}

//关闭主板型号选择，如果保内保外为空，判断结果
function zbxh_window_close(rowindex){
	var xhId = $.trim($("#xhId-"+rowindex).val());
	if(xhId){
		//判断保内保外
		var url=ctx+"/workflowcon/zbxhIsWarranty";
		var param="xhId=" + xhId + "&salesTime="+ $("#salesTime-"+rowindex).val() + "&sxdw=" + $("#cusName-"+rowindex).val(); 
		asyncCallService(url, 'post', false,'json', param, function(returnData) {
			processSSOOrPrivilege(returnData);
			if(returnData.code=='0'){
				var ret=returnData.data;
				if(ret==0){
					$("#isWarranty-"+rowindex).val('保内');
				}else if(ret==1){
					$("#isWarranty-"+rowindex).val('保外');
				}else{
					$("#isWarranty-"+rowindex).val('未知');
				}
			}
		});
	}
	$("#zbxh_rowindex").val("");
	$("#searchBymodel").val("");
	windowCommClose('zbsc');
}

function isMessagerAlertShow(){
	var alertNode = $(".messager-window").css("display");
	 if('block' == alertNode ){
		return true;
	}  
	return false;
}


function zbxhmanageInsert(){
	
	$("#mId_hidden").val(0);
	//清空下缓存
	$("#modelP").val("");
	$("#marketModelP").val("");
	$("#zbremarkP").val("");
	updateWindowOpen();
	comboboxInit();
	ceateComboboxInit();
}

function zbxhmanageSave(){
	var isValid = $('#wzbform').form('validate');
	if(isValid){
		var rowindex = $("#hideSindex").val();
		var model = $.trim($("#modelP").val().toUpperCase());
		var marketModel = $.trim($("#marketModelP").val().toUpperCase());
		var gId = $("#type").combobox('getValue');
		var modelType = $("#type").combobox('getText');
		var remark = $("#zbremarkP").val().trim();
		var createType = $("#createType").combobox('getValue');
		var reqAjaxPrams="&model="+model+"&gId="+gId+"&marketModel="+marketModel+"&modelType="+modelType+"&remark="+remark+
		"&createType="+createType+"&enabledFlag=0&mId=0";
		var url=ctx+"/zbxhmanage/insertZbxhmanageByAccept";
		asyncCallService(url, 'post', false,'json', reqAjaxPrams, function(returnData){
			processSSOOrPrivilege(returnData);
			if(returnData.code=='0'){
				updateWindowClose();
				queryListPageZBXH(1);//重新加载ZbxhmanageList
			}else{
				$.messager.alert("操作提示",returnData.msg,"info");
			}
		}, function(){
			 $.messager.alert("操作提示","网络错误","info");
		});
	}
}

//打开增加或修改窗口
function updateWindowOpen()
{
	windowVisible("wzb");
	$(".validatebox-tip").remove();
	$(".validatebox-invalid").removeClass("validatebox-invalid");
	$('#wzb').window('open');
	
}

//关闭增加或修改窗口
function updateWindowClose()
{
	$('#wzb').window('close');
}

// 类型下拉框的公用方法
function comboboxInit() {
	var url = ctx + '/basegroupcon/queryList';
	var param = "enumSn=BASE_ZBXH";
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var diclist = '[';
		$.each(returnData.data, function(i, j) {
			diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
		});
		var reg = /,$/gi;
		diclist = diclist.replace(reg, "");
		diclist += ']';
		$('#type').combobox({
			data : eval('(' + diclist + ')'),
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function(data) {
				if (data) {
					$('#type').combobox('setValue', data[0].id);
				}
			}
		});
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}

// 类型下拉框的公用方法
function ceateComboboxInit() {
	var url = ctx + '/basegroupcon/queryList';
	var param = "enumSn=BASE_CJLX";
	asyncCallService(url, 'post', false, 'json', param, function(returnData) {
		var diclist = '[';
		$.each(returnData.data, function(i, j) {
			diclist += '{"id":"' + j.gid + '","text":"' + j.gname + '"},';
		});
		var reg = /,$/gi;
		diclist = diclist.replace(reg, "");
		diclist += ']';
		$('#createType').combobox({
			data : eval('(' + diclist + ')'),
			valueField : 'id',
			textField : 'text',
			onLoadSuccess : function(data) {
				if (data) {
					$('#createType').combobox('setValue', data[0].id);
				}
			}
		});
	}, function() {
		$.messager.alert("操作提示", "网络错误", "info");
	});
}



/**
 * 复制送修备注，给后面的列赋相同的值
 */
function copySxRemark(rowindex) {
	var selectName =$("#remark-"+rowindex).val();
	$("input[name='sxRemark']").each(function() {
		var id = $(this).attr("id");
 		var strs= new Array(); //定义一数组
 		strs=id.split("-"); //字符分割 
		if(strs[1]>rowindex){
			//先删除，后新增的行赋值
			$("#remark-"+strs[1]).val(selectName);
 		}
	});
}

/**
 * 复制批次备注，给后面的列赋相同的值
 */
function copyRepairNumberRemark(rowindex) {
	var selectName =$("#repairNumberRemark-"+rowindex).val();
	$("input[name='repairNumberRemark']").each(function() {
		var id = $(this).attr("id");
 		var strs= new Array(); //定义一数组
 		strs=id.split("-"); //字符分割 
		if(strs[1]>rowindex){
			//先删除，后新增的行赋值
			$("#repairNumberRemark-"+strs[1]).val(selectName);
 		}
	});
}
